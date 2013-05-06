package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Liste over fag, med diverse metoder for å søke på fagene.
 */
public class FagListe implements Serializable{

	private static final long serialVersionUID = 1030L;
	private List<Fag> register = new LinkedList<>();
	
	public FagListe(){
		
	}
	public void removeFag(Fag fag){
		register.remove(fag);
	}
	
	//vi går ut ifra at det kun er snakk om den aller nyeste eksamenen i dette tilfellet.
	//Finner alle studentene som er oppmeldt i faget.
	public ArrayList<Student> findEksamensOppmeldtStudenter(Fag fag){
		ArrayList<Student> studentene = new ArrayList<>();
		Eksamen e = fag.getRecentEksamen();
		List<EksamensDeltaker> deltakerne = e.getDeltakere();
		for(EksamensDeltaker ed : deltakerne){
			if(ed.isOppmeldt())
			studentene.add(ed.getDeltaker());
		}
			
		return studentene;
	}
	
	//Finner et fag basert på navnet, kan returnere flere fag.
	public ArrayList<Fag> findByNavn(String navn){
		ArrayList<Fag> fagene = new ArrayList<>();
		for(Fag f : register){
			if(navn.equalsIgnoreCase(f.getNavn()))
				fagene.add(f);
		}
		return fagene;
	}
	//returnerer null om vi ikke finner faget ved input, returnerer unikt fag basert på fagkode
	public Fag finnFagByFagkode(String fagkode){
		for(Fag f : register){
			if(fagkode.equalsIgnoreCase(f.getFagkode()))
				return f;
		}	
		return null;
	}
	
	// metoden legger til et fag i faglisten, sjekker via privat metode om
	// fagkoden er valid
	// og returnerer true om faget ble lagt til
	public boolean addFag(Fag faget) {

		String fagkode = faget.getFagkode();
		
		// privat metode for å sjekke at fagkoden er unik.
		// Burde legge til bedre håndtering for input fra bruker, slik at
		// brukeren ikke mister alt om det blir skrevet et fagkode som allerede
		// er i bruk
		if (checkValidFagkode(fagkode)) {
			register.add(faget);
			return true;
		}
		return false;
	}
	
	//Legger til fag med parameter
	public Fag addFag(String navn, String fagkode, String beskrivelse, String vurderingsform , int studiepoeng, Laerer lærer){
		Fag f = new Fag(navn, fagkode, beskrivelse, vurderingsform, studiepoeng, lærer);
		addFag(f);
		return f;
	}
	
	
	//sjekker om fagkode ikke er lik eksisterende fagkode, og returnerer boolean
	private boolean checkValidFagkode(String fagkode) {
		for(Fag f : register){
			String fagetsKode = f.getFagkode();
			if (fagkode.equalsIgnoreCase(fagetsKode)) {
				return false;
			}
		}
		//kommer man gjennom loopen er det ingen fag med samme kode.
		return true;
	}
	
	//Returnerer liste over alle Fag
	public ArrayList<Fag> visAlle() {
		ArrayList<Fag> fagene = new ArrayList<>();
		for(Fag f : register){
			fagene.add(f);
		}
		return fagene;
	}
	
	public String toString() {
		String stringen = new String();

		for(Fag f : register){
			stringen += f.toString() + "\n\n";
		}
		return stringen;
	}
}
