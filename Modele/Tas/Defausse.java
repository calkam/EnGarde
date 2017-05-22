package Modele.Tas;

import java.util.Stack;

import Modele.Reglages;
import Modele.Visiteur;

public class Defausse extends Tas {
	private Stack<Carte> d;
	
	public Defausse(){
		super(0, Reglages.lis("DefausseLargeur"), Reglages.lis("DefausseHauteur"));
		d = new Stack<Carte>();
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}
	
	public Defausse(int nombre){
		super(nombre);
		d = new Stack<Carte>();
	}
	
	public void ajouter(Carte c){
		c.setTas(Carte.defausse);
		d.push(c);
		nombreCarte[c.getContenu()]++ ;
	}

	public Carte carteDuDessus(){
		if(d.size() == 0){
			return null;
		}else{
			return d.lastElement();
		}
	}
	
	public boolean estVide(){
		return d.isEmpty();
	}
	
	public Stack<Carte> getDefausse() {
		return d;
	}

	public void setDefause(Stack<Carte> d) {
		this.d = d;
	}

	@Override
	public String toString() {
		String resultat = "";
		resultat += "Defausse [\n";
		resultat += "  size= " + d.size() + "\n";
		resultat += "  defausse= " + d.toString() + "\n";
		resultat += "]\n";
		return resultat;
	}
		
}