package Modele.Joueur.IA;

import java.util.Enumeration;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

public class IALegendaire extends IA {

	public IALegendaire(int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
	}
	
	public Action actionIA (Tour tour) throws Exception {

		ActionsJouables actions_jouables ;
		Action actionChoisie = null;
		Action actionJouee = null;
		Tour tour_sauv= tour.clone();
		int val_max = Integer.MIN_VALUE ;
		int val_courante = 0;
			
		Joueur IA = tour.getJoueurPremier().equals(this) ? tour.getJoueurPremier() : tour.getJoueurSecond() ;
		Joueur Adverse = tour.getJoueurPremier().equals(tour.joueurAdverse(this)) ? tour.getJoueurPremier() : tour.getJoueurSecond() ;
		
		int distance = calculerNormeEntreDeuxPositions(IA.getPositionDeMaFigurine(), Adverse.getPositionDeMaFigurine());
		
		actions_jouables = IA.peutFaireAction(tour.getEstAttaque());
		
		System.out.println(actions_jouables.toString());
		
		Enumeration<Action> e = actions_jouables.elements();
		
		while(e.hasMoreElements()){
			actionChoisie = e.nextElement();
			
			tour.setEstAttaque(tour.jouerAction(actionChoisie, IA)); 
			
			if(actionChoisie.getTypeAction() == Joueur.Parade){
				
				System.out.println("PARADE : " + tour.getJoueurPremier().getMain().getNombreCarte() + "\n");
				val_courante = Max(tour,IA, Adverse,4);
				//val_courante = AlphaBeta(tour,5,-1000000,1000000,false);
				IA.remplirMain(tour.getPioche());
			
			}else{
				
				IA.remplirMain(tour.getPioche());
				val_courante = Min(tour, IA, Adverse, 4);
				//val_courante = AlphaBeta(tour,5,-1000000,1000000,true);
			}
			
			if(val_courante > val_max){
				val_max = val_courante;
				actionJouee = actionChoisie;
			}
			
			tour = tour_sauv.clone();
			//System.out.println("Val_courante : " + val_courante + "\n");
	
		}	
			
		//tour = tour_sauv;
		if(actionJouee == null){
			System.out.println("PROBLEME\n");
		}
		
		if(actionJouee.getTypeAction() == 1){
			if(Adverse.getMain().getNombreCarteGroupe(distance+actionJouee.getCarteDeplacement().getContenu()) > IA.getMain().getNombreCarteGroupe(distance+actionJouee.getCarteDeplacement().getContenu())){
				actions_jouables = actions_jouables.supprimerAction(actionJouee);
				if(!actions_jouables.isEmpty()){
					System.out.println(actions_jouables.toString());
					
					e = actions_jouables.elements();
					
					while(e.hasMoreElements()){
						actionChoisie = e.nextElement();
						
						tour.setEstAttaque(tour.jouerAction(actionChoisie, IA)); 
						
						if(actionChoisie.getTypeAction() == Joueur.Parade){
							
							System.out.println("PARADE : " + tour.getJoueurPremier().getMain().getNombreCarte() + "\n");
							val_courante = Max(tour,IA, Adverse,4);
							//val_courante = AlphaBeta(tour,5,-1000000,1000000,false);
							IA.remplirMain(tour.getPioche());
						
						}else{
							
							IA.remplirMain(tour.getPioche());
							val_courante = Min(tour, IA, Adverse, 4);
							//val_courante = AlphaBeta(tour,5,-1000000,1000000,true);
						}
						
						if(val_courante > val_max){
							val_max = val_courante;
							actionJouee = actionChoisie;
						}
						
						tour = tour_sauv.clone();
						//System.out.println("Val_courante : " + val_courante + "\n");
				
					}	
						
				}
			}
		}
		
		if(actionJouee.getTypeAction() == 2){
			if(Adverse.getMain().getNombreCarteGroupe(distance-actionJouee.getCarteDeplacement().getContenu()) > IA.getMain().getNombreCarteGroupe(distance-actionJouee.getCarteDeplacement().getContenu())){
				actions_jouables = actions_jouables.supprimerAction(actionJouee);
				if(!actions_jouables.isEmpty()){
					System.out.println(actions_jouables.toString());
					
					e = actions_jouables.elements();
					
					while(e.hasMoreElements()){
						actionChoisie = e.nextElement();
						
						tour.setEstAttaque(tour.jouerAction(actionChoisie, IA)); 
						
						if(actionChoisie.getTypeAction() == Joueur.Parade){
							
							System.out.println("PARADE : " + tour.getJoueurPremier().getMain().getNombreCarte() + "\n");
							val_courante = Max(tour,IA, Adverse,4);
							//val_courante = AlphaBeta(tour,5,-1000000,1000000,false);
							IA.remplirMain(tour.getPioche());
						
						}else{
							
							IA.remplirMain(tour.getPioche());
							val_courante = Min(tour, IA, Adverse, 4);
							//val_courante = AlphaBeta(tour,5,-1000000,1000000,true);
						}
						
						if(val_courante > val_max){
							val_max = val_courante;
							actionJouee = actionChoisie;
						}
						
						tour = tour_sauv.clone();
						//System.out.println("Val_courante : " + val_courante + "\n");
				
					}	
						
				}
			}
		}
		
		return actionJouee;
	}
	
	public int AlphaBeta(Tour tour, Joueur IA, Joueur Adverse, int profondeur, int alpha, int beta, boolean cherche_min) throws Exception {
		
		ActionsJouables actions_jouables ;
		Action actionChoisie = null;
		Tour tour_sauv;
		int val_courante = 0;
		
		if(!cherche_min){
			int val_max = -100000;
			
			actions_jouables = IA.peutFaireAction(tour.getEstAttaque());
			tour_sauv = tour.clone();
			
			if(actions_jouables.isEmpty()){
				return val_max;
			}
			
			if(tour.getPioche().estVide() && tour.getEstAttaque().getC1()!=1 &&  tour.getEstAttaque().getC1()!=2){
				
				int distance = calculerNormeEntreDeuxPositions(IA.getPositionDeMaFigurine(), Adverse.getPositionDeMaFigurine());
				int nbcarteIA = 0, nbcarteAdv = 0;
				nbcarteIA = IA.getMain().getNombreCarteGroupe(distance);
				nbcarteAdv = Adverse.getMain().getNombreCarteGroupe(distance);
				
				if(nbcarteIA>nbcarteAdv){
					return 10000;
				}else if(nbcarteIA<nbcarteAdv){
					return -10000;
				}else{
						
					int distIA = 25, distAdv = 25;
					distIA =  Math.abs(IA.getPositionDeMaFigurine()-12);
					distAdv = Math.abs(Adverse.getPositionDeMaFigurine()-12);
						
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
				return eval1(IA,Adverse,actions_jouables);
			}
			
			int A = alpha;
			int B = beta;
			
			Enumeration<Action> e = actions_jouables.elements();
			
			while(e.hasMoreElements()){
				
				actionChoisie = e.nextElement();
				tour.setEstAttaque(tour.jouerAction(actionChoisie,IA));
				
				if(actionChoisie.getTypeAction() == Joueur.Parade){
					
					val_courante = AlphaBeta(tour, IA, Adverse, profondeur-1,A,B,true);
					IA.remplirMain(tour.getPioche());
					
				}else{
					
					IA.remplirMain(tour.getPioche());
					val_courante = AlphaBeta(tour, IA, Adverse, profondeur-1,A,B,false);
				}
				
				if(val_courante > A){
					A = val_courante;
				}
				if(A>=B){
					return B;
				}
				
				tour = tour_sauv.clone();
	
			}
			
			return A;
			
		}else{
			
			int val_max = 100000;
			
			actions_jouables = Adverse.peutFaireAction(tour.getEstAttaque());
			tour_sauv = tour.clone();
		
			if(actions_jouables.isEmpty()){
				return val_max;
			}
			
			if(tour.getPioche().estVide()){
				
				int distance = calculerNormeEntreDeuxPositions(IA.getPositionDeMaFigurine(), Adverse.getPositionDeMaFigurine());
				int nbcarteIA = 0, nbcarteAdv = 0;
				nbcarteIA = IA.getMain().getNombreCarteGroupe(distance);
				nbcarteAdv = Adverse.getMain().getNombreCarteGroupe(distance);
				
				if(nbcarteIA>nbcarteAdv){
					return 10000;
				}else if(nbcarteIA<nbcarteAdv){
					return -10000;
				}else{
					
					int distIA = 25, distAdv = 25;
					distIA =  Math.abs(IA.getPositionDeMaFigurine()-12);
					distAdv = Math.abs(Adverse.getPositionDeMaFigurine()-12);
					
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
				return eval2(IA,Adverse,actions_jouables);
			}
			
			int A = alpha;
			int B = beta;
			
			Enumeration<Action> e = actions_jouables.elements();
			
			while(e.hasMoreElements()){
				
				actionChoisie = e.nextElement();
				tour.setEstAttaque(tour.jouerAction(actionChoisie,Adverse));
				
				if(actionChoisie.getTypeAction() == Joueur.Parade){
					
					val_courante = AlphaBeta(tour,IA,Adverse,profondeur-1,A,B,true);
					Adverse.remplirMain(tour.getPioche());
				
				}else{
					
					Adverse.remplirMain(tour.getPioche());
					val_courante = AlphaBeta(tour,IA,Adverse,profondeur-1,A,B,false);
				}
				
				if(val_courante < B){
					B = val_courante;
				}
				if(A>=B){
					return A;
				}
				
				tour = tour_sauv.clone();
	
			}
			
			return B;
		}
		
	}
	
	public int Min(Tour tour, Joueur IA, Joueur Adverse, int profondeur) throws Exception{
		
		ActionsJouables actions_jouables ;
		Action actionChoisie = null;
		Tour tour_sauv;
		int val_max = 100000;
		int val_courante = 0;
		
		actions_jouables = Adverse.peutFaireAction(tour.getEstAttaque());
		tour_sauv = tour.clone();
	
		if(actions_jouables.isEmpty()){
			return val_max;
		}
		
		if(tour.getPioche().estVide()){
			
			int distance = calculerNormeEntreDeuxPositions(IA.getPositionDeMaFigurine(), Adverse.getPositionDeMaFigurine());
			int nbcarteIA = 0, nbcarteAdv = 0;
			nbcarteIA = IA.getMain().getNombreCarteGroupe(distance);
			nbcarteAdv = Adverse.getMain().getNombreCarteGroupe(distance);
			
			if(nbcarteIA>nbcarteAdv){
				return 1000000;
			}else if(nbcarteIA<nbcarteAdv){
				return -1000000;
			}else{
				
				int distIA = 25, distAdv = 25;
				distIA =  Math.abs(IA.getPositionDeMaFigurine()-12);
				distAdv = Math.abs(Adverse.getPositionDeMaFigurine()-12);
				
				if(distIA<distAdv){
					return 1000000;
				}else if(distIA>distAdv){
					return -1000000;
				}else{
					return 0;
				}
			}
		}
		
		if(profondeur == 0){
			return eval2(IA,Adverse,actions_jouables);
		}
		
		Enumeration<Action> e = actions_jouables.elements();
		
		while(e.hasMoreElements()){
			
			actionChoisie = e.nextElement();
			tour.setEstAttaque(tour.jouerAction(actionChoisie, Adverse));
			
			if(actionChoisie.getTypeAction() == Joueur.Parade){
				
				val_courante = Min(tour,IA,Adverse,profondeur-1);
				Adverse.remplirMain(tour.getPioche());
			
			}else{
				
				Adverse.remplirMain(tour.getPioche());
				val_courante = Max(tour,IA,Adverse,profondeur-1);
			}
			
			if(val_courante < val_max){
				val_max = val_courante;
			}
			
			tour = tour_sauv.clone();
	
		}
		
		
		return val_max;
	
	}
	
	public int Max(Tour tour, Joueur IA, Joueur Adverse, int profondeur) throws Exception{
		
		ActionsJouables actions_jouables ;
		Action actionChoisie = null;
		Tour tour_sauv;
		int val_max = -100000;
		int val_courante = 0;
		
		actions_jouables = IA.peutFaireAction(tour.getEstAttaque());
		tour_sauv = tour.clone();
	
		if(actions_jouables.isEmpty()){
			return val_max;
		}
		
		if(tour.getPioche().estVide() && tour.getEstAttaque().getC1()!=1 &&  tour.getEstAttaque().getC1()!=2){
			
			int distance = calculerNormeEntreDeuxPositions(IA.getPositionDeMaFigurine(), Adverse.getPositionDeMaFigurine());
			int nbcarteIA = 0, nbcarteAdv = 0;
			nbcarteIA = IA.getMain().getNombreCarteGroupe(distance);
			nbcarteAdv = Adverse.getMain().getNombreCarteGroupe(distance);
			
			if(nbcarteIA>nbcarteAdv){
				return 1000000;
			}else if(nbcarteIA<nbcarteAdv){
				return -1000000;
			}else{
				
				int distIA = 25, distAdv = 25;
				distIA =  Math.abs(IA.getPositionDeMaFigurine()-12);
				distAdv = Math.abs(Adverse.getPositionDeMaFigurine()-12);
				
				if(distIA<distAdv){
					return 1000000;
				}else if(distIA>distAdv){
					return -1000000;
				}else{
					return 0;
				}
			}
		}
		
		if(profondeur == 0){
			//System.out.println("\nProfonduer de 0 : "+eval(tour,actions_jouables));
			return eval1(IA,Adverse,actions_jouables);
		}
		
		
		Enumeration<Action> e = actions_jouables.elements();
		
		while(e.hasMoreElements()){
			
			actionChoisie = e.nextElement();
			tour.setEstAttaque(tour.jouerAction(actionChoisie,IA));
			
			if(actionChoisie.getTypeAction() == Joueur.Parade){
				val_courante = Max(tour,IA, Adverse, profondeur-1);
				IA.remplirMain(tour.getPioche());
				
			}else{
				
				IA.remplirMain(tour.getPioche());
				val_courante = Min(tour,IA, Adverse, profondeur-1);
			}
			
			if(val_courante > val_max){
				val_max = val_courante;
			}
			
			tour = tour_sauv.clone();
	
		}
		
		return val_max;
	}
	
	public int eval2(Joueur IA, Joueur Adverse, ActionsJouables actions_jouables) throws Exception {
		
		int position = 0;
		position = IA.getPositionDeMaFigurine();
		return (23-position) /*+ 500*PlusAvancerIA(tour,actions_jouables)*/ + 1000*nbAttaqueImparableOuEgale(Adverse, actions_jouables) + 10000*nbAttaqueImparable(Adverse,actions_jouables) + 500*DistSecu();
	}
	
	public int eval1(Joueur IA, Joueur Adverse, ActionsJouables actions_jouables) throws Exception {
		int position = 0;
		position = IA.getPositionDeMaFigurine();
		return (23-position) /*+ 500*PlusAvancerIA(tour,actions_jouables)*/ - 10000*AttaqueImparableRecu(IA,Adverse,actions_jouables) ;
}

	public int PlusAvancerIA(Joueur IA, ActionsJouables actions_jouables) throws Exception {
		
		
		int position1 = 0, position2 = 0;
		position1 = IA.getPositionDeMaFigurine();
		position2 = IA.getPositionFigurineAdverse();
		if((23 - position1)>position2){
			return 1;
		}
		
		return 0;
	}
	
	public int DistSecu() throws Exception{
		int dist = distanceFigurines() ;
		if(dist > 5){
			return 1;
		}
		return 0;
	}

	public int nbAttaqueImparableOuEgale(Joueur Adverse, ActionsJouables actions_jouables) throws Exception{
		
		Action action_courante;
		Enumeration<Action> e = actions_jouables.elements();
		int cpt = 0;
		
		while(e.hasMoreElements()){
			action_courante = e.nextElement();
			if(action_courante.getTypeAction()==Joueur.AttaqueDirecte || action_courante.getTypeAction()==Joueur.AttaqueIndirecte){
				if(action_courante.getNbCartes() >= Adverse.getMain().getNombreCarteGroupe(action_courante.getCarteAction().getContenu())){
					cpt++;
				}
			}
		}
		
		return cpt;
	}

	public int nbAttaqueImparable(Joueur Adverse, ActionsJouables actions_jouables) throws Exception{
		
		Action action_courante;
		Enumeration<Action> e = actions_jouables.elements();
		int cpt = 0;
		
		while(e.hasMoreElements()){
			action_courante = e.nextElement();
			if(action_courante.getTypeAction()==Joueur.AttaqueDirecte || action_courante.getTypeAction()==Joueur.AttaqueIndirecte){
				if(action_courante.getNbCartes() > Adverse.getMain().getNombreCarteGroupe(action_courante.getCarteAction().getContenu())){
					cpt++;
				}
			}
		}
		
		return cpt;
	}

	public int nbAttaqueAdv(Tour tour,ActionsJouables actions_jouables) throws Exception{
		Action action_courante;
		Enumeration<Action> e = actions_jouables.elements();
		int cpt = 0;
		
		while(e.hasMoreElements()){
			action_courante = e.nextElement();
			if(action_courante.getTypeAction()==Joueur.AttaqueDirecte || action_courante.getTypeAction()==Joueur.AttaqueIndirecte){
				cpt++;
			}
			
		}
		
		return cpt;
	}

	public int AttaqueImparableRecu(Joueur IA, Joueur Adverse, ActionsJouables actions_jouables) throws Exception{
		
		Action action_courante;
		Enumeration<Action> e = actions_jouables.elements();
		boolean FuiteObligatoire = true;
		int dist = distanceFigurines() ;
		
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
		
		}else{
			
			if(IA.getMain().getNombreCarteGroupe(dist) < Adverse.getMain().getNombreCarteGroupe(dist)){
				return 1;
			}
		}
		
		return 0;
	}

	private int calculerNormeEntreDeuxPositions(int position1, int position2){
		return Math.abs(position1 - position2);
	}

	@Override
	public Joueur clone () {
		
		IALegendaire joueur = new IALegendaire(this.direction, this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}

	@Override
	public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	

	
}
