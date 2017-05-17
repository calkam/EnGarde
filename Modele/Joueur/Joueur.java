package Modele.Joueur;

import java.util.ArrayList;

import Modele.Couple;
import Modele.Tour;
import Modele.Triplet;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Plateau.Piste;
import Modele.Plateau.Score;
import Modele.Tas.Carte;
import Modele.Tas.Main;

/**
 * @author gourdeaf
 *
 */
public abstract class Joueur implements Visitable{
	
	public final static int ActionImpossible = -1 ;
	public final static int Reculer = 0 ;
	public final static int Avancer = 1 ;
	public final static int AttaqueDirecte = 2 ;
	public final static int AttaqueIndirecte  = 3 ;
	public final static int Parade = 4 ;
	public final static int Fuite = 5 ;
	
	protected String nom ;
	protected Main main ;
	protected Piste piste ;
	protected Score score ;

	public Joueur(String nom, Main main, Piste piste) {
		this.nom = nom ;
		this.main = main;
		this.piste = piste;
		this.score = new Score();
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
        return main.accept(v);
	}
	
	public boolean peut_executer_parade(int valeurCarteMain, int nombreDeCartes, int valeurCarteAttaque) throws Exception {
		boolean b = false;
		
		if(valeurCarteMain == valeurCarteAttaque && main.getNombreCarteGroupe(valeurCarteMain) >= nombreDeCartes){
			b = true;			
		}	
		
		return b;
	}
	
	abstract public int peut_reculer (int distance)  ;
	abstract public Couple<Boolean, Integer> peut_avancer_ou_attaquer_directement (int distance)  ;
	abstract public int peut_attaquer_indirectement (int deplacement, int portee)  ;
	abstract public void avancer (int distance)  ;
	abstract public void reculer (int distance)  ;
	abstract public void executer_attaque_indirecte (int deplacement, int portee, int nombre)  ;
	
	abstract public int getPositionFigurine() ;
	abstract public void setPositionFigurine(int position) ;
	abstract public void reinitialiserPositionFigurine() ;
	abstract public void viderLaMain(); 
	
	public ActionsJouables peutFaireAction(int cote, ArrayList<Carte> cartes, Triplet<Integer, Integer, Integer> est_attaque) throws Exception {
		
		int position ;
		ActionsJouables actions_jouables = new ActionsJouables () ;
		Carte carte;
		Carte carteOpt;
		
		Main main = new Main();
		main.setCote(cote);
		main.setMain(cartes);
		
		for(int i=0; i<main.getNombreCarte(); i++){
			
			carte = main.getCarte(i);
			
			if(est_attaque.getC1() == Tour.pasAttaque){
				Couple <Boolean, Integer> test_avancer_ou_attaquer ;
				
				if((position = peut_reculer(carte.getContenu())) != ActionImpossible){
					actions_jouables.ajouterActionNeutre(carte.getID(), Reculer, position, carte) ;
				}
				
				if ((test_avancer_ou_attaquer = peut_avancer_ou_attaquer_directement(carte.getContenu())).getC2() != ActionImpossible) {
					
					if (!test_avancer_ou_attaquer.getC1()) {
						
						for(int j=1; j<=main.getNombreCarteGroupe(carte.getContenu()); j++){
							actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueDirecte, getPositionFigurine(), null, carte, j);
						}
							
					} else {
						
						actions_jouables.ajouterActionNeutre(carte.getID(), Avancer, test_avancer_ou_attaquer.getC2(), carte) ;
					
						for (int j=0; j<main.getNombreCarte(); j++) {
							if(i!=j){
								carteOpt = main.getCarte(j);
						
								if (((position = test_avancer_ou_attaquer.getC2()) != ActionImpossible) && 
								    (peut_attaquer_indirectement(position, carteOpt.getContenu()) != ActionImpossible)){
							
									for(int k=1; k<=main.getNombreCarteGroupe(carteOpt.getContenu()); k++){
										actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueIndirecte, test_avancer_ou_attaquer.getC2(), carte, carteOpt, k) ;
									}								
								}
							}
						}
						
					}
				}
			}else{
				
				// On a oublié de tester que la valeur de la carte utilisée pour parer est la même que celle du joueur adverse utilisée pour son attaque 				
				if(peut_executer_parade(carte.getContenu(), est_attaque.getC2(), est_attaque.getC3())){
					actions_jouables.ajouterActionDefensive(carte.getID(), Parade, getPositionFigurine(), null, carte, est_attaque.getC2());
				}
				
				if(est_attaque.getC1() == Tour.attaqueIndirect){
					if((position = peut_reculer(carte.getContenu())) != ActionImpossible){
						actions_jouables.ajouterActionDefensive(carte.getID(), Fuite, position, carte, null, 0);
					}
				}
				
			}
		}
			
		return actions_jouables ;
			
	}
	
	public void ajouterCarteDansMain(Carte c){
		main.ajouter(c);
	}
	
	public void defausserUneCarte(Carte c) throws Exception{
		main.supprimer(c);
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Main getMain() {
		return main;
	}

	public ArrayList<Carte> getCartesDeLaMain() {
		return main.getMain();
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	
	public int getNbPoints() {
		return score.getNbPoints();
	}
	public void setNbPoints(int nbPoints) {
		this.score.setNbPoints(nbPoints);
	}
	
	public Piste getPiste() {
		return piste;
	}

	public void setPiste(Piste piste) {
		this.piste = piste;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String str = "";
		str += "Joueur [\n";
		str += "  nom= " + nom + ",\n";
		str += "  " + main;
		str += "  score=" + score + ",\n";
		str += "]\n";
		return str;
	}
}