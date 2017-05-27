package Controleur;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import Modele.Jeu;
import Vue.MainApp;


public class ControleurChoixPartie {

    private MainApp mainApp;

    ObservableList<String> ChoixCombattantList = FXCollections.observableArrayList("Humain","IA");
    ObservableList<String> ChoixNiveauList = FXCollections.observableArrayList("Facile","Moyen","Difficile","Légendaire");

    @FXML
    private Button buttonDebut;

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
    private Text nomJ1;

    @FXML
    private Label erreurCombattant;

    @FXML
    private Label TextNombreVie;

    @FXML
    private int NombreVie = 3;

    @FXML
    private ImageView plus;

    @FXML
    private ImageView moins;

    @FXML
	private void initialize(){
    	Label modej1 = new Label();
        Label modej2 = new Label();
        Label niveauj1 = new Label();
        Label niveauj2 = new Label();

        TextNombreVie.setText("3");
		ChoixCombattant1.setValue("Humain");
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
					if(modej1.getText().equals("Humain")){
						NiveauCombattant1.setVisible(false);

						NomCombattant1.setVisible(true);
					}
					else if(modej1.getText().equals("IA")){
						NomCombattant1.setVisible(false);

						NiveauCombattant1.setVisible(true);
					}
				});

		ChoixCombattant2.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					modej2.setText((String)newValue);
					if(modej2.getText().equals("Humain")){
						NiveauCombattant2.setVisible(false);

						NomCombattant2.setVisible(true);
					}
					else if(modej2.getText().equals("IA")){
						NomCombattant2.setVisible(false);

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

		NomCombattant1.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
					@Override
					public void handle(KeyEvent ke){
						if (ke.getCode().equals(KeyCode.ENTER)){
							lancerPartie();
						}
					}
				});

		NomCombattant2.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent ke){
				if (ke.getCode().equals(KeyCode.ENTER)){
					lancerPartie();
				}
			}
		});
	}

    public ControleurChoixPartie(){
    }

	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
	private void incrementeVie(){
		NombreVie = Integer.parseInt(TextNombreVie.getText());
		if (NombreVie < 5){
			if(NombreVie == 2){
				moins.setDisable(false);
				Image moinsEnable = new Image("moins.png");
				moins.setImage(moinsEnable);
			}
			NombreVie++;
			TextNombreVie.setText(Integer.toString(NombreVie));			System.out.println("IA VS IA");
			System.out.println(NiveauCombattant1.getValue());
			System.out.println(NiveauCombattant2.getValue());
		}
	}

	@FXML
	private void handleInPlus(){
		if (NombreVie == 5){
			Image plusDisable = new Image("plus_disable.png");
			plus.setImage(plusDisable);
			plus.setDisable(true);
		} else {
			Image imageC = new Image("SourisEpeePlante.png");
			mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
			Image plusHover = new Image("plus_hover.png");
			plus.setImage(plusHover);
		}
	}

	@FXML
	private void handleOutPlus(){
		if (NombreVie == 5){
			Image plusDisable = new Image("plus_disable.png");
			plus.setImage(plusDisable);
			plus.setDisable(true);
		}else {
			Image imageC = new Image("SourisEpee.png");
			mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
			Image plusEnable = new Image("plus.png");
			plus.setImage(plusEnable);
		}
	}

	@FXML
	private void decrementeVie(){
		NombreVie = Integer.parseInt(TextNombreVie.getText());
		if (NombreVie > 2){
			if(NombreVie == 5){
				plus.setDisable(false);
				Image plusEnable = new Image("plus.png");
				plus.setImage(plusEnable);
			}
			NombreVie--;
			TextNombreVie.setText(Integer.toString(NombreVie));
		}
	}

	@FXML
	private void handleInMoins(){
		if (NombreVie == 2){			System.out.println("IA VS IA");
		System.out.println(NiveauCombattant1.getValue());
		System.out.println(NiveauCombattant2.getValue());
			Image moinsDisable = new Image("moins_disable.png");
			moins.setImage(moinsDisable);
			moins.setDisable(true);
		} else {
			Image imageC = new Image("SourisEpeePlante.png");
			mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
			Image moinsHover = new Image("moins_hover.png");
			moins.setImage(moinsHover);
		}
	}

	@FXML
	private void handleOutMoins(){
		if (NombreVie == 2){
			Image moinsDisable = new Image("moins_disable.png");
			moins.setImage(moinsDisable);
			moins.setDisable(true);
		} else {
			Image imageC = new Image("SourisEpee.png");
			mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
			Image moinsEnable = new Image("moins.png");
			moins.setImage(moinsEnable);
		}
	}

	@FXML
    private void lancerPartie(){
		int nbCaractereMax = 12;

		String nomCombattant1 = NomCombattant1.getText();
		String nomCombattant2 = NomCombattant2.getText();
		String typeCombattant1 = null;
		String typeCombattant2 = null;

		if(ChoixCombattant1.getValue().equals("Humain") && ChoixCombattant2.getValue().equals("Humain")){
			typeCombattant1 = "Humain";
			typeCombattant2 = "Humain";
		}else if(ChoixCombattant1.getValue().equals("Humain") && ChoixCombattant2.getValue().equals("IA")){
			typeCombattant1 = "Humain";
			typeCombattant2 = "IA";
			nomCombattant2 = NiveauCombattant2.getValue();
		}else if(ChoixCombattant1.getValue().equals("IA") && ChoixCombattant2.getValue().equals("Humain")){
			typeCombattant1 = "IA";
			typeCombattant2 = "Humain";
			nomCombattant1 = NiveauCombattant1.getValue();
		}else if(ChoixCombattant1.getValue().equals("IA") && ChoixCombattant2.getValue().equals("IA")){
			typeCombattant1 = "IA";
			typeCombattant2 = "IA";
			nomCombattant1 = NiveauCombattant1.getValue();
			nomCombattant2 = NiveauCombattant2.getValue();
		}

		if(nomCombattant1.length() == 0){
			nomCombattant1 = "Joueur1";
		}
		if(nomCombattant2.length() == 0){
			nomCombattant2 = "Joueur2";
		}

		System.out.println(nomCombattant2);

		if(nomCombattant1.length() < nbCaractereMax){
			if(nomCombattant2.length() < nbCaractereMax){
				Jeu.VICTOIRE = NombreVie;
				mainApp.jeu(nomCombattant1, nomCombattant2, typeCombattant1, typeCombattant2, true);
			}else{
				erreurCombattant.setText("Nom Combattant 2 trop long (<12)");
			}
		}else{
			erreurCombattant.setText("Nom Combattant 1 trop long (<12)");
		}
    }

	@FXML
	private void menu(){
		mainApp.acceuil();
	}

	@FXML
	private void handleIn(){
		Image imageC = new Image("SourisEpeePlante.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}

	@FXML
	private void handleOut(){
		Image imageC = new Image("SourisEpee.png");
		mainApp.getPrimaryStage().getScene().setCursor(new ImageCursor(imageC));
	}
}

