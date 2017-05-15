package Controleur;

import javafx.fxml.FXML;
import Modele.Jeu;
import Vue.MainApp;

public class ControleurJeu {

    private MainApp mainApp;
    
    private Jeu jeu;
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void init(Jeu j){
		this.setJeu(j);
	}

	@FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}
	
	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	
    /*private void dessinerJoueurGauche(){
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
    	Image im2 = new Image("/Ressources/barreDeVie.png");
    	gc.drawImage(im2, -20, 0, scoreGauche.getWidth(), scoreGauche.getHeight());
    	if(y==0){
    		
    	}else{
    		for(int i=0; i<y; i++){
	    		gc.setStroke(Color.RED);
	    		gc.drawImage(im, scoreDroit.getWidth()/4, scoreDroit.getHeight()*i/5, scoreDroit.getWidth()*2/4, scoreDroit.getHeight()/5);
    		}
    	}
    }
    
    */

}
