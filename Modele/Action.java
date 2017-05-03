package Modele;

/**
 * @author gourdeaf
 *
 */
public abstract class Action {
	
	
	/** 
	 * Si l'action est possible, retourne l'indice de la case où il est possible de se rendre
	 * 
	 * @param maCarte
	 * @param maFigurine
	 * @param figurineAdverse
	 * @return
	 */
	public abstract int casePossible(Carte maCarte, Piste piste);
}