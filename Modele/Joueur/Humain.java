package Modele.Joueur;

import Modele.Figurine;

public class Humain extends Joueur {
	
	public Humain(String m_nom) {
		super(m_nom);
	}
	
	public Humain(String m_nom, Figurine m_figurine) {
		super(m_nom, m_figurine);
	}

}