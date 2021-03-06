package Modele.Composant;

public class Rectangle extends ComposantGraphique {

	protected float largeur;
	protected float hauteur;
	
	public Rectangle(float x, float y) {
		super(x, y);
	}
	
	public Rectangle(float x, float y, float l, float h) {
		super(x, y);
		initialiser(l, h);
	}
	
	final void initialiser(float l, float h) {
        this.largeur = l;
        this.hauteur = h;
    }

	public void fixeDimensions(float l, float h) {
        initialiser(l, h);
    }

	public Boolean estCollision(float x, float y) {
		if (dansLaBoite(x, y)) {
            return true;
        } else {
            return false;
        }
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
	
	public void setLargeur(float l) {
		this.largeur = l;
	}
	
	public void setHauteur(float h) {
		this.hauteur = h;
	}

}