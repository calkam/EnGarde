package Modele.Joueur;

import java.util.ArrayList;

import Modele.Piste;
import Modele.Visiteur;
import Modele.Carte;
import Modele.Case;
import Modele.ActionOffensive;
import Modele.Figurine;
import Modele.Main;

/**
 * @author gourdeaf
 *
 */
public abstract class Joueur {
	
	private String nom ;
	private Main main ;
	protected Piste piste ;
	
	protected boolean deplacer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() ;
		
		return piste.estdansPiste(position_arrivee) ;
		
	}

	protected boolean estlibre (int position) {
		
		return position != piste.getFigurineDroite().getPosition() ;
		
	}
	/*ArrayList<Action> lesActions;
	
	protected Joueur(String nom) {
		
		this.nom = nom ;
		lesActions = new ArrayList<>();
		lesActions.add(new AttaqueDirecte());
		lesActions.add(new AttaqueIndirecte());
		lesActions.add(new Parade());
		lesActions.add(new Retraite());
		lesActions.add(new Avancer());
		lesActions.add(new Reculer());
	}*/
	
	/**
	 * Met en surbrillance les cases correpondantes à des actions possibles
	 * en fonction de la carte sélectionnée dans la main du joueur, la position
	 * de sa figurine et de celle du joueur adverse
	 * 
	 * @param maCarte
	 */
	/*public void selectionCarte(Carte maCarte){
		Object figurineAdverse;
		
		for(Action a : lesActions){
			if(a.estPossible(maCarte, figurine, figurineAdverse)){
				Case casePossible = a.casePossible(maCarte, figurine, figurineAdverse);
				
				Piste.accepte(new Visiteur(){
					void visite(Case c){
						if(c.equals(casePossible)){
							// On met le flag de la case c à true pour mettre la case en surbrillance					
						}
					}
				});
			}
		}
	}*/
}