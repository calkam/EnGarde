package Modele.Joueur.IA;

import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionDefensive;
import Modele.Joueur.ActionNeutre;
import Modele.Joueur.ActionOffensive;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Main;

public class IAMoyen extends IA {

	public IAMoyen(int direction, String nom, Main main, Piste piste) {
		super(direction, nom, main, piste);
	}
	
	public Action actionIA (Tour tour) throws Exception {
		
		Action action_jouee = new ActionNeutre(Reculer,0,22,new Carte(5))  ;
		int nbcartes = Integer.MIN_VALUE ;
		int distance = distanceFigurines () ;
		int surplus = 0;
		
		if (tour.getEstAttaque().getC1() == 1) { //Attaque directe subie
			for (Carte c : main.getMain()){
				if(c.getContenu() == tour.getEstAttaque().getC3()){
					action_jouee = new ActionDefensive(Parade,tour.getEstAttaque().getC2(), getPositionDeMaFigurine() ,null,c);
				}
			}
		}
		
		if (tour.getEstAttaque().getC1() == 2) { //Attaque indirecte subie
			
			if ( 2*(main.getNombreCarteGroupe(distance)) < (5 - tour.getDefausse().getNombreCarteGroupe(distance)) || main.getNombreCarteGroupe(tour.getEstAttaque().getC3()) <= tour.getEstAttaque().getC2() ) {
				//Si je ne peux pas parer, ou bien que je peux parer mais que je peux perde en attaquant directement juste après
				action_jouee = ReculerPlus5(distance, tour.getDefausse()); //On regarde si on peux reculer a une distance >= 6
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on a aucune carte permettant de reculer a une distance >= 6
					
					action_jouee = TrouverCarteMoinsRisquee(distance, false, tour.getDefausse()); //On choisis alors de reculer la ou le risque de perde est le moins élevé
				}
				
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peut pas reculer, c'est que l'on dois parer et etre en état de panique après, courage !
					
					for (Carte c : main.getMain()){
						if(c.getContenu() == tour.getEstAttaque().getC3()){
							action_jouee = new ActionDefensive(Parade,tour.getEstAttaque().getC2(), getPositionDeMaFigurine() ,null,c);
						}
					}
				}
			}else{ //On decide de parer l'tour.getEstAttaque() indirecte si on peut tour.getEstAttaque()r directement sans rique après, et qu'on a au moins une carte pour l'tour.getEstAttaque() !
				

				for (Carte c : main.getMain()){
					if(c.getContenu() == tour.getEstAttaque().getC3()){
						action_jouee = new ActionDefensive(Parade,tour.getEstAttaque().getC2(), getPositionDeMaFigurine() ,null,c);
					}
				}
				//action_jouee = new ActionDefensive(Parade,tour.getEstAttaque().getC2(),piste.getFigurineDroite().getPosition(),null,tour.getEstAttaque().getC1());
				//Dans ce cas, on doit directement relancer l'IA pour décider de son coup après parade !
			}
			
		}
		
		if(tour.getEstAttaque().getC1() == pasAttaque){ //Si on a pas encore decider du coup a jouer (si on a pas subie d'tour.getEstAttaque() indirecte/directe)
			
			if(tour.getPioche().getNombreCarte() == 1){ //Si il n'y a plus qu'une carte dans la pioche au debut de mon tour
				
				for (Carte c : main.getMain()) {//On regarde dans le cas ou on est plus avancer que l'adversaire sur la piste, si on peut reculer a une distance >=6 pour gagner directement
					
					if( (distance + c.getContenu()) >= 6 && Math.abs(12-(getPositionDeMaFigurine() - direction * c.getContenu())) <= Math.abs(12-getPositionFigurineAdverse()) ){
						action_jouee = new ActionNeutre (Reculer,0, getPositionDeMaFigurine() - direction * c.getContenu(),c);
					}
				
				}
				
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
					// Si la pioche est vide, mais qu'on ne peux pas fuir et gagner comme le roi des voleurs
					
					action_jouee = TrouverCarteMoinsRisquee(distance, true, tour.getDefausse());
					
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
						
						action_jouee = TrouverCarteMoinsRisquee(distance, false, tour.getDefausse());
					}
				}
				
			}else{ //Si la pioche n'est pas bientot vide
				int nbcartedist = 0;
				int nbcartedistattaque = 0;
				
				for (Carte c : main.getMain()) {
					if(c.getContenu() == distance){
						nbcartedist++;
					}
				}
				for (Carte c : tour.getDefausse().getD()) {
					if(c.getContenu() == distance){
						nbcartedistattaque++;
					}
				}
					if(nbcartedist >= 3 || (nbcartedist == 2 && nbcartedistattaque >= 1 )
							|| (nbcartedist == 1 && nbcartedistattaque >= 3 ) ){
						//teste si l'tour.getEstAttaque() directe est SANS risques, on tour.getEstAttaque() le cas echeant !
						for (Carte c : main.getMain()) {
							if(c.getContenu() == distance){
								action_jouee = new ActionOffensive(AttaqueDirecte,main.getNombreCarteGroupe(distance), getPositionDeMaFigurine() ,null,c); 
							}
						}
						//On tour.getEstAttaque() directement avec toutes les cartes de valeur distance présentes dans la main
					}
				
				
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peut pas tour.getEstAttaque()r directement (sous condition), on teste si on peut tour.getEstAttaque()r directement
					
					for(int i=0; i<main.getNombreCarte(); i++){ //Pour chaque carte de la main
						for(int j=0; j<main.getNombreCarte(); j++){ //On regarde pour les 4 autres cartes
							if(i!=j){
								if((main.getCarte(i).getContenu() + main.getCarte(j).getContenu()) == distance ){ //Si on a (c1+c2)=distance --> tour.getEstAttaque() indirecte possible
									if( (main.getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 3) || 
											(main.getNombreCarteGroupe(main.getCarte(j).getContenu()) == 2 && tour.getDefausse().getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 2 ) ||
											(main.getNombreCarteGroupe(main.getCarte(j).getContenu()) == 1 && tour.getDefausse().getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 3 ) ){
										action_jouee = new ActionOffensive(AttaqueIndirecte,main.getNombreCarteGroupe(main.getCarte(j).getContenu()), getPositionFigurine(MaFigurine) + direction * main.getCarte(i).getContenu(),main.getCarte(i),main.getCarte(j));
										//On tour.getEstAttaque() indirectement si on est sur de ne pas perdre au prochain tour
									}
											
								}
							}
						}
						
					}
				}
				
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
					//Si on a decider de ne pas tour.getEstAttaque()r indirectement, on dois alors choisir la derniere action possible : le deplacement uniquement 
					System.out.println("RENTRER\n");
					nbcartes = -100;
					
					for (Carte c : main.getMain()) { //On teste si on peut avancer à une case 'non-mortelle'
						surplus = 0;
						if(distance == 2*c.getContenu()){
							surplus = 1;
						}
						if ( (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + tour.getDefausse().getNombreCarteGroupe(distance - c.getContenu()) -5) >= 0 || (distance - c.getContenu()) >= 6 ){
							if (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + tour.getDefausse().getNombreCarteGroupe(distance - c.getContenu()) -5 > nbcartes || (distance - c.getContenu()) >= 6 ) {
								//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
								//Tel que nb de cette carte dans main + tour.getDefausse() est maximal !
								nbcartes = 2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + tour.getDefausse().getNombreCarteGroupe(distance - c.getContenu()) -5;
								action_jouee = new ActionNeutre (Avancer,0, getPositionDeMaFigurine() + direction * c.getContenu(),c);
								//On renvoie comme action la carte jouée, et on avance
								
							}
							
						}
					}
					
					
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peut avancer a une case non-mortelle, on regarde si on peut reculer a une distance >= 6
						action_jouee = ReculerPlus5(distance, tour.getDefausse());
					}
					
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peux pas avancer a une case non-mortelle, ni reculer a une distance >= 6 
								
							action_jouee = TrouverCarteMoinsRisquee(distance, false, tour.getDefausse());
							
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
								//Si on est obliger d'avancer a une case potentiellement mortelle
								action_jouee = TrouverCarteMoinsRisquee(distance, true, tour.getDefausse());
						}
					}
				}
			}
		}
	
		return action_jouee;
		
	}
	
	
	Action TrouverCarteMoinsRisquee(int distance, boolean avancer, Defausse defausse) throws Exception{
		
		int nbcartes = -100;
		Action action_jouee = new ActionNeutre(Reculer,0,22,new Carte(5))  ;
		int surplus = 0;
		
		for (Carte c : main.getMain()) {
			surplus = 0;
			
			if(!avancer){
				if(peut_reculer(c.getContenu()) != ActionImpossible) { //Test si on peut reculer avec la carte c
					
					if (2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu())-5 > nbcartes) {
						//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
						//Tel que nb de cette carte dans main + tour.getDefausse() est maximal !
						
						nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu())-5;
						action_jouee = new ActionNeutre (Reculer,0, getPositionDeMaFigurine() - direction * c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on recule
						
					}
				
				}
			}else{
				if(peut_avancer_ou_attaquer_directement(c.getContenu()).getC2() != ActionImpossible && peut_avancer_ou_attaquer_directement(c.getContenu()).getC1() ){ //Test si on peut avancer avec la carte c
					
					if( distance == 2*c.getContenu() ){ //Attention, ne pas compter la carte avec laquelle on tour.getEstAttaque() si elle est égale a la distance après coup !
						surplus = 1;
					}
					
					if (2*(main.getNombreCarteGroupe(c.getContenu())-surplus) + defausse.getNombreCarteGroupe(c.getContenu()) -5> nbcartes) {
						//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
						//On choisis la carte avec le moins de risques de perdre au tour adverse !
						
						nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())-surplus) + defausse.getNombreCarteGroupe(c.getContenu())-5;
						action_jouee = new ActionNeutre (Avancer,0, getPositionDeMaFigurine() + direction * c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on avance
						
					}
				
				}
			}
		}
		
		return action_jouee;
	}
	
	Action ReculerPlus5(int distance, Defausse defausse) throws Exception{
		
		int nbcartes = -100;
		Action action_jouee = new ActionNeutre(Reculer,0,22,new Carte(5))  ;
		
		for (Carte c : main.getMain()) {
			
			if(peut_reculer(c.getContenu()) != ActionImpossible) { //Test si on peut reculer avec la carte c
				
				if (distance + c.getContenu() >= 6) { //Teste si on peut reculer a une distance >= 6 avec la carte c
					
					if (2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu())-5 > nbcartes) {
						//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
						//Tel que nb de cette carte dans main + tour.getDefausse() est maximal !
						
						nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu())-5;
						action_jouee = new ActionNeutre (Reculer,0, getPositionDeMaFigurine() - direction * c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on recule
						
					}
				}
				
			}
			
		}
		
		return action_jouee;
	}

	@Override
	public Joueur clone () {
		
		IAMoyen joueur = new IAMoyen(this.direction, this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}
	
}

