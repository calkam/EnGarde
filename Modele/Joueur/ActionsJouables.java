package Modele.Joueur ;

import java.util.Enumeration;
import java.util.Hashtable;
import Modele.Triplet;
import Modele.Tas.Carte;

@SuppressWarnings("serial")
public class ActionsJouables extends Hashtable <Triplet<Integer, Integer, Integer>, Action> {
	
	// CONSTRUCTEURS

	public ActionsJouables() {
		super();
	}

	public ActionsJouables(int initialCapacity) {
		super(initialCapacity);
	}
	
	public void ajouterAction (int classeAction, Integer idCarte, Integer typeAction, int positionArrivee, Carte carteDeplacement, Carte carteAction, int nbCartes) throws Exception {
		Triplet<Integer, Integer, Integer> clé = new Triplet<Integer, Integer, Integer>(idCarte, typeAction, nbCartes);
		Action action ; 
		
		switch (classeAction) {
		case Action.ActionOffensive : action = new ActionOffensive(typeAction, nbCartes, positionArrivee, carteDeplacement, carteAction) ; break ;
		case Action.ActionNeutre : action = new ActionNeutre(typeAction, 0, positionArrivee, carteDeplacement) ; break ;
		case Action.ActionDefensive : action = new ActionDefensive(typeAction, nbCartes, positionArrivee, carteDeplacement, carteAction) ; break ;
		default : throw new Exception ("classeAction inconnue") ;
		}
		putIfAbsent(clé, action);
	}
	
	public ActionsJouables supprimerAction (Action action) throws Exception {
		
		Enumeration<Action> e = this.elements() ;
		
		while(e.hasMoreElements()) {
			
			Action action_courante = e.nextElement() ;
			
			if (action_courante.equals(action)) {
				
				this.remove(action) ;
				return this ;
			}
			
		}
		
		return null;
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
