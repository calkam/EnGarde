package Modele.Tas;

import Modele.Visitable;
import Modele.Composant.Rectangle;

public abstract class Tas extends Rectangle implements Visitable {
	
	// nombreCarte est un tableau de 6 entiers
	// La case d'indice i > 0 contient le nombre de cartes de valeur i du Tas
	// La case d'indice 0 contient le nombre de cartes totales du Tas
	public final static int carteValeurMax = 6;
	
	protected int [] nombreCarte ;
	
	public Tas(){
		this(0);
	}

	public Tas(int m_nombreCarte){
		this(m_nombreCarte, 0, 0);
	}
	
	public Tas(int m_nombreCarte, float largeur, float hauteur){
		super(0, 0, largeur, hauteur);
		this.nombreCarte = new int [carteValeurMax] ;
		this.nombreCarte[0] = m_nombreCarte ;
		for (int i = 1 ; i < carteValeurMax; i++)
			this.nombreCarte[i] = 0 ; 
	}
	
	public void fixeDimensions(float l, float h){
		this.setLargeur(l);
		this.setHauteur(h);
	}
	
	public int getNombreCarte(){
		return nombreCarte[0];
	}
	public void setNombreCarte(int nbCartes){
		nombreCarte[0] = nbCartes;
	}	
	public int getNombreCarteGroupe(int i) throws Exception{	
		if(i > carteValeurMax){
			throw new Exception("Il n'y pas autant de carte dans la main");
		}
		return nombreCarte[i];
	}
	public void setNombreCarteGroupe(int i, int nbCartes){
		nombreCarte[i] = nbCartes;
	}
}