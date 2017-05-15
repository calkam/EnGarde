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

public class IADifficileGauche extends IAGauche {

	public IADifficileGauche(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}
	
	abstract public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse) throws Exception;
	abstract public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse) throws Exception {
		
		Action action_jouee = new ActionNeutre(Avancer,0,22,new Carte(5))  ;
		int nbcartes = -100 ;
		int distance = piste.getFigurineDroite().getPosition() - piste.getFigurineGauche().getPosition();
		int surplus = 0;
		
		if (attaque.getC1() == AttaqueIndirecte) { //Attaque indirecte subie
			
			if ( 2*(main.getNombreCarteGroupe(distance)) < (5 - defausse.getNombreCarteGroupe(distance)) || main.getNombreCarteGroupe(attaque.getC3()) < attaque.getC2() ) {
				//Si je ne peux pas parer, ou bien que je peux parer mais que je peux perde en attaquant directement juste après
				
				action_jouee = ReculerPlus5(distance, defausse); //On regarde si on peux reculer a une distance >= 6
				
				if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){ //Si on a aucune carte permettant de reculer a une distance >= 6
					
					action_jouee = TrouverCarteMoinsRisquee(distance, false, defausse); //On choisis alors de reculer la ou le risque de perde est le moins élevé
				}
				
			}else{ //On decide de parer l'attaque indirecte si on peut attaquer directement sans rique après
				
				for (Carte c : main.getMain()){
					if(c.getContenu() == attaque.getC3()){
						action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineGauche().getPosition(),null,c);
					}
				}
				
				//action_jouee = new ActionDefensive(Parade,attaque.getC2(),piste.getFigurineGauche().getPosition(),null,attaque.getC1());
				//Dans ce cas, on doit directement relancer l'IA pour décider de son coup après parade !
			}
			
		}
		
		if(attaque.getC1() == pasAttaque){ //Si on a pas encore decider du coup a jouer (si on a pas subie d'attaque indirecte/directe)
			
			if(defausse.getNombreCarte() == 1){ //Si il n'y a plus qu'une carte dans la pioche au début de mon tour
				
				for (Carte c : main.getMain()) {//On regarde dans le cas ou on est plus avancer que l'adversaire sur la piste, si on peut reculer a une distance >=6 pour gagner directement
					
					if( (distance + c.getContenu()) >=6 && Math.abs(12-(piste.getFigurineGauche().getPosition() - c.getContenu() ))<=Math.abs(12-piste.getFigurineDroite().getPosition()) ){
						action_jouee = (Action) new ActionNeutre (Reculer,0,piste.getFigurineGauche().getPosition()-c.getContenu(),c);
					}
				
				}
				
				if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){ 
					// Si la pioche est vide, mais qu'on ne peux pas fuir et gagner comme le roi des voleurs
					
					action_jouee = TrouverCarteMoinsRisquee(distance, true, defausse);
					
					if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){
						
						action_jouee = TrouverCarteMoinsRisquee(distance, false, defausse);
					}
				}
				
			}else{ //Si la pioche n'est pas vide
				
				if( !peut_avancer_ou_attaquer_directement(distance).getC1() ){ //Si attaque directe possible avec une ou plusieurs cartes de la main
					if(main.getNombreCarteGroupe(distance) >= 3 || (main.getNombreCarteGroupe(distance) == 2 && defausse.getNombreCarteGroupe(distance) >= 1 )
							|| (main.getNombreCarteGroupe(distance) == 1 && defausse.getNombreCarteGroupe(distance) >= 3 ) ){
						//teste si l'attaque directe est SANS risques, on attaque le cas echeant !
						action_jouee = new ActionOffensive(AttaqueDirecte,main.getNombreCarteGroupe(distance),piste.getFigurineGauche().getPosition(),null,new Carte(distance)); 
						//On attaque directement avec toutes les cartes de valeur distance présentes dans la main
					}
				}
				
				if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){ //Si on ne peut pas attaquer directement (sous condition), on teste si on peut attaquer directement
					
					for(int i=0; i<main.getNombreCarte(); i++){ //Pour chaque carte de la main
						for(int j=0; j<main.getNombreCarte(); j++){ //On regarde pour les 4 autres cartes
							if(i!=j){
								if((main.getCarte(i).getContenu() + main.getCarte(j).getContenu()) == distance ){ //Si on a (c1+c2)=distance --> attaque indirecte possible
									if( (main.getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 3) || 
											(main.getNombreCarteGroupe(main.getCarte(j).getContenu()) == 2 && defausse.getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 2 ) ||
											(main.getNombreCarteGroupe(main.getCarte(j).getContenu()) == 1 && defausse.getNombreCarteGroupe(main.getCarte(j).getContenu()) >= 3 ) ){
										action_jouee = new ActionOffensive(AttaqueIndirecte,main.getNombreCarteGroupe(main.getCarte(j).getContenu()),piste.getFigurineGauche().getPosition() + main.getCarte(i).getContenu(),main.getCarte(i),main.getCarte(j));
										//On attaque indirectement si on est sur de ne pas perdre au prochain tour
									}
											
								}
							}
						}
						
					}
				}
				
				if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){ 
					//Si on a decider de ne pas attaquer indirectement, on dois alors choisir la derniere action possible : le deplacement uniquement 

					action_jouee = ReculerPlus5(distance, defausse);
					
					if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){ 
						//Si on n'a pas de carte permettant de reculer a une distance >= 6
						for (Carte c : main.getMain()) { //On teste si on peut avancer à une case 'non-mortelle'
							surplus = 0;
							if(distance == 2*c.getContenu()){
								surplus = 1;
							}
							if ( (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5) >= 0 ){
								if (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5 > nbcartes) {
									//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
									//Tel que nb de cette carte dans main + defausse est maximal !
									nbcartes = 2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5;
									action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineGauche().getPosition()+c.getContenu(),c);
									//On renvoie comme action la carte jouée, et on avance
									
								}
								
							}
						}
					}
					
					if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){ //Si on ne peux pas avancer a une case non-mortelle 
								
							action_jouee = TrouverCarteMoinsRisquee(distance, false, defausse);
							
							if((action_jouee.equals(new ActionNeutre(Avancer,0,22,new Carte(5))))){ 
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
		Action action_jouee = new ActionNeutre(Avancer,0,22,new Carte(5))  ;
		int surplus = 0;
		
		for (Carte c : main.getMain()) {
			surplus = 0;
			
			if(!avancer){
				if(peut_reculer(c.getContenu()) != ActionImpossible) { //Test si on peut reculer avec la carte c
					
					if (2*(main.getNombreCarteGroupe(distance + c.getContenu())) + defausse.getNombreCarteGroupe(distance + c.getContenu())-5 > nbcartes) {
						//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
						//Tel que nb de cette carte dans main + defausse est maximal !
						
						nbcartes = 2*(main.getNombreCarteGroupe(distance + c.getContenu())) + defausse.getNombreCarteGroupe(distance + c.getContenu())-5;
						action_jouee = new ActionNeutre (Reculer,0,piste.getFigurineGauche().getPosition()-c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on recule
						
					}
				
				}
			}else{
				if( peut_avancer_ou_attaquer_directement(distance).getC1() ){ //Test si on peut avancer avec la carte c
					
					if( distance == 2*c.getContenu() ){ //Attention, ne pas compter la carte avec laquelle on attaque si elle est égale a la distance après coup !
						surplus = 1;
					}
					
					if (2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu()) -5> nbcartes) {
						//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
						//On choisis la carte avec le moins de risques de perdre au tour adverse !
						
						nbcartes = 2*(main.getNombreCarteGroupe(distance - c.getContenu())-surplus) + defausse.getNombreCarteGroupe(distance - c.getContenu())-5;
						action_jouee = new ActionNeutre (Avancer,0,piste.getFigurineGauche().getPosition()+c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on avance
						
					}
				
				}
			}
		}
		
		return action_jouee;
	}
	
	Action ReculerPlus5(int distance, Defausse defausse) throws Exception{
		
		int nbcartes = -100;
		Action action_jouee = new ActionNeutre(Avancer,0,22,new Carte(5))  ;
		
		for (Carte c : main.getMain()) {
			
			if(peut_reculer(c.getContenu()) != ActionImpossible) { //Test si on peut reculer avec la carte c
				
				if (distance + c.getContenu() >= 6) { //Teste si on peut reculer a une distance >= 6 avec la carte c
		
					if (2*(main.getNombreCarteGroupe(distance + c.getContenu())) + defausse.getNombreCarteGroupe(distance + c.getContenu())-5 > nbcartes) {
						//Si plusieurs cartes permettent ce déplacement, on choisis celle qui a été le plus jouée :
						//Tel que nb de cette carte dans main + defausse est maximal !
						
						nbcartes = 2*(main.getNombreCarteGroupe(distance + c.getContenu())) + defausse.getNombreCarteGroupe(distance + c.getContenu())-5;
						action_jouee = (Action) new ActionNeutre (Reculer,0,piste.getFigurineGauche().getPosition()-c.getContenu(),c);
						//On renvoie comme action la carte jouée, et on recule
						
					}
				}
				
			}
			
		}
		
		return action_jouee;
	}


	@Override
	public Joueur clone() {
		// TODO Auto-generated method stub
		return null;
	}
}