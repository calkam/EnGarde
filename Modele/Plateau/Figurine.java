package Modele.Plateau ;
import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Point;
import Modele.Composant.Rectangle;

public class Figurine extends ObjetMouvant implements Visitable {
		
	// CONSTANTES
	
	// TYPE FIGURINE
	// La Figurine GAUCHE a pour direction la DIRECTIONDROITE (1)
	// La Figurine DROITE a pour direction la DIRECTIONGAUCHE (-1)
	
	public final static int GAUCHE = 1 ;
	public final static int DROITE = -1 ;
	
	// ATTRIBUTS
	
	protected int direction ;
	protected int position ;
	
	// CONSTRUCTEUR
	
	public Figurine(int direction, float x, float y, int position) {
		super(new Rectangle(x, y, Reglages.lis("FigurineLargeur"), Reglages.lis("FigurineHauteur")), new Point(0, 0));
		this.direction = direction;
		this.position = position;
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return false;
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