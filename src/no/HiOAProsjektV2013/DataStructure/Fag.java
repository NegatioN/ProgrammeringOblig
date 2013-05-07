package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		this.lærer = lærer;
		this.studiepoeng = studiepoeng;
		krav = new Arbeidskrav(this);
		
		studenter = new ArrayList<Student>();
	}
	
	public void addStudent(Student s){
		studenter.add(s);
	}
	public ArrayList<Student> getStudenter(){
		return studenter;
	}

	public void addEksamen(Eksamen e) {
		eksamener.add(e);
	}

	public void addEksamen(Date dato) {
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
		this.lærer = lærer;
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
		Eksamen e = findEksamenByDate(år);
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
	public double findStrykProsentByDate(int år){
		Eksamen e = findEksamenByDate(år);
		
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
			if(karakter == 'f' || karakter == 'F')
				antallStryk++;
		}
		double strykProsent = (antallStryk/antallStudenter)*100;
		
		return strykProsent;
	}

	private Eksamen findEksamenByDate(int år) {
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
