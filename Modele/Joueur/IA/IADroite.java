package Modele.Joueur.IA;


import Modele.Joueur.JoueurDroit;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

abstract public class IADroite extends JoueurDroit{

	public final static int pasAttaque = 0;
	public final static int attaqueDirect = 1;
	public final static int attaqueIndirect = 2;
	
	public IADroite(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}

}
