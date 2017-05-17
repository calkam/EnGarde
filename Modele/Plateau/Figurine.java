package Modele.Plateau ;
import Modele.Composant.Rectangle;

public class Figurine extends Rectangle {
		
	// TYPE FIGURINE
	// La Figurine GAUCHE a pour direction la direction DROITE (1)
	// La Figurine DROITE a pour direction la direction GAUCHE (-1)
	
	// ATTRIBUTS
	
	protected int direction ;
	protected int position ;
	
	// CONSTRUCTEUR
	
	public Figurine(int direction, float x, float y, int position) {
		super(x, y);
		this.direction = direction;
		this.position = position;
	}
	
	/**
	 * GETTER/SETTER
	 **/
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	// TO STRING

	@Override
	public String toString() {
		return "[position=" + position + "]";
	}
}