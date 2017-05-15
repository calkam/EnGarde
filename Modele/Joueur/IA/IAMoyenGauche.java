package Modele.Joueur.IA;

import Modele.Triplet;
import Modele.Joueur.Action;
import Modele.Joueur.Joueur;
import Modele.Joueur.JoueurGauche;
import Modele.Plateau.Piste;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;

public class IAMoyenGauche extends JoueurGauche {

	public IAMoyenGauche(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}

	@Override
	public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Joueur clone() {
		// TODO Auto-generated method stub
		return null;
	}

	
}