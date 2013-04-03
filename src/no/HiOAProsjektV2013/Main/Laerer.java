package no.HiOAProsjektV2013.Main;

public class Laerer extends Person{
	private String kontorNr;

	public Laerer(String navn, String epost, int tlf, String kontorNr) {
		super(navn, epost, tlf);
		this.kontorNr = kontorNr;
	}

	public String getKontor(){
		return kontorNr;
	}
}
