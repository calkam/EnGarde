package Modele.Joueur ;

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
	
	public Scanner scanner ;

	public Humain(int direction, String nom, Main main, Piste piste, Scanner scanner) {
		super(direction, nom, main, piste);
		this.scanner = scanner ;
	}

	public Action actionHumain(ActionsJouables actions_jouables, Tour tour) throws Exception {

		int choixAction;
		
		System.out.println(actions_jouables);
		
		if(actions_jouables.size() == 0){
			return new ActionNeutre(Joueur.ActionImpossible);
		}
		
		System.out.println("Veuillez effectuer votre choix d'action : nombre entre 0 et N (N étant un entier naturel)");
		
		choixAction = Integer.parseInt(scanner.nextLine());
		
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
		
		Humain joueur = new Humain(this.direction, this.nom, this.main.clone(), this.piste.clone(), new Scanner(System.in)) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}

	@Override
	public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception {
		
		return actionHumain(actions_jouables, tour) ;
	}

}
