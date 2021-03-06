package Modele.Tas;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.Rectangle;

public class Carte extends Rectangle implements Visitable {

	// CONSTANTES
	// IDENTIFICATEURS DES TAS DE CARTES

	public final static int mainDroite = Main.droite;
	public final static int mainGauche = Main.gauche;
	public final static int pioche = 2;
	public final static int defausse = 3;

	// VALEURS DES CARTES

	public final static int UN = 1;
	public final static int DEUX = 2;
	public final static int TROIS = 3;
	public final static int QUATRE = 4;
	public final static int CINQ = 5;

	// ATTRIBUTS

	private int id;
	private int contenu;
	private int tas;
	private boolean selectionne;
	private boolean visible;

	// CONSTRUCTEURS

	public Carte(int id, int contenu, int tas, float x, float y, float largeur, float hauteur, boolean select){
		super(x, y, Reglages.lis("CarteLargeur"), Reglages.lis("CarteHauteur"));
		this.id = id;
		this.tas = tas;
		this.contenu = contenu ;
		this.selectionne = select;
		this.visible = false;
	}

	public Carte(int id, int contenu, float x, float y, float largeur, float hauteur){
		this(id, contenu, Carte.pioche, x, y, Reglages.lis("CarteLargeur"), Reglages.lis("CarteHauteur"), false);
	}

	public Carte(int id, int contenu){
		this(id, contenu, 0, 0, Reglages.lis("CarteLargeur"), Reglages.lis("CarteHauteur"));
	}

	public Carte(int contenu){
		this(0, contenu);
	}

	public Carte(){
		super(0, 0, 0, 0);
	}

	// HASHCODE ET EQUALS

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contenu;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carte other = (Carte) obj;
		if (contenu != other.contenu)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	// TO STRING

	public String toString(){
		String str = "Carte : ";
		switch (this.getContenu()) {
	        case 1: str += Integer.toString(1); break;
	        case 2: str += Integer.toString(2); break;
	        case 3: str += Integer.toString(3); break;
	        case 4: str += Integer.toString(4); break;
	        case 5: str += Integer.toString(5); break;
	        default: throw new RuntimeException("Ajout de carte sur " + this +" impossible");
		}
		return str;
	}

	/**
	 * GETTER/SETTER
	 **/

	public int getID() {
		return id;
	}

	public int getContenu() {
		return contenu;
	}

	public int getTas() {
		return tas;
	}

	public void setTas(int tas) {
		this.tas = tas;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
		if(selectionne){
			this.setY(0);
		}else{
			this.setY(Reglages.lis("PositionYCarte"));
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * VUE
	 **/

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

}