package no.HiOAProsjektV2013.DataStructure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FagListe {

	private List<Fag> register = new LinkedList<>();
	private Iterator<Fag> iterator;
	
	public FagListe(){
		
	}
	
	
	// metoden legger til et fag i faglisten, sjekker via privat metode om
	// fagkoden er valid
	// og returnerer true om faget ble lagt til
	public boolean addFag(Fag faget) {

		String fagkode = faget.getFagkode();
		
		// privat metode for å sjekke at fagkoden er unik.
		// Burde legge til bedre håndtering for input fra bruker, slik at
		// brukeren ikke mister alt om det blir skrevet et fagkode som allerede
		// er i bruk
		if (checkValidFagkode(fagkode)) {
			register.add(faget);
			return true;
		}
		return false;
	}
	
	//sjekker om fagkode ikke er lik eksisterende fagkode, og returnerer boolean
	private boolean checkValidFagkode(String fagkode) {
		refreshIterator();
		while (iterator.hasNext()) {
			String fagetsKode = iterator.next().getFagkode();
			if (fagkode.equalsIgnoreCase(fagetsKode)) {
				return false;
			}
		}
		//kommer man gjennom loopen er det ingen fag med samme kode.
		return true;
	}
	
	private void refreshIterator(){
		iterator = register.iterator();
	}
}
