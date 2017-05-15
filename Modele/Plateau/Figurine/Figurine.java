package Modele.Plateau.Figurine;
import Modele.Composant.Rectangle;

public abstract class Figurine extends Rectangle {
	protected int position ;
	
	public Figurine(){}
	
	public Figurine(float x, float y, int position) {
		super(x, y);
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