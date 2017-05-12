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
	
	public Manche(int numero, Joueur joueur1, Joueur joueur2){
		this(numero, 0, new Tour());
		initialiserJoueur(joueur1, joueur2);
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
	
	private void joueurAGagne(Joueur joueur){
		System.out.println(joueur.getNom() + " a gagné la manche !");
		joueur.setScore(joueur.getScore()+1);
	}
	
	public void afficherScore(){
		System.out.println("\nScore : " + joueur1.getNom() + " " + joueur1.getScore() + " - " + joueur2.getScore() + " " + joueur2.getNom());
	}
	
	private int calculerNormeEntreDeuxPositions(int position1, int position2){
		return Math.abs(position1 - position2);
	}
	
	public void reinitialiserPiste(){
		joueur1.reinitialiserPositionFigurine();
		joueur2.reinitialiserPositionFigurine();
	}
	
	public void jouerManche() throws Exception{
		int resultat;
		Couple<Joueur, Joueur> tmp;
		tmp = choisirPremier();
		tourEnCours.setPioche(pioche);
		tourEnCours.setDefausse(defausse);
		tourEnCours.setJoueurPremier(tmp.getC1());
		tourEnCours.setJoueurSecond(tmp.getC2());
		tourEnCours.remplirMain(tourEnCours.getJoueurPremier());
		tourEnCours.remplirMain(tourEnCours.getJoueurSecond());
		
		System.out.println(joueur1.toString());
		System.out.println(joueur2.toString());
		System.out.println(joueur1.getPiste().toString());
		System.out.println(pioche);
		
		do{
			resultat = tourEnCours.jouerTour();
			nbTourRealise++;
		}while(estPasFini(resultat));
		
		System.out.println("/*************************************************************************************************************/");
		tourEnCours.afficherPiste(positionF1, positionF2);		
		
		if(resultat == Tour.joueurPremierPerdu){			
			joueurAGagne(tourEnCours.getJoueurSecond());
		}else if(resultat == Tour.joueurSecondPerdu){
			joueurAGagne(tourEnCours.getJoueurPremier());
		}else if(resultat == Tour.piocheVide){
			System.out.println("La pioche est vide :");
			
			int distanceEntreFigurineJ1EtFigurineJ2 = calculerNormeEntreDeuxPositions(joueur1.getPositionFigurine(), joueur2.getPositionFigurine());
			
			if(distanceEntreFigurineJ1EtFigurineJ2 < 6){
				int nbCartesDistanceJ1 = joueur1.getMain().getNombreCarteGroupe(distanceEntreFigurineJ1EtFigurineJ2);
				int nbCartesDistanceJ2 = joueur2.getMain().getNombreCarteGroupe(distanceEntreFigurineJ1EtFigurineJ2);
				
				if(nbCartesDistanceJ1 > nbCartesDistanceJ2){
					System.out.println(joueur1.getNom() + " ayant plus de cartes pour attaquer directectement son adversaire...");
					joueurAGagne(joueur1);
				}else if(nbCartesDistanceJ1 < nbCartesDistanceJ2){
					System.out.println(joueur2.getNom() + " ayant plus de cartes pour attaquer directectement son adversaire...");
					joueurAGagne(joueur2);
				}
			}else{
				int distanceEntreCaseMedianeEtFigurineJ1 = calculerNormeEntreDeuxPositions(12, joueur1.getPositionFigurine());
				int distanceEntreCaseMedianeEtFigurineJ2 = calculerNormeEntreDeuxPositions(12, joueur2.getPositionFigurine());
				
				if(distanceEntreCaseMedianeEtFigurineJ1 > distanceEntreCaseMedianeEtFigurineJ2){
					System.out.println(joueur2.getNom() + " étant plus proche de la case médiane...");
					joueurAGagne(joueur2);
				}else if(distanceEntreCaseMedianeEtFigurineJ1 < distanceEntreCaseMedianeEtFigurineJ2){
					System.out.println(joueur1.getNom() + " étant plus proche de la case médiane...");
					joueurAGagne(joueur1);
				}else{
					System.out.println("Manche nulle !");
				}
			}
		}
		
		afficherScore();
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