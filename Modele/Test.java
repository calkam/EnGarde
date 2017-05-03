import Modele.*;
public class Test {

	public static void main(String[] args) {
		Pioche  p = new Pioche();
		p.melanger();
		System.out.println(p);

		Main m = new Main();
		m.ajouter(p.piocher());
		m.ajouter(p.piocher());
		m.ajouter(p.piocher());
		System.out.println(m);
		System.out.println(p);
		
		m.supprimer(m.getCarte(0));
		System.out.println(m);
	}

}
