package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Studieprogram implements Serializable{

	private static final long serialVersionUID = 1060L;
	private String navn;
	private List<Fag> fagIProgrammet = new LinkedList<>();

	public Studieprogram(String navn) {
		this.navn = navn;
	}

	public void addFag(Fag fag) {
		fagIProgrammet.add(fag);
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

	// toString returnerer en liste av alle fag i studieprogrammet.
	// kan være nødvendig å flytte en sånn metode til window slik at hver
	// getFagkode blir en egen knapp.
	public String toString() {
		String stringen = "Navn: " + navn +  "\nFag:";
		
		for (Fag f : fagIProgrammet) {
			stringen += "\n" + f.getFagkode();
		}
		
		return stringen;
	}
}
