package Modele;

import java.util.ArrayList;

import Modele.Joueur.Joueur;

public class Piste extends Rectangle {

	private Figurine figurine1;
	private Figurine figurine2;
	private ArrayList<Case> cases;
	
	public Piste(Figurine m_figurine1, Figurine m_figurine2) {
		super(0, 0, 800, 600);
		this.figurine1 = m_figurine1;
		this.figurine2 = m_figurine2;
		this.initTableauCases();
	}
	
	public void initTableauCases(){
		cases = new ArrayList<Case>();
		for(int i=0; i<23; i++){
			Case c = new Case(0, i*Case.largeur, 0);
			cases.add(c);
		}
	}
	
	public Figurine getFigurine1(Joueur j) {
		return figurine1;
	}
	
	public Figurine getFigurine2() {
		return figurine2;
	}
	public void setFigurine2(Figurine figurine2) {
		this.figurine2 = figurine2;
	}
	
	public ArrayList<Case> getCases() {
		return cases;
	}
	
	public void setCases(ArrayList<Case> cases) {
		this.cases = cases;
	}
	
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}

	public void ajouteObservateur(ObjetMouvant objetMouvant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		String str = "Piste \n";
		str += "[\n";
		str += "  figurine1=" + figurine1 + ",\n";
		str += "  figurine2=" + figurine2 + ",\n";
		str += "  ";
		for(Case c : cases){
			str += "{" + c + "}, ";
		}
		str += "\n";
		str += "]\n";
		return str;
	}

}