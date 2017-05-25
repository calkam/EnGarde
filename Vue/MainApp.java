package Vue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import Controleur.ControleurAcceuil;
import Controleur.ControleurAlertQuitter;
import Controleur.ControleurChoixPartie;
import Controleur.ControleurJeu;
import Controleur.ControleurRegles;
import Controleur.ControleurSauvegardes;
import Modele.Jeu;
import Modele.Manche;
import Modele.Sauvegarde;
import Modele.Plateau.PlateauScore;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	static Jeu jeu;
	static Properties prop;

	private RafraichissementJavaFX r;

    private Stage primaryStage;
    private Stage dialogStage;
    private BorderPane rootLayout;
    public static int iActionFaites;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("En Garde");
        //this.primaryStage.setFullScreen(true);
        this.primaryStage.setWidth(1600);
        this.primaryStage.setHeight(900);
        this.primaryStage.getIcons().add(new Image("dosCarte.png"));

        iActionFaites = Sauvegarde.ENCOURS;

        initRootLayout();
        acceuil();
        //jeu("Joueur1", "Moyen", "Humain", "IA", true);
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Acceuil
     */
    public void acceuil() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/Acceuil.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ControleurAcceuil controller = loader.getController();
            controller.setMainApp(this);

            if(r != null){
            	r.stop();
            }

            Image imageC = new Image("SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC , 100, 100));

            //Utils.playSound("MainTheme.mp3");
            //Utils.playSound("itsTimeToDuel.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   	/**
    * Ouvre une boire de dialogue qui demande si l'utilisateur veux bien quitter
    */
   	public boolean alertQuitter() {
       try {
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("/Vue/AlertQuitter.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage.
           dialogStage = new Stage();
           dialogStage.initStyle(StageStyle.UNDECORATED);
           dialogStage.setTitle("Alerte");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           Image imageC = new Image("SourisEpee.png");
           scene.setCursor(new ImageCursor(imageC));

           ControleurAlertQuitter controller = loader.getController();
           controller.setDialogStage(dialogStage);

           // Show the dialog and wait until the user closes it
           dialogStage.showAndWait();
           return controller.isOkClicked();
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }

   	public void sauvegardes(Boolean bfenetre){
   		try {
			WritableImage writableImage = new WritableImage((int)primaryStage.getScene().getWidth(), (int)primaryStage.getScene().getHeight());
	   		primaryStage.getScene().snapshot(writableImage);

	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/Vue/Sauvegardes.fxml"));
	        AnchorPane personOverview;

			personOverview = (AnchorPane) loader.load();

	        ObservableList<Person> data = FXCollections.observableArrayList();
	        TableView<Person> table = new TableView<>();
	        TableColumn firstNameCol = new TableColumn<>("Nom");
	        firstNameCol.setMinWidth(240);
	        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

	        TableColumn lastNameCol = new TableColumn<>("Type");
	        lastNameCol.setMinWidth(310);
	        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

	        TableColumn date = new TableColumn<>("date");
	        date.setMinWidth(150);
	        lastNameCol.setMaxWidth(150);
	        date.setCellValueFactory(new PropertyValueFactory<>("Date"));

	        table.setItems(data);
	        table.getColumns().addAll(firstNameCol, lastNameCol, date);

	        ArrayList<ArrayList<String>> str = Sauvegarde.recuperationNomSauvegarde();

	        if(!str.isEmpty()){
	            for(int i = 0 ; i < str.size();i++){
	            	if( str .get(i).get(0).length() > 14 && str.get(i).get(0).substring(0, 14).equals("aucuncaractere")){
	            		data.add(new Person(str.get(i).get(0).substring(14),str.get(i).get(2),str.get(i).get(1)));
	            	}else{
	            		data.add(new Person(str.get(i).get(0),str.get(i).get(2),str.get(i).get(1)));
	            	}
	            }
	        }
	        table.setTranslateY(100);
	        table.setTranslateX(200);

	        table.setMinWidth(700);
	        table.setMaxWidth(700);
	        table.setMaxHeight(600);
	        table.setMinHeight(500);
	        table.setStyle("-fx-background-color: rgba(0,0,0,0.75);");
	        table.getStylesheets().add("Vue/Theme.css");
	        table.getSelectionModel().selectedIndexProperty().addListener(observable ->affichageImage(table,str,personOverview));

	        creationBouton(personOverview,table,str,bfenetre, writableImage);

	        personOverview.getChildren().add(table);
	        rootLayout.setCenter(personOverview);

	        ControleurSauvegardes controller = loader.getController();
	        controller.setMainApp(this);

	        Image imageC = new Image("SourisEpee.png");
	        primaryStage.getScene().setCursor(new ImageCursor(imageC));
   		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

   	private void creationBouton(AnchorPane personOverview,TableView<Person> table, ArrayList<ArrayList<String>> str, Boolean bfenetre, WritableImage writableImage) {
   		if(bfenetre){
   			Button menu = new Button("Menu Principal");
   			menu.setMinWidth(275);
   			menu.setMinHeight(40);
   			menu.setLayoutX(200.0);
   			menu.setLayoutY(700);
   			menu.setTextOverrun(OverrunStyle.CLIP);
   			menu.setFont(Font.font("Calibri", 20));
   			menu.setOnAction(actionEvent ->  acceuil());
   			personOverview.getChildren().add(menu);

   			Button charger = new Button("Charger");
   			charger.setMinWidth(275);
   			charger.setMinHeight(40);
   			charger.setLayoutX(829.0);
   			charger.setLayoutY(700);
   			charger.setTextOverrun(OverrunStyle.CLIP);
   			charger.setFont(Font.font("Calibri", 20));
   			charger.setDisable(true);
   			charger.setOnAction(actionEvent ->  chargerElement(personOverview,table,str));
   			personOverview.getChildren().add(charger);

   			Button supprimer = new Button("Supprimer");
   			supprimer.setMinWidth(275);
   			supprimer.setMinHeight(40);
   			supprimer.setLayoutX(1125.0);
   			supprimer.setLayoutY(700);
   			supprimer.setTextOverrun(OverrunStyle.CLIP);
   			supprimer.setFont(Font.font("Calibri", 20));
   			supprimer.setDisable(true);
   			supprimer.setOnAction(actionEvent ->  supprimerElement(personOverview,table,str));
   			personOverview.getChildren().add(supprimer);
   		}else{
   			Button jeu = new Button("Retour au jeu");
   			jeu.setMinWidth(275);
   			jeu.setMinHeight(40);
   			jeu.setLayoutX(200.0);
   			jeu.setLayoutY(700);
   			jeu.setTextOverrun(OverrunStyle.CLIP);
   			jeu.setFont(Font.font("Calibri", 20));
   			jeu.setOnAction(actionEvent ->  jeu("","","","",false));
   			personOverview.getChildren().add(jeu);

   			Button ajout = new Button("Ajouter");
   			ajout.setMinWidth(275);
   			ajout.setMinHeight(40);
   			ajout.setLayoutX(829.0);
   			ajout.setLayoutY(700);
   			ajout.setTextOverrun(OverrunStyle.CLIP);
   			ajout.setFont(Font.font("Calibri", 20));
   			ajout.setOnAction(actionEvent ->  ajouterElement(personOverview,table,str,writableImage));
   			personOverview.getChildren().add(ajout);

   			Button ecrase = new Button("Ecraser");
   			ecrase.setMinWidth(275);
   			ecrase.setMinHeight(40);
   			ecrase.setLayoutX(1125.0);
   			ecrase.setLayoutY(700);
   			ecrase.setTextOverrun(OverrunStyle.CLIP);
   			ecrase.setFont(Font.font("Calibri", 20));
   			ecrase.setDisable(true);
   			ecrase.setOnAction(actionEvent ->  ecraserElement(personOverview,table,str,writableImage));
   			personOverview.getChildren().add(ecrase);
   		}

	}

	private void ecraserElement(AnchorPane personOverview,TableView<Person> table, ArrayList<ArrayList<String>> str, WritableImage writableImage){
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		String nom = new String(str.get(selectedIndex).get(0));
		supprimerElement(personOverview,table,str);

		try {
			Sauvegarde.ecrireHistorique(nom, jeu.getManche());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file = new File(nom);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
			System.out.println("snapshot saved: " + file.getAbsolutePath());
		} catch (IOException ex) {
		}
		sauvegardes(false);
		//retourJeu(personOverview,table,str);
	}

	private void ajouterElement(AnchorPane personOverview, TableView<Person> table,ArrayList<ArrayList<String>> str, WritableImage writableImage){
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/FenetreSauvegarder.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setTitle("Alerte");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            Image imageC = new Image("SourisEpee.png");
            scene.setCursor(new ImageCursor(imageC));


            TextField textField = new TextField ();
            textField.setLayoutX(250);
            textField.setLayoutY(70);
            textField.setMinWidth(250);
            page.getChildren().add(textField);

            Button sauvegarder = new Button("Sauvegarder");
            sauvegarder.setMinWidth(175);
            sauvegarder.setMinHeight(40);
            sauvegarder.setLayoutX(360.0);
            sauvegarder.setLayoutY(140.0);
            sauvegarder.setTextOverrun(OverrunStyle.CLIP);
            sauvegarder.setFont(Font.font("Calibri", 20));
            sauvegarder.setOnAction(actionEvent -> enregistrerPartie(textField,str ,writableImage,sauvegarder) );
            sauvegarder.setDisable(true);
            page.getChildren().add(sauvegarder);

            textField.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event) {
					textField.clear();
				}
            });
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                public void handle(KeyEvent event) {
                  //if (event.getCode().equals(KeyCode.ENTER)) {}
                	sauvegarder.setDisable(false);
                }
            });


            ControleurAlertQuitter controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}


	private void enregistrerPartie(TextField textField, ArrayList<ArrayList<String>> str, WritableImage writableImage, Button sauvegarder){
   		String tmp = textField.getText();

   		if(tmp.charAt(0) >='0' && tmp.charAt(0) <= '9'){
   			tmp = "aucuncaractere"+tmp;
   		}

   	  if(!str.isEmpty()){
   		int i = 0;
   		while(i < str.size() && (!str.get(i).get(0).equals(tmp))){
   			i++;
   		}

   		if(i >= str.size()){
   			dialogStage.close();
			File file = new File(tmp);
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
				System.out.println("snapshot saved: " + file.getAbsolutePath());
				Sauvegarde.ecrireHistorique(tmp,jeu.getManche());

			} catch (IOException ex) {
			} catch (Exception e) {
				e.printStackTrace();
			}
			sauvegardes(false);
   		}else{
   			textField.clear();
   			textField.setText("ERREUR:NOM DEJA EXISTANT");
   			sauvegarder.setDisable(true);
   		}
   	  }else{
   		dialogStage.close();
		File file = new File(tmp);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
			System.out.println("snapshot saved: " + file.getAbsolutePath());
			Sauvegarde.ecrireHistorique(tmp,jeu.getManche());
		} catch (IOException ex) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		sauvegardes(false);
   	  }
   	}

	private void supprimerElement(AnchorPane personOverview,TableView<Person> table, ArrayList<ArrayList<String>> str){
        int selectedIndex = table.getSelectionModel().getSelectedIndex();

    	Sauvegarde.supprimerHistorique(str.get(selectedIndex).get(0));

    	File file = new File(str.get(selectedIndex).get(0));
		file.delete();

    	str.remove(selectedIndex);

   		table.getItems().remove(selectedIndex);
   		int index = personOverview.getChildren().size()-1;
		if(personOverview.getChildren().get(index) instanceof ImageView){
			personOverview.getChildren().remove(index);
        }
		if(str.isEmpty()){
        	personOverview.getChildren().get(1).setDisable(true);
			personOverview.getChildren().get(2).setDisable(true);
        }

   	}

	private void chargerElement(AnchorPane personOverview,TableView<Person> table, ArrayList<ArrayList<String>> str){

		try {
			int selectedIndex = table.getSelectionModel().getSelectedIndex();
			Manche m;
			m = Sauvegarde.lireHistorique(str.get(selectedIndex).get(0));

			jeu.setJoueur1(m.getJoueur1());
			jeu.setJoueur2(m.getJoueur2());

			jeu.setPiste(m.getJoueur1().getPiste());

			jeu.setPlateauScoreJ1(new PlateauScore(m.getJoueur2().getScore(), PlateauScore.gauche));
			jeu.setPlateauScoreJ2(new PlateauScore(m.getJoueur1().getScore(), PlateauScore.droite));

			jeu.setManche(m);
			//Ajouter le nouveau plateau de score ???
			jeu("","","","",false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}

   	private void affichageImage(TableView<Person> table, ArrayList<ArrayList<String>> str, AnchorPane personOverview){
   		if(!str.isEmpty()){
   			int index = table.getSelectionModel().getSelectedIndex();
	   	   	String name = str.get(index).get(0);

	   	    ImageView imageView = new ImageView();
	   	    imageView.setX(950);
	   	    imageView.setY(100);

   	        try {
   				imageView.setImage(new Image(new FileInputStream(new File(name)),450,300,false,false));
   				index = personOverview.getChildren().size()-1;
   				if(personOverview.getChildren().get(index) instanceof ImageView){
   					personOverview.getChildren().remove(index);
   				}
   				personOverview.getChildren().add(imageView);

   				personOverview.getChildren().get(1).setDisable(false);
   				personOverview.getChildren().get(2).setDisable(false);
   			} catch (FileNotFoundException e) {

   				index = personOverview.getChildren().size()-1;
   				if(personOverview.getChildren().get(index) instanceof ImageView){
   					personOverview.getChildren().remove(index);
   				}
   			}
   		}
   	}

   	public static class Person {
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty date;

        private Person(String fName, String lName, String strdate) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.date =  new SimpleStringProperty(strdate);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String strdate) {
            date.set(strdate);
        }
    }

   	public void jeu(String j1, String j2, String type1, String type2, Boolean bjeu) {
   		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/Jeu.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            //Utils.playSound("itsTimeToDuel.mp3");

            Canvas terrain = null, pioche = null, defausse = null, mainDroite = null, mainGauche = null, scoreDroit = null, scoreGauche = null;

            for(int i=0; i<personOverview.getChildren().size(); i++){
            	if(personOverview.getChildren().get(i).getId() != null){
            		switch(personOverview.getChildren().get(i).getId()){
	            		case "terrain" : terrain = (Canvas) personOverview.getChildren().get(i); break;
	            		case "pioche" : pioche = (Canvas) personOverview.getChildren().get(i); break;
	            		case "defausse" : defausse = (Canvas) personOverview.getChildren().get(i); break;
	            		case "mainGauche" : mainGauche = (Canvas) personOverview.getChildren().get(i); break;
	            		case "mainDroite" : mainDroite = (Canvas) personOverview.getChildren().get(i); break;
	            		case "scoreDroit" : scoreDroit = (Canvas) personOverview.getChildren().get(i); break;
	            		case "scoreGauche" : scoreGauche = (Canvas) personOverview.getChildren().get(i); break;
            		}
            	}
            }

            Button sauvegarder = new Button("Sauvegarder");
            sauvegarder.setMinWidth(151);
            sauvegarder.setMinHeight(37);
            sauvegarder.setLayoutX(289.0);
            sauvegarder.setLayoutY(16.0);
            sauvegarder.setTextOverrun(OverrunStyle.CLIP);
            sauvegarder.setFont(Font.font("Calibri", 20));
            sauvegarder.setOnAction(actionEvent ->  sauvegardes(false));
			personOverview.getChildren().add(sauvegarder);

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            if(bjeu){
            	try {
    				jeu.init(j1, j2, type1, type2);
    				jeu.lancerJeu();
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
            //changeDisableMain

            // Give the controller access to the main app.
            ControleurJeu controller = loader.getController();
            controller.setMainApp(this);
            controller.init(jeu, bjeu);

            if(r != null){
            	r.stop();
            }

            r = new RafraichissementJavaFX(jeu, terrain, pioche, defausse, scoreDroit, mainDroite, scoreGauche, mainGauche);
    		r.start();

            Image imageC = new Image("SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void choixPartie() {
   		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/ChoixPartie.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ControleurChoixPartie controller = loader.getController();
            controller.setMainApp(this);

            Image imageC = new Image("SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void regles() {
   		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Vue/Regles.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ControleurRegles controller = loader.getController();
            controller.setMainApp(this);

            Image imageC = new Image("SourisEpee.png");
            primaryStage.getScene().setCursor(new ImageCursor(imageC));

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void creer(String[] args, Jeu j, Properties p){
    	jeu = j;
    	prop = p;
    	launch(args);
    }

	public void setActionFaites(int i) {
		this.iActionFaites = i;
	}

	public int getActionFaites(){
		return this.iActionFaites;
	}

}