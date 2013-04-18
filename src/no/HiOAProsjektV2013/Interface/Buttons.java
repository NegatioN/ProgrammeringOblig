package no.HiOAProsjektV2013.Interface;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

//klassen genererer knapper for hovedvinduet, og legger til en actionlistener + størrelse på knappen.
//for å unngå kodeduplisering
public class Buttons {

	public static final Dimension HALV = new Dimension(160, 30);
	public static final Dimension HEL = new Dimension(260, 30);

	private ActionListener al;
	//fill med button-opprettelseting.
	public Buttons(ActionListener al){
		this.al = al;
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
	
}
