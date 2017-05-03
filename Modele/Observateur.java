package Modele;

/**
 * @author gourdeaf
 *
 */
public interface Observateur {

	public void actualiser(Observable o, Object arg);
}