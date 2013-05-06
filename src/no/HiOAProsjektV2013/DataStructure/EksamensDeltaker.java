package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;


/*
 * En eksamensdeltaker er et ganske enkelt objekt som inneholder en student, karakter og andre småinfo
 * om en eksamensdeltakelse.
 */
public class EksamensDeltaker implements Serializable{

	private static final long serialVersionUID = 1041L;
	private Student deltakeren;
	private char karakter = '\0';
	private boolean oppmeldt;
	private Fag fag;
	
	public EksamensDeltaker(Student deltakeren, Fag fag){
		this.deltakeren = deltakeren;
		this.fag = fag;
		//legger til denne eksamenen til deltakerens eksamensliste
		deltakeren.setEksamen(this);
		oppmeldt = true;
	}
	public void setKarakter(char k){
		char process = Character.toUpperCase(k);
		karakter = process;
	}
	public char getKarakter(){
		return karakter;
	}
	public Student getDeltaker(){
		return deltakeren;
	}
	public void avmeld(){
		oppmeldt = false;
	}
	public void påmeld(){
		oppmeldt = true;
	}
	public boolean isOppmeldt(){
		return oppmeldt;
	}
	public Fag getFag(){
		return fag;
	}
	
}
