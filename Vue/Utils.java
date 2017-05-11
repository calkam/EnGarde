package Vue;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Utils {

    public static void playSound(String nameFile){
    	try{
    		String musicFile = "/home/d/dutronce/Documents/Prog6/Projet_En_Garde/workspace/source/Ressources/" + nameFile;
    		
	        Media m = new Media(new File(musicFile).toURI().toString());
	        MediaPlayer player = new MediaPlayer(m);
	        player.play();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }

}