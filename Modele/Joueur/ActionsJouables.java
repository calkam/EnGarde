package Modele.Joueur ;

import java.util.Hashtable;
import Modele.Triplet;
import Modele.Tas.Carte;

@SuppressWarnings("serial")
public class ActionsJouables extends Hashtable <String, Triplet <Integer, Carte, Carte>> {

	public ActionsJouables() {
		super();
	}

	public ActionsJouables(int initialCapacity) {
		super(initialCapacity);
	}
	
	public Triplet <Integer, Carte, Carte> ajouterChoix (String cle, int position, Carte carte, Carte carte_attaque_indirecte) {
		
		return put(cle, new Triplet <Integer, Carte, Carte> (position, carte, carte_attaque_indirecte));
		
	}
	
	

}
