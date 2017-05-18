package Modele.Plateau;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.ComposantGraphique;
import Modele.Composant.Rectangle;

public class Case extends Rectangle implements Visitable {
	
	// CONSTANTES
	// COULEURS

	public final static int TRANSPARENT = 0;
	public final static int ROUGE = 1;
	public final static int VERT = 2; 
	public final static int JAUNE = 3; 
	
	// ATTRIBUTS
	
    private int couleur;
    private int numero;
    
    // CONSTRUCTEURS

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
    
    // TO STRING
    
    @Override
    public String toString() {
        return "[" + getX() + ", " + getY() + "]," + " [couleur " + couleur + ", numero " + numero + "]";
    }
    
    /**
	 * GETTER/SETTER
	 **/

    public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	protected ComposantGraphique copieVers(float x, float y) {
        return new Case(couleur, x, y);
    }

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	/**
	 * VUE
	 **/

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

}
