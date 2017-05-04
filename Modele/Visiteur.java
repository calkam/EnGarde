package Modele;

/**
 * @author gourdeaf
 *
 */
public abstract class Visiteur {

	public boolean visite(Case c){
		return false;
	};
	
	public boolean visite(Composant c) {
		return false;
	}
	
	public boolean visite(ComposantGraphique c) {
		return false;
	}
	
	public boolean visite(ObjetMouvant o) {
		return false;
	}
}