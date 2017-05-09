package Vue;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import Vue.MainApp;


public class ControleurChoixPartie {
		
    private MainApp mainApp;
    
    public ControleurChoixPartie(){
    }
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
    private void lancerPartie(){
		mainApp.jeu();
    }
	
	@FXML
	private void menu(){
		mainApp.acceuil();
	}
}

