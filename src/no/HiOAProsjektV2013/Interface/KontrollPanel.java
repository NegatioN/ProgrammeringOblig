package no.HiOAProsjektV2013.Interface;

import no.HiOAProsjektV2013.DataStructure.Skole;

//klassen skal ta for seg håntering av input og videresende dette til 
public class KontrollPanel {

	//SØK PÅ NAVN AV FAG/STUDENT o.l. blir gjort som en else-case. Altså hvis det ikke matcher et regex
	
	private Skole skolen = new Skole();
	private String fagkodeRegex = "\\D{4}\\d{4}";
	private String studentNrRegex = "s\\d{6}";
	private String årRegex = "\\d{4}";
	
	public void checkSpression(String input){
		
	}
	
}
