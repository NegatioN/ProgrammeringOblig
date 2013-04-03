package no.HiOAProsjektV2013.Main;

import java.util.LinkedList;
import java.util.List;

public class Arbeidskrav {

	private String fagkode;
	private List<Krav> register = new LinkedList<>();
	
	public Arbeidskrav(Fag fag){
		fagkode = fag.getFagkode();
	}
	
	
	
}
