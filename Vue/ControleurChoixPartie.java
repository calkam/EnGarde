package Vue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.awt.TextField;

import Vue.MainApp;


public class ControleurChoixPartie {
		
    private MainApp mainApp;
    
    ObservableList<String> ChoixCombattantList = FXCollections.observableArrayList("Joueur","IA");
    ObservableList<String> ChoixNiveauList = FXCollections.observableArrayList("Facile","Moyen","Difficile");

    
    //Choix type du combattant 1
    @FXML
    private ChoiceBox<String> ChoixCombattant1;
    
    //Choix type du combattant 2
    @FXML
    private ChoiceBox<String> ChoixCombattant2;
    
    //Combattant 1 IA -> Choix niveau du combattant 1
    @FXML
    private ChoiceBox<String> NiveauCombattant1;
    
    //Combattant 1 joueur -> Choix nom du combattant 1
    @FXML
    private TextField NomCombattant1;
    
    //Combattant 2 IA -> Choix niveau du combattant 2
    @FXML
    private ChoiceBox<String> NiveauCombattant2;
    
    //Combattant 2 joueur -> Choix nom du combattant 2
    @FXML
    private TextField NomCombattant2;
    
    @FXML
	private void initialize(){
		ChoixCombattant1.setValue("IA");
    	ChoixCombattant1.setItems(ChoixCombattantList);
    	ChoixCombattant2.setValue("IA");
    	ChoixCombattant2.setItems(ChoixCombattantList);
    	NiveauCombattant1.setValue("Facile");
    	NiveauCombattant1.setItems(ChoixNiveauList);
    	NiveauCombattant2.setValue("Facile");
    	NiveauCombattant2.setItems(ChoixNiveauList);    	
	}
    
    public ControleurChoixPartie(){
    }
    
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
    private void lancerPartie(){
		mainApp.jeu();
    }
	
	@FXML
	private void menu(){
		mainApp.acceuil();
	}

}

