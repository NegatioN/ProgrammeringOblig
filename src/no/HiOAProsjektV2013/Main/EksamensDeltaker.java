package no.HiOAProsjektV2013.Main;

public class EksamensDeltaker {

	private Student deltakeren;
	private char karakter = '\0';
	
	public EksamensDeltaker(Student deltakeren){
		this.deltakeren = deltakeren;
	}
	public void setKarakter(char k){
		karakter = k;
	}
	public Student getDeltaker(){
		return deltakeren;
	}
	
}
