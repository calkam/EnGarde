package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionNeutre extends Action{

	private Carte carteDeplacement;
	private Carte carteAction=null;

	public ActionNeutre(int typeAction, int nbCartes, int positionArrivee, Carte carteDeplacement) {
		super(typeAction, nbCartes, positionArrivee);
		this.carteDeplacement = carteDeplacement;
	}

	public Carte getCarteDeplacement() {
		return carteDeplacement;
	}

	public void setCarteDeplacement(Carte carteDeplacement) {
		this.carteDeplacement = carteDeplacement;
	}
	
	public Carte getCarteAction(){
		return carteAction;
	}
	
	public void setCarteAction(Carte carteAction){
		return;
	}

	@Override
	public String toString() {
		String strTypeAction = "";
		
		switch(typeAction){
			case Joueur.ActionImpossible: strTypeAction += "ActionImpossible";break;
			case Joueur.Reculer: strTypeAction += "Reculer";break;
			case Joueur.Avancer: strTypeAction += "Avancer";break;
			case Joueur.AttaqueDirecte: strTypeAction += "AttaqueDirecte";break;
			case Joueur.AttaqueIndirecte: strTypeAction += "AttaqueIndirecte";break;
			case Joueur.Parade: strTypeAction += "Parade";break;
			case Joueur.Fuite: strTypeAction += "Fuite";break;
			default: strTypeAction += "Erreur";
		}
		
		String str = "ActionNeutre [ typeAction= " + strTypeAction + ", nbCartes= " + nbCartes 
				+ ", positionArrivee= " + positionArrivee + ", deplacement= " + carteDeplacement + " ]";
		
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((carteDeplacement == null) ? 0 : carteDeplacement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionNeutre other = (ActionNeutre) obj;
		if (carteDeplacement == null) {
			if (other.carteDeplacement != null)
				return false;
		} else if (!carteDeplacement.equals(other.carteDeplacement))
			return false;
		return true;
	}
	
}
