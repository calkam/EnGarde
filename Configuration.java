import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    static void erreur(Exception e, String nom) {
        System.err.println("Impossible de charger " + nom);
        System.err.println(e);
        System.exit(1);
    }

    static void chargerProprietes(Properties p, InputStream in) {
        try {
            p.load(in);
        } catch (IOException e) {
            erreur(e, "defaut.cfg");
        }
    }

    public static Properties proprietes() {
        Properties p=null;
        InputStream in;
		try {
			in = new FileInputStream("/home/c/calkam/ProjetL3/workspace/source/Ressources/defaut.cfg");
			Properties defaut = new Properties();
	        chargerProprietes(defaut, in);
	        try{
		        String nom = System.getProperty("user.home") + "/.engardoides";
	            in = new FileInputStream(nom);
	            p = new Properties(defaut);
	            chargerProprietes(p, in);
	        }catch (FileNotFoundException e1) {
	        	p = defaut;
	        }
            return p;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return p;
    }
}
