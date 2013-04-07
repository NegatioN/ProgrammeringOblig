package no.HiOAProsjektV2013.DataStructure;

public class EksamensDeltaker {

	private Student deltakeren;
	private char karakter = '\0';
	
	public EksamensDeltaker(Student deltakeren){
		this.deltakeren = deltakeren;
	}
	public void setKarakter(char k){
		karakter = k;
	}
	public char getKarakter(){
		return karakter;
	}
	public Student getDeltaker(){
		return deltakeren;
	}
	
}
