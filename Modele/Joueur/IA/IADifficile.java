package Modele.Joueur.IA;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

public class IADifficile extends IA {

	public IADifficile(int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action actionIA(Tour tour) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Joueur clone () {
		
		IAFacile joueur = new IAFacile(this.direction, this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}
}