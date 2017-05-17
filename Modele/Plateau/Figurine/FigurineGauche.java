package Modele.Plateau.Figurine;

import Modele.Visiteur;

public class FigurineGauche extends Figurine {
	
	public FigurineGauche(int position, float x, float y) {
		super(position, x, y);
	}
	
	public FigurineGauche(int i){
		this(i, 0, 0);
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}
}
