package Modele;

import Modele.Joueur.*;
import Modele.Plateau.*;
import Modele.Tas.*;

public class Jeu {
	
	private final static int VICTOIRE = 3; 
	
	// CONSTANTES POSITION JOUEUR/FIGURINE
	
	private final static int PositionGauche = Joueur.DROITE ;
	private final static int PositionDroite = Joueur.GAUCHE ;
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Piste piste;
	private PlateauScore plateauScore;
	private Manche manche;

	public void init() throws Exception {
		plateauScore = new PlateauScore() ;
		piste = new Piste(new Figurine(PositionGauche, 0,0,1), new Figurine(PositionDroite, 0,0,23)) ;
		joueur1 = new FabriqueJoueur (PositionGauche, "Humain", "Kaiba (Joueur 1)", new Main(), piste).nouveauJoueur() ;
		joueur2 = new FabriqueJoueur (PositionDroite, "IA", "Moyen", new Main(), piste).nouveauJoueur() ;
		joueur1.setScore(0);
		joueur2.setScore(0);
	}
	
	public void lancerJeu(){
		try {
			initialiserPremiereManche();
			lancerLaManche();
			while(!gainPartie()){
				System.out.println("\n/*************************************************************************************************************/");
				nouvelleManche();
				lancerLaManche();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (joueur1 instanceof Humain) ((Humain) joueur1).scanner.close();
		if (joueur2 instanceof Humain) ((Humain) joueur2).scanner.close();
		
	}
	
	/**
	 * FIN DE PARTIE
	 */	
	public boolean gainPartie(){
		boolean gagne = false;
		
		if(joueur1.getScore() == VICTOIRE){
			gagne = true;
			affichageVictoire(joueur1.getNom(), joueur2.getNom());
		}
		if(joueur2.getScore() == VICTOIRE){			
			gagne = true;
			affichageVictoire(joueur2.getNom(), joueur1.getNom());
		}
		
		return gagne;
	}
	
	public void affichageVictoire(String nomJoueurVictorieux, String nomJoueurPerdant){
		System.out.println(nomJoueurVictorieux + " a triomphé de son adversaire !");
		System.out.println("Gloire à " + nomJoueurVictorieux);
		System.out.println(nomJoueurPerdant + " est une victime");
	}

	/**
	 * INITIALISATION MANCHE
	 */
	
	public void initialiserPremiereManche(){
		
		Historique histo = null ;
		
		if ((joueur1 instanceof Humain && !(joueur2 instanceof Humain)) || (!(joueur1 instanceof Humain) && joueur2 instanceof Humain)) {
			
			histo = new Historique () ;
			
		}
		
		manche = new Manche(0, joueur1, joueur2, piste, histo);
	}
	
	public void nouvelleManche(){
		
		Historique histo = null ;
		
		if ((joueur1 instanceof Humain && !(joueur2 instanceof Humain)) || (!(joueur1 instanceof Humain) && joueur2 instanceof Humain)) {
			
			histo = new Historique () ;
			
		}
		
		joueur1.viderMain();
		joueur2.viderMain();
		manche = new Manche(manche.getNumero()+1, joueur1, joueur2, piste, histo);
	}
	
	public void lancerLaManche() throws Exception{
		manche.jouerManche();
		manche.reinitialiserPiste();
	}

	/**
	 * GETTER / SETTER / TOSTRING
	 */
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

	public Piste getPiste() {
		return piste;
	}

	public void setPiste(Piste piste) {
		this.piste = piste;
	}

	public PlateauScore getPlateauScore() {
		return plateauScore;
	}

	public void setPlateauScore(PlateauScore plateauScore) {
		this.plateauScore = plateauScore;
	}

	public Manche getManche() {
		return manche;
	}

	public void setManche(Manche manche) {
		this.manche = manche;
	}

	@Override
	public String toString() {
		String str = "Jeu";
		return str;
	}
}