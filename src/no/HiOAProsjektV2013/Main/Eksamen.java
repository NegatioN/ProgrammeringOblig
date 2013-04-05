package no.HiOAProsjektV2013.Main;

import java.util.*;

public class Eksamen {

	private String navn;
	private int karakter;
	private Date dato;
	private Liste<EksamensDeltaker> deltakere = new Liste<>();
	private Iterator<EksamensDeltaker> iterator = deltakere.getIterator();
	
	public Eksamen(String navn, int karakter, Date dato){
		this.navn = navn;
		this.karakter = karakter;
		this.dato = dato;
	}
	
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public int getKarakter() {
		return karakter;
	}
	public void setKarakter(int karakter) {
		this.karakter = karakter;
	}

	public Date getDato() {
		return dato;
	}
	public void setDato(Date dato) {
		this.dato = dato;
	}
	
	//melder studenten av eksamenen. Fjernes fra listen over eksamensdeltakere.
	public void avmeld(Student student){
		while(iterator.hasNext()){
			if(student.equals((iterator.next().getDeltaker()))){
				iterator.remove();
				return;
			}
		}
		//add en eller annen beskjed om at deltakeren ikke ble funnet og dermed ikke fjernet.
		return;
	}
	
	//lager eksamensdeltakeren og legger til i listen
	public void addDeltaker(Student student){
		EksamensDeltaker ed = new EksamensDeltaker(student);
		deltakere.add(ed);
	}
	
}
