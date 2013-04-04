package no.HiOAProsjektV2013.Main;


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
	
	public Laerer[] findLærer(String navn){
		Liste<Integer> posisjonene = new Liste<>();
		for(int i = 0;i<lærerne.size();i++){
			if(navn.equals(lærerne.get(i).getNavn())){
				posisjonene.add(i);
			}
		}
		
		Laerer[] lærerMedNavnet = new Laerer[posisjonene.size()];
		for(int j = 0; j < posisjonene.size();j++){
			lærerMedNavnet[j] = lærerne.get(posisjonene.get(j));
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
