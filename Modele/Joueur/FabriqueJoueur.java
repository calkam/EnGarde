package Modele.Joueur;

import Modele.Joueur.IA.IADifficile;
import Modele.Joueur.IA.IAFacile;
import Modele.Joueur.IA.IAMoyen;

public class FabriqueJoueur {
	
	String type ;
	String nom ;
	
	FabriqueJoueur(String type, String nom) {
		
		this.type = type ;
		this.nom = nom ;
	}
	
	public Joueur nouveauJoueur () throws Exception {
		
		switch(type) {
		
		case "Humain" : return new Humain(nom) ;
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
