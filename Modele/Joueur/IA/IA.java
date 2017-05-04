package Modele.Joueur.IA;

import Modele.Piste;
import Modele.Joueur.*;
import Modele.Main;

public abstract class IA extends Joueur {

	IA(String nom) {
		super(nom, new Main(), new Piste());
	}
}