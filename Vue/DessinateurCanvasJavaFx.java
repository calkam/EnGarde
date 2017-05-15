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
		gcMainDroite = mainDroite.getGraphicsContext2D();
		gcMainGauche = mainGauche.getGraphicsContext2D();
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
    	switch(c.getCouleur()){
    		case 0 : gcTerrain.setStroke(Color.TRANSPARENT); break;
    		case 1 : gcTerrain.setStroke(Color.RED); break;
    		default : System.out.println("pas de couleur défini");
    	}
    	gcTerrain.strokeRect(c.getX(), c.getY(), c.getLargeur(), c.getHauteur());
		return false;
	}

    public boolean visite(Main m){
    	GraphicsContext gc;
    	if(!m.isVisible()){
    		if(m.getCote() == Main.droite){
    			mainDroite.setDisable(true);
    		}else if(m.getCote() == Main.gauche){
    			mainGauche.setDisable(true);
    		}
    	}else{
    		if(m.getCote() == Main.droite){
    			mainDroite.setDisable(false);
    		}else if(m.getCote() == Main.gauche){
    			mainGauche.setDisable(false);
    		}
    	}

    	if(m.getCote() == Main.droite){
    		gc = gcMainDroite; 
    	}else{
        	gc = gcMainGauche;
    	}
    	m.fixeDimensions((float)mainDroite.getWidth(), (float)mainDroite.getHeight());
		dessinerMain(gc, m);
    	return false;
    }
    
    public boolean visite(Pioche p){
    	gcPioche = pioche.getGraphicsContext2D();
    	p.fixeDimensions((float)pioche.getWidth(), (float)pioche.getHeight());
    	dessinerPioche(gcPioche, p);
    	return false;
    }
    
    public boolean visite(Defausse d){
    	gcDefausse = defausse.getGraphicsContext2D();
    	d.fixeDimensions((float)defausse.getWidth(), (float)defausse.getHeight());
    	dessinerDefausse(gcDefausse, d);
    	return false;
    }
    
    public boolean visite(Carte c){
    	GraphicsContext gc;
    	
    	if(c.getTas() == Carte.mainDroite){
    		gc = gcMainDroite;
    	}else{
    		gc = gcMainGauche; 
    	}
    	
    	if(c.isVisible()){
    		dessinerCarteVertiRecto(gc, c.getX(), c.getY(), c.getLargeur(), c.getHauteur(), c.getContenu());
    	}else{
    		dessinerCarteVertiVerso(gc, c.getX(), c.getY(), c.getLargeur(), c.getHauteur());
    	}
    	return false;
    }
    
    public boolean visite(FigurineDroite fd){
    	dessinerJoueurDroit(gcTerrain, fd);
    	return false;
    }
    
    public boolean visite(FigurineGauche fg){
    	dessinerJoueurGauche(gcTerrain, fg);
    	return false;
    }
    
    /**
     * 
     * DESSIN DES INFOS DU JEU
     */
    
    private void dessinerTerrain(GraphicsContext gc)
    {
        gc.clearRect(0, 0, terrain.getWidth(), terrain.getHeight());
        Image a = new Image("/Ressources/arriere_plan.png");
        gc.drawImage(a, 0, 0);
    }
    
    private void dessinerPioche(GraphicsContext gc, Pioche p){
    	Image i = new Image("/Ressources/dosCartePioche.jpg");
    	gc.drawImage(i, 0, 0, p.getLargeur(), p.getHauteur());
        dessinerCarteHori(gc, 0, 0, p.getLargeur(), p.getHauteur());
    }
    
    private void dessinerDefausse(GraphicsContext gc, Defausse d){
    	gc.clearRect(0, 0, d.getLargeur(), d.getHauteur());
        gc.setFill(Color.BLACK);
        dessinerCarteHori(gc, 0, 0, d.getLargeur(), d.getHauteur());
    }
    
    private void dessinerJoueurGauche(GraphicsContext gc, FigurineGauche fg){
    	Image i = new Image("/Ressources/joueurRouge.png");
    	gc.drawImage(i, fg.getX(), fg.getY(), fg.getLargeur(), fg.getHauteur());
    	gc.strokeRect(fg.getX(), fg.getY(), fg.getLargeur(), fg.getHauteur());
    }
    
    private void dessinerJoueurDroit(GraphicsContext gc, FigurineDroite fd){
    	Image i = new Image("/Ressources/joueurBleu.png");
    	gc.drawImage(i, fd.getX(), fd.getY(), fd.getLargeur(), fd.getHauteur());
    	gc.strokeRect(fd.getX(), fd.getY(), fd.getLargeur(), fd.getHauteur());
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
    
    private void dessinerMain(GraphicsContext gc, Main m){
        gc.setStroke(Color.BLUE);
        gc.strokeRect(0, 0, m.getLargeur(), m.getHauteur());
        //gc.clearRect(0, 0, m.getLargeur(), m.getHauteur());
    }
    
    private void dessinerCarteVertiRecto(GraphicsContext gc, double x, double y, double l, double h, int valeur){
    	Image i = new Image("/Ressources/N"+ valeur +".png");
    	gc.drawImage(i, x, y, l, h);
    	gc.strokeRect(x, y, l, h);
    }
    
    private void dessinerCarteVertiVerso(GraphicsContext gc, double x, double y, double l, double h){
    	Image i = new Image("/Ressources/dosCarte.jpg");
    	gc.drawImage(i, x, y, l, h);
    	gc.strokeRect(x, y, l, h);
    }
    
    private void dessinerCarteHori(GraphicsContext gc, double x, double y, double l, double h){
    	gc.strokeRect(x, y, l, h);
    }
   
}
