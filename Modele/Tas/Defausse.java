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
	}

	@Override
	public String toString() {
		return "Defausse [d=" + d + "]";
	}
		
}