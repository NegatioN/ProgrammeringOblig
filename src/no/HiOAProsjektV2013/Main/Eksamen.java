package no.HiOAProsjektV2013.Main;

import java.util.*;

public class Eksamen {

	private String navn;
	private int karakter;
	private Date dato;
	private List<Student> deltakere = new LinkedList<>();
	
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
	
	public void addDeltaker(Student student){
		deltakere.add(student);
	}
	
}
