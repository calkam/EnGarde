package Modele.Joueur;

public abstract class Joueur {
	
	String nom ;
	Main main ;
	Figurine figurine ;
	
	protected Joueur(String nom) {
		
		this.nom = nom ;
		
	}
	
}