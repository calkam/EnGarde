package Modele.Joueur;
import Modele.Joueur.Action ;
import Modele.Tas.Main;
import Modele.Plateau.Piste;

public class JoueurGauche extends Joueur implements Action {
	
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
	public int peut_avancer(int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + distance ;
		
		if(avancer_dans_piste (position_arrivee) && estlibre(position_arrivee))
			
			return position_arrivee ;
		
		return -1 ;
	}

	@Override
	public int peut_executer_attaque_directe(int portee) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + portee ;
		
		if(avancer_dans_piste (position_arrivee) && ! estlibre(position_arrivee))
		
			return position_arrivee ;
		
		return -1 ;
	}

	@Override
	public int peut_executer_attaque_indirecte(int deplacement, int portee) {

		int position_arrivee = piste.getFigurineGauche().getPosition() + deplacement ;
		
		if(peut_avancer (position_arrivee) != -1 && peut_executer_attaque_directe (position_arrivee + portee) != -1)
		
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
