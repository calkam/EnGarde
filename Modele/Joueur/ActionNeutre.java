package Modele.Joueur;

import Modele.Tas.Carte;

public class ActionNeutre extends Action{

	private Carte deplacement;

	public ActionNeutre(int typeAction, int nbCartes, int positionArrivee, Carte deplacement) {
		super(typeAction, nbCartes, positionArrivee);
		this.deplacement = deplacement;
	}

	public Carte getDeplacement() {
		return deplacement;
	}

	public void setDeplacement(Carte deplacement) {
		this.deplacement = deplacement;
	}

	@Override
	public String toString() {
		String str = "ActionDefensive [ typeAction= " + typeAction + ", nbCartes= " + nbCartes 
				+ ", positionArrivee= " + positionArrivee + ", deplacement= " + deplacement + " ]";
		
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		ActionNeutre other = (ActionNeutre) obj;
		if (deplacement == null) {
			if (other.deplacement != null)
				return false;
		} else if (!deplacement.equals(other.deplacement))
			return false;
		return true;
	}
	
}
