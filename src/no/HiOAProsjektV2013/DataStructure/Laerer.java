package no.HiOAProsjektV2013.DataStructure;

public class Laerer extends Person{
	private String kontorNr;

	public Laerer(String navn, String epost, int tlf, String kontorNr) {
		super(navn, epost, tlf);
		this.kontorNr = kontorNr;
	}

	public String getKontor(){
		return kontorNr;
	}
	
	public String toString(){
		String stringen = new String();
		stringen = 	"Navn: " + getfNavn() + " " + geteNavn() + 
					"\nE-post: " + getEpost() + 
					"\nTlf: " + getTelefonNr() + 
					"\nKontorNr: " + kontorNr;
		return stringen;
	}
}
