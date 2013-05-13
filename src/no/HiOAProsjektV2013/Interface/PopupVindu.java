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
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import no.HiOAProsjektV2013.DataStructure.Arbeidskrav;
import no.HiOAProsjektV2013.DataStructure.Eksamen;
import no.HiOAProsjektV2013.DataStructure.EksamensDeltaker;
import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Krav;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;
import no.HiOAProsjektV2013.Interface.InputFelt;
import no.HiOAProsjektV2013.Main.DateHandler;

public class PopupVindu extends JPanel{

	private static final long serialVersionUID = 1073L;
	private Dimension venstreSize = new Dimension(290, 600), høyreSize = new Dimension(400, 600), infoSize = new Dimension(400, 250), tabellSize = new Dimension(385, 200);
	private InputFelt navn, epost, tlf, adresse, start, slutt, kontorNr, fagkode, beskrivelse, studiepoeng, vurderingsform, studentNr, fag, eksamensdato;
	private JPanel panelet, visepanel, faginfo, kravinfo;
	private TestWindow vindu;
	private lytter ly = new lytter();
	private Buttons button = new Buttons(ly);
	private Object aktiv;
	private JButton leggtil, fjern, oppmeldte, deltaker , lagreKrav, visFag, visEksamen, visKrav, tilbake;
	private JComboBox<Fag> velgFag, studentFag;
	private JComboBox<Laerer> velgLærer;
	private JComboBox<Studieprogram> velgProg;
	private JComboBox<Eksamen> velgEksamen;
	private combolytter cl = new combolytter();
	private JTable resultater;
	private JCheckBox oppmeldtCheck;
	private JList<Krav> kravListe;
	private Arbeidskrav aktivKrav;
	private DateFormat formatter = new SimpleDateFormat("dd.mm.yy"); //Setter inputformat for dato
	private RightClickMenus popup;
	private JComboBox<String> vurderingBox = null;
	private DateHandler dateHandler = new DateHandler();
	
	public PopupVindu(TestWindow vindu, Object o){
		this.vindu = vindu;
		popup = vindu.getRightClickMenu();
		
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
		System.out.println("PING JEG EKSISTERER");

		String studNavn = s.getfNavn() +" "+ s.geteNavn();
		String studEpost = s.getEpost();
		String studTlf = s.getTelefonNr() + "";
		String studAdresse = s.getAdresse();
		Date startdato = s.getStart().getTime();

		navn	 		= new InputFelt(studNavn, 20, false);
		epost	 		= new InputFelt(studEpost, 20, TestWindow.mailRegex);
		tlf		 		= new InputFelt(studTlf, 20, TestWindow.mobRegex);
		adresse			= new InputFelt(studAdresse, 20);
		start			= new InputFelt(formatter.format(startdato), 20, false);
		try{
			Date sluttdato = s.getSlutt().getTime();
			slutt 		= new InputFelt(formatter.format(sluttdato), 20, TestWindow.dateRegex);
		} catch(NullPointerException npe){
			slutt 		= new InputFelt("Sluttdato", 20, TestWindow.dateRegex);
		}

		velgFag = new JComboBox<Fag>();
		velgFag.setPreferredSize(Buttons.HEL);
		for(Fag f : vindu.getSkole().getFagene().visAlle()) {
			velgFag.addItem((Fag)f);
		}

		
		velgProg = new JComboBox<Studieprogram>();
		velgProg.setPreferredSize(Buttons.HEL);
		for(Studieprogram sp : vindu.getSkole().getStudieprogrammene().visAlle()) {
			velgProg.addItem(sp);
		}
		try{
			velgProg.setSelectedItem(s.getStudieprogram());
		} catch(NullPointerException npe){
			velgProg.setSelectedIndex(-1);
		}
		
		panelet = new JPanel();
		panelet.setPreferredSize(venstreSize);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
		panelet.add(slutt);
		panelet.add(velgProg);
		panelet.add(velgFag);
		leggtil = button.generateButton("Legg til fag", panelet, Buttons.HEL);
		panelet.add(Box.createRigidArea(Buttons.HEL));
		
		visFag = button.generateButton("Vis fag", panelet, Buttons.HEL);
		visEksamen = button.generateButton("Vis Eksamener", panelet, Buttons.HEL);
		button.generateButton("Lagre", panelet, Buttons.HEL);
		
		return panelet;
	}
	public Component fyllVindu(Laerer l){
		aktiv = l;

		String n = l.getfNavn() +" "+ l.geteNavn();
		String e = l.getEpost();
		String t = l.getTelefonNr() + "";
		String k = l.getKontor();

		navn	 		= new InputFelt(n, 20, false);
		epost	 		= new InputFelt(e, 20, TestWindow.mailRegex);
		tlf		 		= new InputFelt(t, 20, TestWindow.mobRegex);
		kontorNr		= new InputFelt(k, 20);

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

		navn	 		= new InputFelt(n, 20, false);
		fagkode	 		= new InputFelt(fk, 20, false);
		beskrivelse		= new InputFelt(b, 20, false);
		studiepoeng		= new InputFelt(""+sp, 20, "\\d{2}");
		vurderingsform	= new InputFelt(vf, 20, false);
		eksamensdato 	= new InputFelt("dag/mnd/år", 20, TestWindow.dateRegex);

		String[] boxitems =  {"Muntlig", "Skriftlig", "Prosjekt"};
		vurderingBox = new JComboBox<String>(boxitems);
		vurderingBox.setPreferredSize(Buttons.HEL);
		vurderingBox.setSelectedItem(f.getVurderingsform());
		
		velgLærer = new JComboBox<Laerer>();
		velgLærer.setSelectedItem(f.getLærer());
		velgLærer.setPreferredSize(Buttons.HEL);
		for(Laerer l : vindu.getSkole().getLærerne().visAlle()) {
			velgLærer.addItem((Laerer)l);
		}

		panelet = new JPanel();
		panelet.setPreferredSize(venstreSize);
		panelet.add(navn);
		panelet.add(fagkode);
		panelet.add(beskrivelse);
		panelet.add(studiepoeng);
		panelet.add(vurderingBox);
		panelet.add(velgLærer);
		panelet.add(eksamensdato);
		leggtil = button.generateButton("Legg til Eksamen", panelet, Buttons.HEL);
		panelet.add(Box.createRigidArea(Buttons.HEL));

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

		velgFag = new JComboBox<Fag>();
		velgFag.setPreferredSize(Buttons.HEL);
		for(Fag f : vindu.getSkole().getFagene().visAlle()) {
			velgFag.addItem((Fag)f);
		}

		navn	 		= new InputFelt(n, 20, TestWindow.tittelRegex);
		fag		 		= new InputFelt(fagene, 20, false);

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

	public JPanel fagPanel(){
		visepanel = new JPanel();
		visepanel.setPreferredSize(høyreSize);

		faginfo = new JPanel(new GridLayout(5,1));
		faginfo.setPreferredSize(infoSize);

		studentFag = new JComboBox<Fag>();
		studentFag.setPreferredSize(Buttons.HEL);
		studentFag.addItemListener(cl);
		for(Fag f : ((Student) aktiv).getfagListe()) {
			studentFag.addItem((Fag)f);
		}

		visepanel.add(studentFag);

		if(studentFag.getItemCount() > 0)
			visFag(studentFag.getItemAt(TestWindow.FØRSTE));
		else
			visFag();

		visepanel.add(faginfo);		
		fjern = button.generateButton("Fjern fag", visepanel, Buttons.HEL);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);

		return visepanel;
	}
	public JPanel kravPanel(Fag f){
		visepanel = new JPanel();
		visepanel.setPreferredSize(høyreSize);

		aktivKrav = f.getKrav();

		DefaultListModel<Krav> modell = new DefaultListModel<Krav>();  
		kravListe = new JList<>(modell);
		kravListe.setPreferredSize(tabellSize);
		kravListe.setBorder(BorderFactory.createEtchedBorder());
		for(Krav k : f.getKrav().getKrav()){
			modell.addElement(k);
		}

		kravinfo = new JPanel();
		kravinfo.setPreferredSize(infoSize);
		kravinfo.setBorder(BorderFactory.createTitledBorder("Arbeidskrav for " + (f.getNavn())));
		kravinfo.add(kravListe);

		beskrivelse	= new InputFelt("Arbeidskrav", 20);

		visepanel.add(kravinfo);
		visepanel.add(beskrivelse);
		lagreKrav = button.generateButton("Lagre krav", visepanel, Buttons.HEL);
		fjern = button.generateButton("Fjern valgt krav", visepanel, Buttons.HEL);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);

		return visepanel;
	}
	public JPanel eksamensPanel(){
		visepanel = new JPanel();
		visepanel.setPreferredSize((høyreSize));

		faginfo = new JPanel();
		faginfo.setPreferredSize(infoSize);

		velgEksamen = new JComboBox<Eksamen>();
		velgEksamen.setPreferredSize(Buttons.HEL);
		velgEksamen.addItemListener(cl);
		for(Eksamen e : ((Fag)aktiv).getEksamener()) {
			velgEksamen.addItem(e);
		}

		visepanel.add(velgEksamen);

		if(velgEksamen.getItemCount() > 0)
			visEksamen(velgEksamen.getItemAt(TestWindow.FØRSTE));
		else
			visFag();

		studentNr = new InputFelt("StudentNr", 20, TestWindow.studentNrRegex);
		visepanel.add(faginfo);
		visepanel.add(studentNr);
		deltaker = button.generateButton("Legg til deltaker", visepanel, Buttons.HEL);
		oppmeldte = button.generateButton("Legg til Oppmeldte", visepanel, Buttons.HEL);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);

		visepanel.updateUI();
		return visepanel;
	}
	public JPanel eksamensPanel(Student s){
		visepanel = new JPanel();
		visepanel.setPreferredSize((høyreSize));

		faginfo = new JPanel();
		faginfo.setPreferredSize(infoSize);
		faginfo.setBorder(BorderFactory.createTitledBorder("Eksamener for " + s.getfNavn() + " " + s.geteNavn()));

		Tabellmodell modell = new Tabellmodell(s);
		resultater = new JTable(modell);
		resultater.setPreferredScrollableViewportSize(tabellSize);
		resultater.addMouseListener(popup);
		faginfo.add(new JScrollPane(resultater));

		visepanel.add(faginfo);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);
		return visepanel;
	}
	public void visEksamen(Eksamen e){
		faginfo.removeAll();
		faginfo.setBorder(BorderFactory.createTitledBorder("Eksamen for " + ((Fag)aktiv).getNavn()));

		Tabellmodell modell = new Tabellmodell(e);
		resultater = new JTable(modell);
		resultater.setPreferredScrollableViewportSize(tabellSize);
		resultater.addMouseListener(popup);
		faginfo.add(new JScrollPane(resultater));
		faginfo.updateUI();
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

	private class Tabellmodell extends AbstractTableModel{

		private static final long serialVersionUID = 100110L;

		private String[] kolonnenavn = {"Fag", "Dato", "StudentNr", "Møtt", "Karakter"};
		private Object[][] celler = {{"", "", "", "", ""}};

		public Tabellmodell(Eksamen e){
			if(!e.getDeltakere().isEmpty())
				fyllTabell(e.getDeltakere().size(), e.getDeltakere());
		}
		public Tabellmodell(Student s){
			if(!s.getEksamener().isEmpty())
				fyllTabell(s.getEksamener().size(), s.getEksamener());
		}
		
		private void fyllTabell(int lengde, LinkedList<EksamensDeltaker> eksamener){
			celler = new Object[lengde][5];
			int rad = 0;
			for(EksamensDeltaker ed: eksamener){
				Eksamen e = ed.getFag().findEksamenByDate(ed.getDato());
				celler[rad][0] = ed.getFag().getFagkode();
				celler[rad][1] = e;
				celler[rad][2] = ed;
				celler[rad][3] = ed.isOppmøtt();
				celler[rad++][4] = new String(""+ed.getKarakter());
			}
			
			this.addTableModelListener(new tabellytter());
		}
		
		public Object getValueAt(int rad, int kolonne){
			return celler[rad][kolonne];
		}

		public String getColumnName(int kolonne) {
			return kolonnenavn[kolonne];
		}

		public int getRowCount() { 
			return celler.length; 
		}

		public int getColumnCount() {
			return celler[0].length;
		}

		public boolean isCellEditable(int rad, int kolonne){ 
			switch (kolonne) {
			case 3:
			case 4:
				return true;
			default:
				return false;
			}
		}

		public void setValueAt(Object value, int rad, int kolonne) {
			celler[rad][kolonne] = value;
			fireTableCellUpdated(rad, kolonne);
		}

		//For å informere tabellmodellen om kolonnenes datatyper.
		public Class<? extends Object> getColumnClass(int kolonne) {
			return celler[0][kolonne].getClass();
		}
	}
	private class tabellytter implements TableModelListener{
		public void tableChanged(TableModelEvent e) {
			EksamensDeltaker ed = (EksamensDeltaker)resultater.getValueAt(e.getFirstRow(), 2);

			if(e.getColumn() == 3)
				ed.setOppmøtt((Boolean)resultater.getValueAt(e.getFirstRow(), 3));
			else{
				String s = (resultater.getValueAt(e.getFirstRow(), e.getColumn())).toString();
				char k = ed.getKarakter();
				if(s.matches("[a-fA-F]")){
					k = s.charAt(TestWindow.FØRSTE);
				} else if(s.matches("\0[a-fA-F]")){
					k = s.charAt(1);
				}
				if(k != ed.getKarakter())
					ed.setKarakter(k);
			}
			
			if(velgEksamen != null)
				visEksamen((Eksamen)velgEksamen.getSelectedItem());
			else
				vindu.cover(eksamensPanel((Student)aktiv));
		}
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
						Student s = (Student)aktiv;
						s.addFag(f);
						vindu.cover(fagPanel());
						studentFag.setSelectedItem(f);
						visFag(f);
					}
					else if(aktiv instanceof Studieprogram){
						Studieprogram studiet = (Studieprogram) aktiv;
						studiet.addFag((Fag)velgFag.getSelectedItem());
					}
					else if(aktiv instanceof Fag){
							GregorianCalendar dato = dateHandler.dateFixer(eksamensdato.getText(), null);
							//hvis dato null er input invalid på en eller annen måte.
							if(dato == null){
								eksamensdato.setText("Feil dato-format.");
								return;
							}

							Eksamen eks = new Eksamen(dato, (Fag)aktiv);
							((Fag)aktiv).addEksamen(eks);
							eksamensdato.setText("Eksamen lagret");
							vindu.cover(eksamensPanel());
							velgEksamen.setSelectedItem(eks);
							visEksamen(eks);
					}
				} catch (NullPointerException npe){
					npe.printStackTrace();
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
						aktivKrav.fjernKrav(kravListe.getSelectedValue());
						vindu.cover(kravPanel((Fag)aktiv));
					}


				} catch (NullPointerException npe){
				}
			}

			else if(e.getSource() == deltaker){
				Student s = vindu.getSkole().getStudentene().findStudentByStudentNr(studentNr.getText());
				((Eksamen)velgEksamen.getSelectedItem()).addDeltaker(s);
				visEksamen((Eksamen)velgEksamen.getSelectedItem());
			}
			else if(e.getSource() == oppmeldte){
				ArrayList<Student> studentliste = vindu.getSkole().getStudentene().visAlle();
				((Eksamen)velgEksamen.getSelectedItem()).addOppmeldteStudenter(studentliste);
				visEksamen((Eksamen)velgEksamen.getSelectedItem());
			}

			else if(e.getSource() == lagreKrav){
				aktivKrav.addKrav(new Krav(beskrivelse.getText()));
				vindu.cover(kravPanel((Fag) aktiv));
			} 

			else if(e.getSource() == tilbake){
				vindu.vis();
			} 
			else if(e.getSource() == visEksamen){
				if(aktiv instanceof Student)
					vindu.cover(eksamensPanel((Student)aktiv));
				else
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
					
					try {
						Date date;
						date = (Date) formatter.parse(slutt.getText());
						GregorianCalendar dato = (GregorianCalendar) GregorianCalendar.getInstance();
						dato.setTime(date);
						s.setSlutt(dato);
					} catch (ParseException pe) {
						
					}
				
					try{
						int nr = Integer.parseInt(tlf.getText());
						
						s.setAdresse(adresse.getText());
						s.setTlf(nr);
						s.setEpost(epost.getText());
						if(velgProg.getSelectedIndex() != -1)
							s.setStudieprogram((Studieprogram)velgProg.getSelectedItem());

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
					f.setVurderingsform(vurderingBox.getSelectedItem().toString());
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
