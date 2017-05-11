package Modele.Tas;

public abstract class Tas {
	
	// nombreCarte est un tableau de 6 entiers
	// La case d'indice i > 0 contient le nombre de cartes de valeur i du Tas
	// La case d'indice 0 contient le nombre de cartes totales du Tas
	private final static int nombreMaxCarte = 6;
	
	protected int [] nombreCarte ;
	
	public Tas(){
		this(0);
	}
	
	public Tas(int m_nombreCarte){
		
		this.nombreCarte = new int [nombreMaxCarte] ;
		this.nombreCarte[0] = m_nombreCarte ;
		for (int i = 1 ; i < nombreMaxCarte; i++)
			this.nombreCarte[i] = 0 ; 
	}
	
	public int getNombreCarte(){
		return nombreCarte[0];
	}
	public void setNombreCarte(int nbCartes){
		nombreCarte[0] = nbCartes;
	}	
	public int getNombreCarteGroupe(int i) throws Exception{	
		if(i > nombreMaxCarte){
			throw new Exception("Il n'y pas autant de carte dans la main");
		}
		return nombreCarte[i];
	}
	public void setNombreCarteGroupe(int i, int nbCartes){
		nombreCarte[i] = nbCartes;
	}
}