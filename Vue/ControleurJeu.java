package Vue;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import Modele.Jeu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import com.sun.imageio.stream.CloseableDisposerRecord;

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

    private MainApp mainApp;
    
    private Jeu jeu;
    
    DessinateurCanvasJavaFx d;
    
    public ControleurJeu(){
    }
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void init(Jeu j){
		this.jeu = j;
		d = new DessinateurCanvasJavaFx(terrain, pioche, defausse, scoreDroit, mainDroite, scoreGauche, mainGauche);
		jeu.accept(d);
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
    	Image im = new Image("/Ressources/coeur.png");
    	if(y==0){
    		
    	}else{
    		for(int i=0; i<y; i++){
    			gc.setStroke(Color.BLUE);
    	        gc.strokeRect(0, 0, scoreDroit.getWidth(), scoreDroit.getHeight());
    	        gc.drawImage(im, scoreDroit.getWidth()/4, scoreDroit.getHeight()*i/5, scoreDroit.getWidth()*2/4, scoreDroit.getHeight()/5);
    		}
    	}
    }
    
    //y est le score 1, 2, 3, 4 ou 5 manche(s) gagnée(s)
    private void dessinerScoreGauche(double y){
    	Image im = new Image("/Ressources/coeur.png");
    	//Image im2 = new Image("/Ressources/barreDeVie.png");
    	//gc.drawImage(im2, -20, 0, scoreGauche.getWidth(), scoreGauche.getHeight());
    	if(y==0){
    		
    	}else{
    		for(int i=0; i<y; i++){
	    		gc.setStroke(Color.RED);
	    		gc.drawImage(im, scoreDroit.getWidth()/4, scoreDroit.getHeight()*i/5, scoreDroit.getWidth()*2/4, scoreDroit.getHeight()/5);
    		}
    	}
    }
    
    private void dessinerMainDroite(double nbCarte, int carteASoulever){
    	for(int i=0; i<nbCarte; i++){
    		if(i==carteASoulever){
    			souleverCarteNumero(mainDroite, i);
    		}else{
    			dessinerCarteVerti(mainDroite, i*90, 60);
    		}
    	}
    }
    
    private void dessinerMainGauche(double nbCarte){
    	for(int i=0; i<nbCarte; i++){
    		dessinerCarteVerti(mainDroite, i*90, 60);
    	}
    }
    
    //diminuer l'origine des cartes afin de faire croire qu'elles montent
    private void souleverCarteNumero(Canvas c, double numCarte){
    	gc.clearRect(numCarte*90, 0, 150, 200 );
    	dessinerCarteVerti(c, numCarte*90, 0);
    }
    
    private void dessinerCarteVerti(Canvas c, double x, double y){
    	Image i = new Image("/Ressources/dosCarte.jpg");
    	gc.drawImage(i, x, y, 150, 200);
    	//gc.fillRect(x, y, 150, 200);
    	gc.strokeRect(x, y, 150, 200);
    }
    
    private void dessinerCarteHori(Canvas c, double x, double y){
    	gc.strokeRect(x, y, 200, 150);
    }
>>>>>>> Vue
    
    @FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}

}
