package Modele.Joueur;

import java.util.ArrayList;

import Modele.Plateau.Piste;
import Modele.Tas.Carte;
import Modele.Tas.Main;

public class JoueurDroit extends Joueur implements Action {

	public JoueurDroit(String nom, Main main, Piste piste) {
		super(nom, main, piste) ;
	}
	
	private boolean deplacer_dans_piste (int distance) {
		
		int position_arrivee = piste.getFigurineGauche().getPosition() - distance ;
		
		return piste.estdansPiste(position_arrivee) ;
		
	}
	
	private boolean estlibre (int position) {
		
		return position != piste.getFigurineGauche().getPosition() ;
		
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
	public void avancer(int distance) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() - distance) ;
		
	}

	@Override
	public void reculer(int distance) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() + distance) ;
		
	}

	@Override
	public void executer_attaque_indirecte(int deplacement, int portee, int nombre) {
		
		piste.getFigurineGauche().setPosition(piste.getFigurineGauche().getPosition() - deplacement) ;
		
	}
	
	@Override
	public ArrayList <CartesDefaussees> peut_executer_action(int val_carte) {
		
		int position ;
		ArrayList <CartesDefaussees> choix_possibles = new ArrayList <> () ;
		
		if ((position = peut_reculer(val_carte)) != -1)
			
			choix_possibles.add(new CartesDefaussees ("Reculer", position)) ;
		
		if ((position = peut_avancer(val_carte)) != 1) {
			
			choix_possibles.add(new CartesDefaussees ("Avancer", position)) ;
			
			if ((position = peut_executer_attaque_directe(val_carte)) != 1)
				
				choix_possibles.add(new CartesDefaussees ("Attaque directe", position)) ;
			
			for (Carte c : main.getMain())
			
				if ((position = peut_executer_attaque_indirecte(val_carte, c.getContenu())) != 1)
				
					choix_possibles.add(new CartesDefaussees ("Attaque indirecte", position)) ;
		}
		
		return choix_possibles ;
		
	}

}
