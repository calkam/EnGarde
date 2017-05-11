package Modele.Composant;

import Modele.Plateau.Piste;

public abstract class ObjetMouvant extends ComposantGraphique implements Observateur {

    ComposantGraphique delegue;
    Piste piste;

    ObjetMouvant(ComposantGraphique d, Point v) {
        super(v.x(), v.y());
        delegue = d;
    }
    
    void observe(Piste p) {
        piste = p;
        piste.ajouteObservateur(this);
    }
    
    @Override
    public void miseAJour(Observable o, Object arg) {
        
    }

    @Override
    boolean accepte(Visiteur v) {
        return v.visite(this);
    }

    boolean rafraichit(long t) {
        return false;
    }

    void fixeVitesse(float x, float y) {
        this.getCoord().fixe(x, y);
    }

    float vitX() {
        return this.getCoord().x();
    }

    float vitY() {
        return this.getCoord().y();
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
