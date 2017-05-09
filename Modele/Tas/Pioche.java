package Modele.Tas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Pioche extends Tas {
	private Stack<Carte> p;
	
	public Pioche(){
		super(25);
		p = new Stack<Carte>();
		this.melanger();
	}
	
	public void melanger(){
		ArrayList<ArrayList<Carte> > jeu = new ArrayList<> (5);
		ArrayList<Carte> tas;
		
		int index = 0;
		Random r = new Random();
			
		for(Carte c :Carte.values()){
			tas = new ArrayList<>();
			for(int j = 1 ; j <= 5 ; j++){
				tas.add(c);
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
		String resultat = "Pile: "+p.size()+'\n' ;
		resultat += p.toString();
		return resultat;
		
	}
}