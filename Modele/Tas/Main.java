package Modele.Tas;

import java.util.ArrayList;

import Modele.Visiteur;

public class Main extends Tas {
	
	public final static int droite = 0;
	public final static int gauche = 1;
	
	private ArrayList<Carte> main;
	int cote;
	
	public Main(int cote){
		super(0);
		this.main = new ArrayList<>();
		this.cote = cote;
	}
	
	public Main(){
		super(0);
		this.main = new ArrayList<>();
	}
	
	public void ajouter(Carte c){
		main.add(c);
		if(cote == droite){
			c.setTas(Carte.mainDroite);
		}else{
			c.setTas(Carte.mainGauche);
		}
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
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		boolean retour = v.visite(this);
        for (Carte c : this.getMain()) {
            retour = retour || c.accept(v);
        }
		return retour;
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
	
	public int getCote() {
		return cote;
	}

	public void setPosition(int cote) {
		this.cote = cote;
	}

	public String toString(){
		String resultat = "";
		resultat += "Main [\n";
		resultat += "    size= " + main.size() + "\n";
		resultat += "    main= " + main.toString() + "\n";
		resultat += "  ]\n";
		return resultat;
	}
	
	/**
	 * ACTION SUR MAIN
	 */
	public void getCarteClick(double x, double y) {
		// TODO Auto-generated method stub
		for(int i=0; i<main.size(); i++){
			if(main.get(i).estCollision((float)x, (float)y)){
				if(!main.get(i).isSelectionne()){
					main.get(i).setSelectionne(true);
				}else{
					main.get(i).setSelectionne(false);
				}
			}
		}
	}
			
}