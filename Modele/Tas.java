package Modele;

public abstract class Tas {
	protected int nombreCarte;
	
	public Tas(){
		this(0);
	}
	
	public Tas(int m_nombreCarte){
		this.nombreCarte = m_nombreCarte;
	}
	
	public int getNombreCarte(){
		return nombreCarte;
	}
	
	public void setNombreCarte(int i){
		nombreCarte=i;
	}
}