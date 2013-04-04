package no.HiOAProsjektV2013.Main;

import java.util.LinkedList;
import java.util.List;

public class Studieprogram {

	private String navn;
	private List<Fag> fagIProgrammet = new LinkedList<>();
	
	public Studieprogram(String navn){
		this.navn = navn;
	}
	
	public void addFag(Fag fag){
		fagIProgrammet.add(fag);
	}
	
	public String getNavn(){
		return navn;
	}
	
	//toString returnerer en liste av alle fag i studieprogrammet.
	//kan v�re n�dvendig � flytte en s�nn metode til window slik at hver getFagkode blir en egen knapp.
	public String toString(){
		String stringen = "";
		for(int i = 0; i < fagIProgrammet.size();i++){
			stringen += fagIProgrammet.get(i).getFagkode() + "\n";
		}
		
		return stringen;
	}
	
	
}
