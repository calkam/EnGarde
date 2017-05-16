package Vue;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControleurAlertQuitter {

    private Stage dialogStage;
    private boolean okClicked = false;
    private MainApp mainApp;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * clic sur Annuler.
     */
    @FXML
    private void handleAnnuler() {
        dialogStage.close();
    }
    
    /**
     * clic sur Quitter.
     */
    @FXML
    private void handleQuitter() {
    	dialogStage.close();
    	Platform.exit();
    }
}
