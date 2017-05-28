package Controleur;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import Vue.MainApp;

public class ControleurRegles {

	private MainApp mainApp;

	@FXML
	private ScrollPane scrollPane;

    public ControleurRegles(){
    }

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
	private void menu(){
		mainApp.acceuil();
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
