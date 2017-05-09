package Modele.Joueur ;

import java.util.ArrayList;
import java.util.Hashtable;
import Modele.Triplet;
import Modele.Tas.Carte;

@SuppressWarnings("serial")
public class ActionsJouables extends Hashtable <Integer, ArrayList<Triplet<Integer, Carte, Carte>>>{

	public ActionsJouables() {
		super();
	}

	public ActionsJouables(int initialCapacity) {
		super(initialCapacity);
	}
	
	public Triplet <Integer, Carte, Carte> ajouterChoix (Integer action, int position, Carte carte, Carte carte_attaque_indirecte) {
		
		return put(action, new Triplet <Integer, Carte, Carte> (position, carte, carte_attaque_indirecte));
		
	}

	@Override
	public String toString() {
		String str = "";
		
		for(Integer i : this.keySet()){
			str += "Clé " + i + " : " ;
			str += this.get(i).toString();
			str += "\n";
		}
		
		return str;
	}
}
