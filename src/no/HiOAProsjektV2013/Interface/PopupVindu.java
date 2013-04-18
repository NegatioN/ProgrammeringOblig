package no.HiOAProsjektV2013.Interface;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class PopupVindu {

	private JFrame vindu;
	private Dimension size = new Dimension(300, 350);
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode, beskrivelse, studiepoeng, vurderingsform, lærer;
	private JPanel panelet;
	private lytter ly = new lytter();
	private Buttons button = new Buttons(ly);
	private Object aktiv;

	public PopupVindu(JFrame ramme, Object o){
		vindu = new JFrame("Info-display");	
		
		if(o instanceof Student)
			vindu.add(generateWindow((Student) o));		
		else if(o instanceof Laerer)
			vindu.add(generateWindow((Laerer) o));		
		else if(o instanceof Fag)
			vindu.add(generateWindow((Fag) o));	
		else if(o instanceof Studieprogram)
			vindu.add(generateWindow((Studieprogram) o));	
		
		vindu.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		vindu.setSize(size);
		vindu.setResizable(false);
		vindu.setLocationRelativeTo(ramme);
		vindu.setVisible(true);
	}
	
	/*public Component generateWindow(JList liste){
		panelet = new JPanel();
		
		navn	 		= new JTextField("navn", 20);
		epost	 		= new JTextField("e-post", 20);
		tlf		 		= new JTextField("tlf", 20);
		adresse			= new JTextField("adresse", 20);
		start			= new JTextField("startdato", 20);
		kontorNr		= new JTextField("kontorNr", 20);
		fagkode			= new JTextField("fagkode", 20);
		beskrivelse		= new JTextField("beskrivelse", 20);
		vurderingsform	= new JTextField("vurderingsform", 20);
		studiepoeng		= new JTextField("studiepoeng", 20);
		lærer			= new JTextField("lærer", 20);
		
		
		return panelet;
	}*/
	//trenger ikke ta inn lista som parameter, kan hentes fra objeketet.
	
	public Component generateWindow(Student s){
		aktiv = s;
		
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		String studNavn = s.getfNavn() +" "+ s.geteNavn();
		String studEpost = s.getEpost();
		String studTlf = s.getTelefonNr() + "";
		String studAdresse = s.getAdresse();
		Date startdato = s.getStart();
		
//		Arbeidskrav[] fagene = s.getFagene();
//		JList<Arbeidskrav> liste = new JList<>();
//		liste.setListData(fagene);
		
		navn	 		= new JTextField(studNavn, 20);
		epost	 		= new JTextField(studEpost, 20);
		tlf		 		= new JTextField(studTlf, 20);
		adresse			= new JTextField(studAdresse, 20);
		start			= new JTextField(formatter.format(startdato), 20);
		
		navn.setEditable(false);
		epost.setEditable(true);
		tlf.setEditable(true);
		start.setEditable(false);

		panelet = new JPanel();
		panelet.setSize(size);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
		button.generateButton("Lagre", panelet, Buttons.HEL);
//		panelet.add(liste);
		
		return panelet;
	}
	public Component generateWindow(Laerer l){
		aktiv = l;
		
		String n = l.getfNavn() +" "+ l.geteNavn();
		String e = l.getEpost();
		String t = l.getTelefonNr() + "";
		String k = l.getKontor();
		
		navn	 		= new JTextField(n, 20);
		epost	 		= new JTextField(e, 20);
		tlf		 		= new JTextField(t, 20);
		kontorNr		= new JTextField(k, 20);
		
		navn.setEditable(false);
		epost.setEditable(true);
		tlf.setEditable(true);
		kontorNr.setEditable(true);

		panelet = new JPanel();
		panelet.setSize(size);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(kontorNr);
		button.generateButton("Lagre", panelet, Buttons.HEL);
		
		return panelet;
	}
	public Component generateWindow(Fag f){
		aktiv = f;
		
		String n = f.getNavn();
		String fk = f.getFagkode();
		String b = f.getBeskrivelse() + "";
		int sp = f.getStudiepoeng();
		String vf = f.getVurderingsform();
		String l = f.getLærer().getfNavn() +" "+ f.getLærer().geteNavn();
		
		navn	 		= new JTextField(n, 20);
		fagkode	 		= new JTextField(fk, 20);
		beskrivelse		= new JTextField(b, 20);
		studiepoeng		= new JTextField(""+sp, 20);
		vurderingsform	= new JTextField(vf, 20);
		lærer			= new JTextField(l, 20);
		
		
		navn			.setEditable(true);
		fagkode			.setEditable(false);
		beskrivelse		.setEditable(true);
		studiepoeng		.setEditable(true);
		vurderingsform	.setEditable(true);
		lærer			.setEditable(false);

		panelet = new JPanel();
		panelet.setSize(size);
		panelet.add(navn);
		panelet.add(fagkode);
		panelet.add(beskrivelse);
		panelet.add(studiepoeng);
		panelet.add(vurderingsform);
		panelet.add(lærer);

		button.generateButton("Lagre", panelet, Buttons.HEL);
		
		return panelet;
	}
	
	public Component generateWindow(Studieprogram sp){
		aktiv = sp;
		
		String n = sp.getNavn();
		
		navn	 		= new JTextField(n, 20);

		navn.setEditable(false);
		
		panelet = new JPanel();
		panelet.setSize(size);
		panelet.add(navn);
		
		button.generateButton("Lagre", panelet, Buttons.HEL);
		
		return panelet;
	}
	
	private class lytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(aktiv instanceof Student){
				((Student) aktiv).setAdresse(adresse.getText());
			} else if(aktiv instanceof Laerer){
				
			} else if(aktiv instanceof Fag){
				
			} else if(aktiv instanceof Studieprogram){

			}
		}
	}
	
}
