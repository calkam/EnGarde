package Modele.Plateau;

import java.util.ArrayList;

import Modele.Jeu;
import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class PlateauScore extends Rectangle implements Visitable {
	
	public final static int droite = 0;
	public final static int gauche = 1;
	
	private ArrayList<Jeton> jetons;
	private int cote;
	private Score score;

<<<<<<< HEAD
	private Jeton jeton1;
	private Jeton jeton2;
	public PlateauScore(){
		super();
	}

	public PlateauScore(float x, float y, Jeton jeton1, Jeton jeton2, ArrayList<Case> casesJ1, ArrayList<Case> casesJ2, int scoreJ1, int scoreJ2) {
		super(x, y);
		this.jeton1 = jeton1;
		this.jeton2 = jeton2;
=======
	public PlateauScore(float x, float y, float largeur, float hauteur, Score score, int cote) {
		super(x, y, largeur, hauteur);
		this.cote = cote;
		this.score = score;
		initTableauJeton();
	}
	
	public PlateauScore(Score score, int cote){
		this(0, 0, Reglages.lis("PlateauScoreLargeur"), Reglages.lis("PlateauScoreHauteur"), score, cote);
	}
	
	public void initTableauJeton(){
		jetons = new ArrayList<Jeton>();
		int proportion = Jeu.VICTOIRE+1;
		for(int i=0; i<Jeu.VICTOIRE; i++){
			Jeton j = new Jeton(0, 0, 0);
			j.setCote(this.cote);
			j.setLargeur(Reglages.lis("JetonLargeur"));
			j.setHauteur(Reglages.lis("JetonHauteur"));
			j.setX(this.getLargeur()/2-j.getLargeur()/2);
			j.setY(((this.getHauteur()*i)/proportion)+(j.getHauteur()/2));
			jetons.add(j);
		}
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		boolean retour = v.visite(this);
		for(Jeton j : jetons){
			retour = retour || j.accept(v);
		}
		return retour;
	}

	public ArrayList<Jeton> getJetons() {
		return jetons;
	}

	public void setJetons(ArrayList<Jeton> jetons) {
		this.jetons = jetons;
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
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
	
}