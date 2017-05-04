package Modele.Joueur.IA;

import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

public abstract class IA extends Joueur {

	IA(String nom) {
		super(nom, new Main(), new Piste());
	}
}