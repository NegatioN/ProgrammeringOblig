package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//	Klassen fungerer som en samling av alle datastrukturer for skolesystemet
//	Dette for å unngå clutter i hovedklassen/vindusklassen som skal lage og holde datastrukturen
public class Skole implements Serializable{

	private static final long serialVersionUID = 1000L;
	private List<Studieprogram> studieprogrammene = new LinkedList<>();
	private LaererListe lærerne = new LaererListe();
	private FagListe fagene = new FagListe();
	private StudentListe studentene = new StudentListe();
	private Iterator<Studieprogram> iterator;

	public Skole() {
		// Constructor
	}

	public ArrayList<Studieprogram> getStudieprogramListe(){
		ArrayList<Studieprogram> programmene = new ArrayList<>();
		programmene.addAll(studieprogrammene);
		return programmene;
	}
	
	public List<Studieprogram> getStudieprogrammene() {
		return studieprogrammene;
	}

	public LaererListe getLærerne() {
		return lærerne;
	}

	public FagListe getFagene() {
		return fagene;
	}

	public StudentListe getStudentene() {
		return studentene;
	}
	
	//********************************Studieprogrammetoder********************************
	
	//Søk på studieprogram etter navn
	public Studieprogram finnStudProgByNavn(String navn){
		for(Studieprogram sp : studieprogrammene){
			if(navn.equalsIgnoreCase(sp.getNavn()));
				return sp;
		}
		return null;
	}
	
	//Legger til et gitt fag i et gitt studieprogram
	public void addFagToStudProg(String navn, String fagkode){
		Studieprogram sp = finnStudProgByNavn(navn);
		sp.addFag(getFagene().finnFagByFagkode(fagkode));
	}
	
	//Legger til nytt studieprogram med gitt navn i listen studieprogrammene
	public Studieprogram addStudProg(String navn) {
		Studieprogram sp = new Studieprogram(navn);
		studieprogrammene.add(sp);
		return sp;
	}
	
	//Skriver ut all info om studieprogrammene
	public String studprogToString() {
		String stringen = new String();

		refreshIterator();

		while (iterator.hasNext()) {
			stringen += iterator.next().toString() + "\n\n";
		}
		return stringen;
	}

	private void refreshIterator() {
		iterator = studieprogrammene.iterator();
	}
}
