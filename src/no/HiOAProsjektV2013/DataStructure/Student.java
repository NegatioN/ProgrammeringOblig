package no.HiOAProsjektV2013.DataStructure;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Student extends Person{
	
	private String adresse;
	private int studentnummer;
	private Date start, slutt;
	//Listen med arbeidskrav representerer alle fagene studenten tar
	//finner fag studenten har ved å liste opp .getFagkode() fra kravene.
	private List<Arbeidskrav> fagListe = new LinkedList<>();
	private List<EksamensDeltaker> eksamener = new LinkedList<>();
	private Iterator<Arbeidskrav> iterator;
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
		refreshIterator();
		
		while(iterator.hasNext()){
			Arbeidskrav krav = iterator.next();
			if(fagkode.equalsIgnoreCase(krav.getFagkode())){
				return true;
			}
		}
		
		return false;
	}
	private boolean harFaget(Fag fag){
		Arbeidskrav krav = null;
		String fagkode = fag.getFagkode();
		refreshIterator();
		
		while(iterator.hasNext()){
			krav = iterator.next();
			if(fagkode.equalsIgnoreCase(krav.getFagkode())){
				return true;
			}
		}
		return false;
	}
	
	public void addFag(Fag fag){
		fagListe.add(fag.getKrav());
	}
	
	public boolean innfriddKrav(Fag fag){
		Arbeidskrav kravene = null;
		//Har personen faget? For debug. Dobler søkefarten, men kanskje ikke så relevant mtp 
		//at man har maks 20-25 fag.
		boolean harFaget = harFaget(fag);
		if(!harFaget)
			return false;
		refreshIterator();
		String fagkode = fag.getFagkode();
		
		while(iterator.hasNext()){
			kravene = iterator.next();
			if(fagkode.equalsIgnoreCase(kravene.getFagkode()))
					return kravene.kravInnfridd();
		}
		return false;
	}
	
	public String visAlleFag(){
		String stringen = new String();
		for(int i = 0; i< fagListe.size();i++){
			stringen += fagListe.get(i).getFagkode() + "\n";
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


	public void setSluttÅr(Date slutt) {
		this.slutt = slutt;
	}
	//har studenten avsluttet studiet? sant hvis slutt ikke lik null
	public boolean isAvsluttet(){
	if(slutt != null) avsluttet = true;
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
		String stringen = new String();
		stringen = "StudentNr: " + studentnummer + 
					"\nNavn: " + getfNavn() + " " + geteNavn() + 
					"\nE-post: " + getEpost() + 
					"\nTlf: " + getTelefonNr() + 
					"\nAdresse: " + adresse + 
					"\nStartdato: " + start;
		
		return stringen;
	}
	private void refreshIterator(){
		iterator = fagListe.iterator();
	}

}
