package Vue;

import Modele.Visiteur;
import Modele.Plateau.Case;
import Modele.Plateau.Jeton;
import Modele.Plateau.MessageBox;
import Modele.Plateau.Piste;
import Modele.Plateau.PlateauScore;
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
import javafx.scene.text.Font;

public class DessinateurCanvasJavaFx extends Visiteur {

	private Piste piste;
	
    private Canvas terrain;
    private Canvas pioche;
    private Canvas defausse;
    private Canvas scoreDroit;
    private Canvas mainDroite;
    private Canvas scoreGauche;
    private Canvas mainGauche;
    
    public static boolean visibilityActivated = true;
    
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
    
    public boolean visite(Piste p){
    	gcTerrain = terrain.getGraphicsContext2D();
    	piste = p;
    	gcTerrain.clearRect(0, 0, terrain.getWidth(), terrain.getHeight());
    	dessinerTerrain(gcTerrain);
		return false;
    }
    
    public boolean visite(Case c){
    	switch(c.getCouleur()){
    		case Case.TRANSPARENT : gcTerrain.setStroke(Color.TRANSPARENT); break;
    		case Case.ROUGE : gcTerrain.setStroke(Color.RED); break;
    		case Case.VERT : gcTerrain.setStroke(Color.GREEN); break;
    		case Case.JAUNE : gcTerrain.setStroke(Color.YELLOW); break;
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
    		gcMainDroite = mainDroite.getGraphicsContext2D();
    		gc = gcMainDroite; 
    	}else{
    		gcMainGauche = mainGauche.getGraphicsContext2D();
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
    		if(c.isVisible() || visibilityActivated){
    			gc.setStroke(Color.BLUE);
    		}else{
    			gc.setStroke(Color.TRANSPARENT);
    		}
    	}else{
    		gc = gcMainGauche;
    		if(c.isVisible() || visibilityActivated){
    			gc.setStroke(Color.RED);
    		}else{
    			gc.setStroke(Color.TRANSPARENT);
    		}
    	}
    	
    	if(c.isVisible() || visibilityActivated){
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
    
    public boolean visite(PlateauScore ps){
    	GraphicsContext gc;
    	if(ps.getCote() == PlateauScore.droite){
    		gcScoreDroit = scoreDroit.getGraphicsContext2D();
    		gc = gcScoreDroit;
    	}else{
    		gcScoreGauche = scoreGauche.getGraphicsContext2D();
    		gc = gcScoreGauche;
    	}
    	dessinerPlateauScore(gc, ps.getX(), ps.getY(), ps.getLargeur(), ps.getHauteur(), ps.getCote());
    	return false;
    }
    
    public boolean visite(Jeton j){
    	GraphicsContext gc;
    	if(j.getCote() == Jeton.droit){
    		gc = gcScoreDroit;
    	}else{
    		gc = gcScoreGauche;
    	}
    	if(j.isVisible()){
    		dessinerJeton(gc, j.getX(), j.getY(), j.getLargeur(), j.getHauteur());	
    	}
    	return false;
    }
    
    public boolean visite(MessageBox m){
    	GraphicsContext gc = gcTerrain;
    	//dessinerMessageBox(gc, m.getX(), m.getY(), m.getLargeur(), m.getHauteur());
    	ecrireTexte(gc, (m.getX()+m.getLargeur()/2), m.getHauteur()/2, m.getTexte(), m.getTexte().length());
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
		gc.clearRect(p.getX(), p.getY(), p.getLargeur(), p.getHauteur());
    	Image i = new Image("/Ressources/dosCartePioche.jpg");
    	gc.drawImage(i, 0, 0, 200, 150 /*p.getLargeur(), p.getHauteur()*/);
        dessinerCarteHoriVerso(gc, 0, 0, 200, 150/*p.getLargeur(), p.getHauteur()*/);
    }
    
    private void dessinerDefausse(GraphicsContext gc, Defausse d){
    	gc.clearRect(d.getX(), d.getY(), d.getLargeur(), d.getHauteur());
    	if(!d.estVide()){
    		dessinerCarteHoriRecto(gc, 0, 0,200, 150 /*d.getLargeur(), d.getHauteur()*/, d.carteDuDessus().getContenu());
    	}
    }
    
    private void dessinerJoueurGauche(GraphicsContext gc, FigurineGauche fg){
    	Image i = new Image("/Ressources/joueurRouge.png");
    	gc.drawImage(i, fg.getX(), fg.getY(), fg.getLargeur(), fg.getHauteur());
    }
    
    private void dessinerJoueurDroit(GraphicsContext gc, FigurineDroite fd){
    	Image i = new Image("/Ressources/joueurBleu.png");
    	gc.drawImage(i, fd.getX(), fd.getY(), fd.getLargeur(), fd.getHauteur());
    }
    
    private void dessinerMain(GraphicsContext gc, Main m){
    	gc.clearRect(0, 0, m.getLargeur(), m.getHauteur());
    }
    
    private void dessinerCarteVertiRecto(GraphicsContext gc, double x, double y, double l, double h, int valeur){
    	gc.clearRect(x, y, l, h);
    	Image i = new Image("/Ressources/N"+ valeur +".png");
    	gc.drawImage(i, x, y, l, h);
    	gc.strokeRect(x, y, l, h);
    }
    
    private void dessinerCarteVertiVerso(GraphicsContext gc, double x, double y, double l, double h){
    	gc.clearRect(x, y, l, h);
    	Image i = new Image("/Ressources/dosCarte.jpg");
    	gc.drawImage(i, x, y, l, h);
    	gc.strokeRect(x, y, l, h);
    }
    
    private void dessinerCarteHoriVerso(GraphicsContext gc, double x, double y, double l, double h){
    	gc.clearRect(x, y, l, h);
    	Image i = new Image("/Ressources/dosCartePioche.jpg");
    	gc.drawImage(i, x, y, l, h);
    	gc.strokeRect(x, y, l, h);
    }
    
    private void dessinerCarteHoriRecto(GraphicsContext gc, double x, double y, double l, double h, int valeur){
    	gc.clearRect(x, y, l, h);
    	Image i = new Image("/Ressources/N"+ valeur +"_rotate.png");
    	gc.drawImage(i, x, y, l, h);
    }
   
    private void dessinerPlateauScore(GraphicsContext gc, double x, double y, double l, double h, int cote){
		Image i;
		gc.clearRect(x, y, l, h);
		if(cote == PlateauScore.droite){
			i = new Image("/Ressources/bareeDeVieBleu.png");
		}else{
			i = new Image("/Ressources/bareeDeVieRouge.png");
		}
    	gc.drawImage(i, x, y, l, h);
    }
    
    private void dessinerJeton(GraphicsContext gc, double x, double y, double l, double h){
		Image i = new Image("/Ressources/coeur.png");
    	gc.drawImage(i, x, y, l, h);
    }

    private void dessinerMessageBox(GraphicsContext gc, float x, float y, float l, float h) {
		// TODO Auto-generated method stub
    	gc.setFill(Color.BROWN);
		gc.fillRect(x, y, l, h);
	}
    
    //3 -> 15, 50 -> 25, 25 -> 6
    
    private void ecrireTexte(GraphicsContext gc, float x, float y, String s, int length){
    	float policeSize = (float) 22.5;
    	float recule = (float) (length*(policeSize/4.10));
    	Font f = new Font("Courier", policeSize);
    	gc.setFill(Color.WHITE);
    	gc.setStroke(Color.BLACK);
    	gc.setFont(f);
    	gc.fillText(s, x-recule, y+15);
    	gc.strokeText(s, x-recule, y+15);
    }
}
