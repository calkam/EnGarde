package Modele;

import java.util.Arrays;

public abstract class Composant {

    Composant() {
    }

    // Un composant peut accepter un visiteur destiné à
    // effectuer un traitement sur lui. La valeur de retour
    // détermine si le composant doit être supprimé
    boolean accepte(Visiteur v) {
        return v.visite(this);
    }

}
