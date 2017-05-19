package Modele;

import java.util.ArrayList;
import java.util.Enumeration;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Plateau.Case;
import Modele.Plateau.MessageBox;
import Modele.Plateau.Piste;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Tour implements Visitable{
	
	// CONSTANTES
	
	// STATUT JOUEUR
	
	private final static boolean joueurPerdu = false;
	private final static boolean joueurPasPerdu = true;
	
	// STATUT FIN DE PARTIE
	
	public final static int joueurPremierPerdu = 0;
	public final static int joueurSecondPerdu = 1;
	public final static int aucunJoueurPerdu = 2;
	public final static int piocheVide = 3;
	
	// STATUT ATTAQUE
	
	public final static int PasAttaque = 0;
	public final static int AttaqueDirecte = 1;
	public final static int AttaqueIndirecte = 2;
	
	// ATTRIBUTS
	
	private Joueur joueurPremier;
	private Joueur joueurSecond;
	private Pioche pioche;
	private Defausse defausse;
	private MessageBox messageBox;
	private Piste piste ;
	private Historique histo ;
	
	// Type de l'attaque, nombre de cartes attaque, valeur de la carte attaque
	private Triplet<Integer, Integer, Integer> estAttaque;
	private ActionsJouables actions_jouables;
	
	// CONSTRUCTEURS
	
	public Tour(Piste piste, Historique histo){
		
		this.piste = piste ;
		this.histo = histo ;
		this.estAttaque = new Triplet<>(PasAttaque, 0, 0);
	}
	
	public Tour(Joueur m_joueurPremier, Joueur m_joueurSecond){
		this.pioche = new Pioche();
		this.defausse = new Defausse();
		this.joueurPremier = m_joueurPremier;
		this.joueurSecond = m_joueurSecond;
		this.estAttaque = new Triplet<>(PasAttaque, 0, 0);
	}
	
	// CLONE
	
	@Override
	public Tour clone () {
		
		Tour tour = new Tour (this.piste, this.histo) ;
		tour.joueurPremier = this.joueurPremier.clone() ;
		tour.joueurSecond = this.joueurSecond.clone() ;
		tour.pioche = this.pioche.clone() ;
		tour.defausse = this.defausse.clone() ;
		
		return tour ;
		
	}
	
	/**
	 * MOTEUR
	 */
	
	private Triplet<Integer, Integer, Integer> avancer_reculer_fuire (Action actionAJouer, Joueur joueur, int typeAction) throws Exception {
		
		Carte carteDeplacement = actionAJouer.getCarteDeplacement() ;
		joueur.defausserCartes(carteDeplacement, 1, defausse) ;
		switch(typeAction) {
		case Joueur.Avancer : joueur.avancer(carteDeplacement.getContenu()) ; break ;
		case Joueur.Reculer : joueur.reculer(carteDeplacement.getContenu()) ; break ;
		default : throw new Exception ("Modele.Tour.jouer_avancer_reculer_fuire : typeAction inconnu") ;
		}
		return new Triplet <> (PasAttaque,0,0) ;
		
	}
	
	private Triplet<Integer, Integer, Integer> attaquer_directement_parer (Action actionAJouer, Joueur joueur) throws Exception {
		
		Carte carteAction = actionAJouer.getCarteAction();
		joueur.defausserCartes(carteAction, actionAJouer.getNbCartes(), defausse) ;
		return new Triplet <> (null, actionAJouer.getNbCartes(), carteAction.getContenu()) ;
		
	}
	
	private Triplet<Integer, Integer, Integer> jouerAction(Action actionAJouer, Joueur joueur) throws Exception{
		
		Triplet<Integer, Integer, Integer> config ;
		
		switch(actionAJouer.getTypeAction()) {
		case Joueur.Avancer : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ;
		case Joueur.Reculer : case Joueur.Fuite : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Reculer) ;
		default :
		}
		config = attaquer_directement_parer(actionAJouer, joueur) ;
		switch(actionAJouer.getTypeAction()) {
		case Joueur.AttaqueDirecte : config.setC1(AttaqueDirecte); break ;
		case Joueur.AttaqueIndirecte : avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ; config.setC1(AttaqueIndirecte) ; break ;
		case Joueur.Parade : config.setC1(PasAttaque) ; break ;
		default : throw new Exception("Modele.Tour.executerAction : typeAction inconnu") ;
		}
		
		return config ;
		
	}
	
	/**
	 * VUE
	 */
	
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
		messageBox.setTexte(joueurPremier.getNom() + " commence");
	}
	
	public void commencerTour(Joueur joueur){
		joueur.getMain().setVisible(true);
	}
	
	public boolean possibiliteAction(Joueur joueur, ArrayList<Carte> cartes){
		try {
			Action action;
			Enumeration<Action> e;
			
			actions_jouables = joueur.peutFaireActionAvecCarteSelectionne(joueur.getMain().getCote(), cartes, estAttaque);
			
			if(joueur.peutFaireAction(estAttaque).size() != 0){
				e = actions_jouables.elements();
				
				joueur.getPiste().reinitialiserCouleurCase();
				
				while(e.hasMoreElements()){
					action = e.nextElement();
					if(actionNeutre(action)){
						joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.ROUGE);
					}else if(actionOffensive(action)){
						joueur.getPiste().getCases().get(joueurAdverse(joueur).getPositionDeMaFigurine()-1).setCouleur(Case.VERT);
					}else{
						if(action.getTypeAction() == Joueur.Parade){
							joueur.getPiste().getCases().get(joueur.getPositionDeMaFigurine()-1).setCouleur(Case.JAUNE);
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
	
	public boolean executerAction(Joueur joueur, float x, float y) throws Exception{
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
				   caseClicked.getNumero() == joueurAdverse(joueur).getPositionDeMaFigurine())){
					trouve = true;
				}
			}
			
			if(trouve){
				try {
					estAttaque = jouerAction(action, joueur);
					if(action.getTypeAction() != Joueur.Parade){
						joueur.remplirMain(pioche);
						changerJoueur(joueur);
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				joueur.getPiste().reinitialiserCouleurCase();
				
				actions_jouables = null;
				return trouve;
			}else{
				return trouve;
			}
		}else{
			return trouve;
		}
	}
	
	public boolean adversairePeutFaireAction(Joueur joueur){
		ActionsJouables testAction;
		
		try {
			testAction = joueurAdverse(joueur).peutFaireAction(estAttaque);
			if(testAction.size() == 0){
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void changerJoueur(Joueur joueur){		
		joueur.getMain().setVisible(false);
		joueurAdverse(joueur).getMain().setVisible(true);
	}
	
	/*private Triplet<Integer, Integer, Integer> jouerAction(Action actionAJouer, Joueur joueur) throws Exception{
		Carte carteDeplacement=null;
		Carte carteAction=null;
		
		int typeAction;
		int nbCartesAttqJouees;
		int valeurCarteAttqJouee;
		
		switch(actionAJouer.getTypeAction()){
			case Joueur.Reculer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserCartes(carteDeplacement, 1, defausse);
				typeAction = PasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Avancer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserCartes(carteDeplacement, 1, defausse);
				typeAction = PasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.AttaqueDirecte : 
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserCartes(carteAction, actionAJouer.getNbCartes(), defausse);
				typeAction = AttaqueDirecte; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.AttaqueIndirecte :
				// On avance dans un premier temps
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserCartes(carteDeplacement, 1, defausse);
				
				// On attaque dans un second temps	
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserCartes(carteAction, actionAJouer.getNbCartes(), defausse);
				typeAction = AttaqueIndirecte; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.Parade :
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserCartes(carteAction, actionAJouer.getNbCartes(), defausse);
				typeAction = PasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Fuite :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserCartes(carteDeplacement, 1, defausse);
				typeAction = PasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			default: throw new Exception("Erreur lors de l'exécution de l'action");
		}
		
		return new Triplet<>(typeAction, nbCartesAttqJouees, valeurCarteAttqJouee);
	}*/
	
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
	
	public Triplet<Integer, Integer, Integer> getEstAttaque() {
		return estAttaque;
	}

	public void setEstAttaque(Triplet<Integer, Integer, Integer> estAttaque) {
		this.estAttaque = estAttaque;
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}
	
}