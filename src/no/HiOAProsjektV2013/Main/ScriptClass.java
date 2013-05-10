package no.HiOAProsjektV2013.Main;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import no.HiOAProsjektV2013.DataStructure.Eksamen;
import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

//oppretter objekter.
public class ScriptClass {

	private Skole skolen;
	private Fag fag1, fag2, fag3;
	private Student stud1, stud2, stud3;
	private Studieprogram sp1, sp2;
	private Eksamen e1,e2,e3;
	private DateHandler dateHandler = new DateHandler();
	
	public ScriptClass(Skole skolen){
		this.skolen = skolen;
		generateLaerer();
		generateFag();
		generateStudProg();
		generateKrav();
		generateEksamen();
		generateStudent();
		
	}
	
	public void generateStudent(){
		GregorianCalendar dato = (GregorianCalendar) GregorianCalendar.getInstance();
		for(int i = 0;i<20;i++){
			stud1 = skolen.getStudentene().addStudent("Joakim Rishaug", "Joaimrishaug@gmail.com", 95153437, "Her 25", dato);
			stud2 = skolen.getStudentene().addStudent("Lars-Erik Kasin", "lekasin@gmail.com", 12345678, "Her 2", dato);
			stud3 = skolen.getStudentene().addStudent("Herp Derp", "Herp@gmail.com", 12345678, "Camp 25", dato);
			stud1.setStudieprogram(sp1);
			stud2.setStudieprogram(sp2);
			stud2.setStudieprogram(sp2);
			skolen.getStudentene().addStudent("Jon Jensen", "jon@gmail.com", 32131123, "Veien 22", dato);
			skolen.getStudentene().addStudent("Eva Jensen", "ej93@gmail.com", 44112231, "Veien 22", dato);
			skolen.getStudentene().addStudent("Stig Rishaug", "ilike@gmail.com", 98123823, "Storet 40", dato);
			skolen.getStudentene().addStudent("Yuri Spasiba", "Spasi@mail.com", 98123823, "Russia Central 22", dato);

		}
	}
	public void generateLaerer(){
		skolen.getLærerne().addLærer("Eva Hadler", "Hadlers@gonna.haddle", 48586939, "Haddleoffice-255");
		skolen.getLærerne().addLærer("Sindre Duvedahl", "duve@mailbag.com", 98228831, "Haddleoffice-253");
		skolen.getLærerne().addLærer("Jorunn Thatcher", "jord@database.com", 41212332, "Haddleoffice-123");

	}
	public void generateFag(){
		ArrayList<Laerer> lærer = skolen.getLærerne().findByNavn("Eva");
		fag1 = skolen.getFagene().addFag("Faget", "fage1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
		fag2 = skolen.getFagene().addFag("Fysikk", "FYSK1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
		fag3 = skolen.getFagene().addFag("Programmering", "prog1000", "Kult fag", "Skriftlig", 10, lærer.get(0));
	}
	public void generateEksamen(){
		e1 = new Eksamen(dateHandler.dateFixer("20-10-98", null),fag1);
		e2 = new Eksamen(dateHandler.dateFixer("20-10-98", null),fag2);
		e3 = new Eksamen(dateHandler.dateFixer("20-10-98", null),fag3);
		fag1.addEksamen(e1);
		fag2.addEksamen(e2);
		fag3.addEksamen(e3);
	}
	
	public void generateStudProg(){
		sp1 = skolen.getStudieprogrammene().addStudProg("Dataingeniør");
		sp2 = skolen.getStudieprogrammene().addStudProg("Anvendt datateknologi");
		sp1.addFag(fag1);
		sp2.addFag(fag2);
		sp2.addFag(fag3);
		sp1.addFag(fag3);
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
