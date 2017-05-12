package Modele.Tas;

import java.util.Stack;

public class Defausse extends Tas {
	private Stack<Carte> d;
	
	public Defausse(){
		super();
		d = new Stack<Carte>();
	}
	
	public Defausse(int nombre){
		super(nombre);
		d = new Stack<Carte>();
	}
	
	public void ajouter(Carte c){
		d.push(c);
		nombreCarte[c.getContenu()]++ ;
		nombreCarte[0]++ ;
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