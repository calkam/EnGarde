package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionDefensive extends Action {

	private Carte deplacement;
	private Carte defense;
	
	public ActionDefensive(int typeAction, int nbCartes, int positionArrivee, Carte deplacement, Carte defense) {
		super(typeAction, nbCartes, positionArrivee);
		this.deplacement = deplacement;
		this.defense = defense;
	}
	
	public Carte getDeplacement() {
		return deplacement;
	}
	public void setDeplacement(Carte deplacement) {
		this.deplacement = deplacement;
	}
	public Carte getDefense() {
		return defense;
	}
	public void setDefense(Carte defense) {
		this.defense = defense;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((defense == null) ? 0 : defense.hashCode());
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
		ActionDefensive other = (ActionDefensive) obj;
		if (defense == null) {
			if (other.defense != null)
				return false;
		} else if (!defense.equals(other.defense))
			return false;
		if (deplacement == null) {
			if (other.deplacement != null)
				return false;
		} else if (!deplacement.equals(other.deplacement))
			return false;
		return true;
	}
	@Override
	public String toString() {
		String str = "ActionDefensive [ typeAction= " + typeAction + ", nbCartes= " + nbCartes 
				+ ", positionArrivee= " + positionArrivee + ", deplacement= " + deplacement + ", defense= " + defense + " ]";
		
		return str;
	}

	
	
}
