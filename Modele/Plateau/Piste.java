package Modele.Plateau;

import java.util.ArrayList;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Rectangle;
import Modele.Joueur.Joueur;
import Modele.Joueur.JoueurDroit;
import Modele.Plateau.Figurine.*;

public class Piste extends Rectangle implements Visitable {

	private final static int nombreDeCases = 23;

	private FigurineGauche figurineGauche;
	private FigurineDroite figurineDroite;
	private ArrayList<Case> cases;
	private MessageBox messageBox;

    private long tempsEcoule;

	public Piste(FigurineGauche figurineGauche, FigurineDroite figurineDroite) {
		super(0, 0, Reglages.lis("PisteLargeur"), Reglages.lis("PisteHauteur"));
		this.figurineGauche = figurineGauche ;
		this.figurineDroite = figurineDroite ;
		this.initTableauCases();
		float largeurCase = cases.get(0).getLargeur();

		this.figurineGauche.setY(Reglages.lis("PisteHauteur")-figurineGauche.getHauteur()-25);
		this.figurineDroite.setY(Reglages.lis("PisteHauteur")-figurineDroite.getHauteur()-25);
		this.figurineGauche.setX(largeurCase * figurineGauche.getPosition());
		this.figurineDroite.setX(largeurCase * figurineDroite.getPosition());

		this.messageBox = new MessageBox("");
		messageBox.setX(this.getLargeur()/2-messageBox.getLargeur()/2);
		messageBox.setY(0);
	}

	public void rafraichit(long t) {
        tempsEcoule = t;
    }

	long tempsEcoule() {
        return tempsEcoule;
    }

	public void setPositionFigurine(Joueur joueur, int position){
		Figurine figurine;

		if(joueur instanceof JoueurDroit){
			figurine = getFigurineDroite();
		}else{
			figurine = getFigurineGauche();
		}
		figurine.setPosition(position);

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

	public void setFigurineDroitePosition(int position){
		float largeurCase = cases.get(0).getLargeur();
		this.figurineDroite.setPosition(position);
		this.figurineDroite.setX(largeurCase * figurineDroite.getPosition());
	}

	public void setFigurineGauchePosition(int position){
		float largeurCase = cases.get(0).getLargeur();
		this.figurineGauche.setPosition(position);
		this.figurineGauche.setX(largeurCase * figurineGauche.getPosition());
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	public void setMessageInMessageBox(String texte) {
		this.messageBox.setTexte(texte);
	}

	public boolean estDansPiste(int position) {
		return position >=1 && position <= nombreDeCases;
	}

	public void initTableauCases(){
		cases = new ArrayList<Case>(nombreDeCases);
		int proportion = nombreDeCases + 2;
		for(int i=0; i<nombreDeCases; i++){
			Case c = new Case(0, 0, Reglages.lis("PositionYCase"));
			c.setLargeur(Reglages.lis("CaseLargeur"));
			c.setHauteur(Reglages.lis("CaseHauteur"));
			c.setX(((this.getLargeur()*i)/proportion)+c.getLargeur());
			c.setNumero(i+1);
			cases.add(c);
		}
	}

	public void setProportion(){
		/*int proportion = nombreDeCases + 2;

		for(int i=0; i<nombreDeCases; i++){
			cases.get(i).setLargeur(this.getLargeur() / proportion);
			cases.get(i).setHauteur(this.getHauteur());
			cases.get(i).setX(((this.getLargeur()*i)/proportion)+cases.get(i).getLargeur());
			cases.add(cases.get(i));
		}*/
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
        retour = retour || figurineDroite.accept(v);
		retour = retour || figurineGauche.accept(v);
		retour = retour || messageBox.accept(v);
        return retour;
	}

	public void fixeDimensions(float l, float h){
		this.setLargeur(l);
		this.setHauteur(h);
		setProportion();
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

	public void reinitialiserCouleurCase() {
		// TODO Auto-generated method stub
		for(Case c : cases){
			c.setCouleur(0);
		}
	}

	public void changeColorCaseClicked(int couleur, double x, double y) {
		// TODO Auto-generated method stub
		Case c = getCaseEvent(x, y);
		if(c.estCollision((float)x, (float)y)){
			c.setCouleur(couleur);
		}
	}

	public Case getCaseEvent(double x, double y) {
		// TODO Auto-generated method stub
		for(Case c : cases){
			if(c.estCollision((float)x, (float)y)){
				return c;
			}
		}
		return null;
	}

}