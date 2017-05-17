package Modele;

import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Joueur.Humain.Humain;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Pioche;

public class Tour{
	
	private final static int nombreCarteMax = 5;
	
	private final static boolean joueurPerdu = false;
	private final static boolean joueurPasPerdu = true;
	
	public final static int joueurPremierPerdu = 0;
	public final static int joueurSecondPerdu = 1;
	public final static int aucunJoueurPerdu = 2;
	public final static int piocheVide = 3;
	
	public final static int PasAttaque = 0;
	public final static int AttaqueDirecte = 1;
	public final static int AttaqueIndirecte = 2;
	
	private Joueur joueurPremier;
	private Joueur joueurSecond;
	private Pioche pioche;
	private Defausse defausse;
	private Historique histo ;
	// Type de l'attaque, nombre de cartes attaque, valeur de la carte attaque
	private Triplet<Integer, Integer, Integer> estAttaque;
	
	public Tour(Historique histo){
		
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
	
	public int jouerTour() throws Exception{
		
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
	
	public boolean jouerTourJoueur(Joueur joueur) throws Exception{
		
		Action choixAction ;	
		ActionsJouables actions_jouables ;
		
		System.out.println("/*************************************************************************************************************/");
		System.out.println("Joueur : " + joueur.getNom() + ", position : " + joueur.getPositionDeMaFigurine());
		System.out.println("Main : " + joueur.getMain().getMain());
		System.out.println("Nb cartes pioche : " + pioche.getNombreCarte() + "\n");
		afficherPiste(joueurPremier.getPositionDeMaFigurine(), joueurSecond.getPositionDeMaFigurine());
		
		if(histo != null && (joueur instanceof Humain)) {
			
			histo.ajouterTour(this);
			System.out.println(histo) ;
			
		}
		
		actions_jouables = joueur.peutFaireAction(estAttaque);
			
		if(actions_jouables.isEmpty()) {
			return joueurPerdu;
		}
		
		actions_jouables = joueur.peutFaireAction(estAttaque);
		choixAction = joueur.selectionnerAction(actions_jouables, this);		
		System.out.println(choixAction);	
		
		estAttaque = executerAction(choixAction, joueur) ;
		
		if(choixAction.getTypeAction() == Joueur.Parade && !pioche.estVide()){
			
			System.out.println("Joueur : " + joueur.getNom() + ", position : " + joueur.getPositionDeMaFigurine());
			System.out.println("Main : " + joueur.getMain().getMain() + "\n");
			
			actions_jouables = joueur.peutFaireAction(estAttaque);
			
			if(actions_jouables.isEmpty()) {
				return joueurPerdu;
			}
			
			actions_jouables = joueur.peutFaireAction(estAttaque);
			choixAction = joueur.selectionnerAction(actions_jouables, this);		
			System.out.println(choixAction);			
			
			estAttaque = executerAction(choixAction, joueur);
		
		}
		
		remplirMain(joueur);
		
		System.out.println("Joueur : " + joueur.getNom() + ", position : " + joueur.getPositionDeMaFigurine());
		System.out.println("Main : " + joueur.getMain().getMain() + "\n");
		System.out.println("Nb cartes pioche : " + pioche.getNombreCarte() + "\n");
		
		return joueurPasPerdu;
	}

	public void remplirMain(Joueur j){		
		int nbCarteMain = j.getMain().getNombreCarte();
		
		int i=nbCarteMain;
			
		while(!pioche.estVide() && i < nombreCarteMax){
			j.ajouterCarteDansMain(pioche.piocher());
			i++;
		}
	}
	
	private void DefausserCartes(int valeur, int nbCartes, Joueur joueur, Defausse defausse) throws Exception{
		
		int i=1; //On commence à 1 car une carte a déjà été défaussée dans executerAction()
		
		for(Carte c : joueur.getCartesDeLaMain()){
				
			if(c.getContenu() == valeur){
				defausse.ajouter(c);
				joueur.defausserUneCarte(c);
				i++;
			}
			
			if(i>=nbCartes){
				break;
			}
		}	
	}
	
	private Triplet<Integer, Integer, Integer> avancer_reculer_fuire (Action actionAJouer, Joueur joueur, int typeAction) throws Exception {
		
		Carte carteDeplacement = actionAJouer.getCarteDeplacement() ;
		defausse.ajouter(carteDeplacement) ;
		joueur.defausserUneCarte(carteDeplacement) ;
		switch(typeAction) {
		case Joueur.Avancer : joueur.avancer(carteDeplacement.getContenu()) ; break ;
		case Joueur.Reculer : joueur.reculer(carteDeplacement.getContenu()) ; break ;
		default : throw new Exception ("Modele.Tour.jouer_avancer_reculer_fuire : typeAction inconnu") ;
		}
		return new Triplet <> (PasAttaque,0,0) ;
		
	}
	
	private Triplet<Integer, Integer, Integer> attaquer_directement_parer (Action actionAJouer, Joueur joueur) throws Exception {
		
		Carte carteAction = actionAJouer.getCarteAction();
		defausse.ajouter(carteAction);
		joueur.defausserUneCarte(carteAction);
		
		if(actionAJouer.getNbCartes() > 1) {
			DefausserCartes (carteAction.getContenu(), actionAJouer.getNbCartes(), joueur, defausse) ;
		}
		
		return new Triplet <> (null, actionAJouer.getNbCartes(), carteAction.getContenu()) ;
		
	}
	
	private Triplet<Integer, Integer, Integer> executerAction(Action actionAJouer, Joueur joueur) throws Exception{
		
		Triplet<Integer, Integer, Integer> config ;
		
		switch(actionAJouer.getTypeAction()) {
		case Joueur.Avancer : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ;
		case Joueur.Reculer : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Reculer) ;
		case Joueur.Fuite   : return avancer_reculer_fuire(actionAJouer, joueur, Joueur.Reculer) ;
		default :
		}
		
		System.out.println(actionAJouer) ;
		
		config = attaquer_directement_parer(actionAJouer, joueur) ;
		switch(actionAJouer.getTypeAction()) {
		case Joueur.AttaqueDirecte : config.setC1(AttaqueDirecte); break ;
		case Joueur.AttaqueIndirecte : avancer_reculer_fuire(actionAJouer, joueur, Joueur.Avancer) ; config.setC1(AttaqueIndirecte) ; break ;
		case Joueur.Parade : config.setC1(PasAttaque) ; break ;
		default : throw new Exception("Modele.Tour.executerAction : typeAction inconnu") ;
		
		}
		
		return config ;
		
	}
	
	/*private Triplet<Integer, Integer, Integer> executerAction(Action actionAJouer, Joueur joueur) throws Exception{
		Carte carteDeplacement=null;
		Carte carteAction=null;
		
		int typeAction;
		int nbCartesAttqJouees;
		int valeurCarteAttqJouee;
		
		ArrayList<Carte> cartesDeMemeValeur;
		
		switch(actionAJouer.getTypeAction()){
			case Joueur.Reculer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = PasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Avancer :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = PasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.AttaqueDirecte : 
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						defausse.ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}
				typeAction = attaqueDirect; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.AttaqueIndirecte :
				// On avance dans un premier temps
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.avancer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				
				// On attaque dans un second temps	
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						defausse.ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}
				typeAction = attaqueIndirect; nbCartesAttqJouees = actionAJouer.getNbCartes(); valeurCarteAttqJouee = carteAction.getContenu();
				break;
			case Joueur.Parade :
				carteAction = actionAJouer.getCarteAction();
				defausse.ajouter(carteAction);
				joueur.defausserUneCarte(carteAction);
				if(actionAJouer.getNbCartes() > 1){
					cartesDeMemeValeur = getAutreCarteDeValeur(carteAction.getContenu(), actionAJouer.getNbCartes(), joueur);
					for(Carte c : cartesDeMemeValeur){
						defausse.ajouter(c);
						joueur.defausserUneCarte(c);
					}
				}				
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			case Joueur.Fuite :
				carteDeplacement = actionAJouer.getCarteDeplacement();
				joueur.reculer(carteDeplacement.getContenu());
				defausse.ajouter(carteDeplacement);
				joueur.defausserUneCarte(carteDeplacement);
				typeAction = pasAttaque; nbCartesAttqJouees = 0; valeurCarteAttqJouee = 0;
				break;
			default: throw new Exception("Erreur lors de l'exécution de l'action");
		}
		
		System.out.println("\nVous avez joué : carte de déplacement : " + carteDeplacement + ", carte d'action : " + carteAction);
		
		return new Triplet<>(typeAction, nbCartesAttqJouees, valeurCarteAttqJouee);
	}*/
	
	public void afficherPiste(int positionF1, int positionF2){
		String str = "";
		String strPosition = "";
		
		for(int i = 1 ; i < 24; i++){
			if(i == positionF1){
				str += "♟   ";
			}else if(i == positionF2){
				str += "♙   ";
			}else{
				str += "_   ";
			}
			
			if(i < 10){
				strPosition += i + "   ";		
			}else{
				strPosition += i + "  ";
			}
		}
		
		strPosition += "\n";
		
		System.out.println(str);
		System.out.println(strPosition);
	}
	
	@Override
	public Tour clone () {
		
		Tour tour = new Tour (this.histo) ;
		tour.joueurPremier = this.joueurPremier.clone() ;
		tour.joueurSecond = this.joueurSecond.clone() ;
		tour.pioche = this.pioche.clone() ;
		tour.defausse = this.defausse.clone() ;
		
		return tour ;
		
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
	
}