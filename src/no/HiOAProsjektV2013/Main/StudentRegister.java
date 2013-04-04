package no.HiOAProsjektV2013.Main;

import java.util.LinkedList;
import java.util.List;

public class StudentRegister {

	private List<Student> register = new LinkedList<>();
	
	public StudentRegister(){
		
	}
	public void addStudent(Student student){
		register.add(student);
	}
	
}
