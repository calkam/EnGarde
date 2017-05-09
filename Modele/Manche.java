package Modele;

import java.util.Random;

import Modele.Joueur.Joueur;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Manche {
	
	private int numero;
	private int nbTourRealise;
	private Tour tourEnCours;
	private Pioche pioche;
	private Defausse defausse;
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	public Manche(int numero, int nbTourRealise, Tour tour) {
		this.numero = numero;
		this.nbTourRealise = nbTourRealise;
		defausse = new Defausse();
		pioche = new Pioche();
		tourEnCours = tour;
	}
	
	public Manche(int numero){
		this(numero, 0, new Tour());
	}

	public void initialiserJoueur(Joueur j1, Joueur j2){
		joueur1 = j1;
		joueur2 = j2;
	}
	
	private void choisirPremier(Joueur joueurPremier, Joueur joueurSecond){
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		if(choix){
			joueurPremier = joueur1;
			joueurSecond = joueur2;
		}else{
			joueurPremier = joueur2;
			joueurSecond = joueur1;
		}
	}
	
	private boolean estPasFini(int resultat){
		return resultat == Tour.aucunJoueurPerdu;
	}
	
	public void jouerManche(Joueur joueur1, Joueur joueur2){
		Joueur joueurPremier = null;
		Joueur joueurSecond = null;
		int resultat;
		choisirPremier(joueurPremier, joueurSecond);
		tourEnCours.setPioche(pioche);
		tourEnCours.setJoueurPremier(joueurPremier);
		tourEnCours.setJoueurSecond(joueurSecond);
		do{
			resultat = tourEnCours.jouerTour();
			nbTourRealise++;
		}while(estPasFini(resultat));
		
		if(resultat == Tour.piocheVide){
		}
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
}