package Modele.Joueur.IA;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

abstract public class IA extends Joueur {
	
	public IA (int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
	}
	
	abstract public Action actionIA (Tour tour) throws Exception ;
	
	protected int distanceFigurines () throws Exception {
		return Math.abs(getPositionDeMaFigurine() - getPositionFigurineAdverse()) ;
	}

}
