package Modele;

/**
 * @author gourdeaf
 *
 */
public abstract class Action {	
	
	/** 
	 * Test à partir d'une carte de la main du joueur, de la position de sa figurine
	 * et de celle de son adversaire si l'action est possible
	 * 
	 * @param maCarte
	 * @param maFigurine
	 * @param figurineAdverse
	 */
	public abstract void estPossible(Carte maCarte, Figurine maFigurine, Figurine figurineAdverse);
	
	/** 
	 * Si l'action est possible, retourne l'indice de la case où il est possible de se rendre
	 * 
	 * @param maCarte
	 * @param maFigurine
	 * @param figurineAdverse
	 * @return
	 */
	public abstract Case casePossible(Carte maCarte, Figurine maFigurine, Figurine figurineAdverse);
}