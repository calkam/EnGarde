package Modele.Plateau;

import java.util.ArrayList;

import Modele.Jeu;
import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class PlateauScore extends Rectangle implements Visitable {
	
	// CONSTANTES
	// POSITION
	
	public final static int droite = 0;
	public final static int gauche = 1;
	
	// ATTRIBUTS
	
	private ArrayList<Jeton> jetons;
	private int cote;
	private Score score;
	
	// CONSTRUCTEURS
	
	public PlateauScore(float x, float y, float largeur, float hauteur, Score score, int cote) {
		super(x, y, largeur, hauteur);
		this.cote = cote;
		this.score = score;
		initTableauJeton(score);
	}
	
	public PlateauScore(Score score, int cote){
		this(0, 0, Reglages.lis("PlateauScoreLargeur"), Reglages.lis("PlateauScoreHauteur"), score, cote);
	}
	
	private void initTableauJeton(Score score2){
		
		jetons = new ArrayList<Jeton>();
		int proportion = Jeu.VICTOIRE+1;
		int tmp = Jeu.VICTOIRE- score2.getNbPoints(); 
		
		for(int i=0; i< tmp; i++){
			Jeton j = new Jeton(0, 0, 0);
			j.setCote(this.cote);
			j.setLargeur(Reglages.lis("JetonLargeur"));
			j.setHauteur(Reglages.lis("JetonHauteur"));
			j.setX(this.getLargeur()/2-j.getLargeur()/2);
			j.setY(((this.getHauteur()*i)/proportion)+(j.getHauteur()/2));
			jetons.add(j);
		}
	}
	
	/**
	 * GETTER/SETTER
	 **/

	public ArrayList<Jeton> getJetons() {
		return jetons;
	}

	public void setJetons(ArrayList<Jeton> jetons) {
		this.jetons = jetons;
	}
	
	public int getCote() {
		return cote;
	}

	public void setCote(int cote) {
		this.cote = cote;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Jeton getJetonsNumero(int num) {
		return jetons.get(num);
	}
	
	/**
	 * VUE
	 **/
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		boolean retour = v.visite(this);
		for(Jeton j : jetons){
			retour = retour || j.accept(v);
		}
		return retour;
	}
	
}