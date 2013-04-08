package no.HiOAProsjektV2013.DataStructure;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Fag {

	private String navn, fagkode, beskrivelse, vurderingsform;
	private Laerer lærer;
	private Arbeidskrav krav;
	private int studiepoeng;
	private List<Eksamen> eksamener = new LinkedList<>();
	private ListIterator<Eksamen> iterator = eksamener.listIterator();
	
	
	public Fag(String navn, String fagkode, String beskrivelse, String vurderingsform , int studiepoeng, Laerer lærer){
		this.beskrivelse = beskrivelse;
		this.navn = navn;
		//alltid uppercase fagkode
		this.fagkode = fagkode.toUpperCase();
		this.vurderingsform = vurderingsform;
		this.lærer = lærer;
		this.studiepoeng = studiepoeng;
	}
	public void addEksamen(Eksamen e){
		eksamener.add(e);
	}
	
	//skal returnere startobjekt-1 altså siste, men usikker på om det funker, trenger bugtest.
	public Eksamen getRecentEksamen(){
		refreshIterator();
		return iterator.previous();
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
	private void refreshIterator(){
		iterator = eksamener.listIterator();
	}
}
