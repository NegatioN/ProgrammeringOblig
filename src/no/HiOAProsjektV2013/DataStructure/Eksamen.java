package no.HiOAProsjektV2013.DataStructure;

import java.util.*;

public class Eksamen {

	private Date dato;
	private List<EksamensDeltaker> deltakere = new LinkedList<>();
	private Iterator<EksamensDeltaker> iterator;
	
	
	public Eksamen(Date dato){
		this.dato = dato;
	}
	public Eksamen(Date dato, ArrayList<Student> studenter){
		this.dato = dato;
		addOppmeldteStudenter(studenter);
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
	
	public void addOppmeldteStudenter(ArrayList<Student>)
	
	private void refreshIterator(){
		iterator = deltakere.iterator();
	}
	
}
