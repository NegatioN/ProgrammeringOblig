package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;

public class Laerer extends Person implements Serializable{
	
	private static final long serialVersionUID = 1011L;
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
