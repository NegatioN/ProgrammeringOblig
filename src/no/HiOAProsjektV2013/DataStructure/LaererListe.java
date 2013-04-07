package no.HiOAProsjektV2013.DataStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LaererListe {

	private List<Laerer> register = new LinkedList<>();
	private Iterator<Laerer> iterator;
	
	public LaererListe(){
		
	}
	
	public void add(Laerer lærer){
		register.add(lærer);
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
			if(navnene.length < Person.KUNETNAVNSKREVET){
				lærerneEtternavn = findLærerByEtternavn(navnene[Person.ETTERNAVN]);
			}else{
				//hvis denne slår til, har brukeren skrevet inn kun et ord, og det vil da søkes på både som fornavn og etternavn
				lærerneEtternavn = findLærerByEtternavn(navnene[Person.FORNAVN]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}finally{
			for(Laerer l : lærerneEtternavn){
				System.out.println("Her er jeg");
				lærerne.add(l);
			}
		}

//		while(iterator.hasNext()){
//			System.out.println("Her er jeg");
//			Laerer lærer = iterator.next();
//			System.out.println(lærer.geteNavn());
//			lærerne.add(lærer);
//		}
		
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
	
	
	
	
	private void refreshIterator(){
		iterator = register.iterator();
	}
	
}
