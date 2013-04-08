package no.HiOAProsjektV2013.Interface;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class TestWindow extends JFrame implements ActionListener{
	
	public static void main(String[] args){
		TestWindow tw = new TestWindow();
		tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea info;
	private JButton nystudent, nylærer, nyttfag, nyttstudieprog, lagre, tilbake, legginnfag;
	private JTextField navn, epost, tlf, adresse, studentnummer, start, kontorNr, fagkode, beskrivelse, studiepoeng, vurderingsform, lærer;
	private Skole skolen;
	private Iterator<Laerer> iterator;
	private Date testDato;
	private JPanel c, stud, lær, fag, studprog;
	private Dimension knapp;
	
	public TestWindow(){
		
		super("Registrering");
		
		skolen = new Skole();
		
		knapp = new Dimension(260, 25);

		åpningsvindu();
		
		setLayout(new FlowLayout());
		setSize(400,700);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);

		lagre = new JButton("Lagre");
		lagre.setPreferredSize(knapp);
		lagre.addActionListener(this);
		
		tilbake = new JButton("Tilbake");
		tilbake.setPreferredSize(knapp);
		tilbake.addActionListener(this);
	}
	
	public void åpningsvindu(){
		
		nystudent = new JButton("Legg til student");
		nylærer = new JButton("Legg til lærer");
		nyttfag = new JButton("Legg til fag");
		nyttstudieprog = new JButton("Legg til studieprogram");
		
		nystudent.setPreferredSize(knapp);
		nylærer.setPreferredSize(knapp);
		nyttfag.setPreferredSize(knapp);
		nyttstudieprog.setPreferredSize(knapp);
		
		nystudent.addActionListener(this);
		nylærer.addActionListener(this);
		nyttfag.addActionListener(this);
		nyttstudieprog.addActionListener(this);
		
		JPanel c = new JPanel();
		c.add(nystudent);
		c.add(nylærer);
		c.add(nyttfag);
		c.add(nyttstudieprog);
		setContentPane(c);
		revalidate();
		
	}
	
	public void student(){
		stud = new JPanel();
		
		info = new JTextArea(20,25);
		
		navn = new JTextField("navn", 20);
		epost = new JTextField("e-post", 20);
		tlf = new JTextField("tlf", 20);
		adresse = new JTextField("adresse", 20);
		studentnummer = new JTextField("studnr", 20);
		start = new JTextField("startdato", 20);

		lagre = new JButton("Lagre");
		lagre.setPreferredSize(knapp);
		lagre.addActionListener(this);
		
		stud.add(info);
		stud.add(navn);
		stud.add(epost);
		stud.add(tlf);
		stud.add(adresse);
		stud.add(studentnummer);
		stud.add(start);
		stud.add(lagre);
		stud.add(tilbake);

		setContentPane(stud);
		revalidate();
	}
	
	public void lærer(){
		lær = new JPanel();
		
		info = new JTextArea(20,25);
		
		navn = new JTextField("navn", 20);
		epost = new JTextField("e-post", 20);
		tlf = new JTextField("tlf", 20);
		kontorNr = new JTextField("kontorNr", 20);
		
		lær.add(info);
		lær.add(navn);
		lær.add(epost);
		lær.add(tlf);
		lær.add(kontorNr);
		lær.add(lagre);
		lær.add(tilbake);

		setContentPane(lær);
		revalidate();
	}
	
	public void fag(){
		fag = new JPanel();
		
		info = new JTextArea(20,25);
		
		navn = new JTextField("navn", 20);
		fagkode = new JTextField("fagkode", 20);
		beskrivelse = new JTextField("beskrivelse", 20);
		vurderingsform = new JTextField("vurderingsform", 20);
		studiepoeng = new JTextField("studiepoeng", 20);
		lærer = new JTextField("lærer", 20);

		fag.add(info);
		fag.add(navn);
		fag.add(fagkode);
		fag.add(beskrivelse);
		fag.add(studiepoeng);
		fag.add(vurderingsform);
		fag.add(lærer);
		fag.add(lagre);
		fag.add(tilbake);

		setContentPane(fag);
		revalidate();
	}
	
	public void studieprog(){
		studprog = new JPanel();
		
		info = new JTextArea(20,25);
		
		navn = new JTextField("navn", 20);
		fagkode = new JTextField("fagkode", 20);
		
		legginnfag = new JButton("Legg til fag");
		legginnfag.setPreferredSize(knapp);
		legginnfag.addActionListener(this);
		
		studprog.add(info);
		studprog.add(navn);
		studprog.add(fagkode);
		studprog.add(legginnfag);
		studprog.add(lagre);
		studprog.add(tilbake);

		setContentPane(studprog);
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nystudent){
			student();
		}
		if(e.getSource() == nylærer){
			lærer();
		}
		if(e.getSource() == nyttfag){
			fag();
		}
		if(e.getSource() == nyttstudieprog){
			studieprog();
		}
		if(e.getSource() == tilbake){
			åpningsvindu();
		}
		if(e.getSource() == lagre){
			if(getContentPane() == stud){
				
				int nr = Integer.parseInt(tlf.getText());
				DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
				try {
					Date date = (Date)formatter.parse(start.getText());
					skolen.getStudentene().addStudent(navn.getText(), epost.getText(), nr, adresse.getText(), date);
				}
				catch (ParseException pe){
					info.setText("Feil datoformat");
				}
			}
			else if(getContentPane() == lær){
				int nr = Integer.parseInt(tlf.getText());
				skolen.getLærerne().add(new Laerer(navn.getText(), epost.getText(), nr, kontorNr.getText()));
			}
			else if(getContentPane() == fag){
				int poeng = Integer.parseInt(studiepoeng.getText());
				Laerer læreren = new Laerer("Eva Vihovde", "Lærern@hioa.no", 95113342, "PS230");
				skolen.getFagene().addFag(new Fag(navn.getText(), fagkode.getText(), beskrivelse.getText(), vurderingsform.getText(), poeng, læreren));
			}
			else if(getContentPane() == studprog){
				skolen.getStudieprogrammene().add(new Studieprogram(navn.getText()));
			}
		}
		if(e.getSource() == legginnfag){
			//skolen.getFagene().finnFagByFagkode(fagkode.getText());
		}
	}
}
