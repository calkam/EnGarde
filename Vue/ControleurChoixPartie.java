package Vue;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import Vue.MainApp;


public class ControleurChoixPartie {
		
    private MainApp mainApp;
    
    ObservableList<String> ChoixCombattantList = FXCollections.observableArrayList("Joueur","IA");
    ObservableList<String> ChoixNiveauList = FXCollections.observableArrayList("Facile","Moyen","Difficile","Légendaire");

    
    //Choix type du combattant 1
    @FXML
    private ChoiceBox<String> ChoixCombattant1;
    
    //Choix type du combattant 2
    @FXML
    private ChoiceBox<String> ChoixCombattant2;
    
    //Combattant 1 IA -> Choix niveau du combattant 1
    @FXML
    private Text TexteNiveau1;
    
    @FXML
    private ChoiceBox<String> NiveauCombattant1;
    
    //Combattant 1 joueur -> Choix nom du combattant 1
    @FXML
    private Text TexteNom1;
    
    @FXML
    private TextField NomCombattant1;
    
    //Combattant 2 IA -> Choix niveau du combattant 2
    @FXML
    private Text TexteNiveau2;
    
    @FXML
    private ChoiceBox<String> NiveauCombattant2;
    
    //Combattant 2 joueur -> Choix nom du combattant 2
    @FXML
    private Text TexteNom2;
    
    @FXML
    private TextField NomCombattant2;
    
    @FXML
	private void initialize(){
    	Label modej1 = new Label();
        Label modej2 = new Label();
        Label niveauj1 = new Label();
        Label niveauj2 = new Label();

    	
		ChoixCombattant1.setValue("Joueur");
    	ChoixCombattant1.setItems(ChoixCombattantList);
    	ChoixCombattant2.setValue("IA");
    	ChoixCombattant2.setItems(ChoixCombattantList);
    	NiveauCombattant1.setValue("Facile");
    	NiveauCombattant1.setItems(ChoixNiveauList);
    	NiveauCombattant2.setValue("Facile");
    	NiveauCombattant2.setItems(ChoixNiveauList);
    	
		ChoixCombattant1.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					modej1.setText((String)newValue);
					if(modej1.getText().equals("Joueur")){
						TexteNiveau1.setVisible(false);
						NiveauCombattant1.setVisible(false);
						
						TexteNom1.setVisible(true);
						NomCombattant1.setVisible(true);			
					}
					else if(modej1.getText().equals("IA")){					
						TexteNom1.setVisible(false);
						NomCombattant1.setVisible(false);
						
						TexteNiveau1.setVisible(true);
						NiveauCombattant1.setVisible(true);
					}
				});
		
		ChoixCombattant2.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					modej2.setText((String)newValue);
					if(modej2.getText().equals("Joueur")){
						TexteNiveau2.setVisible(false);
						NiveauCombattant2.setVisible(false);
						
						TexteNom2.setVisible(true);
						NomCombattant2.setVisible(true);			
					}
					else if(modej2.getText().equals("IA")){					
						TexteNom2.setVisible(false);
						NomCombattant2.setVisible(false);
						
						TexteNiveau2.setVisible(true);
						NiveauCombattant2.setVisible(true);
					}
				});
		
		NiveauCombattant1.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					niveauj1.setText((String)newValue);
				});
		
		NiveauCombattant2.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					niveauj2.setText((String)newValue);
				});		
		
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

	@FXML
	private void handleIn(){
		Image imageC = new Image("/Ressources/SourisEpeePlante.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}
	
	@FXML
	private void handleOut(){
		Image imageC = new Image("/Ressources/SourisEpee.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}
}

