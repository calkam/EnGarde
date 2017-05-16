package Controleur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import Modele.Jeu;
import Modele.Manche;
import Modele.Tour;
import Modele.Joueur.Joueur;
import Modele.Tas.Carte;
import Vue.MainApp;

public class ControleurJeu {

    private MainApp mainApp;
    
    private Jeu jeu;
    
    @FXML
    private Canvas terrain;
    
    @FXML
    private Canvas mainGauche;
    
    @FXML
    private Canvas mainDroite;
    
    private ArrayList<Carte> cartes;
    
    private Joueur joueurEnCours = null;
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void init(Jeu j){
		this.setJeu(j);
		
		setActionTerrain();
		setActionMain();
		cartes = new ArrayList<Carte>();
	}
	
	private boolean modifierActionPossible(int numJoueur, double x, double y){
		Carte carteAction;
		if(numJoueur==1){
			joueurEnCours = jeu.getJoueur1();
		}else{
			joueurEnCours = jeu.getJoueur2();
		}
		carteAction = joueurEnCours.getMain().getCarteClick(x, y);
    	if(!cartes.contains(carteAction)){
    		cartes.add(carteAction);
    	}else{
    		cartes.remove(carteAction);
    	}
    	
    	return jeu.getManche().getTourEnCours().possibiliteAction(joueurEnCours, cartes);
	}
	
	private void verifierFinDeManche(boolean peutFaireAction){
		int resultat;
		int joueurVictorieux;
		
		if(!peutFaireAction || jeu.getManche().getPioche().estVide()){
    		try {
    			if(!peutFaireAction){
    				resultat = Tour.joueurPremierPerdu;
    			}else{
    				resultat = Tour.piocheVide;
    			}
				joueurVictorieux = jeu.getManche().finDeManche(resultat);
				cartes = new ArrayList<Carte>();
				if(!jeu.gainPartie()){
					jeu.nouvelleManche();
					jeu.lancerLaManche();
				}else{
					if(joueurVictorieux == Manche.JOUEUR1){
						jeu.affichageVictoire(jeu.getJoueur1().getNom(), jeu.getJoueur2().getNom());
					}else{
						jeu.affichageVictoire(jeu.getJoueur2().getNom(), jeu.getJoueur1().getNom());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
	
	private void setActionMain(){
		mainGauche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	boolean peutFaireAction;
        	        	peutFaireAction = modifierActionPossible(1, event.getX(), event.getY());
        	        	verifierFinDeManche(peutFaireAction);
        	            break;
        	        default:
        	            break;
            	}
            }
        });
		
		mainDroite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	boolean peutFaireAction;
        	        	peutFaireAction = modifierActionPossible(2, event.getX(), event.getY());
        	        	verifierFinDeManche(peutFaireAction);
        	            break;
        	        default:
        	            break;
            	}
            }
        });
	}
	
	private void setActionTerrain(){
		terrain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	switch (event.getButton()) {
        	        case PRIMARY:
        	        	if(joueurEnCours != null){
        	        		if(jeu.getManche().getTourEnCours().executerAction(joueurEnCours, (float)event.getX(), (float)event.getY())){
        	        			cartes = new ArrayList<Carte>();
        	        		}
        	        	}
        	            break;
        	        default:
        	            break;
            	}
            }
        });
        
		terrain.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	//jeu.getPiste().getCasesHover(event.getX(), event.getY());
            }
        });
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

}
