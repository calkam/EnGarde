package Modele;

import java.util.Scanner;

import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Joueur.JoueurDroit;
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
	// Type de l'attaque, nombre de carte attaque
	private Couple<Integer, Integer> estAttaque;
	
	public Tour(){
		this.estAttaque = new Couple<>(pasAttaque, 0);
	}
	
	public Tour(Joueur m_joueurPremier, Joueur m_joueurSecond){
		this.joueurPremier = m_joueurPremier;
		this.joueurSecond = m_joueurSecond;
		this.pioche = new Pioche();
		this.defausse = new Defausse();
		this.estAttaque = new Couple<>(pasAttaque, 0);
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
		
		actions_jouables = joueur.peutFaireAction(estAttaque);
		choixAction = selectionnerAction(actions_jouables);
		
		if(choixAction == Joueur.ActionImpossible){
			return joueurPerdu;
		}else{
			//estAttaque = executerAction(choixAction, actions_jouables, joueur);			
		}
		
		if(choixAction == Joueur.Parade){
			estAttaque.setC1(pasAttaque); estAttaque.setC2(0); 
			actions_jouables = joueur.peutFaireAction(estAttaque);
			choixAction = selectionnerAction(actions_jouables);
			
			if(choixAction == Joueur.ActionImpossible){
				return joueurPerdu;
			}else{
				//estAttaque = executerAction(choixAction, actions_jouables, joueur);	
			}
		}
		remplirMain(joueur);
		
		return joueurPasPerdu;
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
		System.out.print("Veuillez effectuer votre choix d'action : nombre entre 0 et N (N étant un entier naturel)");
		
		choixAction = Integer.parseInt(s.nextLine());
		
		return choixAction;
	}
	
	private int executerAction(Couple<Integer, Integer> choixAction, ActionsJouables actions_jouables, Joueur joueur) throws Exception{
		Carte carteJouee = actions_jouables.get(choixAction.getC1()).get(choixAction.getC2()).c2;
		
		switch(choixAction.getC1()){
			case Joueur.Reculer : 				
				joueur.reculer(carteJouee.getContenu());
				defausse.ajouter(carteJouee);
				joueur.defausserUneCarte(carteJouee);
				break;
			case Joueur.Avancer : 
				joueur.avancer(carteJouee.getContenu());
				defausse.ajouter(carteJouee);
				joueur.defausserUneCarte(carteJouee);
				break;
			case Joueur.AttaqueDirecte : 
				// J'EN SUIS LA !!!
				// J'EN SUIS LA !!!
				// J'EN SUIS LA !!!
				// J'EN SUIS LA !!!
				// J'EN SUIS LA !!!
				// J'EN SUIS LA !!!
				break;
			case Joueur.AttaqueIndirecte : 
				break;
			case Joueur.Parade : 
				break;
			case Joueur.Fuite : 
				break;
			default: throw new Exception("Erreur lors de l'exécution de l'action");
		}
		
		return 0;
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