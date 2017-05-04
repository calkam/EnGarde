package Modele;

public class Manche {
	
	int numero;
	int nbTourRealise;
	Tour tourEnCours;
	
	public Manche(int numero, int nbTourRealise, Tour tourEnCours) {
		this.numero = numero;
		this.nbTourRealise = nbTourRealise;
		this.tourEnCours = tourEnCours;
	}
	
	public Manche(int numero, Tour tour) {
		this(numero, 0, tour);
	}
	
	public Manche(int numero){
		this(numero, 0, new Tour());
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public void jouerManche(){
		while(!pioche.estVide() && ){
			tourEnCours.jouerTour();
		}
	}
	
}