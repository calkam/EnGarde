package Modele;

abstract class ObjetMouvant extends ComposantGraphique implements Observateur {

    ComposantGraphique delegue;
    Plateau plateau;

    ObjetMouvant(ComposantGraphique d, Point v) {
        super(v.x(), v.y());
        delegue = d;
    }
    
    void observe(Plateau p) {
        plateau = p;
        plateau.ajouteObservateur(this);
    }
    
    @Override
    public boolean miseAJour() {
        return false;
    }

    @Override
    boolean accepte(Visiteur v) {
        return v.visite(this);
    }

    boolean rafraichit(long t) {
        return false;
    }

    void fixeVitesse(float x, float y) {
        coord.fixe(x, y);
    }

    float vitX() {
        return coord.x();
    }

    float vitY() {
        return coord.y();
    }

    @Override
    ComposantGraphique copieVers(float x, float y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getLargeur() {
        return delegue.getLargeur();
    }

    @Override
    public float getHauteur() {
        return delegue.getHauteur();
    }

    @Override
    public float posX() {
        return delegue.posX();
    }

    @Override
    public float posY() {
        return delegue.posY();
    }

    @Override
    void deplacePosition(float x, float y) {
        delegue.deplacePosition(x, y);
    }

    void fixePosition(float x, float y) {
        delegue.deplacePosition(x - delegue.posX(), y - delegue.posY());
    }
   
}
