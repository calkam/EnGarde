package Modele;

import Controleur.ControleurChoixPartie;

import Modele.Joueur.*;
import Modele.Plateau.*;
import Modele.Tas.*;

public class Jeu implements Visitable{
	
<<<<<<< HEAD
	private final static int VICTOIRE = 3; 
	
	// CONSTANTES POSITION JOUEUR/FIGURINE
	
	private final static int PositionGauche = Joueur.DROITE ;
	private final static int PositionDroite = Joueur.GAUCHE ;
=======
	public final static int VICTOIRE = 3; 
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Piste piste;
	private PlateauScore plateauScoreJ1;
	private PlateauScore plateauScoreJ2;
	private Manche manche;
	
	private long dernierChrono;

<<<<<<< HEAD
	public void init() throws Exception {
		plateauScore = new PlateauScore() ;
		piste = new Piste(new Figurine(PositionGauche, 0,0,1), new Figurine(PositionDroite, 0,0,23)) ;
		joueur1 = new FabriqueJoueur (PositionGauche, "Humain", "Kaiba (Joueur 1)", new Main(), piste).nouveauJoueur() ;
		joueur2 = new FabriqueJoueur (PositionDroite, "IA", "Moyen", new Main(), piste).nouveauJoueur() ;
		joueur1.setScore(0);
		joueur2.setScore(0);
=======
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
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
	}
	
	public void lancerJeu(){
		try {
			initialiserPremiereManche();
			lancerLaManche();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (joueur1 instanceof Humain) ((Humain) joueur1).scanner.close();
		if (joueur2 instanceof Humain) ((Humain) joueur2).scanner.close();
		
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
		
		Historique histo = null ;
		
		if ((joueur1 instanceof Humain && !(joueur2 instanceof Humain)) || (!(joueur1 instanceof Humain) && joueur2 instanceof Humain)) {
			
			histo = new Historique () ;
			
		}
		
		manche = new Manche(0, joueur1, joueur2, piste, histo);
	}
	
	public void nouvelleManche(){
<<<<<<< HEAD
		
		Historique histo = null ;
		
		if ((joueur1 instanceof Humain && !(joueur2 instanceof Humain)) || (!(joueur1 instanceof Humain) && joueur2 instanceof Humain)) {
			
			histo = new Historique () ;
			
		}
		
		joueur1.viderMain();
		joueur2.viderMain();
		manche = new Manche(manche.getNumero()+1, joueur1, joueur2, piste, histo);
=======
		manche = new Manche(manche.getNumero()+1, joueur1, joueur2);
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
	}
	
	public void lancerLaManche() throws Exception{
		manche.reinitialiserPiste();
		manche.commencerManche();
	}

	public void changerScore(Joueur joueur) {
		// TODO Auto-generated method stub
		joueur.setNbPoints(joueur.getNbPoints()+1);
		System.out.println(joueur);
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