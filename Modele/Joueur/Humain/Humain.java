package Modele.Joueur.Humain;

import Modele.Tas.Main;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;

public abstract class Humain extends Joueur {

	public Humain(String nom, Main main, Piste piste) {
		super(nom, main, piste) ;
	}

}