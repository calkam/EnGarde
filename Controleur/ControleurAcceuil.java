package Controleur;

import javafx.fxml.FXML;
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
		mainApp.sauvegardes();
	}
	
	@FXML
    private void handleQuitter(){
		mainApp.alertQuitter();
    }
}
