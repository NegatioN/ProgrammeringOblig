package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Liste over lærerobjekter. Har søkemetoder for å finne lærere og et register av lærere.
 */
public class LaererListe extends PersonListe<Laerer> implements Serializable{

	private static final long serialVersionUID = 1010L;
	private ArrayList<Laerer> fornavnRegister;
	private ArrayList<Laerer> etternavnRegister;
	
	public LaererListe(){
		etternavnRegister = new ArrayList<Laerer>();
		fornavnRegister = new ArrayList<Laerer>();
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
	public ArrayList<Laerer> findByNavn(String navn){
		String[] navnene = nameSplitter(navn);
		
		ArrayList<Laerer> fornavn = findByFornavn(navnene[Person.FORNAVN]);
		ArrayList<Laerer> etternavn = findByEtternavn(navnene[Person.ETTERNAVN]);
		ArrayList<Laerer> lærerne = super.findByNavn(fornavn, etternavn);
		return lærerne;
	}
	//delmetode for å gå gjennom registeret i studentlista.
	private ArrayList<Laerer> findByFornavn(String fnavn){
		ArrayList<Laerer> lærerne = new ArrayList<>();
		
		for(Laerer l : register){
			if(l.getfNavn().equalsIgnoreCase(fnavn)){
				lærerne.add(l);
			}
		}		
		return lærerne;
	}
	//delmetode for å gå gjennom registeret i studentlista.
	private ArrayList<Laerer> findByEtternavn(String enavn){
		ArrayList<Laerer> lærerne = new ArrayList<>();
		
		for(Laerer l : register){
			if(l.geteNavn().equalsIgnoreCase(enavn)){
				lærerne.add(l);
			}
		}		
		return lærerne;
	}

	//Returnerer liste over alle lærere
	public ArrayList<Laerer> visAlle() {
		ArrayList<Laerer> lærerne = new ArrayList<>();
		for(Laerer l : register){
			lærerne.add(l);
		}
		return lærerne;
	}
	
	public String toString() {
		String stringen = new String();

		for(Laerer l : register){
			stringen += l.toString() + "\n\n";
		}
		return stringen;
	}
}

