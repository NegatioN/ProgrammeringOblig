package no.HiOAProsjektV2013.Interface;

import java.io.Serializable;

import no.HiOAProsjektV2013.DataStructure.Skole;

//klassen skal ta for seg håntering av input og videresende regex til metodene
public class KontrollPanel implements Serializable{

	private static final long serialVersionUID = 3L;
	//SØK PÅ NAVN AV FAG/STUDENT o.l. blir gjort som en else-case. Altså hvis det ikke matcher et regex
	
	private Skole skolen = new Skole();
	private String fagkodeRegex = "\\D{4}\\d{4}";
	private String studentNrRegex = "s\\d{6}";
	private String årRegex = "\\d{4}";
	private String mobRegex = "\\d{8}";
	private String mailRegex = "\\S+@\\S+.\\S+";
	private String adresseRegex = "\\s+";
	
	public String checkSpression(String input){
		if(input.matches(fagkodeRegex))
			return fagkodeRegex;
		else if(input.matches(studentNrRegex))
			return studentNrRegex;
		else if(input.matches(årRegex))
			return årRegex;
		else if(input.matches(mobRegex))
			return mobRegex;
		else if(input.matches(mailRegex))
			return mailRegex;
		else if(input.matches(adresseRegex))
			return adresseRegex;
		return null;
	}
	
}
