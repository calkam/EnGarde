package Modele;

public class Test {

	static Jeu jeu;
	
	public static void main(String[] args) throws Exception {
		
		jeu = new Jeu();
		try {
			jeu.init();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		jeu.lancerJeu();
	}
	
}
