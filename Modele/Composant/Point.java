package Modele.Composant;

public class Point {

    private float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    float x() {
        return x;
    }
    
    float y() {
        return y;
    }
    
    void setX(float x){
    	this.x = x;
    }
    
    void setY(float y){
    	this.y = y;
    }
    
    Point fixe(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    Point mult(float s) {
        return fixe(x()*s, y()*s);
    }

    Point ajoute(float x, float y) {
        return fixe(x() + x, y() + y);
    }
    
    Point ajoute(Point p) {
        return ajoute(p.x(), p.y());
    }

    float normeCarree() {
        return x() * x() + y() * y();
    }

    float norme() {
        return (float) Math.sqrt(normeCarree());
    }

    Point normalise() {
        return mult(1.0f / norme());
    }

    float produitScalaire(Point p) {
        return x() * p.x() + y() * p.y();
    }

    @Override
    public String toString() {
        return "(" + x() + ", " + y() + ")";
    }
}
