package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.swing.JOptionPane;

/*
 * Klassen definerer en Eksamens-hendelse. Et fag kan ha mange eksamener over tid. 
 * Eksamen inneholder en liste av alle eksamensdeltakerne.
 */
public class Eksamen implements Serializable{
	
	private static final long serialVersionUID = 1040L;
	private GregorianCalendar dato;
	private LinkedList<EksamensDeltaker> deltakere = new LinkedList<>();
	private Fag fag;
	
	public Eksamen(GregorianCalendar dato, Fag fag){
		GregorianCalendar kalender = (GregorianCalendar) GregorianCalendar.getInstance();
		kalender = dato;
		this.dato = kalender;
		this.fag = fag;
	}
	
	public Eksamen(Date dato, ArrayList<Student> studenter, Fag fag){
		GregorianCalendar kalender = (GregorianCalendar) GregorianCalendar.getInstance();
		kalender.setTime(dato);
		this.fag = fag;
		addOppmeldteStudenter(studenter);
	}

	public Fag getFag(){
		return fag;
	}
	public Date getDato() {
		Date date = dato.getTime();
		date.setMonth(date.getMonth()-1);
		return date;
	}
	public GregorianCalendar getKalender(){
		return dato;
	}
	public LinkedList<EksamensDeltaker> getDeltakere(){
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
		for (EksamensDeltaker ed : deltakere) {
			if (ed.getDeltaker().equals(student))
				return;
		}
		if (student.innfriddKrav(fag) && !student.maksForsøkOverskredet(fag)) {
			EksamensDeltaker ny = new EksamensDeltaker(student, fag, dato);
			deltakere.add(ny);
		}else{
			JOptionPane.showMessageDialog(null, "Studenten har ikke bestått krav, eller har overskredet antall forsøk.");
		}
	}
	//legger til alle de oppmeldte studentene (studenter som har bestått alle krav)
	public void addOppmeldteStudenter(ArrayList<Student> studentene){
		for(Student studenten : studentene)
			if(studenten.innfriddKrav(fag) && !studenten.maksForsøkOverskredet(fag)){
				addDeltaker(studenten);
			}
	}
	
	public String toString(){
		DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
		return formatter.format(getDato());
	}
	
}
