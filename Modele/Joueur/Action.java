package Modele.Joueur;

public abstract class Action {

	protected int typeAction;
	protected int nbCartes;
	protected int positionArrivee;

	public Action(int typeAction, int nbCartes, int positionArrivee) {
		super();
		this.typeAction = typeAction;
		this.nbCartes = nbCartes;
		this.positionArrivee = positionArrivee;
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
	
	@Override
	public String toString() {
		return "Action [typeAction=" + typeAction + ", nbCartes=" + nbCartes + ", positionArrivee=" + positionArrivee
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (nbCartes != other.nbCartes)
			return false;
		if (positionArrivee != other.positionArrivee)
			return false;
		if (typeAction != other.typeAction)
			return false;
		return true;
	}
}
