package Modele;

import java.util.ArrayList;

import Modele.Joueur.FigurineGauche;

public class Piste extends Rectangle {

	private FigurineGauche figurineGauche;
	private FigurineDroite figurineDroite;
	private ArrayList<Case> cases;
	
	public Piste(FigurineGauche figurineGauche, FigurineDroite figurineDroite) {
		super(0, 0);
		this.figurineGauche = figurineGauche ;
		this.figurineDroite = figurineDroite ;
		this.cases = new ArrayList<Case>(23);
	}
	
	public FigurineGauche getFigurineGauche() {
		return figurineGauche;
	}

	public void setFigurineGauche(FigurineGauche figurineGauche) {
		this.figurineGauche = figurineGauche ;
	}

	public FigurineDroite getFigurineDroite() {
		return figurineDroite;
	}

	public void setFigurineDroite(FigurineDroite figurineDroite) {
		this.figurineDroite = figurineDroite ;
	}

	public ArrayList<Case> getCases() {
		return cases;
	}
	public void setCases(ArrayList<Case> cases) {
		this.cases = cases;
	}
	
	public boolean estdansPiste (int position) {
		
		return position >= 0 && position < cases.size() ;
		
	}
	
	public boolean accepte(Visiteur visiteur) {
		return false;
		// TODO Auto-generated method stub
	}

}