package Modele;

import Modele.Joueur.*;
import Modele.Plateau.*;
import Modele.Tas.*;

public class Jeu implements Visitable{
	
	public static int VICTOIRE = 4;
	
	public final static int JOUEUR1GAGNE = 0;
	public final static int JOUEUR2GAGNE = 1;
	public final static int JOUEUR1PERDU = 2;
	public final static int JOUEUR2PERDU = 3;
	
	// CONSTANTES POSITION JOUEUR/FIGURINE
	
	private final static int PositionGauche = Joueur.DirectionDroite ;
	private final static int PositionDroite = Joueur.DirectionGauche ;
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Piste piste;
	private PlateauScore plateauScoreJ1;
	private PlateauScore plateauScoreJ2;
	private Manche manche;
	private int numero;
	
	private long dernierChrono;

	public void init(String j1, String j2, String type1, String type2) throws Exception {
		Score scoreJ1 = new Score();
		Score scoreJ2 = new Score();
		plateauScoreJ1 = new PlateauScore(scoreJ2, PlateauScore.gauche) ;
		plateauScoreJ2 = new PlateauScore(scoreJ1, PlateauScore.droite) ;
		piste = new Piste(new Figurine(PositionGauche,1), new Figurine(PositionDroite,23)) ;
		joueur1 = new FabriqueJoueur (PositionGauche, type1, j1, new Main(PositionGauche), piste).nouveauJoueur() ;
		joueur2 = new FabriqueJoueur (PositionDroite, type2, j2, new Main(PositionDroite), piste).nouveauJoueur() ;
		joueur1.setScore(scoreJ1);
		joueur2.setScore(scoreJ2);
		this.dernierChrono = System.nanoTime();
	}
	
	public void lancerJeu(){
		try {
			nouvelleManche();
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
	public Couple<Integer, Integer> gainPartie(){
		
		if(joueur1.getNbPoints() == VICTOIRE){
			return new Couple<>(JOUEUR1GAGNE, JOUEUR2PERDU);
		}
		if(joueur2.getNbPoints() == VICTOIRE){			
			return new Couple<>(JOUEUR2GAGNE, JOUEUR1PERDU); 
		}
		return null;
	}
	
	public void affichageVictoire(String nomJoueurVictorieux, String nomJoueurPerdant){
		
		piste.setMessageInMessageBox(nomJoueurVictorieux + " a triomphé de son adversaire ! Gloire à " + nomJoueurVictorieux +"! "+ nomJoueurPerdant + " est une victime");
	
		System.out.println("Gloire à " + nomJoueurVictorieux);
		System.out.println(nomJoueurPerdant + " est une victime");
	}

	/**
	 * INITIALISATION MANCHE
	 */
	
	public void nouvelleManche(){
		//joueur1.initHisto();
		//joueur2.initHisto();
		manche = new Manche(++numero, joueur1, joueur2, piste.getMessageBox());
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
	 * @throws Exception 
	 */
	
	@Override
	public boolean accept(Visiteur d) throws Exception {
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