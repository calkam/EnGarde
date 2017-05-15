package Modele.Plateau.Figurine;

import Modele.Visiteur;

public class FigurineGauche extends Figurine {
	
	public FigurineGauche(float x, float y, int position) {
		super(x, y, position);
	}
	
	public FigurineGauche(int i){
		this(0, 0, i);
	}

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}
}
