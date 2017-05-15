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
    	System.out.println("coucou");
    	switch (event.getButton()) {
	        case PRIMARY:
	        	jeu.getPiste().getCasesClick(event.getX(), event.getY());
	            break;
	        default:
	        	System.out.println("coucou");
	            break;
    	}
    }
    
}
