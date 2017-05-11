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
    	//Association au Terrain
        gc = terrain.getGraphicsContext2D();
        dessinerTerrain();
        gc.setStroke(Color.BLUE);
        dessinerJoueurDroit();
        gc.setStroke(Color.RED);
        dessinerJoueurGauche();
        
        //Association a la Pioche
        gc = pioche.getGraphicsContext2D();
        //dessinerPioche();
        dessinerCarteHori(pioche, 0, 0);
        
        //Association a la Defausse
        gc = defausse.getGraphicsContext2D();
        //dessinerDefausse();
        dessinerCarteHori(defausse, 0, 0);
        
        //Association aux scores
        gc = scoreDroit.getGraphicsContext2D();
        dessinerScoreDroit(2);
        
        gc = scoreGauche.getGraphicsContext2D();
        dessinerScoreGauche(4);
        
        //Association aux mains
        gc = mainDroite.getGraphicsContext2D();
        //dessinerMainDroite();
        gc.setStroke(Color.BLUE);
        dessinerCarteVerti(mainDroite, 0, 0);
        
        gc = mainGauche.getGraphicsContext2D();
        //dessinerMainGauche();
        gc.setStroke(Color.RED);
        dessinerCarteVerti(mainGauche, 0, 0);        
    }

    private void dessinerJoueurGauche(){
        gc.strokeRect((terrain.getWidth()*2)/25, 0, terrain.getWidth()/25, terrain.getHeight());
    }
    
    private void dessinerJoueurDroit(){
    	gc.strokeRect((terrain.getWidth()*16)/25, 0, terrain.getWidth()/25, terrain.getHeight());
    }
    
    private void dessinerTerrain()
    {
        gc.clearRect(0, 0, terrain.getWidth(), terrain.getHeight());
        Image a = new Image("/Ressources/arriere_plan.png");
        gc.drawImage(a, 0, 0);
    }
    
    private void dessinerPioche(){
    	gc.clearRect(0, 0, pioche.getWidth(), pioche.getHeight());
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, pioche.getWidth(), pioche.getHeight());
    }
    
    private void dessinerDefausse(){
    	gc.clearRect(0, 0, defausse.getWidth(), defausse.getHeight());
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, defausse.getWidth(), defausse.getHeight());
    }
    
    //y est le score 1, 2, 3, 4 ou 5 manche(s) gagnée(s)
    private void dessinerScoreDroit(double y){
    	if(y==0){
    		
    	}else{
    		for(int i=0; i<y; i++){
    			gc.setStroke(Color.BLUE);
    	        gc.strokeRect(0, 0, scoreDroit.getWidth(), scoreDroit.getHeight());
    	        gc.strokeOval(1, scoreDroit.getHeight()*i/5, scoreDroit.getWidth()-2, scoreDroit.getHeight()/5);
    		}
    	}
    }
    
    //y est le score 1, 2, 3, 4 ou 5 manche(s) gagnée(s)
    private void dessinerScoreGauche(double y){
    	if(y==0){
    		
    	}else{
    		for(int i=0; i<y; i++){
	    		gc.setStroke(Color.RED);
	    		gc.strokeRect(0, 0, scoreGauche.getWidth(), scoreGauche.getHeight());
	            gc.strokeOval(1, scoreGauche.getHeight()*i/5, scoreGauche.getWidth()-2, scoreGauche.getHeight()/5);
    		}
    	}
    }
    
    private void dessinerMainDroite(){
    	gc.clearRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
        gc.setFill(Color.GREY);
        gc.fillRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
    }
    
    private void dessinerMainGauche(){
    	gc.clearRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
    }
    
    private void dessinerCarteVerti(Canvas c, double x, double y){
    	gc.strokeRect(x, y, x+150, y+200);
    }
    
    private void dessinerCarteHori(Canvas c, double x, double y){
    	gc.strokeRect(x, y, x+200, y+150);
    }
    
    @FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}

}
