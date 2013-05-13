package no.HiOAProsjektV2013.Interface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputFelt extends JTextField implements FocusListener, ActionListener{

	private static final long serialVersionUID = 101112L;
	private String regex = ".*";
	private String tekst, feilmelding;
	public static final int KORT = 12, LANG = 20;

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
		setEditable(editable);
		if(editable)
			addFocusListener(this);
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
