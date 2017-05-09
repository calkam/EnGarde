package Modele.Joueur;

import Modele.Couple;
import Modele.Tour;
import Modele.Plateau.Piste;
import Modele.Plateau.Score;
import Modele.Tas.Carte;
import Modele.Tas.Main;

/**
 * @author gourdeaf
 *
 */
public abstract class Joueur {
	
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
	
	public boolean peut_executer_parade(int attaque, int nombre) {
		return main.getNombreCarteGroupe(attaque) >= nombre ;
	}
	
	abstract public int peut_reculer (int distance)  ;
	abstract public Couple<Boolean, Integer> peut_avancer_ou_attaquer_directement (int distance)  ;
	abstract public int peut_attaquer_indirectement (int deplacement, int portee)  ;
	abstract public void avancer (int distance)  ;
	abstract public void reculer (int distance)  ;
	abstract public void executer_attaque_indirecte (int deplacement, int portee, int nombre)  ;
	
	public ActionsJouables peutFaireAction(int est_attaque) {
		
		int position ;
		ActionsJouables actions_jouables = new ActionsJouables () ;
		
		for (Carte val_carte : main.getMain()) {
			
			if ((position = peut_reculer(val_carte.getContenu())) != ActionImpossible)
				
				actions_jouables.ajouterChoix(Reculer, position, val_carte, null) ;
		
			if (est_attaque == Tour.pasAttaque) {
					
				Couple <Boolean, Integer> test_avancer_ou_attaquer ;
				
				if ((test_avancer_ou_attaquer = peut_avancer_ou_attaquer_directement(val_carte.getContenu())).getC2() != ActionImpossible) {
					
					if (! test_avancer_ou_attaquer.getC1())
						
						actions_jouables.ajouterChoix(AttaqueDirecte, test_avancer_ou_attaquer.getC2(), val_carte, null) ;
					
					else {
						
						actions_jouables.ajouterChoix(Avancer, test_avancer_ou_attaquer.getC2(), val_carte, null) ;
					
						for (Carte c : main.getMain()) {
							
							int position_attaque_indirecte ;
					
							if ((position = test_avancer_ou_attaquer.getC2()) != ActionImpossible && 
									(position_attaque_indirecte = peut_attaquer_indirectement(val_carte.getContenu(), c.getContenu())) != ActionImpossible)
						
								actions_jouables.ajouterChoix(AttaqueIndirecte, position + position_attaque_indirecte, val_carte, c) ;
							
						}
					}
				}	
			}
			
			else if (peut_executer_parade(val_carte.getContenu(), 1))
						
				actions_jouables.ajouterChoix(Parade, position, val_carte, null) ;		
		}
			
		return actions_jouables ;
			
	}
	
	public void ajouterCarteDansMain(Carte c){
		main.ajouter(c);
	}
	
	public Carte jouerUneCarte(int i) throws Exception{
		main.supprimer(main.getCarte(i));
		return main.getCarte(i); 
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
	public void setMain(Main main) {
		this.main = main;
	}
	public int getScore() {
		return score.getNbPoints();
	}
	public void setScore(int nbPoints) {
		this.score.setNbPoints(nbPoints);
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
		if (main == null) {
			if (other.main != null)
				return false;
		} else if (!main.equals(other.main))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (piste == null) {
			if (other.piste != null)
				return false;
		} else if (!piste.equals(other.piste))
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
	
	/*ArrayList<Action> lesActions;
	
	protected Joueur(String nom) {
		
		this.nom = nom ;
		lesActions = new ArrayList<>();
		lesActions.add(new AttaqueDirecte());
		lesActions.add(new AttaqueIndirecte());
		lesActions.add(new Parade());
		lesActions.add(new Retraite());
		lesActions.add(new Avancer());
		lesActions.add(new Reculer());
	}*/
	
	/**
	 * Met en surbrillance les cases correpondantes à des actions possibles
	 * en fonction de la carte sélectionnée dans la main du joueur, la position
	 * de sa figurine et de celle du joueur adverse
	 * 
	 * @param maCarte
	 */
	/*public void selectionCarte(Carte maCarte){
		Object figurineAdverse;
		
		for(Action a : lesActions){
			if(a.estPossible(maCarte, figurine, figurineAdverse)){
				Case casePossible = a.casePossible(maCarte, figurine, figurineAdverse);
				
				Piste.accepte(new Visiteur(){
					void visite(Case c){
						if(c.equals(casePossible)){
							// On met le flag de la case c à true pour mettre la case en surbrillance					
						}
					}
				});
			}
		}
	}*/
}