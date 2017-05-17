package Modele.Plateau;

import java.util.ArrayList;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Rectangle;
import Modele.Composant.Visiteur;
import Modele.Joueur.Joueur;

public class Piste extends Rectangle {
	
	// ATTRIBUTS

	private Figurine figurineGauche;
	private Figurine figurineDroite;
	private ArrayList<Case> cases;
	
	// CONSTRUCTEUR
	
	public Piste(Figurine figurineGauche, Figurine figurineDroite) {
		super(0, 0, 800, 600);
		this.figurineGauche = figurineGauche ;
		this.figurineDroite = figurineDroite ;
		this.initTableauCases();
	}
	
	/**
	 * GETTER/SETTER
	 * @throws Exception 
	 **/
	
	public Figurine getFigurine(int direction) throws Exception {
		
		switch (direction) {
		
		case Joueur.DROITE : return getFigurineGauche() ;
		case Joueur.GAUCHE : return getFigurineDroite() ;
		default : throw new Exception ("Modele.Plateau.Piste.getFigurine : direction inconnue") ;
		
		}
	
	}

	public Figurine getFigurineGauche() {
		return figurineGauche;
	}

	public void setFigurineGauche(Figurine figurineGauche) {
		this.figurineGauche = figurineGauche ;
	}

	public Figurine getFigurineDroite() {
		return figurineDroite;
	}

	public void setFigurineDroite(Figurine figurineDroite) {
		this.figurineDroite = figurineDroite ;
	}
	
	public boolean estdansPiste(int position) {
		return position >=1 && position < cases.size() ;
	}
	
	public void initTableauCases(){
		cases = new ArrayList<Case>();
		for(int i=0; i<24; i++){
			Case c = new Case(0, i*Case.largeur, 0);
			cases.add(c);
		}
	}
	
	public ArrayList<Case> getCases() {
		return cases;
	}
	
	public void setCases(ArrayList<Case> cases) {
		this.cases = cases;
	}
	
	public boolean accepte(Visiteur visiteur) {
		return false;
		
	}

	public void ajouteObservateur(ObjetMouvant objetMouvant) {
		// TODO Auto-generated method stub
		
	}
	
	// TO STRING

	@Override
	public String toString() {
		String str = "Piste \n";
		str += "[\n";
		str += "  figurineGauche=" + figurineGauche.toString() + ",\n";
		str += "  figurineDroite=" + figurineDroite.toString() + ",\n";
		str += "  cases=";
		for(Case c : cases){
			str += "{" + c + "}, ";
		}
		str += "\n";
		str += "]\n";
		return str;
	}
	
	// CLONE
	
	@SuppressWarnings("unchecked")
	@Override
	public Piste clone () {
		
		Piste piste = new Piste (
			
			new Figurine(this.getFigurineGauche().getDirection(), this.getFigurineGauche().posX(), this.getFigurineGauche().posY(), this.getFigurineGauche().getPosition()),
			new Figurine(this.getFigurineDroite().getDirection(), this.getFigurineDroite().posX(), this.getFigurineDroite().posY(), this.getFigurineDroite().getPosition())
			
			) ;
		
		piste.cases = (ArrayList<Case>) this.cases.clone() ;
		return piste ;
		
	}

}