package Modele.Plateau;

import Modele.Visitable;
import Modele.Visiteur;
import Modele.Composant.ComposantGraphique;
import Modele.Composant.ObjetMouvant;
import Modele.Composant.Point;

public class Jeton extends ObjetMouvant implements Visitable {

	public Jeton(ComposantGraphique d, Point v) {
		super(d, v);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		return false;
	}
}