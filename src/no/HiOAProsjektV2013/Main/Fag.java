package no.HiOAProsjektV2013.Main;

public class Fag {

	private String navn, fagkode, beskrivelse;
	private Laerer l�rer;
	private Arbeidskrav krav;
	private int studiepoeng;
	
	public Fag(String navn, String fagkode, String beskrivelse, int studiepoeng, Laerer l�rer){
		this.beskrivelse = beskrivelse;
		this.navn = navn;
		this.fagkode = fagkode;
		this.l�rer = l�rer;
		this.studiepoeng = studiepoeng;
	}

	public Laerer getL�rer() {
		return l�rer;
	}

	public void setL�rer(Laerer l�rer) {
		this.l�rer = l�rer;
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
	public int getStudiepoeng(){
		return studiepoeng;
	}
	//Lager et krav for referanse-kravlista basert p� en beskrivelse
	public void addKrav(String beskrivelse){
		Krav kravet = new Krav(beskrivelse);
		krav.addKrav(kravet);
	}
	
}
