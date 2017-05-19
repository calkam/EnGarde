package Modele.Plateau ;
import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Point;
import Modele.Composant.Rectangle;
import Modele.Joueur.Joueur;

public class Figurine extends ObjetMouvant implements Visitable {
		
	// CONSTANTES
	
	// TYPE FIGURINE
	// La Figurine GAUCHE a pour direction la DirectionDroite (1)
	// La Figurine DROITE a pour direction la DirectionGauche (-1)
	
	public final static int GAUCHE = Joueur.DirectionDroite ;
	public final static int DROITE = Joueur.DirectionGauche ;
	
	// ATTRIBUTS
	
	protected int direction ;
	protected int position ;
	
	// CONSTRUCTEUR
	
	public Figurine(int direction, float x, float y, int position) {
		super(new Rectangle(x, y, Reglages.lis("FigurineLargeur"), Reglages.lis("FigurineHauteur")), new Point(0, 0));
		this.direction = direction;
		this.position = position;
	}
	
	public Figurine(int direction, int position){
		this(direction, 0, 0, position);
	}
	
	@Override
	public boolean accept(Visiteur v) throws Exception {
		return v.visite(this);
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