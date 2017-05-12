package Vue;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import Modele.Jeu;
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
		//d.initGraphics();
		//d.dessinerElement();
	}
    
    @FXML
	private void menuPrincipal(){
		mainApp.acceuil();
	}

}
