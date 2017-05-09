package Modele.Plateau;

public class Score {
	private int nbPoints;
	
	public Score(){
		nbPoints = 0;
	}

	public int getNbPoints() {
		return nbPoints;
	}

	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}

	@Override
	public String toString() {
		return Integer.toString(nbPoints);
	}
}