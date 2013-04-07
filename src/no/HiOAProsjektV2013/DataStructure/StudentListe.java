package no.HiOAProsjektV2013.DataStructure;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StudentListe{
	
	public static int studentNummer = 100000;

	private List<Student> register;
	//iterator må lages på nytt hver gang et objekt har blitt lagt til
	//ellers oppstår concurrentmodification-exception.
	private Iterator<Student> iterator;

	public StudentListe(){
		register = new LinkedList<>();
	}
	//legger til en ny student UANSETT og incrementer studentnummer
	public void addStudent(String navn, String epost, int tlf, String adresse, Date start){
		Student s = new Student(navn, epost, tlf, adresse, "s" + studentNummer++, start);
		register.add(s);
	}
	public ArrayList<Student> findKravBeståttStudenter(Fag fag){
		ArrayList<Student> studentene = new ArrayList<>();
		refreshIterator();
		Student s = null;
		while(iterator.hasNext()){
			s = iterator.next();
			if(s.innfriddKrav(fag))
				studentene.add(s);
		}
		
		
		return studentene;
	}
	
	
	public ArrayList<Student> findStudentByFag(Fag fag){
		ArrayList<Student> studentene = new ArrayList<>();
		String fagkode = fag.getFagkode();
		refreshIterator();
		while(iterator.hasNext()){
			Student s = iterator.next();
			//sjekker hver students arbeidskrav for fagkoden, og ser om de har faget.
			if(s.harFaget(fagkode))
				studentene.add(s);			
		}		
		//returnerer en liste med alle studentene i faget
		return studentene;
	}
	
	public ArrayList<Student> findStudentByStudieprogram(Studieprogram sp){
		ArrayList<Student> studentene = new ArrayList<>();
		refreshIterator();
		//går gjennom studentlista og tar ut alle med gitt studieprogram
		while(iterator.hasNext()){
			Student s = iterator.next();
			if(s.getStudieprogram().equals(sp)){
				studentene.add(s);
			}
		}		
		return studentene;
	}
	
	public String toString(){
		String stringen = new String();
		
		refreshIterator();
		
		while(iterator.hasNext()){
			stringen += iterator.next().getStudentnummer() + "\n";
		}
		return stringen;
	}
	//gjør iteratoren klar for gjennomløping av lista. Laget for oversiktlighet
	private void refreshIterator(){
		iterator = register.iterator();
	}
	
}
