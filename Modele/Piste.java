package Modele;

import java.util.ArrayList;

public class Piste extends Rectangle {

	private int figurine1;
	private int figurine2;
	private ArrayList<Case> cases;
	
	public Piste(int figurine1, int figurine2) {
		this.figurine1 = figurine1;
		this.figurine2 = figurine2;
		this.cases = new ArrayList<Case>(23);
	}
	
	public int getFigurineJoueur(Joueur j) {
		
		
		
		return figurine1;
	}
	
	public int getFigurine2() {
		return figurine2;
	}
	public void setFigurine2(int figurine2) {
		this.figurine2 = figurine2;
	}
	public ArrayList<Case> getCases() {
		return cases;
	}
	public void setCases(ArrayList<Case> cases) {
		this.cases = cases;
	}

}