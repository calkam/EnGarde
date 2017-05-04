package Modele;

import Modele.Joueur.*;
import Modele.Plateau.*;
import Modele.Plateau.Figurine.*;
import Modele.Tas.*;

public class Jeu {
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Piste piste;
	private PlateauScore plateauScore;
	private Manche manche;

	public void init() throws Exception {
		plateauScore = new PlateauScore() ;
		piste = new Piste(new FigurineGauche(), new FigurineDroite()) ;
		joueur1 = new FabriqueJoueur (1, "Humain", "Joueur1", new Main(), piste).nouveauJoueur() ;
		joueur2 = new FabriqueJoueur (2, "Humain", "Joueur2", new Main(), piste).nouveauJoueur() ;
	}

	/**
	 * INITIALISATION MANCHE
	 */
	
	public void initialiserPremiereManche(){
		manche = new Manche(0);
		manche.initialiserJoueur(joueur1, joueur2);
	}
	
	public void nouvelleManche(){
		manche = new Manche(manche.getNumero()+1);
	}
	
	public void lancerLaManche(){
		manche.jouerManche(joueur1, joueur2);
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

	@Override
	public String toString() {
		String str = "Jeu";
		return str;
	}
}