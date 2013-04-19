package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//	Klassen fungerer som en samling av alle datastrukturer for skolesystemet
//	Dette for å unngå clutter i hovedklassen/vindusklassen som skal lage og holde datastrukturen
public class Skole implements Serializable{

	private static final long serialVersionUID = 1000L;
	private StudieprogramListe studieprogrammene = new StudieprogramListe();
	private LaererListe lærerne = new LaererListe();
	private FagListe fagene = new FagListe();
	private StudentListe studentene = new StudentListe();
	private String navnRegex = "\\D+";
	private String fagkodeRegex = "\\w{4}\\d{4}";
	private String studentNrRegex = "s\\d{6}";
	private String arRegex = "\\d{4}";
	
	public Skole() {
		// Constructor
	}

	public StudieprogramListe getStudieprogrammene() {
		return studieprogrammene;
	}

	public LaererListe getLærerne() {
		return lærerne;
	}

	public FagListe getFagene() {
		return fagene;
	}

	public StudentListe getStudentene() {
		return studentene;
	}
	
	//Legger til et gitt fag i et gitt studieprogram
	public void addFagToStudProg(String navn, String fagkode){
		Studieprogram sp = studieprogrammene.findEnByNavn(navn);
		Fag f = sp.finnFag(getFagene().finnFagByFagkode(fagkode));
		//hvis faget ikke finnes i studieprogrammet fra før, altså søket gir null.
		if( f == null)
			sp.addFag(getFagene().finnFagByFagkode(fagkode));

	}
	//legger til kravene fra faget i studentobjektet. Skal brukes via internal window.
	public void addFagToStud(Student s, String fagkode){
		Fag f = fagene.finnFagByFagkode(fagkode);
		if(f != null)
			s.addFag(f);
	}
	//fjernet et arbeidskrav/fag fra en studenten gitt at han har det
	public void removeFagFromStud(Student s, String fagkode){
		Fag f = fagene.finnFagByFagkode(fagkode);
		if(f != null)
		s.removeFag(f);
	}
	//legger til krav til gitt fag
	public void addKravToFag(String fagkode, String beskrivelse){
		Fag f = fagene.finnFagByFagkode(fagkode);
		if(f != null)
			f.addKrav(beskrivelse);
	}
	public void addKravToFag(Fag f, String beskrivelse){
		if(f != null)
			f.addKrav(beskrivelse);
	}
	//prøver å legge til en eksamen til gitt fag basert på input. Kan feile.
	public void addEksamenToFag(Fag fag, String dato){
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for startdato
		try {
			Date date = (Date) formatter.parse(dato);
			fag.addEksamen(date);
		} catch (NumberFormatException nfe){
			System.out.println("NumberformatException i addEksamenToFag");
		} catch (ParseException pe) {
			System.out.println("ParseException i addEksamenToFag");
		}
		
	}
	
	//****************SØØØØØØØØØØK SØKEMETODER SØØØØØØØØØØK****************//
	
	public ArrayList<?> søk(String input){ //Sjekker inputen i søkefeltet og utfører relevante søk
		
		if(input.matches(arRegex)){ //Sjekker om det er søkt på år
			//split input og sjekk neste paramenter for hvordan søket skal håndteres.
		}
		else if (input.matches(fagkodeRegex)){ //Sjekker om det er søkt på fagkode	
			return fagkodeSøk(input);
		}
		else if (input.matches(studentNrRegex)){ //Sjekker om det er søkt på studentNr
			return studentNrSøk(input);
		}
		else if (input.matches(navnRegex)){ //Sjekker om det er søkt på navn (student, lærer, fag og studieprogram)
			return navnSøk(input);
		}
		return null;
	}

	//tror vi må gjøre søkene separate om vi skal ha ut objektlister.
	// Virker som det kan være lurt å kun returnere søk basert på aktivert tab eller en eller annen restriction
	private ArrayList<?> navnSøk(String input){ //Utfører alle søk som tar imot navn, og returnerer en string (SKAL RETURNERE OBJEKT)
		ArrayList<Student> studenter = new ArrayList<>();
		ArrayList<Laerer> lærere = new ArrayList<>();
		ArrayList<Fag> fag = new ArrayList<>();
		ArrayList<Studieprogram> studieprog = new ArrayList<>();

		//Tekstsøk
		for(Student s : getStudentene().findByNavn(input)){
			studenter.add(s);
		}
		
		for(Laerer l : getLærerne().findByNavn(input)){
			lærere.add(l);
		}
		
		for(Fag f : getFagene().findByNavn(input)){
			fag.add(f);
		}
		
		for(Studieprogram sp : getStudieprogrammene().findByNavn(input)){
			studieprog.add(sp);
		}
		
		if(!studenter.isEmpty())
			return studenter;
		else if(!lærere.isEmpty())
			return lærere;
		else if(!fag.isEmpty())
			return fag;
		else if(!studieprog.isEmpty())
			return studieprog;
		else
			return null;

	}
	
	public String årSøk(){
		return null;
	}

	private ArrayList<Student> studentNrSøk(String input){
		ArrayList<Student> student = new ArrayList<>();
		student.add(studentene.findStudentByStudentNr(input));
		if(student.isEmpty())
			return null;
		return student;
	}
	
	private ArrayList<Fag> fagkodeSøk(String input){
		ArrayList<Fag> fag = new ArrayList<>();
		fag.add(getFagene().finnFagByFagkode(input));
		if(fag.isEmpty())
			return null;
		return fag;

	}

}
