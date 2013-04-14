package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.Main.Archiver;

public class TestWindow extends JFrame implements ActionListener {

	public static void main(String[] args) {
		TestWindow tw = new TestWindow("StudieAdministrasjon");
		tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static final long serialVersionUID = 1L;

	private Archiver arkivet;
	private ListeBoks<Student> listeboks = new ListeBoks<Student>();
	private JTextArea info;
	private VinduLytter vl;
	private JButton nystudent, nylærer, nyttfag, nyttstudieprog, visstudent,
			vislærer, visfag, visstudieprog, lagre, leggtilfag;
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode,
			beskrivelse, studiepoeng, vurderingsform, lærer;
	private Skole skolen;
	private JPanel rammeverk, innhold, stud, lær, fag, studprog, vis;
	private Dimension knapp, halvknapp, size;
	private JScrollPane scroll;
	private Soek søk;

	public TestWindow(String tittel) {

		super(tittel);
		//oppretter save-objektet
		arkivet 	= new Archiver();
		
		//Oppretter gammelt objekt om det fins, eller nytt om vi ikke har et.
		skolen 		= arkivet.readFromFile();
		
		
		//TEST
		ArrayList<Fag> fag = skolen.getFagene().findByNavn("Faget");
		fag.get(0).addKrav("Bare et testkrav");
		
		ArrayList<Student> student = skolen.getStudentene().findByNavn("Joakim");
		System.out.println(student.get(0).toString());
		System.out.println(fag.get(0).toString());
		student.get(0).addFag(fag.get(0));
		
		
		//TEST
		//setter WindowListener
		vl = new VinduLytter(this);
		
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
		
		nystudent		.setPreferredSize(halvknapp);
		nylærer			.setPreferredSize(halvknapp);
		nyttfag			.setPreferredSize(halvknapp);
		nyttstudieprog	.setPreferredSize(halvknapp);
		visstudent		.setPreferredSize(halvknapp);
		vislærer		.setPreferredSize(halvknapp);
		visfag			.setPreferredSize(halvknapp);
		visstudieprog	.setPreferredSize(halvknapp);

		
		nystudent		.addActionListener(this);
		nylærer			.addActionListener(this);
		nyttfag			.addActionListener(this);
		nyttstudieprog	.addActionListener(this);
		visstudent		.addActionListener(this);
		vislærer		.addActionListener(this);
		visfag			.addActionListener(this);
		visstudieprog	.addActionListener(this);

		søk = new Soek(skolen, this, 150, 100);
		
		JPanel leggtil = new JPanel();
		JPanel visning = new JPanel();
		
		leggtil.setPreferredSize(new Dimension(700,50));
		visning.setPreferredSize(new Dimension(150,450));
		
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
	
	//getmetoder for windowlistener slik at det lagres info via system.exit
	public Archiver getArkiv(){
		return arkivet;
	}
	public Skole getSkole(){
		return skolen;
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
	public void vis(String tekst) {
		vis = new JPanel();
		vis.setPreferredSize(size);
		
		info = new JTextArea(20, 25);
		info.setText(tekst);

		vis.add(new JScrollPane(info));
		innhold(vis);
	}
	
	//Listeboks funker kind-of, men vi må redefinere toStrings for å bruke de saklig. Trenger også noe hjelp med plassering av de. 
	public void vis(JList<Student> studenter){
	vis = panelRefresh();
	}
	private JPanel panelRefresh(){
		
		vis = new JPanel();
		vis.setPreferredSize(size);
		vis.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		vis.add(listeboks.listiFy(skolen.getStudentene().findByNavn("Kåre Rishaug")), gbc);
		JLabel myLabel = new JLabel("Label1");
		vis.add(myLabel, gbc);
		
		scroll = new JScrollPane(info);

		vis.add(scroll);
		
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
			vis(skolen.studprogToString());
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
				info.setText(skolen.addStudProg(navn.getText()).toString());
			}
		}
		
		//************************************************************************************************************************************
		
		if (e.getSource() == leggtilfag) {
			if(innhold.getComponent(0).equals(studprog)){
				try{
					skolen.addFagToStudProg(navn.getText(), fagkode.getText());
					info.setText(skolen.finnStudProgByNavn(navn.getText()).toString());
				} catch (NullPointerException npe){
					info.setText("Ugyldig fagkode");
				}
			}
		}
		
		if (e.getSource() == søk.getFelt() || e.getSource() == søk.getKnapp())
		{
			info.setText(søk.søk());
		}
	}
}

