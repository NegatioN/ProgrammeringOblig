package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FagListe implements Serializable{

	private static final long serialVersionUID = 1030L;
	private List<Fag> register = new LinkedList<>();
	private Iterator<Fag> iterator;
	private Iterator<EksamensDeltaker> eIterator;
	
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
		eIterator = deltakerne.iterator();
		while(eIterator.hasNext()){
			EksamensDeltaker ed = eIterator.next();
			if(ed.isOppmeldt())
			studentene.add(ed.getDeltaker());
		}
			
		return studentene;
	}
	
	//Finner et fag basert på navnet, kan returnere flere fag.
	public ArrayList<Fag> finnFagByNavn(String navn){
		ArrayList<Fag> fagene = new ArrayList<>();
		Fag faget = null;
		refreshIterator();
		while(iterator.hasNext()){
			faget = iterator.next();
			if(navn.equalsIgnoreCase(faget.getNavn()))
				fagene.add(faget);
		}
		
		
		return fagene;
	}
	//returnerer null om vi ikke finner faget ved input, returnerer unikt fag basert på fagkode
	public Fag finnFagByFagkode(String fagkode){
		Fag faget = null;
		refreshIterator();
		while(iterator.hasNext()){
			faget = iterator.next();
			if(fagkode.equalsIgnoreCase(faget.getFagkode()))
				return faget;
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
		refreshIterator();
		while (iterator.hasNext()) {
			String fagetsKode = iterator.next().getFagkode();
			if (fagkode.equalsIgnoreCase(fagetsKode)) {
				return false;
			}
		}
		//kommer man gjennom loopen er det ingen fag med samme kode.
		return true;
	}
	
	private void refreshIterator(){
		iterator = register.iterator();
	}
	
	public String toString() {
		String stringen = new String();

		refreshIterator();

		while (iterator.hasNext()) {
			stringen += iterator.next().toString() + "\n\n";
		}
		return stringen;
	}
}
