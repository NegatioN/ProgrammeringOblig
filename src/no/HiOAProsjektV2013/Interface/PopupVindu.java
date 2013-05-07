package no.HiOAProsjektV2013.Interface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import no.HiOAProsjektV2013.DataStructure.Arbeidskrav;
import no.HiOAProsjektV2013.DataStructure.Eksamen;
import no.HiOAProsjektV2013.DataStructure.EksamensDeltaker;
import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Krav;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class PopupVindu extends JPanel{

	private static final long serialVersionUID = 1073L;
	private Dimension venstreSize = new Dimension(290, 600), høyreSize = new Dimension(400, 600), infoSize = new Dimension(400, 250);
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode, beskrivelse, studiepoeng, vurderingsform, fag, eksamensdato;
	private JPanel panelet, visepanel, faginfo, kravinfo;
	private lytter ly = new lytter();
	private Buttons button = new Buttons(ly);
	private Object aktiv;
	private Skole skolen;
	private JButton leggtil, fjern, oppmeldte, lagreKrav, visFag, visEksamen, visKrav, tilbake;
	private JComboBox<Fag> velgFag, studentFag;
	private JComboBox<Laerer> velgLærer;
	private JComboBox<Eksamen> velgEksamen;
	private TestWindow vindu;
	private JCheckBox oppmeldtCheck;
	private Arbeidskrav aktivKrav;

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

		setPreferredSize(venstreSize);
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
		Date startdato = s.getStart().getTime();

		navn	 		= new JTextField(studNavn, 20);
		epost	 		= new JTextField(studEpost, 20);
		tlf		 		= new JTextField(studTlf, 20);
		adresse			= new JTextField(studAdresse, 20);
		start			= new JTextField(formatter.format(startdato), 20);

		navn.setEditable(false);
		start.setEditable(false);

		panelet = new JPanel();
		panelet.setPreferredSize(venstreSize);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
		visFag = button.generateButton("Vis fag", panelet, Buttons.HEL);
		button.generateButton("Lagre", panelet, Buttons.HEL);

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
		panelet.setPreferredSize(venstreSize);
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

		navn	 		= new JTextField(n, 20);
		fagkode	 		= new JTextField(fk, 20);
		beskrivelse		= new JTextField(b, 20);
		studiepoeng		= new JTextField(""+sp, 20);
		vurderingsform	= new JTextField(vf, 20);
		eksamensdato 	= new JTextField("Eksamensdato", 20);
		beskrivelse		= new JTextField("Arbeidskrav", 20);

		Laerer[] lærerA = new Laerer[skolen.getLærerne().visAlle().size()];
		skolen.getLærerne().visAlle().toArray(lærerA);
		velgLærer = new JComboBox<Laerer>(lærerA);
		velgLærer.setSelectedItem(f.getLærer());
		velgLærer.setPreferredSize(Buttons.HEL);

		navn			.setEditable(false);
		fagkode			.setEditable(false);
		studiepoeng		.setEditable(false);

		panelet = new JPanel();
		panelet.setPreferredSize(venstreSize);
		panelet.add(navn);
		panelet.add(fagkode);
		panelet.add(beskrivelse);
		panelet.add(studiepoeng);
		panelet.add(vurderingsform);
		panelet.add(velgLærer);

		visEksamen = button.generateButton("Vis Eksamener", panelet, Buttons.HEL);
		visKrav = button.generateButton("Vis Arbeidskrav", panelet, Buttons.HEL);
		
		button.generateButton("Lagre", panelet, Buttons.HEL);

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
		panelet.setPreferredSize(venstreSize);
		panelet.add(navn);
		panelet.add(fag);
		panelet.add(velgFag);

		button.generateButton("Lagre", panelet, Buttons.HEL);
		leggtil = button.generateButton("Legg til fag", panelet, Buttons.HEL);
		fjern = button.generateButton("Fjern fag", panelet, Buttons.HEL);
		return panelet;
	}

	public void visFag(Fag f){
		faginfo.removeAll();
		faginfo.setBorder(BorderFactory.createTitledBorder(f.getNavn()));
		
		aktivKrav = ((Student)aktiv).getFagKrav(f);
		
		checklytter cl = new checklytter();
		for(Krav krav : aktivKrav.getKrav()){
			JCheckBox cb = new JCheckBox(krav.getBeskrivelse());
			faginfo.add(cb);
			cb.setSelected(krav.isGodkjent());
			cb.addItemListener(cl);
		}
		oppmeldtCheck = new JCheckBox("Oppmeldt til eksamen");
		oppmeldtCheck.setSelected(((Student)aktiv).innfriddKrav(f));
		faginfo.add(oppmeldtCheck);
		faginfo.updateUI();
	}
	public void visFag(){
		faginfo.removeAll();
		if(studentFag == null || studentFag.getSelectedIndex() == -1)
			faginfo.setBorder(BorderFactory.createTitledBorder("Ingen fag"));
		else
			visFag((Fag)studentFag.getSelectedItem());
			
		faginfo.updateUI();
	}
	public JPanel fagPanel(){
		visepanel = new JPanel();
		visepanel.setPreferredSize(høyreSize);

		faginfo = new JPanel(new GridLayout(5,1));
		faginfo.setPreferredSize(infoSize);

		combolytter cl = new combolytter();
		Fag[] fagB = new Fag[((Student) aktiv).getfagListe().size()];
		((Student) aktiv).getfagListe().toArray(fagB);
		studentFag = new JComboBox<Fag>(fagB);
		studentFag.setPreferredSize(Buttons.HEL);
		studentFag.addItemListener(cl);

		Fag[] fagA = new Fag[skolen.getFagene().visAlle().size()];
		skolen.getFagene().visAlle().toArray(fagA);
		velgFag = new JComboBox<Fag>(fagA);
		velgFag.setPreferredSize(Buttons.HEL);

		visepanel.add(studentFag);

		if(fagB.length > 0)
			visFag(fagB[0]);
		else
			visFag();

		visepanel.add(faginfo);		
		fjern = button.generateButton("Fjern fag", visepanel, Buttons.HEL);

		visepanel.add(velgFag);
		leggtil = button.generateButton("Legg til fag", visepanel, Buttons.HEL);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);

		return visepanel;
	}

	public JPanel eksamensPanel(){
		visepanel = new JPanel();
		visepanel.setPreferredSize((høyreSize));
		
		faginfo = new JPanel();
		faginfo.setPreferredSize(infoSize);
		
		combolytter cl = new combolytter();
		Eksamen[] eks = new Eksamen[((Fag)aktiv).getEksamener().size()];
		((Fag)aktiv).getEksamener().toArray(eks);
		
		velgEksamen = new JComboBox<Eksamen>(eks);
		velgEksamen.setPreferredSize(Buttons.HEL);
		velgEksamen.addItemListener(cl);
		
		visepanel.add(velgEksamen);
		if(eks.length > 0)
			visEksamen(eks[0]);
		else
			visFag();
		
		visepanel.add(faginfo);
		oppmeldte = button.generateButton("Legg til Oppmeldte", visepanel, Buttons.HEL);
		visepanel.add(eksamensdato);
		
		leggtil = button.generateButton("Legg til Eksamen", visepanel, Buttons.HEL);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);
		
		visepanel.updateUI();
		return visepanel;
	}
	public void visEksamen(Eksamen e){
		faginfo.removeAll();
		faginfo.setBorder(BorderFactory.createTitledBorder("Eksamen for " + ((Fag)aktiv).getNavn()));

		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for dato

		String[] kolonnenavn = {"Dato", "StudentNr", "Karakter"};
		Object[][] celler = new Object[e.getDeltakere().size()][3];
		
		int rad = 0;
		for(EksamensDeltaker ed: e.getDeltakere()){
			celler[rad][0] = formatter.format(e.getDato());
			celler[rad][1] = ed.getDeltaker();
			celler[rad++][2] = ed.getKarakter();
		}

		JTable resultater = new JTable(celler, kolonnenavn);
		resultater.setPreferredScrollableViewportSize(new Dimension(390, 200));
		faginfo.add(new JScrollPane(resultater));
		faginfo.updateUI();
	}
	
	public JPanel kravPanel(Fag f){
		visepanel = new JPanel();
		visepanel.setPreferredSize(høyreSize);

		aktivKrav = f.getKrav();

		kravinfo = new JPanel();
		kravinfo.setPreferredSize(infoSize);
		kravinfo.setBorder(BorderFactory.createTitledBorder("Arbeidskrav for " + (f.getNavn())));
		
		for(Krav k : f.getKrav().getKrav()){
			kravinfo.add(new JLabel(k.getBeskrivelse()));
		}

		visepanel.add(kravinfo);		
		fjern = button.generateButton("Fjern krav", visepanel, Buttons.HEL);
		
		beskrivelse = new JTextField("Nytt arbeidskrav");
		
		lagreKrav = button.generateButton("Lagre krav", visepanel, Buttons.HEL);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);

		return visepanel;
	}

	private class combolytter implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if(e.getSource() == studentFag){
					visFag((Fag)e.getItem());
				} else if(e.getSource() == velgEksamen) {
					visEksamen((Eksamen)e.getItem());
				}
			}
		}       
	}
	private class checklytter implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				for(Krav k : aktivKrav.getKrav()){
					if( ((JCheckBox)e.getSource()).getText() == k.getBeskrivelse())
						k.setGodkjent(true);
				}
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				for(Krav k : aktivKrav.getKrav()){
					if( ((JCheckBox)e.getSource()).getText() == k.getBeskrivelse())
						k.setGodkjent(false);
				}
			}
			visFag();
		}       
	}
	private class lytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == leggtil) {
				try{
					if(aktiv instanceof Student){
						Fag f = (Fag)velgFag.getSelectedItem();
						((Student) aktiv).addFag(f);
						vindu.cover(fagPanel());
						visFag(f);
					}
					else if(aktiv instanceof Studieprogram){
						Studieprogram studiet = (Studieprogram) aktiv;
						studiet.addFag((Fag)velgFag.getSelectedItem());
					}
					else if(aktiv instanceof Fag){
						try {
							DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for dato
							Date date = (Date) formatter.parse(eksamensdato.getText());
							((Fag)aktiv).addEksamen(date);
							eksamensdato.setText("Eksamen lagret");
							vindu.cover(eksamensPanel());
						} catch (ParseException pe) {
							eksamensdato.setText("Feil datoformat");
						}
					}
				} catch (NullPointerException npe){
					System.out.println("Nullpointerfeil");
				}

			} else if(e.getSource() == fjern) {
				try{
					if(aktiv instanceof Student){
						((Student) aktiv).removeFag((Fag)studentFag.getSelectedItem());
						vindu.cover(fagPanel());
					}
					else if(aktiv instanceof Studieprogram)
						((Studieprogram) aktiv).fjernFag(((Fag)velgFag.getSelectedItem()));
					else if(aktiv instanceof Fag){
						
					}


				} catch (NullPointerException npe){
				}
			}
			
			else if(e.getSource() == oppmeldte){
				ArrayList<Student> studentliste = vindu.getSkole().getStudentene().visAlle();
				((Eksamen)velgEksamen.getSelectedItem()).addOppmeldteStudenter(studentliste, (Fag) aktiv);
				vindu.cover(eksamensPanel());
			}
			
			else if(e.getSource() == lagreKrav){
				vindu.cover(kravPanel((Fag) aktiv));
			} 
			
			else if(e.getSource() == tilbake){
				vindu.vis();
			} 
			else if(e.getSource() == visEksamen){
				vindu.cover(eksamensPanel());
			}
			else if(e.getSource() == visKrav){
				vindu.cover(kravPanel((Fag) aktiv));
			}
			else if(e.getSource() == visFag){
				vindu.cover(fagPanel());
			}
			else{
				vindu.display();
				vindu.vis();

				if(aktiv instanceof Student){
					Student s = (Student) aktiv;
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
			}
		}
	}
}
