package Modele.Tas;

import java.util.ArrayList;

public class Main extends Tas {
	private ArrayList<Carte> main;
	
	public Main(){
		super(0);
		this.main = new ArrayList<>();
	}
	
	public void ajouter(Carte c){
		main.add(c);
		nombreCarte[c.getContenu()]++ ;
		nombreCarte[0]++ ;
	}
	
	public void supprimer(Carte c) throws Exception{
		
		int i = 0;
		while(i < main.size() && (!main.get(i).equals(c))){
			i++;
		}
		if(i < main.size()){
			main.remove(i);
			nombreCarte[c.getContenu()]-- ;
			nombreCarte[0]-- ;
		}else {
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
		String resultat = "";
		resultat += "Main [\n";
		resultat += "    size= " + main.size() + "\n";
		resultat += "    main= " + main.toString() + "\n";
		resultat += "  ]\n";
		return resultat;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Main clone () {
		
		Main main = new Main() ;
		main.nombreCarte = this.nombreCarte.clone() ;
		main.setMain((ArrayList<Carte>) this.getMain().clone()) ;
		return main ;
		
	}
			
}