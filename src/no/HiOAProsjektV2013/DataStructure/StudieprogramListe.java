package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/*
 * Liste over studieprogrammene. Inneholder søkenemetodene for studieprogram.
 */
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
	public ArrayList<Studieprogram> findByNavn(String input){
		ArrayList<Studieprogram> studiene = new ArrayList<>();
		Pattern mønster = Pattern.compile(Pattern.quote(input), Pattern.CASE_INSENSITIVE);
		for(Studieprogram sp : register){
			if(mønster.matcher(sp.getNavn()).find())
				studiene.add(sp);
		}
		return studiene;
	}
	
	//Legger til nytt studieprogram med gitt navn i listen studieprogrammene
	public Studieprogram addStudProg(String navn) {
		Studieprogram sp = new Studieprogram(navn);
		for(Studieprogram program : register)
			if(sp.getNavn().equals(program.getNavn()))
				return program;
		register.add(sp);
		return sp;
	}
	
	public void removeStudieprogram(Studieprogram studprog){
		register.remove(studprog);
	}
	
	//Returnerer liste over alle studieprogram
	public ArrayList<Studieprogram> getAlle() {
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
