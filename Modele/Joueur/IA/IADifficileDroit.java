package Modele.Joueur.IA;

import Modele.Triplet;
import Modele.Joueur.Action;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;

public class IADifficileDroit extends IADroite {

	public IADifficileDroit(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}


	@Override
	public Joueur clone () {
		
		IADifficileDroit joueur = new IADifficileDroit(this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}


	@Override
	public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
