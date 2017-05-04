package Modele;

/**
 * @author gourdeaf
 *
 */
/**
 * @author bleuzeb
 *
 */

public interface ActionOffensive {	
	
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
	
	/** 
	 * Exécution d'une action possible
	 * 
	 * @param valeur Carte : distance/portée/déplacement
	 * @return void
	 */
	
	public void avancer (Carte distance)  ;
	public void reculer (Carte distance)  ;
	public void executer_attaque_directe (Carte portee, int nombre)  ;
	public void executer_attaque_indirecte (Carte deplacement, Carte portee, int nombre)  ;
	
	
}