package no.HiOAProsjektV2013.Interface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Denne klassen brukes til å opprette tekstfeltene som brukes i programmet. Muligheten til å sjekke opp mot regexer er særlig nyttig,
 * da mange av tekstfeltene brukes til å lagre objecter, og derfor må ha tekst på en spesiell form. Her kan vi også sørge for at feltene
 * har en standardtekst forsvinner når det skal skrives, og dukker opp igjen når feltet blankes ut.
 */
public class InputFelt extends JTextField implements FocusListener{

	private static final long serialVersionUID = 101112L;
	private String regex = ".*"; //Standardregex for åpne felter. Endres som regel i konstruktøren
	private String tekst, feilmelding;
	public static final int KORT = 12, LANG = 20;

	//Bruker overloading til å kunne opprette forskjellige tekstfelter etter behov
	public InputFelt(String tekst, int bredde){
		super(tekst, bredde);
		this.tekst = tekst; //Tekst brukes til å lagre standardteksten feltet skal inneholde. Det skal kunne dukke opp igjen når feltet blankes ut
		addFocusListener(this); //Brukes til å fjerne/sette teksten når feltet er, og ikke er i fokus
	}
	public InputFelt(String tekst, int bredde, String regex ){
		super(tekst, bredde);
		this.regex = regex; //regex som sier noe om hva som skal tas inn via dette feltet
		this.tekst = tekst;
		addFocusListener(this); 
	}
	public InputFelt(String tekst, int bredde, ActionListener al){
		super(tekst, bredde);
		this.tekst = tekst;
		addFocusListener(this);
		addActionListener(al); //Tar imot ActionListener hvis feltet skal kunne ta utføre spesielle oppgaver
	}
	public InputFelt(String tekst, int bredde, Boolean editable){ //Brukes på felter som ikke skal blankes ut. Oftest felter som ikke skal redigeres.
		super(tekst, bredde);
		setEditable(editable);
	}
	
	public void focusGained(FocusEvent e) {
		setForeground(Color.BLACK); //Tilbakestiller tekstfargen hvis feltet har vist en feilmelding
		if(getText().equals(tekst) || getText().equals(feilmelding)) //Hvis teksten tilsvarer standardtekst eller feilmelding,
			setText("");											//har ikke bruker skrevet noe, og feltet blankes ut for brukervennlighet
	}
	public void focusLost(FocusEvent e) {
		if(getText().equals("")) //Fyller feltet med standardteksten hvis det er blankt
			setText(tekst);
		else if(!getText().matches(regex)){ //Sjekker hvilken regex som er sendt ved. Denne forteller hva feltet skal ta imot,
			switch(regex){					//og også hvilken feilmelding som skal vises
			case Vindu.fagkodeRegex :
				feilmelding = "Kun på formen ABCD1234";
				break;
			case Vindu.tittelRegex :
				feilmelding = "Kun ett eller to ord tillatt";
				break;
			case Vindu.studentNrRegex :
				feilmelding = "Kun på formen S123456";
				break;
			case Vindu.årRegex :
				feilmelding = "Kun på formen 2010";
				break;
			case Vindu.mobRegex :
				feilmelding = "Kun 8 siffer tillatt";
				break;
			case Vindu.mailRegex :
				feilmelding = "Feil epost-format";
				break;
			case Vindu.navnRegex :
				feilmelding = "Fornavn og etternavn";
				break;
			case Vindu.dateRegex :
				feilmelding = "På form dd.mm.åå, eller dd.mm.åååå";
				break;
			case Vindu.sPoengRegex:
				feilmelding = "Kun hele tall mellom 0-60";
				break;
			default:
				feilmelding = "Feil inputformat"; //Generell feilmelding som ikke skal forekomme
			}
			setForeground(Color.RED); //Bytter tekstfargen for å tydeligjøre at det sendes en feilmelding
			setText(feilmelding); //Setter passende feilmelding i tekstfeltet så bruker ser hvor feilen er
		} 
	} //End of FocusLost
} //End of class InputFelt
