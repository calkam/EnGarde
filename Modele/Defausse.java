package Modele;

import java.util.Stack;

public class Defausse extends Tas {
	private Stack<Carte> d;
	
	Defausse(){
		super();
		d = new Stack();
		this.nombre = 0;
	}
	Defausse(int nombre){
		d = new Stack();
		this.nombre = nombre;
	}
	
	public void ajouter(Carte c){
		d.push(c);
	}
}