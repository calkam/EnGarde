package Modele.Joueur;

import Modele.Carte;
import Modele.Figurine;
import Modele.Main;

/**
 * @author gourdeaf
 *
 */
public abstract class Joueur {
	
	String nom ;
	Main main ;
	Figurine figurine ;
	
	public Joueur(String m_nom) {
		this(m_nom, new Figurine());
	}
	
	public Joueur(String m_nom, Figurine m_figurine) {
		this(m_nom, m_figurine, new Main());
	}
	
	public Joueur(String m_nom, Figurine m_figurine, Main m_main) {
		this.nom = m_nom;
		this.figurine = m_figurine;
		this.main = m_main;
	}
	
	public void ajouterCarteDansMain(Carte c){
		main.ajouter(c);
	}
	
	public Carte jouerUneCarte(int i){
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
	public Figurine getFigurine() {
		return figurine;
	}
	public void setFigurine(Figurine figurine) {
		this.figurine = figurine;
	}

	@Override
	public String toString() {
		return "Joueur [nom=" + nom + ", main=" + main + ", figurine=" + figurine + "]";
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