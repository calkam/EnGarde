  package Modele.Joueur;

import Modele.Tas.Main;
import Modele.Couple;
import Modele.Plateau.Piste;

public class JoueurGauche extends Joueur {
	
	public JoueurGauche(String nom, Main main, Piste piste) {
		super(nom, main, piste) ;
		
	}
	
	private boolean deplacer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + distance ;
		
		return piste.estdansPiste(position_arrivee) ;
		
	}
	
	private boolean estlibre (int position) {
		
		return position != piste.getFigurineDroite().getPosition() ;
		
	}

	private boolean avancer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + distance ;
		
		return deplacer_dans_piste(position_arrivee) && position_arrivee <= piste.getFigurineDroite().getPosition() ;
		
	}
	
	@Override
	public int peut_reculer(int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() - distance ;
		
		if(deplacer_dans_piste (position_arrivee))
			
			return position_arrivee ;
		
		return -1 ;
		
	}

	@Override
	public Couple <Boolean, Integer> peut_avancer_ou_attaquer_directement(int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + distance ;
		
		if(!avancer_dans_piste (position_arrivee))
			
			return new Couple <> (null, -1) ;
			
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
		
		return -1 ;
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

}
