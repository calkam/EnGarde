package Modele.Plateau;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class Case extends Rectangle implements Visitable {

    private int couleur;

    private void initialise(int c) {
        couleur = c;
    }

    Case(int c, float x, float y) {
    	super(x, y, Reglages.lis("CaseLargeur"), Reglages.lis("CaseHauteur"));
        initialise(c);
    }

    public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	@Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]," + " couleur " + couleur + "";
    }

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

}
