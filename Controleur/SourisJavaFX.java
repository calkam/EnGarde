package Controleur;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import Modele.Jeu;

public class SourisJavaFX implements EventHandler<MouseEvent> {

	Jeu jeu;
	
    public SourisJavaFX(Jeu j){
    	this.jeu = j;
    }
   
    @Override
    public void handle(MouseEvent event) {
    	switch (event.getButton()) {
	        case PRIMARY:
	        	System.out.println("coucou");
	        	jeu.getPiste().getCasesClick(event.getX(), event.getY());
	            break;
	        default:
	            break;
    	}
    }
    
}
