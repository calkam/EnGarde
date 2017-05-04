package Modele;

/**
 * @author gourdeaf
 *
 */
public abstract class Visiteur {

	public void visite(Case c){		
	};
	
	public void visite(Composant c) {
	}
	
	public void visite(ComposantGraphique c) {
	}
	
	public void visite(ObjetMouvant o) {
	}
}