package Vue;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Vue.ControleurAcceuil;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private Stage primaryStage;
    private Stage dialogStage;
    private BorderPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("En Garde");
        //this.primaryStage.setFullScreen(true);
        this.primaryStage.setWidth(1600);
        this.primaryStage.setHeight(900);
        this.primaryStage.getIcons().add(new Image("Ressources/dosCarte.jpg"));
        
        initRootLayout();
        
        acceuil();  
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Acceuil
     */
    public void acceuil() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/Acceuil.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            // Give the controller access to the main app.
            ControleurAcceuil controller = loader.getController();
            controller.setMainApp(this);
            
            Image imageC = new Image("/Ressources/SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC , 100, 100));

            Utils.playSound("MainTheme.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
   	/**
    * Ouvre une boire de dialogue qui demande si l'utilisateur veux bien quitter
    */
   	public boolean alertQuitter() {
       try {
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("/Vue/AlertQuitter.fxml"));
           AnchorPane page = (AnchorPane) loader.load(); 
           
           // Create the dialog Stage.
           dialogStage = new Stage();
           dialogStage.initStyle(StageStyle.UNDECORATED);
           dialogStage.setTitle("Alerte");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);
           
           Image imageC = new Image("/Ressources/SourisEpee.png");
           scene.setCursor(new ImageCursor(imageC));
           
           ControleurAlertQuitter controller = loader.getController();
           controller.setDialogStage(dialogStage);
           
           // Show the dialog and wait until the user closes it
           dialogStage.showAndWait();
           return controller.isOkClicked();
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }
   	
   	public void sauvegardes() {
   		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/Sauvegardes.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            // Give the controller access to the main app.
            ControleurSauvegardes controller = loader.getController();
            controller.setMainApp(this);
            
            Image imageC = new Image("/Ressources/SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
   	
   	public void jeu() {
   		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/Jeu.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            // Give the controller access to the main app.
            ControleurJeu controller = loader.getController();
            controller.setMainApp(this);
            controller.initGraphics();
            
            Image imageC = new Image("/Ressources/SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
   	
	public void choixPartie() {
   		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/ChoixPartie.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            // Give the controller access to the main app.
            ControleurChoixPartie controller = loader.getController();
            controller.setMainApp(this);
            
            Image imageC = new Image("/Ressources/SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

	

}