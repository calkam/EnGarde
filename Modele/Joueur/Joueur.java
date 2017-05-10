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
	abstract public int getPositionFigurine() ;
	
	public ActionsJouables peutFaireAction(Couple<Integer, Integer> est_attaque) {
		
		int position ;
		ActionsJouables actions_jouables = new ActionsJouables () ;
		Carte carte;
		Carte carteOpt;
		
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
							actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueDirecte, test_avancer_ou_attaquer.getC2(), null, carte, j);
						}
							
					} else {
						
						actions_jouables.ajouterActionNeutre(carte.getID(), Avancer, test_avancer_ou_attaquer.getC2(), carte) ;
					
						for (int j=0; j<main.getNombreCarte(); j++) {
							if(i!=j){
								carteOpt = main.getCarte(j);
								
								int position_attaque_indirecte ;
						
								if (((position = test_avancer_ou_attaquer.getC2()) != ActionImpossible) && 
								    ((position_attaque_indirecte = peut_attaquer_indirectement(position, carteOpt.getContenu())) != ActionImpossible)){
							
									for(int k=1; k<=main.getNombreCarteGroupe(carte.getContenu()); k++){
										actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueIndirecte, position + position_attaque_indirecte, carte, carteOpt, k+1) ;
									}
									
								}
							}
						}
						
					}
				}
			}else{
				
				if(peut_executer_parade(carte.getContenu(), est_attaque.getC2())){
					actions_jouables.ajouterActionDefensive(carte.getID(), Parade, getPositionFigurine(), null, carte, est_attaque.getC2());
				}
				
				if(est_attaque.getC1() == Tour.attaqueIndirect){
					if((position = peut_reculer(carte.getContenu())) != ActionImpossible){
						actions_jouables.ajouterActionDefensive(carte.getID(), Parade, position, carte, null, 1);
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
	public void setMain(Main main) {
		this.main = main;
	}
	public int getScore() {
		return score.getNbPoints();
	}
	public void setScore(int nbPoints) {
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