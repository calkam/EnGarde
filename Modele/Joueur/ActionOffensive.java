package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionOffensive extends Action {
	
	public ActionOffensive(int typeAction, int nbCartes, int positionArrivee, Carte carteDeplacement, Carte carteAttaque) throws Exception {
		super(typeAction, nbCartes, positionArrivee);
		setCarteDeplacement(carteDeplacement) ; 
		setCarteAction(carteAttaque) ; 
	}
	
}
