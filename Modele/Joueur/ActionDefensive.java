package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionDefensive extends Action {
	
	public ActionDefensive(int typeAction, int nbCartes, int positionArrivee, Carte carteDeplacement, Carte carteDefense) throws Exception {
		super(typeAction, nbCartes, positionArrivee);
		if (typeAction == Joueur.Fuite)  setCarteDeplacement(carteDeplacement) ; 
		else setCarteAction(carteDefense) ;
	}
	
}
