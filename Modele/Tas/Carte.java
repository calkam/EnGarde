package Modele.Tas;

public enum Carte {
	
	UN(1),
	DEUX(2),
	TROIS(3),
	QUATRE(4),
	CINQ(5);
	
	private int contenu;
	
	Carte(int contenu){
		this.contenu = contenu ;
	}
	
	public int getContenu() {
		return contenu;
	}

	public boolean equals(Carte c) {
		return (this.getContenu() == c.getContenu());
	}

	public String toString(){
		switch (this.getContenu()) {
	        case 1: return Integer.toString(1);
	        case 2: return Integer.toString(2);
	        case 3: return Integer.toString(3);
	        case 4: return Integer.toString(4);
	        case 5: return Integer.toString(5);
	        default: throw new RuntimeException("Ajout de carte sur " + this +" impossible");
		}
	}
	
}