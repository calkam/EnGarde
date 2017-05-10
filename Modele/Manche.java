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
	
	private Couple<Joueur, Joueur> choisirPremier(){
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		Couple<Joueur, Joueur> c;
		
		if(choix){
			c = new Couple<>(joueur1, joueur2);
		}else{
			c = new Couple<>(joueur2, joueur1);
		}
		
		return c;
	}
	
	private boolean estPasFini(int resultat){
		return resultat == Tour.aucunJoueurPerdu;
	}
	
	public void jouerManche() throws Exception{
		Couple<Joueur, Joueur> tmp;
		int resultat;
		tmp = choisirPremier();
		tourEnCours.setPioche(pioche);
		tourEnCours.setDefausse(defausse);
		tourEnCours.setJoueurPremier(tmp.getC1());
		tourEnCours.setJoueurSecond(tmp.getC2());
		tourEnCours.remplirMain(tourEnCours.getJoueurPremier());
		tourEnCours.remplirMain(tourEnCours.getJoueurSecond());
		do{
			System.out.println(joueur1.toString());
			System.out.println(joueur2.toString());
			System.out.println(joueur1.getPiste().toString());
			resultat = tourEnCours.jouerTour();
			nbTourRealise++;
		}while(estPasFini(resultat));
		
		if(resultat == Tour.piocheVide){
		}
	}
	
	/**
	 * 
	 * GETTER/SETTER
	 */
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNbTourRealise() {
		return nbTourRealise;
	}

	public void setNbTourRealise(int nbTourRealise) {
		this.nbTourRealise = nbTourRealise;
	}

	public Tour getTourEnCours() {
		return tourEnCours;
	}

	public void setTourEnCours(Tour tourEnCours) {
		this.tourEnCours = tourEnCours;
	}

	public Pioche getPioche() {
		return pioche;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	public Defausse getDefausse() {
		return defausse;
	}

	public void setDefausse(Defausse defausse) {
		this.defausse = defausse;
	}

	public Joueur getJoueur1() {
		return joueur1;
	}

	public void setJoueur1(Joueur joueur1) {
		this.joueur1 = joueur1;
	}

	public Joueur getJoueur2() {
		return joueur2;
	}

	public void setJoueur2(Joueur joueur2) {
		this.joueur2 = joueur2;
	}
	
	
	
}