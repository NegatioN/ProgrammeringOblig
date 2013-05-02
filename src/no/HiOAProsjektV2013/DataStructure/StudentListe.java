package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import no.HiOAProsjektV2013.Main.EtternavnSammenligner;
import no.HiOAProsjektV2013.Main.FornavnSammenligner;

public class StudentListe extends PersonListe<Student> implements Serializable{

	private static final long serialVersionUID = 1020L;

	private static int studentNummer = 100000;

	private final int FØRSTE = 0, FORNAVN = 2000, ETTERNAVN = 3000;
	
	private List<Student> register;
	private ArrayList<Student> etternavnRegister, fornavnRegister;

	public StudentListe() {
		register = new LinkedList<Student>();
		etternavnRegister = new ArrayList<Student>();
		fornavnRegister = new ArrayList<Student>();
	}

	// legger til en ny student UANSETT og incrementer studentnummer
	public Student addStudent(String navn, String epost, int tlf, String adresse,
			Date start) {
		Student s = new Student(navn, epost, tlf, adresse, newId(), start);
		//legger til pointer til studentobjekt til liste i kronologisk rekkefølge, og i liste som sorteres på navn.
		register.add(s);
		settInnSortert(s);
		return s;
	}
	//et current litt urealistisk problem er at den gir litt blankt F i skikkelig sortering når folk har identisk navn. og dette føkker opp søket.
	private void settInnSortert(Student s){
		
		int pos = 0;
		try{
			//gir såvidt jeg forstår en negativ verdi av der det skal settes inn HVIS den ikke finner en som er .equals
		pos = Collections.binarySearch(etternavnRegister, s,new EtternavnSammenligner());
		}catch(NullPointerException npe){
			npe.printStackTrace();
		}
		//setter inn på sortert plass for etternavn
		if(etternavnRegister.isEmpty())
			etternavnRegister.add(s);
		else{
		pos = (Math.abs(pos) - 1);
			etternavnRegister.add(pos, s);
		}
		try{
		pos = Collections.binarySearch(fornavnRegister, s, new FornavnSammenligner());
		pos = (Math.abs(pos) - 1);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		if(fornavnRegister.isEmpty())
			fornavnRegister.add(s);
		else{
		pos = (Math.abs(pos));
			fornavnRegister.add(pos, s);
		}
	}
	
	public void removeStudent(Student student){
		register.remove(student);
		etternavnRegister.remove(student);
	}
	
	//tar inn date eleven sluttet og studenten.
	public void avsluttStudent(Student student, Date dato){
		student.setSlutt(dato);
	}

	//finnes ved knapp i Studentinfo-display
	public ArrayList<Student> findKravBeståttStudenter(Fag fag) {
		ArrayList<Student> studentene = new ArrayList<>();
		for(Student s : register){
			if(s.innfriddKrav(fag))
				studentene.add(s);
		}
		
		return studentene;
	}

	//fungerer currently ikke om du skriver inn en liten bokstav først i søket. 
	//finner studentene basert på fornavn/etternavn og samler begge søkene til en arraylist.
	public ArrayList<Student> findByNavn(String input) {
		System.out.println(fornavnRegister.toString());
		System.out.println(etternavnRegister.toString());
		char firstLetter = input.charAt(FØRSTE);
		firstLetter = Character.toUpperCase(firstLetter);
		int max = (etternavnRegister.size() - 1);
		int min = 0;
		int mid;
		int searchStart = 0;
		Student tempstudent;
		
		while (min <= max) {
			mid = (max + min) / 2;
			tempstudent = etternavnRegister.get(mid);
			// finner plassen i arrayen hvor vi skal starte sekvensiellt søk.
			if (firstLetter == tempstudent.geteNavn().charAt(FØRSTE)) {
				searchStart = etternavnRegister.indexOf(tempstudent);
				// ruller oss tilbake til starten av denne bokstavens forekomst.
				try{
					while (firstLetter == etternavnRegister
							.get(searchStart - 1).geteNavn().charAt(FØRSTE)) {
						searchStart--;
					}
				}catch(ArrayIndexOutOfBoundsException e){
					//fortsetter med uendret searchStart
				}
				break;
			} else if (firstLetter < tempstudent.geteNavn().charAt(FØRSTE)) {
				max = mid - 1;
			} else if (firstLetter > tempstudent.geteNavn().charAt(FØRSTE)) {
				min = mid + 1;
			}

		}// end while for etternavn

		ArrayList<Student> studenteneEtternavn = looper(searchStart, input, etternavnRegister, firstLetter, ETTERNAVN);
		
		searchStart = 0;
		min = 0;
		max = (fornavnRegister.size() -1);
		while (min <= max) {
			mid = (max + min) / 2;
			tempstudent = fornavnRegister.get(mid);
			// finner plassen i arrayen hvor vi skal starte sekvensiellt søk.
			if (firstLetter == tempstudent.getfNavn().charAt(FØRSTE)) {
				searchStart = fornavnRegister.indexOf(tempstudent);
				// ruller oss tilbake til starten av denne bokstavens forekomst.
				if (searchStart != 0) {
					while (firstLetter == fornavnRegister.get(searchStart - 1)
							.getfNavn().charAt(FØRSTE)) {
						searchStart--;
					}
				}
				break;
			} else if (firstLetter < tempstudent.getfNavn().charAt(FØRSTE)) {
				max = mid - 1;
			} else if (firstLetter > tempstudent.getfNavn().charAt(FØRSTE)) {
				min = mid + 1;
			}

		}// end while for fornavn
		ArrayList<Student> studenteneFornavn = looper(searchStart, input, fornavnRegister, firstLetter, FORNAVN);
		
		ArrayList<Student> studentene = samler(studenteneFornavn, studenteneEtternavn);
		
		return studentene;
	}
	//looper gjennom alle med samme forbokstav som input sekvensiellt. Enten i fornavnslista eller etternavn.
	private ArrayList<Student> looper(int start, String input, ArrayList<Student> sortedList, char first, int qualifier){
		ArrayList<Student> studentene = new ArrayList<>();
		Iterator iterator;
		if (qualifier == ETTERNAVN) {
			try {
				// legger til alle studentene fra searchStart til første bokstav
				// ikke lengre er det samme.
				iterator = sortedList.listIterator(start);
				while (iterator.hasNext()) {
					Student temp = (Student) iterator.next();
					if (first != temp.geteNavn().charAt(FØRSTE)) {
						break;
					} else if (temp.geteNavn().contains(input)) {
						studentene.add(temp);
					}
				}
			}// hvis arrayen er tom, eller ikke befolket med personer med gitt
				// bokstav som start.
			catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}else if(qualifier == FORNAVN){
			try {
				// legger til alle studentene fra searchStart til første bokstav
				// ikke lengre er det samme.
				iterator = sortedList.listIterator(start);
				while (iterator.hasNext()) {
					Student temp = (Student) iterator.next();
					if (first != temp.getfNavn().charAt(FØRSTE)) {
						break;
					} else if (temp.getfNavn().contains(input)) {
						studentene.add(temp);
					}
				}
			}// hvis arrayen er tom, eller ikke befolket med personer med gitt
				// bokstav som start.
			catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
		return studentene;
	}
	private ArrayList<Student> samler(ArrayList<Student> fornavn, ArrayList<Student> etternavn){
		ArrayList<Student> studentene = fornavn;
		try{
		for(Student s : etternavn){
			if(!studentene.contains(s))
				studentene.add(s);
		}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return studentene;
	}
	
	
	
//	//delmetode for å gå gjennom registeret i studentlista.
//	private ArrayList<Student> findByFornavn(String fnavn){
//		ArrayList<Student> studentene = new ArrayList<>();
//		
//		
//		for(Student s : register){
//			if(s.getfNavn().equalsIgnoreCase(fnavn)){
//				studentene.add(s);
//			}
//		}		
//		return studentene;
//	}
//	//delmetode for å gå gjennom registeret i studentlista.
//	private ArrayList<Student> findByEtternavn(String enavn){
//		ArrayList<Student> studentene = new ArrayList<>();
//		
//		for(Student s : register){
//			if(s.geteNavn().equalsIgnoreCase(enavn)){
//				studentene.add(s);
//			}
//		}		
//		return studentene;
//	}
	
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
	
	public ArrayList<Student> findStudentByFag(Fag fag) {
		ArrayList<Student> studentene = new ArrayList<>();
		for(Student s : register){
			// sjekker hver students arbeidskrav for fagkoden, og ser om de har
			// faget.
			if (s.harFaget(fag))
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
		ArrayList<Student> checkStudenter = sp.getStudenter();
		ArrayList<Student> studentene = new ArrayList<>();

		for (Student s : checkStudenter) {
			if (dato.equals(s.getStart()))
				studentene.add(s);
		}

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
