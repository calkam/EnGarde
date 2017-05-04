package Modele.Joueur;

import java.util.ArrayList;

/**
 * @author gourdeaf
 *
 */
/**
 * @author bleuzeb
 *
 */

public interface Action {	
	
	/** 
	 * Si l'action est possible avec la carte, retourne l'indice de la case où il est possible de se rendre
	 * 
	 * @param valeur carte : distance/portée/déplacement
	 * @return int position
	 */
	
	public int peut_avancer (int distance)  ;
	public int peut_reculer (int distance)  ;
	public int peut_executer_attaque_directe (int portee)  ;
	public int peut_executer_attaque_indirecte (int deplacement, int portee)  ;
	public ArrayList<CartesDefaussees> peut_executer_action (int val_carte) ;
	
	
	
	/** 
	 * Exécution d'une action possible
	 * 
	 * @param valeur Carte : distance/portée/déplacement
	 * @return void
	 */
	
	public void avancer (int distance)  ;
	public void reculer (int distance)  ;
	public void executer_attaque_indirecte (int deplacement, int portee, int nombre)  ;
	
	
	
}