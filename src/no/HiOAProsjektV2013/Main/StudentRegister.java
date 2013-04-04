package no.HiOAProsjektV2013.Main;

import java.util.LinkedList;
import java.util.List;

public class StudentRegister {
	
	public static int studentNummer = 100000;
	

	private List<Student> register = new LinkedList<>();
	
	public StudentRegister(){
		
	}
	public void addStudent(String navn, String epost, int tlf, String adresse, int startÅr){
		Student s = new Student(navn, epost, tlf, adresse, "s" + studentNummer++, startÅr);
		register.add(s);
	}
	
	public String toString(){
		String stringen = new String();
		for(int i = 0; i < register.size();i++){
			stringen += register.get(i).getStudentnummer() + "\n";
		}
		
		return stringen;
	}
	
}
