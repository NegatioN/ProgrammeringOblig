package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

//abstrakt parent-class for å minimere kodeduplikasjon i StudentListe og LaererListe
//FIKS NAVNSORTERING PÅ LÆRERE JOAKIM
public abstract class PersonListe<E> implements Serializable{
	
	
	private static final long serialVersionUID = 1100L;
	private final int FØRSTE = 0, ANDRE = 1, FORNAVN = 10, ETTERNAVN = 20;

	public PersonListe(){
		//
	}

	
	public ArrayList<E> findByNavn(String input, ArrayList<E> fornavnRegister, ArrayList<E> etternavnRegister) {
		
		String[] navn = nameSplitter(input);
		System.out.println(navn[0] + "\t" + navn[1]);
		
		char fFirstLetter = navn[FØRSTE].charAt(FØRSTE);
		fFirstLetter = Character.toUpperCase(fFirstLetter);
		char eFirstLetter = navn[ANDRE].charAt(FØRSTE);
		eFirstLetter = Character.toUpperCase(eFirstLetter);
		
		int max = (etternavnRegister.size() - 1);
		int min = 0;
		int mid;
		int searchStart = 0;
		E tempPerson;
		
		while (min <= max) {
			mid = (max + min) / 2;
			tempPerson = etternavnRegister.get(mid);
			// finner plassen i arrayen hvor vi skal starte sekvensiellt søk.
			if (eFirstLetter == ((Person) tempPerson).geteNavn().charAt(FØRSTE)) {
				searchStart = etternavnRegister.indexOf(tempPerson);
				// ruller oss tilbake til starten av denne bokstavens forekomst.
				try{
					while (eFirstLetter == ((Person) etternavnRegister
							.get(searchStart - 1)).geteNavn().charAt(FØRSTE)) {
						searchStart--;
					}
				}catch(ArrayIndexOutOfBoundsException e){
					//fortsetter med uendret searchStart
				}
				break;
			} else if (eFirstLetter < ((Person) tempPerson).geteNavn().charAt(FØRSTE)) {
				max = mid - 1;
			} else if (eFirstLetter > ((Person) tempPerson).geteNavn().charAt(FØRSTE)) {
				min = mid + 1;
			}

		}// end while for etternavn

		ArrayList<E> studenteneEtternavn = looper(searchStart, navn[ANDRE], etternavnRegister, eFirstLetter, ETTERNAVN);
		
		searchStart = 0;
		min = 0;
		max = (fornavnRegister.size() -1);
		while (min <= max) {
			mid = (max + min) / 2;
			tempPerson = fornavnRegister.get(mid);
			// finner plassen i arrayen hvor vi skal starte sekvensiellt søk.
			if (fFirstLetter == ((Person) tempPerson).getfNavn().charAt(FØRSTE)) {
				searchStart = fornavnRegister.indexOf(tempPerson);
				// ruller oss tilbake til starten av denne bokstavens forekomst.
				try{
					while (fFirstLetter == ((Person) fornavnRegister.get(searchStart - 1))
							.getfNavn().charAt(FØRSTE)) {
						searchStart--;
					}
				}catch(ArrayIndexOutOfBoundsException ex){
					break;
				}
				break;
			} else if (fFirstLetter < ((Person) tempPerson).getfNavn().charAt(FØRSTE)) {
				max = mid - 1;
			} else if (fFirstLetter > ((Person) tempPerson).getfNavn().charAt(FØRSTE)) {
				min = mid + 1;
			}

		}// end while for fornavn
		ArrayList<E> studenteneFornavn = looper(searchStart, navn[FØRSTE], fornavnRegister, fFirstLetter, FORNAVN);
		
		ArrayList<E> studentene = findByNavn(studenteneFornavn, studenteneEtternavn);
		
		return studentene;
	}
	
	//looper gjennom alle med samme forbokstav som input sekvensiellt. Enten i fornavnslista eller etternavn.
	private ArrayList<E> looper(int start, String input, ArrayList<E> sortedList, char first, int qualifier){
		ArrayList<E> folkene = new ArrayList<>();
		Iterator iterator;
		if (qualifier == ETTERNAVN) {
			try {
				// legger til alle studentene fra searchStart til første bokstav
				// ikke lengre er det samme.
				iterator = sortedList.listIterator(start);
				Pattern mønster = Pattern.compile(Pattern.quote(input), Pattern.CASE_INSENSITIVE);
				while (iterator.hasNext()) {
					E temp = (E) iterator.next();
					//pattern lages slik at vi kan søke med contains på all type input.
					if (first != ((Person) temp).geteNavn().charAt(FØRSTE)) {
						break;
					} else if(mønster.matcher(((Person) temp).geteNavn()).find()){
						folkene.add(temp);
					}
				}
			}// hvis arrayen er tom, eller ikke befolket med personer med gitt
				// bokstav som start.
			catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}else if(qualifier == FORNAVN){
			try {
				// legger til alle studentene fra searchStart til første bokstav
				// ikke lengre er det samme.
				Pattern mønster = Pattern.compile(Pattern.quote(input), Pattern.CASE_INSENSITIVE);
				iterator = sortedList.listIterator(start);
				while (iterator.hasNext()) {
					E temp = (E) iterator.next();

					if (first != ((Person) temp).getfNavn().charAt(FØRSTE)) {
						break;
					} else if(mønster.matcher(((Person) temp).getfNavn()).find()){
						folkene.add(temp);
					}
				}
			}// hvis arrayen er tom, eller ikke befolket med personer med gitt
				// bokstav som start.
			catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
		return folkene;
	}
	
	
	// parameterisert metode for å sette sammen listene man får etter å søke
	// gjennom etter fornavn og etternavn i StudentListe og LaererListe
	public ArrayList<E> findByNavn(ArrayList<E> fornavn, ArrayList<E> etternavn){
		ArrayList<E> folkene = fornavn;
		try{
		for(E s : etternavn){
			if(!folkene.contains(s))
				folkene.add(s);
		}
		}catch(NullPointerException e){
			e.printStackTrace();
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
