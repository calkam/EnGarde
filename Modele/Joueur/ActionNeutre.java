package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionNeutre extends Action {
	
	@Override
	public Carte getCarteAction() {
		throw new UnsupportedOperationException("Modele.Joueur.ActionNeutre.getCarteAction : Une ActionNeutre n'a pas d'attribut carteAction") ;
	}
	
	@Override
	public void setCarteAction(Carte carteAction) {
		throw new UnsupportedOperationException("Modele.Joueur.ActionNeutre.getCarteAction : Une ActionNeutre n'a pas d'attribut carteAction") ;
	}

	public ActionNeutre(int typeAction, int nbCartes, int positionArrivee, Carte carteDeplacement) throws Exception {
		super(typeAction, nbCartes, positionArrivee);
		setCarteDeplacement(carteDeplacement) ; 
	}

	public ActionNeutre(int typeAction) {
		super(typeAction);
	}
	
}
