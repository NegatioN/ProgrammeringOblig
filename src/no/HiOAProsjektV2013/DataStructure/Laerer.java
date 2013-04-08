package no.HiOAProsjektV2013.DataStructure;

public class Laerer extends Person{
	private String kontorNr;

	public Laerer(String navn, String epost, int tlf, String kontorNr) {
		super(navn, epost, tlf);
		this.kontorNr = kontorNr;
		System.out.println("Lagret lærer: " + navn);
	}

	public String getKontor(){
		return kontorNr;
	}
}
