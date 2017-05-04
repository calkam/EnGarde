package Modele;

import java.util.ArrayList;

import Modele.Joueur.FigurineGauche;

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
		return position >=0 && position < cases.size() ;
	}
	
	public void initTableauCases(){
		cases = new ArrayList<Case>();
		for(int i=0; i<23; i++){
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
		str += "  figurineGauche=" + figurineGauche + ",\n";
		str += "  figurineDroite=" + figurineDroite + ",\n";
		str += "  ";
		for(Case c : cases){
			str += "{" + c + "}, ";
		}
		str += "\n";
		str += "]\n";
		return str;
	}

}