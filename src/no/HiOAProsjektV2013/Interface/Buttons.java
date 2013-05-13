package no.HiOAProsjektV2013.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.ImageObserver;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//klassen genererer knapper for hovedvinduet, og legger til en actionlistener + størrelse på knappen.
//for å unngå kodeduplisering
public class Buttons implements ActionListener, FocusListener{

	public static final Dimension HALV = new Dimension(160, 30);
	public static final Dimension HEL = new Dimension(260, 30);
	public static final Dimension ICONBUTTON = new Dimension(64,64);
	public static final int KORT = 12, LANG = 20;
	private ActionListener al;
	//fill med button-opprettelseting.
	public Buttons( ActionListener al){
		this.al = al;
	}
	//lager knapp med alle inputvalues binder til actionlistener
	public JButton generateButton(String name, JPanel panelet, Dimension size, ActionListener actionListener){
		JButton knapp = new JButton(name);
		knapp.setPreferredSize(size);
		knapp.addActionListener(actionListener);
		panelet.add(knapp);
		return knapp;
	}
	//lager knapp med alle inputvalues binder til actionlistener
	public JButton generateButton(String name, Dimension size, ActionListener actionListener){
		JButton knapp = new JButton(name);
		knapp.setPreferredSize(size);
		knapp.addActionListener(actionListener);
		return knapp;
	}
	//lager knapp med inputvalues.
	public JButton generateButton(String name, JPanel panelet, Dimension size){
		JButton knapp = new JButton(name);
		knapp.setPreferredSize(size);
		knapp.addActionListener(al);
		panelet.add(knapp);
		return knapp;
	}
	//lager knapp med ikoner
	public JButton generateButton(String name, JPanel panelet, Dimension size, String qualifier, ImageIcon icon, ActionListener action){
		JButton knapp = new JButton(name,icon);
		
		//fikser icon	
		knapp.setVerticalTextPosition(AbstractButton.BOTTOM);
		knapp.setHorizontalTextPosition(AbstractButton.CENTER);
		knapp.setPreferredSize(size);
		//setter gjennomsiktig bakgrunn på knappen
		knapp.setOpaque(false);
		knapp.setContentAreaFilled(false);
		knapp.setBorderPainted(false);
		
		knapp.addActionListener(action);
		panelet.add(knapp,qualifier);
		return knapp;
	}
	//generisk knapp
	public JButton generateButton(String name, Dimension size){
		JButton knapp = new JButton(name);
		knapp.setPreferredSize(size);
		knapp.addActionListener(al);
		return knapp;
	}
	//kanskje kaste denne inn i testwindow pga at den må update selectedValue, og jeg er usikker på å ta inn et TestWindow her.
	//for valuePassing til søk
	public ButtonGroup generateButtonGroup(){
		 ButtonGroup group = new ButtonGroup();
		 JRadioButton lærer = new JRadioButton("Lærere");
		 JRadioButton student = new JRadioButton("Student");
		 JRadioButton fag = new JRadioButton("Fag");
		 JRadioButton studier = new JRadioButton("Studier");
		 
		 lærer.setActionCommand(TestWindow.LÆRER+"");
		 student.setActionCommand(TestWindow.STUDENT+"");
		 fag.setActionCommand(TestWindow.FAG+"");
		 studier.setActionCommand(TestWindow.STUDIEPROGRAM+"");
		 
		 lærer.addActionListener(this);
		 student.addActionListener(this);
		 fag.addActionListener(this);
		 studier.addActionListener(this);
		 
		 student.setSelected(true);
		 
		 group.add(student);
		 group.add(lærer);
		 group.add(fag);
		 group.add(studier);
		 
		 return group;
	}

	public JTextField generateTextField(String tekst, int bredde){
		JTextField felt = new JTextField(tekst, bredde);
		felt.setName(tekst);
		felt.addFocusListener(this);
		return felt;
	}
	public JTextField generateTextField(String tekst, int bredde, ActionListener al){
		JTextField felt = new JTextField(tekst, bredde);
		felt.setName(tekst);
		felt.addFocusListener(this);
		return felt;
	}
	public JTextField generateTextField(String tekst, int bredde, Boolean editable){
		JTextField felt = new JTextField(tekst, bredde);
		felt.setName(tekst);
		felt.setEditable(editable);
		if(editable)
			felt.addFocusListener(this);
		return felt;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//try trengs egentlig ikke siden vi ALLTID sender tall i stringen.
		try{
		int passValue = Integer.parseInt(e.getActionCommand());
//		tw.setSelectedValue(passValue);
		}catch(NumberFormatException ne){
			ne.printStackTrace();
		}
	}
	public void focusGained(FocusEvent e) {
		JTextField fokus = (JTextField)(e.getSource());
		if(fokus.getText().equals(fokus.getName()))
			fokus.setText("");
	}
	public void focusLost(FocusEvent e) {
		JTextField fokus = (JTextField)(e.getSource());
		if(fokus.getText().equals(""))
			fokus.setText(fokus.getName());
	}
}
