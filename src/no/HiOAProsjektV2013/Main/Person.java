package no.HiOAProsjektV2013.Main;

public class Person {
	private String navn, epost;
	private int telefonNr;
	
	public Person(String navn, String epost, int tlf){
		this.navn = navn;
		this.epost = epost;
		telefonNr = tlf;
	}

	public String getNavn() {
		return navn;
	}

	public int getTelefonNr() {
		return telefonNr;
	}
	
	public String getEpost(){
		return epost;
	}
	
}
