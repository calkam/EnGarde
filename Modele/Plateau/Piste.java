package Modele.Plateau;

import java.util.ArrayList;

import Modele.Reglages;
import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Rectangle;


public class Piste extends Rectangle implements Visitable {
	
	// CONSTANTES

	private final static int nombreDeCases = 23;
	private final float LargeurCase ;
	
	// ATTRIBUTS
	
	private Figurine figurineGauche;
	private Figurine figurineDroite;
	private ArrayList<Case> cases;
	private MessageBox messageBox;
    private long tempsEcoule;
	

	// CONSTRUCTEUR

	public Piste(Figurine figurineGauche, Figurine figurineDroite) {
		super(0, 0, Reglages.lis("PisteLargeur"), Reglages.lis("PisteHauteur"));
		this.figurineGauche = figurineGauche ;
		this.figurineDroite = figurineDroite ;
		this.initTableauCases();
		this.LargeurCase = cases.get(0).getLargeur();
		this.figurineGauche.setY(Reglages.lis("PisteHauteur")-figurineGauche.getHauteur()-25);
		this.figurineDroite.setY(Reglages.lis("PisteHauteur")-figurineDroite.getHauteur()-25);
		this.figurineGauche.setX(LargeurCase * figurineGauche.getPosition());
		this.figurineDroite.setX(LargeurCase * figurineDroite.getPosition());
		this.messageBox = new MessageBox("");
		messageBox.setX(this.getLargeur()/2-messageBox.getLargeur()/2);
		messageBox.setY(0);
	
	}
	
	private void initTableauCases(){
		cases = new ArrayList<Case>(nombreDeCases);
		int proportion = nombreDeCases + 2;
		for(int i=0; i<nombreDeCases; i++){
			Case c = new Case(0, 0, 0);
			c.setLargeur(this.getLargeur() / proportion);
			c.setHauteur(this.getHauteur());
			c.setX(((this.getLargeur()*i)/proportion)+c.getLargeur());
			c.setNumero(i+1);
			cases.add(c);
		}
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
			
			new Figurine(this.getFigurineGauche().getDirection(), this.getFigurineGauche().getX(), this.getFigurineGauche().getY(), this.getFigurineGauche().getPosition()),
			new Figurine(this.getFigurineDroite().getDirection(), this.getFigurineDroite().getX(), this.getFigurineDroite().getY(), this.getFigurineDroite().getPosition())
			
			) ;
		
		piste.cases = (ArrayList<Case>) this.cases.clone() ;
		return piste ;
		
	}
	
	/**
	 * GETTER/SETTER
	 * @throws Exception 
	 **/
	
	public Figurine getFigurine(int direction) throws Exception {
		
		switch (direction) {
		
		case Figurine.GAUCHE : return getFigurineGauche() ;
		case Figurine.DROITE : return getFigurineDroite() ;
		default : throw new Exception ("Modele.Plateau.Piste.getFigurine : direction inconnue") ;
		
		}
	
	}
	
	public Figurine getFigurineGauche() {
		return figurineGauche ;
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
	
	public void setFigurinePosition(int direction, int position) throws Exception {
		
		Figurine figurine = getFigurine(direction) ;
		figurine.setPosition(position) ;
		figurine.setX(LargeurCase * figurine.getPosition());
		
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
	
	public void rafraichit(long t) {
        tempsEcoule = t;
    }
	
	long tempsEcoule() {
        return tempsEcoule;
    }

	/**
	 * MOTEUR
	 */
	
	public boolean estDansPiste(int position) {
		return position >=1 && position <= nombreDeCases;
	}
	
	public void afficherPiste(){
		String str = "";
		String strPosition = "";
		
		for(int i = 1 ; i < 24; i++){
			if(i == figurineGauche.getPosition()){
				str += "♙   ";
			}else if(i == figurineDroite.getPosition()){
				str += "♟   ";
			}else{
				str += "_   ";
			}
			
			if(i < 10){
				strPosition += i + "   ";		
			}else{
				strPosition += i + "  ";
			}
		}
		
		strPosition += "\n";
		
		System.out.println(str);
		System.out.println(strPosition);
	}
	
	/**
	 * VUE
	 * @throws Exception 
	 */
	
	public ArrayList<Case> getCases() {
		return cases;
	}

	public void setCases(ArrayList<Case> cases) {
		this.cases = cases;
	}

	@Override
	public boolean accept(Visiteur v) throws Exception {
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
	
	private void setProportion(){
		int proportion = nombreDeCases + 2;
		
		for(int i=0; i<nombreDeCases; i++){
			cases.get(i).setLargeur(this.getLargeur() / proportion);
			cases.get(i).setHauteur(this.getHauteur());
			cases.get(i).setX(((this.getLargeur()*i)/proportion)+cases.get(i).getLargeur());
			cases.add(cases.get(i));
		}
	}

	public void ajouteObservateur(ObjetMouvant objetMouvant) {
		// TODO Auto-generated method stub

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