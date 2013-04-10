package no.HiOAProsjektV2013.DataStructure;

import java.util.LinkedList;
import java.util.List;

public class Studieprogram {

	private String navn;
	private List<Fag> fagIProgrammet = new LinkedList<>();

	public Studieprogram(String navn) {
		this.navn = navn;
	}

	public void addFag(Fag fag) {
		fagIProgrammet.add(fag);
	}

	public void fjernFag(Fag fag) {
		int i = indeks(fag);
		try {
			fagIProgrammet.remove(i);
		} catch (IndexOutOfBoundsException e) {
			// return message til bruker? Handle exceptions på et høyere nivå
			// for mer uniform behandling
		}
	}

	public String getNavn() {
		return navn;
	}

	// toString returnerer en liste av alle fag i studieprogrammet.
	// kan være nødvendig å flytte en sånn metode til window slik at hver
	// getFagkode blir en egen knapp.
	public String toString() {
		String stringen = "Navn: " + navn +  "\nFag:";
		for (int i = 0; i < fagIProgrammet.size(); i++) {
			stringen += "\n" + fagIProgrammet.get(i).getFagkode();
		}

		return stringen;
	}

	private int indeks(Fag fag) {
		for (int i = 0; i < fagIProgrammet.size(); i++) {
			if (fagIProgrammet.get(i).equals(fag)) {
				return i;
			}
		}
		return -1;
	}

}
