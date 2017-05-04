package Modele;

/**
 * @author gourdeaf
 *
 */
interface Action {	
	
	/** 
	 * Si l'action est possible, retourne l'indice de la case où il est possible de se rendre
	 * 
	 * @param maCarte
	 * @param maFigurine
	 * @param figurineAdverse
	 * @return
	 */
	
	public void avancer (int déplacement, int pos_fig_adv)  ;
	public void reculer (int déplacement, int pos_fig_adv)  ;
	public void attaque_directe (int déplacement, int pos_fig_adv)  ;
	public void attaque_indirecte (int déplacement, int pos_fig_adv)  ;
	public void parade (int déplacement, int pos_fig_adv)  ;
	public void retraite (int déplacement, int pos_fig_adv)  ;
	
	public abstract int casePossible(Carte maCarte, Piste piste);
	
}