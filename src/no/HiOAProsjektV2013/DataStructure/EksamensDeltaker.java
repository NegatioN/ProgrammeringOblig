package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;


//Sette eksamensdeltakerobjekt til å være en eksamen for en person om karakteren har blitt satt?
public class EksamensDeltaker implements Serializable{

	private static final long serialVersionUID = 1041L;
	private Student deltakeren;
	private char karakter = '\0';
	private boolean oppmeldt;
	
	public EksamensDeltaker(Student deltakeren){
		this.deltakeren = deltakeren;
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
	
}
