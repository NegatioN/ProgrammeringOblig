package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;

//abstrakt parent-class for å minimere kodeduplikasjon i StudentListe og LaererListe
public abstract class PersonListe<E> implements Serializable{
	
	
	private static final long serialVersionUID = 1100L;

	public PersonListe(){
		//
	}

	// parameterisert metode for å sette sammen listene man får etter å søke
	// gjennom etter fornavn og etternavn i StudentListe og LaererListe
	public ArrayList<E> findByNavn(ArrayList<E> fornavn, ArrayList<E> etternavn){
		ArrayList<E> folkene = fornavn;
		//slik at listen ikke automatisk blir tom om det ikke finnes noen folk med gitt fornavn
		if(folkene.isEmpty()){
			for(E person : etternavn){
				folkene.add(person);
			}
		}else{
			ArrayList<Integer> valueHolder = new ArrayList<>();
			int counter = 0;
			for(E person : etternavn){
				for(E eksisterendeFolk : folkene){
					if(!eksisterendeFolk.equals(person))
						valueHolder.add(counter);
				}
				counter++;
			}
			//må ha en loop utenfor slik at folkene-lista ikke endrer seg MENS den looper, og gir concurrentmodException.
			//hvis vi folk som ikke eksisterer fra før
			if(!valueHolder.isEmpty()){
				for(Integer i : valueHolder){
					folkene.add(etternavn.get(i));
				}
			}
		}
		return folkene;
	}

	//metoden deler opp et input-navn til fornavn og etternavn slik at det kan søkes på.
	public String[] nameSplitter(String navn){
		String regex = "\\s";
		String[] navnene = navn.split(regex);
		
		String[] ferdig = new String[2];
		ferdig[Person.FORNAVN] = navnene[Person.FORNAVN];
		//hvis bruker kun har skrevet inn et navn, vil det bli søkt på som fornavn og etternavn
		if(navnene.length > Person.KUNETNAVNSKREVET)
		ferdig[Person.ETTERNAVN] = navnene[navnene.length - Person.ETTERNAVN];
		else
			ferdig[Person.ETTERNAVN] = navnene[Person.FORNAVN];

		return ferdig;
	}
	
}
