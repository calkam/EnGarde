package Vue;

import Modele.Visiteur;
import Modele.Plateau.Case;
import Modele.Plateau.Piste;
import Modele.Plateau.Figurine.FigurineDroite;
import Modele.Plateau.Figurine.FigurineGauche;
import Modele.Tas.Carte;
import Modele.Tas.Defausse;
import Modele.Tas.Main;
import Modele.Tas.Pioche;
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
		
		gcTerrain = terrain.getGraphicsContext2D();
	}
    
    public void initGraphics(){
    	gcScoreDroit = scoreDroit.getGraphicsContext2D();
    	gcScoreGauche = scoreGauche.getGraphicsContext2D();
    }
    
    public boolean visite(Piste p){
    	piste = p;
    	p.fixeDimensions((float)terrain.getWidth(), (float)terrain.getHeight());
    	gcTerrain.clearRect(0, 0, terrain.getWidth(), terrain.getHeight());
    	dessinerTerrain(gcTerrain);
		return false;
    }
    
    public boolean visite(Case c){
    	//System.out.println(c.getCouleur());
    	switch(c.getCouleur()){
    		case 0 : gcTerrain.setStroke(Color.TRANSPARENT); break;
    		case 1 : gcTerrain.setStroke(Color.RED); break;
    		default : System.out.println("pas de couleur défini");;
    	}
    	gcTerrain.strokeRect(c.getX(), c.getY(), c.getLargeur(), c.getHauteur());
		return false;
	}

    public boolean visite(Main m){
    	if(m.getCote() == Main.droite){
        	gcMainDroite = mainDroite.getGraphicsContext2D();
    		m.fixeDimensions((float)mainDroite.getWidth(), (float)mainDroite.getHeight());
    		dessinerMainDroite(gcMainDroite);
    	}else{
        	gcMainGauche = mainGauche.getGraphicsContext2D();
    		m.fixeDimensions((float)mainGauche.getWidth(), (float)mainGauche.getHeight());
    		dessinerMainGauche(gcMainGauche);
    	}
    	return false;
    }
    
    public boolean visite(Pioche p){
    	gcPioche = pioche.getGraphicsContext2D();
    	p.fixeDimensions((float)pioche.getWidth(), (float)pioche.getHeight());
    	dessinerPioche(gcPioche);
    	return false;
    }
    
    public boolean visite(Defausse d){
    	gcDefausse = defausse.getGraphicsContext2D();
    	d.fixeDimensions((float)defausse.getWidth(), (float)defausse.getHeight());
    	dessinerDefausse(gcDefausse);
    	return false;
    }
    
    public boolean visite(Carte c){
    	if(c.getTas() == Carte.mainDroite){
    		gcMainDroite.setStroke(Color.BLUE);
    		if(c.isSelectionne()){
    			dessinerCarteVerti(gcMainDroite, c.getX(), 0);
    		}else{
    			dessinerCarteVerti(gcMainDroite, c.getX(), c.getY());
    		}
    	}else if(c.getTas() == Carte.mainGauche){
    		gcMainGauche.setStroke(Color.RED);
    		if(c.isSelectionne()){
    			dessinerCarteVerti(gcMainGauche, c.getX(), 0);
    		}else{
    			dessinerCarteVerti(gcMainGauche, c.getX(), c.getY());
    		}
    	}
    	return false;
    }
    
    public boolean visite(FigurineDroite c){
    	dessinerJoueurDroit(gcTerrain);
    	return false;
    }
    
    public boolean visite(FigurineGauche c){
    	dessinerJoueurGauche(gcTerrain);
    	return false;
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
    	Image i = new Image("/Ressources/dosCartePioche.jpg");
    	gc.drawImage(i, 0, 0, 200, 150);
        gc.setFill(Color.RED);
        dessinerCarteHori(gc, 0, 0);
    }
    
    private void dessinerDefausse(GraphicsContext gc){
    	gc.clearRect(0, 0, defausse.getWidth(), defausse.getHeight());
        gc.setFill(Color.GREEN);
        dessinerCarteHori(gc, 0, 0);
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
    
    private void dessinerMainDroite(GraphicsContext gc){
    	gc.clearRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
        gc.setStroke(Color.BLUE);
        gc.strokeRect(0, 0, mainDroite.getWidth(), mainDroite.getHeight());
    }
    
    private void dessinerMainGauche(GraphicsContext gc){
    	gc.clearRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
        gc.setStroke(Color.RED);
        gc.strokeRect(0, 0, mainGauche.getWidth(), mainGauche.getHeight());
    }
    
  //diminuer l'origine des cartes afin de faire croire qu'elles montent
    private void souleverCarteNumero(GraphicsContext gc, double numCarte){
    	gc.clearRect(numCarte*90, 0, 150, 200 );
    	dessinerCarteVerti(gc, numCarte*90, 0);
    }
    
    private void dessinerCarteVerti(GraphicsContext gc, double x, double y){
    	Image i = new Image("/Ressources/dosCarte.jpg");
    	gc.drawImage(i, x, y, 150, 200);
    	gc.strokeRect(x, y, 150, 200);
    }
    
    private void dessinerCarteHori(GraphicsContext gc, double x, double y){
    	gc.strokeRect(x, y, 200, 150);
    }
   
}
