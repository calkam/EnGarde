package Modele.Plateau.Figurine;
import Modele.Reglages;
import Modele.Visitable;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Point;
import Modele.Composant.Rectangle;

public abstract class Figurine extends ObjetMouvant implements Visitable {
	protected int position ;
	
	public Figurine(int position, float x, float y) {
		super(new Rectangle(x, y, Reglages.lis("FigurineLargeur"), Reglages.lis("FigurineHauteur")), new Point(0, 0));
		this.position = position;
	}
	
	public Figurine(int i){
		this(i, 0, 0);
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