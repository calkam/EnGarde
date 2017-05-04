package Modele;

import java.util.Stack;

public class Defausse extends Tas {
	private Stack<Carte> d;
	
	public Defausse(){
		super(0);
		d = new Stack<Carte>();
	}
	
	public Defausse(int nombre){
		super(nombre);
		d = new Stack<Carte>();
	}
	
	public void ajouter(Carte c){
		d.push(c);
	}

	@Override
	public String toString() {
		return "Defausse [d=" + d + "]";
	}
	
	
}