import java.util.Properties;

import javax.security.auth.login.Configuration;

import Modele.Jeu;
import Modele.Reglages;
import Vue.MainApp;

public class Engardoide {

    public static void main(String[] args) {
        Jeu j;
        Properties p = Configuration.proprietes();
        Reglages.init(p);
        j = new Jeu();
        MainApp.creer(args, j, p);
    }

}
