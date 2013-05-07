package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import no.HiOAProsjektV2013.Interface.TestWindow;

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
	
	public ArrayList<?> søk(String input, int qualifier){ //Sjekker inputen i søkefeltet og utfører relevante søk
//		System.out.println(studs.toString());
		
		if(qualifier == TestWindow.FAG){
			ArrayList<Fag> fagene = fagSøk(input);
			return fagene;
		}else if(qualifier == TestWindow.STUDENT){
			ArrayList<Student> studentene = studentSøk(input);
			return studentene;
		}else if(qualifier == TestWindow.LÆRER){
			ArrayList<Laerer> lærerne = lærerSøk(input);
			return lærerne;
		}else if(qualifier == TestWindow.STUDIEPROGRAM){
			ArrayList<Studieprogram> studiene = studieSøk(input);
			return studiene;
		}
		
		
		if(input.matches(arRegex)){ //Sjekker om det er søkt på år
			//split input og sjekk neste paramenter for hvordan søket skal håndteres.
		}
		return null;
	}
	//finner studenter med faget bruker søker på
	public ArrayList<Student> findStudentMedFag(String input){		
		Fag faget = fagSøk(input).get(0);
		
		ArrayList<Student> studentene = faget.getStudenter();
		
		return studentene;
	}
	//finner studenter med startåret brukeren søker på.
	public ArrayList<Student> findStudentByStart(String input){
		int startÅr = Integer.parseInt(input);
		ArrayList<Student> studentene = getStudentene().findStudentByStart(startÅr);
		
		return studentene;
	}
	//finner studenter med sluttåret brukeren søker på.
	public ArrayList<Student> findStudentBySlutt(String input){
		int sluttÅr = Integer.parseInt(input);
		ArrayList<Student> studentene = getStudentene().findStudentBySlutt(sluttÅr);
		return studentene;
	}
	//returnerer studiepoengene mellom år X og år Y for studenten.
	public int findStudiepoengForStudIPeriode(Student s, String start, String slutt){
		int startÅr = Integer.parseInt(start);
		int sluttÅr = Integer.parseInt(slutt);
		int studiepoeng = 0;
		
		for(EksamensDeltaker ed : s.getEksamener()){
			int eksamensÅr = ed.getDato().get(Calendar.YEAR);
			if(startÅr <= eksamensÅr && eksamensÅr <= sluttÅr){
				char karakter = ed.getKarakter();
				if(karakter != '\0' && karakter != 'F'){
					studiepoeng += ed.getFag().getStudiepoeng();
				}
			}
		}
		
		return studiepoeng;
	}
	//remade metoder som skal brukes om qualifier-int slår til, slik at ikke ALLE søk skjer hver gang.
	private ArrayList<Fag> fagSøk(String input){
		if(input.matches(fagkodeRegex)){
			return fagkodeSøk(input);
		}
		ArrayList<Fag> fagene = new ArrayList<>();
		for(Fag f : getFagene().findByNavn(input)){
			fagene.add(f);
		}
		return fagene;
	}
	private ArrayList<Student> studentSøk(String input){
		if(input.matches(studentNrRegex))
			return studentNrSøk(input);
		ArrayList<Student> studentene = getStudentene().findByNavn(input);		
		return studentene;
	}
	private ArrayList<Laerer> lærerSøk(String input){
		ArrayList<Laerer> lærerne = getLærerne().findByNavn(input);
		return lærerne;
	}
	private ArrayList<Studieprogram> studieSøk(String input){
		ArrayList<Studieprogram> studiene = getStudieprogrammene().findByNavn(input);
		return studiene;
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
