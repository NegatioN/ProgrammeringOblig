package no.HiOAProsjektV2013.DataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//	Klassen fungerer som en samling av alle datastrukturer for skolesystemet
//	Dette for å unngå clutter i hovedklassen/vindusklassen som skal lage og holde datastrukturen
public class Skole {

	private List<Studieprogram> studieprogrammene = new LinkedList<>();
	private LaererListe lærerne = new LaererListe();
	private FagListe fagene = new FagListe();
	private StudentListe studentene = new StudentListe();

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

}
