package no.HiOAProsjektV2013.Interface;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.Main.Archiver;

public class TestWindow extends JFrame implements ActionListener {

	public static void main(String[] args) {
		TestWindow tw = new TestWindow("StudieAdministrasjon");
		tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static final long serialVersionUID = 1L;

	private Archiver arkivet;
	private JTextArea info;
	private JButton nystudent, nylærer, nyttfag, nyttstudieprog, visstudent,
			vislærer, visfag, visstudieprog, lagre, legginnfag, søk;
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode,
			beskrivelse, studiepoeng, vurderingsform, lærer, søkefelt;
	private Skole skolen;
	private JPanel rammeverk, innhold, stud, lær, fag, studprog, vis;
	private Dimension knapp, halvknapp, size;
	private JScrollPane scroll;

	public TestWindow(String tittel) {

		super(tittel);

		skolen = arkivet.readFromFile();

		rammeverk = new JPanel(new BorderLayout());
		add(rammeverk);
		
		knapp 		= new Dimension(260, 25);
		halvknapp 	= new Dimension(140, 25);
		size 		= new Dimension(500, 500);
		
		fyllRamme();

		setLayout(new FlowLayout());
		setSize(700, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
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
		søk			 	= new JButton("Søk");
		søkefelt		= new JTextField("Søk");
		
		nystudent		.setPreferredSize(halvknapp);
		nylærer			.setPreferredSize(halvknapp);
		nyttfag			.setPreferredSize(halvknapp);
		nyttstudieprog	.setPreferredSize(halvknapp);
		visstudent		.setPreferredSize(halvknapp);
		vislærer		.setPreferredSize(halvknapp);
		visfag			.setPreferredSize(halvknapp);
		visstudieprog	.setPreferredSize(halvknapp);
		søk				.setPreferredSize(halvknapp);
		søkefelt		.setPreferredSize(halvknapp);
		
		nystudent		.addActionListener(this);
		nylærer			.addActionListener(this);
		nyttfag			.addActionListener(this);
		nyttstudieprog	.addActionListener(this);
		visstudent		.addActionListener(this);
		vislærer		.addActionListener(this);
		visfag			.addActionListener(this);
		visstudieprog	.addActionListener(this);
		søk				.addActionListener(this);
		søkefelt		.addActionListener(this);
		
		JPanel leggtil = new JPanel();
		JPanel visning = new JPanel();
		
		leggtil.setPreferredSize(new Dimension(700,50));
		visning.setPreferredSize(new Dimension(150,450));
		
		visning.add(søkefelt);
		visning.add(søk);
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
		revalidate();
		
		//Oppretter objekter til registreringsfelter
		navn	 		= new JTextField("navn", 20);
		epost	 		= new JTextField("e-post", 20);
		tlf		 		= new JTextField("tlf", 20);
		adresse			= new JTextField("adresse", 20);
		start			= new JTextField("startdato", 20);
		navn			= new JTextField("navn", 20);
		epost			= new JTextField("e-post", 20);
		tlf				= new JTextField("tlf", 20);
		kontorNr		= new JTextField("kontorNr", 20);
		fagkode			= new JTextField("fagkode", 20);
		beskrivelse		= new JTextField("beskrivelse", 20);
		vurderingsform	= new JTextField("vurderingsform", 20);
		studiepoeng		= new JTextField("studiepoeng", 20);
		lærer			= new JTextField("lærer", 20);
		info 			= new JTextArea(20, 25);
		
		lagre = new JButton("Lagre");
		lagre.setPreferredSize(knapp);
		lagre.addActionListener(this);
		
		innhold = new JPanel();
		vis("");
		rammeverk.add(innhold, BorderLayout.WEST);
	}
	
	public void innhold(Component c){
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

		legginnfag = new JButton("Legg til fag");
		legginnfag.setPreferredSize(knapp);
		legginnfag.addActionListener(this);

		studprog.add(info);
		studprog.add(navn);
		studprog.add(fagkode);
		studprog.add(lagre);
		studprog.add(legginnfag);

		innhold(studprog);
	}
	
	//Viser resultat av søk o.l

	public void vis(String tekst) {
		vis = new JPanel();
		vis.setPreferredSize(size);
		
		info = new JTextArea(20, 25);
		info.setText(tekst);
		scroll = new JScrollPane(info);

		vis.add(scroll);

		innhold(vis);
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
			vis(skolen.studprogToString());
		}
		
		//Lagring av objekter
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
			
		else if (innhold.getComponent(0).equals(fag)) {
				try{
					int poeng = Integer.parseInt(studiepoeng.getText());
					Laerer læreren = skolen.getLærerne().findLærerByNavn(lærer.getText()).get(0);
					
					info.setText(skolen.getFagene().addFag(navn.getText(), 
							fagkode.getText(),
							beskrivelse.getText(),
							vurderingsform.getText(), 
							poeng, 
							læreren).toString());
					
				}catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				}catch (NullPointerException nfe){
					info.setText("Finner ikke lærer");
				}
				
			} 
			
		else if (innhold.getComponent(0).equals(studprog)) {
				info.setText(skolen.addStudProg(navn.getText()).toString());
			}
		}
		
		if (e.getSource() == legginnfag) {
			skolen.addFagToStudProg(navn.getText(), fagkode.getText());
			info.setText(skolen.finnStudProgByNavn(navn.getText()).toString());
		}
		if (e.getSource() == søk || e.getSource() == søkefelt){
			String resultat = "Lærere:";
			for(Laerer l : skolen.getLærerne().findLærerByNavn(søkefelt.getText())){
				resultat += "\n" + l.getfNavn() + l.geteNavn();
			}
			vis(resultat);
		}
	}
}


