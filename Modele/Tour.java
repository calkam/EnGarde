package Modele;

import Modele.Joueur.Joueur;
import Modele.Tas.Pioche;

public class Tour{
	
	private final static int nombreCarteMax = 5;
	
	private final static boolean joueurPerdu = false;
	private final static boolean joueurPasPerdu = true;
	
	public final static int joueurPremierPerdu = 0;
	public final static int joueurSecondPerdu = 1;
	public final static int aucunJoueurPerdu = 2;
	public final static int piocheVide = 3;
	
	private final static int pasAttaque = 0;
	private final static int attaqueDirect = 1;
	private final static int attaqueIndirect = 2;
	
	private Joueur joueurPremier;
	private Joueur joueurSecond;
	private Pioche pioche;
	private int estAttaque;
	
	public Tour(){}
	
	public Tour(Joueur m_joueurPremier, Joueur m_joueurSecond, Pioche pioche){
		this.joueurPremier = m_joueurPremier;
		this.joueurSecond = m_joueurSecond;
		this.pioche = new Pioche();
		this.estAttaque = 0;
		remplirMain(joueurPremier);
		remplirMain(joueurSecond);
	}
	
	public int jouerTour(){
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
	
	public boolean jouerTourJoueur(Joueur joueur){
		String choixAction;	
		
		joueur.getActionPossible(estAttaque);
		choixAction = selectionnerAction();
		
		if(choixAction == ""){
			return joueurPerdu;
		}else{
			estAttaque = executerAction(choixAction);			
		}
		
		if(choixAction == "Parade"){
			estAttaque = pasAttaque;
			joueur.getActionPossible(estAttaque);
			choixAction = selectionnerAction();
			
			if(choixAction == ""){
				return joueurPerdu;
			}else{
				estAttaque = executerAction(choixAction);			
			}
		}
		remplirMain(joueur);
		
		return joueurPasPerdu;
	}
	
	private void remplirMain(Joueur j){		
		int nbCarteMain = j.getMain().getNombreCarte();
		
		int i=nbCarteMain;
		
		while(i < nombreCarteMax && !pioche.estVide()){
			j.ajouterCarteDansMain(pioche.piocher());
		}
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
	
}