package Modele.Plateau;

import java.util.ArrayList;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Rectangle;
import Modele.Plateau.Figurine.*;

public class Piste extends Rectangle implements Visitable {

	private final static int nombreDeCases = 23;
	
	private FigurineGauche figurineGauche;
	private FigurineDroite figurineDroite;
	private ArrayList<Case> cases;
	
	public Piste(FigurineGauche figurineGauche, FigurineDroite figurineDroite) {
		super(0, 0, Reglages.lis("PisteLargeur"), Reglages.lis("PisteHauteur"));
		this.figurineGauche = figurineGauche ;
		this.figurineDroite = figurineDroite ;
		this.initTableauCases();
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
	
	public boolean estDansPiste(int position) {
		return position >=1 && position < cases.size() ;
	}
	
	public void initTableauCases(){
		cases = new ArrayList<Case>();
		int proportion = nombreDeCases + 2;
		for(int i=0; i<nombreDeCases; i++){
			Case c = new Case(0, 0, 0);
			c.setLargeur(this.getLargeur() / proportion);
			c.setHauteur(this.getHauteur());
			c.setX(((this.getLargeur()*i)/proportion)+c.getLargeur());
			cases.add(c);
		}
	}
	
	public ArrayList<Case> getCases() {
		return cases;
	}
	
	public void setCases(ArrayList<Case> cases) {
		this.cases = cases;
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		boolean retour = v.visite(this);
        for (Case c : cases) {
            retour = retour || c.accept(v);
        }
        return retour;
	}

	public void fixeDimensions(float l, float h){
		this.setLargeur(l);
		this.setHauteur(h);
		initTableauCases();
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

}