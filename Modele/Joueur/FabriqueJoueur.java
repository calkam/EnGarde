package Modele.Joueur;

import Modele.Tas.Main;
import Modele.Plateau.Piste;

import Modele.Joueur.IA.IADifficile;
import Modele.Joueur.IA.IAFacile;
import Modele.Joueur.IA.IALegendaire;
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
			case "Humain" : return new Humain(position, nom, main, piste) ;
			case "IA" :
				switch (nom) {
					case "Facile"     : return new IAFacile(position, "IA " + nom, main, piste) ;
					case "Moyen"      : return new IAMoyen(position, "IA " + nom, main, piste) ;
					case "Difficile"  : return new IADifficile(position, "IA " + nom, main, piste) ;
					case "Legendaire" : return new IALegendaire(position, "IA " + nom, main, piste) ;
					default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : difficulté inconnue") ;
				}
			
			default : throw new Exception("Modele.Joueur.FabriqueJoueur.nouveauJoueur : type de joueur inconnu") ;
		}
	}
}
