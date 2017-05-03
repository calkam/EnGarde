package Modele;

public enum Carte {
	
	UN(1),
	DEUX(2),
	TROIS(3),
	QUATRE(4),
	CINQ(5);
	
	int contenu;
	
	Carte(int i){
		this.contenu = i;
	}
	
	public boolean equals(Carte c) {
		return (this.contenu == c.contenu);
	}

	public String toString(){
		switch (this.contenu) {
	        case 1: return "un";
	        case 2: return "deux";
	        case 3: return "trois";
	        case 4: return "quatre";
	        case 5: return "cinq";
	        default: throw new RuntimeException("Ajout de carte sur " + this +" impossible");
		}
	}
	
}