package no.HiOAProsjektV2013.Main;

public class Fag {

	private String navn, fagkode, beskrivelse;
	private Laerer lærer;
	private Arbeidskrav krav;
	
	public Fag(String navn, String fagkode, String beskrivelse, Laerer lærer){
		this.beskrivelse = beskrivelse;
		this.navn = navn;
		this.fagkode = fagkode;
		this.lærer = lærer;
	}

	public Laerer getLærer() {
		return lærer;
	}

	public void setLærer(Laerer lærer) {
		this.lærer = lærer;
	}

	public String getNavn() {
		return navn;
	}

	public String getFagkode() {
		return fagkode;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public Arbeidskrav getKrav() {
		return krav;
	}
	
}
