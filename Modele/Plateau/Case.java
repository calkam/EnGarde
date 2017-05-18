package Modele.Plateau;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class Case extends Rectangle implements Visitable {

	public final static int TRANSPARENT = 0;
	public final static int ROUGE = 1;
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

<<<<<<< HEAD
	protected ComposantGraphique copieVers(float x, float y) {
        return new Case(couleur, x, y);
    }
=======
	public int getNumero() {
		return numero;
	}
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a

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
