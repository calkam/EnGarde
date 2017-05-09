package Modele;

import java.util.ArrayList;
import java.util.HashMap;

import Modele.Tas.Carte;

public class Test {

	static Jeu jeu;
	
	public static void main(String[] args) {
		
		ArrayList<Integer> list;
		HashMap <Integer, ArrayList<Integer>> hashmap ;
		
		list = new ArrayList<>();
		hashmap = new HashMap <>();
		
		
		list.add(1);
		hashmap.putIfAbsent(1, new ArrayList<>()) ;
		hashmap.get(1).add(1) ;
		
		hashmap.get(1).remove(0) ;
		list.remove(0) ;
		System.out.println(hashmap.get(1)) ;
		System.out.println(list) ;
		
		/*jeu = new Jeu();
		try {
			jeu.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jeu.getJoueur1());*/
	}
	
}
