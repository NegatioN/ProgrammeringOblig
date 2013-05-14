package no.HiOAProsjektV2013.DataStructure;
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 14.05.13
import java.io.Serializable;

/*
 * Klassen er et enkelt krav i listen av kravene for et fag.
 * Kravet inneholder en beskrivende tekst, og en boolean for om det er bestått eller ikke, for den gitte studenten.
 */
public class Krav implements Serializable{

	private static final long serialVersionUID = 1051L;
	private String beskrivelse;
	private boolean godkjent;
	
	public Krav(String beskrivelse){
		this.beskrivelse = beskrivelse;
		godkjent = false;
	}
	public Krav(Krav krav){
		this.beskrivelse = krav.getBeskrivelse();
		this.godkjent = krav.isGodkjent();
	}

	public boolean isGodkjent() {
		return godkjent;
	}

	public void setGodkjent(boolean godkjent) {
		this.godkjent = godkjent;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}
	
	public String toString() {
		return beskrivelse;
	}
	
}
