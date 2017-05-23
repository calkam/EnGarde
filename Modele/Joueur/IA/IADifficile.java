package Modele.Joueur.IA;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

public class IADifficile extends IA {

	public IADifficile(int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action actionIA(Tour tour) throws Exception {
		return null;

		/*public Action actionIA (Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse,Main main_adv) throws Exception {
			
			Action action_jouee = new ActionNeutre(Reculer,0,22,new Carte(5))  ;
			int distance = piste.getFigurineDroite().getPosition() - piste.getFigurineGauche().getPosition();
			double risque = 17.0;
			double reussite = 50.0;
			
			if (attaque.getC1() == 1) { //Attaque directe subie
				for (Carte c : main.getMain()){
					if(c.getContenu() == attaque.getC3()){ //Parade obligatoire
						action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineDroite().getPosition(),null,c);
					}
				}
			}		
			
			if (attaque.getC1() == 2) { //Attaque indirecte subie
				
				if (main.getNombreCarteGroupe(attaque.getC3()) >= attaque.getC2() ) {// Si je peux parer, je pare
					
						for (Carte c : main.getMain()){
							if(c.getContenu() == attaque.getC3()){
								action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineDroite().getPosition(),null,c);
							}
						}
				}else{ //Si parade impossible
					
					if(pioche.getNombreCarte() == 1){
						for (Carte c : main.getMain()){
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
								action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
							}else{
								if(c.getContenu() < action_jouee.getCarteDeplacement().getContenu()){
									action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
								}
							}
							
						}
					}else{
						double Proba, sauv=100.0;
						boolean sansrisque = false,deja_passee = false;
						for (Carte c : main.getMain()){
							if(peut_reculer(c.getContenu()) != ActionImpossible) { 
								if(){ //Si on calcule un risque de perdre au tour prochain < 0.2, on recule, on choisis, si plusieurs cartes, celle qui nous permet de reculer le moins possible 
									sansrisque = true;
									if(sauv==100){//Si on a calculer aucun mouvements a perte > 0.2 avant d'en calculer un < 0.2
										if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
											action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
										}else{
											if(action_jouee.getCarteDeplacement().getContenu() > c.getContenu()){
												action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
											}
										}
									}else{//Si on a calculer des mouvements a perte > 0.2 avant d'en calculer un < 0.2
										if(deja_passee){
											if(action_jouee.getCarteDeplacement().getContenu() > c.getContenu()){
												action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
											}
										}else{
											action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
											deja_passee = true;
										}
									}
								}else{
									if(!sansrisque){ //Tant qu'on a pas calculer un risque de perte < 0.2, on choisis de reculer avec la carte ayant le moins de chance de perdre au prochain tour
										if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
											sauv = Proba;
											action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
										}else{
											if(Proba<sauv){
												sauv = Proba;
												action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
											}
										}
									}	
								}
							}
						}
					}
				}
			}
			
			if(attaque.getC1() == pasAttaque){ //Si on a pas encore decider du coup a jouer (si on a pas subie d'attaque indirecte/directe)
				
				if(pioche.getNombreCarte() == 1){ //Si il n'y a plus qu'une carte dans la pioche au debut de mon tour
					
					for (Carte c : main.getMain()) {//On regarde dans le cas ou on est plus avancer que l'adversaire sur la piste, si on peut reculer a une distance >=6 pour gagner directement
						
						if( (distance + c.getContenu()) >=6 && Math.abs(12-(piste.getFigurineDroite().getPosition() + c.getContenu() ))<=Math.abs(12-piste.getFigurineGauche().getPosition()) ){
							action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
						}
					
					}
					
				}
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on n'a pas pus gagner comme le roi des voleurs
					int nbcartedist = 0;
					int nbcartedistdefausse = 0;
					
					for (Carte c : main.getMain()) {
						if(c.getContenu() == distance){
							nbcartedist++;
						}
					}
					for (Carte c : defausse.getD()) {
						if(c.getContenu() == distance){
							nbcartedistdefausse++;
						}
					}
					if(nbcartedist>0){//Si attaque directe possible
						double Proba;
						int x,n,m,a;
						n = 25 -main.getNombreCarte() -defausse.getNombreCarte();
						a = n -pioche.getNombreCarte();
						x = nbcartedist+1;
						m = 5-nbcartedist -nbcartedistdefausse ;
						Proba = ProbaSup(x,n,m,a);
						System.out.println("Risque : "+ Proba +"\n");
						if(Proba<=risque){//Si risque de perte < 0.2, on attaque !
							for (Carte c : main.getMain()) {
								if(c.getContenu() == distance){
									action_jouee = new ActionOffensive(AttaqueDirecte,main.getNombreCarteGroupe(distance),piste.getFigurineDroite().getPosition(),null,c); 
								}
							}
						}
						
					}
					
					
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peut pas attaquer directement (sous condition), on teste si on peut attaquer indirectement
						
						for(int i=0; i<main.getNombreCarte(); i++){ //Pour chaque carte de la main
							for(int j=0; j<main.getNombreCarte(); j++){ //On regarde pour les 4 autres cartes
								if(i!=j){
									if((main.getCarte(i).getContenu() + main.getCarte(j).getContenu()) == distance ){ //Si on a (c1+c2)=distance --> attaque indirecte possible
										double Proba;
										int x,n,m,a,surplus=0;
										n = 25 -main.getNombreCarte() -defausse.getNombreCarte();
										a = 25 -main.getNombreCarte() -defausse.getNombreCarte() -pioche.getNombreCarte();
										x = main.getNombreCarteGroupe(main.getCarte(j).getContenu())+1;
										m = 5-main.getNombreCarteGroupe(main.getCarte(j).getContenu()) -defausse.getNombreCarteGroupe(main.getCarte(j).getContenu()) ;
										if(main.getCarte(i).getContenu() == main.getCarte(j).getContenu()){
											surplus = 1;
											x--;
										}
										if(pioche.getNombreCarte()>7){
											Proba = ProbaSup(x,n,m,a);
											System.out.println("Risque : "+ Proba +"\n");
											if(Proba<=risque){//Si risque de perde < 0.2, on attaque !
												if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
													action_jouee = new ActionOffensive(AttaqueIndirecte,main.getNombreCarteGroupe(main.getCarte(j).getContenu())-surplus,piste.getFigurineDroite().getPosition() - main.getCarte(i).getContenu(),main.getCarte(i),main.getCarte(j));
												}else{
													if(main.getCarte(i).getContenu() > action_jouee.getCarteDeplacement().getContenu()){
														action_jouee = new ActionOffensive(AttaqueIndirecte,main.getNombreCarteGroupe(main.getCarte(j).getContenu())-surplus,piste.getFigurineDroite().getPosition() - main.getCarte(i).getContenu(),main.getCarte(i),main.getCarte(j));
													}
												}
											}	
										}else{
											Proba = ProbaInf(x-1,n,m,a);
											System.out.println("Réussite : "+ Proba +"\n");
											if(Proba>=reussite){//Si risque de reussite > 0.5, on attaque !
												if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
													action_jouee = new ActionOffensive(AttaqueIndirecte,main.getNombreCarteGroupe(main.getCarte(j).getContenu())-surplus,piste.getFigurineDroite().getPosition() - main.getCarte(i).getContenu(),main.getCarte(i),main.getCarte(j));
												}else{
													if(main.getCarte(i).getContenu() > action_jouee.getCarteDeplacement().getContenu()){
														action_jouee = new ActionOffensive(AttaqueIndirecte,main.getNombreCarteGroupe(main.getCarte(j).getContenu())-surplus,piste.getFigurineDroite().getPosition() - main.getCarte(i).getContenu(),main.getCarte(i),main.getCarte(j));
													}
												}
											}	
										}
									}
								}
							}
							
						}
					}
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
						//Si on a decider de ne pas attaquer indirectement, on dois alors choisir la derniere action possible : le deplacement unique
						
						if(distance>12 ){//Si les deux joueurs sont assez éloignés (Correspond au début de manche)
							System.out.println("ELOIGNEE\n");
							
							for (Carte c : main.getMain()) {
								if(main.getNombreCarteGroupe(c.getContenu())>=4){//Si présence d'un groupe de 4 cartes ou plus, jouez une de ces cartes
									action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
								}
							}
							
							
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){//Sinon
								for (Carte c : main.getMain()) {
									if(main.getNombreCarteGroupe(c.getContenu())>=3 && c.getContenu()>3){//Si présence d'un groupe de 3 cartes ou plus, jouez une de ces cartes
										action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
									}
								}
							}
							
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){//Sinon
								for (Carte c : main.getMain()) {
									if(main.getNombreCarteGroupe(c.getContenu())==2 && c.getContenu()>3){//Si présence d'un groupe de 2 cartes, jouez une de ces cartes si sa valeur est >=4
										action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
									}
								}
							}
							
							
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){//Sinon
								for (Carte c : main.getMain()) {
									if(main.getNombreCarteGroupe(c.getContenu())==1){//On choisis la carte seule avec la plus grande valeur
										if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
											action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
										}else{
											if(c.getContenu() > action_jouee.getCarteDeplacement().getContenu()){
												action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
											}
										}
									}
								}
							}
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){//Sinon
								for (Carte c : main.getMain()) {
									if(main.getNombreCarteGroupe(c.getContenu())>=3){//Si présence d'un groupe de 3 cartes ou plus, jouez une de ces cartes
										action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
									}
								}
							}
							
							
							
						}else{//Si les deux joueurs sont approximativement proche
							System.out.println("PROCHE\n");
							if(distance>8){
								for (Carte c : main.getMain()) {
									if((distance - c.getContenu())>7){//Si présence d'un groupe de 3 cartes ou plus, jouez une de ces cartes
										action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
									}
								}
							}
							
							boolean sans_risque = false;
							double Proba;
							int x,n,m,a;
							n = 25 -main.getNombreCarte() -defausse.getNombreCarte();
							a = n -pioche.getNombreCarte();
							for (Carte c : main.getMain()){
								if(peut_avancer_ou_attaquer_directement(c.getContenu()).getC2()!= ActionImpossible && peut_avancer_ou_attaquer_directement(c.getContenu()).getC1()==true){ //Test si on peut avancer avec la carte c
									x = main.getNombreCarteGroupe(distance - c.getContenu())+1;
									m = 5-main.getNombreCarteGroupe(distance - c.getContenu()) -defausse.getNombreCarteGroupe(distance - c.getContenu()) ;
									if(distance == 2*c.getContenu()){
										x--;
									}
									if((distance - c.getContenu())>5){
										Proba = 0.0;
									}else{
										Proba = ProbaSup(x,n,m,a);
										System.out.println("Risque : "+ Proba +"\n");
									}
									
									if(Proba <= 30){ //Si on calcule un risque de perdre au tour prochain < 0.2, on avance, on choisis, si plusieurs cartes, celle qui nous permet d'avancer le plus possible 
										sans_risque = true;
										if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
											action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
										}else{
											if(action_jouee.getCarteDeplacement().getContenu() < c.getContenu()){
												action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
											}
										}
									}
								}
							}
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on obtient aucune action en avant avec perte < 0.2, on regarde en reculant
								n = 25 -main.getNombreCarte() -defausse.getNombreCarte();
								a = n -pioche.getNombreCarte();
								for (Carte c : main.getMain()){
									if(peut_reculer(c.getContenu()) != ActionImpossible) { //Si on peut reculer avec la carte
										x = main.getNombreCarteGroupe(distance + c.getContenu())+1;
										m = 5-main.getNombreCarteGroupe(distance + c.getContenu()) -defausse.getNombreCarteGroupe(distance + c.getContenu()) ;
										if((distance + c.getContenu())>5){
											Proba = 0.0;
										}else{
											Proba = ProbaSup(x,n,m,a);
											System.out.println("Risque : "+ Proba +"\n");
										}
										if(Proba <= risque){ //Si on calcule un risque de perdre au tour prochain < 0.2, on recule, on choisis, si plusieurs cartes, celle qui nous permet de reculer le moins possible 
											sans_risque = true;
											if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
												action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
											}else{
												if(action_jouee.getCarteDeplacement().getContenu() > c.getContenu()){
													action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
												}
												
											}
										}
									}
								}
							}
							
							if(!sans_risque){ //Si on n'a pas trouver de deplacement avec un risque < 0.2, on avance la ou le risque est minime
								System.out.println("TROP DE RISQUE\n");
								double sauv = 100;
								n = 25 -main.getNombreCarte() -defausse.getNombreCarte();
								a = n -pioche.getNombreCarte();
								for (Carte c : main.getMain()){
									if(peut_avancer_ou_attaquer_directement(c.getContenu()).getC2()!= ActionImpossible && peut_avancer_ou_attaquer_directement(c.getContenu()).getC1()==true){ //Test si on peut avancer avec la carte c
										x = main.getNombreCarteGroupe(distance - c.getContenu())+1;
										m = 5-main.getNombreCarteGroupe(distance - c.getContenu()) -defausse.getNombreCarteGroupe(distance - c.getContenu()) ;
										if(distance == 2*c.getContenu()&&x>0){
											x--;
										}
										if((distance - c.getContenu())>5){
											Proba = 0.0;
										}else{
											Proba = ProbaSup(x,n,m,a);
											System.out.println("Risque : "+ Proba +"\n");
										}
										
										if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
											sauv = Proba;
											action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
										}else{
											if(Proba<sauv){
												sauv = Proba;
												action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
											}
										}
									}
								}
							}
							
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
								boolean deja_passee = false;
								sans_risque = false;
								double sauv = 100;
								n = 25 -main.getNombreCarte() -defausse.getNombreCarte();
								a = n -pioche.getNombreCarte();
								for (Carte c : main.getMain()){
									if(peut_reculer(c.getContenu()) != ActionImpossible) { 
										x = main.getNombreCarteGroupe(distance + c.getContenu())+1;
										m = 5-main.getNombreCarteGroupe(distance + c.getContenu()) -defausse.getNombreCarteGroupe(distance + c.getContenu()) ;
										//if(attaque.getC3() == (distance + c.getContenu())){
										//	m = m -attaque.getC2();
										//}
										if((distance + c.getContenu())>5){
											Proba = 0.0;
										}else{
											Proba = ProbaSup(x,n,m,a);
											System.out.println("Risque : "+ Proba +"\n");
										}
											
										if(Proba <= risque){ //Si on calcule un risque de perdre au tour prochain < 0.2, on recule, on choisis, si plusieurs cartes, celle qui nous permet de reculer le moins possible 
											sans_risque = true;
											if(sauv==100){//Si on a calculer aucun mouvements a perte > 0.2 avant d'en calculer un < 0.2
												if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
													action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
												}else{
													if(action_jouee.getCarteDeplacement().getContenu() > c.getContenu()){
														action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
													}
												}
											}else{//Si on a calculer des mouvements a perte > 0.2 avant d'en calculer un < 0.2
												if(deja_passee){
													if(action_jouee.getCarteDeplacement().getContenu() > c.getContenu()){
														action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
													}
												}else{
													action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
													deja_passee = true;
												}
											}
										}else{
											if(!sans_risque){ //Tant qu'on a pas calculer un risque de perte < 0.2, on choisis de reculer avec la carte ayant le moins de chance de perdre au prochain tour
												if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
													sauv = Proba;
													action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
												}else{
													if(Proba<sauv){
														sauv = Proba;
														action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
													}
												}
											}	
										}
									}
								}
							}
							
						}
					
					}
					
				
				}
				
			}
		
			return action_jouee;
			
		}
		
		
		double ProbaSup(int x, int n, int m, int a) {
			double res = 0.0;
			
			for(int i=x; i<=5; i++){
				res = res + Proba(i,n,m,a);
			}
			
			return res;
		}
		
		double ProbaInf(int x, int n, int m, int a) {
			double res = 0.0;
			
			for(int i=x; i>=0; i--){
				res = res + Proba(i,n,m,a);
			}
			
			return res;
		}
		
		double Proba(int x, int n, int m, int a) {
			
			return 100.0*(Combinatoire(m,x)*Combinatoire((n-m),(a-x)))/Combinatoire(n,a);
		}
		
		double Combinatoire(int a, int b){
		
			return (Fact(a)/(Fact(b)*Fact(a-b)));	
		}
		double Fact(int a){
			double res = 1.0;
			
			if(a == 0){
				return 1.0;
			}
			
			for(int i=1; i<=a; i++){
				res = res*i;
			}
			
			return res;
		}*/

	}
	
	@Override
	public Joueur clone () {
		
		IADifficile joueur = new IADifficile(this.direction, this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}

	@Override
	public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}