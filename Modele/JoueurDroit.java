package Modele;

import Modele.Joueur.Joueur;

public class JoueurDroit extends Joueur implements ActionOffensive {

	public JoueurDroit(String nom, Main main, Piste piste) {
		super(nom, main, piste) ;
	}
	
	private boolean estlibre (int position) {
		
		return position != piste.getFigurineDroite().getPosition() ;
		
	}

	private boolean avancer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() ;
		
		return deplacer_dans_piste(position_arrivee) && position_arrivee >= piste.getFigurineDroite().getPosition() ;
		
	}
	
	@Override
	public int peut_reculer(int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() + distance ;
		
		if(deplacer_dans_piste (position_arrivee))
			
			return position_arrivee ;
		
		return -1 ;
		
	}

	@Override
	public int peut_avancer(int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() - distance ;
		
		if(avancer_dans_piste (position_arrivee) && estlibre(position_arrivee))
			
			return position_arrivee ;
		
		return -1 ;
	}

	@Override
	public int peut_executer_attaque_directe(int portee) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() - portee ;
		
		if(avancer_dans_piste (position_arrivee) && ! estlibre(position_arrivee))
		
			return position_arrivee ;
		
		return -1 ;
	}

	@Override
	public int peut_executer_attaque_indirecte(int deplacement, int portee) {

		int position_arrivee = piste.getFigurineGauche().getPosition() - deplacement ;
		
		if(peut_avancer (position_arrivee) != -1 && peut_executer_attaque_directe (position_arrivee - portee) != -1)
		
			return position_arrivee;
		
		return -1 ;
	}
	
	@Override
	public void avancer(Carte distance) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() - distance.getContenu()) ;
		
	}

	@Override
	public void reculer(Carte distance) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() + distance.getContenu()) ;
		
	}

	@Override
	public void executer_attaque_directe(Carte portee, int nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executer_attaque_indirecte(Carte deplacement, Carte portee, int nombre) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() - deplacement.getContenu()) ;
		
	}

}
