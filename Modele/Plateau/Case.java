package Modele.Plateau;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class Case extends Rectangle implements Visitable {

	public final static int TRANSPARENT = 0;
	public final static int BLACK = 1;
	public final static int VERT = 2; 
	public final static int JAUNE = 3; 
	
    private int couleur;
    private int numero;

    Case(int c, int numero, float x, float y) {
    	super(x, y, Reglages.lis("CaseLargeur"), Reglages.lis("CaseHauteur"));
        initialise(c, numero);
    }
    
    Case(int c, float x, float y) {
    	super(x, y, Reglages.lis("CaseLargeur"), Reglages.lis("CaseHauteur"));
        initialise(c, -1);
    }
    
    private void initialise(int c, int numero) {
        this.couleur = c;
        this.numero = numero;
    }

    public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]," + " [couleur " + couleur + ", numero " + numero + "]";
    }

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

}
