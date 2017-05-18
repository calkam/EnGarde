package Modele.Plateau;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class Jeton extends Rectangle implements Visitable {

	public final static int droit = 0;
	public final static int gauche = 1;
	
	boolean visible;
	int cote;
	
	public Jeton(float x, float y, float largeur, float hauteur, boolean visible, int cote){
		super(x, y, largeur, hauteur);
		this.visible = visible;
		this.cote = cote;
	}
	
	public Jeton(float x, float y, int cote){
		this(x, y, Reglages.lis("JetonLargeur"), Reglages.lis("JetonHauteur"), true, cote);
	}

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getCote() {
		return cote;
	}

	public void setCote(int cote) {
		this.cote = cote;
	}

}