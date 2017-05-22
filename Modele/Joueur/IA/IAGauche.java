package Modele.Joueur.IA;

import Modele.Tour;
import Modele.Triplet;
import Modele.Joueur.Action;
import Modele.Joueur.JoueurGauche;
import Modele.Plateau.Piste;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;

abstract public class IAGauche extends JoueurGauche {
	
	public final static int pasAttaque = 0;
	public final static int attaqueDirect = 1;
	public final static int attaqueIndirect = 2;
	
	public IAGauche(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}
	
	abstract public Action actionIA (Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse, Tour tour_courant) throws Exception ;

}
