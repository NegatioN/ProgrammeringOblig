package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import no.HiOAProsjektV2013.Main.EtternavnSammenligner;
import no.HiOAProsjektV2013.Main.FornavnSammenligner;

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
		settInnSortert(lærer);
	}
	
	private void settInnSortert(Laerer l){
		super.settInnSortert(l, fornavnRegister, new FornavnSammenligner());
		super.settInnSortert(l, etternavnRegister, new EtternavnSammenligner());
	}
	

	//Legger til ny lærer med parameterne som input
	public Laerer addLærer(String navn, String epost, int tlf, String kontorNr){
		Laerer l = new Laerer(navn, epost, tlf, kontorNr);
		addLærer(l);
		return l;
	}
	public void removeLærer(Laerer lærer){
		fornavnRegister.remove(lærer);
		etternavnRegister.remove(lærer);
	}
	
	//finner lærer ved navn uansett om det søkes ved fornavn eller etternavn i input
	//PS finner IKKE folk med delvis treff. Må være korrekt stavet fornavn eller etternavn.
	public ArrayList<Laerer> findByNavn(String input){
		ArrayList<Laerer> lærerne = super.findByNavn(input, fornavnRegister, etternavnRegister);
		
		return lærerne;
	}

	//Returnerer liste over alle lærere
	public ArrayList<Laerer> visAlle() {
		ArrayList<Laerer> lærerne = new ArrayList<>();
		for(Laerer l : fornavnRegister){
			lærerne.add(l);
		}
		return lærerne;
	}
	
	public String toString() {
		String stringen = new String();

		for(Laerer l : fornavnRegister){
			stringen += l.toString() + "\n\n";
		}
		return stringen;
	}
}

