package no.HiOAProsjektV2013.Main;

public class Teacher extends Person{

	private String kontorNr;
	public Teacher(String navn, String epost, int tlf, String kontorNr) {
		super(navn, epost, tlf);
		this.kontorNr = kontorNr;
	}

	public String getKontor(){
		return kontorNr;
	}
}
