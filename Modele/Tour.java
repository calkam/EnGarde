package Modele;

import java.util.ArrayList;
import java.util.Enumeration;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Plateau.Case;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Tour implements Visitable{
	
	private final static int nombreCarteMax = 5;
	
	private final static boolean joueurPerdu = false;
	private final static boolean joueurPasPerdu = true;
	
	public final static int joueurPremierPerdu = 0;
	public final static int joueurSecondPerdu = 1;
	public final static int aucunJoueurPerdu = 2;
	public final static int piocheVide = 3;
	
	public final static int pasAttaque = 0;
	public final static int attaqueDirect = 1;
	public final static int attaqueIndirect = 2;
	
	private Joueur joueurPremier;
	private Joueur joueurSecond;
	private Pioche pioche;
	private Defausse defausse;
	// Type de l'attaque, nombre de cartes attaque, valeur de la carte attaque
	private Triplet<Integer, Integer, Integer> estAttaque;
	
	private ActionsJouables actions_jouables;
	
	public Tour(){
		this.estAttaque = new Triplet<>(pasAttaque, 0, 0);
	}
	
	public Tour(Joueur m_joueurPremier, Joueur m_joueurSecond){
		this.pioche = new Pioche();
		this.defausse = new Defausse();
		this.joueurPremier = m_joueurPremier;
		this.joueurSecond = m_joueurSecond;
		this.estAttaque = new Triplet<>(pasAttaque, 0, 0);
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		boolean retour = false;
		retour = retour || pioche.accept(v);
		retour = retour || defausse.accept(v);
		retour = retour || joueurPremier.accept(v);
		retour = retour || joueurSecond.accept(v);
		return retour;
	}
	
	public boolean actionNeutre(Action action){
		return action.getTypeAction() == Joueur.Reculer || action.getTypeAction() == Joueur.Avancer; 
	}
	
	public boolean actionOffensive(Action action){
		return action.getTypeAction() == Joueur.AttaqueDirecte || action.getTypeAction() == Joueur.AttaqueIndirecte; 
	}
	
	public Joueur joueurAdverse(Joueur joueur){
		if(joueur.equals(joueurPremier)){
			return joueurSecond;
		}else{
			return joueurPremier;
		}
	}
	
	public void jouerTour() throws Exception{
		commencerTour(joueurPremier);
	}
	
	public void commencerTour(Joueur joueur){
		joueur.getMain().setVisible(true);
	}
	
	public boolean possibiliteAction(Joueur joueur, ArrayList<Carte> cartes){
		try {
			Action action;
			Enumeration<Action> e;
			
			actions_jouables = joueur.peutFaireAction(joueur.getMain().getCote(), cartes, estAttaque);
			
			if(joueur.peutFaireAction(joueur.getMain().getCote(), joueur.getCartesDeLaMain(), estAttaque).size() != 0){
				e = actions_jouables.elements();
				
				joueur.getPiste().reinitialiserCouleurCase();
				
				while(e.hasMoreElements()){
					action = e.nextElement();
					if(actionNeutre(action)){
						joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.ROUGE);
					}else if(actionOffensive(action)){
						joueur.getPiste().getCases().get(joueurAdverse(joueur).getPositionFigurine()-1).setCouleur(Case.VERT);
					}else{
						if(action.getTypeAction() == Joueur.Parade){
							joueur.getPiste().getCases().get(joueur.getPositionFigurine()-1).setCouleur(Case.JAUNE);
						}else if(action.getTypeAction() == Joueur.Fuite){
							joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.JAUNE);
						}
					}
				}
				return joueurPasPerdu;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return joueurPerdu;
	}
	
	public boolean executerAction(Joueur joueur, float x, float y){
		Action action = null;
		Enumeration<Action> e;
		Case caseClicked;
		boolean trouve = false;
		
		caseClicked = joueur.getPiste().getCaseClicked(x, y);
		
		if(actions_jouables != null && actions_jouables.size() != 0){
			e = actions_jouables.elements();
			
			while(e.hasMoreElements() && !trouve){
				action = e.nextElement();
				if(caseClicked.getNumero() == action.getPositionArrivee() || (actionOffensive(action) && 
				   caseClicked.getNumero() == joueurAdverse(joueur).getPositionFigurine())){
					trouve = true;
				}
			}
			
			if(trouve){
				try {
					estAttaque = jouerAction(action, joueur);
					if(action.getTypeAction() != Joueur.Parade){
						remplirMain(joueur);
						changerJoueur(joueur);
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				joueur.getPiste().reinitialiserCouleurCase();
				actions_jouables = null;
				return joueurPasPerdu;
			}else{
				return joueurPasPerdu;
			}
		}else{
			return joueurPerdu;
		}
	}
	
	public void changerJoueur(Joueur joueur){		
		joueur.getMain().setVisible(false);
		joueurAdverse(joueur).getMain().setVisible(true);
	}
	
	private ArrayList<Carte> getAutreCarteDeValeur(int valeur, int nbCartes, Joueur joueur){
		ArrayList<Carte> cartes = new ArrayList<Carte>();
		
		int i=1; //On commence à 1 car une carte a déjà été défaussée dans executerAction() 
		
		for(Carte c : joueur.getCartesDeLaMain()){
			if(c.getContenu() == valeur){
				cartes.add(c);
				i++;
			}
			if(i>=nbCartes){
				break;
			}
		}
		
		return cartes;
	}
	
	private Triplet<Integer, Integer, Integer> jouerAction(Action actionAJouer, Joueur joueur) throws Exception{
		Carte carteDeplacement=null;
		Carte carteAction=null;
		
		int typeAction;
		int nbCartesAttqJouees;
		int valeurCarteAttqJouee;
		
		ArrayList<Carte> cartesDeMemeValeur;
		
		switch(actionAJouer.getTypeAction()){
			case Joueur.Reculer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Avancer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.AttaqueDirecte : 
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						defausse.ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}
				typeAction = attaqueDirect; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.AttaqueIndirecte :
				// On avance dans un premier temps
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				
				// On attaque dans un second temps	
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						defausse.ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}
				typeAction = attaqueIndirect; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.Parade :
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						defausse.ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}				
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Fuite :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			default: throw new Exception("Erreur lors de l'exécution de l'action");
		}
		
		return new Triplet<>(typeAction, nbCartesAttqJouees, valeurCarteAttqJouee);
	}
	
	public void remplirMain(Joueur j){		
		int nbCarteMain = j.getMain().getNombreCarte();
		
		int i=nbCarteMain;
		
		while(!pioche.estVide() && i < nombreCarteMax){
			Carte c = pioche.piocher();
			c.setVisible(true);
			j.ajouterCarteDansMain(c);
			i++;
		}
		
		j.getMain().repositionnerMain();
	}
	
	/**
	 * GETTER/SETTER
	 */
	public Pioche getPioche() {
		return pioche;
	}

	public Joueur getJoueurPremier() {
		return joueurPremier;
	}

	public void setJoueurPremier(Joueur joueurPremier) {
		this.joueurPremier = joueurPremier;
	}

	public Joueur getJoueurSecond() {
		return joueurSecond;
	}

	public void setJoueurSecond(Joueur joueurSecond) {
		this.joueurSecond = joueurSecond;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	public Defausse getDefausse() {
		return this.defausse;
	}
	
	public void setDefausse(Defausse defausse) {
		this.defausse = defausse;
	}
	
}