package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/*
 * Klassen representerer en student og er det største objektet i programmet.
 * Et objekt er åpenbart en student på skolen, og har mange fag, eksamener og fag-krav og et studieprogram.
 */
public class Student extends Person implements Serializable{
	
	private static final long serialVersionUID = 1021L;
	private String adresse;
	private int studentnummer;
	private GregorianCalendar start, slutt;
	private LinkedList<EksamensDeltaker> eksamener = new LinkedList<>();
	//faglista er kun pointers
	private List<Fag> fagListe = new LinkedList<>();
	//kravlisten er de individuelle objektene som opprettes per student.
	private List<Arbeidskrav> kravListe = new LinkedList<>();
	private boolean avsluttet = false;
	private Studieprogram sp = null;
	

	public Student(String navn, String epost, int tlf, String adresse, int studentnummer, GregorianCalendar start) {
		super(navn, epost, tlf);
		this.adresse = adresse;
		this.studentnummer = studentnummer;
		this.start = start;
		//sluttÅr settes lik null til studenten avslutter
		slutt = null;
	}

	public void setStudieprogram(Studieprogram studieprogram){
		//try-blokken gjør at en student kun kan være registrert på 1 studieprogram. Kan hende det er relevant med flere.
		try{
			sp.removeStudent(this);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		sp = studieprogram;
		sp.addStudent(this);
	}
	public Studieprogram getStudieprogram(){
		return sp;
	}
	
	public void setEksamen(EksamensDeltaker ed){
		eksamener.add(ed);
	}
	
	//har studenten overskredet 3 forsøk for denne eksamenen?
	public boolean maksForsøkOverskredet(Fag fag){
		int counter = 0;
		for(EksamensDeltaker deltaker: eksamener){
			if(fag.equals(deltaker.getFag()))
				counter++;
		}
		if(counter >= 3){
			return true;
		}
		
		return false;
	}
	
	
	//har studenten faget vi leter etter?
	//Sjekker gjennom arbeidskravene etter fagkode.
	public boolean harFaget(String fagkode){
		
		for(Fag f : fagListe){
			if(f.getFagkode() == fagkode)
				return true;
		}
		
		return false;
	}
	
	public boolean harFaget(Fag fag){
		for(Fag f : fagListe){
			if(fag.equals(f))
				return true;
		}
		return false;
	}
	//legger til krav i kravlista til studenten, men kun hvis det ikke finnes.
	public void addFag(Fag fag){
		if(!harFaget(fag)){
			kravListe.add(fag.makeKrav());
			fagListe.add(fag);
			fag.addStudent(this);
		}
		else{
			return;
		}
	}
	//privat metode kun for bruk i removeFag.
	//studenten vil ikke ha samme pointer til et arbeidskrav-objekt som faget, så det må søkes på intærne krav.
	private Arbeidskrav harKravet(Fag fag){
		String fagkode = fag.getFagkode();
		for(Arbeidskrav krav : kravListe){
			if(fagkode.equalsIgnoreCase(krav.getFagkode()))
				return krav;
		}
		return null;
	}
	public void removeFag(Fag fag){
		Arbeidskrav krav = harKravet(fag);
		if(krav != null){
			kravListe.remove(krav);
			fagListe.remove(fag);
			fag.removeStudent(this);
		}
	}
	
	public boolean innfriddKrav(Fag fag){
		//Har personen faget? For debug. Dobler søkefarten, men kanskje ikke så relevant mtp 
		//at man har maks 20-25 fag.
		boolean harFaget = harFaget(fag);
		if(!harFaget)
			return false;
		String fagkode = fag.getFagkode();
		
		for(Arbeidskrav krav : kravListe){
			if(fagkode.equalsIgnoreCase(krav.getFagkode()))
				return krav.kravInnfridd();
		}
		return false;
	}
	
	public String visAlleFag(){
		String stringen = new String();
		for(int i = 0; i< kravListe.size();i++){
			stringen += kravListe.get(i).getFagkode() + "\n";
		}
		stringen += "############";
		return stringen;
	}
	

	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public GregorianCalendar getSlutt() {
		return slutt;
	}


	public void setSlutt(GregorianCalendar slutt) {
		avsluttet = true;
		this.slutt = slutt;
	}
	//har studenten avsluttet studiet? sant hvis slutt ikke lik null
	public boolean isAvsluttet(){
	return avsluttet;
	}

	//returnerer "sXXXXXX", men studentnummeret er en int så binærsøk går fortere.
	public String printStudentnummer() {
		return "s" + studentnummer;
	}
	public int getStudentnummer(){
		return studentnummer;
	}


	public GregorianCalendar getStart() {
		return start;
	}
	public String toString(){	
		return "S" + studentnummer + " - " + getfNavn() + " " + geteNavn();
	}
	
	public String fullString(){	
		String stringen = new String();
		
		stringen = "StudentNr: " + studentnummer + 
					"\nNavn: " + getfNavn() + " " + geteNavn() + 
					"\nE-post: " + getEpost() + 
					"\nTlf: " + getTelefonNr() + 
					"\nAdresse: " + adresse + 
					"\nStartdato: " + new SimpleDateFormat("dd. MMM yyyy").format(start.getTime()) +
					"\nFag: ";
	
		//må legge inn arbeidskrav i faget før vi kan reference.
		//if(!fagListe.isEmpty()){
		for(Fag fag : fagListe){
			stringen += fag.getFagkode() + "\n";
		}
		
		return stringen;
	}
	
	public List<Arbeidskrav> getKravene(){
		return kravListe;
	}
	
	public Arbeidskrav getFagKrav(Fag fag){
		for( Arbeidskrav ak : kravListe){
			if(ak.getFagkode() == fag.getFagkode())
				return ak;
		}
		return null;
	}
	
	public List<Fag> getfagListe(){
		return fagListe;
	}
	public LinkedList<EksamensDeltaker> getEksamener(){
		return eksamener;
	}

}
