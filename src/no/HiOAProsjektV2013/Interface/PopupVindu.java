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
	private Dimension size = new Dimension(290, 390);
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode, beskrivelse, studiepoeng, vurderingsform, fag, eksamensdato, kravbeskrivelse;
	private JPanel panelet, visepanel, faginfo;
	private lytter ly = new lytter();
	private Buttons button = new Buttons(ly);
	private Object aktiv;
	private Skole skolen;
	private JButton leggtil, fjernFag, slett, oppmeldte, arbeidskrav, visFag, visEksamen, tilbake;
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

		navn	 		= new JTextField(studNavn, 20);
		epost	 		= new JTextField(studEpost, 20);
		tlf		 		= new JTextField(studTlf, 20);
		adresse			= new JTextField(studAdresse, 20);
		start			= new JTextField(formatter.format(startdato), 20);

		navn.setEditable(false);
		start.setEditable(false);

		panelet = new JPanel();
		panelet.setPreferredSize(size);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
		visFag = button.generateButton("Vis fag", panelet, Buttons.HEL);
		button.generateButton("Lagre", panelet, Buttons.HEL);
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
		eksamensdato 	= new JTextField("Eksamensdato", 20);
		kravbeskrivelse	= new JTextField("Arbeidskrav", 20);
		kravbeskrivelse.addActionListener(ly);

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
		visEksamen = button.generateButton("Vis eksamener", panelet, Buttons.HEL);
		arbeidskrav = button.generateButton("Legg til arbeidskrav", panelet, Buttons.HEL);
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
		visepanel.setPreferredSize(size);

		faginfo = new JPanel(new GridLayout(5,1));
		faginfo.setPreferredSize(new Dimension(260, 180));

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
		fjernFag = button.generateButton("Fjern fag", visepanel, Buttons.HEL);

		visepanel.add(velgFag);
		leggtil = button.generateButton("Legg til fag", visepanel, Buttons.HEL);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);

		return visepanel;
	}

	public JPanel eksamensPanel(){
		visepanel = new JPanel();
		visepanel.setPreferredSize(size);
		
		faginfo = new JPanel();
		faginfo.setPreferredSize(new Dimension(260, 210));
		
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
		resultater.setPreferredScrollableViewportSize(new Dimension(245, 160));
		faginfo.add(new JScrollPane(resultater));
		faginfo.updateUI();
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
						((Studieprogram) aktiv).addFag((Fag)velgFag.getSelectedItem());
					}
					else if(aktiv instanceof Fag){
						try {
							DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for dato
							Date date = (Date) formatter.parse(eksamensdato.getText());
							((Fag)aktiv).addEksamen(date);
							eksamensdato.setText("Eksamen lagret");
							vindu.display(eksamensPanel());
						} catch (ParseException pe) {
							eksamensdato.setText("Feil datoformat");
						}
					}
				} catch (NullPointerException npe){
					System.out.println("Nullpointerfeil");
				}

			} else if(e.getSource() == fjernFag) {
				try{
					if(aktiv instanceof Student){
						((Student) aktiv).removeFag((Fag)studentFag.getSelectedItem());
						vindu.display(fagPanel());
					}
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
			}
			
			else if(e.getSource() == arbeidskrav){
				panelet.remove(eksamensdato);
				panelet.remove(arbeidskrav);
				panelet.remove(slett);
				panelet.add(kravbeskrivelse);
				panelet.add(slett);
				panelet.updateUI();
			}
			
			else if(e.getSource() == oppmeldte){
				ArrayList<Student> studentliste = vindu.getSkole().getStudentene().visAlle();
				((Eksamen)velgEksamen.getSelectedItem()).addOppmeldteStudenter(studentliste, (Fag) aktiv);
				vindu.display(eksamensPanel());
			}
			
			else if(e.getSource() == kravbeskrivelse){
				if(kravbeskrivelse.getText() != "Arbeidskrav lagret")
					((Fag)aktiv).addKrav(kravbeskrivelse.getText());
				kravbeskrivelse.setText("Arbeidskrav lagret");
			} else if(e.getSource() == tilbake){
				if(aktiv instanceof Student)
					vindu.vis(); 
//					vindu.display(fyllVindu((Student)aktiv));
				else if(aktiv instanceof Fag){
					vindu.display(fyllVindu((Fag)aktiv));
				}
			}
			else{
				//vindu.display();

				if(aktiv instanceof Student){
					Student s = (Student) aktiv;
					if(e.getSource() == visFag){
						vindu.cover(fagPanel());
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
					if(e.getSource() == visEksamen){
						vindu.display(eksamensPanel());
					}
					
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
