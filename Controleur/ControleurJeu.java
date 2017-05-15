package Controleur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import Modele.Jeu;
import Modele.Joueur.Joueur;
import Modele.Tas.Carte;
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
    
    private ArrayList<Carte> cartes;
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void init(Jeu j){
		this.setJeu(j);
		
		setActionTerrain();
		setActionMain();
		cartes = new ArrayList<Carte>();
	}
	
	private void modifierActionPossible(int numJoueur, double x, double y){
		Carte carteAction;
		Joueur joueur;
		if(numJoueur==1){
			joueur = jeu.getJoueur1();
		}else{
			joueur = jeu.getJoueur2();
		}
		carteAction = joueur.getMain().getCarteClick(x, y);
    	if(!cartes.contains(carteAction)){
    		cartes.add(carteAction);
    	}else{
    		cartes.remove(carteAction);
    	}
    	
    	jeu.getManche().getTourEnCours().possibiliteAction(joueur, cartes);
	}
	
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
        	        	jeu.getPiste().getCasesClick(event.getX(), event.getY());
        	            break;
        	        default:
        	            break;
            	}
            }
        });
        
		terrain.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	jeu.getPiste().getCasesHover(event.getX(), event.getY());
            }
        });
	}

	@FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}
	
	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

}
