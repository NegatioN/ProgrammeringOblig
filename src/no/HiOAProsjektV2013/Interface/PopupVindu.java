package no.HiOAProsjektV2013.Interface;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class PopupVindu extends JPanel{
	
	private static final long serialVersionUID = 1073L;
	private Dimension size = new Dimension(290, 390);
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode, beskrivelse, studiepoeng, vurderingsform, fag, eksamensdato;
	private JPanel panelet;
	private lytter ly = new lytter();
	private Buttons button = new Buttons(ly);
	private Object aktiv;
	private Skole skolen;
	private JButton leggtil, fjernFag, slett, eksamen;
	private JComboBox<Fag> velgFag;
	private JComboBox<Laerer> velgLærer;
	private TestWindow vindu;
	
	public PopupVindu(TestWindow vindu, Object o, Skole skolen){
		this.vindu = vindu;
		this.skolen = skolen;
		
		if(o instanceof Student)
			add(fyllVindu((Student) o));		
		else if(o instanceof Laerer)
			add(fyllVindu((Laerer) o));		
		else if(o instanceof Fag)
			add(fyllVindu((Fag) o));	
		else if(o instanceof Studieprogram)
			add(fyllVindu((Studieprogram) o));	
		
		setPreferredSize(size);
		setVisible(true);
		vindu.display(this);
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
		
		Fag[] fagA = new Fag[skolen.getFagene().visAlle().size()];
		skolen.getFagene().visAlle().toArray(fagA);
		velgFag = new JComboBox<Fag>(fagA);
		velgFag.setPreferredSize(Buttons.HEL);
		
		navn.setEditable(false);
		start.setEditable(false);
		//fag.setEditable(false);

		panelet = new JPanel();
		panelet.setPreferredSize(size);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
		panelet.add(velgFag);
		button.generateButton("Lagre", panelet, Buttons.HEL);
//		panelet.add(liste);
		
		leggtil = button.generateButton("Legg til fag", panelet, Buttons.HEL);
		fjernFag = button.generateButton("Fjern fag", panelet, Buttons.HEL);
		slett = button.generateButton("Slett Student", panelet, Buttons.HEL);

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
		panelet.setPreferredSize(size);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(kontorNr);
		button.generateButton("Lagre", panelet, Buttons.HEL);
		slett = button.generateButton("Slett lærer", panelet, Buttons.HEL);

		return panelet;
	}
	public Component fyllVindu(Fag f){
		aktiv = f;
		
		String n = f.getNavn();
		String fk = f.getFagkode();
		String b = f.getBeskrivelse() + "";
		int sp = f.getStudiepoeng();
		String vf = f.getVurderingsform();
		
		navn	 		= new JTextField(n, 20);
		fagkode	 		= new JTextField(fk, 20);
		beskrivelse		= new JTextField(b, 20);
		studiepoeng		= new JTextField(""+sp, 20);
		vurderingsform	= new JTextField(vf, 20);
		eksamensdato 	= new JTextField("eksamensdato", 20);
		eksamensdato.addActionListener(ly);
		
		Laerer[] lærerA = new Laerer[skolen.getLærerne().visAlle().size()];
		skolen.getLærerne().visAlle().toArray(lærerA);
		velgLærer = new JComboBox<Laerer>(lærerA);
		velgLærer.setSelectedItem(f.getLærer());
		velgLærer.setPreferredSize(Buttons.HEL);

		navn			.setEditable(false);
		fagkode			.setEditable(false);
		studiepoeng		.setEditable(false);

		panelet = new JPanel();
		panelet.setPreferredSize(size);
		panelet.add(navn);
		panelet.add(fagkode);
		panelet.add(beskrivelse);
		panelet.add(studiepoeng);
		panelet.add(vurderingsform);
		panelet.add(velgLærer);
		
		button.generateButton("Lagre", panelet, Buttons.HEL);
		eksamen = button.generateButton("Legg til eksamen", panelet, Buttons.HEL);
		slett = button.generateButton("Slett fag", panelet, Buttons.HEL);

		return panelet;
	}
	public Component fyllVindu(Studieprogram sp){
		aktiv = sp;
		
		String n = sp.getNavn();
		
		String fagene = "";
		for(Fag f : sp.getFagene()){
			if(fagene != "")
				fagene += ", ";
			fagene += f.getNavn();
		}
		
		Fag[] fagA = new Fag[skolen.getFagene().visAlle().size()];
		skolen.getFagene().visAlle().toArray(fagA);
		velgFag = new JComboBox<Fag>(fagA);
		velgFag.setPreferredSize(Buttons.HEL);
		
		navn	 		= new JTextField(n, 20);
		fag		 		= new JTextField(fagene, 20);
		
		//navn.setEditable(false);
		fag.setEditable(false);

		panelet = new JPanel();
		panelet.setPreferredSize(size);
		panelet.add(navn);
		panelet.add(fag);
		panelet.add(velgFag);
		
		
		button.generateButton("Lagre", panelet, Buttons.HEL);
		leggtil = button.generateButton("Legg til fag", panelet, Buttons.HEL);
		fjernFag = button.generateButton("Fjern fag", panelet, Buttons.HEL);
		slett = button.generateButton("Slett studieprogram", panelet, Buttons.HEL);
		return panelet;
	}
	
	private class lytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == leggtil) {
				try{
					if(aktiv instanceof Student)
						((Student) aktiv).addFag((Fag)velgFag.getSelectedItem());
					else if(aktiv instanceof Studieprogram)
						((Studieprogram) aktiv).addFag((Fag)velgFag.getSelectedItem());
					
				} catch (NullPointerException npe){
				}
			} else if(e.getSource() == fjernFag) {
				try{
					if(aktiv instanceof Student)
						((Student) aktiv).addFag((Fag)velgFag.getSelectedItem());
					else if(aktiv instanceof Studieprogram)
						((Studieprogram) aktiv).fjernFag(((Fag)velgFag.getSelectedItem()));
						
					} catch (NullPointerException npe){
				}
			} else if(e.getSource() == slett){
				if(aktiv instanceof Student)
					skolen.getStudentene().removeStudent((Student)aktiv);
				else if(aktiv instanceof Laerer)
					skolen.getLærerne().removeLærer((Laerer)aktiv);
				else if(aktiv instanceof Fag)
					skolen.getFagene().removeFag((Fag)aktiv);
				else if(aktiv instanceof Studieprogram)
					skolen.getStudieprogrammene().removeStudieprogram((Studieprogram)aktiv);
				
				vindu.setText(aktiv.toString() + " ble fjernet fra systemet.");
				vindu.display();
			} else if(e.getSource() == eksamen){
				panelet.remove(eksamen);
				panelet.remove(slett);
				panelet.add(eksamensdato);
				panelet.add(slett);
				panelet.revalidate();
			} else if(e.getSource() == eksamensdato){
				try {
					DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for dato
					Date date = (Date) formatter.parse(eksamensdato.getText());
					((Fag)aktiv).addEksamen(date);
					eksamensdato.setText("Eksamen lagret");
				} catch (ParseException pe) {
					eksamensdato.setText("Feil datoformat");
				}
				panelet.revalidate();
			}
			else{
				
			if(aktiv instanceof Student){
				Student s = (Student) aktiv;
				if(e.getSource() == fagkode){
					System.out.println("Yey");
					return;
				}
				try{
					int nr = Integer.parseInt(tlf.getText());
					
					s.setAdresse(adresse.getText());
					s.setTlf(nr);
					s.setEpost(epost.getText());
					
				}catch (NumberFormatException nfe){
					System.out.println("Feil Nummerformat");
				}
				vindu.setText(s.fullString());
			} else if(aktiv instanceof Laerer){
				Laerer l = (Laerer) aktiv;
				try{
					int nr = Integer.parseInt(tlf.getText());
					
					l.setTlf(nr);
					l.setEpost(epost.getText());
					l.setKontor(kontorNr.getText());

				}catch (NumberFormatException nfe){
					System.out.println("Feil Nummerformat");
				}
				vindu.setText(l.fullString());
			} else if(aktiv instanceof Fag){
				Fag f = (Fag) aktiv;
				f.setBeskrivelse(beskrivelse.getText());
				f.setVurderingsform(vurderingsform.getText());
				f.setLærer((Laerer)velgLærer.getSelectedItem());
				vindu.setText(f.fullString());
			} else if(aktiv instanceof Studieprogram){
				Studieprogram sp = (Studieprogram) aktiv;
				sp.setNavn(navn.getText());
				vindu.setText(sp.fullString());
			}
			vindu.display();
			}
	}
	}
}
