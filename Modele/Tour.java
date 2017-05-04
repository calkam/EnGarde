package Modele;

import Modele.Joueur.Joueur;

public class Tour{

	Joueur joueurAttaquant;
	Joueur joueurDefenseur;
	
	public void initialiserTour(Joueur m_joueurAttaquant, Joueur m_joueurDefenseur){
		this.joueurAttaquant = m_joueurAttaquant;
		this.joueurAttaquant = m_joueurDefenseur;
	}
	
	public void jouerTour(){
		selectionnerCarte();
		choisirAction();
		executerAction();
	}
	
	public void selectionnerCarte(){
		
	}
	
	public void choisirAction(){
		
	}
	
	public void executerAction(){
		
	}
	
	/**
	 * GETTER/SETTER
	 */
	public Joueur getJoueurAttaquant() {
		return joueurAttaquant;
	}

	public void setJoueurAttaquant(Joueur joueurAttaquant) {
		this.joueurAttaquant = joueurAttaquant;
	}

	public Joueur getJoueurDefenseur() {
		return joueurDefenseur;
	}

	public void setJoueurDefenseur(Joueur joueurDefenseur) {
		this.joueurDefenseur = joueurDefenseur;
	}
	
}