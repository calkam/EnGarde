package Modele.Tas;

import java.util.Stack;

import Modele.Visiteur;

public class Defausse extends Tas {
	private Stack<Carte> d;
	
	public Defausse(){
		super();
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