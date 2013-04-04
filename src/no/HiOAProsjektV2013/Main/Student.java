package no.HiOAProsjektV2013.Main;

import java.util.LinkedList;
import java.util.List;

public class Student extends Person{
	
	private String adresse, studentnummer;
	private int start�r, slutt�r;
	private List<Arbeidskrav> fagListe = new LinkedList<>();
	private boolean avsluttet = false;
	

	public Student(String navn, String epost, int tlf, String adresse, String studentnummer, int start�r) {
		super(navn, epost, tlf);
		this.adresse = adresse;
		this.studentnummer = studentnummer;
		this.start�r = start�r;
		//slutt�r settes lik -1 til studenten avslutter
		slutt�r = -1;
	}

	//Listen med arbeidskrav representerer alle fagene studenten tar
	//finner fag studenten har ved � liste opp .getFagkode() fra kravene.
	public void settInn(Fag fag){
		fagListe.add(fag.getKrav());
	}
	
	public String showFag(){
		String stringen = new String();
		
		for(int i = 0;i < fagListe.size();i++){
			stringen += fagListe.get(i).getFagkode() + "\n";
		}
		
		return stringen;
	}

	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public int getSlutt�r() {
		return slutt�r;
	}


	public void setSlutt�r(int slutt�r) {
		this.slutt�r = slutt�r;
	}
	//har studenten avsluttet studiet? sant hvis slutt�r ikke lik -1
	public boolean isAvsluttet(){
	if(slutt�r != -1) avsluttet = true;
	return avsluttet;
	}


	public String getStudentnummer() {
		return studentnummer;
	}


	public int getStart�r() {
		return start�r;
	}

}
