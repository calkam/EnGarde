package Controleur;

import javafx.fxml.FXML;
import Vue.MainApp;

public class ControleurSauvegardes {
		
    private MainApp mainApp;
    
    public ControleurSauvegardes(){
    }
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void charger() {
	}	
	
	@FXML
    private void sauvegarder(){
    }
	
	@FXML
	private void menu(){
		mainApp.acceuil();
	}
}
