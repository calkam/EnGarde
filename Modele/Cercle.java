package Modele;

class Cercle extends ComposantGraphique {

    float rayon;
    
    public Cercle() {
		super(0, 0);
		rayon = 0;
	}

	Cercle(float x, float y, float rayon) {
        super(x, y);
        this.rayon = rayon;
    }

    float lisRayon() {
        return rayon;
    }
    
    void fixeRayon(float r) {
        rayon = r;
    }

    @Override
    ComposantGraphique copieVers(float x, float y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getLargeur() {
        return rayon * 2;
    }

    @Override
    public float getHauteur() {
        return rayon * 2;
    }
}
