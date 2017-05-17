package Modele.Joueur.IA;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

abstract public class IA extends Joueur {
	
	public final static int pasAttaque = 0;
	public final static int attaqueDirect = 1;
	public final static int attaqueIndirect = 2;
	
	public IA (int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
	}
	
	abstract public Action actionIA (Tour tour) throws Exception ;
	
	public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception {
		
		return actionIA (tour) ;
		
	}

}
