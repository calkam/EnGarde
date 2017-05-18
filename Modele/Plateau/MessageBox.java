package Modele.Plateau;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class MessageBox extends Rectangle implements Visitable{

    String texte;

    MessageBox(String t, float x, float y, float largeur, float hauteur) {
    	super(x, y, largeur, hauteur);
    	this.texte = t;
    }
    
    MessageBox(String t, float x, float y){
    	this(t, x, y, Reglages.lis("MessageBoxLargeur"), Reglages.lis("MessageBoxHauteur"));
    }
    
    MessageBox(String t){
    	this(t, 0, 0, Reglages.lis("MessageBoxLargeur"), Reglages.lis("MessageBoxHauteur"));
    }

    public String getTexte() {
        return texte;
    }
    
    public void setTexte(String t) {
        this.texte = t;
    }

    @Override
	public boolean accept(Visiteur v) {
        return v.visite(this);
    }
}
