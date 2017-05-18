package Modele;

import Modele.Joueur.*;
import Modele.Plateau.*;
import Modele.Plateau.Figurine.*;
import Modele.Tas.*;

public class Jeu implements Visitable{
	
	public final static int VICTOIRE = 1; 
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Piste piste;
	private PlateauScore plateauScoreJ1;
	private PlateauScore plateauScoreJ2;
	private Manche manche;
	
	private long dernierChrono;

	public void init(String j1, String j2, String type1, String type2) throws Exception {
		Score scoreJ1 = new Score();
		Score scoreJ2 = new Score();
		plateauScoreJ1 = new PlateauScore(scoreJ2, PlateauScore.gauche) ;
		plateauScoreJ2 = new PlateauScore(scoreJ1, PlateauScore.droite) ;
		piste = new Piste(new FigurineGauche(1), new FigurineDroite(23)) ;
		joueur1 = new FabriqueJoueur (1, type1, j1, new Main(), piste).nouveauJoueur() ;
		joueur2 = new FabriqueJoueur (2, type2, j2, new Main(), piste).nouveauJoueur() ;
		joueur1.setScore(scoreJ1);
		joueur2.setScore(scoreJ2);
		this.dernierChrono = System.nanoTime();
	}
	
	public void lancerJeu(){
		try {
			initialiserPremiereManche();
			lancerLaManche();
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
		
		if(joueur1.getNbPoints() == VICTOIRE){
			gagne = true;
			affichageVictoire(joueur1.getNom(), joueur2.getNom());
		}
		if(joueur2.getNbPoints() == VICTOIRE){			
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
		manche.reinitialiserPiste();
		manche.commencerManche();
	}

	public void changerScore(Joueur joueur) {
		// TODO Auto-generated method stub
		joueur.setNbPoints(joueur.getNbPoints()+1);
		if(joueur.equals(joueur1)){
			plateauScoreJ2.getJetonsNumero(VICTOIRE-joueur.getNbPoints()).setVisible(false);
		}else{
			plateauScoreJ1.getJetonsNumero(VICTOIRE-joueur.getNbPoints()).setVisible(false);
		}
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
            plateauScoreJ1.accept(d);
            plateauScoreJ2.accept(d);
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

	public PlateauScore getPlateauScoreJ1() {
		return plateauScoreJ1;
	}

	public void setPlateauScoreJ1(PlateauScore plateauScore) {
		this.plateauScoreJ2 = plateauScore;
	}
	
	public PlateauScore getPlateauScoreJ2() {
		return plateauScoreJ2;
	}

	public void setPlateauScoreJ2(PlateauScore plateauScore) {
		this.plateauScoreJ2 = plateauScore;
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