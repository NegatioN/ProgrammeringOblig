package no.HiOAProsjektV2013.Main;

import java.util.List;


//	Klassen fungerer som en samling av alle datastrukturer for skolesystemet
//	Dette for å unngå clutter i hovedklassen/vindusklassen som skal lage og holde datastrukturen
public class Skole {

	
	private Liste<Studieprogram> studieprogrammene = new Liste<>();
	private Liste<Laerer> lærerne = new Liste<>();
	private Liste<Fag> fagene = new Liste<>();
	private StudentRegister studentene = new StudentRegister();
	
	public Skole(){
		//Constructor
	}
	
	//Finner alle lærerne med et gitt fornavn
	public Laerer[] findLærerByFornavn(String fnavn){
		Liste<Integer> posisjonene = new Liste<>();
		for(int i = 0;i<lærerne.size();i++){
			if(fnavn.equals(lærerne.get(i).getfNavn())){
				posisjonene.add(i);
			}
		}
		
		return alleLærerne(posisjonene);
	}
	public Laerer[] findLærerByEtternavn(String enavn){
		
		Liste<Integer> posisjonene = new Liste<>();
		for(int i = 0;i<lærerne.size();i++){
			if(enavn.equals(lærerne.get(i).geteNavn())){
				posisjonene.add(i);
			}
		}
		return alleLærerne(posisjonene);
	}
	//privat metode for å unngå duplisering av kode på eNavn og fNavn-søk
	private Laerer[] alleLærerne(Liste<Integer> pos){
		Laerer[] lærerMedNavnet = new Laerer[pos.size()];
		
		for(int j = 0; j < pos.size();j++){
			lærerMedNavnet[j] = lærerne.get(pos.get(j));
		}
		
		return lærerMedNavnet;
	}
	
	public Liste<Studieprogram> getStudieprogrammene(){
		return studieprogrammene;
	}


	public Liste<Laerer> getLærerne() {
		return lærerne;
	}


	public Liste<Fag> getFagene() {
		return fagene;
	}


	public StudentRegister getStudentene() {
		return studentene;
	}
	
}
