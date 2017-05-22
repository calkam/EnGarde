package Modele.Joueur.IA;

import java.util.ArrayList;
import java.util.Enumeration;

import Modele.Tour;
import Modele.Triplet;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;

public class IALegendaireDroite extends IADroite {

	public IALegendaireDroite(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}

	@Override
	public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse, Tour tour_courant) throws Exception {
		
		ActionsJouables actions_jouables ;
		Action actionChoisie = null;
		Action actionJouee = null;
		Tour tour_sauv= tour_courant.clone();
		int val_max = -1000000;
		int val_courante = 0;
		boolean estPremier =tour_courant.getJoueurPremier().getPositionFigurine() > tour_courant.getJoueurSecond().getPositionFigurine() ;
		
		if(estPremier){
			actions_jouables = tour_courant.getJoueurPremier().peutFaireAction(attaque);
		}else{
			System.out.println("ATTAQUE : " + tour_courant.getEstAttaque().getC1() + "\n");
			actions_jouables = tour_courant.getJoueurSecond().peutFaireAction(attaque);
		}
		System.out.println(actions_jouables.toString());
		Enumeration<Action> e = actions_jouables.elements();
		
		while(e.hasMoreElements()){
			actionChoisie = e.nextElement();
			
			if(estPremier){
				tour_courant.setEstAttaque(executerAction(actionChoisie,tour_courant.getJoueurPremier(),tour_courant)); 
			}else{
				tour_courant.setEstAttaque(executerAction(actionChoisie,tour_courant.getJoueurSecond(),tour_courant));
			}
			
			if(actionChoisie.getTypeAction() == Joueur.Parade){
				System.out.println("PARADE : " + tour_courant.getJoueurPremier().getMain().getNombreCarte() + "\n");
				val_courante = Max(tour_courant,6);
				if(estPremier){
					tour_courant.remplirMain(tour_courant.getJoueurPremier());
				}else{
					tour_courant.remplirMain(tour_courant.getJoueurSecond());
				}
			}else{
				if(estPremier){
					tour_courant.remplirMain(tour_courant.getJoueurPremier());
				}else{
					tour_courant.remplirMain(tour_courant.getJoueurSecond());
				}
				/*System.out.println(tour_courant.getJoueurPremier().toString());
				System.out.println(tour_courant.getJoueurSecond().toString());
				System.out.println(tour_courant.getDefausse().toString());
				System.out.println(tour_courant.getPioche().toString());
				System.out.println(tour_courant.getJoueurPremier().getPositionFigurine());
				System.out.println(tour_courant.getJoueurSecond().getPositionFigurine());
				*/
				val_courante = Min(tour_courant,6);
			}
			
			if(val_courante > val_max){
				val_max = val_courante;
				actionJouee = actionChoisie;
			}
			
			tour_courant = tour_sauv.clone();
			//System.out.println("Val_courante : " + val_courante + "\n");

		}	
			
		//tour_courant = tour_sauv;
		if(actionJouee == null){
			System.out.println("PROBLEME\n");
		}
		
		return actionJouee;
	}

	
	private Triplet<Integer, Integer, Integer> executerAction(Action actionAJouer, Joueur joueur, Tour tour_courant) throws Exception{
		Carte carteDeplacement=null;
		Carte carteAction=null;
		
		int typeAction;
		int nbCartesAttqJouees;
		int valeurCarteAttqJouee;
		
		ArrayList<Carte> cartesDeMemeValeur;
		
		//System.out.println("Main : " + joueur.getMain().getMain());
		
		switch(actionAJouer.getTypeAction()){
			case Joueur.Reculer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				tour_courant.getDefausse().ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Avancer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				tour_courant.getDefausse().ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.AttaqueDirecte : 
				carteAction = actionAJouer.getCarteAction();
				tour_courant.getDefausse().ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = tour_courant.getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						tour_courant.getDefausse().ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}
				typeAction = attaqueDirect; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.AttaqueIndirecte :
				// On avance dans un premier temps
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				tour_courant.getDefausse().ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				
				// On attaque dans un second temps	
				carteAction = actionAJouer.getCarteAction();
				tour_courant.getDefausse().ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = tour_courant.getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						tour_courant.getDefausse().ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}
				typeAction = attaqueIndirect; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.Parade :
				carteAction = actionAJouer.getCarteAction();
				tour_courant.getDefausse().ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = tour_courant.getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						tour_courant.getDefausse().ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}				
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Fuite :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				tour_courant.getDefausse().ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			default: throw new Exception("Erreur lors de l'exécution de l'action");
		}
		
		//System.out.println("\nVous avez joué : carte de déplacement : " + carteDeplacement + ", carte d'action : " + carteAction);
		
		return new Triplet<>(typeAction, nbCartesAttqJouees, valeurCarteAttqJouee);
	}
	
	public int Max(Tour tour_courant, int profondeur) throws Exception{
		
		ActionsJouables actions_jouables ;
		Action actionChoisie = null;
		Tour tour_sauv;
		int val_max = -100000;
		int val_courante = 0;
		boolean estPremier =tour_courant.getJoueurPremier().getPositionFigurine() > tour_courant.getJoueurSecond().getPositionFigurine() ;
		
		if(estPremier){
			actions_jouables = tour_courant.getJoueurPremier().peutFaireAction(tour_courant.getEstAttaque());
		}else{
			actions_jouables = tour_courant.getJoueurSecond().peutFaireAction(tour_courant.getEstAttaque());
		}
		tour_sauv = tour_courant.clone();
	
		if(actions_jouables.isEmpty()){
			return val_max;
		}
		
		if(tour_courant.getPioche().estVide() && tour_courant.getEstAttaque().getC1()!=1 &&  tour_courant.getEstAttaque().getC1()!=2){
			int distance = calculerNormeEntreDeuxPositions(tour_courant.getJoueurPremier().getPositionFigurine(), tour_courant.getJoueurSecond().getPositionFigurine());
			int nbcarteIA = 0, nbcarteAdv = 0;
			if(estPremier){
				nbcarteIA = tour_courant.getJoueurPremier().getMain().getNombreCarteGroupe(distance);
				nbcarteAdv = tour_courant.getJoueurSecond().getMain().getNombreCarteGroupe(distance);
			}else{
				nbcarteIA = tour_courant.getJoueurSecond().getMain().getNombreCarteGroupe(distance);
				nbcarteAdv = tour_courant.getJoueurPremier().getMain().getNombreCarteGroupe(distance);
			}
			if(nbcarteIA>nbcarteAdv){
				return 10000;
			}else if(nbcarteIA<nbcarteAdv){
				return -10000;
			}else{
				int distIA = 25, distAdv = 25;
				if(estPremier){
					distIA = Math.abs(tour_courant.getJoueurPremier().getPositionFigurine()-12);
					distAdv = Math.abs(tour_courant.getJoueurSecond().getPositionFigurine()-12);
				}else{
					distIA = Math.abs(tour_courant.getJoueurSecond().getPositionFigurine()-12);
					distAdv = Math.abs(tour_courant.getJoueurPremier().getPositionFigurine()-12);
				}
				if(distIA<distAdv){
					return 10000;
				}else if(distIA>distAdv){
					return -10000;
				}else{
					return 0;
				}
			}
		}
		
		if(profondeur == 0){
			//System.out.println("\nProfonduer de 0 : "+eval(tour_courant,actions_jouables));
			return eval(tour_courant,actions_jouables);
		}
		
		
		Enumeration<Action> e = actions_jouables.elements();
		
		while(e.hasMoreElements()){
			actionChoisie = e.nextElement();
			
			if(estPremier){
				tour_courant.setEstAttaque(executerAction(actionChoisie,tour_courant.getJoueurPremier(),tour_courant));
			}else{
				tour_courant.setEstAttaque(executerAction(actionChoisie,tour_courant.getJoueurSecond(),tour_courant));
			}
			
			if(actionChoisie.getTypeAction() == Joueur.Parade){
				val_courante = Max(tour_courant,profondeur-1);
				if(estPremier){
					tour_courant.remplirMain(tour_courant.getJoueurPremier());
				}else{
					tour_courant.remplirMain(tour_courant.getJoueurSecond());
				}
			}else{
				if(estPremier){
					tour_courant.remplirMain(tour_courant.getJoueurPremier());
				}else{
					tour_courant.remplirMain(tour_courant.getJoueurSecond());
				}
				val_courante = Min(tour_courant,profondeur-1);
			}
			
			if(val_courante > val_max){
				val_max = val_courante;
			}
			
			tour_courant = tour_sauv.clone();

		}
		
		
		return val_max;
		
		
		
	}
	

public int Min(Tour tour_courant, int profondeur) throws Exception{
		
		ActionsJouables actions_jouables ;
		Action actionChoisie = null;
		Tour tour_sauv;
		int val_max = 100000;
		int val_courante = 0;
		boolean estPremier =tour_courant.getJoueurPremier().getPositionFigurine() > tour_courant.getJoueurSecond().getPositionFigurine() ;
		
		if(estPremier){
			actions_jouables = tour_courant.getJoueurSecond().peutFaireAction(tour_courant.getEstAttaque());
		}else{
			actions_jouables = tour_courant.getJoueurPremier().peutFaireAction(tour_courant.getEstAttaque());
		}
		
		tour_sauv = tour_courant.clone();
	
		if(actions_jouables.isEmpty()){
			return val_max;
		}
		
		if(tour_courant.getPioche().estVide()){
			int distance = calculerNormeEntreDeuxPositions(tour_courant.getJoueurPremier().getPositionFigurine(), tour_courant.getJoueurSecond().getPositionFigurine());
			int nbcarteIA = 0, nbcarteAdv = 0;
			if(estPremier){
				nbcarteIA = tour_courant.getJoueurPremier().getMain().getNombreCarteGroupe(distance);
				nbcarteAdv = tour_courant.getJoueurSecond().getMain().getNombreCarteGroupe(distance);
			}else{
				nbcarteIA = tour_courant.getJoueurSecond().getMain().getNombreCarteGroupe(distance);
				nbcarteAdv = tour_courant.getJoueurPremier().getMain().getNombreCarteGroupe(distance);
			}
			if(nbcarteIA>nbcarteAdv){
				return 10000;
			}else if(nbcarteIA<nbcarteAdv){
				return -10000;
			}else{
				int distIA = 25, distAdv = 25;
				if(estPremier){
					distIA = Math.abs(tour_courant.getJoueurPremier().getPositionFigurine()-12);
					distAdv = Math.abs(tour_courant.getJoueurSecond().getPositionFigurine()-12);
				}else{
					distIA = Math.abs(tour_courant.getJoueurSecond().getPositionFigurine()-12);
					distAdv = Math.abs(tour_courant.getJoueurPremier().getPositionFigurine()-12);
				}
				if(distIA<distAdv){
					return 10000;
				}else if(distIA>distAdv){
					return -10000;
				}else{
					return 0;
				}
			}
		}
		
		if(profondeur == 0){
			return eval(tour_courant,actions_jouables);
		}
		
		Enumeration<Action> e = actions_jouables.elements();
		
		while(e.hasMoreElements()){
			actionChoisie = e.nextElement();
			
			if(estPremier){
				tour_courant.setEstAttaque(executerAction(actionChoisie,tour_courant.getJoueurSecond(),tour_courant));
			}else{
				tour_courant.setEstAttaque(executerAction(actionChoisie,tour_courant.getJoueurPremier(),tour_courant));
			}
			
			if(actionChoisie.getTypeAction() == Joueur.Parade){
				val_courante = Min(tour_courant,profondeur-1);
				if(estPremier){
					tour_courant.remplirMain(tour_courant.getJoueurSecond());
				}else{
					tour_courant.remplirMain(tour_courant.getJoueurPremier());
				}
			}else{
				if(estPremier){
					tour_courant.remplirMain(tour_courant.getJoueurSecond());
				}else{
					tour_courant.remplirMain(tour_courant.getJoueurPremier());
				}
				val_courante = Max(tour_courant,profondeur-1);
			}
			
			if(val_courante < val_max){
				val_max = val_courante;
			}
			
			tour_courant = tour_sauv.clone();

		}
		
		
		return val_max;
	
	}

	public int eval(Tour tour_courant,ActionsJouables actions_jouables) throws Exception {
		int position = 0;
		boolean estPremier =tour_courant.getJoueurPremier().getPositionFigurine() > tour_courant.getJoueurSecond().getPositionFigurine() ;
			position = tour_courant.getJoueurPremier().getPositionFigurine();
		if(estPremier){
			position = tour_courant.getJoueurPremier().getPositionFigurine();
		}else{
			position = tour_courant.getJoueurSecond().getPositionFigurine();
		}

		return (23-position) + 1000*nbAttaqueImparable(tour_courant,actions_jouables) -10000*AttaqueImparableRecu(tour_courant,actions_jouables);
	}
	
	public int nbAttaqueImparable(Tour tour_courant,ActionsJouables actions_jouables) throws Exception{
		Action action_courante;
		Enumeration<Action> e = actions_jouables.elements();
		int cpt = 0;
		boolean estPremier =tour_courant.getJoueurPremier().getPositionFigurine() > tour_courant.getJoueurSecond().getPositionFigurine() ;
		
		while(e.hasMoreElements()){
			action_courante = e.nextElement();
			if(action_courante.getTypeAction()==Joueur.AttaqueDirecte || action_courante.getTypeAction()==Joueur.AttaqueIndirecte){
				if(estPremier){
					if(action_courante.getNbCartes() > tour_courant.getJoueurSecond().getMain().getNombreCarteGroupe(action_courante.getCarteAction().getContenu())){
						cpt++;
					}
				}else{
					if(action_courante.getNbCartes() > tour_courant.getJoueurPremier().getMain().getNombreCarteGroupe(action_courante.getCarteAction().getContenu())){
						cpt++;
					}
				}
				
			}
			
		}
		
		return cpt;
	}
	
	public int AttaqueImparableRecu(Tour tour_courant,ActionsJouables actions_jouables) throws Exception{
		Action action_courante;
		Enumeration<Action> e = actions_jouables.elements();
		boolean FuiteObligatoire = true;
		
		if(actions_jouables.isEmpty()){
			return 1;
		}
		while(e.hasMoreElements()){
			action_courante = e.nextElement();
			if(action_courante.getTypeAction()!= Joueur.Fuite){
				FuiteObligatoire = false;
			}
				
		}
		
		if(FuiteObligatoire){
			return 1;
		}
		
		return 0;
	}
	
	private int calculerNormeEntreDeuxPositions(int position1, int position2){
		return Math.abs(position1 - position2);
	}
	
	/*@Override
	public Joueur clone () {
		
		IALegendaireDroite joueur = new IALegendaireDroite(this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}*/

	

	
}
