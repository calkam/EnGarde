package Modele;

public class Case extends Rectangle implements Visitable {

    private int couleur;
    final static float largeur = 30;
    final static float hauteur = 30;

    private void initialise(int c) {
        couleur = c;
    }

    Case(int c, float x, float y) {
    	super(x, y, largeur, hauteur);
        initialise(c);
    }

    public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	@Override
    ComposantGraphique copieVers(float x, float y) {
        return new Case(couleur, x, y);
    }

    @Override
    public String toString() {
        return "[" + posX() + ", " + posY() + "]," + " couleur " + couleur + "";
    }

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

}
