package Modele;

public class Case extends Rectangle implements Visitable {

    int couleur;
    Observable attaches;

    public int lisCouleur() {
        return couleur;
    }

    private void initialise(int c) {
        couleur = c;
    }

    Case(int c) {
        this(c, 0, 0);
    }

    Case(int c, float x, float y) {
    	super(x, y);
        initialise(c);
    }

    @Override
    ComposantGraphique copieVers(float x, float y) {
        return new Case(couleur, x, y);
    }

    @Override
    public String toString() {
        return "Brique en (" + posX() + ", " + posY() + ")," + ", couleur s" + couleur;
    }

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

}
