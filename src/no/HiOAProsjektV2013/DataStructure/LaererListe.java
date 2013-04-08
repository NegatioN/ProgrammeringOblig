package no.HiOAProsjektV2013.DataStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LaererListe extends Liste<Laerer>{

	
	private List<Laerer> register = new LinkedList<>();
	private Iterator<Laerer> iterator;
	
	public LaererListe(){
		
	}
	
	public void add(Laerer lærer){
		register.add(lærer);
	}

	//Fant ut at jeg ikke måtte ha denne, men kanskje ikke så dumt?
	public void addLærer(String navn, String epost, int tlf, String kontorNr){
		Laerer l = new Laerer(navn, epost, tlf, kontorNr);
		register.add(l);
	}
	
	
	//finner lærer ved navn uansett om det søkes ved fornavn eller etternavn i input
	//PS finner IKKE folk med delvis treff. Må være korrekt stavet fornavn eller etternavn.
	public ArrayList<Laerer> findLærerByNavn(String navn){
		String regex = "\\s";
		String[] navnene = navn.split(regex);
		
		ArrayList<Laerer> lærerne = findLærerByFornavn(navnene[Person.FORNAVN]);
		ArrayList<Laerer> lærerneEtternavn = null;
		//har brukeren skrevet inn minst to ord?
		try {
			if(navnene.length > Person.KUNETNAVNSKREVET){
				lærerneEtternavn = findLærerByEtternavn(navnene[navnene.length - Person.ETTERNAVN]);
			}else{
				//hvis denne slår til, har brukeren skrevet inn kun et ord, og det vil da søkes på både som fornavn og etternavn
				lærerneEtternavn = findLærerByEtternavn(navnene[Person.FORNAVN]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}finally{
			//setter sammen begge listene som kommer fra søket.
			for(Laerer l : lærerneEtternavn){
				lærerne.add(l);
			}
		}
		
		return lærerne;
	}
	
	//delmetode i findByName
	// Finner alle lærerne med et gitt fornavn
	private ArrayList<Laerer> findLærerByFornavn(String fnavn) {
		ArrayList<Laerer> lærerne = new ArrayList<>();
		refreshIterator();
		
		while(iterator.hasNext()){
			Laerer sjekkLærer = iterator.next();
			String fornavn = sjekkLærer.getfNavn();
			if(fornavn.equalsIgnoreCase(fnavn)){
				lærerne.add(sjekkLærer);
			}
		}

		return lærerne;
	}

	//delmetode i findByName
	private ArrayList<Laerer> findLærerByEtternavn(String enavn) {

		ArrayList<Laerer> lærerne = new ArrayList<>();
		refreshIterator();
		
		while(iterator.hasNext()){
			Laerer sjekkLærer = iterator.next();
			String etternavn = sjekkLærer.geteNavn();
			if(etternavn.equalsIgnoreCase(enavn)){
				lærerne.add(sjekkLærer);
			}
		}

		return lærerne;
	}
	
	
}

