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
	public Studieprogram findByNavn(String navn){
		for(Studieprogram sp : register){
			if(sp.getNavn().equalsIgnoreCase(navn))
				return sp;
		}
		return null;
	}
	
	//Legger til nytt studieprogram med gitt navn i listen studieprogrammene
	public Studieprogram addStudProg(String navn) {
		Studieprogram sp = new Studieprogram(navn);
		register.add(sp);
		return sp;
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
