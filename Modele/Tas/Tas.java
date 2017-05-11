package Modele.Tas;

public abstract class Tas {
	
	// nombreCarte est un tableau de 6 entiers
	// La case d'indice i > 0 contient le nombre de cartes de valeur i du Tas
	// La case d'indice 0 contient le nombre de cartes totales du Tas
	
	protected int [] nombreCarte ;
	
	public Tas(){
		this(0);
	}
	
	public Tas(int m_nombreCarte){
		
		this.nombreCarte = new int [6] ;
		this.nombreCarte[0] = m_nombreCarte ;
		for (int i = 1 ; i < 6; i++)
			this.nombreCarte[i] = 0 ; 
	}
	
	public int getNombreCarte(){
		return nombreCarte[0];
	}
	public void setNombreCarte(int nbCartes){
		nombreCarte[0] = nbCartes;
	}	
	public int getNombreCarteGroupe(int i){		
		return nombreCarte[i];
	}
	public void setNombreCarteGroupe(int i, int nbCartes){
		nombreCarte[i] = nbCartes;
	}
}