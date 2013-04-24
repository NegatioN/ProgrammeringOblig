package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Student extends Person implements Serializable{
	
	private static final long serialVersionUID = 1021L;
	private String adresse;
	private int studentnummer;
	private Date start, slutt;
	//Listen med arbeidskrav representerer alle fagene studenten tar
	//finner fag studenten har ved å liste opp .getFagkode() fra kravene.
	private List<Arbeidskrav> kravListe = new LinkedList<>();
	private List<EksamensDeltaker> eksamener = new LinkedList<>();
	private List<Fag> fagListe = new LinkedList<>();
	private boolean avsluttet = false;
	private Studieprogram sp = null;
	

	public Student(String navn, String epost, int tlf, String adresse, int studentnummer, Date start) {
		super(navn, epost, tlf);
		this.adresse = adresse;
		this.studentnummer = studentnummer;
		this.start = start;
		//sluttÅr settes lik -1 til studenten avslutter
		slutt = null;
	}

	public void setStudieprogram(Studieprogram studieprogram){
		sp = studieprogram;
	}
	public Studieprogram getStudieprogram(){
		return sp;
	}
	
	public void setEksamen(EksamensDeltaker ed){
		eksamener.add(ed);
	}
	
	
	//har studenten faget vi leter etter?
	//Sjekker gjennom arbeidskravene etter fagkode.
	public boolean harFaget(String fagkode){
		
		for(Arbeidskrav krav : kravListe){
			if(fagkode.equalsIgnoreCase(krav.getFagkode()))
				return true;
		}
		
		return false;
	}
	private boolean harFaget(Fag fag){
		for(Fag checkFag : fagListe){
			if(fag.equals(checkFag))
				return true;
		}
		return false;
	}
	//legger til krav i kravlista til studenten, men kun hvis det ikke finnes.
	public void addFag(Fag fag){
		if(!harFaget(fag)){
			kravListe.add(fag.getKrav());
			fagListe.add(fag);
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


	public Date getSlutt() {
		return slutt;
	}


	public void setSlutt(Date slutt) {
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


	public Date getStart() {
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
					"\nStartdato: " + new SimpleDateFormat("dd. MMM yyyy").format(start) +
					"\nFag: ";
	
		//må legge inn arbeidskrav i faget før vi kan reference.
		/*if(!fagListe.isEmpty()){
		for(Fag fag : fagListe){
			stringen += fag.getFagkode() + "\n";
		}
		}*/
		
		return stringen;
	}
	
	public Arbeidskrav[] getKravene(){
		return (Arbeidskrav[]) kravListe.toArray();
	}
	public Fag[] getFagene(){
		return (Fag[])fagListe.toArray();
	}


}
