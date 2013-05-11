package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import no.HiOAProsjektV2013.Interface.TestWindow;

//	Klassen fungerer som en samling av alle datastrukturer for skolesystemet
//	Dette for å unngå clutter i hovedklassen/vindusklassen som skal lage og holde datastrukturen
public class Skole implements Serializable{

	private static final long serialVersionUID = 1000L;
	private StudieprogramListe studieprogrammene = new StudieprogramListe();
	private LaererListe lærerne = new LaererListe();
	private FagListe fagene = new FagListe();
	private StudentListe studentene = new StudentListe();
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
//	//prøver å legge til en eksamen til gitt fag basert på input. Kan feile.
//	public void addEksamenToFag(Fag fag, String dato){
//		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for startdato
//		try {
//			Date date = (Date) formatter.parse(dato);
//			fag.addEksamen(date);
//		} catch (NumberFormatException nfe){
//			System.out.println("NumberformatException i addEksamenToFag");
//		} catch (ParseException pe) {
//			System.out.println("ParseException i addEksamenToFag");
//		}
//		
//	}
	
	//****************SØØØØØØØØØØK SØKEMETODER SØØØØØØØØØØK****************//
	
	public ArrayList<?> søk(String input, int qualifier){ //Sjekker inputen i søkefeltet og utfører relevante søk
		
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
	public ArrayList<Student> findStudentMedFag(Fag fag){
		ArrayList<Student> studentene = fag.getStudenter();
		return studentene;
	}
	
	public ArrayList<Student> findStudentMedFagByÅr(String input, int år){
		ArrayList<Student> studenter = new ArrayList<>();
		
		for(Student s : findStudentMedFag(input)){
			if(s.getStart().get(Calendar.YEAR) == år){
				studenter.add(s);
			}
		}
		return studenter;
	}
	//finner fagene som denne læreren har hovedansvar for.
	public ArrayList<Fag> fagLedetAv(Laerer lærer){
		return getLærerne().findFagLedetAv(lærer);
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
	//finner studenter som gikk på skolen i perioden brukeren søker på.
//	public ArrayList<Student> findStudentByPeriode(String start, String slutt){
//		int startÅr = Integer.parseInt(start);
//		int sluttÅr = Integer.parseInt(slutt);
//		ArrayList<Student> studentene = getStudentene().findStudentBySlutt(sluttÅr);
//		return studentene;
//	}
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
	
	//finner studentene ved studieprogram
	public ArrayList<Student> findStudentsByStudieprogram(String navn){
		Studieprogram sp = studieprogrammene.findEnByNavn(navn);
		return findStudentsByStudieprogram(sp);
	}
	public ArrayList<Student> findStudentsByStudieprogram(Studieprogram sp){
		ArrayList<Student> studentene = getStudentene().findStudentByStudieprogram(sp);
		return studentene;
	}
	//finner studentene ved studieprogrammet som startet i år X
	public ArrayList<Student> findStudentByStudieprogramByStart(Studieprogram sp, int start){
		ArrayList<Student> studentene = new ArrayList<>();
		
		for(Student s : getStudentene().findStudentByStudieprogram(sp)){
			if(s.getStart().get(Calendar.YEAR) == start){
				studentene.add(s);
			}
		}
		return studentene;
	}
	//interfacemetoder for å finne strykprosent
	public double findStrykProsent(Fag fag, int år){
		try{
			double strykprosent = fag.findStrykProsentByÅr(år);
			return strykprosent;
		}
		catch(NullPointerException npe){
			return 0;
		}
	}
	public double findStrykProsent(Fag fag, Eksamen e){
		double strykprosent = fag.findStrykProsentEksamen(e);
		return strykprosent;
	}
	//interfacemetoder for å finne karakterfordeling
	public int[] findKarakterDistribusjon(Fag fag, int år){
		int[] karakterene = fag.findKarakterDistribusjon(år);
		return karakterene;
	}
	public int[] findKarakterDistribusjon(Fag fag, Eksamen e){
		int[] karakterene = fag.findKarakterDistribusjon(e);
		return karakterene;
	}
	
	//finner studenter med beståtte arbeidskrav i gitt fag. (som ikke har sluttet)
	public ArrayList<Student> findKravBeståtteStudenter(Fag fag){
		ArrayList<Student> studentene = new ArrayList<>();
		
		for(Student s : fag.getStudenter()){
			if(!s.isAvsluttet()){
				if(s.innfriddKrav(fag))
					studentene.add(s);
			}
		}		
		return studentene;
	}
	
	//private søkemetoder for vanlige søk i hovedmeny
	private ArrayList<Fag> fagSøk(String input){
		if(input.matches(TestWindow.fagkodeRegex)){
			return fagkodeSøk(input);
		}
		ArrayList<Fag> fagene = new ArrayList<>();
		for(Fag f : getFagene().findByNavn(input)){
			fagene.add(f);
		}
		return fagene;
	}
	private ArrayList<Student> studentSøk(String input){
		if(input.matches(TestWindow.studentNrRegex))
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
