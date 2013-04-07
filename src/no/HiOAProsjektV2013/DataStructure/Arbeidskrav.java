package no.HiOAProsjektV2013.DataStructure;

import java.util.ArrayList;
import java.util.List;

public class Arbeidskrav {

	private String fagkode;
	private List<Krav> register = new ArrayList<>();

	public Arbeidskrav(Fag fag) {
		fagkode = fag.getFagkode();
	}

	// Sjekker at alle arbeidskrav i listen er innfridd. Hvis ikke alle er
	// innfridd returners false
	public boolean kravInnfridd() {
		boolean innfridd = false;

		for (int i = 0; i < register.size(); i++) {
			if (register.get(i).isGodkjent() == false)
				return innfridd;
		}
		return true;
	}

	public void addKrav(Krav krav) {
		register.add(krav);
	}
	public String getFagkode(){
		return fagkode;
	}

}
