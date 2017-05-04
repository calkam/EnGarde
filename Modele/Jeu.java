package Modele;

import Modele.Joueur.*;

public class Jeu {

	private final static int nombreCarteMax = 5;
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Piste piste;
	private PlateauScore plateauScore;
	private Pioche pioche;
	private Defausse defausse;
	private Manche manche;

	public void init() {
		joueur1 = new Humain("Joueur1", new Figurine());
		joueur2 = new Humain("Joueur2", new Figurine());
		piste = new Piste(joueur1.getFigurine(), joueur2.getFigurine());
		pioche = new Pioche();
		defausse = new Defausse();
		completerMain(joueur1);
		completerMain(joueur2);
	}

	public void completerMain(Joueur j){
		int nbCarteMain = j.getMain().getNombreCarte();
		
		for(int i=nbCarteMain; i<nombreCarteMax; i++){
			j.ajouterCarteDansMain(pioche.piocher());
		}
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

	@Override
	public String toString() {
		String str = "Jeu";
		return str;
	}
	
	
	
}