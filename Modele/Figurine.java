package Modele;

public class Figurine extends Cercle {
	private String nomJoueur ;
	private int position ;
	
	Figurine(String nom, int i){
		super();
		this.nomJoueur = nom;
		this.position = i ;
	}
	
	public String getNomJoueur() {
		return nomJoueur;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	
}