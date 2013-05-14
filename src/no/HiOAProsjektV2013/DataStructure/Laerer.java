package no.HiOAProsjektV2013.DataStructure;
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 14.05.13
import java.io.Serializable;
import java.util.ArrayList;

/*
 * Klassen representerer en lærer ved skolen.
 * Inneholder personinfo fra Person-klassen og annen info som kontornummer.
 */
public class Laerer extends Person implements Serializable{
	
	private static final long serialVersionUID = 1011L;
	private String kontorNr;
	private ArrayList<Fag> fagene = new ArrayList<>();

	public Laerer(String navn, String epost, int tlf, String kontorNr) {
		super(navn, epost, tlf);
		this.kontorNr = kontorNr;
	}

	public String getKontor(){
		return kontorNr;
	}
	//metoder for å legge til/fjerne/vise fag i lista ledet av læreren.
	public void addFag(Fag fag){
		fagene.add(fag);
	}
	public void removeFag(Fag fag){
		fagene.remove(fag);
	}
	public ArrayList<Fag> getFagene(){
		return fagene;
	}
	//end 
	
	public void setKontor(String kontorNr){
		this.kontorNr = kontorNr;
	}
	//tostring for bruk i JList og JTable
	public String toString(){
		String stringen = new String();
		stringen = 	getfNavn() + " " + geteNavn() +" - " + kontorNr;
		return stringen;
	}
	//string for visning av info i displaypanel
	public String fullString(){
		String stringen = new String();
		stringen = 	"Navn: " + getfNavn() + " " + geteNavn() + 
					"\nE-post: " + getEpost() + 
					"\nTlf: " + getTelefonNr() + 
					"\nKontorNr: " + kontorNr;
		return stringen;
	}
}
