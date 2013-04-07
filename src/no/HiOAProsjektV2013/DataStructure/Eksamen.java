package no.HiOAProsjektV2013.DataStructure;

import java.util.*;

public class Eksamen {

	private String navn;
	private int karakter;
	private Date dato;
	private List<EksamensDeltaker> deltakere = new LinkedList<>();
	private Iterator<EksamensDeltaker> iterator;
	
	
	public Eksamen(String navn, Date dato){
		this.navn = navn;
		this.dato = dato;
	}
	
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}

	public Date getDato() {
		return dato;
	}
	public void setDato(Date dato) {
		this.dato = dato;
	}
	public List<EksamensDeltaker> getDeltakere(){
		return deltakere;
	}
	
	//melder studenten av eksamenen. Setter boolean i deltaker til false for oppmeldt. (for historikkens del)
	public void avmeld(Student student){
		refreshIterator();
		while(iterator.hasNext()){
			EksamensDeltaker ed = iterator.next();
			if(student.equals(ed.getDeltaker())){
				ed.avmeld();
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
	
	private void refreshIterator(){
		iterator = deltakere.iterator();
	}
	
}
