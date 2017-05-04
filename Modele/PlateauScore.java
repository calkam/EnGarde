package Modele;

import java.util.ArrayList;

public class PlateauScore extends Rectangle {

	private Jeton jeton1;
	private Jeton jeton2;
	private ArrayList<Case> casesJ1;
	private ArrayList<Case> casesJ2;
	private int scoreJ1;
	private int scoreJ2;
	
	public PlateauScore(){
		super();
	}

	public PlateauScore(float x, float y, Jeton jeton1, Jeton jeton2, ArrayList<Case> casesJ1, ArrayList<Case> casesJ2, int scoreJ1, int scoreJ2) {
		super(x, y);
		this.jeton1 = jeton1;
		this.jeton2 = jeton2;
		this.casesJ1 = casesJ1;
		this.casesJ2 = casesJ2;
		this.scoreJ1 = scoreJ1;
		this.scoreJ2 = scoreJ2;
	}
	
	PlateauScore(float x, float y) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
	}

	public Jeton getJeton1() {
		return this.jeton1;
	}

	public void setJeton1(Jeton jeton1) {
		this.jeton1 = jeton1;
	}

	public Jeton getJeton2() {
		return this.jeton2;
	}

	public void setJeton2(Jeton jeton2) {
		this.jeton2 = jeton2;
	}

}