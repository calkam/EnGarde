package Modele;

import java.util.Random;

import Modele.Joueur.Joueur;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;
import Modele.Tas.Tas;

public class Manche implements Visitable{
	
	public final static int JOUEUR1GAGNE = 0;
	public final static int JOUEUR2GAGNE = 1;
	public final static int MATCHNULLE = 2;
	
	private int numero;
	private int nbTourRealise;
	private Tour tourEnCours;
	private Pioche pioche;
	private Defausse defausse;
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	public Manche(int numero, int nbTourRealise, Joueur j1, Joueur j2, Tour tour) {
		this.numero = numero;
		this.nbTourRealise = nbTourRealise;
		defausse = new Defausse();
		pioche = new Pioche();
		initialiserJoueur(j1, j2);
		tourEnCours = tour;
		initialiserTour();
	}
	
	public Manche(int numero, Joueur joueur1, Joueur joueur2){
		this(numero, 0, joueur1, joueur2, new Tour());
	}

	public void initialiserTour(){
		Couple<Joueur, Joueur> tmp;
		tmp = choisirPremier();
		tourEnCours.setPioche(pioche);
		tourEnCours.setDefausse(defausse);
		tourEnCours.setJoueurPremier(tmp.getC1());
		tourEnCours.setJoueurSecond(tmp.getC2());
		tourEnCours.getJoueurPremier().viderLaMain();
		tourEnCours.getJoueurSecond().viderLaMain();
		tourEnCours.remplirMain(tourEnCours.getJoueurPremier());
		tourEnCours.remplirMain(tourEnCours.getJoueurSecond());
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return tourEnCours.accept(v);
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
	
	private void joueurAGagne(Joueur joueur){
		System.out.println(joueur.getNom() + " a gagné la manche !");
	}
	
	public void afficherScore(){
		System.out.println("\nScore : " + joueur1.getNom() + " " + joueur1.getNbPoints() + " - " + joueur2.getNbPoints() + " " + joueur2.getNom());
	}
	
	private int calculerNormeEntreDeuxPositions(int position1, int position2){
		return Math.abs(position1 - position2);
	}
	
	public void reinitialiserPiste(){
		joueur1.reinitialiserPositionFigurine();
		joueur2.reinitialiserPositionFigurine();
	}
	
	public void commencerManche(){
		try {
			tourEnCours.getJoueurSecond().getMain().setVisible(false);
			tourEnCours.jouerTour();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int finDeManche(int resultat) throws Exception{
		
		if(resultat == Tour.joueurPremierPerdu){			
			joueurAGagne(tourEnCours.getJoueurSecond());
			if(tourEnCours.getJoueurSecond().equals(joueur1)){
				return JOUEUR1GAGNE;
			}else{
				return JOUEUR2GAGNE;
			}
		}else if(resultat == Tour.joueurSecondPerdu){
			joueurAGagne(tourEnCours.getJoueurPremier());
			if(tourEnCours.getJoueurSecond().equals(joueur1)){
				return JOUEUR1GAGNE;
			}else{
				return JOUEUR2GAGNE;
			}
		}else if(resultat == Tour.piocheVide){
			System.out.println("La pioche est vide :");
			
			int distanceEntreFigurineJ1EtFigurineJ2 = calculerNormeEntreDeuxPositions(joueur1.getPositionFigurine(), joueur2.getPositionFigurine());
			
			if(distanceEntreFigurineJ1EtFigurineJ2 < Tas.carteValeurMax){
				int nbCartesDistanceJ1 = joueur1.getMain().getNombreCarteGroupe(distanceEntreFigurineJ1EtFigurineJ2);
				int nbCartesDistanceJ2 = joueur2.getMain().getNombreCarteGroupe(distanceEntreFigurineJ1EtFigurineJ2);
				
				if(nbCartesDistanceJ1 > nbCartesDistanceJ2){
					System.out.println(joueur1.getNom() + " ayant plus de cartes pour attaquer directectement son adversaire...");
					joueurAGagne(joueur1);
					return JOUEUR1GAGNE;
				}else if(nbCartesDistanceJ1 < nbCartesDistanceJ2){
					System.out.println(joueur2.getNom() + " ayant plus de cartes pour attaquer directectement son adversaire...");
					joueurAGagne(joueur2);
					return JOUEUR2GAGNE;
				}
			}else{
				int distanceEntreCaseMedianeEtFigurineJ1 = calculerNormeEntreDeuxPositions(12, joueur1.getPositionFigurine());
				int distanceEntreCaseMedianeEtFigurineJ2 = calculerNormeEntreDeuxPositions(12, joueur2.getPositionFigurine());
				
				if(distanceEntreCaseMedianeEtFigurineJ1 > distanceEntreCaseMedianeEtFigurineJ2){
					System.out.println(joueur2.getNom() + " étant plus proche de la case médiane...");
					joueurAGagne(joueur2);
					return JOUEUR1GAGNE;
				}else if(distanceEntreCaseMedianeEtFigurineJ1 < distanceEntreCaseMedianeEtFigurineJ2){
					System.out.println(joueur1.getNom() + " étant plus proche de la case médiane...");
					joueurAGagne(joueur1);
					return JOUEUR2GAGNE;
				}else{
					System.out.println("Manche nulle !");
					return MATCHNULLE;
				}
			}
		}
		return MATCHNULLE;
	}
	
	/*public void jouerManche() throws Exception{
		int resultat;
		
		do{
			resultat = tourEnCours.jouerTour();
			nbTourRealise++;
		}while(estPasFini(resultat));
		
		if(resultat == Tour.joueurPremierPerdu){			
			joueurAGagne(tourEnCours.getJoueurSecond());
		}else if(resultat == Tour.joueurSecondPerdu){
			joueurAGagne(tourEnCours.getJoueurPremier());
		}else if(resultat == Tour.piocheVide){
			System.out.println("La pioche est vide :");
			
			int distanceEntreFigurineJ1EtFigurineJ2 = calculerNormeEntreDeuxPositions(joueur1.getPositionFigurine(), joueur2.getPositionFigurine());
			
			if(distanceEntreFigurineJ1EtFigurineJ2 < Tas.carteValeurMax){
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
	}*/
	
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