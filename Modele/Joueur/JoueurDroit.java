package Modele.Joueur;

import Modele.Couple;
import Modele.Plateau.Piste;
import Modele.Tas.Main;

public class JoueurDroit extends Joueur {

	public JoueurDroit(String nom, Main main, Piste piste) {
		super(nom, main, piste) ;
	}
	
	// INUTILISEE
	/*private boolean deplacer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineDroite().getPosition() - distance ;
		
		return piste.estdansPiste(position_arrivee) ;
		
	}*/
	
	private boolean estlibre (int position) {
		
		return position != piste.getFigurineGauche().getPosition() ;
		
	}

	private boolean avancer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineDroite().getPosition() - distance ;
		
		//C'EST DE LA MERDE EGALEMENT return deplacer_dans_piste(position_arrivee) && position_arrivee >= piste.getFigurineDroite().getPosition() ;
		
		return position_arrivee >= piste.getFigurineGauche().getPosition() ;		
	}
	
	@Override
	public int peut_reculer(int distance) {
		
		int position_arrivee = piste.getFigurineDroite().getPosition() + distance ;
		
		if(piste.estdansPiste(position_arrivee))
			
			return position_arrivee ;
		
		return ActionImpossible ;
	}

	@Override
	public Couple <Boolean, Integer> peut_avancer_ou_attaquer_directement(int distance) {
		
		int position_arrivee = piste.getFigurineDroite().getPosition() - distance ;
		
		if(!avancer_dans_piste (distance))
			
			return new Couple <> (null, ActionImpossible) ;
			
		if (estlibre(position_arrivee))
				
			return new Couple <> (true, position_arrivee) ;
			
		else
				
			return new Couple <> (false, position_arrivee) ;
		
	}

	@Override
	public int peut_attaquer_indirectement(int position_apres_deplacement, int portee) {

		int position_arrivee = position_apres_deplacement - portee ;
		
		if(! estlibre(position_arrivee))
		
			return position_arrivee;
		
		return ActionImpossible ;
	}
	
	@Override
	public void avancer(int distance) {
		
		piste.getFigurineDroite().setPosition(piste.getFigurineDroite().getPosition() - distance) ;
		
	}

	@Override
	public void reculer(int distance) {
		
		piste.getFigurineDroite().setPosition(piste.getFigurineDroite().getPosition() + distance) ;
		
	}

	@Override
	public void executer_attaque_indirecte(int deplacement, int portee, int nombre) {
		
		piste.getFigurineDroite().setPosition(piste.getFigurineDroite().getPosition() - deplacement) ;
		
	}
	
	public int getPositionFigurine(){
		return this.getPiste().getFigurineDroite().getPosition();
	}
	
}
