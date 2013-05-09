package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import no.HiOAProsjektV2013.Main.EtternavnSammenligner;
import no.HiOAProsjektV2013.Main.FornavnSammenligner;

/*
 * Klassen er en liste av studenter. Studentene er sortert på studentnummer, fornavn og etternavn.
 * Inneholder søkemetodene for studenter. 
 */
public class StudentListe extends PersonListe<Student> implements Serializable{

	private static final long serialVersionUID = 1020L;

	private static int studentNummer = 100000;
	
	private List<Student> register;
	private ArrayList<Student> etternavnRegister, fornavnRegister;

	public StudentListe() {
		register = new LinkedList<Student>();
		etternavnRegister = new ArrayList<Student>();
		fornavnRegister = new ArrayList<Student>();
	}

	// legger til en ny student UANSETT og incrementer studentnummer
	public Student addStudent(String navn, String epost, int tlf, String adresse,
			GregorianCalendar start) {
		Student s = new Student(navn, epost, tlf, adresse, newId(), start);
		//legger til pointer til studentobjekt til liste i kronologisk rekkefølge, og i liste som sorteres på navn.
		register.add(s);
		settInnSortert(s);
		return s;
	}
	//Setter inn studenten sortert i fornavnsregister og etternavnsregister. 
	private void settInnSortert(Student s){
		super.settInnSortert(s, fornavnRegister, new FornavnSammenligner());
		super.settInnSortert(s, etternavnRegister, new EtternavnSammenligner());
	}
	
	public void removeStudent(Student student){
		register.remove(student);
		etternavnRegister.remove(student);
		fornavnRegister.remove(student);
	}
	
	//tar inn date eleven sluttet og studenten.
	public void avsluttStudent(Student student, GregorianCalendar dato){
		student.setSlutt(dato);
	}
	
	//finner studentene basert på fornavn/etternavn og samler begge søkene til en arraylist.
	public ArrayList<Student> findByNavn(String input){
		ArrayList<Student> studentene = super.findByNavn(input, fornavnRegister, etternavnRegister);
		return studentene;
	}
	
	
	//metoden skal gjøre et binærsøk gjennom studentlista som er i rekkefølge på studentNr
	public Student findStudentByStudentNr(String studNr){
		//input har en s, og 6 tall
		String regex = "s\\d{6}";
		if(!studNr.matches(regex)) //bruker har skrevet et ikke-valid studentnr
			return null;
		String[] splittet = studNr.split("\\D"); //splitter på non-digit
		//vi får studentnummeret
		int studNret = Integer.parseInt(splittet[1]);
		
		
		//størrelser for binærsøk
		int max = register.size()-1;
		int min = 0;
		int mid, tempStudNr;
		
		//binærsøk gjennom register
		while(min <= max){
			mid = max + min / 2;
			
			tempStudNr = register.get(mid).getStudentnummer();
			
			if(tempStudNr < studNret)
				min = mid + 1;
			else if(tempStudNr > studNret)
				max = mid - 1;
			else
				return register.get(mid);
		}
		
		return null;
	}
	
	// gir nytt studentnummer, og øker staticvariabel. Gjør variabelen usynlig
	// for andre deler av programmet
	private int newId() {
		return studentNummer++;
	}
	

	public ArrayList<Student> findStudentByStart(int år) {
		ArrayList<Student> studentene = new ArrayList<>();
		for(Student s : register){
			if(s.getStart().get(Calendar.YEAR) == år){
				studentene.add(s);
			}
		}
		return studentene;
	}

	public ArrayList<Student> findStudentBySlutt(int år) {
		ArrayList<Student> studentene = new ArrayList<>();
		for (Student s : register) {
			if (s.isAvsluttet()) {
				if (s.getSlutt().get(Calendar.YEAR) == år) {
					studentene.add(s);
				}
			}
		}
		return studentene;
	}
	//plukker alle studenter som ligger lagret på et spesifikt studie
	public ArrayList<Student> findStudentByStudieprogram(Studieprogram sp) {
		ArrayList<Student> studentene = sp.getStudenter();
		return studentene;
	}

	//brukes KUN i loading av programmet slik at vi får overført verdien
	public void setStudentNrCount(int studnr){
		studentNummer = studnr;
	}
	//brukes til lagring av variabelen i Archiver-klassen
	public int getStudentnummer(){
		return studentNummer;
	}
	
	//Returnerer liste over alle Fag
	public ArrayList<Student> visAlle() {
		ArrayList<Student> studentene = new ArrayList<>();
		for(Student s : register){
			studentene.add(s);
		}
		return studentene;
	}

	public String toString() {
		String stringen = new String();

		for(Student s : register){
			stringen += s.toString() + "\n\n";
		}
		return stringen;
	}

}
