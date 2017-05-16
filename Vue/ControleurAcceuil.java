package Vue;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
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
}
