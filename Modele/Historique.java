package Modele;

import java.util.ArrayList;

import Modele.Joueur.Action;

public class Historique {
	
	private int nbTourRealiseJoueur ;
	
	ArrayList <Tour> tours ;
	ArrayList <ArrayList <Action>> liste_actions ;
	
	public Historique () {
		
		this.nbTourRealiseJoueur = 0 ;
		this.tours = new ArrayList <> () ;
		this.liste_actions = new ArrayList <> () ;
		
	}
	
	public void ajouterTour (Tour t) {
		
		tours.add(t.clone()) ;
		nbTourRealiseJoueur++ ;
		
	}
	
	public void annulerTour (Tour t) {
		
		if (nbTourRealiseJoueur > 0) {
			
			Tour dernier_tour = tours.remove(nbTourRealiseJoueur-1) ;
			nbTourRealiseJoueur-- ;
			
			t.setJoueurPremier(dernier_tour.getJoueurPremier());
			t.setJoueurSecond(dernier_tour.getJoueurSecond());
			t.setPioche(dernier_tour.getPioche());
			t.setDefausse(dernier_tour.getDefausse());
			
		}
		
		
		
	}
	
	@Override
	public String toString() {
		
		/*String str = "";
		str += "Historique [\n";
		
		for (Tour t : tours) {
			
			str += "JoueurPremier : " + t.getJoueurPremier() ;
			str += "JoueurSecond : " + t.getJoueurSecond() ;
			str += "Pioche : " + t.getPioche() ;
			str += "Defausse : " + t.getDefausse() ;
			
		}
		
		str += "]\n";
		return str;*/
		
		return "nbTourRealiseJoueur : " + nbTourRealiseJoueur + "\n";
	}
	
}