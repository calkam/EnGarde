package Modele.Composant;

import Modele.Visiteur;

public abstract class Composant {

    public Composant() {
    	
    }

    // Un composant peut accepter un visiteur destiné à
    // effectuer un traitement sur lui. La valeur de retour
    // détermine si le composant doit être supprimé
    public boolean accepte(Visiteur v) {
        return v.visite(this);
    }

}
