package no.HiOAProsjektV2013.Main;

import java.util.Iterator;

//	Klassen fungerer som en samling av alle datastrukturer for skolesystemet
//	Dette for å unngå clutter i hovedklassen/vindusklassen som skal lage og holde datastrukturen
public class Skole {

	private Liste<Studieprogram> studieprogrammene = new Liste<>();
	private Iterator<Studieprogram> studieIterator = studieprogrammene
			.getIterator();
	private Liste<Laerer> lærerne = new Liste<>();
	private Iterator<Laerer> lærerIterator = lærerne.getIterator();
	private Liste<Fag> fagene = new Liste<>();
	private Iterator<Fag> fagIterator = fagene.getIterator();
	private StudentRegister studentene = new StudentRegister();

	public Skole() {
		// Constructor
	}
	
	// metoden legger til et fag i faglisten, sjekker via privat metode om
	// fagkoden er valid
	// og returnerer true om faget ble lagt til
	public boolean addFag(Fag faget) {
		boolean added = false;

		String fagkode = faget.getFagkode();
		
		// privat metode for å sjekke at fagkoden er unik.
		// Burde legge til bedre håndtering for input fra bruker, slik at
		// brukeren ikke mister alt om det blir skrevet et fagkode som allerede
		// er i bruk
		if (checkValidFagkode(fagkode)) {
			fagene.add(faget);
		}
		return added;
	}

	private boolean checkValidFagkode(String fagkode) {
		boolean check = true;
		while (fagIterator.hasNext()) {
			if (fagkode.equalsIgnoreCase(fagIterator.next().getFagkode())) {
				check = false;
			}
			fagIterator.next();
		}
		return check;
	}

	// Finner alle lærerne med et gitt fornavn
	public Laerer[] findLærerByFornavn(String fnavn) {
		Liste<Integer> posisjonene = new Liste<>();
		for (int i = 0; i < lærerne.size(); i++) {
			if (fnavn.equals(lærerne.get(i).getfNavn())) {
				posisjonene.add(i);
			}
		}

		return alleLærerne(posisjonene);
	}

	public Laerer[] findLærerByEtternavn(String enavn) {

		Liste<Integer> posisjonene = new Liste<>();
		for (int i = 0; i < lærerne.size(); i++) {
			if (enavn.equals(lærerne.get(i).geteNavn())) {
				posisjonene.add(i);
			}
		}
		return alleLærerne(posisjonene);
	}

	// privat metode for å unngå duplisering av kode på eNavn og fNavn-søk
	private Laerer[] alleLærerne(Liste<Integer> pos) {
		Laerer[] lærerMedNavnet = new Laerer[pos.size()];

		for (int j = 0; j < pos.size(); j++) {
			lærerMedNavnet[j] = lærerne.get(pos.get(j));
		}

		return lærerMedNavnet;
	}

	public Liste<Studieprogram> getStudieprogrammene() {
		return studieprogrammene;
	}

	public Liste<Laerer> getLærerne() {
		return lærerne;
	}

	public Liste<Fag> getFagene() {
		return fagene;
	}

	public StudentRegister getStudentene() {
		return studentene;
	}

}
