package no.HiOAProsjektV2013.DataStructure;

import java.io.Serializable;

//	Klassen fungerer som en samling av alle datastrukturer for skolesystemet
//	Dette for å unngå clutter i hovedklassen/vindusklassen som skal lage og holde datastrukturen
public class Skole implements Serializable{

	private static final long serialVersionUID = 1000L;
	private StudieprogramListe studieprogrammene = new StudieprogramListe();
	private LaererListe lærerne = new LaererListe();
	private FagListe fagene = new FagListe();
	private StudentListe studentene = new StudentListe();
	private String in, resultat;
	private String navnRegex = "\\D+";
	private String fagkodeRegex = "\\w{4}\\d{4}";
	private String studentNrRegex = "s\\d{6}";
	private String arRegex = "\\d{4}";
	
	public Skole() {
		// Constructor
	}

	public StudieprogramListe getStudieprogrammene() {
		return studieprogrammene;
	}

	public LaererListe getLærerne() {
		return lærerne;
	}

	public FagListe getFagene() {
		return fagene;
	}

	public StudentListe getStudentene() {
		return studentene;
	}
	
	//Legger til et gitt fag i et gitt studieprogram
	public void addFagToStudProg(String navn, String fagkode){
		Studieprogram sp = studieprogrammene.findByNavn(navn);
		Fag f = sp.finnFag(getFagene().finnFagByFagkode(fagkode));
		if( f == null)
			sp.addFag(getFagene().finnFagByFagkode(fagkode));
	}
	
	
	//****************SØØØØØØØØØØK SØKEMETODER SØØØØØØØØØØK****************//
	
	public String søk(String input){ //Sjekker inputen i søkefeltet og utfører relevante søk

		resultat = "Søkeresultat:";
		
		in = input; //For at input skal kunne brukes i de andre søkemetodene
		
		if(in.matches(arRegex)){ //Sjekker om det er søkt på år
			
		}
		else if (in.matches(fagkodeRegex)){ //Sjekker om det er søkt på fagkode	
			return fagkodeSøk();
		}
		else if (in.matches(studentNrRegex)){ //Sjekker om det er søkt på studentNr
			return studentNrSøk();
		}
		else if (in.matches(navnRegex)){ //Sjekker om det er søkt på navn (student, lærer, fag og studieprogram)
			return navnSøk();
		}
		
		return "Ugyldig søk";
	}

	public String navnSøk(){ //Utfører alle søk som tar imot navn, og returnerer en string (SKAL RETURNERE OBJEKT)
		for(Laerer l : getLærerne().findByNavn(in)){
			resultat += "\n" + l.getfNavn() +" "+ l.geteNavn();
		}
		
		for(Student s : getStudentene().findByNavn(in)){
			resultat += "\n" + s.getfNavn() +" "+ s.geteNavn();
		}
		
		for(Fag f : getFagene().findByNavn(in)){
			resultat += "\n" + f.getNavn() +" "+ f.getFagkode();
		}
		
		if(getStudieprogrammene().findByNavn(in) != null)
			resultat += "\n" + getStudieprogrammene().findByNavn(in).getNavn();
		
		if( resultat == "Søkeresultat:")
			return "Ingen treff";
		return resultat;
	}
	
	public String årSøk(){
		return null;
	}

	public String studentNrSøk(){

		resultat += "\n" + getStudentene().findStudentByStudentNr(in);
		if( resultat == "Søkeresultat:")
			return "Ingen treff";
		return resultat;
	}
	
	public String fagkodeSøk(){
	
		resultat += "\n" + getFagene().finnFagByFagkode(in);
		if( resultat == "Søkeresultat:")
			return "Ingen treff";
		return resultat;

	}

}
