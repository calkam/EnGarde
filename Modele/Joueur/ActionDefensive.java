package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionDefensive extends Action {

	private Carte carteDeplacement;
	private Carte carteDefense;
	
	public ActionDefensive(int typeAction, int nbCartes, int positionArrivee, Carte carteDeplacement, Carte carteDefense) {
		super(typeAction, nbCartes, positionArrivee);
		this.carteDeplacement = carteDeplacement;
		this.carteDefense = carteDefense;
	}
	
	public Carte getCarteDeplacement() {
		return carteDeplacement;
	}
	public void setCarteDeplacement(Carte carteDeplacement) {
		this.carteDeplacement = carteDeplacement;
	}
	public Carte getCarteAction() {
		return carteDefense;
	}
	public void setCarteAction(Carte carteDefense) {
		this.carteDefense = carteDefense;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((carteDefense == null) ? 0 : carteDefense.hashCode());
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
		ActionDefensive other = (ActionDefensive) obj;
		if (carteDefense == null) {
			if (other.carteDefense != null)
				return false;
		} else if (!carteDefense.equals(other.carteDefense))
			return false;
		if (carteDeplacement == null) {
			if (other.carteDeplacement != null)
				return false;
		} else if (!carteDeplacement.equals(other.carteDeplacement))
			return false;
		return true;
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
		
		String str = "ActionDefensive [ typeAction= " + strTypeAction + ", nbCartes= " + nbCartes 
				+ ", positionArrivee= " + positionArrivee + ", deplacement= " + carteDeplacement + ", defense= " + carteDefense + " ]";
		
		return str;
	}

	
	
}
