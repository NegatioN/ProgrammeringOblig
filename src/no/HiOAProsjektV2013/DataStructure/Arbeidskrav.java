package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Klassen definerer en liste av krav i et fag-objekt slik at en student kan ta eksamen
public class Arbeidskrav implements Serializable, Cloneable{

	private static final long serialVersionUID = 1050L;
	private String fagkode;
	private List<Krav> register = new ArrayList<>();

	public Arbeidskrav(Fag fag) {
		fagkode = fag.getFagkode();
	}
	public Arbeidskrav(Arbeidskrav krav){
		fagkode = krav.getFagkode();
		
	}

	// Sjekker at alle arbeidskrav i listen er innfridd. Hvis ikke alle er
	// innfridd returners false
	public boolean kravInnfridd() {
		for(Krav krav : register){
			if(!krav.isGodkjent())
				return false;
		}
		//hvis vi kommer gjennom loopen er alle krav innfridd.
		return true;
	}

	public void addKrav(Krav krav) {
		register.add(krav);
	}
	public String getFagkode(){
		return fagkode;
	}
	
	public String toString(){
		String stringen = "";
		for(Krav krav : register){
			stringen += krav.getBeskrivelse() + "\n";
		}
		return stringen;
	}
	
	public List<Krav> getKrav(){
		return register;
	}
	private void dropList(){
		register = new ArrayList<>();
	}
	public Arbeidskrav clone(Arbeidskrav krav){
		Arbeidskrav kravet = null;
		try {
			kravet = (Arbeidskrav) krav.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		kravet.dropList();
		int counter = 0;
		
		for(Krav minikrav : krav.getKrav()){
			System.out.println(++counter);
			Krav klonekrav = new Krav(minikrav);
			kravet.addKrav(klonekrav);
		}
		System.out.println("Krav-hash " + krav.hashCode());
		System.out.println("klone-hash " + kravet.hashCode());
		return kravet;
	}

}
