package Controleur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import Modele.Jeu;
import Modele.Manche;
import Modele.Tour;
import Modele.Joueur.Joueur;
import Modele.Joueur.Humain.HumainDroit;
import Modele.Joueur.Humain.HumainGauche;
import Modele.Tas.Carte;
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
			jeu.nouvelleManche();
			nbCartePioche.setText(Integer.toString(jeu.getManche().getPioche().size()));
			try {
				jeu.lancerLaManche();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setActionMain(){
		mainGauche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	boolean peutFaireAction;
        	        	peutFaireAction = modifierActionPossible(1, event.getX(), event.getY());
        	        	verifierFinDeManche(jeu.getJoueur1(), peutFaireAction);
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
        	        	boolean peutFaireAction;
    	        		peutFaireAction = modifierActionPossible(2, event.getX(), event.getY());
    	        		verifierFinDeManche(jeu.getJoueur2(), peutFaireAction);
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
        	        		boolean peutFaireAction;
        	        		boolean caseFound;
        	        		int etatAttaque;
        	        		caseFound = jeu.getManche().getTourEnCours().executerAction(joueurEnCours, (float)event.getX(), (float)event.getY());
        	        		if(caseFound){
        	        			cartes = new ArrayList<Carte>();
        	        			peutFaireAction = jeu.getManche().getTourEnCours().adversairePeutFaireAction(joueurEnCours);
            	        		verifierFinDeManche(jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours), peutFaireAction);
            	        		System.out.println(jeu.getManche().getTourEnCours().getEstAttaque().getC1());
            	        		etatAttaque = jeu.getManche().getTourEnCours().getEstAttaque().getC1();
            	        		if(etatAttaque == Tour.pasAttaque || !peutFaireAction){
            	        			verifierFinDeLaPioche();
            	        		}
            	        		jeu.getPiste().getMessageBox().setTexte("Au tour de " + jeu.getManche().getTourEnCours().joueurAdverse(joueurEnCours).getNom());
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
