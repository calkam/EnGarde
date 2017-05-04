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
	        case 1: return "un";
	        case 2: return "deux";
	        case 3: return "trois";
	        case 4: return "quatre";
	        case 5: return "cinq";
	        default: throw new RuntimeException("Ajout de carte sur " + this +" impossible");
		}
	}
	
}