package Modele.Tas;

import java.util.Stack;

import Modele.Reglages;
import Modele.Visiteur;

public class Defausse extends Tas {
	
	// ATTRIBUT
	
	private Stack<Carte> d;
	
	// CONSTRUCTEURS
	
	public Defausse(){
		super(0, Reglages.lis("DefausseLargeur"), Reglages.lis("DefausseHauteur"));
		d = new Stack<Carte>();
	}
	
	public Defausse(int nombre){
		super(nombre);
		d = new Stack<Carte>();
	}
	
	// TO STRING
	
	@Override
	public String toString() {
		String resultat = "";
		resultat += "Defausse [\n";
		resultat += "  size= " + d.size() + "\n";
		resultat += "  defausse= " + d.toString() + "\n";
		resultat += "]\n";
		return resultat;
	}
	
	// CLONE
	
	@SuppressWarnings("unchecked")
	@Override
	public Defausse clone () {
		
		Defausse defausse = new Defausse() ;
		defausse.nombreCarte = this.nombreCarte.clone() ;
		defausse.d = (Stack<Carte>) this.d.clone() ;
		return defausse ;
		
	}
	
	/**
	 * MOTEUR
	 */
	
	public void ajouter(Carte c){
		c.setTas(Carte.defausse);
		d.push(c);
		nombreCarte[c.getContenu()]++ ;
	}

	public Carte carteDuDessus(){
		if(d.size() == 0){
			return null;
		}else{
			return d.lastElement();
		}
	}
	
	public boolean estVide(){
		return d.isEmpty();
	}
	
	/**
	 * GETTER/SETTER
	 **/
	
	public Stack<Carte> getD() {
		return d;
	}
	public void setD(Stack<Carte> d) {
		this.d = d;
	}
	
	/**
	 * VUE
	 **/

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}
}