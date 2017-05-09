package Vue;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import Vue.MainApp;


public class ControleurAcceuil {
		
    private Stage dialogStage;
    private MainApp mainApp;
    
    public ControleurAcceuil(){
    }
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
