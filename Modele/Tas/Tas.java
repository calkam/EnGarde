package Modele.Tas;

public abstract class Tas {
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
	
	public int getNombreCarteGroupe(int i){
		
		return nombreCarte[i];
		
	}
	
	
}