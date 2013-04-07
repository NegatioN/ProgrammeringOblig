package no.HiOAProsjektV2013.DataStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Arbeidskrav {

	private String fagkode;
	private List<Krav> register = new ArrayList<>();
	private Iterator<Krav> iterator;

	public Arbeidskrav(Fag fag) {
		fagkode = fag.getFagkode();
	}

	// Sjekker at alle arbeidskrav i listen er innfridd. Hvis ikke alle er
	// innfridd returners false
	public boolean kravInnfridd() {
		refreshIterator();
		
		while(iterator.hasNext()){
			Krav krav = iterator.next();
			if(!krav.isGodkjent())
				return false;
		}
		return true;
	}

	public void addKrav(Krav krav) {
		register.add(krav);
	}
	public String getFagkode(){
		return fagkode;
	}
	private void refreshIterator(){
		iterator = register.iterator();
	}

}
