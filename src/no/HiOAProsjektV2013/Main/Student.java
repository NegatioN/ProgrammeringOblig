package no.HiOAProsjektV2013.Main;

import java.util.LinkedList;
import java.util.List;

public class Student extends Person{
	
	private String adresse, studentnummer;
	private int startÅr, sluttÅr;
	private List<Arbeidskrav> fagListe = new LinkedList<>();
	private boolean avsluttet = false;
	

	public Student(String navn, String epost, int tlf, String adresse, String studentnummer, int startÅr) {
		super(navn, epost, tlf);
		this.adresse = adresse;
		this.studentnummer = studentnummer;
		this.startÅr = startÅr;
		//sluttÅr settes lik -1 til studenten avslutter
		sluttÅr = -1;
	}

	//Listen med arbeidskrav representerer alle fagene studenten tar
	//finner fag studenten har ved å liste opp .getFagkode() fra kravene.
	public void settInn(Fag fag){
		fagListe.add(fag.getKrav());
	}
	
	public boolean innfriddKrav(Fag fag){
		for(int i = 0; i < fagListe.size();i++){
			if(fag.getFagkode().equals(fagListe.get(i).getFagkode())){
				return fagListe.get(i).kravInnfridd();
			}
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
	
	public String showFag(){
		String stringen = new String();
		
		for(int i = 0;i < fagListe.size()-1;i++){
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

}
