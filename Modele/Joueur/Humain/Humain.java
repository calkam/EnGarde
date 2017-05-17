package Modele.Joueur.Humain;

import java.util.Enumeration;
import java.util.Scanner;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionNeutre;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Tas.Main;
import Modele.Plateau.Piste;

public class Humain extends Joueur {

	public Humain(int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
	}

	@Override
	public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception {

		int choixAction;
		
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		
		System.out.println(actions_jouables);
		
		if(actions_jouables.size() == 0){
			return new ActionNeutre(Joueur.ActionImpossible);
		}
		
		System.out.println("Veuillez effectuer votre choix d'action : nombre entre 0 et N (N étant un entier naturel)");
		
		choixAction = Integer.parseInt(s.nextLine());
		
		Action actionCherchee = null;		
		
		Enumeration<Action> e = actions_jouables.elements();
		int i = 0;
		
		while(e.hasMoreElements() && i != choixAction){
			e.nextElement();				
			i++;
		}
		
		if(i == choixAction){
			actionCherchee = e.nextElement();
		}
		
		return actionCherchee;
	}
	
	@Override
	public Joueur clone () {
		
		Humain joueur = new Humain(this.direction, this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}

}
