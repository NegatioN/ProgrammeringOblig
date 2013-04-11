package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StudentListe implements Serializable{

	private static final long serialVersionUID = 1020L;

	private static int studentNummer = 100000;

	private List<Student> register;

	public StudentListe() {
		register = new LinkedList<>();
	}

	// legger til en ny student UANSETT og incrementer studentnummer
	public Student addStudent(String navn, String epost, int tlf, String adresse,
			Date start) {
		Student s = new Student(navn, epost, tlf, adresse, newId(), start);
		register.add(s);
		return s;
	}
	//tar inn date eleven sluttet og studenten.
	public void avsluttStudent(Student student, Date dato){
		student.setSlutt(dato);
	}

	public ArrayList<Student> findKravBeståttStudenter(Fag fag) {
		ArrayList<Student> studentene = new ArrayList<>();
		for(Student s : register){
			if(s.innfriddKrav(fag))
				studentene.add(s);
		}
		
//		refreshIterator();
//		Student s = null;
//		while (iterator.hasNext()) {
//			s = iterator.next();
//			if (s.innfriddKrav(fag))
//				studentene.add(s);
//		}
		return studentene;
	}
	public ArrayList<Student> findStudentByNavn(String navn){
		ArrayList<Student> studentene = new ArrayList<>();
		//looper gjennom alle studentene og finner de med korrekt navn (kan være flere)
		for(Student s : register){
			if(s.getfNavn().equalsIgnoreCase(navn))
				studentene.add(s);
			}
		
		return studentene;
	}

	//metoden skal gjøre et binærsøk gjennom studentlista som er i rekkefølge på studentNr
	public Student findStudentByStudentNr(String studNr){
		//input har en s, og 6 tall
		String regex = "s\\d{6}";
		if(!studNr.matches(regex))
			//bruker har skrevet et ikke-valid studentnr
			return null;
		//splitter på non-digit
		regex = "\\D";
		String[] inputFixed = studNr.split(regex);
		//vi får studentnummeret
		int studentnummeret = Integer.parseInt(inputFixed[inputFixed.length-1]);
		//størrelser for binærsøk
		int max = register.size()-1;
		int min = 0;
		
		//binærsøk gjennom register
		while(min < max){
			int mid = (max + min) / 2;
			Student checkStudent = register.get(mid);
			int checkStudentnummer = checkStudent.getStudentnummer();
			if(studentnummeret == checkStudentnummer)
				return checkStudent;
			else if(studentnummeret < checkStudentnummer){
				max = mid;
			}
			else if(studentnummeret > checkStudentnummer){
				min = mid;
			}
		}
		
		
		return null;
	}
	// gir nytt studentnummer, og øker staticvariabel. Gjør variabelen usynlig
	// for andre deler av programmet
	private int newId() {
		return studentNummer++;
	}

	public ArrayList<Student> findStudentByFag(Fag fag) {
		ArrayList<Student> studentene = new ArrayList<>();
		String fagkode = fag.getFagkode();
		for(Student s : register){
			// sjekker hver students arbeidskrav for fagkoden, og ser om de har
			// faget.
			if (s.harFaget(fagkode))
				studentene.add(s);
		}
		// returnerer en liste med alle studentene i faget
		return studentene;
	}

	public ArrayList<Student> findStudentByStart(Date dato) {
		ArrayList<Student> studentene = new ArrayList<>();
		for(Student s : register){
			if (s.getStart().equals(dato))
				studentene.add(s);
		}
		return studentene;
	}

	public ArrayList<Student> findStudentBySlutt(Date dato) {
		ArrayList<Student> studentene = new ArrayList<>();
		for(Student s : register){
			if (s.isAvsluttet()) {
				if (s.getSlutt().equals(dato))
					studentene.add(s);
			}
		}
		return studentene;
	}

	public ArrayList<Student> findStudentByStudieprogram(Studieprogram sp) {
		ArrayList<Student> studentene = new ArrayList<>();
		// går gjennom studentlista og tar ut alle med gitt studieprogram
		for(Student s : register){
			if (s.getStudieprogram().equals(sp)) {
				studentene.add(s);
			}
		}
		return studentene;
	}

	// finner alle studentene i studieprogrammet, og velger ut de som startet i
	// en viss dato.
	public ArrayList<Student> findStudentByStudieprogramByÅr(Studieprogram sp,
			Date dato) {
		ArrayList<Student> checkStudenter = findStudentByStudieprogram(sp);
		ArrayList<Student> studentene = new ArrayList<>();

		for (Student s : checkStudenter) {
			if (dato.equals(s.getStart()))
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
