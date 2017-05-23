package Controleur;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import Vue.MainApp;


public class ControleurAcceuil {
		
    private MainApp mainApp;
    
    public ControleurAcceuil(){
    }
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void handleNouvellePartie() {
		mainApp.choixPartie();
	}
	
	@FXML
	private void handleSauvegarde() {
		mainApp.sauvegardes(true);
	}
	
	@FXML
    private void handleQuitter(){
		mainApp.alertQuitter();
    }
	
	
	@FXML
    private void handleRegles(){
		mainApp.regles();
    }
	
	@FXML
	private void handleIn(){
		Image imageC = new Image("SourisEpeePlante.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}
	
	@FXML
	private void handleOut(){
		Image imageC = new Image("SourisEpee.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}
}
