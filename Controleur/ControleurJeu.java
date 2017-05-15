package Controleur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import Modele.Jeu;
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
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void init(Jeu j){
		this.setJeu(j);
		
		setActionTerrain();
		setActionMain();
	}
	
	private void setActionMain(){
		mainGauche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	jeu.getJoueur1().getMain().getCarteClick(event.getX(), event.getY());
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
        	        	jeu.getJoueur2().getMain().getCarteClick(event.getX(), event.getY());
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
