package Vue;

import Modele.Visiteur;
import Modele.Jeu;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;

public class RafraichissementJavaFX extends AnimationTimer {

    private Jeu jeu;
    private Canvas terrain;
    private Canvas pioche;
    private Canvas defausse;
    private Canvas scoreDroit;
    private Canvas mainDroite;
    private Canvas scoreGauche;
    private Canvas mainGauche;
    private Visiteur[] dessinateurs;
    private int courant;

    RafraichissementJavaFX(Jeu j, Canvas t, Canvas p, Canvas d, Canvas sd, Canvas md, Canvas sg, Canvas mg) {
        this.jeu = j;
		this.terrain = t;
		this.pioche = p;
		this.defausse = d;
		this.scoreDroit = sd;
		this.mainDroite = md;
		this.scoreGauche = sg;
		this.mainGauche = mg;
        this.dessinateurs = new Visiteur[1];
        this.dessinateurs[0] = new DessinateurCanvasJavaFx(terrain, pioche, defausse, scoreDroit, mainDroite, scoreGauche, mainGauche);
        this.courant = 0;
    }

    public void changeDessinateur() {
        courant++;
        if (courant >= dessinateurs.length) {
            courant = 0;
        }
    }

    @Override
    public void handle(long now) {
        jeu.rafraichit(now);
        try {
			if (jeu.accept(dessinateurs[courant])) {
			    Platform.exit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
