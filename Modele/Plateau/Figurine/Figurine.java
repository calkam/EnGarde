package Modele.Plateau.Figurine;
import Modele.Composant.Cercle ;

public abstract class Figurine extends Cercle {
	protected int position ;
	
	public Figurine(){}
	
	public Figurine(float x, float y, float rayon, int position) {
		super(x, y, rayon);
		this.position = position;
	}
	
	Figurine(int i){
		this.position = i;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "[position=" + position + "]";
	}	
}