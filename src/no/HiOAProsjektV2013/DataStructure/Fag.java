package no.HiOAProsjektV2013.DataStructure;

public class Fag {

	private String navn, fagkode, beskrivelse, vurderingsform;
	private Laerer lærer;
	private Arbeidskrav krav;
	private int studiepoeng;
	
	public Fag(String navn, String fagkode, String beskrivelse, String vurderingsform , int studiepoeng, Laerer lærer){
		this.beskrivelse = beskrivelse;
		this.navn = navn;
		//alltid uppercase fagkode
		this.fagkode = fagkode.toUpperCase();
		this.vurderingsform = vurderingsform;
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
	
	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	public int getStudiepoeng(){
		return studiepoeng;
	}
	
	public Arbeidskrav getKrav() {
		return krav;
	}
	
	public String getVurderingsform(){
		return vurderingsform;
	}
	
	public void setVurderingsform(String vurderingsform) {
		this.vurderingsform = vurderingsform;
	}
	
	//Lager et krav for referanse-kravlista basert på en beskrivelse
	public void addKrav(String beskrivelse){
		Krav kravet = new Krav(beskrivelse);
		krav.addKrav(kravet);
	}
}
