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
		System.out.println(jeu.getJoueur1());
		System.out.println(jeu.getPioche());
	}
	
}
