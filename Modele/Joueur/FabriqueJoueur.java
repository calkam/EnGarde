package Modele.Joueur;

import Modele.Tas.Main;
import Modele.Plateau.Piste;
import Modele.Joueur.Humain.*;
import Modele.Joueur.IA.*;

public class FabriqueJoueur {
	
	int position ;
	String type ;
	String nom ;
	Main main ;
	Piste piste ;
	
	public FabriqueJoueur(int position, String type, String nom, Main main, Piste piste) {
		
		this.position = position ;
		this.type = type ;
		this.nom = nom ;
		this.main = main ;
		this.piste = piste ;
		
	}
	
	public Joueur nouveauJoueur () throws Exception {
		
		switch (type) {
		
		case "Humain" : 
			
			switch (position) {
			
			case 1 : return new HumainGauche(nom, main, piste) ;
			case 2 : return new HumainDroit(nom, main, piste) ;
			default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : position du joueur inconnue") ;
			
		}
		case "IA" :
			
			switch (nom) {
			
			case "Facile" : return new IAFacile() ;
			case "Moyen" : return new IAMoyen() ;
			case "Difficile" : return new IADifficile() ;
			default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : difficulté inconnue") ;
			
			}
		
		default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : type de joueur inconnu") ;
			
		}
		
	}

}
