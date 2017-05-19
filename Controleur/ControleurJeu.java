package Controleur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import com.sun.webkit.ThemeClient;

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
    private Button buttonFinDeTour;

    
    //ATTRIBUT
    private ArrayList<Carte> cartes;
    
    private Joueur joueurEnCours = null;
    
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
		fondTheatre.setVisible(false);
		tableauFin.setVisible(false);
		fondTheatre.toFront();
		tableauFin.toFront();
        mainDroite.toFront();
        mainGauche.toFront();
        terrain.toFront();
        buttonFinDeTour.setDisable(true);
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
				resultat = jeu.getManche().finDeManche(resultat);
				verifierFinDuJeu(resultat);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void verifierFinDeLaPioche(){
		int resultat;
		nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
		if(jeu.getManche().getPioche().estVide()){
			try {
				resultat = jeu.getManche().finDeManche(Tour.piocheVide);
				verifierFinDuJeu(resultat);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void verifierFinDuJeu(int resultat){
		cartes = new ArrayList<Carte>();
		if(resultat == Manche.JOUEUR1GAGNE){
			jeu.changerScore(jeu.getJoueur1());
		}else if(resultat == Manche.JOUEUR2GAGNE){
			jeu.changerScore(jeu.getJoueur2());
		}
		if(!jeu.gainPartie()){
			modifierWidgetFin();
			jeu.nouvelleManche();
			nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
			try {
				jeu.lancerLaManche();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			modifierWidgetFin();
		}
	}
	
	public void modifierWidgetFin(){
		fondTheatre.setVisible(true);
		tableauFin.setVisible(true);
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
            	        		buttonFinDeTour.setStyle("-fx-background-image:url(/Ressources/bouton_nonPresse.png);");
        	        			cartes = new ArrayList<Carte>();
        	        			buttonFinDeTour.setDisable(false);
        	        			changeAbleMain(joueurEnCours, true);
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
	private void finDeTour(){
		boolean peutFaireAction;
		int etatAttaque;
		Tour tour = jeu.getManche().getTourEnCours();
		if(tour.getEstAttaque().getC1() != Tour.parade){
			changeAbleMain(tour.joueurAdverse(joueurEnCours), false);
			tour.changerJoueur(joueurEnCours);
		}else{
			changeAbleMain(joueurEnCours, false);
		}
		
		buttonFinDeTour.setDisable(true);
		buttonFinDeTour.setStyle("-fx-background-image:url(/Ressources/finDeTour.png);");
		peutFaireAction = jeu.getManche().getTourEnCours().adversairePeutFaireAction(joueurEnCours);
		verifierFinDeManche(jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours), peutFaireAction);
		
		etatAttaque = jeu.getManche().getTourEnCours().getEstAttaque().getC1();
		if(etatAttaque == Tour.pasAttaque || !peutFaireAction){
			verifierFinDeLaPioche();
		}
		jeu.getPiste().getMessageBox().setTexte("Au tour de " + jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getNom());
	}
	
	public void changeAbleMain(Joueur joueur, boolean disable){
		if(joueur.getMain().getCote() == Main.droite){
			mainDroite.setDisable(disable);
		}else{
			mainGauche.setDisable(disable);
		}
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
