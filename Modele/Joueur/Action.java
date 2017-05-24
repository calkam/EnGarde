package Modele.Joueur;

import Modele.Tas.Carte;

public abstract class Action {
	
	// CONSTANTES CLASSES ACTION

	public static final int ActionOffensive = 0 ;
	public static final int ActionNeutre = 1 ;
	public static final int ActionDefensive = 2 ;
	
	// ATTRIBUTS

	protected int typeAction;
	protected int nbCartes;
	protected int positionArrivee;
	protected Carte carteDeplacement;
	protected Carte carteAction;

	public Action(int typeAction, int nbCartes, int positionArrivee) {
		this.typeAction = typeAction;
		this.nbCartes = nbCartes;
		this.positionArrivee = positionArrivee;
	}
	
	public Action(int typeAction) {
		super();
		this.typeAction = typeAction;
	}
	public Carte getCarteDeplacement() {
		return carteDeplacement;
	}
	public void setCarteDeplacement(Carte carteDeplacement) {
		this.carteDeplacement = carteDeplacement;
	}
	public Carte getCarteAction() {
		return carteAction;
	}
	public void setCarteAction(Carte carteAction) {
		this.carteAction = carteAction;
	}
	public int getTypeAction() {
		return typeAction;
	}
	public void setTypeAction(int typeAction) {
		this.typeAction = typeAction;
	}
	public int getNbCartes() {
		return nbCartes;
	}
	public void setNbCartes(int nbCartes) {
		this.nbCartes = nbCartes;
	}
	public int getPositionArrivee() {
		return positionArrivee;
	}
	public void setPositionArrivee(int positionArrivee) {
		this.positionArrivee = positionArrivee;
	}
	
	public boolean estActionOffensive(){
		return getTypeAction() == Joueur.AttaqueDirecte || getTypeAction() == Joueur.AttaqueIndirecte; 
	}
	
	public boolean estActionNeutre(){
		return getTypeAction() == Joueur.Reculer || getTypeAction() == Joueur.Avancer; 
	}
	
	public boolean estActionDefensive(){
		return getTypeAction() == Joueur.Fuite || getTypeAction() == Joueur.Parade ; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carteAction == null) ? 0 : carteAction.hashCode());
		result = prime * result + ((carteDeplacement == null) ? 0 : carteDeplacement.hashCode());
		result = prime * result + nbCartes;
		result = prime * result + positionArrivee;
		result = prime * result + typeAction;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (carteAction == null) {
			if (other.carteAction != null)
				return false;
		} else if (!carteAction.equals(other.carteAction))
			return false;
		if (carteDeplacement == null) {
			if (other.carteDeplacement != null)
				return false;
		} else if (!carteDeplacement.equals(other.carteDeplacement))
			return false;
		if (nbCartes != other.nbCartes)
			return false;
		if (positionArrivee != other.positionArrivee)
			return false;
		if (typeAction != other.typeAction)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String strTypeAction = "";
		
		switch(typeAction){
			case Joueur.ActionImpossible: strTypeAction += "ActionImpossible";break;
			case Joueur.PasAttaque: strTypeAction += "PasAttaque";break;
			case Joueur.Reculer: strTypeAction += "Reculer";break;
			case Joueur.Avancer: strTypeAction += "Avancer";break;
			case Joueur.AttaqueDirecte: strTypeAction += "AttaqueDirecte";break;
			case Joueur.AttaqueIndirecte: strTypeAction += "AttaqueIndirecte";break;
			case Joueur.Parade: strTypeAction += "Parade";break;
			case Joueur.Fuite: strTypeAction += "Fuite";break;
			default: strTypeAction += "Erreur";
		}
		
		String str = estActionOffensive() ? "ActionOffensive" : estActionNeutre() ? "ActionNeutre" : "ActionDefensive";
		
		str += " [ typeAction= " + strTypeAction + ", nbCartes= " + nbCartes 
				+ ", positionArrivee= " + positionArrivee + ", deplacement= " + carteDeplacement + ", defense= " + carteAction + " ]";
		
		return str;
	}
	
	
}
