package no.HiOAProsjektV2013.Main;

import java.util.ArrayList;
import java.util.Date;

import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;

public class ScriptClass {

	private Skole skolen;
	
	public ScriptClass(Skole skolen){
		this.skolen = skolen;
		generateStudent();
		generateLaerer();
		generateFag();
		
	}
	
	public void generateStudent(){
		skolen.getStudentene().addStudent("Joakim Rishaug", "Joaimrishaug@gmail.com", 95153437, "Her 25", new Date());
		skolen.getStudentene().addStudent("Lars-erik Kasin", "lekasin@gmail.com", 12345678, "Her 2", new Date());
		skolen.getStudentene().addStudent("Herp Derp", "Herp@gmail.com", 12345678, "Camp 25", new Date());
	}
	public void generateLaerer(){
		skolen.getLærerne().addLærer("Eva Hadler", "Hadlers@gonna.haddle", 48586939, "Haddleoffice-255");
		skolen.getLærerne().addLærer("Sindre Duvedahl", "duve@mailbag.com", 48586939, "Haddleoffice-253");
	}
	public void generateFag(){
		ArrayList<Laerer> lærer = skolen.getLærerne().findByNavn("Eva");
		skolen.getFagene().addFag("Faget", "fage1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
		skolen.getFagene().addFag("Fysikk", "FYSK1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
		skolen.getFagene().addFag("Programmering", "prog1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
	}
}
