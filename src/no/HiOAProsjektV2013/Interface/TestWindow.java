package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;
import no.HiOAProsjektV2013.Main.Archiver;
import no.HiOAProsjektV2013.Main.ScriptClass;

public class TestWindow extends JFrame implements ActionListener {

	public static void main(String[] args) {
		TestWindow tw = new TestWindow("StudieAdministrasjon");
		tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static final long serialVersionUID = 1L;

	private Archiver arkivet;
	private ListeBoks<Student> studentboks = new ListeBoks<>();
	private ListeBoks<Laerer> laererboks = new ListeBoks<>();
	private ListeBoks<Fag> fagboks = new ListeBoks<>();
	private ListeBoks<Studieprogram> studieboks = new ListeBoks<>();
	private ScriptClass sc;
	private IndreVindu innerWindow;
	private JTextArea info;
	private VinduLytter vl;
	private JButton nystudent, nylærer, nyttfag, nyttstudieprog, visstudent,
			vislærer, visfag, visstudieprog, lagre, leggtilfag, søkeknapp, rediger;
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode,
			beskrivelse, studiepoeng, vurderingsform, lærer, søkefelt;
	private Skole skolen;
	private JPanel rammeverk, innhold, stud, lær, fag, studprog, vis;
	private Dimension knapp, halvknapp, size;

	public TestWindow(String tittel) {

		super(tittel);
		//oppretter save-objektet
		arkivet 	= new Archiver();
		
		//Oppretter gammelt objekt om det fins, eller nytt om vi ikke har et.
		skolen 		= arkivet.readFromFile();

		//TEST
		//setter WindowListener
		vl = new VinduLytter(this);
		
		//script for å generere fag, studenter og lærere
		//kommenter den ut etter 1 generate
//		sc = new ScriptClass(skolen);
		
		rammeverk = new JPanel(new BorderLayout());
		add(rammeverk);
		
		knapp 		= new Dimension(260, 25);
		halvknapp 	= new Dimension(140, 25);
		size 		= new Dimension(500, 450);
		
		fyllRamme();
		
		setSize(700, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	
	//getmetoder for windowlistener slik at det lagres info via system.exit
	public Archiver getArkiv(){
		return arkivet;
	}
	
	public Skole getSkole(){
		return skolen;
	}
	
	//Oppretter og legger inn elementer til vinduet
	public void fyllRamme() {

		//Oppretter objekter til Fast ramme
		nystudent		= new JButton("Legg til student");
		nylærer			= new JButton("Legg til lærer");
		nyttfag			= new JButton("Legg til fag");
		nyttstudieprog	= new JButton("Legg til studieprogram");
		visstudent		= new JButton("Vis studenter");
		vislærer		= new JButton("Vis lærere");
		visfag			= new JButton("Vis fag");
		visstudieprog	= new JButton("Vis studieprogram");
		søkeknapp 		= new JButton("Søk");
		søkefelt 		= new JTextField("Søk");

		nystudent		.setPreferredSize(halvknapp);
		nylærer			.setPreferredSize(halvknapp);
		nyttfag			.setPreferredSize(halvknapp);
		nyttstudieprog	.setPreferredSize(halvknapp);
		visstudent		.setPreferredSize(halvknapp);
		vislærer		.setPreferredSize(halvknapp);
		visfag			.setPreferredSize(halvknapp);
		visstudieprog	.setPreferredSize(halvknapp);
		søkeknapp		.setPreferredSize(halvknapp);
		søkefelt		.setPreferredSize(halvknapp);

		
		nystudent		.addActionListener(this);
		nylærer			.addActionListener(this);
		nyttfag			.addActionListener(this);
		nyttstudieprog	.addActionListener(this);
		visstudent		.addActionListener(this);
		vislærer		.addActionListener(this);
		visfag			.addActionListener(this);
		visstudieprog	.addActionListener(this);
		søkeknapp		.addActionListener(this);
		søkefelt		.addActionListener(this);
				
		JPanel leggtil = new JPanel();
		JPanel visning = new JPanel();
		
		leggtil.setPreferredSize(new Dimension(700,50));
		visning.setPreferredSize(new Dimension(150,450));
		
		visning.add(søkefelt);
		visning.add(søkeknapp);
		visning.add(visstudent);
		visning.add(vislærer);
		visning.add(visfag);
		visning.add(visstudieprog);

		leggtil.add(nystudent);
		leggtil.add(nylærer);
		leggtil.add(nyttfag);
		leggtil.add(nyttstudieprog);
		
		rammeverk.add(leggtil, BorderLayout.NORTH);
		rammeverk.add(visning, BorderLayout.EAST);
		
		//Oppretter objekter til registreringsfelter
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
		
		info 			= new JTextArea(20, 25);
		
		lagre 		= new JButton("Lagre");
		leggtilfag 	= new JButton("Legg til fag");
		
		lagre		.setPreferredSize(knapp);
		leggtilfag	.setPreferredSize(knapp);
		
		lagre		.addActionListener(this);
		leggtilfag	.addActionListener(this);
	
		innhold = new JPanel();
		vis("");
		rammeverk.add(innhold, BorderLayout.WEST);
		revalidate();
	}
	
	//Resetter tekstfeltene
	public void refresh(){
		
		navn			.setText("navn");
		epost			.setText("epost");
		tlf				.setText("tlf");
		adresse			.setText("adresse");
		start			.setText("startdato");
		kontorNr		.setText("kontoNr");
		fagkode			.setText("fagkode");
		beskrivelse		.setText("beskrivelse");
		vurderingsform	.setText("vurderingsform");
		studiepoeng		.setText("studiepoeng");
		lærer			.setText("lærer");
	}

	//Oppdaterer innholdspanelet
	public void innhold(Component c){
		refresh();
		innhold.removeAll();
		innhold.add(c);
		innhold.updateUI();
		info.setEditable(false);
		revalidate();
	}
	
	//Metoder for å vise relevante felter for registrering av objekter
	public void student() {
		stud = new JPanel();
		stud.setPreferredSize(size);
		info = new JTextArea(8,25);
		
		stud.add(info);
		stud.add(navn);
		stud.add(epost);
		stud.add(tlf);
		stud.add(adresse);
		stud.add(start);
		stud.add(lagre);
		innhold(stud);
	}
	public void lærer() {
		lær = new JPanel();
		lær.setPreferredSize(size);
		info = new JTextArea(8,25);

		lær.add(info);
		lær.add(navn);
		lær.add(epost);
		lær.add(tlf);
		lær.add(kontorNr);
		lær.add(lagre);

		innhold(lær);
	}
	public void fag() {
		fag = new JPanel();
		fag.setPreferredSize(size);
		info = new JTextArea(8,25);
		
		fag.add(info);
		fag.add(navn);
		fag.add(fagkode);
		fag.add(beskrivelse);
		fag.add(studiepoeng);
		fag.add(vurderingsform);
		fag.add(lærer);
		fag.add(lagre);
		
		innhold(fag);
	}
	public void studieprog() {
		studprog = new JPanel();
		studprog.setPreferredSize(size);
		info = new JTextArea(8,25);

		studprog.add(info);
		studprog.add(navn);
		studprog.add(fagkode);
		studprog.add(lagre);
		studprog.add(leggtilfag);

		innhold(studprog);
	}
	
	//Viser resultat av søk o.l
	public void vis(JList<?> liste) {
		vis = new JPanel();
		vis.setPreferredSize(size);

		vis.add(new JScrollPane(info));
		//comment ut panelRefresh her for å få normalt display av info igjen.
		panelRefresh(liste);
		innhold(vis);
	}
	
	public void vis(String tekst) {
		vis = new JPanel();
		vis.setPreferredSize(size);
		
		info = new JTextArea(20, 25);
		info.setText(tekst);

		vis.add(new JScrollPane(info));
		innhold(vis);
	}
	
	//Listeboks funker kind-of, men vi må redefinere toStrings for å bruke de saklig.
	//trenger å jobbe med plassering innenfor hovedpanelet.
	private JPanel panelRefresh(JList<?> liste){
		
		vis = new JPanel();
		vis.setPreferredSize(size);
		
		rediger = new JButton("rediger");
		rediger.setPreferredSize(knapp);
		rediger.addActionListener(this);
		
		vis.add(new JScrollPane(liste));
		vis.add(rediger);
		return vis;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nystudent) {
			student();
		}
		if (e.getSource() == nylærer) {
			lærer();
		}
		if (e.getSource() == nyttfag) {
			fag();
		}
		if (e.getSource() == nyttstudieprog) {
			studieprog();
		}
		if (e.getSource() == visstudent) {
			vis(skolen.getStudentene().toString());
		}
		if (e.getSource() == vislærer) {
			vis(skolen.getLærerne().toString());
		}
		if (e.getSource() == visfag) {
			vis(skolen.getFagene().toString());
		}
		if (e.getSource() == visstudieprog) {
			vis(skolen.getStudieprogrammene().toString());
		}
		
		//Lagring av objekter
		//************************************************************************************************************************************
		if (e.getSource() == lagre) {
			
			if (innhold.getComponent(0).equals(stud)) { //Sjekker hvilket panel som ligger i innhold-panelet
				
				DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for startdato
				try {
					int nr = Integer.parseInt(tlf.getText());
					Date date = (Date) formatter.parse(start.getText());
					
					info.setText(skolen.getStudentene().addStudent(navn.getText(), 
							epost.getText(), 
							nr,
							adresse.getText(), 
							date).toString());
					
				} catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				} catch (ParseException pe) {
					info.setText("Feil datoformat");
				}
				
			} 
		//***************************************************************
		
			else if (innhold.getComponent(0).equals(lær)) {
				try{
					int nr = Integer.parseInt(tlf.getText());
					
					info.setText(skolen.getLærerne().addLærer(navn.getText(), 
								epost.getText(), 
								nr,
								kontorNr.getText()).toString());
					
				}catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				}
			} 
		//***************************************************************
		
		else if (innhold.getComponent(0).equals(fag)) {
				try{
					int poeng = Integer.parseInt(studiepoeng.getText());
					Laerer læreren = skolen.getLærerne().findByNavn(lærer.getText()).get(0);
					
					info.setText(skolen.getFagene().addFag(navn.getText(), 
							fagkode.getText(),
							beskrivelse.getText(),
							vurderingsform.getText(), 
							poeng, 
							læreren).toString());
					
				}catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				}catch (NullPointerException nfe){
					info.setText("Nullpointer-feil");
				}catch (IndexOutOfBoundsException iobe){
					info.setText("Finner ikke lærer");
				}
				
			} 
		//***************************************************************
		
		else if (innhold.getComponent(0).equals(studprog)) {
				info.setText(skolen.getStudieprogrammene().addStudProg(navn.getText()).toString());
			}
		}
		
		//************************************************************************************************************************************
		
		if (e.getSource() == leggtilfag) {
			if(innhold.getComponent(0).equals(studprog)){
				try{
					skolen.addFagToStudProg(navn.getText(), fagkode.getText());
					info.setText(skolen.getStudieprogrammene().findByNavn(navn.getText()).toString());
				} catch (NullPointerException npe){
					info.setText("Ugyldig fagkode");
				}
			}
		}
		
		// søker på fritekst-søk, gjennom navn på alle tingene. Tror dette må
		// optimaliseres slik at vi ikke looper HELE datastrukturen vår hver
		// gang.
		if (e.getSource() == søkefelt || e.getSource() == søkeknapp) {
			ArrayList<Student> studentene = skolen.getStudentene().findByNavn(
					søkefelt.getText());
			ArrayList<Laerer> lærerne = skolen.getLærerne().findByNavn(
					søkefelt.getText());
			ArrayList<Fag> fagene = skolen.getFagene().findByNavn(
					søkefelt.getText());
			ArrayList<Studieprogram> studieprogrammene = skolen
					.getStudieprogrammene().findByNavn(søkefelt.getText());
			if (studentene.size() > 0) {
				JList<Student> listen = studentboks.listiFy(studentene);
				vis(listen);
			} else if (lærerne.size() > 0) {
				JList<Laerer> listen = laererboks.listiFy(lærerne);
				vis(listen);
			} else if (fagene.size() > 0) {
				JList<Fag> listen = fagboks.listiFy(fagene);
				vis(listen);
			} else if (studieprogrammene.size() > 0) {
				JList<Studieprogram> listen = studieboks.listiFy(studieprogrammene);
				vis(listen);
			} else {
				//sjekker søket for hvilken liste som kom ut av navnsøk. Vi remaker nok denne, så orker ikke implemente hele.
				try {
					ArrayList<?> arrayen = skolen.søk(søkefelt.getText());
					if (arrayen.get(0) instanceof Student) {
						JList<Student> listen = studentboks.listiFy((ArrayList<Student>) arrayen);
						vis(listen);
					} else if (arrayen.get(0) instanceof Fag) {
						JList<Fag> listen = fagboks.listiFy((ArrayList<Fag>) arrayen);
						vis(listen);
					}
				} catch (NullPointerException npe) {
					npe.printStackTrace();
				}
			}

		}
		
		if (e.getSource() == rediger){
			if(studentboks.getValgt() != null){
				
			}
		}
	}
}

