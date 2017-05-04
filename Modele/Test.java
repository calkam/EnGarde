package Modele;
public class Test {

	static Jeu jeu;
	
	public static void main(String[] args) {
		jeu = new Jeu();
		jeu.init();
		System.out.println(jeu.getJoueur1());
		System.out.println(jeu.getPioche());
	}
	
}
