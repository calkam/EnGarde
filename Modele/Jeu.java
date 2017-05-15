package Modele;

import Modele.Joueur.*;
import Modele.Plateau.*;
import Modele.Plateau.Figurine.*;
import Modele.Tas.*;

public class Jeu implements Visitable{
	
	private final static int VICTOIRE = 5; 
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Piste piste;
	private PlateauScore plateauScore;
	private Manche manche;
	
	private long dernierChrono;

	public void init() throws Exception {
		plateauScore = new PlateauScore() ;
		piste = new Piste(new FigurineGauche(0,0,1), new FigurineDroite(0,0,23)) ;
		joueur1 = new FabriqueJoueur (1, "Humain", "Kaiba (Joueur 1)", new Main(), piste).nouveauJoueur() ;
		joueur2 = new FabriqueJoueur (2, "Humain", "Yugi (Joueur 2)", new Main(), piste).nouveauJoueur() ;
		joueur1.setScore(0);
		joueur2.setScore(0);
		initialiserPremiereManche();
		
		this.dernierChrono = System.nanoTime();
	}
	
	public void lancerJeu(){
		try {
			lancerLaManche();
			while(!gainPartie()){
				System.out.println("\n/*************************************************************************************************************/");
				nouvelleManche();
				lancerLaManche();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * RAFFRAICHISSEMENT
	 */
	
	public boolean rafraichit(long nouveau) {
        final long temps = nouveau - dernierChrono;
        dernierChrono = nouveau;
        
        piste.rafraichit(temps);
        return false;
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
		manche = new Manche(0, joueur1, joueur2);
	}
	
	public void nouvelleManche(){
		manche = new Manche(manche.getNumero()+1, joueur1, joueur2);
	}
	
	public void lancerLaManche() throws Exception{
		manche.jouerManche();
		manche.reinitialiserPiste();
	}

	/**
	 * 
	 * VISITABLE
	 */
	
	@Override
	public boolean accept(Visiteur d) {
	   if(piste != null){
            piste.accept(d);
            manche.accept(d);
            return false;
        }else{
            return true;
        }
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
		String str = "Jeu" + joueur1;
		return str;
	}


}