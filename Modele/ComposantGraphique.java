package Modele;

abstract class ComposantGraphique extends Composant {
    Point coord;

    abstract ComposantGraphique copieVers(float x, float y);

    public abstract float getLargeur();

    public abstract float getHauteur();

    ComposantGraphique(float x, float y) {
        coord = new Point(x, y);
    }
    
    public float posX() {
        return coord.x();
    }

    public float posY() {
        return coord.y();
    }

    void deplacePosition(float x, float y) {
        coord.ajoute(x, y);
    }

    boolean dansLaBoite(ComposantGraphique c) {
        return (c.posX() < posX() + getLargeur())
                && (c.posX() + c.getLargeur() > posX())
                && (c.posY() < posY() + getHauteur())
                && (c.posY() + c.getHauteur() > posY());
    }
}
