package Controleur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Enumeration;

import Modele.Couple;
import Modele.Jeu;
import Modele.Manche;
import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Joueur.Humain.HumainDroit;
import Modele.Joueur.Humain.HumainGauche;
import Modele.Plateau.Case;
import Modele.Tas.Carte;
import Modele.Tas.Main;
import Vue.DessinateurCanvasJavaFx;
import Vue.MainApp;

public class ControleurJeu {

    private MainApp mainApp;

    private Jeu jeu;

    @FXML
    private Canvas terrain;

    @FXML
    private Canvas mainGauche;

    @FXML
    private Canvas mainDroite;

    @FXML
    private Label nbCartePioche;

    @FXML
    private Label nomJoueur1;

    @FXML
    private Label nomJoueur2;

    @FXML
    private CheckBox mainVisible;

    @FXML
    private Pane fondTheatre;

    @FXML
    private Pane tableauFin;

    @FXML
    private Button buttonGestionTour;

    @FXML
    private ButtonBar buttonBarFinPartie;

    @FXML
    private ButtonBar buttonBarFinManche;

    @FXML
    private Label textTableauFin;

    //ATTRIBUT
    private final static int FINPARTIE=0;
    private final static int FINMANCHE=1;

    private final static int FINDETOUR=0;
    private final static int PRETAJOUER=1;

    private ArrayList<Carte> cartes;

    private Joueur joueurEnCours = null;
    private String messageCourant;
    
    private int gestionTour = FINDETOUR;

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void init(Jeu j){
		this.setJeu(j);
		nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
		nomJoueur1.setText(jeu.getJoueur1().getNom());
		nomJoueur2.setText(jeu.getJoueur2().getNom());
		messageCourant = "Au tour de " + jeu.getManche().getTourEnCours().getJoueurPremier().getNom();
		jeu.getManche().getTourEnCours().getMessageBox().setTexte(messageCourant);
		setActionTerrain();
		setActionMain();
		cartes = new ArrayList<Carte>();
		initialiserWidget();
	}

	public void initialiserWidget(){
		changeDisableMain(jeu.getManche().getTourEnCours().getJoueurSecond(), true);
		fondTheatre.setVisible(false);
		tableauFin.setVisible(false);
        mainDroite.toFront();
        mainGauche.toFront();
        terrain.toFront();
        fondTheatre.toFront();
		tableauFin.toFront();
        buttonGestionTour.setDisable(true);
        DessinateurCanvasJavaFx.visibilityActivated = false;
	}

	@FXML
	private void handleIn(){
		Image imageC = new Image("/Ressources/SourisEpeePlante.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}

	@FXML
	private void handleOut(){
		Image imageC = new Image("/Ressources/SourisEpee.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}

	@FXML
	private void handleIsSelected(){
		if(mainVisible.isSelected()){
			DessinateurCanvasJavaFx.visibilityActivated = true;
		}else{
			DessinateurCanvasJavaFx.visibilityActivated = false;
		}
	}

	private boolean modifierActionPossible(int numJoueur, double x, double y){
		Carte carteAction;
		if(numJoueur==1){
			joueurEnCours = jeu.getJoueur1();
		}else{
			joueurEnCours = jeu.getJoueur2();
		}
		if((carteAction = joueurEnCours.getMain().getCarteClick(x, y)) != null){
	    	if(!cartes.contains(carteAction)){
	    		cartes.add(carteAction);
	    	}else{
	    		cartes.remove(carteAction);
	    	}
		}
    	return jeu.getManche().getTourEnCours().possibiliteAction(joueurEnCours, cartes);
	}
	
	private void reinitialiserApresFinManche(){
		gestionTour = FINDETOUR;
		messageCourant = "Au tours de " + jeu.getManche().getTourEnCours().getJoueurPremier().getNom();
		nbCartePioche.setText("15");
        mainVisible.setSelected(false);
		terrain.setDisable(false);
	}

	private void verifierFinDeManche(Joueur joueur, boolean peutFaireAction){
		int resultat;
		if(!peutFaireAction){
			if(joueur.equals(jeu.getManche().getTourEnCours().getJoueurPremier())){
				resultat = Tour.joueurPremierPerdu;
			}else{
				resultat = Tour.joueurSecondPerdu;
			}
			try {
				reinitialiserApresFinManche();
				verifierFinDuJeu(jeu.getManche().finDeManche(resultat));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void verifierFinDeLaPioche(){
		if(jeu.getManche().getPioche().estVide()){
			reinitialiserApresFinManche();
			try {
				verifierFinDuJeu(jeu.getManche().finDeManche(Tour.piocheVide));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void verifierFinDuJeu(Couple<Integer, Integer> resultat){
		Couple<Integer, Integer> resultatFinPartie;
		cartes = new ArrayList<Carte>();
		if(resultat.getC1() == Manche.JOUEUR1GAGNE){
			jeu.changerScore(jeu.getJoueur1());
		}else if(resultat.getC1() == Manche.JOUEUR2GAGNE){
			jeu.changerScore(jeu.getJoueur2());
		}
		DessinateurCanvasJavaFx.visibilityActivated = true;
		resultatFinPartie = jeu.gainPartie();
		if(resultatFinPartie == null){
			afficherWidgetFin(FINMANCHE, resultat);
		}else{
			afficherWidgetFin(FINPARTIE, resultatFinPartie);
		}
	}

	public void cacherWidgetFin(){
		fondTheatre.setVisible(false);
		tableauFin.setVisible(false);
	}

	public void afficherWidgetFin(int typeFin, Couple<Integer, Integer> resultat){
		String joueurVictorieux = "";
		String joueurPerdant = "";
		fondTheatre.setVisible(true);
		tableauFin.setVisible(true);
		if(typeFin == FINPARTIE){
			buttonBarFinPartie.setVisible(true);
			buttonBarFinManche.setVisible(false);

			if(resultat.getC1() == Jeu.JOUEUR1GAGNE){
				joueurVictorieux = jeu.getJoueur1().getNom();
				joueurPerdant = jeu.getJoueur2().getNom();
			}else if(resultat.getC1() == Jeu.JOUEUR2GAGNE){
				joueurVictorieux = jeu.getJoueur2().getNom();
				joueurPerdant = jeu.getJoueur1().getNom();
			}

			textTableauFin.setText(joueurVictorieux + " a triomphé de son adversaire !\n Gloire à " + joueurVictorieux +"!\n "+ joueurPerdant + " est une victime");

		}else if(typeFin == FINMANCHE){
			buttonBarFinPartie.setVisible(false);
			buttonBarFinManche.setVisible(true);
			
			if(resultat.getC1() == Manche.JOUEUR1GAGNE){
				joueurVictorieux = jeu.getJoueur1().getNom();
				joueurPerdant = jeu.getJoueur2().getNom();
			}else if(resultat.getC1() == Manche.JOUEUR2GAGNE){
				joueurVictorieux = jeu.getJoueur2().getNom();
				joueurPerdant = jeu.getJoueur1().getNom();
			}

			if(resultat.getC1() == Manche.MATCHNULLE){

			}else{
				switch(resultat.getC2()){
					case Manche.VICTOIRESIMPLE :
						textTableauFin.setText(joueurVictorieux + " a gagné la manche !\n" + joueurPerdant + " s'est fait victimiser");
						break;
					case Manche.PLUSCARTEATTAQUEDIRECT :
						textTableauFin.setText(joueurVictorieux + " a plus de cartes \n pour attaquer directectement son adversaire.\n" + joueurVictorieux + " a gagné la manche !");
						break;
					case Manche.PLUSCARTEMEDIANE :
						textTableauFin.setText(joueurVictorieux + " étant plus proche de la case médiane\n" + joueurVictorieux + " a gagné la manche !");
						break;
				}
			}

			buttonGestionTour.setText("Fin De Tour");
			buttonGestionTour.setDisable(true);
		}

		mainDroite.setVisible(true);
		mainGauche.setVisible(true);
	}

	//GESTION DES ACTIONS
	private void setActionMain(){
		mainGauche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	modifierActionPossible(1, event.getX(), event.getY());
        	            break;
        	        default:
        	            break;
            	}
            }
        });

		mainDroite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
    	        		modifierActionPossible(2, event.getX(), event.getY());
        	            break;
        	        default:
        	            break;
            	}
            }
        });
	}

	private void setActionTerrain(){
		terrain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            private ActionsJouables actions_jouables;

			@Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	if(joueurEnCours != null){
        	        		boolean caseFound;
        	        		Enumeration<Action> e;
        	        		Tour tour = jeu.getManche().getTourEnCours();
        	        		
    	                	actions_jouables = tour.getActions_jouables();

    	                	if(actions_jouables != null){
		    	            	e = actions_jouables.elements();
	        	        		
	        	        		caseFound = jeu.getManche().getTourEnCours().executerAction(joueurEnCours, (float)event.getX(), (float)event.getY());
	        	        		if(caseFound){
	        	        			buttonGestionTour.setStyle("-fx-background-image:url(/Ressources/finDeTourC.png);");
	        	        			cartes = new ArrayList<Carte>();
	        	        			if(jeu.getManche().getTourEnCours().getEstAttaque().getC1() != Tour.parade){
	        	        				buttonGestionTour.setDisable(false);
	        	        				changeDisableMain(joueurEnCours, true);
	        	        				jeu.getManche().getTourEnCours().getMessageBox().setTexte("Appuyer sur le bouton Fin De Tour");
	        	        				terrain.setDisable(true);
	        	        			}else{
	        	        				jeu.getManche().getTourEnCours().getMessageBox().setTexte("Vous venez de parer lancer une contre-attaque");
	        	        			}
	        	        			
	        	        			//Affichage des messages
	        	        			Action action = null;
	        	        			boolean trouve = false;
	    	    	            	
	    	    	            	Case caseClicked = jeu.getPiste().getCaseEvent(event.getX(), event.getY());
	    	    	            	
	    	    	    			while(e.hasMoreElements() && !trouve){
	    	    	    				action = e.nextElement();
	    	    	    				if(caseClicked.getNumero() == action.getPositionArrivee() ||
	    	    	    				  (tour.actionOffensive(action) && caseClicked.getNumero() == tour.joueurAdverse(joueurEnCours).getPositionFigurine())){
	    	    	    					trouve = true;
	    	    	    				}
	    	    	    			}
	
	    	    	    			if(trouve){
	    	    		    			switch(action.getTypeAction()){
	    	    		    				case Joueur.Reculer :
	    	    		    					messageCourant = joueurEnCours.getNom() + " a reculé de " + action.getCarteDeplacement().getContenu() + " cases";
	    	    		    					break;
	    	    		    				case Joueur.Avancer :
	    	    		    					messageCourant = joueurEnCours.getNom() + " a avancé de " + action.getCarteDeplacement().getContenu() + " cases";
	    	    		    					break;
	    	    		    				case Joueur.AttaqueDirecte :
	    	    		    					messageCourant = joueurEnCours.getNom() + " vous attaque " + action.getNbCartes() + " fois avec une puissance de " + action.getCarteAction().getContenu();
	    	    		    					break;
	    	    		    				case Joueur.AttaqueIndirecte :
	    	    		    					messageCourant = joueurEnCours.getNom() + " a avancé de " + action.getCarteDeplacement().getContenu() + " cases vers la position " + action.getPositionArrivee() + " et vous attaque " + action.getNbCartes() + " fois avec une puissance " + action.getCarteAction().getContenu();
	    	    		    					break;
	    	    		    				case Joueur.Fuite :
	    	    		    					messageCourant = joueurEnCours.getNom() + " a fuis de " + action.getCarteDeplacement().getContenu() + " cases";
	    	    		    					break;
	    	    		    			}
	    	    	    			}
	        	        			
	        	        			nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
	        	        		}
    	                	}
        	        	}
        	            break;
        	            
        	        default:
        	            break;
            	}
            }
        });

		terrain.setOnMouseExited(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event){
				if(!terrain.isDisable()){
					Tour tour = jeu.getManche().getTourEnCours();
					tour.getMessageBox().setTexte(messageCourant);
				}
			}
			
		});
		
		terrain.setOnMouseMoved(new EventHandler<MouseEvent>() {
            private ActionsJouables actions_jouables;

			@Override
            public void handle(MouseEvent event) {
            	Case caseHovered;
            	Enumeration<Action> e;
            	Action action = null;
            	boolean trouve=false;
            	Tour tour;

            	tour = jeu.getManche().getTourEnCours();

            	actions_jouables = tour.getActions_jouables();
            	caseHovered = jeu.getPiste().getCaseEvent(event.getX(), event.getY());

            	if(caseHovered != null && actions_jouables != null && actions_jouables.size() != 0){
	            	e = actions_jouables.elements();

	    			while(e.hasMoreElements() && !trouve){
	    				action = e.nextElement();
	    				if(caseHovered.getNumero() == action.getPositionArrivee() ||
	    				  (tour.actionOffensive(action) && caseHovered.getNumero() == tour.joueurAdverse(joueurEnCours).getPositionFigurine())){
	    					trouve = true;
	    				}
	    			}

	    			if(trouve){
		    			switch(action.getTypeAction()){
		    				case Joueur.Reculer :
		    					tour.getMessageBox().setTexte("Reculer de " + action.getCarteDeplacement().getContenu() + " cases vers la position " + action.getPositionArrivee());
		    					break;
		    				case Joueur.Avancer :
		    					tour.getMessageBox().setTexte("Avancer de " + action.getCarteDeplacement().getContenu() + " cases vers la position " + action.getPositionArrivee());
		    					break;
		    				case Joueur.AttaqueDirecte :
		    					tour.getMessageBox().setTexte("Effectuer " + action.getNbCartes() + " attaque de puissance " + action.getCarteAction().getContenu());
		    					break;
		    				case Joueur.AttaqueIndirecte :
		    					tour.getMessageBox().setTexte("Avancer de " + action.getCarteDeplacement().getContenu() + " cases puis effectuer " + action.getNbCartes() + " attaque de puissance " + action.getCarteAction().getContenu());
		    					break;
		    				case Joueur.Parade :
		    					tour.getMessageBox().setTexte("Parade contre " + action.getNbCartes() + " attaque de puissance " + action.getCarteAction().getContenu());
		    					break;
		    				case Joueur.Fuite :
		    					tour.getMessageBox().setTexte("Fuite de " + action.getCarteDeplacement().getContenu() + " cases vers la position " + action.getPositionArrivee());
		    					break;
		    				default :
		    					tour.getMessageBox().setTexte(messageCourant);
		    			}
	    			}else{
	    				tour.getMessageBox().setTexte(messageCourant);
	    			}
            	}else{
            		tour.getMessageBox().setTexte(messageCourant);
            	}
        	}
        });
	}

	@FXML
	private void gestionTour(){
		if(gestionTour == FINDETOUR){
			finDeTour();
		}else if(gestionTour == PRETAJOUER){
			pretAJouer();
		}
	}

	private void pretAJouer(){
		Tour tour = jeu.getManche().getTourEnCours();
		tour.changerJoueur(joueurEnCours);
		buttonGestionTour.setText("Fin De Tour");
		buttonGestionTour.setDisable(true);
		changeDisableMain(tour.joueurAdverse(joueurEnCours), false);
		gestionTour=FINDETOUR;
		jeu.getPiste().getMessageBox().setTexte(messageCourant);
		terrain.setDisable(false);
	}

	private void finDeTour(){
		boolean peutFaireAction;
		int etatAttaque;
		Tour tour = jeu.getManche().getTourEnCours();
		if(tour.getEstAttaque().getC1() != Tour.parade){
			changeDisableMain(tour.joueurAdverse(joueurEnCours), true);
			changeDisableMain(joueurEnCours, true);
		}

		buttonGestionTour.setText("Prêt A Jouer");
		joueurEnCours.getMain().setVisible(false);
		gestionTour=PRETAJOUER;
		buttonGestionTour.setStyle("-fx-background-image:url(/Ressources/finDeTour.png);");

		peutFaireAction = jeu.getManche().getTourEnCours().adversairePeutFaireAction(joueurEnCours);
		verifierFinDeManche(jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours), peutFaireAction);

		etatAttaque = jeu.getManche().getTourEnCours().getEstAttaque().getC1();
		if(etatAttaque == Tour.pasAttaque || !peutFaireAction){
			verifierFinDeLaPioche();
		}
		
		jeu.getPiste().getMessageBox().setTexte("Au tour de " + jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getNom() + ". Appuyer sur Prêt A Jouer !");
	}

	public void changeDisableMain(Joueur joueur, boolean disable){
		if(joueur.getMain().getCote() == Main.droite){
			mainDroite.setDisable(disable);
		}else{
			mainGauche.setDisable(disable);
		}
	}

	@FXML
	private void mancheSuivante(){
		DessinateurCanvasJavaFx.visibilityActivated = false;
		jeu.nouvelleManche();
		try {
			jeu.lancerLaManche();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jeu.getManche().getTourEnCours().getJoueurPremier().equals(jeu.getJoueur1())){
			mainGauche.setDisable(false);
			mainDroite.setDisable(true);
		}else{
			mainGauche.setDisable(true);
			mainDroite.setDisable(false);
		}
		cacherWidgetFin();
	}

	@FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}

	@FXML
	private void nouvellePartie(){
		String type1 = jeu.getJoueur1() instanceof HumainGauche || jeu.getJoueur1() instanceof HumainDroit ? "Humain" : "IA" ;
		String type2 = jeu.getJoueur2() instanceof HumainGauche || jeu.getJoueur2() instanceof HumainDroit ? "Humain" : "IA" ;
		mainApp.jeu(jeu.getJoueur1().getNom(), jeu.getJoueur2().getNom(), type1, type2);
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

}
