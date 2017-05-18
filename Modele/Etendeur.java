package Modele;

import Modele.Composant.ComposantGraphique;

// Class mise à disposition du dessinateur pour redimensionner les composants
// à la taille de son dessin
public class Etendeur {

    float x, y, l, h;
    float factX, factY;

    public void fixeEchelle(float fx, float fy) {
        factX = fx;
        factY = fy;
    }

    // Ici on triche un peu :
    // Comme on sait que les dimensions du composant ne varient pas
    // durant toute la vie de l'étendeur, on stocke directement le
    // résultat à l'échelle.
    public void fixeComposant(ComposantGraphique c) {
        x = c.getX() * factX;
        y = c.getY() * factY;
        l = c.getLargeur() * factX;
        h = c.getHauteur() * factY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLargeur() {
        return l;
    }

    public float getHauteur() {
        return h;
    }

    public float factX() {
        return factX;
    }

    public float factY() {
        return factY;
    }
}
