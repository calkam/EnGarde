package Modele.Joueur;

import java.util.ArrayList;
import Modele.Couple;
import Modele.Tour;
import Modele.Triplet;
import Modele.Plateau.Piste;
import Modele.Plateau.Score;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;

/**
 * @author gourdeaf
 *
 */
public abstract class Joueur {
	
	// TYPES ACTION
	
	public final static int ActionImpossible = -1 ;
	public final static int Reculer = 0 ;
	public final static int Avancer = 1 ;
	public final static int AttaqueDirecte = 2 ;
	public final static int AttaqueIndirecte  = 3 ;
	public final static int Parade = 4 ;
	public final static int Fuite = 5 ;
	
	// TYPE JOUEUR
	// Le Joueur GAUCHE a pour direction la direction DROITE (1)
	// Le Joueur DROIT a pour direction la direction GAUCHE (-1)
	
	public final static int DROITE = 1 ;
	public final static int GAUCHE = -1 ;
	
	// FIGURINE JOUEUR
	// La Figurine du Joueur va dans la même direction que lui
	// La Figurine du Joueur adverse va dans la direction opposée du Joueur
	
	public final int MaFigurine  ;
	public final int FigurineAdverse ;
	
	// ATTRIBUTS
	
	protected int direction ;
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
		
		return position != piste.getFigurine(FigurineAdverse).getPosition() ;
		
	}
	
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
				
				// On a oublié de tester que la valeur de la carte utilisée pour parer est la même que celle du joueur adverse utilisée pour son attaque 				
				if(peut_executer_parade(carte.getContenu(), est_attaque.getC2(), est_attaque.getC3())){
					actions_jouables.ajouterActionDefensive(carte.getID(), Parade, getPositionFigurine(MaFigurine), null, carte, est_attaque.getC2());
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
	
	public void viderMain(){		
		main = new Main();
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
		str += "  nom = " + nom + ", direction = " + direction + "\n";
		str += "  " + main;
		str += "  score =" + score + ",\n";
		str += "]\n";
		return str;
	}
	
	abstract public Action selectionnerAction(ActionsJouables actions_jouables, Tour tour) throws Exception ;
	
	// CLONE
	
	@Override
	abstract public Joueur clone () ;
}