package Modele.Joueur ;

import Modele.Couple;

public class CartesDefaussees extends Couple <String, Integer> {
	
	public CartesDefaussees(String action, int position) {
		
		super(action, position) ;
	}

	@Override
	public String toString() {
		return "[" + getC1() + " : (case en surbrillance : " + getC2() + ")]";
	}
	

}
