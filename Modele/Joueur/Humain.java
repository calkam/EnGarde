package Modele.Joueur ;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Tas.Main;
import Modele.Plateau.Piste;

public class Humain extends Joueur {

	public Humain(int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
	}
	
	@Override
	public Joueur clone () {
		
		Humain joueur = new Humain(this.direction, this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}

	@Override
	public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception {
		
		return null ;// actionHumain(actions_jouables, tour) ;
	}

	@Override
	public Action actionIA(Tour tour) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
