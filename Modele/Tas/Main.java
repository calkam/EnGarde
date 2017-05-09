package Modele.Tas;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Tas {
	private ArrayList<Carte> main;
	private HashMap <Integer, ArrayList<Carte>> typecartes ;
	
	public Main(){
		super(0);
		this.main = new ArrayList<>();
		this.typecartes = new HashMap<>();
	}
	
	public void ajouter(Carte c){
		main.add(c);
		typecartes.putIfAbsent(c.getContenu(), new ArrayList<>()) ;
		typecartes.get(c.getContenu()).add(c) ;
		nombreCarte[c.getContenu()]++ ;
	}
	
	public void supprimer(Carte c) throws Exception{
		
		int i = 0;
		while(i < main.size() && (!main.get(i).equals(c))){
			i++;
		}
		if(i < main.size()){
			main.remove(i);
			nombreCarte[c.getContenu()]-- ;
		}else {
			throw new Exception ("Modele.Tas.Main.supprimer : rien a supprimer");
		}
		
		try {
		
			typecartes.get(c.getContenu()).remove(0) ;
			
		} catch (IndexOutOfBoundsException e) {
			
			throw new Exception ("Modele.Tas.Main.supprimer : rien a supprimer");
			
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