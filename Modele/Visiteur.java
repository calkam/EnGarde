package Modele;

import Modele.Composant.Composant;
import Modele.Composant.ObjetMouvant;
import Modele.Plateau.Case;
import Modele.Plateau.Piste;
import Modele.Plateau.Figurine.Figurine;
import Modele.Plateau.Figurine.FigurineDroite;
import Modele.Plateau.Figurine.FigurineGauche;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;

/**
 * @author gourdeaf
 *
 */
public abstract class Visiteur {

	public boolean visite(Composant c) {
		return false;
	}
	
	public boolean visite(ObjetMouvant o) {
		return visite((Composant) o);
	}
	
	public boolean visite(Case c){
		return visite((Composant) c);
	};
	
	public boolean visite(Carte c) {
		return visite((Composant) c);
	}
	
	public boolean visite(Piste p) {
		return visite((Composant) p);
	}
	
	public boolean visite(Pioche p) {
		return visite((Composant) p);
	}
	
	public boolean visite(Defausse d) {
		return visite((Composant) d);
	}
	
	public boolean visite(Main m) {
		return visite((Composant) m);
	}
	
	public boolean visite(Figurine f) {
		return visite((ObjetMouvant) f);
	}
	
	public boolean visite(FigurineDroite fd) {
		return visite((Figurine) fd);
	}
	
	public boolean visite(FigurineGauche fg) {
		return visite((Figurine) fg);
	}
}