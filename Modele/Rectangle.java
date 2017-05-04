package Modele;

public class Rectangle extends ComposantGraphique {

	private float largeur;
	private float hauteur;
	
	Rectangle(float x, float y, float l, float h) {
		super(x, y);
		initialiser(l, h);
		// TODO Auto-generated constructor stub
	}
	
	final void initialiser(float l, float h) {
        this.largeur = l;
        hauteur = h;
    }

	void fixeDimensions(float l, float h) {
        initialiser(l, h);
    }

	public Boolean estCollision(Rectangle r) {
		if (dansLaBoite(r)) {
            return true;
        } else {
            return false;
        }
	}
	
	@Override
	ComposantGraphique copieVers(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getLargeur() {
		// TODO Auto-generated method stub
		return this.largeur;
	}

	@Override
	public float getHauteur() {
		// TODO Auto-generated method stub
		return this.hauteur;
	}

}