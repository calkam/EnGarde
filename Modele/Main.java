package Modele;

import java.util.ArrayList;

public class Main extends Tas {
	private ArrayList<Carte> main;
	
	public Main(){
		this.main = new ArrayList<Carte>();
		this.nombre = 0;
	}
	
	public void ajouter(Carte c){
		main.add(c);
	}
	
	public void supprimer(Carte c){
		int i =0;
		while(i < main.size() && (!main.get(i).equals(c))){
			i++;
		}
		if(i < main.size()){
			main.remove(i);
		}else{
			System.out.println("Erreur rien a supprimer");
		}
	}
	
	public Carte getCarte(int c){
		return this.main.get(c);
	}
	
	public ArrayList<Carte> getMain(){
		return this.main;
	}
	
	public void setMain(ArrayList<Carte> m) {
		this.main = m;
	}

	public String toString(){
		String resultat = "Main: "+ main.size() +'\n' ;
		resultat += main.toString();
		return resultat;
		
	}
			
}