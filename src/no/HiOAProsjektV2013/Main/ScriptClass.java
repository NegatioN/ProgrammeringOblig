package no.HiOAProsjektV2013.Main;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import no.HiOAProsjektV2013.DataStructure.Eksamen;
import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

//oppretter objekter.
public class ScriptClass {

	private Skole skolen;
	private Student stud1;
	private Studieprogram sp1, sp2,sp3;
	private DateHandler dateHandler = new DateHandler();
	private String[] fornavn = {"Sondre", "Martin", "Lars", "Erik", "Joakim", "Eva", "Ole", "Markus", "Jenny",
			"Martine", "Guro", "Line", "Harry", "Mikkel", "Kåre", "Petter"};
	private String[] etternavn = {"Bogen", "Hage", "Larsen", "Vihovde", "Sturlason", "Void", "Hansen", 
			"Persson", "Hansen", "Lillemark", "Hole", "Potter", "Petrov", "Galgas"};
	private String[] kravNavn = {"Obligatorisk Innlevering", "Muntlig fremføringg", "Multiple Choice"};
	private String[] fagnavn = {"Programmering","Fysikk", "Økonomi", "Oprativsystem", "Algoritmer", "Matematikk"};
	private String[] vurderingsform = {"Muntlig", "Skriftlig", "Prosjekt"};
	private ArrayList<Fag> scriptFagene = new ArrayList<>();
	private ArrayList<Studieprogram> scriptSp = new ArrayList<>();
	private int counter = 0;
	Random rand = new Random(); 

	
	public ScriptClass(Skole skolen){
		this.skolen = skolen;
		generateLaerer();
		generateEksamen();
		generateStudProg();
		generateStudent();
		
	}
	
	
	
	public void generateStudent(){
		int maxSp = scriptSp.size()-1;
		for(int i = 0; i<600;i++){
			String fnavn = fornavn[randomTall(0, fornavn.length-1)];
			String enavn = etternavn[randomTall(0, etternavn.length-1)];
			String navn = fnavn + " " + enavn;
			String epost = fnavn+"."+enavn+"@gmail.com";
			stud1 = skolen.getStudentene().addStudent(navn, epost, numberRandomizer() , "Adresse " + randomTall(0,100), dateRandomizer());
			stud1.setStudieprogram(scriptSp.get(randomTall(0,maxSp)));
			
		}
	}
	public void generateLaerer(){
		while(counter < fagnavn.length){
		String fnavn = fornavn[randomTall(0, fornavn.length-1)];
		String enavn = etternavn[randomTall(0, etternavn.length-1)];
		String navn = fnavn + " " + enavn;
		String epost = fnavn+"."+enavn+"@gmail.com";
		Laerer l = skolen.getLærerne().addLærer(navn, epost, numberRandomizer(), "PI-255");
		Fag f = generateFag(l);
		scriptFagene.add(f);
		counter++;
		}

	}
	private Fag generateFag(Laerer l){
		
		String fagnavnet = fagnavn[randomTall(0,fagnavn.length-1)];
		String fagkode = fagnavnet.substring(0, 4) + 1000;
		Fag fag = skolen.getFagene().addFag(fagnavnet, fagkode, "Beskrivende tekst", vurderingsform[randomTall(0,vurderingsform.length-1)], randomTall(0,30), l);
		
		int antKrav = randomTall(0,5);
		for(int i = 0; i<antKrav;i++){
			generateKrav(fag);
		}
		
		return fag;
	}
	private void generateEksamen(){
		for(Fag fag : scriptFagene){
			for (int i = 0; i < 3; i++) {
				Eksamen e = new Eksamen(dateRandomizer(), fag);
				fag.addEksamen(e);
			}
		}
	}
	
	public void generateStudProg(){
		
		
		
		sp1 = skolen.getStudieprogrammene().addStudProg("Dataingeniør");
		sp2 = skolen.getStudieprogrammene().addStudProg("Anvendt datateknologi");
		sp3 = skolen.getStudieprogrammene().addStudProg("Samfunnsøkonomi");
		scriptSp.add(sp1);
		scriptSp.add(sp2);
		scriptSp.add(sp3);
		int maxfag = scriptFagene.size()-1;
		
		for(int i = 0; i<6;i++){
			sp1.addFag(scriptFagene.get(randomTall(0,maxfag)));
			sp2.addFag(scriptFagene.get(randomTall(0,maxfag)));
			sp3.addFag(scriptFagene.get(randomTall(0,maxfag)));
		}
	}
	private void generateKrav(Fag fag){
		fag.addKrav(kravNavn[randomTall(0,kravNavn.length-1)]);		
	}
	
	
	//randomizers
	private int randomTall(int fra, int til)
	{
		//+1 for å unngå nextint(0)
		int tall = rand.nextInt(til-fra+1) + fra; 
		return tall;
	}
	private GregorianCalendar dateRandomizer(){
		int day = randomTall(1,28);
		int month = randomTall(0,12);
		int year = randomTall(1980, 2015);
		
		String date = day + "." + month + "." + year;
		GregorianCalendar greg = dateHandler.dateFixer(date, null);
		return greg;
	}
	private int numberRandomizer(){
		String number = "" + randomTall(1,9);
		for(int i = 0;i<7;i++){
			number += randomTall(0,9);
		}
		int tlfnummer = Integer.parseInt(number);
		return tlfnummer;
	}
	
}
