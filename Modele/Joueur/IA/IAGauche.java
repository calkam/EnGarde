package Modele.Joueur.IA;

import Modele.Couple;
import Modele.Joueur.JoueurGauche;
import Modele.Plateau.Piste;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Main;

public class IAGauche extends JoueurGauche {
	
	public final static int pasAttaque = 0;
	public final static int attaqueDirect = 1;
	public final static int attaqueIndirect = 2;
	
	
	protected Defausse defausse ;
	protected Couple <Carte, Integer> attaque ;
	
	public IAGauche(String nom, Main main, Piste piste, Defausse defausse) {
		super(nom, main, piste);
		this.defausse = defausse ;
		
	}

}
