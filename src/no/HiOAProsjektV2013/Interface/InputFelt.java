package no.HiOAProsjektV2013.Interface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class InputFelt extends JTextField implements FocusListener, ActionListener{

	private static final long serialVersionUID = 101112L;
	private String regex = ".*";
	private String tekst, feilmelding;
	
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

	public boolean sjekkInput(){
		return getText().matches(regex);
	}
	
	public void focusGained(FocusEvent e) {
		setForeground(Color.BLACK);
		if(getText().equals(tekst) || getText().equals(feilmelding))
			setText("");
	}
	public void focusLost(FocusEvent e) {
		if(getText().equals(""))
			setText(tekst);
		else if(!getText().matches(regex)){
			switch(regex){
			case TestWindow.fagkodeRegex :
				feilmelding = "Kun på formen ABCD1234";
				break;
			case TestWindow.tittelRegex :
				feilmelding = "Kun ett eller to ord tillatt";
				break;
			case TestWindow.studentNrRegex :
				feilmelding = "Kun på formen S123456";
				break;
			case TestWindow.årRegex :
				feilmelding = "Kun på formen 2010";
				break;
			case TestWindow.mobRegex :
				feilmelding = "Kun 8 siffer tillatt";
				break;
			case TestWindow.mailRegex :
				feilmelding = "Feil epost-format";
				break;
			case TestWindow.navnRegex :
				feilmelding = "Fornavn og etternavn";
				break;
			case TestWindow.dateRegex :
				feilmelding = "Kun på formen dd.mm.åå, eller dd.mm.åååå";
				break;
			case TestWindow.sPoengRegex:
				feilmelding = "Kun tall mellom 0-60";
				break;
			default:
				feilmelding = "Feil inputformat";
			}
			setForeground(Color.RED);
			setText(feilmelding);
		} 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
