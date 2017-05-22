package Modele.Joueur.IA;

import Modele.Joueur.Joueur;
import Modele.Joueur.JoueurGauche;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

public class IAFacileGauche extends JoueurGauche {

	public IAFacileGauche(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}

	@Override
	public Joueur clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse, Tour tour_courant)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}*/

	
}