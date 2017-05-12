package Modele.Tas;

public class Carte {
	
	public final static int UN = 1;
	public final static int DEUX = 2;
	public final static int TROIS = 3;
	public final static int QUATRE = 4;
	public final static int CINQ = 5;
	
	private int id;
	private int contenu;
	
	Carte(int id, int contenu){
		this.id = id;
		this.contenu = contenu ;
	}
	
	public Carte(int contenu){
		this(0, contenu);
	}
	
	public int getID() {
		return id;
	}
	
	public int getContenu() {
		return contenu;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contenu;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carte other = (Carte) obj;
		if (contenu != other.contenu)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	public String toString(){
		String str = "Carte : ";
		switch (this.getContenu()) {
	        case 1: str += Integer.toString(1); break;
	        case 2: str += Integer.toString(2); break;
	        case 3: str += Integer.toString(3); break;
	        case 4: str += Integer.toString(4); break;
	        case 5: str += Integer.toString(5); break;
	        default: throw new RuntimeException("Ajout de carte sur " + this +" impossible");
		}
		return str;
	}
	
}