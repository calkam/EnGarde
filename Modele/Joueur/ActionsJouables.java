package Modele.Joueur ;

import java.util.Enumeration;
import java.util.Hashtable;
import Modele.Triplet;
import Modele.Tas.Carte;

@SuppressWarnings("serial")
public class ActionsJouables extends Hashtable <Triplet<Integer, Integer, Integer>, Action>{

	public ActionsJouables() {
		super();
	}

	public ActionsJouables(int initialCapacity) {
		super(initialCapacity);
	}
	
	public void ajouterActionOffensive(Integer idCarte, Integer typeAction, int positionArrivee, Carte carteDepl, Carte carteAttaque, int nbCartes) {
		Triplet<Integer, Integer, Integer> clé = new Triplet<Integer, Integer, Integer>(idCarte, typeAction, nbCartes);
		Action action = new ActionOffensive(typeAction, nbCartes, positionArrivee, carteDepl, carteAttaque);
		putIfAbsent(clé, action);
	}
	
	public void ajouterActionDefensive(Integer idCarte, Integer typeAction, int positionArrivee, Carte carteDepl, Carte carteDefense, int nbCartes) {
		Triplet<Integer, Integer, Integer> clé = new Triplet<Integer, Integer, Integer>(idCarte, typeAction, nbCartes);	
		Action action = new ActionDefensive(typeAction, nbCartes, positionArrivee, carteDepl, carteDefense);
		putIfAbsent(clé, action);
	}
	
	public void ajouterActionNeutre(Integer idCarte, Integer typeAction, int positionArrivee, Carte carteDepl) {
		Triplet<Integer, Integer, Integer> clé = new Triplet<Integer, Integer, Integer>(idCarte, typeAction, 0);
		Action action = new ActionNeutre(typeAction, 0, positionArrivee, carteDepl);
		putIfAbsent(clé, action);
	}

	@Override
	public String toString() {
		String str = "";		
		
		if(this.size() == 0){
			str += "Perdu : pas d'actions possibles\n";			
		}
		else{
			Enumeration<Action> e = this.elements();
			int i = 0;
			
			while(e.hasMoreElements()){
				str += "Choix " + i + " : " + e.nextElement().toString() + "\n";				
				i++;
			}
		}
		
		return str;
	}

}
