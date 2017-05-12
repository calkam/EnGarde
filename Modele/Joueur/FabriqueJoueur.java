package Modele.Joueur;

import Modele.Tas.Defausse;
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
	Defausse defausse;
	
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
			default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur.nouveauJoueurHumain : position du joueur inconnue") ;
			
		}
		
		case "IA" :
			
			switch (nom) {
			
			case "Facile" :
				
				switch (position) {
				
				case 1 : return new IAFacileGauche(nom, main, piste) ;
				case 2 : return new IAFacileDroit(nom, main, piste) ;
				default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur.nouvelleIAFacile : position du joueur inconnue") ;
				
				}
			
			case "Moyen" :
			
				switch (position) {
			
				case 1 : return new IAMoyenGauche(nom, main, piste) ;
				case 2 : return new IAMoyenDroit(nom, main, piste) ;
				default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur.nouvelleIAMoyen : position du joueur inconnue") ;
				
				}
					
			case "Difficile" : 
				
				switch (position) {
				
				case 1 : return new IADifficileGauche(nom, main, piste,defausse) ;
				case 2 : return new IADifficileDroit(nom, main, piste, defausse) ;
				default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur.nouvelleIADifficile : position du joueur inconnue") ;
				
				}
			
			default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : difficulté inconnue") ;
			
			}
		
		default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : type de joueur inconnu") ;
			
		}
		
	}

}
