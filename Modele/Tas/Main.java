package Modele.Tas;

import java.util.ArrayList;
import java.util.ListIterator;

import Modele.Reglages;
import Modele.Visiteur;
import Modele.Plateau.Figurine;

public class Main extends Tas {
	
	// CONSTANTES
	
	public final static int nombreCarteMax = 5;
	public final static int droite = Figurine.DROITE ;
	public final static int gauche = Figurine.GAUCHE ;
	
	// ATTRIBUTS
	
	private ArrayList<Carte> main;
	private int cote;
	private boolean visible;
	
	// CONSTRUCTEURS
	
	public Main(int cote, float x, float y, float largeur, float hauteur){
		super(0, x, y, largeur, hauteur);
		this.main = new ArrayList<>();
		this.cote = cote;
		visible = false;
	}
	
	public Main(int cote){
		this(cote, 0, 0, Reglages.lis("MainLargeur"), Reglages.lis("MainHauteur"));
	}
	
	public Main(){
		this(0);
	}
	
	// TO STRING

	public String toString(){
		String resultat = "";
		resultat += "Main [\n";
		resultat += "    size= " + main.size() + "\n";
		resultat += "    main= " + main.toString() + "\n";
		resultat += "    visible= " + visible + "\n";
		resultat += "  ]\n";
		return resultat;
	}
	
	// CLONE
	
	@SuppressWarnings("unchecked")
	@Override
	public Main clone () {
		
		Main main = new Main(getCote(), getX(), getY(), getLargeur(), getHauteur()) ;
		main.setVisible(this.visible);
		main.nombreCarte = this.nombreCarte.clone() ;
		main.setMain((ArrayList<Carte>) getMain().clone()) ;
		return main ;
		
	}
	
	/**
	 * GETTER/SETTER
	 **/
	
	public boolean estVide(){
		return main.isEmpty();
	}
	
	public Carte getCarte(int c){
		return this.main.get(c);
	}
	
	public ArrayList<Carte> getMain(){
		return this.main;
	}
	
	public void setMain(ArrayList<Carte> m) {
		for(Carte c : m){
			ajouter(c);
		}
	}
	
	public int size(){
		return main.size();
	}
	
	public int getCote() {
		return cote;
	}

	public void setCote(int cote) {
		this.cote = cote;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		for(Carte c : main){
			if(!visible){
				c.setSelectionne(false);
			}
			c.setVisible(visible);
		}
	}
	
	/**
	 * MOTEUR
	 */
	
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

	/**
	 * VUE
	 */
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		boolean retour = v.visite(this);
        for (Carte c : this.getMain()) {
            retour = retour || c.accept(v);
        }
		return retour;
	}
	
	public boolean estCollisionCarte(int i, float x, float y){
		return main.get(i).estCollision(x, y) && i+1<main.size() && !main.get(i+1).estCollision(x, y) || 
			   main.get(i).estCollision(x, y) && i==main.size()-1;
	}
	
	public Carte getCarteClick(double x, double y) {
		// TODO Auto-generated method stub
		for(int i=0; i<main.size(); i++){
			if(estCollisionCarte(i, (float)x, (float)y)){
				if(!main.get(i).isSelectionne()){
					main.get(i).setSelectionne(true);
					main.get(i).setY(0);
					return main.get(i);
				}else{
					main.get(i).setSelectionne(false);
					main.get(i).setY(Reglages.lis("PositionYCarte"));
					return main.get(i);
				}
			}
		}
		return null;
	}

	public void repositionnerMain() {
		// TODO Auto-generated method stub
		int i=0;
		for(Carte c : main){
			c.setX((float)(i*c.getLargeur()*0.63));
			c.setY(Reglages.lis("PositionYCarte"));
			i++;
		}
	}
			
}