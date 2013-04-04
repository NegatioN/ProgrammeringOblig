package no.HiOAProsjektV2013.Main;

public class Fag {

	private String navn, fagkode, beskrivelse;
	private Laerer lærer;
	private Arbeidskrav krav;
	private int studiepoeng;
	
	public Fag(String navn, String fagkode, String beskrivelse, int studiepoeng, Laerer lærer){
		this.beskrivelse = beskrivelse;
		this.navn = navn;
		this.fagkode = fagkode;
		this.lærer = lærer;
		this.studiepoeng = studiepoeng;
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
	public int getStudiepoeng(){
		return studiepoeng;
	}
	//Lager et krav for referanse-kravlista basert på en beskrivelse
	public void addKrav(String beskrivelse){
		Krav kravet = new Krav(beskrivelse);
		krav.addKrav(kravet);
	}
	
}
