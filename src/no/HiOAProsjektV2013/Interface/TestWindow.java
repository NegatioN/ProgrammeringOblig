package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

		innhold = new JPanel();
		rammeverk = new JPanel(new BorderLayout());
		rammeverk.add(innhold, BorderLayout.WEST);
		add(rammeverk);
		
		knapp 		= new Dimension(260, 25);
		halvknapp 	= new Dimension(140, 25);
		size 		= new Dimension(400, 500);
		
		lagre = new JButton("Lagre");
		lagre.setPreferredSize(knapp);
		lagre.addActionListener(this);
		
		åpningsvindu();

		setLayout(new FlowLayout());
		setSize(700, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void innhold(Component c){
		innhold.removeAll();
		innhold.add(c);
		innhold.updateUI();
		info.setEditable(false);
		revalidate();
	}

	public void åpningsvindu() {

		nystudent		 = new JButton("Legg til student");
		nylærer			 = new JButton("Legg til lærer");
		nyttfag			 = new JButton("Legg til fag");
		nyttstudieprog	 = new JButton("Legg til studieprogram");
		visstudent		 = new JButton("Vis studenter");
		vislærer		 = new JButton("Vis lærere");
		visfag			 = new JButton("Vis fag");
		visstudieprog	 = new JButton("Vis studieprogram");
		søk				 = new JButton("Søk");
		søkefelt		 = new JTextField("Søk");
		
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
		leggtil.setPreferredSize(new Dimension(600,50));
		JPanel visning = new JPanel();
		visning.setPreferredSize(new Dimension(150,450));
		
		visning.add(søkefelt);
		visning.add(søk);
		leggtil.add(nystudent);
		visning.add(visstudent);
		leggtil.add(nylærer);
		visning.add(vislærer);
		leggtil.add(nyttfag);
		visning.add(visfag);
		leggtil.add(nyttstudieprog);
		visning.add(visstudieprog);
		
		rammeverk.add(leggtil, BorderLayout.NORTH);
		rammeverk.add(visning, BorderLayout.EAST);
		revalidate();

	}

	public void student() {
		stud = new JPanel();
		stud.setPreferredSize(size);
		
		info	 = new JTextArea(8, 25);
		navn	 = new JTextField("navn", 20);
		epost	 = new JTextField("e-post", 20);
		tlf		 = new JTextField("tlf", 20);
		adresse	 = new JTextField("adresse", 20);
		start	 = new JTextField("startdato", 20);

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

		info	 = new JTextArea(8, 25);
		navn	 = new JTextField("navn", 20);
		epost	 = new JTextField("e-post", 20);
		tlf		 = new JTextField("tlf", 20);
		kontorNr = new JTextField("kontorNr", 20);

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

		info = new JTextArea(8, 25);

		navn			 = new JTextField("navn", 20);
		fagkode			 = new JTextField("fagkode", 20);
		beskrivelse		 = new JTextField("beskrivelse", 20);
		vurderingsform	 = new JTextField("vurderingsform", 20);
		studiepoeng		 = new JTextField("studiepoeng", 20);
		lærer			 = new JTextField("lærer", 20);

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

		info		 = new JTextArea(8, 25);
		navn 		 = new JTextField("navn", 20);
		fagkode		 = new JTextField("fagkode", 20);

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
		
		if (e.getSource() == lagre) {

			if (innhold.getComponent(0).equals(stud)) {
				
				DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
				try {
					int nr = Integer.parseInt(tlf.getText());
					Date date = (Date) formatter.parse(start.getText());
					
					info.setText(skolen.getStudentene().addStudent(navn.getText(), 
							epost.getText(), 
							nr,
							adresse.getText(), 
							date).toString());
					
				} catch (ParseException pe) {
					info.setText("Feil datoformat");
				} catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				}
				
			} else if (innhold.getComponent(0).equals(lær)) {
				try{
					int nr = Integer.parseInt(tlf.getText());
					
					info.setText(skolen.getLærerne().addLærer(navn.getText(), 
								epost.getText(), 
								nr,
								kontorNr.getText()).toString());
					
				}catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				}
				
			} else if (innhold.getComponent(0).equals(fag)) {
				try{
					int poeng = Integer.parseInt(studiepoeng.getText());
					Laerer læreren = new Laerer("Eva Vihovde", "Lærern@hioa.no", 95113342, "PS230");
					
					info.setText(skolen.getFagene().addFag(navn.getText(), 
							fagkode.getText(),
							beskrivelse.getText(),
							vurderingsform.getText(), 
							poeng, 
							læreren).toString());
					
				}catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				}
				
			} else if (innhold.getComponent(0).equals(studprog)) {
				info.setText(skolen.addStudProg(navn.getText()).toString());
			}
		}
		
		if (e.getSource() == legginnfag) {
			skolen.addFagToStudProg(navn.getText(), fagkode.getText());
			info.setText(skolen.finnStudProgByNavn(navn.getText()).toString());
		}
	}
}
