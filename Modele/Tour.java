package Modele;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import Modele.Joueur.Action;
import Modele.Joueur.ActionNeutre;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Tour{
	
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
	
	public Tour(){
		this.estAttaque = new Triplet<>(pasAttaque, 0, 0);
	}
	
	public Tour(Joueur m_joueurPremier, Joueur m_joueurSecond){
		this.joueurPremier = m_joueurPremier;
		this.joueurSecond = m_joueurSecond;
		this.pioche = new Pioche();
		this.defausse = new Defausse();
		this.estAttaque = new Triplet<>(pasAttaque, 0, 0);
	}
	
	public int jouerTour() throws Exception{
		
		if(jouerTourJoueur(joueurPremier)){	
			if(jouerTourJoueur(joueurSecond)){
				if(!pioche.estVide()){
					return aucunJoueurPerdu;
				}else{
					return piocheVide;
				}
			}else{
				return joueurSecondPerdu;
			}
		}else{
			return joueurPremierPerdu;
		}
	}
	
	public boolean jouerTourJoueur(Joueur joueur) throws Exception{
		int choixAction ;	
		ActionsJouables actions_jouables ;
		Action actionChoisie;
		
		System.out.println("/*************************************************************************************************************/");
		System.out.println("Joueur : " + joueur.getNom() + ", position : " + joueur.getPositionFigurine());
		System.out.println("Main : " + joueur.getMain().getMain() + "\n");
		afficherPiste(joueurPremier.getPositionFigurine(), joueurSecond.getPositionFigurine());
		
		actions_jouables = joueur.peutFaireAction(estAttaque);
		choixAction = selectionnerAction(actions_jouables);		
		actionChoisie = rechercherAction(choixAction, actions_jouables);
		
		if(actionChoisie.getTypeAction() == Joueur.ActionImpossible){
			return joueurPerdu;
		}else{
			estAttaque = executerAction(actionChoisie, joueur);			
		}
		
		if(actionChoisie.getTypeAction() == Joueur.Parade){
			System.out.println("Joueur : " + joueur.getNom() + ", position : " + joueur.getPositionFigurine());
			System.out.println("Main : " + joueur.getMain().getMain() + "\n");
			
			actions_jouables = joueur.peutFaireAction(estAttaque);
			choixAction = selectionnerAction(actions_jouables);
			actionChoisie = rechercherAction(choixAction, actions_jouables);
			
			if(actionChoisie.getTypeAction() == Joueur.ActionImpossible){
				return joueurPerdu;
			}else{
				estAttaque = executerAction(actionChoisie, joueur);
			}
		}
		remplirMain(joueur);
		
		System.out.println("Joueur : " + joueur.getNom() + ", position : " + joueur.getPositionFigurine());
		System.out.println("Main : " + joueur.getMain().getMain() + "\n");
		
		return joueurPasPerdu;
	}

	private Action rechercherAction(int choixAction, ActionsJouables actions_jouables) {
		
		if(choixAction == Joueur.ActionImpossible){
			return new ActionNeutre(Joueur.ActionImpossible);
		}
		
		Action actionCherchee = null;		
		
		Enumeration<Action> e = actions_jouables.elements();
		int i = 0;
		
		while(e.hasMoreElements() && i != choixAction){
			e.nextElement();				
			i++;
		}
		
		if(i == choixAction){
			actionCherchee = e.nextElement();
		}
		
		return actionCherchee;
	}

	public void remplirMain(Joueur j){		
		int nbCarteMain = j.getMain().getNombreCarte();
		
		int i=nbCarteMain;
			
		while(!pioche.estVide() && i < nombreCarteMax){
			j.ajouterCarteDansMain(pioche.piocher());
			i++;
		}
	}
	
	private int selectionnerAction(ActionsJouables actions_jouables) throws Exception {
		int choixAction;
		
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		
		System.out.println(actions_jouables);
		
		if(actions_jouables.size() == 0){
			return Joueur.ActionImpossible;
		}
		
		System.out.println("Veuillez effectuer votre choix d'action : nombre entre 0 et N (N étant un entier naturel)");
		
		choixAction = Integer.parseInt(s.nextLine());
		
		return choixAction;
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
	
	private Triplet<Integer, Integer, Integer> executerAction(Action actionAJouer, Joueur joueur) throws Exception{
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
		
		System.out.println("\nVous avez joué : carte de déplacement : " + carteDeplacement + ", carte d'action : " + carteAction);
		
		return new Triplet<>(typeAction, nbCartesAttqJouees, valeurCarteAttqJouee);
	}
	
	public void afficherPiste(int positionF1, int positionF2){
		String str = "";
		
		for(int i = 1 ; i < 24; i++){
			if(i == positionF1){
				str += "♙ ";
			}else if(i == positionF2){
				str += "♟ ";
			}else{
				str += "_ ";
			}	
		}
		
		str += "\n";
		
		System.out.println(str);
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