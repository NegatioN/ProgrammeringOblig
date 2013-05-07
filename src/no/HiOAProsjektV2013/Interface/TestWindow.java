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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;
import no.HiOAProsjektV2013.Main.Archiver;
import no.HiOAProsjektV2013.Main.ScriptClass;

/*
 * Hovedvinduet i programmet. Inneholder mesteparten av interfacen.
 * LE, skriv litt mer her xD
 */
public class TestWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	//checkbox burde sette selectedValue til en av disse, og passe value inn i search
	public static final int LÆRER = 30, STUDENT = 40, FAG = 50, STUDIEPROGRAM = 60;

	private Archiver arkivet;
	private VinduLytter vl;
	private Skole skolen;
	private Buttons buttonGenerator;
	private ListeBoks<Student> studentboks = new ListeBoks<>(this);
	private ListeBoks<Laerer> laererboks = new ListeBoks<>(this);
	private ListeBoks<Fag> fagboks = new ListeBoks<>(this);
	private ListeBoks<Studieprogram> studieboks = new ListeBoks<>(this);
	private PopupVindu pop;
	private JTextArea info;
	private JButton nystudent, nylærer, nyttfag, nyttstudieprog, visstudent,
			vislærer, visfag, visstudieprog, lagre, leggtilfag, settiprog, søkeknapp, avansert;
	private JRadioButton studentCheck, lærerCheck, fagCheck, studieCheck;
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode,
			beskrivelse, studiepoeng, vurderingsform, søkefelt;
	private JPanel rammeverk, innhold, stud, lær, fag, studprog, display;
	private Dimension size = new Dimension(300,400);
	private Border ramme = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	private JComboBox<Fag> velgFag;
	private JComboBox<Laerer> velgLærer;
	private JComboBox<Studieprogram> velgProg;
	//endre
	private int selectedValue = STUDENT;
	
	private final String fagkodeRegex = "\\D{4}\\d{4}";
	private final String studentNrRegex = "s\\d{6}";
	private final String årRegex = "\\d{4}";
	private final String mobRegex = "\\d{8}";
	private final String mailRegex = "\\S+@\\S+.\\S+";
	private final String navnRegex = "\\S+\\s\\S+";
	private final String datoRegex = "\\D";
	
	private JMenuBar meny;

	public TestWindow(String tittel) {

		super(tittel);
		//oppretter buttongeneratoren som sparer linjer på gjentagende oppgaver.
		buttonGenerator = new Buttons(this);
		//oppretter save-objektet
		arkivet 	= new Archiver();
		//Oppretter gammelt objekt om det fins, eller nytt om vi ikke har et.
		skolen 		= arkivet.readFromFile();
		//Oppretter vinduslytter
		vl = new VinduLytter(this);
		
		//script for å generere fag, studenter og lærere
		//kommenter den ut etter 1 generate
//		ScriptClass sc = new ScriptClass(skolen);
		
		rammeverk = new JPanel(new BorderLayout());
		add(rammeverk);
		fyllRamme();
		
		//setter inn meny
		populateMenu();
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);

	}
	//fyller meny-baren
	private void populateMenu(){
		meny = new JMenuBar();
		
		JMenu fil = new JMenu("Fil");
		fil.setMnemonic('F');
		
		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic('S');
		//anonym actionlistener som lagrer fila
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				arkivet.writeToFile(skolen);
				//må endres til å reflektere utfall.
				JOptionPane.showMessageDialog(null, "Filen er lagret");
			}
		});
		fil.add(save);
		
		JMenu om = new JMenu("Om");
		om.setMnemonic('O');
		
		JMenuItem omOss = new JMenuItem("Om oss");
		omOss.setMnemonic('S');
		omOss.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "BLABLABLA");
			}
		});
		JMenuItem omProgrammet = new JMenuItem("Om Programmet");
		omProgrammet.setMnemonic('P');
		omProgrammet.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "Programmet er laget for skoler av medium størrelse med behov for lagring av informasjon om" +
						" sine studenter og ansatte.");

			}
		});
		
		om.add(omProgrammet);
		om.add(omOss);
		
		meny.add(fil);
		meny.add(om);
		
		
		setJMenuBar(meny);
	}
	//kanskje kaste denne inn i testwindow pga at den må update selectedValue, og jeg er usikker på å ta inn et TestWindow her.
	//for valuePassing til søk
	private void generateButtonGroup(JPanel panel){
		 ButtonGroup group = new ButtonGroup();
		 lærerCheck = new JRadioButton("Lærere");
		 studentCheck = new JRadioButton("Student");
		 fagCheck = new JRadioButton("Fag");
		 studieCheck = new JRadioButton("Studier");
		 
		 lærerCheck.setActionCommand(LÆRER+"");
		 studentCheck.setActionCommand(STUDENT+"");
		 fagCheck.setActionCommand(FAG+"");
		 studieCheck.setActionCommand(STUDIEPROGRAM+"");
		 
		 lærerCheck.addActionListener(this);
		 studentCheck.addActionListener(this);
		 fagCheck.addActionListener(this);
		 studieCheck.addActionListener(this);
		 
		 studentCheck.setSelected(true);
		 
		 
		 group.add(studentCheck);
		 group.add(lærerCheck);
		 group.add(fagCheck);
		 group.add(studieCheck);
		 
		 panel.add(studentCheck);
		 panel.add(lærerCheck);
		 panel.add(fagCheck);
		 panel.add(studieCheck);
	}

	//getmetoder for windowlistener slik at det lagres info via system.exit
	public Archiver getArkiv(){
		return arkivet;
	}
	
	public Skole getSkole(){
		return skolen;
	}
	
	//Metoder som endrer teksten i display-tekstområdet
	public void setText(String tekst){
		info.setText(tekst);
	}
	
	//Oppretter og legger inn elementer til vinduet
	public void fyllRamme() {

		//Oppretter objekter til Fast ramme
		JPanel leggtil = new JPanel();
		JPanel visning = new JPanel();
		display = new JPanel();
		leggtil.setPreferredSize(new Dimension(800,50));
		visning.setPreferredSize(new Dimension(170,400));
		display.setPreferredSize(size);
		leggtil.setBorder(ramme);
		visning.setBorder(ramme);
		display.setBorder(ramme);
		
		søkefelt = new JTextField("Søk");
		søkefelt.setPreferredSize(Buttons.HALV);
		søkefelt.addActionListener(this);
		visning.add(søkefelt);
		
		søkeknapp 		= buttonGenerator.generateButton("Søk", visning, Buttons.HALV);
		generateButtonGroup(visning);
		avansert 		= buttonGenerator.generateButton("Avansert søk", visning, Buttons.HALV);
		visning.add(Box.createRigidArea(Buttons.HALV));

		nystudent 		= buttonGenerator.generateButton("Legg til student", leggtil, Buttons.HALV);
		nylærer 		= buttonGenerator.generateButton("Legg til lærer", leggtil, Buttons.HALV);
		nyttfag 		= buttonGenerator.generateButton("Legg til fag", leggtil, Buttons.HALV);
		nyttstudieprog 	= buttonGenerator.generateButton("Legg til studieprogram", leggtil, new Dimension(200, 30));

		visstudent 		= buttonGenerator.generateButton("Vis student", visning, Buttons.HALV);
		vislærer 		= buttonGenerator.generateButton("Vis lærere", visning, Buttons.HALV);
		visfag 			= buttonGenerator.generateButton("Vis fag", visning, Buttons.HALV);
		visstudieprog 	= buttonGenerator.generateButton("Vis studieprogram", visning, Buttons.HALV);

		info = new JTextArea(23, 23);
		info.setBorder(BorderFactory.createLoweredBevelBorder());
		info.setEditable(false);
		info.setLineWrap(true);
		info.setText("\n\n\n\n\n Velkommen til vår studieadmininistrasjon!\n" +
				   "                       Her er alt mulig!");
		
		display.add(info);
		rammeverk.add(display, BorderLayout.WEST);
		rammeverk.add(leggtil, BorderLayout.NORTH);
		rammeverk.add(visning, BorderLayout.EAST);

		//Oppretter objekter til registreringsfelter
		navn	 		= new JTextField("Navn", 20);
		epost	 		= new JTextField("E-post", 20);
		tlf		 		= new JTextField("Telefon", 20);
		adresse			= new JTextField("Adresse", 20);
		start			= new JTextField("Startdato", 20);
		kontorNr		= new JTextField("Kontornummer", 20);
		fagkode			= new JTextField("Fagkode", 20);
		beskrivelse		= new JTextField("Beskrivelse", 20);
		vurderingsform	= new JTextField("Vurderingsform", 20);
		studiepoeng		= new JTextField("Studiepoeng", 20);
		
		Fag[] fagA = new Fag[skolen.getFagene().visAlle().size()];
		skolen.getFagene().visAlle().toArray(fagA);
		velgFag = new JComboBox<Fag>(fagA);
		velgFag.setPreferredSize(Buttons.HEL);
		
		Laerer[] lærerA = new Laerer[skolen.getLærerne().visAlle().size()];
		skolen.getLærerne().visAlle().toArray(lærerA);
		velgLærer = new JComboBox<Laerer>(lærerA);
		velgLærer.setPreferredSize(Buttons.HEL);
		
		Studieprogram[] progA = new Studieprogram[skolen.getStudieprogrammene().visAlle().size()];
		skolen.getStudieprogrammene().visAlle().toArray(progA);
		velgProg = new JComboBox<Studieprogram>(progA);
		velgProg.setPreferredSize(Buttons.HEL);
		
		lagre = buttonGenerator.generateButton("Lagre", Buttons.HEL);
		leggtilfag = buttonGenerator.generateButton("Legg til fag", Buttons.HEL);
		settiprog = buttonGenerator.generateButton("Velg studieprogram", Buttons.HEL);
		innhold = new JPanel();
		innhold.setBorder( ramme);
		
		rammeverk.add(innhold, BorderLayout.CENTER);
		revalidate();
	}
		
	//Metoder for å vise relevante felter for registrering av objekter
	public void student() {
		stud = new JPanel();
		stud.setPreferredSize(size);

		stud.add(navn);
		stud.add(epost);
		stud.add(tlf);
		stud.add(adresse);
		stud.add(start);
		stud.add(lagre);
		stud.add(Box.createRigidArea(Buttons.HEL));
		stud.add(velgProg);
		stud.add(settiprog);
		velgProg.setVisible(false);
		settiprog.setVisible(false);
		
		vis(stud);
	}
	public void lærer() {
		lær = new JPanel();
		lær.setPreferredSize(size);

		lær.add(navn);
		lær.add(epost);
		lær.add(tlf);
		lær.add(kontorNr);
		lær.add(lagre);

		vis(lær);
	}
	public void fag() {
		fag = new JPanel();
		fag.setPreferredSize(size);
		
		fag.add(navn);
		fag.add(fagkode);
		fag.add(beskrivelse);
		fag.add(studiepoeng);
		fag.add(vurderingsform);
		fag.add(velgLærer);
		fag.add(lagre);
		
		vis(fag);
	}
	public void studieprog() {
		studprog = new JPanel();
		studprog.setPreferredSize(size);
		
		studprog.add(navn);
		studprog.add(lagre);
		studprog.add(Box.createRigidArea(Buttons.HEL));
		studprog.add(velgFag);
		studprog.add(leggtilfag);
		
		vis(studprog);
	}

	//Resetter tekstfeltene
	public void refresh(){
		
		navn			.setText("Navn");
		epost			.setText("E-post");
		tlf				.setText("Telefon");
		adresse			.setText("Adresse");
		start			.setText("Startdato");
		kontorNr		.setText("Kontornummer");
		fagkode			.setText("Fagkode");
		beskrivelse		.setText("Beskrivelse");
		vurderingsform	.setText("Vurderingsform");
		studiepoeng		.setText("Studiepoeng");
	}
	//Oppdaterer innholdspanelet
	public void vis(Component c){
		refresh();
		innhold.removeAll();
		innhold.add(c);
		innhold.updateUI();
		revalidate();
	}
	public void cover(Component c){
		if(innhold.getComponentCount() < 2){
			innhold.getComponent(0).setVisible(false);
			innhold.add(c);
			System.out.println(innhold.getComponentCount());
			innhold.updateUI();
			revalidate();
		}
	}
	public void vis(){
		innhold.getComponent(0).setVisible(true);
		innhold.remove(1);
		innhold.updateUI();
		revalidate();
	}
	public void setSelectedValue(int i){
		selectedValue = i;
	}
	
	//Oppdaterer display-panelet
	public void display(Component c){
		display.removeAll();
		display.add(c);
		display.updateUI();
		revalidate();
	}
	
	//Får opp igjen displayet
	public void display(){
		display.removeAll();
		display.add(info);
		display.updateUI();
		revalidate();
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		//Nullstiller displayet
		display();
		setText(""); 
		//setter selectedValue for programmet sånn at vi får filtrert hva vi søker mot.
		if(e.getSource() == studentCheck || e.getSource() == lærerCheck || e.getSource() == fagCheck || e.getSource() == studieCheck)
		selectedValue = Integer.parseInt(e.getActionCommand());
		System.out.println(selectedValue);
		
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
			vis(studentboks.visResultat(studentboks.listify(skolen.getStudentene().visAlle())));
		}
		if (e.getSource() == vislærer) {
			vis(laererboks.visResultat(laererboks.listify(skolen.getLærerne().visAlle())));
		}
		if (e.getSource() == visfag) {
			vis(fagboks.visResultat(fagboks.listify(skolen.getFagene().visAlle())));
		}
		if (e.getSource() == visstudieprog) {
			vis(studieboks.visResultat(studieboks.listify(skolen.getStudieprogrammene().visAlle())));
		}
		
		//Lagring av objekter
		//************************************************************************************************************************************
		if (e.getSource() == lagre) {
			
			if (innhold.getComponent(0).equals(stud)) { //Sjekker hvilket panel som ligger i innhold-panelet
				
				DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); //Setter inputformat for startdato
				try {
					int nr = Integer.parseInt(tlf.getText());
					//regex-checks på input.
					if(!tlf.getText().matches(mobRegex)){
						tlf.setText("Feil nummerformat");
						return;
					}
					else if(!epost.getText().matches(mailRegex)){
						epost.setText("Feil epost-format.");
						return;
					}
					else if(!navn.getText().matches(navnRegex)){
						navn.setText("Fornavn og Etternavn");
						return;
					}
//					int[] datoTing = new int[3];
//					String[] datoString = start.getText().split(datoRegex);
//					int counter = 0;
//					for(int tall : datoTing){
//						tall = Integer.parseInt(datoString[counter++]);
//					}
//					GregorianCalendar dato = new GregorianCalendar(datoTing[2], datoTing[1], datoTing[0]);
//					System.out.println(dato.get(Calendar.YEAR) + " år " + dato.get(Calendar.MONTH) + " måned " + dato.get(Calendar.DATE) + " dag");
					Date date = (Date) formatter.parse(start.getText());

					info.setText(skolen.getStudentene().addStudent(navn.getText(), 
							epost.getText(), 
							nr,
							adresse.getText(), 
							date).fullString());

					velgProg.setVisible(true);
					settiprog.setVisible(true);

				} catch (NumberFormatException nfe){
					tlf.setText("Feil nummerformat");
				} catch (ParseException pe) {
					start.setText("Feil datoformat");
				}
				
			} 
		
			else if (innhold.getComponent(0).equals(lær)) {
				try{
					int nr = Integer.parseInt(tlf.getText());
					
					info.setText(skolen.getLærerne().addLærer(navn.getText(), 
								epost.getText(), 
								nr,
								kontorNr.getText()).fullString());
					
				}catch (NumberFormatException nfe){
					info.setText("Feil nummerformat");
				}
			} 
		
		else if (innhold.getComponent(0).equals(fag)) {
				try{
					int poeng = Integer.parseInt(studiepoeng.getText());
					if(!fagkode.getText().matches(fagkodeRegex)){
						fagkode.setText("Feil kodeformat.");
						return;
					}
					
					info.setText(skolen.getFagene().addFag(navn.getText(), 
							fagkode.getText(),
							beskrivelse.getText(),
							vurderingsform.getText(), 
							poeng, 
							(Laerer)velgLærer.getSelectedItem()).fullString());
					
				}catch (NumberFormatException nfe){
					fagkode.setText("Feil kodeformat");
				}catch (NullPointerException nfe){
					info.setText("Nullpointer-feil");
				}catch (IndexOutOfBoundsException iobe){
					info.setText("Finner ikke lærer");
				}
				
			} 
		
		else if (innhold.getComponent(0).equals(studprog)) {
				info.setText(skolen.getStudieprogrammene().addStudProg(navn.getText()).fullString());
			}
		}
		
		//************************************************************************************************************************************
		
		if (e.getSource() == leggtilfag) {
			if(innhold.getComponent(0).equals(studprog)){
				try{
					skolen.getStudieprogrammene().findEnByNavn(navn.getText()).addFag((Fag)velgFag.getSelectedItem());
					info.setText(skolen.getStudieprogrammene().findEnByNavn(navn.getText()).fullString());
				} catch (NullPointerException npe){
					info.setText("Ugyldig fagkode");
				}
			}
		}
		
		//Legg til fagene fra et studieprogram til en registrert student
		if (e.getSource() == settiprog) {
			if(innhold.getComponent(0).equals(stud)){ //TRENGER EN SJEKK FOR Å SE OM DET ER REGISTRERT NY STUDENT
				try{
					Student s =	skolen.getStudentene().findStudentByStudentNr("s" + (skolen.getStudentene().getStudentnummer() - 1));
					for(Fag f : ((Studieprogram) velgProg.getSelectedItem()).getFagene()){
						s.addFag(f);
					}
					info.setText(s.fullString());
				} catch (NullPointerException npe){
					npe.printStackTrace();
				}
			}
		}
		
		//***************************** Søk **************************************
		
		// søker på fritekst-søk, gjennom navn på alle tingene. Tror dette må
		// optimaliseres slik at vi ikke looper HELE datastrukturen vår hver
		// gang.
		if (e.getSource() == søkefelt || e.getSource() == søkeknapp) {
			
			//Oppretter en arraylist av typen som returneres av søk-metoden
			ArrayList<?> resultat = skolen.søk(søkefelt.getText(), selectedValue);
			
			//Sjekker at søket ikke returnerte null eller tom list, sjekker så hva slags objekt første element i listen er,
			//og viser et displayvindu av riktig type
			if(!(resultat == null || resultat.isEmpty())){
				
				if(resultat.get(0) instanceof Student){
					JList<Student> listen = studentboks.listify((ArrayList<Student>) resultat);
					vis(studentboks.visResultat(listen));
					
				} else if(resultat.get(0) instanceof Laerer){
					JList<Laerer> listen = laererboks.listify((ArrayList<Laerer>) resultat);
					vis(laererboks.visResultat(listen));
					
				} else if(resultat.get(0) instanceof Fag){
					JList<Fag> listen = fagboks.listify((ArrayList<Fag>) resultat);
					vis(fagboks.visResultat(listen));
					
				} else if(resultat.get(0) instanceof Studieprogram){
					JList<Studieprogram> listen = studieboks.listify((ArrayList<Studieprogram>) resultat);
					vis(studieboks.visResultat(listen));
				}
			} else{
				vis(new JPanel());
				setText("Ingen treff");
			}
		}
	}
}

