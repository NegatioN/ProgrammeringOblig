package no.HiOAProsjektV2013.Main;

import java.util.ArrayList;
import java.util.Date;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;

public class ScriptClass {

	private Skole skolen;
	private Fag fag1, fag2, fag3;
	private Student stud1, stud2, stud3;
	
	public ScriptClass(Skole skolen){
		this.skolen = skolen;
		generateStudent();
		generateLaerer();
		generateFag();
		generateKrav();
		
	}
	
	public void generateStudent(){
		for(int i = 0; i < 70; i++){
			if(i%3 == 0)
				stud1 = skolen.getStudentene().addStudent("Joakim Rishaug", "Joaimrishaug@gmail.com", 95153437, "Her 25", new Date());
			stud2 = skolen.getStudentene().addStudent("Lars-Erik Kasin", "lekasin@gmail.com", 12345678, "Her 2", new Date());
		}
//		stud1 = skolen.getStudentene().addStudent("Joakim Rishaug", "Joaimrishaug@gmail.com", 95153437, "Her 25", new Date());
//		stud2 = skolen.getStudentene().addStudent("Lars-Erik Kasin", "lekasin@gmail.com", 12345678, "Her 2", new Date());
//		stud3 = skolen.getStudentene().addStudent("Herp Derp", "Herp@gmail.com", 12345678, "Camp 25", new Date());
	}
	public void generateLaerer(){
		skolen.getLærerne().addLærer("Eva Hadler", "Hadlers@gonna.haddle", 48586939, "Haddleoffice-255");
		skolen.getLærerne().addLærer("Sindre Duvedahl", "duve@mailbag.com", 48586939, "Haddleoffice-253");
	}
	public void generateFag(){
		ArrayList<Laerer> lærer = skolen.getLærerne().findByNavn("Eva");
		fag1 = skolen.getFagene().addFag("Faget", "fage1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
		fag2 = skolen.getFagene().addFag("Fysikk", "FYSK1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
		fag3 = skolen.getFagene().addFag("Programmering", "prog1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
	}
	public void generateKrav(){
		fag1.addKrav("Oblig 1");
		fag2.addKrav("Muntlig fremføring");
		fag3.addKrav("Aids");
		
	}
	public void addFagStudent(){
		stud1.addFag(fag1);
		stud2.addFag(fag2);
		stud3.addFag(fag3);
	}
}
