  package Modele.Joueur;

import Modele.Tas.Main;
import Modele.Couple;
import Modele.Plateau.Piste;

public class JoueurGauche extends Joueur {
	
	public JoueurGauche(String nom, Main main, Piste piste) {
		super(nom, main, piste) ;
		main.setPosition(Main.gauche);
		
	}
	
	private boolean estlibre (int position) {
		
		return position != piste.getFigurineDroite().getPosition() ;
		
	}

	private boolean avancer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + distance ;
		
		return position_arrivee <= piste.getFigurineDroite().getPosition() ;
	}
	
	@Override
	public int peut_reculer(int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() - distance ;
		
		if(piste.estDansPiste(position_arrivee))
			
			return position_arrivee ;
		
		return ActionImpossible ;
		
	}

	@Override
	public Couple <Boolean, Integer> peut_avancer_ou_attaquer_directement(int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + distance ;
		
		if(!avancer_dans_piste (distance))
			
			return new Couple <> (null, ActionImpossible) ;
			
		if (estlibre(position_arrivee))
				
			return new Couple <> (true, position_arrivee) ;
			
		else
				
			return new Couple <> (false, position_arrivee) ;
		
	}

	@Override
	public int peut_attaquer_indirectement(int position_apres_deplacement, int portee) {

		int position_arrivee = position_apres_deplacement + portee ;
		
		if(! estlibre(position_arrivee))
		
			return position_arrivee;
		
		return ActionImpossible ;
	}

	@Override
	public void avancer(int distance) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() + distance) ;
		
	}

	@Override
	public void reculer(int distance) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() - distance) ;
		
	}

	@Override
	public void executer_attaque_indirecte(int deplacement, int portee, int nombre) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() + deplacement) ;
		
	}

	public int getPositionFigurine(){
		return this.getPiste().getFigurineGauche().getPosition();
	}

	@Override
	public void setPositionFigurine(int position) {
		// TODO Auto-generated method stub
		this.getPiste().getFigurineGauche().setPosition(position);
	}
	
	@Override
	public void reinitialiserPositionFigurine() {
		// TODO Auto-generated method stub
		this.setPositionFigurine(1);
	}
	
}
