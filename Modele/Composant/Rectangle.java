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

	public Boolean estCollision(Rectangle r) {
		if (dansLaBoite(r)) {
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
	
	@Override
	public void setLargeur(float l) {
		this.largeur = l;
	}
	
	@Override
	public void setHauteur(float h) {
		this.hauteur = h;
	}

}