package Modele;

import java.util.ArrayList;

/**
 * @author gourdeaf
 *
 */
public class Observable {
	ArrayList<Observateur> observateurs;
	
	public Observable(){
		observateurs = new ArrayList<Observateur>();	
	}

	public void ajoutObservateur(Observateur o){
		observateurs.add(o);
	}
		
	public void supprimeObservateur(Observateur o){
		observateurs.remove(o);
	}
	
	public void notifierObservateurs(Object arg){
		for(Observateur o : observateurs){
			o.miseAJour(this, arg);
		}
	}
}