package Modele.Joueur ;

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
		Action action = new ActionOffensive(typeAction, positionArrivee, nbCartes, carteDepl, carteAttaque);
		putIfAbsent(clé, action);
	}
	
	public void ajouterActionDefensive(Integer idCarte, Integer typeAction, int positionArrivee, Carte carteDepl, Carte carteDefense, int nbCartes) {
		Triplet<Integer, Integer, Integer> clé = new Triplet<Integer, Integer, Integer>(idCarte, typeAction, nbCartes);
		Action action = new ActionDefensive(typeAction, positionArrivee, nbCartes, carteDepl, carteDefense);
		putIfAbsent(clé, action);
	}
	
	public void ajouterActionNeutre(Integer idCarte, Integer typeAction, int positionArrivee, Carte carteDepl) {
		Triplet<Integer, Integer, Integer> clé = new Triplet<Integer, Integer, Integer>(idCarte, typeAction, 1);
		Action action = new ActionNeutre(typeAction, positionArrivee, 1, carteDepl);
		putIfAbsent(clé, action);
	}

	@Override
	public String toString() {
		String str = "";
		
		if(this.size() == 0){
			str += "Erreur : pas d'actions possibles\n";			
		}
		else{
			for(Triplet<Integer, Integer, Integer> t : this.keySet()){
				str += "ActionsJouables [ idCarte= " + t.getC1() + ", typeAction= " + t.getC2() + ", nbCartes= " + t.getC3() + ", " + this.get(t).toString() + " ]";
			}
		}
		
		return str;
	}

	/*public void afficherSousActions(Integer i) {
		String str = "";		
		
		for(int j = 0; j < this.get(i).size(); j++){
			Triplet<Integer, Carte, Carte> t = this.get(i).get(j);
			str += "Choix " + j;			
			str += " : [";
			switch(i){
				case Joueur.ActionImpossible: str += "ActionImpossible";break;
				case Joueur.Reculer: str += "Reculer";break;
				case Joueur.Avancer: str += "Avancer";break;
				case Joueur.AttaqueDirecte: str += "AttaqueDirecte";break;
				case Joueur.AttaqueIndirecte: str += "AttaqueIndirecte";break;
				case Joueur.Parade: str += "Parade";break;
				case Joueur.Fuite: str += "Fuite";break;
				default: str += "Erreur";
			}
			str += " : " ;
			str += "[position= " + t.getC1() + ", carte= "+ t.getC2() +", carte_opt= "+ t.getC3() +"]]";
			str += "\n";
		}
		
		System.out.println(str);
	}*/
}
