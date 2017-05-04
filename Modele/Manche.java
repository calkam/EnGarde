package Modele;

import Modele.Joueur.Joueur;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Manche {
	
	private final static int nombreCarteMax = 5;
	
	private int numero;
	private int nbTourRealise;
	private Tour tourEnCours;
	private Pioche pioche;
	private Defausse defausse;
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	public Manche(int numero, int nbTourRealise, Tour tourEnCours) {
		this.numero = numero;
		this.nbTourRealise = nbTourRealise;
		this.tourEnCours = tourEnCours;
		defausse = new Defausse();
		pioche = new Pioche();
	}
	
	public Manche(int numero, Tour tour) {
		this(numero, 0, tour);
	}
	
	public Manche(int numero){
		this(numero, 0, new Tour());
	}

	public void initialiserJoueur(Joueur j1, Joueur j2){
		joueur1 = j1;
		joueur2 = j2;
		remplirMain(joueur1);
		remplirMain(joueur2);
	}
	
	public void remplirMain(Joueur j){
		int nbCarteMain = j.getMain().getNombreCarte();
		
		for(int i=nbCarteMain; i<nombreCarteMax; i++){
			j.ajouterCarteDansMain(pioche.piocher());
		}
	}
	
	public void jouerManche(Joueur joueur1, Joueur joueur2){
		Joueur joueurAttaquant = joueur1;
		Joueur joueurDefenseur = joueur2;
		while(!pioche.estVide() && joueurAttaquant.peutFaireAction()){
			tourEnCours.initialiserTour(joueurAttaquant, joueurDefenseur);
			tourEnCours.jouerTour();
			remplirMain(joueurAttaquant);
			if(joueurAttaquant.equals(joueur1)){
				joueurAttaquant = joueur2;
				joueurDefenseur = joueur1;
			}else{
				joueurAttaquant = joueur1;
				joueurDefenseur = joueur2;
			}
		}
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
}