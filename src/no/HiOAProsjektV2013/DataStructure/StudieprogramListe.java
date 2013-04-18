package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudieprogramListe implements Serializable{
	
	private static final long serialVersionUID = 1070L;
	private List<Studieprogram> register = new LinkedList<>();

	public StudieprogramListe() {
		
	}
		
	public ArrayList<Studieprogram> getStudieprogramListe(){
		ArrayList<Studieprogram> programmene = new ArrayList<>();
		programmene.addAll(register);
		return programmene;
	}
	
	//Søk på studieprogram etter navn
	//utvikle noe som gir treff på delvis korrekt tekst for denne?
	public Studieprogram findEnByNavn(String navn){
		for(Studieprogram sp : register){
			if(sp.getNavn().equalsIgnoreCase(navn))
				return sp;
		}
		return null;
	}
	public ArrayList<Studieprogram> findByNavn(String navn){
		ArrayList<Studieprogram> programmene = new ArrayList<>();
		for(Studieprogram sp : register){
			if(navn.equalsIgnoreCase(sp.getNavn()))
				programmene.add(sp);
		}
		return programmene;
	}
	
	//Legger til nytt studieprogram med gitt navn i listen studieprogrammene
	public Studieprogram addStudProg(String navn) {
		Studieprogram sp = new Studieprogram(navn);
		register.add(sp);
		return sp;
	}
	
	//Returnerer liste over alle studieprogram
	public ArrayList<Studieprogram> visAlle() {
		ArrayList<Studieprogram> programmene = new ArrayList<>();
		for(Studieprogram sp : register){
			programmene.add(sp);
		}
		return programmene;
	}
	
	//Skriver ut all info om studieprogrammene
	public String toString() {
		String stringen = new String();

		for(Studieprogram sp: register){
			stringen += sp.toString() + "\n\n";
		}
		return stringen;
	}
}
