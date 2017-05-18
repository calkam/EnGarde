package Modele.Joueur;

import Modele.Tas.Main;
import Modele.Plateau.Piste;

import java.util.Scanner;

import Modele.Joueur.IA.IADifficile;
import Modele.Joueur.IA.IAFacile;
import Modele.Joueur.IA.IAMoyen;

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
		
		case "Humain" : return new Humain(position, nom, main, piste, new Scanner(System.in)) ;
		
		case "IA" :
			
			switch (nom) {
			
<<<<<<< HEAD
			case "Facile"    : return new IAFacile(position, "IA " + nom, main, piste) ;
			case "Moyen"     : return new IAMoyen(position, "IA " + nom, main, piste) ;
			case "Difficile" : return new IADifficile(position, "IA " + nom, main, piste) ;
=======
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
				
				case 1 : return new IADifficileGauche(nom, main, piste) ;
				case 2 : return new IADifficileDroit(nom, main, piste) ;
				default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur.nouvelleIADifficile : position du joueur inconnue") ;
				
				}
			
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
			default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : difficulté inconnue") ;
			
			}
		
		default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : type de joueur inconnu") ;
			
		}
		
	}

}
