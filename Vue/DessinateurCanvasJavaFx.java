package Vue;

import Modele.Visiteur;
import Modele.Plateau.Case;
import Modele.Plateau.Piste;
import Modele.Tas.Carte;
import Modele.Tas.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class DessinateurCanvasJavaFx extends Visiteur {

	Piste piste;
	
    private Canvas terrain;
    private Canvas pioche;
    private Canvas defausse;
    private Canvas scoreDroit;
    private Canvas mainDroite;
    private Canvas scoreGauche;
    private Canvas mainGauche;
    
    public GraphicsContext gcTerrain, gcPioche, gcDefausse, gcScoreDroit, gcMainDroite, gcScoreGauche, gcMainGauche;
	
    public DessinateurCanvasJavaFx(Canvas terrain, Canvas pioche, Canvas defausse, Canvas scoreDroit, Canvas mainDroite, Canvas scoreGauche, Canvas mainGauche) {
		super();
		this.terrain = terrain;
		this.pioche = pioche;
		this.defausse = defausse;
		this.scoreDroit = scoreDroit;
		this.mainDroite = mainDroite;
		this.scoreGauche = scoreGauche;
		this.mainGauche = mainGauche;
	}
    
    public void initGraphics(){
    	gcPioche = pioche.getGraphicsContext2D();
    	gcDefausse = defausse.getGraphicsContext2D();
    	gcScoreDroit = scoreDroit.getGraphicsContext2D();
    	gcScoreGauche = scoreGauche.getGraphicsContext2D();


    }
    
    public boolean visite(Piste p){
    	piste = p;
    	p.fixeDimensions((float)terrain.getWidth(), (float)terrain.getHeight());
    	gcTerrain = terrain.getGraphicsContext2D();
    	dessinerTerrain(gcTerrain);
		return false;
    }
    
    public boolean visite(Case c){
    	gcTerrain.setStroke(Color.BLACK);
    	gcTerrain.strokeRect(c.getX(), c.getY(), c.getLargeur(), c.getHauteur());
		return false;
	}

    public boolean visite(Main m){
    	if(m.getCote() == Main.droite){
        	gcMainDroite = mainDroite.getGraphicsContext2D();
    		m.fixeDimensions((float)mainDroite.getWidth(), (float)mainDroite.getHeight());
    		gcMainDroite.setStroke(Color.BLUE);
    		dessinerCarteVerti(gcMainDroite, 0, 0);
    	}else{
        	gcMainGauche = mainGauche.getGraphicsContext2D();
    		m.fixeDimensions((float)mainGauche.getWidth(), (float)mainGauche.getHeight());
    		gcMainGauche.setStroke(Color.RED);
    		dessinerCarteVerti(gcMainGauche, 0, 0);
    	}
    	return false;
    }
    
    public boolean visite(Carte c){
    	gcMainDroite.setStroke(Color.BLUE);
    	System.out.println(c.getX() + ", " + c.getY());
        dessinerCarteVerti(gcMainDroite, c.getX(), c.getY());
    	return false;
    }
    
	public void dessinerElement()
    {
        dessinerTerrain(gcTerrain);
        
        dessinerJoueurDroit(gcTerrain);
        
        dessinerJoueurGauche(gcTerrain);
        
        dessinerCarteHori(gcPioche, 0, 0);
        
        dessinerCarteHori(gcDefausse, 0, 0);
        
        dessinerScoreDroit(gcScoreDroit, 2);

        dessinerScoreGauche(gcScoreGauche, 4);
        
    	gcMainDroite.setStroke(Color.BLUE);
        dessinerCarteVerti(gcMainDroite, 0, 0);
        
    	gcMainGauche.setStroke(Color.RED);
        dessinerCarteVerti(gcMainGauche, 0, 0);        
    }
	
    private void dessinerJoueurGauche(GraphicsContext gc){
    	gc.setStroke(Color.RED);
        gc.strokeRect((terrain.getWidth()*2)/25, 0, terrain.getWidth()/25, terrain.getHeight());
    }
    
    private void dessinerJoueurDroit(GraphicsContext gc){
    	gc.setStroke(Color.BLUE);
    	gc.strokeRect((terrain.getWidth()*16)/25, 0, terrain.getWidth()/25, terrain.getHeight());
    }
    
    private void dessinerTerrain(GraphicsContext gc)
    {
        gc.clearRect(0, 0, terrain.getWidth(), terrain.getHeight());
        Image a = new Image("/Ressources/arriere_plan.png");
        gc.drawImage(a, 0, 0);
    }
    
    private void dessinerPioche(GraphicsContext gc){
    	gc.clearRect(0, 0, pioche.getWidth(), pioche.getHeight());
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, pioche.getWidth(), pioche.getHeight());
    }
    
    private void dessinerDefausse(GraphicsContext gc){
    	gc.clearRect(0, 0, defausse.getWidth(), defausse.getHeight());
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, defausse.getWidth(), defausse.getHeight());
    }
    
    //y est le score 1, 2, 3, 4 ou 5 manche(s) gagnée(s)
    private void dessinerScoreDroit(GraphicsContext gc, double y){
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
    private void dessinerScoreGauche(GraphicsContext gc, double y){
    	if(y==0){
    		
    	}else{
    		for(int i=0; i<y; i++){
	    		gc.setStroke(Color.RED);
	    		gc.strokeRect(0, 0, scoreGauche.getWidth(), scoreGauche.getHeight());
	            gc.strokeOval(1, scoreGauche.getHeight()*i/5, scoreGauche.getWidth()-2, scoreGauche.getHeight()/5);
    		}
    	}
    }
    
    //diminuer l'origine des cartes afin de faire croire qu'elles montent
    private void souleverCarteNumero(Canvas c, double numCarte){
    	gc.clearRect(numCarte*90, 0, 150, 200 );
    	dessinerCarteVerti(c, numCarte*90, 0);
    }
    
    private void dessinerMainDroite(GraphicsContext gc){
    	gc.clearRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
        gc.setFill(Color.GREY);
        gc.fillRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
    }
    
    private void dessinerMainGauche(GraphicsContext gc){
    	gc.clearRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
    }
    
    private void dessinerCarteVerti(GraphicsContext gc, double x, double y){
    	gc.strokeRect(x, y, x+150, y+200);
    }
    
    private void dessinerCarteHori(GraphicsContext gc, double x, double y){
    	gc.strokeRect(x, y, x+200, y+150);
    }
}
