package Modele.Joueur.Humain;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;
import Modele.Triplet;
import Modele.Joueur.Action;
import Modele.Joueur.Joueur;
import Modele.Joueur.JoueurGauche;
import Modele.Plateau.Piste;

public class HumainGauche extends JoueurGauche {

	public HumainGauche(String nom, Main main, Piste piste) {
		super(nom, main, piste);
	}
	
	@Override
	public Joueur clone () {
		
		HumainGauche joueur = new HumainGauche(this.nom, this.main.clone(), this.piste.clone()) ;
		joueur.setScore(this.getScore());
		return joueur ;
		
	}

	@Override
	public Action actionIA(Triplet<Integer, Integer, Integer> attaque, Pioche pioche, Defausse defausse)
			throws Exception {
		
		throw new UnsupportedOperationException("Modele.Joueur.Humain.HumainGauche.actionIA : Méthode non supportée par un joueur humain") ;
	
	}

	

}