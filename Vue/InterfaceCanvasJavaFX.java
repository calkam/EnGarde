package Vue;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class InterfaceCanvasJavaFX extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        final boolean fullScreen = false;
        
        primaryStage.setTitle("Armoroides");
        Canvas c = new Canvas();
        
        // Composant de regroupement qui occupe toute la place disponible
        // Le noeud donné en paramètre est placé au centre du BorderPane
        BorderPane b = new BorderPane(c);
        
        // On redimensionne le composant quand son parent change de taille
        c.widthProperty().bind(b.widthProperty());
        c.heightProperty().bind(b.heightProperty());
        
        // Contenu de la fenêtre
        Scene s;
        if (fullScreen) {
            s = new Scene(b);
            primaryStage.setFullScreen(true);            
        } else {
            s = new Scene(b, 800, 600);
        }
        primaryStage.setScene(s);
        
        // Petit message dans la console quand la fenetre est fermée
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                System.out.println("Fin du jeu");
            }
        });
        
        primaryStage.show();
    }

    public static void creer(String[] args) {
        launch(args);
    }
    
    public static void main(String [] args) {
        creer(args);
    }
}
