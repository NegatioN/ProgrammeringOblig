package no.HiOAProsjektV2013.Interface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

//klassen genererer knapper for hovedvinduet, og legger til en actionlistener + størrelse på knappen.
//for å unngå kodeduplisering
public class Buttons implements ActionListener{

	public static final Dimension HALV = new Dimension(160, 30);
	public static final Dimension HEL = new Dimension(260, 30);
	public static final int KORT = 12, LANG = 20;
	private ActionListener al;
	//fill med button-opprettelseting.
	public Buttons( ActionListener al){
		this.al = al;
	}
	public JButton generateButton(String name, JPanel panelet, Dimension size, ActionListener actionListener){
		JButton knapp = new JButton(name);
		knapp.setPreferredSize(size);
		knapp.addActionListener(actionListener);
		panelet.add(knapp);
		return knapp;
	}
	public JButton generateButton(String name, Dimension size, ActionListener actionListener){
		JButton knapp = new JButton(name);
		knapp.setPreferredSize(size);
		knapp.addActionListener(actionListener);
		return knapp;
	}
	public JButton generateButton(String name, JPanel panelet, Dimension size){
		JButton knapp = new JButton(name);
		knapp.setPreferredSize(size);
		knapp.addActionListener(al);
		panelet.add(knapp);
		return knapp;
	}
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
}
