package Modele.Joueur.IA;

import Modele.Triplet;
import Modele.Joueur.Action;
import Modele.Joueur.ActionDefensive;
import Modele.Joueur.ActionNeutre;
import Modele.Joueur.ActionOffensive;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;

public class IAMoyenDroit extends IADroite {

	public IAMoyenDroit(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}

	
	
public Action actionIA (Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse) throws Exception {
		
		Action action_jouee = new ActionNeutre(Reculer,0,22,new Carte(5))  ;
		int nbcartes = -100 ;
		int distance = piste.getFigurineDroite().getPosition() - piste.getFigurineGauche().getPosition();
		int surplus = 0;
		
		if (attaque.getC1() == 1) { //Attaque directe subie
			for (Carte c : main.getMain()){
				if(c.getContenu() == attaque.getC3()){
					action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineDroite().getPosition(),null,c);
				}
			}
		}
		
		if (attaque.getC1() == 2) { //Attaque indirecte subie
			
			if ( 2*(main.getNombreCarteGroupe(distance)) < (5 - defausse.getNombreCarteGroupe(distance)) || main.getNombreCarteGroupe(attaque.getC3()) <= attaque.getC2() ) {
				//Si je ne peux pas parer, ou bien que je peux parer mais que je peux perde en attaquant directement juste après
				action_jouee = ReculerPlus5(distance, defausse); //On regarde si on peux reculer a une distance >= 6
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on a aucune carte permettant de reculer a une distance >= 6
					
					action_jouee = TrouverCarteMoinsRisquee(distance, false, defausse); //On choisis alors de reculer la ou le risque de perde est le moins élevé
				}
				
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peut pas reculer, c'est que l'on dois parer et etre en état de panique après, courage !
					
					for (Carte c : main.getMain()){
						if(c.getContenu() == attaque.getC3()){
							action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineDroite().getPosition(),null,c);
						}
					}
				}
			}else{ //On decide de parer l'attaque indirecte si on peut attaquer directement sans rique après, et qu'on a au moins une carte pour l'attaque !
				

				for (Carte c : main.getMain()){
					if(c.getContenu() == attaque.getC3()){
						action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineDroite().getPosition(),null,c);
					}
				}
				//action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineDroite().getPosition(),null,attaque.getC1());
				//Dans ce cas, on doit directement relancer l'IA pour décider de son coup après parade !
			}
			
		}
		
		if(attaque.getC1() == pasAttaque){ //Si on a pas encore decider du coup a jouer (si on a pas subie d'attaque indirecte/directe)
			
			if(pioche.getNombreCarte() == 1){ //Si il n'y a plus qu'une carte dans la pioche au debut de mon tour
				
				for (Carte c : main.getMain()) {//On regarde dans le cas ou on est plus avancer que l'adversaire sur la piste, si on peut reculer a une distance >=6 pour gagner directement
					
					if( (distance + c.getContenu()) >=6 && Math.abs(12-(piste.getFigurineDroite().getPosition() + c.getContenu() ))<=Math.abs(12-piste.getFigurineGauche().getPosition()) ){
						action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
					}
				
				}
				
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
					// Si la pioche est vide, mais qu'on ne peux pas fuir et gagner comme le roi des voleurs
					
					action_jouee = TrouverCarteMoinsRisquee(distance, true, defausse);
					
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){
						
						action_jouee = TrouverCarteMoinsRisquee(distance, false, defausse);
					}
				}
				
			}else{ //Si la pioche n'est pas bientot vide
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
					if(nbcartedist >= 3 || (nbcartedist == 2 && nbcartedistdefausse >= 1 )
							|| (nbcartedist == 1 && nbcartedistdefausse >= 3 ) ){
						//teste si l'attaque directe est SANS risques, on attaque le cas echeant !
						for (Carte c : main.getMain()) {
							if(c.getContenu() == distance){
								action_jouee = new ActionOffensive(AttaqueDirecte,main.getNombreCarteGroupe(distance),piste.getFigurineDroite().getPosition(),null,c); 
							}
						}
						//On attaque directement avec toutes les cartes de valeur distance présentes dans la main
					}
				
				
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peut pas attaquer directement (sous condition), on teste si on peut attaquer directement
					
					for(int i=0; i<main.getNombreCarte(); i++){ //Pour chaque carte de la main
						for(int j=0; j<main.getNombreCarte(); j++){ //On regarde pour les 4 autres cartes
							if(i!=j){
								if((main.getCarte(i).getContenu() + main.getCarte(j).getContenu()) == distance ){ //Si on a (c1+c2)=distance --> attaque indirecte possible
									if( (main.getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 3) || 
											(main.getNombreCarteGroupe(main.getCarte(j).getContenu()) == 2 && defausse.getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 2 ) ||
											(main.getNombreCarteGroupe(main.getCarte(j).getContenu()) == 1 && defausse.getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 3 ) ){
										action_jouee = new ActionOffensive(AttaqueIndirecte,main.getNombreCarteGroupe(main.getCarte(j).getContenu()),piste.getFigurineDroite().getPosition() - main.getCarte(i).getContenu(),main.getCarte(i),main.getCarte(j));
										//On attaque indirectement si on est sur de ne pas perdre au prochain tour
									}
											
								}
							}
						}
						
					}
				}
				//System.out.println("AAAAAAA\n");
				if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
					//Si on a decider de ne pas attaquer indirectement, on dois alors choisir la derniere action possible : le deplacement uniquement 
					System.out.println("RENTRER\n");
					nbcartes = -100;
					/*for (Carte c : main.getMain()) { //Test si on peut avancer a une case non-mortelle
						surplus = 0;
						if(distance == 2*c.getContenu()){
							surplus = 1;
						}
						if( peut_avancer_ou_attaquer_directement(c.getContenu()).getC1() ){ //Test si on peut avancer avec la carte c
							if((distance - c.getContenu()) >= 6){
								if (2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu()) -5 > nbcartes) {
									nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu()) -5;
									action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
								}else if(2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu()) -5 == nbcartes){
									if(action_jouee.getCarteDeplacement().getContenu()<c.getContenu()){
										action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
									}
								}
							}
						}
					}*/
					for (Carte c : main.getMain()) { //On teste si on peut avancer à une case 'non-mortelle'
						surplus = 0;
						if(distance == 2*c.getContenu()){
							surplus = 1;
						}
						if ( (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5) >= 0 || (distance - c.getContenu()) >= 6 ){
							if (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5 > nbcartes || (distance - c.getContenu()) >= 6 ) {
								//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
								//Tel que nb de cette carte dans main + defausse est maximal !
								nbcartes = 2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5;
								action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
								//On renvoie comme action la carte jouée, et on avance
								
							}
							
						}
					}
					
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peut avancer a une case non-mortelle, on regarde si on peut reculer a une distance >= 6
						action_jouee = ReculerPlus5(distance, defausse);
					}
					
					/*if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
						System.out.println("RENTRER142146142164\n");
						
						//Si on n'a pas de carte permettant de reculer a une distance >= 6
						for (Carte c : main.getMain()) { //On teste si on peut avancer à une case 'non-mortelle'
							surplus = 0;
							if(distance == 2*c.getContenu()){
								surplus = 1;
							}
							if ( (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5) >= 0 ){
								if (2*(main.getNombreCarteGroupe(c.getContenu())-surplus) + defausse.getNombreCarteGroupe(c.getContenu()) -5 > nbcartes) {
									//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
									//Tel que nb de cette carte dans main + defausse est maximal !
									nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())-surplus) + defausse.getNombreCarteGroupe(c.getContenu()) -5;
									action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
									//On renvoie comme action la carte jouée, et on avance
									
								}
								
							}
						}
					}
					*/
					if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ //Si on ne peux pas avancer a une case non-mortelle, ni reculer a une distance >= 6 
								
							action_jouee = TrouverCarteMoinsRisquee(distance, false, defausse);
							
							if((action_jouee.equals(new ActionNeutre(Reculer,0,22,new Carte(5))))){ 
								//Si on est obliger d'avancer a une case potentiellement mortelle
								action_jouee = TrouverCarteMoinsRisquee(distance, true, defausse);
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
						//Tel que nb de cette carte dans main + defausse est maximal !
						
						nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu())-5;
						action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on recule
						
					}
				
				}
			}else{
				if( peut_avancer_ou_attaquer_directement(c.getContenu()).getC1() ){ //Test si on peut avancer avec la carte c
					
					if( distance == 2*c.getContenu() ){ //Attention, ne pas compter la carte avec laquelle on attaque si elle est égale a la distance après coup !
						surplus = 1;
					}
					
					if (2*(main.getNombreCarteGroupe(c.getContenu())-surplus) + defausse.getNombreCarteGroupe(c.getContenu()) -5> nbcartes) {
						//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
						//On choisis la carte avec le moins de risques de perdre au tour adverse !
						
						nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())-surplus) + defausse.getNombreCarteGroupe(c.getContenu())-5;
						action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineDroite().getPosition()-c.getContenu(),c);
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
						//Tel que nb de cette carte dans main + defausse est maximal !
						
						nbcartes = 2*(main.getNombreCarteGroupe(c.getContenu())) + defausse.getNombreCarteGroupe(c.getContenu())-5;
						action_jouee = (Action) new ActionNeutre (Reculer,0,piste.getFigurineDroite().getPosition()+c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on recule
						
					}
				}
				
			}
			
		}
		
		return action_jouee;
	}



	@Override
	public Joueur clone () {
		
		IAMoyenDroit joueur = new IAMoyenDroit(this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}
	
	
	
}

