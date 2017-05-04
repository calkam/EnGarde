package Modele;

public abstract class Figurine extends Cercle {
	protected String nomJoueur ;
	protected int position ;
	
	public Figurine(float x, float y, float rayon, String nomJoueur, int position) {
		super(x, y, rayon);
		this.nomJoueur = nomJoueur;
		this.position = position;
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