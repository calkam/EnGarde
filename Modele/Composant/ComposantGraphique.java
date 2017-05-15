package Modele.Composant;

public abstract class ComposantGraphique extends Composant {
    private Point coord;

	public abstract float getLargeur();
    public abstract float getHauteur();
    
    public ComposantGraphique(float x, float y) {
        coord = new Point(x, y);
    }
    
    public float getX() {
        return coord.x();
    }

    public float getY() {
        return coord.y();
    }
    
    public void setX(float x) {
        this.coord.setX(x);
    }

    public void setY(float y) {
    	this.coord.setY(y);
    }

    public Point getCoord(){
    	return coord;
    }
    
    public void setCoord(Point coord) {
		this.coord = coord;
	}
    
    void deplacePosition(float x, float y) {
        coord.ajoute(x, y);
    }

    boolean dansLaBoite(float x, float y) {
        return (x < getX() + getLargeur() && x > getX() && x < getY() + getHauteur() && y > getY());
    }
}
