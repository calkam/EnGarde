package Modele.Plateau;

import java.util.ArrayList;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Rectangle;
import Modele.Composant.Visiteur;
import Modele.Plateau.Figurine.*;

public class Piste extends Rectangle {

	private FigurineGauche figurineGauche;
	private FigurineDroite figurineDroite;
	private ArrayList<Case> cases;
	
	public Piste(FigurineGauche figurineGauche, FigurineDroite figurineDroite) {
		super(0, 0, 800, 600);
		this.figurineGauche = figurineGauche ;
		this.figurineDroite = figurineDroite ;
		this.initTableauCases();
	}
	
	public Piste() {
		// TODO Auto-generated constructor stub
	}

	public FigurineGauche getFigurineGauche() {
		return figurineGauche;
	}

	public void setFigurineGauche(FigurineGauche figurineGauche) {
		this.figurineGauche = figurineGauche ;
	}

	public FigurineDroite getFigurineDroite() {
		return figurineDroite;
	}

	public void setFigurineDroite(FigurineDroite figurineDroite) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Piste clone () {
		
		Piste piste = new Piste (
			
			new FigurineGauche(this.getFigurineGauche().posX(), this.getFigurineGauche().posY(), this.getFigurineGauche().getPosition()),
			new FigurineDroite(this.getFigurineDroite().posX(), this.getFigurineDroite().posY(), this.getFigurineDroite().getPosition())
			
			) ;
		
		piste.cases = (ArrayList<Case>) this.cases.clone() ;
		return piste ;
		
	}

}