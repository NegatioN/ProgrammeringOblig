package no.HiOAProsjektV2013.Main;

public class Person {
	private String navn;
	private int telefonNr;
	
	public Person(String navn, int tlf){
		this.navn = navn;
		telefonNr = tlf;
	}

	public String getNavn() {
		return navn;
	}

	public int getTelefonNr() {
		return telefonNr;
	}

}
