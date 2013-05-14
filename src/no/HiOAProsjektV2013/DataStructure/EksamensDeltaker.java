package no.HiOAProsjektV2013.DataStructure;
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 14.05.13
import java.io.Serializable;
import java.util.GregorianCalendar;


/*
 * En eksamensdeltaker er et ganske enkelt objekt som inneholder en student, karakter og andre småinfo
 * om en eksamensdeltakelse.
 */
public class EksamensDeltaker implements Serializable{

	private static final long serialVersionUID = 1041L;
	private Student deltakeren;
	private char karakter = '\0';
	private boolean oppmeldt, oppmøtt;
	private Fag fag;
	private GregorianCalendar kalender;
	
	public EksamensDeltaker(Student deltakeren, Fag fag, GregorianCalendar kalender){
		this.kalender = kalender;
		this.deltakeren = deltakeren;
		this.fag = fag;
		//legger til denne eksamenen til deltakerens eksamensliste
		deltakeren.setEksamen(this);
		oppmeldt = true;
	}
	public void setKarakter(char k){
		char process = Character.toUpperCase(k);
		karakter = process;
		setOppmøtt(true);
	}
	public char getKarakter(){
		return karakter;
	}
	public Student getDeltaker(){
		return deltakeren;
	}
	public void avmeld(){
		oppmeldt = false;
	}
	public void påmeld(){
		oppmeldt = true;
	}
	public boolean isOppmeldt(){
		return oppmeldt;
	}
	public Fag getFag(){
		return fag;
	}
	public GregorianCalendar getDato(){
		return kalender;
	}
	
	public String toString(){
		return ""+deltakeren.getStudentnummer();
	}
	public boolean isOppmøtt() {
		return oppmøtt;
	}
	public void setOppmøtt(boolean oppmøtt) {
		this.oppmøtt = oppmøtt;
		if(!oppmøtt)
			karakter = '\0';
	}
	
}
