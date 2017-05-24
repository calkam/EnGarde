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
	
	public void ajouterAction(Action action){
		int id;
		if(action.getCarteAction() != null){
			id = action.getCarteAction().getID();
		}else{
			id = action.getCarteDeplacement().getID();
		}
		Triplet<Integer, Integer, Integer> clé = new Triplet<Integer, Integer, Integer>(id, action.getTypeAction(), action.getNbCartes());
		putIfAbsent(clé, action);
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
