package no.HiOAProsjektV2013.DataStructure;
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 14.05.13
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;


/*
 * Klassen representerer et studieprogram, og inneholder en liste av fag som er assosiert med studieprogrammet.
 * Dette gjør det lettere å opprette studenter med fag på seg, enn å individuelt legge på fag.
 * Inneholder også en liste over studenter som nå går programmet.
 */
public class Studieprogram implements Serializable{

	private static final long serialVersionUID = 1060L;
	private String navn;
	private List<Fag> fagIProgrammet = new LinkedList<>();
	private ArrayList<Student> studenterIProgrammet = new ArrayList<>();

	public Studieprogram(String navn) {
		this.navn = navn;
	}
	
	public boolean harFaget(Fag fag){
		for(Fag checkFag : fagIProgrammet){
			if(fag.getFagkode().equals(checkFag.getFagkode()))
				return true;
		}
		return false;
	}
	
	public void addFag(Fag fag) {
		if(!harFaget(fag))
			fagIProgrammet.add(fag);
		else
			JOptionPane.showMessageDialog(null, this.getNavn() + " har faget " + fag.getFagkode() + " fra før.", "Feilmelding", JOptionPane.ERROR_MESSAGE);
	}

	public List<Fag> getFagene()
	{
		return fagIProgrammet;
	}
	
	public void fjernFag(Fag fag) {
		
		for (Fag f : fagIProgrammet) {
			if(f.equals(fag)) {
				fagIProgrammet.remove(fag);
			}
		}
	}
	//finner faget i studieprogrammet om det finnes.
	public Fag finnFag(Fag fag){
		for (Fag f : fagIProgrammet) {
			if(f.equals(fag)) {
				return fag;
			}
		}
		return null;
	}

	public String getNavn() {
		return navn;
	}
	
	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String toString() {
		String stringen = navn;
		
		return stringen;
	}
	
	public String fullString() {
		String stringen = "Navn: " + navn +  "\n\nFag:";
		if(!fagIProgrammet.isEmpty()){
			for (Fag f : fagIProgrammet) {
				stringen += "\n" + f.getFagkode();
			}
		}
		return stringen;
	}
	public void addStudent(Student s){
		studenterIProgrammet.add(s);
		for(Fag f: fagIProgrammet)
			s.addFag(f);
	}
	public void removeStudent(Student s){
		studenterIProgrammet.remove(s);
	}
	public ArrayList<Student> getStudenter(){
		return studenterIProgrammet;
	}
}
