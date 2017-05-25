package Modele;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Controleur.ControleurJeu;
import Modele.Joueur.FabriqueJoueur;
import Modele.Joueur.Humain;
import Modele.Joueur.Joueur;
import Modele.Plateau.Figurine;
import Modele.Plateau.MessageBox;
import Modele.Plateau.Piste;
import Modele.Plateau.PlateauScore;
import Modele.Tas.Carte;
import Modele.Tas.Main;
import Vue.MainApp;


public class Sauvegarde {

	public final static int FINDETOUR=0;
	public final static int ENTREDEUX=1;
	public final static int ENCOURS=2;


	private static File xml = new File("Historique.xml");

	public static Manche lireHistorique(String name) throws DOMException, Exception{
		if(xml.exists()){

			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			 Manche manche = null;
	        try {
	        	DocumentBuilder builder = factory.newDocumentBuilder();
	        	Document document= builder.parse(xml);
	            Element racine = document.getDocumentElement();

				Element save = (Element) racine.getElementsByTagName(name).item(0);


				Element date  = (Element) save.getElementsByTagName("Date").item(0);
				//date.getTextContent();

				Element etatencours  = (Element) save.getElementsByTagName("Etatencours").item(0);
				MainApp.iActionFaites = Integer.parseInt(etatencours.getTextContent());

				    //Manche
				Element	numero = (Element) save.getElementsByTagName("Manche").item(0);

				    //Pioche
				 Element pioche = (Element) save.getElementsByTagName("Pioche").item(0);

				    NodeList racineNoeuds = pioche.getChildNodes();
				    int nbRacineNoeuds = racineNoeuds.getLength();
				    Stack<Carte> pile = new Stack<Carte>();


				    for (int i = 0; i<nbRacineNoeuds; i++){
				    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
				            Element carte = (Element) racineNoeuds.item(i);
				            Element valeur = (Element) carte.getElementsByTagName("Valeur").item(0);
						    Element id = (Element) carte.getElementsByTagName("Id").item(0);
				            pile.add(new Carte(Integer.parseInt(id.getTextContent()),Integer.parseInt(valeur.getTextContent())));
				    	}
				    }

				    //Defausse
				 Element defausse = (Element) save.getElementsByTagName("Defausse").item(0);
				    racineNoeuds = defausse.getChildNodes();
				    nbRacineNoeuds = racineNoeuds.getLength();

				 	Stack<Carte> pile_d = new Stack<Carte>();
				    for (int i = 0; i<nbRacineNoeuds; i++){
				    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
				            Element carte = (Element) racineNoeuds.item(i);
				            Element valeur = (Element) carte.getElementsByTagName("Valeur").item(0);
						    Element id = (Element) carte.getElementsByTagName("Id").item(0);
						    pile_d.add(new Carte(Integer.parseInt(id.getTextContent()),Integer.parseInt(valeur.getTextContent())));
				    	}
				    }

				 Element aquidejouer = (Element) save.getElementsByTagName("Aquidejouer").item(0);

				    //Joueur Gauche
				 Element joueurgauche = (Element) save.getElementsByTagName("JoueurGauche").item(0);

				    Element nom_jg = (Element)joueurgauche.getElementsByTagName("Nom").item(0);

				    Element type_jg = (Element) joueurgauche.getElementsByTagName("Type").item(0);

				    //Main
				    Element m = (Element) joueurgauche.getElementsByTagName("Main").item(0);
				    racineNoeuds = m.getChildNodes();
				    nbRacineNoeuds = racineNoeuds.getLength();

				    ArrayList<Carte> main_jg = new ArrayList<Carte>();

				    for (int i = 0; i<nbRacineNoeuds; i++){
				    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
				            Element carte = (Element) racineNoeuds.item(i);
				            Element valeur = (Element) carte.getElementsByTagName("Valeur").item(0);
						    Element id = (Element) carte.getElementsByTagName("Id").item(0);
						    main_jg.add(new Carte(Integer.parseInt(id.getTextContent()),Integer.parseInt(valeur.getTextContent())));
				    	}
				    }

				    Element score_jg = (Element) joueurgauche.getElementsByTagName("Score").item(0);

				    Element pf_jg = (Element) joueurgauche.getElementsByTagName("PositionFigurine").item(0);

				    //Joueur droit
				    Element joueurdroit = (Element) save.getElementsByTagName("JoueurDroit").item(0);

				    Element nom_jd = (Element)joueurdroit.getElementsByTagName("Nom").item(0);

				    Element type_jd = (Element) joueurdroit.getElementsByTagName("Type").item(0);

				    //Main
				    m = (Element) joueurdroit.getElementsByTagName("Main").item(0);
				    racineNoeuds = m.getChildNodes();
				    nbRacineNoeuds = racineNoeuds.getLength();

				    ArrayList<Carte> main_jd = new ArrayList<Carte>();

				    for (int i = 0; i<nbRacineNoeuds; i++){
				    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
				            Element carte = (Element) racineNoeuds.item(i);
				            Element valeur = (Element) carte.getElementsByTagName("Valeur").item(0);
						    Element id = (Element) carte.getElementsByTagName("Id").item(0);
						    main_jd.add(new Carte(Integer.parseInt(id.getTextContent()),Integer.parseInt(valeur.getTextContent())));
				    	}
				    }

				    Element score_jd = (Element) joueurdroit.getElementsByTagName("Score").item(0);

				    Element pf_jd = (Element) joueurdroit.getElementsByTagName("PositionFigurine").item(0);
				    /***************************/

				    Figurine fd = new Figurine(Figurine.DROITE, Integer.parseInt(pf_jd.getTextContent()));
				    Figurine fg = new Figurine(Figurine.GAUCHE, Integer.parseInt(pf_jg.getTextContent()));
				    Piste piste = new Piste(fg, fd);

				    Main m_jd = new Main(Main.droite);
				    Main m_jg = new Main(Main.gauche);

				    m_jd.setMain(main_jd);
				    m_jd.repositionnerMain();

				    m_jg.setMain(main_jg);
				    m_jg.repositionnerMain();

				    Joueur jg = new FabriqueJoueur(Joueur.DirectionDroite, type_jg.getTextContent(),nom_jg.getTextContent(), m_jg, piste).nouveauJoueur();
				    Joueur jd = new FabriqueJoueur(Joueur.DirectionGauche, type_jd.getTextContent(),nom_jd.getTextContent(), m_jd, piste).nouveauJoueur();

				    jg.setNbPoints(Integer.parseInt(score_jg.getTextContent()));
				    jd.setNbPoints(Integer.parseInt(score_jd.getTextContent()));

				    jg.getScore().setNbPoints(Integer.parseInt(score_jg.getTextContent()));
				    jd.getScore().setNbPoints(Integer.parseInt(score_jd.getTextContent()));

				    manche = new Manche(Integer.parseInt(numero.getTextContent()),jg,jd, piste.getMessageBox());

				    manche.getPioche().setPioche(pile);
				    manche.getTourEnCours().setPioche(manche.getPioche());

				    manche.getDefausse().setD(pile_d);
				    manche.getTourEnCours().setDefausse(manche.getDefausse());

				    if(aquidejouer.getTextContent().equals(jg.getNom())){
				    	manche.getTourEnCours().setJoueurPremier(jg);
				        manche.getTourEnCours().getJoueurPremier().setMain(m_jg);

				        ControleurJeu.joueurEnCours = jg;

				        manche.getTourEnCours().setJoueurSecond(jd);
					    manche.getTourEnCours().getJoueurSecond().setMain(m_jd);

					    if(Integer.parseInt(etatencours.getTextContent()) == Sauvegarde.ENTREDEUX){
					    	manche.getJoueur1().getMain().setVisible(false);
					    	manche.getTourEnCours().getJoueurPremier().getMain().setVisible(false);

							manche.getJoueur2().getMain().setVisible(false);
							manche.getTourEnCours().getJoueurSecond().getMain().setVisible(false);
					    }else{
					    	 manche.getJoueur1().getMain().setVisible(true);
					    	 manche.getTourEnCours().getJoueurPremier().getMain().setVisible(true);

							 manche.getJoueur2().getMain().setVisible(false);
							 manche.getTourEnCours().getJoueurSecond().getMain().setVisible(false);
					    }

				    }else{
				    	manche.getTourEnCours().setJoueurPremier(jd);
				    	manche.getTourEnCours().getJoueurPremier().setMain(m_jd);
				        ControleurJeu.joueurEnCours = jd;

				        manche.getTourEnCours().setJoueurSecond(jg);
					    manche.getTourEnCours().getJoueurSecond().setMain(m_jg);


					    if(Integer.parseInt(etatencours.getTextContent()) == Sauvegarde.ENTREDEUX){
					    	manche.getJoueur1().getMain().setVisible(false);
					    	manche.getTourEnCours().getJoueurPremier().getMain().setVisible(false);

							manche.getJoueur2().getMain().setVisible(false);
							manche.getTourEnCours().getJoueurSecond().getMain().setVisible(false);
					    }else{
					    	manche.getTourEnCours().getJoueurSecond().getMain().setVisible(false);
						    manche.getJoueur1().getMain().setVisible(false);

						    manche.getTourEnCours().getJoueurPremier().getMain().setVisible(true);
						    manche.getJoueur2().getMain().setVisible(true);
					    }
				    }

		        }
		        catch (final ParserConfigurationException e) {
		            e.printStackTrace();
		        }
		        catch (final SAXException e) {
		            e.printStackTrace();
		        }
		        catch (final IOException e) {
		            e.printStackTrace();
		        }
			return manche;
		}else{
			return null;
		}

	}

public static ArrayList<ArrayList<String>>recuperationNomSauvegarde(){

	ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>();
	if(xml.exists()){
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = null;
			try {
				builder = factory.newDocumentBuilder();
				Document document;

				document = builder.parse(xml);
				Element racine = document.getDocumentElement();

		        NodeList racineNoeuds = racine.getChildNodes();
		        int nbRacineNoeuds = racineNoeuds.getLength();

		        for (int i = 0; i<nbRacineNoeuds; i++){
			    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    		 Element nom = (Element) racineNoeuds.item(i);

			    		 ArrayList<String> tmp = new ArrayList<String>(3);

			    		 tmp.add(nom.getNodeName());
			    		 tmp.add(nom.getElementsByTagName("Date").item(0).getTextContent());



			    		 Element jg  = (Element) nom.getElementsByTagName("JoueurGauche").item(0);
			    		 Element jd  = (Element) nom.getElementsByTagName("JoueurDroit").item(0);

			    		 tmp.add(jg.getElementsByTagName("Type").item(0).getTextContent()+"("+jg.getElementsByTagName("Nom").item(0).getTextContent()+")" +" VS "+jd.getElementsByTagName("Type").item(0).getTextContent()+"("+jd.getElementsByTagName("Nom").item(0).getTextContent()+")" +"\n");

			    		 str.add(tmp);
			    	}
		        }
			 }
	        catch (final ParserConfigurationException e) {
	            e.printStackTrace();
	        }
	        catch (final SAXException e) {
	            e.printStackTrace();
	        }
	        catch (final IOException e) {
	            e.printStackTrace();
	        }
		}
		return str;
	}

	public static void ecrireHistorique(String nomSauvegarde,Manche manche) throws DOMException, Exception{
		if(xml.exists()){
			System.out.println("Teste modification");
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder parser = factory.newDocumentBuilder();
				Document doc = parser.parse(xml);
				Element racine = doc.getDocumentElement();


				ajoutXml(manche,doc,nomSauvegarde,racine);
				/*Element p = doc.createElement("personne");
		        p.setAttribute("nom", "aa");
		        p.setAttribute("téléphone", "bbb");
		        racine.appendChild(p);*/

				TransformerFactory tfact = TransformerFactory.newInstance();
				Transformer transformer = tfact.newTransformer();
				transformer.setOutputProperty("encoding", "ISO-8859-1");
				DOMSource source = new DOMSource(doc);
				FileWriter fw = new FileWriter(xml);
				StreamResult result = new StreamResult(fw);
				transformer.transform(source, result);
			 } catch (ParserConfigurationException pce) {
					pce.printStackTrace();
			 } catch (TransformerException tfe) {
					tfe.printStackTrace();
			 } catch (SAXException | IOException e) {
				 e.printStackTrace();
			 }


		}else{
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				Document document = builder.newDocument();

				Element jeu = document.createElement("Jeu");
				document.appendChild(jeu);

				ajoutXml(manche,document,nomSauvegarde,jeu);

				TransformerFactory XML_Fabrique_Transformeur = TransformerFactory.newInstance();
				Transformer XML_Transformeur = XML_Fabrique_Transformeur.newTransformer();

				DOMSource source = new DOMSource(document);
				StreamResult resultat = new StreamResult(xml);
				XML_Transformeur.transform(source, resultat);

				System.out.println("Le fichier XML a été généré !");

			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();

			  }
		}
	}

	private static void ajoutXml(Manche m, Document document,String nomSauvegarde,Element jeu) throws DOMException, Exception{

			//Element sauvegarde = document.createElement("Sauvegarde"+this.index);
			System.out.println(nomSauvegarde);
			Element sauvegarde = document.createElement(nomSauvegarde);
			//sauvegarde.setAttribute("nom", nomSauvegarde);
			jeu.appendChild(sauvegarde);

			Element dateNow = document.createElement("Date");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
			String tmp = format.format(new Date());
			dateNow.appendChild(document.createTextNode(tmp));
			sauvegarde.appendChild(dateNow);

			Element etatencours = document.createElement("Etatencours");
			etatencours.appendChild(document.createTextNode(MainApp.iActionFaites+""));
		    sauvegarde.appendChild(etatencours);

		    Element manche = document.createElement("Manche");
		    manche.appendChild(document.createTextNode(m.getNumero()+""));
		    sauvegarde.appendChild(manche);

		    Element pioche = document.createElement("Pioche");
		    sauvegarde.appendChild(pioche);

		    @SuppressWarnings("unchecked")
			Stack<Carte> p = (Stack<Carte>) m.getPioche().getPioche().clone();

		    System.out.println("Pioche:"+m.getPioche().getPioche().size() + " valeur p:"+p.size());


		    while(!p.isEmpty()){
		    	Element carte = document.createElement("Carte");
		    	pioche.appendChild(carte);
			    Element valeur = document.createElement("Valeur");
			    valeur.appendChild(document.createTextNode(p.peek().getContenu()+""));
			    carte.appendChild(valeur);
			    Element id = document.createElement("Id");
			    id.appendChild(document.createTextNode(p.pop().getID()+""));
			    carte.appendChild(id);
		    }

		    Element defausse = document.createElement("Defausse");
		    sauvegarde.appendChild(defausse);

		    @SuppressWarnings("unchecked")
			Stack<Carte> d = (Stack<Carte>) m.getDefausse().getD().clone();

		    while(!d.isEmpty()){
		    	Element carte = document.createElement("Carte");
		    	defausse.appendChild(carte);
			    Element valeur = document.createElement("Valeur");
			    valeur.appendChild(document.createTextNode(d.peek().getContenu()+""));
			    carte.appendChild(valeur);
			    Element id = document.createElement("Id");
			    id.appendChild(document.createTextNode(d.pop().getID()+""));
			    carte.appendChild(id);
		    }

		    Element aquidejouer = document.createElement("Aquidejouer");


		    if(m.getJoueur1().getMain().isVisible()){
		    	 aquidejouer.appendChild(document.createTextNode(m.getJoueur1().getNom()));
		    }else{
		    	 aquidejouer.appendChild(document.createTextNode(m.getJoueur2().getNom()));
		    }

		    sauvegarde.appendChild(aquidejouer);

		    if(m.getJoueur1().getDirection() == Joueur.DirectionDroite){
		    	miseajourJoueur(m,document,sauvegarde,"JoueurGauche",m.getJoueur1());
		    	miseajourJoueur(m,document,sauvegarde,"JoueurDroit",m.getJoueur2());
		    }else{

		      	miseajourJoueur(m,document,sauvegarde,"JoueurGauche",m.getJoueur2());
		    	miseajourJoueur(m,document,sauvegarde,"JoueurDroit",m.getJoueur1());
		    }
	}

	private static void miseajourJoueur(Manche manche,Document document, Element sauvegarde, String string, Joueur joueur1) throws DOMException, Exception {
		Element joueur = document.createElement(string);
	    sauvegarde.appendChild(joueur);

    	Element nom = document.createElement("Nom");
    	nom.appendChild(document.createTextNode(joueur1.getNom()));
	    joueur.appendChild(nom);

	    Element type = document.createElement("Type");

	    if(manche.getJoueur1() instanceof Humain){
	    	type.appendChild(document.createTextNode("Humain"));
	    }else{
	    	type.appendChild(document.createTextNode(joueur1.getNom()));
	    }
	    joueur.appendChild(type);

	    Element main = document.createElement("Main");
	    joueur.appendChild(main);

	    ArrayList<Carte> m = joueur1.getMain().getMain();

	    for(int i = 0 ; i < m.size() ; i++){
	    	Element carte = document.createElement("Carte");
	    	main.appendChild(carte);
		    Element valeur = document.createElement("Valeur");
		    valeur.appendChild(document.createTextNode(m.get(i).getContenu()+""));
		    carte.appendChild(valeur);
		    Element id = document.createElement("Id");
		    id.appendChild(document.createTextNode(m.get(i).getID()+""));
		    carte.appendChild(id);
	    }

	    Element score = document.createElement("Score");
	    score.appendChild(document.createTextNode(joueur1.getScore()+""));
	    joueur.appendChild(score);

	    Element positionFigurine = document.createElement("PositionFigurine");
	    positionFigurine.appendChild(document.createTextNode(joueur1.getPositionDeMaFigurine()+""));
	    joueur.appendChild(positionFigurine);
	}

	public static void supprimerHistorique(String nomSauvegarde){
		if(xml.exists()){
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder parser = factory.newDocumentBuilder();
				Document doc = parser.parse(xml);
				Element racine = doc.getDocumentElement();

				NodeList nl = racine.getChildNodes();

		       for (int k = 0; k < nl.getLength(); ++k) {
		          if(nl.item(k).getNodeType()==Node.ELEMENT_NODE) {
		             Element e = (Element) nl.item(k);

		             if(e.getNodeName().equals(nomSauvegarde)) {
		            	 e.getParentNode().removeChild(e);
		             }
		            /* if(e.getAttribute("nom").equals(nomSauvegarde)) {
		            	 e.getParentNode().removeChild(e);
		             }*/
		          }
		       }


				TransformerFactory tfact = TransformerFactory.newInstance();
				Transformer transformer = tfact.newTransformer();
				transformer.setOutputProperty("encoding", "ISO-8859-1");
				DOMSource source = new DOMSource(doc);
				FileWriter fw = new FileWriter(xml);
				StreamResult result = new StreamResult(fw);
				transformer.transform(source, result);
			 } catch (ParserConfigurationException pce) {
					pce.printStackTrace();
			 } catch (TransformerException tfe) {
					tfe.printStackTrace();
			 } catch (SAXException | IOException e) {
				 e.printStackTrace();
			 }
		}
	}
}