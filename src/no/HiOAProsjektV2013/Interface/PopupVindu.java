package no.HiOAProsjektV2013.Interface;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class PopupVindu extends JFrame{
	
	private static final long serialVersionUID = 1073L;
	private Dimension size = new Dimension(300, 350);
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode, beskrivelse, studiepoeng, vurderingsform, lærer, fag;
	private JPanel panelet;
	private lytter ly = new lytter();
	private Buttons button = new Buttons(ly);
	private Object aktiv;

	public PopupVindu(JFrame ramme, Object o){
		super("Info-display");	
		
		if(o instanceof Student)
			add(fyllVindu((Student) o));		
		else if(o instanceof Laerer)
			add(fyllVindu((Laerer) o));		
		else if(o instanceof Fag)
			add(fyllVindu((Fag) o));	
		else if(o instanceof Studieprogram)
			add(fyllVindu((Studieprogram) o));	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(size);
		setResizable(false);
		setLocationRelativeTo(ramme);
		setVisible(true);
	}
	
	public Component fyllVindu(Student s){
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
		//fag				= new JTextField(s.getFagene().toString());
		fagkode 		= new JTextField("Legg til fag med Enter", 20); //Skal bli jcombobox!
		
		fagkode.addActionListener(ly);
		
		navn.setEditable(false);
		start.setEditable(false);
		//fag.setEditable(false);

		panelet = new JPanel();
		panelet.setSize(size);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
		panelet.add(fagkode);
		button.generateButton("Lagre", panelet, Buttons.HEL);
//		panelet.add(liste);
		
		return panelet;
	}
	public Component fyllVindu(Laerer l){
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
	public Component fyllVindu(Fag f){
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
		
		
		navn			.setEditable(false);
		fagkode			.setEditable(false);
		studiepoeng		.setEditable(false);
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
	public Component fyllVindu(Studieprogram sp){
		aktiv = sp;
		
		String n = sp.getNavn();
		
		navn	 		= new JTextField(n, 20);
		fagkode 		= new JTextField(n, 20);
		//navn.setEditable(false);
		
		panelet = new JPanel();
		panelet.setSize(size);
		panelet.add(navn);
		
		button.generateButton("Lagre", panelet, Buttons.HEL);
		button.generateButton("Legg til fag", panelet, Buttons.HEL);
		return panelet;
	}
	
	private class lytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(aktiv instanceof Student){
				if(e.getSource() == fagkode){
					System.out.println("Yey");
					return;
				}
				try{
					int nr = Integer.parseInt(tlf.getText());
					
					((Student) aktiv).setAdresse(adresse.getText());
					((Student) aktiv).setTlf(nr);
					((Student) aktiv).setEpost(epost.getText());
					
				}catch (NumberFormatException nfe){
					System.out.println("Feil Nummerformat");
				}
			} else if(aktiv instanceof Laerer){
				try{
					int nr = Integer.parseInt(tlf.getText());
					
					((Laerer) aktiv).setTlf(nr);
					((Laerer) aktiv).setEpost(epost.getText());
					((Laerer) aktiv).setKontor(kontorNr.getText());

				}catch (NumberFormatException nfe){
					System.out.println("Feil Nummerformat");
				}
			} else if(aktiv instanceof Fag){
				((Fag) aktiv).setBeskrivelse(beskrivelse.getText());
				((Fag) aktiv).setVurderingsform(vurderingsform.getText());
				//((Fag) aktiv).setLærer(lærer.getText());
				
			} else if(aktiv instanceof Studieprogram){
				((Studieprogram) aktiv).setNavn(navn.getText());
			}
			dispose();
		}
	}
	
}
