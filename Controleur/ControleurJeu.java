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
import Modele.Couple;
import Modele.Jeu;
import Modele.Manche;
import Modele.Tour;
import Modele.Joueur.Joueur;
import Modele.Joueur.Humain.HumainDroit;
import Modele.Joueur.Humain.HumainGauche;
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
    
    private int gestionTour = FINDETOUR;
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void init(Jeu j){        
		this.setJeu(j);
		nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
		nomJoueur1.setText(jeu.getJoueur1().getNom());
		nomJoueur2.setText(jeu.getJoueur2().getNom());
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
	
	private void verifierFinDeManche(Joueur joueur, boolean peutFaireAction){
		int resultat;
		if(!peutFaireAction){
			if(joueur.equals(jeu.getManche().getTourEnCours().getJoueurPremier())){
				resultat = Tour.joueurPremierPerdu;
			}else{
				resultat = Tour.joueurSecondPerdu;
			}
			try {
				verifierFinDuJeu(jeu.getManche().finDeManche(resultat));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void verifierFinDeLaPioche(){
		nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
		if(jeu.getManche().getPioche().estVide()){
			try {
				verifierFinDuJeu(jeu.getManche().finDeManche(Tour.piocheVide));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void verifierFinDuJeu(Couple<Integer, Integer> resultat){
		cartes = new ArrayList<Carte>();
		if(resultat.getC1() == Manche.JOUEUR1GAGNE){
			jeu.changerScore(jeu.getJoueur1());
		}else if(resultat.getC1() == Manche.JOUEUR2GAGNE){
			jeu.changerScore(jeu.getJoueur2());
		}
		if(!jeu.gainPartie()){
			afficherWidgetFin(FINMANCHE, resultat);
			nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
		}else{
			afficherWidgetFin(FINPARTIE, resultat);
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
						textTableauFin.setText(joueurVictorieux + " a gagné la manche !\n" + joueurPerdant + " c'est fait victimisé");
						break;
					case Manche.PLUSCARTEATTAQUEDIRECT :
						textTableauFin.setText(joueurVictorieux + " a plus de cartes pour attaquer directectement son adversaire.\n" + joueurVictorieux + " a gagné la manche !");
						break;
					case Manche.PLUSCARTEMEDIANE :
						textTableauFin.setText(joueurVictorieux + " étant plus proche de la case médiane....\n" + joueurVictorieux + " a gagné la manche !");
						break;
				}
			}
		}
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
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	if(joueurEnCours != null){
        	        		boolean caseFound;
        	        		caseFound = jeu.getManche().getTourEnCours().executerAction(joueurEnCours, (float)event.getX(), (float)event.getY());
        	        		if(caseFound){
        	        			buttonGestionTour.setStyle("-fx-background-image:url(/Ressources/bouton_nonPresse.png);");
        	        			cartes = new ArrayList<Carte>();
        	        			if(jeu.getManche().getTourEnCours().getEstAttaque().getC1() != Tour.parade){
        	        				buttonGestionTour.setDisable(false);
        	        				changeDisableMain(joueurEnCours, true);
        	        			}
        	        		}
        	        	}
        	            break;
        	        default:
        	            break;
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
		jeu.getPiste().getMessageBox().setTexte("Au tour de " + jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getNom());
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
		jeu.nouvelleManche();
		try {
			jeu.lancerLaManche();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
