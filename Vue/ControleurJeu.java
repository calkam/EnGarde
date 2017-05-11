package Vue;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import Vue.MainApp;

public class ControleurJeu {
	
    @FXML
    private Canvas terrain;
    
    @FXML
    private Canvas pioche;
    
    @FXML 
    private Canvas defausse;
    
    @FXML
    private Canvas scoreDroit;
    
    @FXML
    private Canvas mainDroite;
    
    @FXML
    private Canvas scoreGauche;
    
    @FXML
    private Canvas mainGauche;
    
    public GraphicsContext gc;
    
    private MainApp mainApp;
    
    public ControleurJeu(){
    }
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void initGraphics()
    {
        gc = terrain.getGraphicsContext2D();
        dessinerTerrain();
        gc = terrain.getGraphicsContext2D();
        dessinerJoueurDroit();
        gc = pioche.getGraphicsContext2D();
        dessinerPioche();
        gc = defausse.getGraphicsContext2D();
        dessinerDefausse();
        gc = scoreDroit.getGraphicsContext2D();
        dessinerScoreDroit();
        gc = scoreGauche.getGraphicsContext2D();
        dessinerScoreGauche();
        gc = mainDroite.getGraphicsContext2D();
        dessinerMainDroite();
        gc = mainGauche.getGraphicsContext2D();
        dessinerMainGauche();
    }

    private void dessinerJoueurDroit(){
        gc.strokeRect(terrain.getWidth()/23, 0, terrain.getWidth()/23, terrain.getHeight());
        System.out.println("You have draw J1");
    }
    
    private void dessinerJoueurGauche(){
    	
    }
    
    private void dessinerTerrain()
    {
        /*gc.clearRect(0, 0, terrain.getWidth(), terrain.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, terrain.getWidth(), terrain.getHeight());*/
        Image a = new Image("/Ressources/arriere_plan.png");
        System.out.println("You have draw terrain");
        gc.drawImage(a, 0, 0);
    }
    
    private void dessinerPioche(){
    	gc.clearRect(0, 0, pioche.getWidth(), pioche.getHeight());
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, pioche.getWidth(), pioche.getHeight());
        System.out.println("You have draw pioche");
    }
    
    private void dessinerDefausse(){
    	gc.clearRect(0, 0, defausse.getWidth(), defausse.getHeight());
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, defausse.getWidth(), defausse.getHeight());
        System.out.println("You have draw defausse");
    }
    
    private void dessinerScoreDroit(){
    	gc.clearRect(0, 0, scoreDroit.getWidth(), scoreDroit.getHeight());
        gc.setFill(Color.PINK);
        gc.fillRect(0, 0, scoreDroit.getWidth(), scoreDroit.getHeight());
        System.out.println("You have draw scoreDroit");
    }
    
    private void dessinerScoreGauche(){
    	gc.clearRect(0, 0, scoreGauche.getWidth(), scoreGauche.getHeight());
        gc.setFill(Color.YELLOW);
        gc.fillRect(0, 0, scoreGauche.getWidth(), scoreGauche.getHeight());
        System.out.println("You have draw scoreGauche");
    }
    
    private void dessinerMainDroite(){
    	gc.clearRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
        gc.setFill(Color.GREY);
        gc.fillRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
        System.out.println("You have draw mainDroite");
    }
    
    private void dessinerMainGauche(){
    	gc.clearRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
        System.out.println("You have draw mainGauche");
    }
    
    
    @FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}

}
