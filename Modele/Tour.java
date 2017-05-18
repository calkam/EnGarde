package Modele;

import java.util.ArrayList;
import java.util.Enumeration;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Humain;
import Modele.Joueur.Joueur;
import Modele.Plateau.Piste;
import Modele.Plateau.Case;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Tour implements Visitable{
	
	// CONSTANTES
	// STATUT JOUEUR
	
	private final static boolean joueurPerdu = false;
	private final static boolean joueurPasPerdu = true;
	
	// STATUT TOUR
	
	public final static int joueurPremierPerdu = 0;
	public final static int joueurSecondPerdu = 1;
	public final static int aucunJoueurPerdu = 2;
	public final static int piocheVide = 3;
	
	// STATUT ATTAQUE
	
	public final static int PasAttaque = 0;
	public final static int AttaqueDirecte = 1;
	public final static int AttaqueIndirecte = 2;
	
	// ATTRIBUTS
	
	private Joueur joueurPremier;
	private Joueur joueurSecond;
	private Pioche pioche;
	private Defausse defausse;
	private Piste piste ;
	private Historique histo ;
	private ActionsJouables actions_jouables ;
	// Type de l'attaque, nombre de cartes attaque, valeur de la carte attaque
	private Triplet<Integer, Integer, Integer> estAttaque;
	
	// CONSTRUCTEURS
	
	public Tour(Piste piste, Historique histo){
		
		this.piste = piste ;
		this.histo = histo ;
		this.estAttaque = new Triplet<>(PasAttaque, 0, 0);
	
	}
	
	public Tour(Joueur m_joueurPremier, Joueur m_joueurSecond){
		this.joueurPremier = m_joueurPremier;
		this.joueurSecond = m_joueurSecond;
		this.pioche = new Pioche();
		this.defausse = new Defausse();
		this.estAttaque = new Triplet<>(PasAttaque, 0, 0);
	}
	
	// CLONE
	
	@Override
	public Tour clone () {
		
		Tour tour = new Tour (this.piste, this.histo) ;
		tour.joueurPremier = this.joueurPremier.clone() ;
		tour.joueurSecond = this.joueurSecond.clone() ;
		tour.pioche = this.pioche.clone() ;
		tour.defausse = this.defausse.clone() ;
		
		return tour ;
		
	}
	
	/**
	 * MOTEUR
	 **/
	
	public int jouerTourMoteur() throws Exception{
		
		if(jouerTourJoueur(joueurPremier)){
			if(pioche.estVide()){
				if(estAttaque.getC1() == PasAttaque){
					return piocheVide;
				}
			}
			
			if(jouerTourJoueur(joueurSecond)){
				if(pioche.estVide()){
					if(estAttaque.getC1() == PasAttaque){
						return piocheVide;
					}
				}
				
				return aucunJoueurPerdu;
			}else{
				return joueurSecondPerdu;
			}
		}else{
			return joueurPremierPerdu;
		}
	}
	
	private Triplet <Boolean, Action, ActionsJouables> jouerActionJoueurMoteur (Joueur joueur, Triplet <Boolean, Action, ActionsJouables> config) throws Exception {
		
		System.out.println("Joueur : " + joueur.getNom() + ", position : " + joueur.getPositionDeMaFigurine());
		System.out.println("Main : " + joueur.getMain().getMain());
		System.out.println("Nb cartes pioche : " + pioche.getNombreCarte() + "\n");
		piste.afficherPiste();
		
		if(histo != null && (joueur instanceof Humain)) {
			histo.ajouterTour(this);
			System.out.println(histo) ;
		}
		
		config.setC3(joueur.peutFaireAction(estAttaque));
			
		if(config.getC3().isEmpty()) {
			config.setC1(joueurPerdu);
			return config ;
		}
		
		config.setC3(joueur.peutFaireAction(estAttaque));
		config.setC2(joueur.selectionnerAction(config.getC3(), this));		
		System.out.println(joueur.getNom() + " a joué :\n"+ config.getC2());	
		estAttaque = executerActionMoteur(config.getC2(), joueur) ;
		config.setC1(joueurPasPerdu);
		return config ;
		
	}
	
	
	public boolean jouerTourJoueurMoteur(Joueur joueur) throws Exception{
		
		Triplet <Boolean, Action, ActionsJouables> config = new Triplet <> (null, null, null) ;
		
		for (int i = 0 ; i < 3 ; i++) System.out.println("/*************************************************************************************************************/");
		System.out.println();
		
		if ((config = jouerActionJoueurMoteur(joueur, config)).getC1() == joueurPerdu) return config.getC1() ;
		
		if(config.getC2().getTypeAction() == Joueur.Parade && !pioche.estVide())
			if ((config = jouerActionJoueurMoteur(joueur, config)).getC1() == joueurPerdu) return config.getC1() ;
		
		joueur.remplirMain(pioche);
		return config.getC1() ;
	}
	
	private Triplet<Integer, Integer, Integer> avancer_reculer_fuire (Action actionAJouer, Joueur joueur, int typeAction) throws Exception {
		
		Carte carteDeplacement = actionAJouer.getCarteDeplacement() ;
		joueur.defausserCartes(carteDeplacement, 1, defausse) ;
		switch(typeAction) {
		case Joueur.Avancer : joueur.avancer(carteDeplacement.getContenu()) ; break ;
		case Joueur.Reculer : joueur.reculer(carteDeplacement.getContenu()) ; break ;
		default : throw new Exception ("Modele.Tour.jouer_avancer_reculer_fuire : typeAction inconnu") ;
		}
		return new Triplet <> (PasAttaque,0,0) ;
		
	}
	
	private Triplet<Integer, Integer, Integer> attaquer_directement_parer (Action actionAJouer, Joueur joueur) throws Exception {
		
		Carte carteAction = actionAJouer.getCarteAction();
		joueur.defausserCartes(carteAction, actionAJouer.getNbCartes(), defausse) ;
		return new Triplet <> (null, actionAJouer.getNbCartes(), carteAction.getContenu()) ;
		
	}
	
	private Triplet<Integer, Integer, Integer> executerActionMoteur(Action actionAJouer, Joueur joueur) throws Exception{
		
		Triplet<Integer, Integer, Integer> config ;
		
		switch(actionAJouer.getTypeAction()) {
		case Joueur.Avancer : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ;
		case Joueur.Reculer : case Joueur.Fuite : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Reculer) ;
		default :
		}
		config = attaquer_directement_parer(actionAJouer, joueur) ;
		switch(actionAJouer.getTypeAction()) {
		case Joueur.AttaqueDirecte : config.setC1(AttaqueDirecte); break ;
		case Joueur.AttaqueIndirecte : avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ; config.setC1(AttaqueIndirecte) ; break ;
		case Joueur.Parade : config.setC1(PasAttaque) ; break ;
		default : throw new Exception("Modele.Tour.executerAction : typeAction inconnu") ;
		}
		
		return config ;
		
	}
	
	public boolean actionNeutre(Action action){
		return action.getTypeAction() == Joueur.Reculer || action.getTypeAction() == Joueur.Avancer; 
	}
	
	public boolean actionOffensive(Action action){
		return action.getTypeAction() == Joueur.AttaqueDirecte || action.getTypeAction() == Joueur.AttaqueIndirecte; 
	}
	
	public Joueur joueurAdverse(Joueur joueur){
		if(joueur.equals(joueurPremier)){
			return joueurSecond;
		}else{
			return joueurPremier;
		}
	}
	
	public void jouerTour() throws Exception{
		commencerTour(joueurPremier);
	}
	
	public void commencerTour(Joueur joueur){
		joueur.getMain().setVisible(true);
	}
	
	public boolean possibiliteAction(Joueur joueur, ArrayList<Carte> cartes){
		try {
			Action action;
			Enumeration<Action> e;
			
			actions_jouables = joueur.peutFaireActionAvecCarteSelectionne(joueur.getMain().getCote(), cartes, estAttaque);
			
			if(joueur.peutFaireAction(estAttaque).size() != 0){
				e = actions_jouables.elements();
				
				joueur.getPiste().reinitialiserCouleurCase();
				
				while(e.hasMoreElements()){
					action = e.nextElement();
					System.out.println(action);
					if(actionNeutre(action)){
						joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.ROUGE);
					}else if(actionOffensive(action)){
						joueur.getPiste().getCases().get(joueurAdverse(joueur).getPositionFigurine()-1).setCouleur(Case.VERT);
					}else{
						if(action.getTypeAction() == Joueur.Parade){
							joueur.getPiste().getCases().get(joueur.getPositionFigurine()-1).setCouleur(Case.JAUNE);
						}else if(action.getTypeAction() == Joueur.Fuite){
							joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.JAUNE);
						}
					}
				}
				System.out.println("");
				return joueurPasPerdu;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return joueurPerdu;
	}

	public boolean executerAction(Joueur joueur, float x, float y){
		Action action = null;
		Enumeration<Action> e;
		Case caseClicked;
		boolean trouve = false;
		
		caseClicked = joueur.getPiste().getCaseClicked(x, y);
		
		if(actions_jouables != null && actions_jouables.size() != 0){
			e = actions_jouables.elements();
			
			while(e.hasMoreElements() && !trouve){
				action = e.nextElement();
				if(caseClicked.getNumero() == action.getPositionArrivee() || (actionOffensive(action) && 
				   caseClicked.getNumero() == joueurAdverse(joueur).getPositionFigurine())){
					trouve = true;
				}
			}
			
			if(trouve){
				try {
					estAttaque = jouerAction(action, joueur);
					if(action.getTypeAction() != Joueur.Parade){
						remplirMain(joueur);
						changerJoueur(joueur);
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				joueur.getPiste().reinitialiserCouleurCase();
				actions_jouables = null;
				return joueurPasPerdu;
			}else{
				return joueurPasPerdu;
			}
		}else{
			return joueurPerdu;
		}
	}
	
	public boolean adversairePeutFaireAction(Joueur joueur){
		ActionsJouables testAction;
		
		try {
			testAction = joueur.peutFaireAction(estAttaque);
			if(testAction == null || testAction.size() == 0){
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void changerJoueur(Joueur joueur){		
		joueur.getMain().setVisible(false);
		joueurAdverse(joueur).getMain().setVisible(true);
	}
	
	/**
	 * GETTER/SETTER
	 */
	public Pioche getPioche() {
		return pioche;
	}

	public Joueur getJoueurPremier() {
		return joueurPremier;
	}

	public void setJoueurPremier(Joueur joueurPremier) {
		this.joueurPremier = joueurPremier;
	}

	public Joueur getJoueurSecond() {
		return joueurSecond;
	}

	public void setJoueurSecond(Joueur joueurSecond) {
		this.joueurSecond = joueurSecond;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	public Defausse getDefausse() {
		return this.defausse;
	}
	
	public void setDefausse(Defausse defausse) {
		this.defausse = defausse;
	}

	public Triplet<Integer, Integer, Integer> getEstAttaque() {
		return estAttaque;
	}

	public void setEstAttaque(Triplet<Integer, Integer, Integer> estAttaque) {
		this.estAttaque = estAttaque;
	}
	
	@Override
	public boolean accept(Visiteur v) {
		// TODO Auto-generated method stub
		boolean retour = false;
		retour = retour || pioche.accept(v);
		retour = retour || defausse.accept(v);
		retour = retour || joueurPremier.accept(v);
		retour = retour || joueurSecond.accept(v);
		return retour;
	}
	
}