package Vue;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
