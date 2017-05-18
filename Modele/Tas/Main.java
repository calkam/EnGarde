package Modele.Tas;

import java.util.ArrayList;
import java.util.ListIterator;

public class Main extends Tas {
	
	
	public final static int nombreCarteMax = 5;
	
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
	
	public void supprimer(Carte c, int nbCartes, Defausse defausse) throws Exception{
		
		ListIterator <Carte> li = main.listIterator() ;
		int cpt = 0 ;
		
		while(li.hasNext()){
		
			Carte ci = li.next() ;
			
			if (ci.getContenu() == c.getContenu()) {
				
				System.out.println(ci) ;
				defausse.ajouter(ci) ;
				li.remove();
				nombreCarte[c.getContenu()]-- ;
				nombreCarte[0]-- ;
				cpt++ ;
				if (cpt == nbCartes) break ;
				
			}
			
		}
			
		if (cpt < nbCartes) throw new Exception ("Modele.Tas.Main.supprimer : rien a supprimer");
		
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