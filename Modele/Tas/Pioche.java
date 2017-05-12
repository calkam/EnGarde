package Modele.Tas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import Modele.Visiteur;

public class Pioche extends Tas {
	private Stack<Carte> p;
	
	public Pioche(){
		super(25);
		p = new Stack<Carte>();
		this.melanger();
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return v.visite(this);
	}
	
	// On mélange les cartes de la pioche selon l'algo suivant :
	// On crée 5 tas de 5 cartes de valeurs identiques de 1 à 5
	// On ajoute dans la pioche une carte d'un des tas d'indice i tiré aléatoirement
	// Si un tas est vide, il est "écrasé" par le tas le plus à droite
	// On continue d'ajouter des cartes tant qu'il reste des tas
	public void melanger(){
		ArrayList<ArrayList<Carte> > jeu = new ArrayList<> (5);
		ArrayList<Carte> tas;
		
		int index = 0;
		Random r = new Random();
			
		for(int i=1; i<=5; i++){
			tas = new ArrayList<>();
			for(int j = 1 ; j <= 5 ; j++){
				tas.add(new Carte((i-1)*5+j, i, Carte.pioche, 0, 0, this.getLargeur(), this.getHauteur()));
			}
			jeu.add(tas);
		}
		
		do{
			if(jeu.size()==1){
				index = 0;
			}else{
				index = r.nextInt(jeu.size());
			}
			
			p.push(jeu.get(index).get(0));
			nombreCarte[index+1]++ ;
			
			
			jeu.get(index).remove(0);
			
			if(jeu.get(index).isEmpty()){
				jeu.remove(index);
			}			
		}while(!jeu.isEmpty());	
	}
	
	public boolean estVide(){
		return (p.size() == 0);
	}
	
	public Carte piocher(){
		nombreCarte[0]--;
		Carte c = p.pop();
		nombreCarte[c.getContenu()]--;
		return c ;
	}
		
	public String toString(){
		String resultat = "";
		resultat += "Pioche [\n";
		resultat += "  size= " + p.size() + "\n";
		resultat += "  pioche= " + p.toString() + "\n";
		resultat += "]\n";
		return resultat;
		
	}
}