package Modele;

import java.util.ArrayList;

import Modele.Joueur.Joueur;

public class Piste extends Rectangle {

	private int figurine1;
	private int figurine2;
	private ArrayList<Case> cases;
	
	public Piste(int figurine1, int figurine2) {
		super(0, 0);
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
	
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

}