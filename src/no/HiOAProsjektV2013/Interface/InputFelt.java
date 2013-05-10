package no.HiOAProsjektV2013.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class InputFelt extends JTextField implements FocusListener, ActionListener{

	private static final long serialVersionUID = 101112L;
	private String regex = ".*";
	private String tekst, feilmelding;
	
	public static final String fagkodeRegex = "\\D{4}\\d{4}";
	public static final String studentNrRegex = "s\\d{6}";
	public static final String årRegex = "\\d{4}";
	public static final String mobRegex = "\\d{8}";
	public static final String mailRegex = "\\S+@\\S+.\\S+";
	public static final String navnRegex = "\\S+\\s\\S+";
	public static final String datoRegex = "\\d{2}-\\w{4}-\\d{2}";
	
	public InputFelt(String tekst, int bredde){
		super(tekst, bredde);
		this.tekst = tekst;
		setName(tekst);
		addFocusListener(this);
	}
	public InputFelt(String tekst, int bredde, String regex ){
		super(tekst, bredde);
		this.regex = regex;
		this.tekst = tekst;
		setName(tekst);
		addFocusListener(this);
	}
	public InputFelt(String tekst, int bredde, ActionListener al){
		super(tekst, bredde);
		this.tekst = tekst;
		setName(tekst);
		addFocusListener(this);
		addActionListener(al);
	}
	public InputFelt(String tekst, int bredde, Boolean editable){
		super(tekst, bredde);
		setName(tekst);
		if(editable)
			addFocusListener(this);
	}

//	public String getText(){
//		if(!super.getText().matches(regex)){
//			String s = super.getText();
//	
//		}
//			return "";
//	}
	
public void focusGained(FocusEvent e) {
		if(getText().equals(tekst) || getText().equals(feilmelding))
			setText("");
	}
	public void focusLost(FocusEvent e) {
		if(getText().equals(""))
			setText(tekst);
		else if(!getText().matches(regex)){
			switch(regex){
			case fagkodeRegex :
				feilmelding = "Kun på formen ABCD1234";
				break;
			case studentNrRegex :
				feilmelding = "Kun på formen S123456";
				break;
			case årRegex :
				feilmelding = "Kun på formen 2010";
				break;
			case mobRegex :
				feilmelding = "Kun 8 siffer tillatt";
				break;
			case mailRegex :
				feilmelding = "Feil epost-format";
				break;
			case navnRegex :
				feilmelding = "Fornavn og etternavn";
				break;
			case datoRegex :
				feilmelding = "Kun på formen dd-MMM-åå";
				break;
			default:
				feilmelding = "Feil inputformat";
			}
			setText(feilmelding);
		} 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
