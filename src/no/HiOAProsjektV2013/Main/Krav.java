package no.HiOAProsjektV2013.Main;

public class Krav {

	private String beskrivelse;
	private boolean status;
	
	public Krav(String beskrivelse){
		this.beskrivelse = beskrivelse;
		status = false;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}
	
	
}
