package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Fag implements Serializable{

	
	private static final long serialVersionUID = 1031L;
	private String navn, fagkode, beskrivelse, vurderingsform;
	private Laerer lærer;
	private Arbeidskrav krav;
	private int studiepoeng;
	private List<Eksamen> eksamener = new LinkedList<>();
	private final int AKARAKTER = 0, BKARAKTER = 1, CKARAKTER = 2,
			DKARAKTER = 3, EKARAKTER = 4, FKARAKTER = 5;

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
	}

	public void addEksamen(Eksamen e) {
		eksamener.add(e);
	}

	public void addEksamen(Date dato) {
		Eksamen e = new Eksamen(dato);
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

	public Arbeidskrav getKrav() {
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
	public int[] findKarakterDistribusjon(Date dato) {
		int[] karakterene = new int[6];
		Eksamen e = findEksamenByDate(dato);
		if (e == null)
			return karakterene;
		// gir alle elementene i arrayen en verdi sånn at vi ser 0-verdier
		for (int i : karakterene) {
			i = 0;
		}
		List<EksamensDeltaker> deltakerne = e.getDeltakere();
		for (EksamensDeltaker ed : deltakerne) {
			// legger bare til en i arrayen som represeenterer antall av hver
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

		return karakterene;
	}
	//returnerer double-verdi med strykprosenten i eksamen på gitt dato i dette bestemte faget.
	public double findStrykProsentByDate(Date dato){
		Eksamen e = findEksamenByDate(dato);
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

	private Eksamen findEksamenByDate(Date dato) {
		for (Eksamen e : eksamener) {
			if (e.getDato().equals(dato))
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
					"\nLærer: " + lærer.geteNavn() + "\nEksamener: " ;
					//"\nArbeidskrav: " + krav.toString() +
		for(Eksamen e: eksamener){
			stringen += new SimpleDateFormat("dd. MMM yyyy").format(e.getDato()) + " ";
		}
		
		return stringen;
	}
}
