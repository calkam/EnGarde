package Modele;

public class Figurine extends Cercle {
	private int position;
	
	public Figurine(){
	}
	
	Figurine(int i){
		this.position = i ;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	
}