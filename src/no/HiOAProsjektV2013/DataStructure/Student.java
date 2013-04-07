package no.HiOAProsjektV2013.DataStructure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Student extends Person{
	
	private String adresse, studentnummer;
	private int startÅr, sluttÅr;
	private List<Arbeidskrav> fagListe = new LinkedList<>();
	private Iterator<Arbeidskrav> iterator;
	private boolean avsluttet = false;
	private Studieprogram sp = null;
	

	public Student(String navn, String epost, int tlf, String adresse, String studentnummer, int startÅr) {
		super(navn, epost, tlf);
		this.adresse = adresse;
		this.studentnummer = studentnummer;
		this.startÅr = startÅr;
		//sluttÅr settes lik -1 til studenten avslutter
		sluttÅr = -1;
	}

	public void setStudieprogram(Studieprogram studieprogram){
		sp = studieprogram;
	}
	public Studieprogram getStudieprogram(){
		return sp;
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
	
	
	//Listen med arbeidskrav representerer alle fagene studenten tar
	//finner fag studenten har ved å liste opp .getFagkode() fra kravene.
	public void settInn(Fag fag){
		fagListe.add(fag.getKrav());
	}
	
	public boolean innfriddKrav(Fag fag){
		//dummy
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


	public int getSluttÅr() {
		return sluttÅr;
	}


	public void setSluttÅr(int sluttÅr) {
		this.sluttÅr = sluttÅr;
	}
	//har studenten avsluttet studiet? sant hvis sluttÅr ikke lik -1
	public boolean isAvsluttet(){
	if(sluttÅr != -1) avsluttet = true;
	return avsluttet;
	}


	public String getStudentnummer() {
		return studentnummer;
	}


	public int getStartÅr() {
		return startÅr;
	}
	public String toString(){
		String stringen = new String();
		
		return stringen;
	}
	private void refreshIterator(){
		iterator = fagListe.iterator();
	}

}
