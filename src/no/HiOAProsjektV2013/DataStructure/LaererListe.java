package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LaererListe implements Serializable{

	private static final long serialVersionUID = 1010L;
	private List<Laerer> register = new LinkedList<>();
	private Iterator<Laerer> iterator;
	
	public LaererListe(){
		
	}
	
	public void addLærer(Laerer lærer){
		register.add(lærer);
	}
	

	//Legger til ny lærer med parameterne som input
	public Laerer addLærer(String navn, String epost, int tlf, String kontorNr){
		Laerer l = new Laerer(navn, epost, tlf, kontorNr);
		addLærer(l);
		return l;
	}
	public void removeLærer(Laerer lærer){
		register.remove(lærer);
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
			lærerne.addAll(lærerneEtternavn);
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

