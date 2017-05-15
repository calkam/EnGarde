package Modele.Joueur.Humain;

import Modele.Triplet;
import Modele.Joueur.Action;
import Modele.Joueur.Joueur;
import Modele.Joueur.JoueurDroit;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;
import Modele.Plateau.Piste;

public class HumainDroit extends JoueurDroit {

	public HumainDroit(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}
	
	@Override
	public Joueur clone () {
		
		HumainDroit joueur = new HumainDroit(this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}

	@Override
	public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse)
			throws Exception {
		
		throw new UnsupportedOperationException("Modele.Joueur.Humain.HumainDroit.actionIA : Méthode non supportée par un joueur humain") ;
	
	}

}
