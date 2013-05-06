package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Klassen definerer en liste av krav i et fag-objekt slik at en student kan ta eksamen
public class Arbeidskrav implements Serializable{

	private static final long serialVersionUID = 1050L;
	private String fagkode;
	private List<Krav> register = new ArrayList<>();

	public Arbeidskrav(Fag fag) {
		fagkode = fag.getFagkode();
	}

	// Sjekker at alle arbeidskrav i listen er innfridd. Hvis ikke alle er
	// innfridd returners false
	public boolean kravInnfridd() {
		for(Krav krav : register){
			if(!krav.isGodkjent())
				return false;
		}
		//hvis vi kommer gjennom loopen er alle krav innfridd.
		return true;
	}

	public void addKrav(Krav krav) {
		register.add(krav);
	}
	public String getFagkode(){
		return fagkode;
	}
	
	public String toString(){
		String stringen = "";
		for(Krav krav : register){
			stringen += krav.getBeskrivelse() + "\n";
		}
		return stringen;
	}
	
	public List<Krav> getKrav(){
		return register;
	}

}
