package Modele.Joueur;

import java.util.ArrayList;

import Modele.AttaqueDirecte;
import Modele.AttaqueIndirecte;
import Modele.Parade;
import Modele.Piste;
import Modele.Retraite;
import Modele.Visiteur;
import Modele.Avancer;
import Modele.Carte;
import Modele.Case;
import Modele.Reculer;
import Modele.Action;
import Modele.Figurine;
import Modele.Main;

/**
 * @author gourdeaf
 *
 */
public abstract class Joueur {
	
	String nom ;
	Main main ;
	Figurine figurine ;
	ArrayList<Action> lesActions;
	
	protected Joueur(String nom) {
		
		this.nom = nom ;
		lesActions = new ArrayList<>();
		lesActions.add(new AttaqueDirecte());
		lesActions.add(new AttaqueIndirecte());
		lesActions.add(new Parade());
		lesActions.add(new Retraite());
		lesActions.add(new Avancer());
		lesActions.add(new Reculer());
	}
	
	/**
	 * Met en surbrillance les cases correpondantes à des actions possibles
	 * en fonction de la carte sélectionnée dans la main du joueur, la position
	 * de sa figurine et de celle du joueur adverse
	 * 
	 * @param maCarte
	 */
	public void selectionCarte(Carte maCarte){
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
	}
}