package no.HiOAProsjektV2013.Interface;
//Lars-Erik Kasin - s178816 - Dataingeniør - 1AA
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 14.05.13
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//klassen genererer knapper for hovedvinduet, og legger til en actionlistener + størrelse på knappen.
//for å unngå kodeduplisering
public class Buttons {

	public static final Dimension HALV = new Dimension(160, 30);
	public static final Dimension HEL = new Dimension(260, 30);
	public static final Dimension ICONBUTTON = new Dimension(64,64);
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
	public JButton generateButton(String name, JPanel panelet, Dimension size, String qualifier, ImageIcon icon,ImageIcon roll,ImageIcon press, ActionListener action){
		JButton knapp = new JButton(name,icon);

		//fikser icon	
		knapp.setPreferredSize(size);
		//setter gjennomsiktig bakgrunn på knappen
		knapp.setOpaque(false);
		knapp.setContentAreaFilled(false);
		knapp.setBorderPainted(false);
		//roll+pressed-state
		knapp.setRolloverEnabled(true);
		knapp.setRolloverIcon(roll);
		knapp.setPressedIcon(press);

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
}
