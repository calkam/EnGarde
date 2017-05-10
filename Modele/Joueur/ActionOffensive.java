package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionOffensive extends Action {

	private Carte deplacement;
	private Carte attaque;
	
	public ActionOffensive(int typeAction, int nbCartes, int positionArrivee, Carte deplacement, Carte attaque) {
		super(typeAction, nbCartes, positionArrivee);
		this.deplacement = deplacement;
		this.attaque = attaque;
	}
	
	public Carte getDeplacement() {
		return deplacement;
	}
	public void setDeplacement(Carte deplacement) {
		this.deplacement = deplacement;
	}
	public Carte getAttaque() {
		return attaque;
	}
	public void setAttaque(Carte attaque) {
		this.attaque = attaque;
	}
	
	@Override
	public String toString() {
		String str = "ActionOffensive [ typeAction= " + typeAction + ", nbCartes= " + nbCartes 
				+ ", positionArrivee= " + positionArrivee + ", deplacement= " + deplacement + ", attaque= " + attaque + " ]";
		
		return str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attaque == null) ? 0 : attaque.hashCode());
		result = prime * result + ((deplacement == null) ? 0 : deplacement.hashCode());
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
		ActionOffensive other = (ActionOffensive) obj;
		if (attaque == null) {
			if (other.attaque != null)
				return false;
		} else if (!attaque.equals(other.attaque))
			return false;
		if (deplacement == null) {
			if (other.deplacement != null)
				return false;
		} else if (!deplacement.equals(other.deplacement))
			return false;
		return true;
	}
	
}
