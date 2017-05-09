package Modele;

public class Test {

	static Jeu jeu;
	
	public static void main(String[] args) {
		
		jeu = new Jeu();
		try {
			jeu.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jeu.initialiserPremiereManche();
		jeu.lancerLaManche();
		
	}
	
}
