package Controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Enumeration;

import Modele.Couple;
import Modele.Jeu;
import Modele.Manche;
import Modele.Sauvegarde;
import Modele.Tour;
import Modele.Joueur.Action;
import Modele.Joueur.ActionsJouables;
import Modele.Joueur.Joueur;
import Modele.Joueur.IA.IA;
import Modele.Joueur.Humain;
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
    //Variable static pour regarder dans verifierFinDeJeu si on est
    //dans une fin de partie ou de manche
    private final static int FINPARTIE=0;
    private final static int FINMANCHE=1;

    //Variable static pour indiquer dans gestionTour si on doit afficher
    //prêt à jouer ou  fin de tour
    private final static int FINDETOUR=0;
    private final static int PRETAJOUER=1;

    //arrayList de carte dans lequel on stock les cartes seléctionné pour
    //définir les action jouables et exécuter les actions
    private ArrayList<Carte> cartes;

    //variable static représentant le joueur entrain de jouer
    //initilialiser dans modificationActionPossible
    public static Joueur joueurEnCours = null;

    //Chaine de caractère définissant le message a afficher
    //dans la messageBox du jeu
    private String messageCourant;

    //Variable s'utilisant avec les variables static
    //- FINDETOUR
    //- PRETAJOUER
    private int gestionTour = FINDETOUR;

    //partie mainApp (pas important pour la compréhension du Controleur)
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	//Initialisation du jeu 2 paramètres
	//j pour le jeu, et bjeu pour indiquer si y vient de la sauvegarde
	//ou du menu principal
	public void init(Jeu j, boolean bjeu){
		//on initialise le jeu
		this.setJeu(j);

		//On modifie le nombre de carte dans la pioche
		nbCartePioche.setText(Integer.toString(15));
		//on initialise les labels de nom des joueurs
		nomJoueur1.setText(jeu.getJoueur1().getNom());
		nomJoueur2.setText(jeu.getJoueur2().getNom());
		//on initialise le message courant de la message Box
		messageCourant = "Au tour de " + jeu.getManche().getTourEnCours().getJoueurPremier().getNom();
		//on initilialise la message Box
		jeu.getManche().getTourEnCours().getMessageBox().setTexte(messageCourant);
		//on définit les écouteurs d'évènements sur le terrain (cf. fonctionnement des events JavaFX)
		setActionTerrain();
		setActionMain();
		//On initilialise les cartes seléctionné a vide
		cartes = new ArrayList<Carte>();
		//on initilialise les widgets
		initialiserWidget();

		joueurEnCours = jeu.getManche().getTourEnCours().getJoueurPremier();

		//partie cédric Sauvegarde / Revenir Au Jeu
		if(!bjeu){
			if(mainApp.getActionFaites() == Sauvegarde.FINDETOUR){
				messageCourant = "Appuyer sur Fin de Tour";
				jeu.getManche().getTourEnCours().getMessageBox().setTexte(messageCourant);

				changeDisableMain(jeu.getManche().getTourEnCours().getJoueurPremier(), true);

				buttonGestionTour.setDisable(false);
				buttonGestionTour.setText("Fin de Tour");
				gestionTour = FINDETOUR;
				buttonGestionTour.setStyle("-fx-background-image:url(finDeTourC.png);");

			}else if(mainApp.getActionFaites() == Sauvegarde.ENTREDEUX){
				messageCourant = "Appuyer sur prêt ";
				jeu.getManche().getTourEnCours().getMessageBox().setTexte(messageCourant);

				changeDisableMain(jeu.getManche().getTourEnCours().getJoueurPremier(), true);
				changeDisableMain(jeu.getManche().getTourEnCours().getJoueurSecond(), true);

				buttonGestionTour.setDisable(false);
				buttonGestionTour.setText("Pret à jouer");
				gestionTour = PRETAJOUER;
				buttonGestionTour.setStyle("-fx-background-image:url(finDeTour.png);");
			}else{
				changeDisableMain(jeu.getManche().getTourEnCours().getJoueurPremier(), false);
				changeDisableMain(jeu.getManche().getTourEnCours().getJoueurSecond(), true);
			}
		}

		//test si il y a une IA qui commence si oui joue son coup
		debutJeu();
	}

	public void debutJeu(){
		if(jeu.getManche().getTourEnCours().getJoueurPremier() instanceof IA){
			joueurEnCours = jeu.getManche().getTourEnCours().getJoueurPremier();
			if(jeu.getManche().getTourEnCours().getJoueurSecond() instanceof IA){
				joueurEnCours.getMain().setVisible(true);
				jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getMain().setVisible(false);
			}else{
				joueurEnCours.getMain().setVisible(false);
				jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getMain().setVisible(true);
			}
			changeDisableMain(joueurEnCours, true);
			Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			    	jouerIA(joueurEnCours);
			    }

			}));
			timer.play();
		}
	}

	//OutKast power - Hey Ah !
	public void jouerIA(Joueur joueur){
		try {
			boolean aParer = false;

			Action action;
			Case c;
			Tour tour;
			ActionsJouables actions_jouables = new ActionsJouables();

			joueurEnCours = joueur;

			tour = jeu.getManche().getTourEnCours();

			action = joueur.actionIA(tour);

			if(action != null){

				actions_jouables.ajouterAction(action);

				c = jeu.getPiste().getCasesNumero(action.getPositionArrivee());

				tour.setActionsJouables(actions_jouables);

				jeu.getManche().getTourEnCours().executerAction(joueur, c.getX()+5, c.getY()+5);

				if(jeu.getManche().getTourEnCours().getEstAttaque().getC1() == Joueur.Parade){
					messageCourant = joueurEnCours.getNom() + " a parer";
    				tour.getMessageBox().setTexte(messageCourant);

    				aParer = true;

					action = joueur.actionIA(tour);

					if(action != null){
						actions_jouables.ajouterAction(action);
						c = jeu.getPiste().getCasesNumero(action.getPositionArrivee());
						tour.setActionsJouables(actions_jouables);
						jeu.getManche().getTourEnCours().executerAction(joueur, c.getX()+5, c.getY()+5);
					}
				}

				switch(action.getTypeAction()){
					case Joueur.Reculer :
						if(action.getCarteDeplacement().getContenu() > 1){
							messageCourant = joueurEnCours.getNom() + " a reculé de " + action.getCarteDeplacement().getContenu() + " cases";
						}else{
							messageCourant = joueurEnCours.getNom() + " a reculé de " + action.getCarteDeplacement().getContenu() + " case";
						}
						break;
					case Joueur.Avancer :
						if(action.getCarteDeplacement().getContenu() > 1){
							messageCourant = joueurEnCours.getNom() + " a avancé de " + action.getCarteDeplacement().getContenu() + " cases";
						}else{
							messageCourant = joueurEnCours.getNom() + " a reculé de " + action.getCarteDeplacement().getContenu() + " case";
						}
						break;
					case Joueur.AttaqueDirecte :
						if(action.getNbCartes() > 1){
							messageCourant = "Vous devez parer avec " + action.getNbCartes() + " cartes de valeur " + action.getCarteAction().getContenu();
						}else{
							messageCourant = "Vous devez parer avec " + action.getNbCartes() + " carte de valeur " + action.getCarteAction().getContenu();
						}
						break;
					case Joueur.AttaqueIndirecte :
						if(action.getNbCartes() > 1){
							messageCourant = "Vous devez parer avec " + action.getNbCartes() + " cartes de valeur " + action.getCarteAction().getContenu() + " ou fuire";
						}else{
							messageCourant = "Vous devez parer avec " + action.getNbCartes() + " carte de valeur " + action.getCarteAction().getContenu() + " ou fuire";
						}
						break;
					case Joueur.Fuite :
						if(action.getCarteDeplacement().getContenu() > 1){
							messageCourant = joueurEnCours.getNom() + " a fui de " + action.getCarteDeplacement().getContenu() + " cases";
						}else{
							messageCourant = joueurEnCours.getNom() + " a fui de " + action.getCarteDeplacement().getContenu() + " case";
						}
						break;
				}

				jeu.getManche().getTourEnCours().getMessageBox().setTexte(messageCourant);

				joueur.getMain().setVisible(false);
			}

			finDeTourIA();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finDeTourIA() throws Exception{
		boolean peutFaireAction;
		boolean mancheTerminee;

		Tour tour = jeu.getManche().getTourEnCours();
		int etatAttaque = tour.getEstAttaque().getC1();

		ActionsJouables actionsTourSuivant = joueurEnCours.peutFaireAction(tour.getEstAttaque());

		if(etatAttaque == Joueur.Parade && !tour.getPioche().estVide() && (actionsTourSuivant.size() == 0 || actionsTourSuivant == null)){
			verifierFinDeManche(joueurEnCours, false);
		}else{
			//on passe le button a prêt à jouer
			buttonGestionTour.setText("Fin De Tour");
			buttonGestionTour.setDisable(true);
			gestionTour=FINDETOUR;
			buttonGestionTour.setStyle("-fx-background-image:url(finDeTour.png);");

			tour.joueurAdverse(joueurEnCours).getMain().setVisible(true);
			changeDisableMain(tour.joueurAdverse(joueurEnCours), false);
			terrain.setDisable(false);

			nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));

			//on test les actions de l'adversaire
			peutFaireAction = tour.adversairePeutFaireAction(joueurEnCours);
			//on vérifie la fin de la manche par rapport à ca
			mancheTerminee = verifierFinDeManche(tour.joueurAdverse(joueurEnCours), peutFaireAction);

			//on test si il n'y pas fin de la pioche
			if(etatAttaque == Joueur.PasAttaque || etatAttaque == Joueur.Parade){ //|| !peutFaireAction){
				mancheTerminee = verifierFinDeLaPioche();
			}

			if(peutFaireAction && tour.joueurAdverse(joueurEnCours) instanceof IA && !mancheTerminee){
				Timeline timer = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
				    	jouerIA(tour.joueurAdverse(joueurEnCours));
				    }
				}));
				timer.play();
			}
		}
	}

	//Fonction d'initilisation des Widgets(Labels, button, etc...)
	public void initialiserWidget(){
		//on empêche de cliquer sur la main du joueur en seconde position
		changeDisableMain(jeu.getManche().getTourEnCours().getJoueurSecond(), true);
		//on cache le tableau de fin de partie et de fin de manche
		fondTheatre.setVisible(false);
		tableauFin.setVisible(false);
		//on met les mains en premier plans et on rend les canvas des mains visibles
        mainDroite.toFront();
        mainGauche.toFront();
        mainDroite.setVisible(true);
        mainGauche.setVisible(true);
        //on met le terrain et le tableau de fin et le voile au premier plan
        terrain.toFront();
        fondTheatre.toFront();
		tableauFin.toFront();
		//on empêche le click sur le bouttons fin de tour
        buttonGestionTour.setDisable(true);
        //on met la visibilité des cartes de la main à faux
        DessinateurCanvasJavaFx.visibilityActivated = false;

        if(jeu.getJoueur1() instanceof IA && jeu.getJoueur2() instanceof IA){
        	jeu.getJoueur1().getMain().setVisible(true);
        	jeu.getJoueur2().getMain().setVisible(true);
        }
	}

	//Modifier action possible 2 paramètres
	//numéro du joueur : 1, 2 (resp. joueur1, joueur2)
	//x, y : position du click sur une carte
	private boolean modifierActionPossible(int numJoueur, double x, double y) throws Exception{
		Carte carteAction;
		//on initialise le joueurEncours par rapport au numJoueur
		if(numJoueur==1){
			joueurEnCours = jeu.getJoueur1();
		}else{
			joueurEnCours = jeu.getJoueur2();
		}
		//si la carte cliquer existe
		if((carteAction = joueurEnCours.getMain().getCarteClick(x, y)) != null){
			//on ajoute la carte si elle est seléctionné:
			//sinon on la supprime de carte
	    	if(!cartes.contains(carteAction)){
	    		cartes.add(carteAction);
	    	}else{
	    		cartes.remove(carteAction);
	    	}
		}
		//on regarde les actions possible pour le joueur, avec les cartes seléctionné
    	return jeu.getManche().getTourEnCours().possibiliteAction(joueurEnCours, cartes);
	}

	//On réinitialise la partie après une fin de manche
	private void reinitialiserApresFinManche(){
		//on remet le bouton fin de tour
		gestionTour = FINDETOUR;
		//on change le message courant de la messageBox
		messageCourant = "Au tours de " + jeu.getManche().getTourEnCours().getJoueurPremier().getNom();
		//on réinitialise le nombre de carte dans la pioche
		nbCartePioche.setText("15");
		//on désactive la seléction de la main
        mainVisible.setSelected(false);
        //on réactive le click sur terrain
		terrain.setDisable(false);
	}

	//vérifier fin de manche : 2 paramètres - test si on est a la fin de la manche si oui la réinitialise et test la fin de partie
	//joueur le joueur ayant fini son tour
	//peutFaireAction : boolean spécifiant si il peut encore faire des actions
	private boolean verifierFinDeManche(Joueur joueur, boolean peutFaireAction) throws Exception{
		int resultat;

		//test si le joueur ne peut plus faire des actions
		if(!peutFaireAction){
			//si il ne peut plus on test si il est joueur premier ou second
			if(joueur.equals(jeu.getManche().getTourEnCours().getJoueurPremier())){
				resultat = Tour.joueurPremierPerdu;
			}else{
				resultat = Tour.joueurSecondPerdu;
			}
			try {
				//on réinitialise la manche
				reinitialiserApresFinManche();
				//on vérifie si la partie et fini en envoyant le résultat de la manche
				verifierFinDuJeu(jeu.getManche().finDeManche(resultat));
			} catch (Exception e) {
				e.printStackTrace();
			}

			// la manche est terminée
			return true;
		}

		// la manche n'est pas terminée
		return false;
	}

	//On vérifie si la pioche n'est pas vide
	private boolean verifierFinDeLaPioche(){
		//si la pioche est vide
		if(jeu.getManche().getPioche().estVide()){
			//on réinitialise la manche
			reinitialiserApresFinManche();
			try {
				//on vérifie si la partie et fini en envoyant le résultat de la manche
				verifierFinDuJeu(jeu.getManche().finDeManche(Tour.piocheVide));
			} catch (Exception e) {
				e.printStackTrace();
			}

			// la manche est terminée
			return true;
		}

		// la manche n'est pas terminée
		return false;
	}

	//On test la fin du jeu
	//resultat : couple avec le joueur ayant gagné ou match nulle et la façon dont il a gagné
	private void verifierFinDuJeu(Couple<Integer, Integer> resultat){
		nbCartePioche.setText("15");
		//couple avec le joueur ayant gagné et le joueur ayant perdu
		Couple<Integer, Integer> resultatFinPartie;
		//on réinitialise les cartes sélectionné
		cartes = new ArrayList<Carte>();
		//on test quel joueur a gagné la manche et on modifie son score
		if(resultat.getC1() == Manche.JOUEUR1GAGNE){
			jeu.changerScore(jeu.getJoueur1());
		}else if(resultat.getC1() == Manche.JOUEUR2GAGNE){
			jeu.changerScore(jeu.getJoueur2());
		}
		//on affiche la main des 2 joueurs pour pouvoir les comparer
		DessinateurCanvasJavaFx.visibilityActivated = true;
		//on test si la partie est fini
		resultatFinPartie = jeu.gainPartie();
		//si pas fin de partie, on affiche les widgets de fin de manche
		//sinon ceux de fin de partie
		if(resultatFinPartie == null){
			afficherWidgetFin(FINMANCHE, resultat);
		}else{
			afficherWidgetFin(FINPARTIE, resultatFinPartie);
		}
	}

	//on cache les widgets de fin de partie
	public void cacherWidgetFin(){
		fondTheatre.setVisible(false);
		tableauFin.setVisible(false);
	}

	//affichage des widgets de fin
	public void afficherWidgetFin(int typeFin, Couple<Integer, Integer> resultat){
		//chaine de caractère, avec le nom du joueur victorieux, et du perdant
		String joueurVictorieux = "";
		String joueurPerdant = "";
		//on affiche les 2 widgets de fin
		fondTheatre.setVisible(true);
		tableauFin.setVisible(true);
		//si on a une fin de partie
		if(typeFin == FINPARTIE){
			//on affiche les messages de fin de partie dans le tableau de fin
			buttonBarFinPartie.setVisible(true);
			buttonBarFinManche.setVisible(false);

			if(resultat.getC1() == Jeu.JOUEUR1GAGNE){
				joueurVictorieux = jeu.getJoueur1().getNom();
				joueurPerdant = jeu.getJoueur2().getNom();
			}else if(resultat.getC1() == Jeu.JOUEUR2GAGNE){
				joueurVictorieux = jeu.getJoueur2().getNom();
				joueurPerdant = jeu.getJoueur1().getNom();
			}

			textTableauFin.setText(joueurVictorieux + "\na triomphé de son adversaire ! Gloire à\n" + joueurVictorieux +" !");

		}else if(typeFin == FINMANCHE){
			//on affiche les messages de fin de manche dans le tableau de fin
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
				textTableauFin.setText("Manche nulle !?");
			}else{
				switch(resultat.getC2()){
					case Manche.VICTOIRESIMPLE :
						textTableauFin.setText(joueurVictorieux + "\na gagné la manche !\n" + joueurPerdant + "\nne pouvait faire aucune action !");
						break;
					case Manche.PLUSCARTEATTAQUEDIRECT :
						textTableauFin.setText(joueurVictorieux + " a gagné la\nmanche ! " + joueurVictorieux + "\na plus de cartes pour attaquer\ndirectectement son adversaire");
						break;
					case Manche.PLUSCARTEMEDIANE :
						textTableauFin.setText(joueurVictorieux + " étant plus\nproche de la case médiane\n" + joueurVictorieux + "\na gagné la manche !");
						break;
				}
			}

			//on désactive le button de fin de tour et on remet le texte fin de tour dedans
			buttonGestionTour.setText("Fin De Tour");
			buttonGestionTour.setDisable(true);
		}

		//on réaffiche les mains
		mainDroite.setVisible(true);
		mainGauche.setVisible(true);
	}

	//GESTION DES ACTIONS
	private void setActionMain(){
		//quand on click sur la main du joueur1
		mainGauche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
						try {
							//on modifie les actions possibles par rapport à la carte clicker
							modifierActionPossible(1, event.getX(), event.getY());
						} catch (Exception e) {
							e.printStackTrace();
						}
        	            break;
        	        default:
        	            break;
            	}
            }
        });

		//quand on click sur la main du joueur2
		mainDroite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
						try {
							//on modifie les actions possibles par rapport à la carte clicker
							modifierActionPossible(2, event.getX(), event.getY());
						} catch (Exception e) {
							e.printStackTrace();
						}
        	            break;
        	        default:
        	            break;
            	}
            }
        });
	}

	//event sur le terrain
	private void setActionTerrain(){
		//quand on click sur le terrain
		terrain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            private ActionsJouables actions_jouables;

			@Override
            public void handle(MouseEvent event) {

            	boolean caseFound = false ;

            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	//on empêche les actions si pas de joueur seléctionné
        	        	if(joueurEnCours != null){
        	        		//on crée une énumération des actions_jouables
        	        		Enumeration<Action> e;
        	        		Tour tour = jeu.getManche().getTourEnCours();

        	        		//on initialise les actions_jouables
    	                	actions_jouables = tour.getActionsJouables();

    	                	//si il y a des actions
    	                	if(actions_jouables != null){
		    	            	e = actions_jouables.elements();

		    	            	//on regarde si la case clicker existe et est une action jouable et on l'exécute si possible
	        	        		try {
									caseFound = tour.executerAction(joueurEnCours, (float)event.getX(), (float)event.getY());
								} catch (Exception e2) {
									e2.printStackTrace();
								}
	        	        		//si on a pu exécuter l'action
	        	        		if(caseFound){
	        	        			//on modifie le button fin de tour
	        	        			buttonGestionTour.setStyle("-fx-background-image:url(finDeTourC.png);");
	        	        			//on réinitialise les cartes seléctionné
	        	        			cartes = new ArrayList<Carte>();
	        	        			//test de la parade
	        	        			if(tour.getEstAttaque().getC1() != Joueur.Parade || (tour.getEstAttaque().getC1() == Joueur.Parade && tour.getPioche().estVide())){
	        	        				//si pas de parade ou une parade mais la pioche est vide
	        	        				//on autorise le click sur le bouton fin de tour
	        	        				buttonGestionTour.setDisable(false);
	        	        				//on empêche le click sur la main
	        	        				changeDisableMain(joueurEnCours, true);
	        	        				//on change la messageBox
	        	        				tour.getMessageBox().setTexte("Appuyer sur le bouton Fin De Tour");
	        	        				//on empêche le click sur le terrain
	        	        				terrain.setDisable(true);
	        	        			}else if(tour.getEstAttaque().getC1() == Joueur.Parade && !tour.getPioche().estVide()){
	        	        				//si on a eu une parade et que la pioche n'est pas vide

	        	        				//On regarde si on peut effectuer des actions le tour suivant
	        	        				try {
											ActionsJouables actionsTourSuivant = joueurEnCours.peutFaireAction(tour.getEstAttaque());

			        	        			if(actionsTourSuivant.size() == 0 || actionsTourSuivant == null){
		        	        					//si on ne peut pas contre-attaquer

			        	        				//on autorise le click sur le bouton fin de tour
			        	        				buttonGestionTour.setDisable(false);
			        	        				//on empêche le click sur la main
			        	        				changeDisableMain(joueurEnCours, true);
			        	        				//on change la messageBox
			        	        				tour.getMessageBox().setTexte("Appuyer sur le bouton Fin De Tour");
			        	        				//on empêche le click sur le terrain
			        	        				terrain.setDisable(true);
		        	        				}else{
			        	        				//si on peut contre-attaquer
			        	        				//on change la messageBox
		        	        					messageCourant = "Vous venez de parer, lancez une contre-attaque ou déplacez-vous !";
			        	        				tour.getMessageBox().setTexte(messageCourant);
			        	        				joueurEnCours.getMain().deselectionneeToutesLesCartes();
		        	        				}
										} catch (Exception e1) {
											e1.printStackTrace();
										}
	        	        			}

	        	        			//Affichage des messages, liés à l'action venant d'être jouée
	        	        			Action action = null;
	        	        			boolean trouve = false;

	    	    	            	Case caseClicked = jeu.getPiste().getCaseEvent(event.getX(), event.getY());

	    	    	    			while(e.hasMoreElements() && !trouve){
	    	    	    				action = e.nextElement();
	    	    	    				try {
											if(caseClicked.getNumero() == action.getPositionArrivee() ||
											  (tour.actionOffensive(action) && caseClicked.getNumero() == tour.joueurAdverse(joueurEnCours).getPositionDeMaFigurine())){
												trouve = true;
											}
										} catch (Exception e1) {
											e1.printStackTrace();
										}
	    	    	    			}

	    	    	    			if(trouve){
	    	    	    				switch(action.getTypeAction()){
	    	    						case Joueur.Reculer :
	    	    							if(action.getCarteDeplacement().getContenu() > 1){
	    	    								messageCourant = joueurEnCours.getNom() + " a reculé de " + action.getCarteDeplacement().getContenu() + " cases";
	    	    							}else{
	    	    								messageCourant = joueurEnCours.getNom() + " a reculé de " + action.getCarteDeplacement().getContenu() + " case";
	    	    							}
	    	    							break;
	    	    						case Joueur.Avancer :
	    	    							if(action.getCarteDeplacement().getContenu() > 1){
	    	    								messageCourant = joueurEnCours.getNom() + " a avancé de " + action.getCarteDeplacement().getContenu() + " cases";
	    	    							}else{
	    	    								messageCourant = joueurEnCours.getNom() + " a reculé de " + action.getCarteDeplacement().getContenu() + " case";
	    	    							}
	    	    							break;
	    	    						case Joueur.AttaqueDirecte :
	    	    							if(action.getNbCartes() > 1){
	    	    								messageCourant = "Vous devez parer avec " + action.getNbCartes() + " cartes de valeur " + action.getCarteAction().getContenu();
	    	    							}else{
	    	    								messageCourant = "Vous devez parer avec " + action.getNbCartes() + " carte de valeur " + action.getCarteAction().getContenu();
	    	    							}
	    	    							break;
	    	    						case Joueur.AttaqueIndirecte :
	    	    							if(action.getNbCartes() > 1){
	    	    								messageCourant = "Vous devez parer avec " + action.getNbCartes() + " cartes de valeur " + action.getCarteAction().getContenu() + " ou fuire";
	    	    							}else{
	    	    								messageCourant = "Vous devez parer avec " + action.getNbCartes() + " carte de valeur " + action.getCarteAction().getContenu() + " ou fuire";
	    	    							}
	    	    							break;
	    	    						case Joueur.Fuite :
	    	    							if(action.getCarteDeplacement().getContenu() > 1){
	    	    								messageCourant = joueurEnCours.getNom() + " a fui de " + action.getCarteDeplacement().getContenu() + " cases";
	    	    							}else{
	    	    								messageCourant = joueurEnCours.getNom() + " a fui de " + action.getCarteDeplacement().getContenu() + " case";
	    	    							}
	    	    							break;
	    	    					}
	    	    	    			}

	    	    	    			//on modifie le nombre de carte dans la pioche
	        	        			nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
	        	        			mainApp.setActionFaites(Sauvegarde.FINDETOUR);

	        	        			if(tour.joueurAdverse(joueurEnCours) instanceof IA){
	        	        				joueurEnCours.getMain().setVisible(false);
	        	        				joueurEnCours.getMain().setVisible(true);
	        	        			}
	        	        		}
    	                	}
        	        	}
        	            break;

        	        default:
        	            break;
            	}
            }
        });

		//si on quitte le terrain
		terrain.setOnMouseExited(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event){
				//si le terrain n'est pas clickable
				if(!terrain.isDisable()){
					//on modifie le message courant
					Tour tour = jeu.getManche().getTourEnCours();
					tour.getMessageBox().setTexte(messageCourant);
				}
			}

		});

		//si on bouge dans le terrain
		terrain.setOnMouseMoved(new EventHandler<MouseEvent>() {
            private ActionsJouables actions_jouables;

            //si on passe sur une case on modifie le message courant de la message Box
			@Override
            public void handle(MouseEvent event) {
            	Case caseHovered;
            	Enumeration<Action> e;
            	Action action = null;
            	boolean trouve=false;
            	Tour tour;

            	tour = jeu.getManche().getTourEnCours();

            	actions_jouables = tour.getActionsJouables();
            	caseHovered = jeu.getPiste().getCaseEvent(event.getX(), event.getY());

            	if(caseHovered != null && actions_jouables != null && actions_jouables.size() != 0){
	            	e = actions_jouables.elements();

	    			while(e.hasMoreElements() && !trouve){
	    				action = e.nextElement();
	    				try {
							if(caseHovered.getNumero() == action.getPositionArrivee() ||
							  (tour.actionOffensive(action) && caseHovered.getNumero() == tour.joueurAdverse(joueurEnCours).getPositionDeMaFigurine())){
								trouve = true;
							}
						} catch (Exception e1) {
							e1.printStackTrace();
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

	//définit l'action a effectuer au click sur le boutton fin de tour
	@FXML
	private void gestionTour() throws Exception{
		//si FINDETOUR
		if(gestionTour == FINDETOUR){
			finDeTour();
		//SI PRETAJOUER
		}else if(gestionTour == PRETAJOUER){
			pretAJouer();
		}
	}

	//Si PRETAJOUER - click sur le button prêt à jouer
	private void pretAJouer(){
		mainApp.setActionFaites(Sauvegarde.ENCOURS);

		Tour tour = jeu.getManche().getTourEnCours();
		//on change de joueur dans le moteur
		tour.changerJoueur(joueurEnCours);
		//on repasse le button à fin de tour et on le désactive
		buttonGestionTour.setText("Fin De Tour");
		buttonGestionTour.setDisable(true);
		//on repasse la main du joueur adverse a clickable
		changeDisableMain(tour.joueurAdverse(joueurEnCours), false);
		gestionTour=FINDETOUR;
		//on change le message courant
		jeu.getPiste().getMessageBox().setTexte(messageCourant);
		//on autorise a clicker sur la piste
		terrain.setDisable(false);

		joueurEnCours = tour.joueurAdverse(joueurEnCours);
	}

	//Si FINDETOUR - click sur le button fin de tour
	private void finDeTour() throws Exception{
		boolean peutFaireAction;
		Tour tour = jeu.getManche().getTourEnCours();
		int etatAttaque = tour.getEstAttaque().getC1();
		boolean mancheTerminee;

		mainApp.setActionFaites(Sauvegarde.ENTREDEUX);

		ActionsJouables actionsTourSuivant = joueurEnCours.peutFaireAction(tour.getEstAttaque());

		if(etatAttaque == Joueur.Parade && !tour.getPioche().estVide() && (actionsTourSuivant.size() == 0 || actionsTourSuivant == null)){
			verifierFinDeManche(joueurEnCours, false);

			//Changement du message
			jeu.getPiste().getMessageBox().setTexte("Au tour de " + jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getNom() + ". Appuyer sur Prêt A Jouer !");

			if(tour.joueurAdverse(joueurEnCours) instanceof IA){
				//tour.joueurAdverse(joueurEnCours).getMain().setVisible(false);
				//changeDisableMain(tour.joueurAdverse(joueurEnCours), true);
				//jouerIA(tour.joueurAdverse(joueurEnCours));
			}else{
				if(!isMainVisible()){
					//on passe le button a prêt à jouer
					buttonGestionTour.setText("Prêt A Jouer");
					joueurEnCours.getMain().setVisible(false);
					gestionTour=PRETAJOUER;
					buttonGestionTour.setStyle("-fx-background-image:url(finDeTour.png);");
				}
			}
		}else{
			//si le joueur n'a pas parer au tour d'avant ou a paré mais la pioche est vide
			if(etatAttaque != Joueur.Parade || (etatAttaque == Joueur.Parade && tour.getPioche().estVide())){
				//on change le disable des mains
				changeDisableMain(tour.joueurAdverse(joueurEnCours), true);
				changeDisableMain(joueurEnCours, true);
			}

			//on test les actions de l'adversaire
			peutFaireAction = tour.adversairePeutFaireAction(joueurEnCours);
			//on vérifie la fin de la manche par rapport à ca
			mancheTerminee = verifierFinDeManche(tour.joueurAdverse(joueurEnCours), peutFaireAction);

			//on test si il n'y a pas fin de la pioche
			if(etatAttaque == Joueur.PasAttaque || etatAttaque == Joueur.Parade){
				mancheTerminee = verifierFinDeLaPioche();
			}

			//Changement du message
			if(isMainVisible()){
				jeu.getPiste().getMessageBox().setTexte("Au tour de " + jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getNom() + ".");
			}else{
				jeu.getPiste().getMessageBox().setTexte("Au tour de " + jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getNom() + ". Appuyer sur Prêt A Jouer !");
			}

			if(tour.joueurAdverse(joueurEnCours) instanceof IA && !mancheTerminee){
				tour.joueurAdverse(joueurEnCours).getMain().setVisible(false);
				changeDisableMain(tour.joueurAdverse(joueurEnCours), true);
				jouerIA(tour.joueurAdverse(joueurEnCours));
			}else{
				if(!isMainVisible()){
					//on passe le button a prêt à jouer
					buttonGestionTour.setText("Prêt A Jouer");
					joueurEnCours.getMain().setVisible(false);
					gestionTour=PRETAJOUER;
					buttonGestionTour.setStyle("-fx-background-image:url(finDeTour.png);");
				}else{
					buttonGestionTour.setDisable(true);
					changeDisableMain(tour.joueurAdverse(joueurEnCours), false);
					terrain.setDisable(false);
				}
			}

		}
	}

	//changement du disable des mains
	public void changeDisableMain(Joueur joueur, boolean disable){
		if(joueur.getMain().getCote() == Main.droite){
			mainDroite.setDisable(disable);
		}else{
			mainGauche.setDisable(disable);
		}
	}

	//on passe à la manche suivante
	@FXML
	private void mancheSuivante(){
		//on change la variable de main visible
		DessinateurCanvasJavaFx.visibilityActivated = false;
		//on créer une nouvelle manche
		jeu.nouvelleManche();
		try {
			//on la lance
			jeu.lancerLaManche();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//change le disable des mains
		if(jeu.getManche().getTourEnCours().getJoueurPremier().equals(jeu.getJoueur1())){
			mainGauche.setDisable(false);
			mainDroite.setDisable(true);
		}else{
			mainGauche.setDisable(true);
			mainDroite.setDisable(false);
		}
		debutJeu();
		//on chache les widgets de fin
		cacherWidgetFin();
	}

	@FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}

	//on lance une nouvelle partie
	@FXML
	private void nouvellePartie(){
		String type1 = jeu.getJoueur1() instanceof Humain ? "Humain" : "IA" ;
		String type2 = jeu.getJoueur2() instanceof Humain ? "Humain" : "IA" ;
		mainApp.jeu(jeu.getJoueur1().getNom(), jeu.getJoueur2().getNom(), type1, type2, true);
	}

	private boolean isMainVisible(){
		return DessinateurCanvasJavaFx.visibilityActivated == true;
	}

	//Pour changer le curseur
	@FXML
	private void handleIn(){
		Image imageC = new Image("SourisEpeePlante.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}

	//Pour changer le curseur
	@FXML
	private void handleOut(){
		Image imageC = new Image("SourisEpee.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}

	//on seléctionne ou on desélectionne la main visible
	@FXML
	private void handleIsSelected(){
		if(mainVisible.isSelected()){
			DessinateurCanvasJavaFx.visibilityActivated = true;
		}else{
			DessinateurCanvasJavaFx.visibilityActivated = false;
			if(jeu.getJoueur1() instanceof Humain && jeu.getJoueur2() instanceof Humain){
				if(gestionTour == FINDETOUR){
					if(joueurEnCours.equals(jeu.getJoueur1())){
						jeu.getJoueur1().getMain().setVisible(true);
						jeu.getJoueur2().getMain().setVisible(false);
					}else{
						jeu.getJoueur1().getMain().setVisible(false);
						jeu.getJoueur2().getMain().setVisible(true);
					}
				}else if(gestionTour == PRETAJOUER){
					jeu.getJoueur1().getMain().setVisible(false);
					jeu.getJoueur2().getMain().setVisible(false);
				}
			}
		}
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

}
