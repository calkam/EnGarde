package Modele;

import java.util.ArrayList;
import java.util.Enumeration;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Plateau.Case;
import Modele.Plateau.MessageBox;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Tour implements Visitable {
	
	// CONSTANTES
	
	// STATUT JOUEUR
	
	public final static boolean JoueurPerdu = false;
	public final static boolean JoueurPasPerdu = true;
	
	// STATUT FIN DE PARTIE

	public final static int joueurPremierPerdu = 0;
	public final static int joueurSecondPerdu = 1;
	public final static int aucunJoueurPerdu = 2;
	public final static int piocheVide = 3;
	
	// ATTRIBUTS
	
	private Joueur joueurPremier;
	private Joueur joueurSecond;
	private Pioche pioche;
	private Defausse defausse;
	private MessageBox messageBox;
	
	// Type de l'attaque, nombre de cartes attaque, valeur de la carte attaque
	private Triplet<Integer, Integer, Integer> estAttaque;
	private ActionsJouables actions_jouables;
	
	// CONSTRUCTEURS
	
	public Tour(){
		this.estAttaque = new Triplet<>(Joueur.PasAttaque, 0, 0);
	}
	
	// CLONE
	
	@Override
	public Tour clone () {
		
		Tour tour = new Tour () ;
		tour.joueurPremier = this.joueurPremier.clone() ;
		tour.joueurSecond = this.joueurSecond.clone() ;
		tour.pioche = this.pioche.clone() ;
		tour.defausse = this.defausse.clone() ;
		
		return tour ;
		
	}
	
	/**
	 * MOTEUR
	 */
	
	private Triplet<Integer, Integer, Integer> avancer_reculer_fuire (Action actionAJouer, Joueur joueur, int typeAction) throws Exception {
		
		Carte carteDeplacement = actionAJouer.getCarteDeplacement() ;
		joueur.defausserCartes(carteDeplacement, 1, defausse) ;
		switch(typeAction) {
		case Joueur.Avancer : joueur.avancer(carteDeplacement.getContenu()) ; break ;
		case Joueur.Reculer : joueur.reculer(carteDeplacement.getContenu()) ; break ;
		default : throw new Exception ("Modele.Tour.jouer_avancer_reculer_fuire : typeAction inconnu") ;
		}
		return new Triplet <> (actionAJouer.getTypeAction() == Joueur.Fuite ? Joueur.Fuite : Joueur.PasAttaque,0,0) ;
		
	}
	
	private Triplet<Integer, Integer, Integer> attaquer_directement_parer (Action actionAJouer, Joueur joueur) throws Exception {
		
		Carte carteAction = actionAJouer.getCarteAction();
		joueur.defausserCartes(carteAction, actionAJouer.getNbCartes(), defausse) ;
		return new Triplet <> (null, actionAJouer.getNbCartes(), carteAction.getContenu()) ;
		
	}
	
	public Triplet<Integer, Integer, Integer> jouerAction(Action actionAJouer, Joueur joueur) throws Exception{
		
		Triplet<Integer, Integer, Integer> config ;
		
		switch(actionAJouer.getTypeAction()) {
		case Joueur.Avancer : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ;
		case Joueur.Reculer : case Joueur.Fuite : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Reculer) ;
		default :
		}
		config = attaquer_directement_parer(actionAJouer, joueur) ;
		switch(actionAJouer.getTypeAction()) {
		case Joueur.AttaqueDirecte : config.setC1(Joueur.AttaqueDirecte); break ;
		case Joueur.AttaqueIndirecte : avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ; config.setC1(Joueur.AttaqueIndirecte) ; break ;
		case Joueur.Parade : config.setC1(Joueur.Parade) ; break ;
		default : throw new Exception("Modele.Tour.executerAction : typeAction inconnu") ;
		}
		
		return config ;
		
	}
	
	/**
	 * VUE
	 */
	
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
	
	public boolean possibiliteAction(Joueur joueur, ArrayList<Carte> cartes) throws Exception {
		
		Action action;
		Enumeration<Action> e;

		actions_jouables = joueur.peutFaireActionAvecCarteSelectionne(joueur.getMain().getCote(), cartes, estAttaque);
			
		if(joueur.peutFaireAction(estAttaque).size() != 0){
			e = actions_jouables.elements();
			
			joueur.getPiste().reinitialiserCouleurCase();
			
			while(e.hasMoreElements()){
				action = e.nextElement();
				if(actionNeutre(action)){
					joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.WHITE);
				}else if(actionOffensive(action)){
					joueur.getPiste().getCases().get(joueurAdverse(joueur).getPositionDeMaFigurine()-1).setCouleur(Case.VERT);
				}else{
					if(action.getTypeAction() == Joueur.Parade){
						joueur.getPiste().getCases().get(joueur.getPositionDeMaFigurine()-1).setCouleur(Case.JAUNE);
					}else if(action.getTypeAction() == Joueur.Fuite){
						joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.JAUNE);
					}
				}
				
				if(joueur.peutFaireAction(estAttaque).size() != 0){
					e = actions_jouables.elements();
	
					joueur.getPiste().reinitialiserCouleurCase();
	
					while(e.hasMoreElements()){
						action = e.nextElement();
						if(actionNeutre(action)){
							joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.WHITE);
						}else if(actionOffensive(action)){
							joueur.getPiste().getCases().get(joueurAdverse(joueur).getPositionDeMaFigurine()-1).setCouleur(Case.VERT);
						}else{
							if(action.getTypeAction() == Joueur.Parade){
								joueur.getPiste().getCases().get(joueur.getPositionDeMaFigurine()-1).setCouleur(Case.JAUNE);
							}else if(action.getTypeAction() == Joueur.Fuite){
								joueur.getPiste().getCases().get(action.getPositionArrivee()-1).setCouleur(Case.JAUNE);
							}
						}
					}
				}
			}
			
			return JoueurPasPerdu;
			
		}
		
		return JoueurPerdu;
		
	}
					
	
	public boolean executerAction(Joueur joueur, float x, float y) throws Exception{
		
		Action action = null;
		Enumeration<Action> e;
		Case caseClicked;
		boolean trouve = false;

		caseClicked = joueur.getPiste().getCaseEvent(x, y);

		if(caseClicked == null){
			return false;
		}

		if(actions_jouables != null && actions_jouables.size() != 0){
			e = actions_jouables.elements();

			while(e.hasMoreElements() && !trouve){
				action = e.nextElement();
				
				if(caseClicked.getNumero() == action.getPositionArrivee() || (actionOffensive(action) && caseClicked.getNumero() == joueurAdverse(joueur).getPositionDeMaFigurine())){
					trouve = true;
				}
			}

			if(trouve){
				
				estAttaque = jouerAction(action, joueur);
				
				if(action.getTypeAction() != Joueur.Parade){
					joueur.remplirMain(pioche);
					//changerJoueur(joueur);
				}else{
					joueur.getMain().deselectionneeToutesLesCartes();
				}

				joueur.getPiste().reinitialiserCouleurCase();
				actions_jouables = null;
				return trouve;
			}else{
				return trouve;
			}
		}else{
			return trouve;
		}
	}

	public boolean adversairePeutFaireAction(Joueur joueur) throws Exception{
		
		ActionsJouables testAction;

		testAction = joueurAdverse(joueur).peutFaireAction(estAttaque);
		
		if(testAction.size() == 0){
			return false;
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

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	public ActionsJouables getActions_jouables() {
		return actions_jouables;
	}

	public void setActions_jouables(ActionsJouables actions_jouables) {
		this.actions_jouables = actions_jouables;
	}

}