package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/*
 * Klassen er et fag på skolen. Faget inneholder eksamener, krav og faglærer.
 * Inneholder også i tillegg informasjon om faget.
 */
public class Fag implements Serializable{

	
	private static final long serialVersionUID = 1031L;
	private String navn, fagkode, beskrivelse, vurderingsform;
	private Laerer lærer;
	private Arbeidskrav krav;
	private int studiepoeng;
	private List<Eksamen> eksamener = new LinkedList<>();
	private final int AKARAKTER = 0, BKARAKTER = 1, CKARAKTER = 2,
			DKARAKTER = 3, EKARAKTER = 4, FKARAKTER = 5;
	private ArrayList<Student> studenter;

	public Fag(String navn, String fagkode, String beskrivelse,
			String vurderingsform, int studiepoeng, Laerer lærer) {
		this.beskrivelse = beskrivelse;
		this.navn = navn;
		// alltid uppercase fagkode
		this.fagkode = fagkode.toUpperCase();
		this.vurderingsform = vurderingsform;
		setLærer(lærer);
		this.studiepoeng = studiepoeng;
		krav = new Arbeidskrav(this);
		
		studenter = new ArrayList<Student>();
	}
	
	public void addStudent(Student s){
		studenter.add(s);
	}
	public void removeStudent(Student s){
		studenter.remove(s);
	}
	public ArrayList<Student> getStudenter(){
		System.out.println(studenter.toString());
		return studenter;
	}

	public void addEksamen(Eksamen e) {
		eksamener.add(e);
	}

	public void addEksamen(GregorianCalendar dato) {
		Eksamen e = new Eksamen(dato, this);
		addEksamen(e);
	}

	public void removeEksamen(Eksamen e) {
		eksamener.remove(e);
	}

	// skal returnere startobjekt-1 altså siste, men usikker på om det funker,
	// trenger bugtest.
	public Eksamen getRecentEksamen() {	
		return eksamener.get(eksamener.size()-1);
	}
	
	public List<Eksamen> getEksamener(){
		return eksamener;
	}

	public Laerer getLærer() {
		return lærer;
	}

	public void setLærer(Laerer lærer) {
		if(this.lærer != null){
			this.lærer.removeFag(this);
		}
		this.lærer = lærer;
		this.lærer.addFag(this);
	}

	public String getNavn() {
		return navn;
	}

	public String getFagkode() {
		return fagkode;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public int getStudiepoeng() {
		return studiepoeng;
	}
	
	//kloner et krav og gir det til en student.
	public Arbeidskrav makeKrav() {
		Arbeidskrav kloneKrav = krav.clone(krav);
		return kloneKrav;
	}
	public Arbeidskrav getKrav(){
		return krav;
	}
	

	public String getVurderingsform() {
		return vurderingsform;
	}

	public void setVurderingsform(String vurderingsform) {
		this.vurderingsform = vurderingsform;
	}

	// Lager et krav for referanse-kravlista basert på en beskrivelse
	public void addKrav(String beskrivelse) {
		Krav kravet = new Krav(beskrivelse);
		krav.addKrav(kravet);
	}

	// returnerer en array av intverdier som representerer antall av hver
	// karakter fra A = array[0] til F = array[6]
	public int[] findKarakterDistribusjon(int år) {
		Eksamen e = findEksamenByÅr(år);
		int[] karakterene = findKarakterDistribusjon(e);
		
		return karakterene;
	}
	public int[] findKarakterDistribusjon(Eksamen e){
		int[] karakterene = new int[6];
		if (eksamener.contains(e)) {
			for (int i : karakterene) {
				i = 0;
			}
			if (e == null)
				return karakterene;
			List<EksamensDeltaker> deltakerne = e.getDeltakere();
			for (EksamensDeltaker ed : deltakerne) {
				// legger bare til en i arrayen som represeenterer antall av
				// hver
				// karakter.
				char karakter = ed.getKarakter();
				if (karakter == 'a' || karakter == 'A')
					karakterene[AKARAKTER] += 1;
				if (karakter == 'b' || karakter == 'B')
					karakterene[BKARAKTER] += 1;
				if (karakter == 'c' || karakter == 'C')
					karakterene[CKARAKTER] += 1;
				if (karakter == 'd' || karakter == 'D')
					karakterene[DKARAKTER] += 1;
				if (karakter == 'e' || karakter == 'E')
					karakterene[EKARAKTER] += 1;
				if (karakter == 'f' || karakter == 'F')
					karakterene[FKARAKTER] += 1;
			}
		}
		return karakterene;
	}
	//returnerer double-verdi med strykprosenten i eksamen på gitt dato i dette bestemte faget.
	public double findStrykProsentByÅr(int år){
		Eksamen e = findEksamenByÅr(år);
		
		double strykProsent = findStrykProsentEksamen(e);
		return strykProsent;
	}
	//strykprosent for en gitt eksamen
	public double findStrykProsentEksamen(Eksamen e){
		List<EksamensDeltaker> deltakere = e.getDeltakere();
		int antallStudenter = deltakere.size();
		int antallStryk = 0;
		for(EksamensDeltaker ed : deltakere){
			char karakter = ed.getKarakter();
			if(karakter == 'f' || karakter == 'F'){
				antallStryk++;
			}
		}
		double strykProsent = ((double)antallStryk/(double)antallStudenter)*100;
		
		return strykProsent;
	}
	public Eksamen findEksamenByDate(GregorianCalendar calendar){
		for(Eksamen e : eksamener){
			if(calendar.equals(e.getKalender())){
				return e;
			}
		}
		return null;
	}

	private Eksamen findEksamenByÅr(int år) {
		for (Eksamen e : eksamener) {
			if (e.getKalender().get(Calendar.YEAR) == år)
				return e;
		}
		return null;
	}
	
	public String toString(){
		String stringen = new String();
		stringen = 	navn +" - "+  fagkode;
		return stringen;
	}
	
	public String fullString(){
		String stringen = new String();
		stringen = 	"Fagkode: " + fagkode + 
					"\nNavn: " + navn + 
					"\nBeskrivelse: " + beskrivelse + 
					"\nVurderingsform: " + vurderingsform + 
					"\nStudiepoeng: " + studiepoeng +
					"\nLærer: " + lærer.geteNavn() + 
					"\nArbeidskrav: " + krav.toString() +
					"\nEksamener: ";
		for(Eksamen e: eksamener){
			stringen += new SimpleDateFormat("dd. MMM yyyy").format(e.getDato()) + " ";
		}
		
		return stringen;
	}
}
