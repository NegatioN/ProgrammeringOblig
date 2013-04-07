package no.HiOAProsjektV2013.DataStructure;

public class Krav {

	private String beskrivelse;
	private boolean godkjent;
	
	public Krav(String beskrivelse){
		this.beskrivelse = beskrivelse;
		godkjent = false;
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
	
	
}
