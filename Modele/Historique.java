package Modele;

import java.util.ArrayList;

public class Historique {
	
	private ArrayList <Tour> tours ;
	private String nomjoueur;
	
	public Historique (String nomjoueur) {
		this.nomjoueur = nomjoueur ;
	}
	
	public void init () {
		this.tours = new ArrayList <> () ;
	}
	
	public void ajouterTour (Tour t) {
		tours.add(t.clone()) ;
	}
	
	public void annulerTour (Tour t) {
		
		if (tours.size() > 0) {
			
			Tour dernier_tour = tours.remove(tours.size()-1) ;
			
			t.setJoueurPremier(dernier_tour.getJoueurPremier());
			t.setJoueurSecond(dernier_tour.getJoueurSecond());
			t.setPioche(dernier_tour.getPioche());
			t.setDefausse(dernier_tour.getDefausse());
			t.setEstAttaque(dernier_tour.getEstAttaque());
			
		}
		
		
		
	}
	
	@Override
	public String toString() {
		
		String str = "";
		
		/*str += "Historique [\n";
		
		for (Tour t : tours) {
			
			str += "JoueurPremier : " + t.getJoueurPremier() ;
			str += "JoueurSecond : " + t.getJoueurSecond() ;
			str += "Pioche : " + t.getPioche() ;
			str += "Defausse : " + t.getDefausse() ;
			
		}
		
		str += "]\n";
		return str;*/
		
		str += "Historique de " + nomjoueur + ", Dernier Tour [\n\n";
		
		str += "JoueurPremier : " + tours.get(tours.size()-1).getJoueurPremier() ;
		str += "JoueurSecond : " + tours.get(tours.size()-1).getJoueurSecond() ;
		str += "Pioche : " + tours.get(tours.size()-1).getPioche() ;
		str += "Defausse : " + tours.get(tours.size()-1).getDefausse() ;
		
		str += "]\n";
		
		return str;
		
		
		
		//return "nbTourRealiseJoueur : " + nbTourRealiseJoueur + "\n";
	}
	
}