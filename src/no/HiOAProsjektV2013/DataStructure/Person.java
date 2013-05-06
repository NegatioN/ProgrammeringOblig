package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;

/*
 * Objektet representerer en person, og blir brukt i både Student og Lærer.
 * Holder på fornavn, etternavn, epost, telefonnummer. 
 */

abstract public class Person implements Serializable{
	

	private static final long serialVersionUID = 1022L;
	private String fNavn, eNavn, epost;
	private int telefonNr;
	
	//variable for posisjon i string-arrayen av navn
	protected static final int FORNAVN = 0, ETTERNAVN = 1, KUNETNAVNSKREVET = 1;
	
	public Person(String navn, String epost, int tlf){
		nameSplitter(navn);
		this.epost = epost;
		telefonNr = tlf;
	}
	
	//Metoden gjør at vi tar inn kun en string som input, men outputter første og siste navn
	//som fornavn og etternavn i Person
	private void nameSplitter(String navn){
		String regex = "\\s";
		String[] alleNavn = navn.split(regex);
		
		this.fNavn = alleNavn[FORNAVN];
		
		if(alleNavn.length >= KUNETNAVNSKREVET)
		this.eNavn = alleNavn[alleNavn.length - ETTERNAVN];
		else
			eNavn = fNavn;
	}

	public int getTelefonNr() {
		return telefonNr;
	}
	
	public String getEpost(){
		return epost;
	}

	public String getfNavn() {
		return fNavn;
	}

	public String geteNavn() {
		return eNavn;
	}
	
	public void setEpost(String epost){
		this.epost = epost;
	}

	public void setTlf(int telefonNr){
		this.telefonNr = telefonNr;
	}
	
}
