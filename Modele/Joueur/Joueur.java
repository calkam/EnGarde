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
	
	// CONSTANTES
	
	// TYPES ACTION
	
	public final static int ActionImpossible = -1 ;
	public final static int Reculer = 0 ;
	public final static int Avancer = 1 ;
	public final static int AttaqueDirecte = 2 ;
	public final static int AttaqueIndirecte  = 3 ;
	public final static int Parade = 4 ;
	public final static int Fuite = 5 ;
	
	// TYPE JOUEUR
	// Le Joueur1 a pour direction la DirectionDroite (1)
	// Le Joueur2 a pour direction la DirectionGauche (-1)
	
	public final static int DirectionDroite = 1 ;
	public final static int DirectionGauche = -1 ;
	
	// FIGURINE JOUEUR
	// La Figurine du Joueur va dans la même direction que lui
	// La Figurine du Joueur adverse va dans la direction opposée du Joueur
	
	public final int MaFigurine  ;
	public final int FigurineAdverse ;
	
	// ATTRIBUTS
	
	protected int direction ;
=======
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
	protected String nom ;
	protected Main main ;
	protected Piste piste ;
	protected Score score ;
	
	// CONSTRUCTEUR

	public Joueur(int direction, String nom, Main main, Piste piste) {
		
		this.direction = direction ;
		this.MaFigurine  = direction ;
		this.FigurineAdverse = -direction ;
		this.nom = nom ;
		this.main = main;
		this.piste = piste;
		this.score = new Score();
	}
	
<<<<<<< HEAD
	// RACCOURCIS GETTER/SETTER POSITION
	
	public int getPositionFigurine(int direction) throws Exception{
		return getPiste().getFigurine(direction).getPosition();
	}

	public void setPositionFigurine(int direction, int position) throws Exception {
		
		getPiste().getFigurine(direction).setPosition(position);
	}
	
	// RÉ-INITIALISATION POSITION FIGURINE
	
	public void reinitialiserPositionFigurine() throws Exception {
		
		switch (direction) {
		
		case DROITE : setPositionFigurine(DROITE, 1) ; break ;
		case GAUCHE : setPositionFigurine(GAUCHE, 23); break ;
		default : throw new Exception ("Modele.Joueur.Joueur.reinitialiserPositionFigurine : direction inconnue") ;
		
		}
		
	}
	
	// permet de différencier un déplacement vers l'avant d'une attaque directe
	// retourne true si le déplacement est possible, false sinon
	
	private boolean estlibre (int position) throws Exception {
=======
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
        return main.accept(v);
	}
	
	public boolean peut_executer_parade(int valeurCarteMain, int nombreDeCartes, int valeurCarteAttaque) throws Exception {
		boolean b = false;
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
		
		return position != piste.getFigurine(FigurineAdverse).getPosition() ;
		
	}
	
<<<<<<< HEAD
	// vérifie si un déplacement vers l'avant est possible
	// retourne true si le déplacement est possible, false sinon

	private boolean avancer_dans_piste (int distance) throws Exception {
		
		int position_arrivee = piste.getFigurine(MaFigurine).getPosition() + distance * direction ; 
		
		return direction * (piste.getFigurine(FigurineAdverse).getPosition() - position_arrivee) >= 0 ;
	}
	
	// vérifie si un déplacement vers l'arrière est possible
	// retourne la postion d'arrivée si possible, ActionImpossible (-1) sinon
	
	public int peut_reculer(int distance) throws Exception {
		
		int position_arrivee = piste.getFigurine(MaFigurine).getPosition() - distance * direction ;
		
		if(piste.estdansPiste(position_arrivee))
			
			return position_arrivee ;
		
		return ActionImpossible ;
		
	}
	
	// vérifie si la carte de déplacement jouée déclenche un déplacement vers l'avant ou une attaque directe
	// retourne (true, position_arrivée) si un déplacement vers l'avant est possible
	// retourne (false, position_arrivee) si une attaque directe est possible
	// retourne (null, ActionImpossible(-1)) si aucune de ces deux actions n'est possible

	public Couple <Boolean, Integer> peut_avancer_ou_attaquer_directement(int distance) throws Exception {
		
		int position_arrivee = piste.getFigurine(MaFigurine).getPosition() + distance * direction ;
		
		if(!avancer_dans_piste (distance))
			
			return new Couple <> (null, ActionImpossible) ;
			
		if (estlibre(position_arrivee))
				
			return new Couple <> (true, position_arrivee) ;
			
		else
				
			return new Couple <> (false, position_arrivee) ;
		
	}
	
	// vérifie si la carte d'attaque jouée déclenche permet une attaque indirecte après un déplacement
	// retourne la postion d'arrivée si possible, ActionImpossible (-1) sinon

	public int peut_attaquer_indirectement(int position_apres_deplacement, int portee) throws Exception {

		int position_arrivee = position_apres_deplacement + portee * direction ;
		
		if(! estlibre(position_arrivee))
		
			return position_arrivee;
		
		return ActionImpossible ;
	}
	
	// met à jour la position de la figurine après un déplacement vers l'avant

	public void avancer(int distance) throws Exception {
		
		piste.getFigurine(MaFigurine).setPosition(piste.getFigurine(MaFigurine).getPosition() + distance * direction) ;
		
	}

	// met à jour la position de la figurine après un déplacement vers l'arrière

	public void reculer(int distance) throws Exception {
		
		piste.getFigurine(MaFigurine).setPosition(piste.getFigurine(MaFigurine).getPosition() - distance * direction) ;
		
	}
	
	// retourne true si le joueur peut parer l'attaque, false sinon
	
	public boolean peut_executer_parade(int valeurCarteMain, int nombreDeCartes, int valeurCarteAttaque) throws Exception {
		return valeurCarteMain == valeurCarteAttaque && main.getNombreCarteGroupe(valeurCarteMain) >= nombreDeCartes ;
	}
=======
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
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
	
	public ActionsJouables peutFaireAction(Triplet<Integer, Integer, Integer> est_attaque) throws Exception {
		
		int position ;
		ActionsJouables actions_jouables = new ActionsJouables () ;
		Carte carte;
		Carte carteOpt;
		
		for(int i=0; i<main.getNombreCarte(); i++){
			
			carte = main.getCarte(i);
			
			if(est_attaque.getC1() == Tour.PasAttaque){
				Couple <Boolean, Integer> test_avancer_ou_attaquer ;
				
				if((position = peut_reculer(carte.getContenu())) != ActionImpossible){
					actions_jouables.ajouterActionNeutre(carte.getID(), Reculer, position, carte) ;
				}
				
				if ((test_avancer_ou_attaquer = peut_avancer_ou_attaquer_directement(carte.getContenu())).getC2() != ActionImpossible) {
					
					if (!test_avancer_ou_attaquer.getC1()) {
						
						for(int j=1; j<=main.getNombreCarteGroupe(carte.getContenu()); j++){
							actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueDirecte, getPositionFigurine(MaFigurine), null, carte, j);
						}
							
					} else {
						
						actions_jouables.ajouterActionNeutre(carte.getID(), Avancer, test_avancer_ou_attaquer.getC2(), carte) ;
					
						for (int j=0; j<main.getNombreCarte(); j++) {
							
							carteOpt = main.getCarte(j);
					
							if (((position = test_avancer_ou_attaquer.getC2()) != ActionImpossible) &&
							    (peut_attaquer_indirectement(position, carteOpt.getContenu()) != ActionImpossible)){
						
								for(int k=1; k<=main.getNombreCarteGroupe(carteOpt.getContenu()) - (main.getCarte(i).getContenu() == carteOpt.getContenu() ? 1 : 0); k++){
									actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueIndirecte, test_avancer_ou_attaquer.getC2(), carte, carteOpt, k) ;
								}								
							}
						}
					}
				}
			}else{
				
<<<<<<< HEAD
				// On a oublié de tester que la valeur de la carte utilisée pour parer est la même que celle du joueur adverse utilisée pour son attaque 				
				if(peut_executer_parade(carte.getContenu(), est_attaque.getC2(), est_attaque.getC3())){
					actions_jouables.ajouterActionDefensive(carte.getID(), Parade, getPositionFigurine(MaFigurine), null, carte, est_attaque.getC2());
=======
				if(main.getNombreCarteGroupe(carte.getContenu()) == est_attaque.getC2()){
					if(peut_executer_parade(carte.getContenu(), est_attaque.getC2(), est_attaque.getC3())){
						actions_jouables.ajouterActionDefensive(carte.getID(), Parade, getPositionFigurine(), null, carte, est_attaque.getC2());
					}
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
				}
				
				if(est_attaque.getC1() == Tour.AttaqueIndirecte){
					if((position = peut_reculer(carte.getContenu())) != ActionImpossible){
						actions_jouables.ajouterActionDefensive(carte.getID(), Fuite, position, carte, null, 0);
					}
				}
				
			}
		}
			
		return actions_jouables ;
			
	}
	
	public ActionsJouables peutFaireActionAvecCarteSelectionne(int cote, ArrayList<Carte> cartes, Triplet<Integer, Integer, Integer> est_attaque) throws Exception {
		
		int position ;
		ActionsJouables actions_jouables = new ActionsJouables () ;
		Couple <Boolean, Integer> test_avancer_ou_attaquer ;
		Carte carte;
		Carte carteOpt;
		
		Main main = new Main();
		main.setCote(cote);
		main.setMain(cartes);
		
		if(main.size() > 0){
			carte = main.getCarte(0);
			
			if(est_attaque.getC1() == Tour.pasAttaque){
				
				if(main.size() > 1){
					if(main.getNombreCarteGroupe(carte.getContenu()) == main.size()){
						
						if ((test_avancer_ou_attaquer = peut_avancer_ou_attaquer_directement(carte.getContenu())).getC2() != ActionImpossible) {
							if (!test_avancer_ou_attaquer.getC1()) {
								actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueDirecte, getPositionFigurine(), null, carte, main.getNombreCarteGroupe(carte.getContenu()));
							}else{
								
								carteOpt = main.getCarte(1);
								
								if(main.getNombreCarteGroupe(carteOpt.getContenu())-1 == main.size()-1){
									
									if (((position = test_avancer_ou_attaquer.getC2()) != ActionImpossible) && 
										    (peut_attaquer_indirectement(position, carteOpt.getContenu()) != ActionImpossible)){
									
										actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueIndirecte, test_avancer_ou_attaquer.getC2(), carte, carteOpt, main.getNombreCarteGroupe(carteOpt.getContenu())-1) ;
										
									}
									
								}
								
							}
						}
							
					}else{
						
						if ((test_avancer_ou_attaquer = peut_avancer_ou_attaquer_directement(carte.getContenu())).getC2() != ActionImpossible) {
							if (test_avancer_ou_attaquer.getC1()) {
								
								carteOpt = main.getCarte(1);
								
								if(main.getNombreCarteGroupe(carteOpt.getContenu()) == main.size()-1){
									
									if (((position = test_avancer_ou_attaquer.getC2()) != ActionImpossible) && 
										    (peut_attaquer_indirectement(position, carteOpt.getContenu()) != ActionImpossible)){
									
										actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueIndirecte, test_avancer_ou_attaquer.getC2(), carte, carteOpt, main.getNombreCarteGroupe(carteOpt.getContenu())) ;
									
									}
									
								}
								
							}
						}
						
					}
				}else{
					if((position = peut_reculer(carte.getContenu())) != ActionImpossible){
						actions_jouables.ajouterActionNeutre(carte.getID(), Reculer, position, carte) ;
					}
					
					if ((test_avancer_ou_attaquer = peut_avancer_ou_attaquer_directement(carte.getContenu())).getC2() != ActionImpossible) {
						
						if (test_avancer_ou_attaquer.getC1()) {
							
							actions_jouables.ajouterActionNeutre(carte.getID(), Avancer, test_avancer_ou_attaquer.getC2(), carte) ;
								
						}else{
							
							actions_jouables.ajouterActionOffensive(carte.getID(), AttaqueDirecte, getPositionFigurine(), null, carte, 1);
							
						}
					}
				}
				
			}else{
				
				if(main.getNombreCarteGroupe(carte.getContenu()) == est_attaque.getC2()){
					if(peut_executer_parade(carte.getContenu(), est_attaque.getC2(), est_attaque.getC3())){
						actions_jouables.ajouterActionDefensive(carte.getID(), Parade, getPositionFigurine(), null, carte, est_attaque.getC2());
					}
				}
					
				if(main.size() == 1){
					if(est_attaque.getC1() == Tour.attaqueIndirect){
						if((position = peut_reculer(carte.getContenu())) != ActionImpossible){
							actions_jouables.ajouterActionDefensive(carte.getID(), Fuite, position, carte, null, 0);
						}
					}
				}

			}
		}
		
		return actions_jouables;
	}
	
	public void ajouterCarteDansMain(Carte c){
		main.ajouter(c);
	}
	
	public void defausserCartes(Carte c, int nbCartes, Defausse defausse) throws Exception{
		main.supprimer(c, nbCartes, defausse);
	}
	
	public void remplirMain(Pioche pioche){		
		int nbCarteMain = getMain().getNombreCarte();
		
		int i=nbCarteMain;
			
		while(!pioche.estVide() && i < Main.nombreCarteMax){
			ajouterCarteDansMain(pioche.piocher());
			i++;
		}
	}
	
	/**
	 * GETTER/SETTER
	 * @throws Exception 
	 **/
	
	public int getPositionDeMaFigurine() throws Exception {
		
		return piste.getFigurine(MaFigurine).getPosition() ;
		
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
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
		str += "  nom = " + nom + ", direction = " + direction + "\n";
		str += "  " + main;
		str += "  score =" + score + ",\n";
		str += "]\n";
		return str;
	}
<<<<<<< HEAD
	
	abstract public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception ;
	
	// CLONE
	
	@Override
	abstract public Joueur clone () ;
=======
>>>>>>> 7ebb790a0de02c0ff1c5d06bfe4ad5d4bbc5c34a
}