package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*
 * Klassen definerer en Eksamens-hendelse. Et fag kan ha mange eksamener over tid. 
 * Eksamen inneholder en liste av alle eksamensdeltakerne.
 */
public class Eksamen implements Serializable{
	
	private static final long serialVersionUID = 1040L;
	private Date dato;
	private List<EksamensDeltaker> deltakere = new LinkedList<>();
	private Fag fag;
	
	public Eksamen(Date dato, Fag fag){
		this.dato = dato;
		this.fag = fag;
	}
	
	public Eksamen(Date dato, ArrayList<Student> studenter, Fag fag){
		this.dato = dato;
		addOppmeldteStudenter(studenter, fag);
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
		for(EksamensDeltaker ed : deltakere){
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
		for(EksamensDeltaker ed : deltakere){
			if(ed.getDeltaker() == student)
				return;
		}
		EksamensDeltaker ny = new EksamensDeltaker(student);
		deltakere.add(ny);
	}
	
	public void addOppmeldteStudenter(ArrayList<Student> studentene, Fag faget){
		for(Student studenten : studentene)
			if(studenten.innfriddKrav(faget)){
				addDeltaker(studenten);
			}
	}
	
	public String toString(){
		DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
		return formatter.format(dato);
	}
	
}
