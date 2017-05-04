package Modele;

import Modele.Joueur.Joueur;

public class Tour implements ActionOffensive{

	Joueur joueur;
	
	
	@Override
	public int peut_avancer(int distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int peut_reculer(int distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int peut_executer_attaque_directe(int portee) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int peut_executer_attaque_indirecte(int deplacement, int portee) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void avancer(Carte distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reculer(Carte distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executer_attaque_directe(Carte portee, int nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executer_attaque_indirecte(Carte deplacement, Carte portee, int nombre) {
		// TODO Auto-generated method stub
		
	}
	
}