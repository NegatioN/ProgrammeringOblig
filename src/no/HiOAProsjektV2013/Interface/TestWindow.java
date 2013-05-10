package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

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
import no.HiOAProsjektV2013.Main.DateHandler;
import no.HiOAProsjektV2013.Main.ScriptClass;

/*
 * Hovedvinduet i programmet. Inneholder mesteparten av interfacen.
 * LE, skriv litt mer her xD
 */
public class TestWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	//checkbox burde sette selectedValue til en av disse, og passe value inn i search
	public static final int LÆRER = 30, STUDENT = 40, FAG = 50, STUDIEPROGRAM = 60, FØRSTE = 0;

	private Archiver arkivet;
	private VinduLytter vl;
	private Skole skolen;
	private Buttons buttonGenerator;
	private DateHandler dateHandler;
	private RightClickMenus popup = new RightClickMenus(this);
	private ListeBoks<Student> studentboks = new ListeBoks<>(this);
	private ListeBoks<Laerer> laererboks = new ListeBoks<>(this);
	private ListeBoks<Fag> fagboks = new ListeBoks<>(this);
	private ListeBoks<Studieprogram> studieboks = new ListeBoks<>(this);
	private JTextArea info;
	private JButton nystudent, nylærer, nyttfag, nyttstudieprog, visstudent,
			vislærer, visfag, visstudieprog, lagre, leggtilfag, søkeknapp, avansert, visAvansert, tilbake;
	private JRadioButton studentCheck, lærerCheck, fagCheck, studieCheck;
	private JTextField navn, epost, tlf, adresse, innDato, utDato, kontorNr, fagkode,
			beskrivelse, studiepoeng, søkefelt,vurderingsform, innÅr, utÅr, studNr;
	private JPanel rammeverk, innhold, stud, lær, fag, studprog, display;
	public static Dimension innholdSize = new Dimension(300,500), toppSize = new Dimension(900,50), søkSize = new Dimension(170,400);
	private Border ramme = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	private JComboBox<Fag> fagBox;
	private JComboBox<Laerer> lærerBox;
	private JComboBox<Studieprogram> progBox;
	private JComboBox<String> vurderingBox;
	private int type;
	//endre
	private int selectedValue = STUDENT;
	
	public static String fagkodeRegex = "\\D{4}\\d{4}";
	public static final String studentNrRegex = "s\\d{6}";
	public static final String årRegex = "\\d{4}";
	public static final String mobRegex = "\\d{8}";
	public static final String mailRegex = "\\S+@\\S+.\\S+";
	public static final String navnRegex = "\\S+\\s\\S+";
	public static final String datoRegex = "\\D";
	public static final String dateRegex1 = "\\d{2}\\W\\d{2}\\W\\d{4}";
	public static final String dateRegex2 = "\\d{2}\\W\\d{2}\\W\\d{2}";
	
	
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
		
		//lager dateHandler
		dateHandler = new DateHandler();
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
	//tar inn lister fra høyreclick-menyene.
	public void listApplier(ArrayList<?> listeobjekter, int qualifier){
		if(qualifier == STUDENT){
		JList<Student> listen = studentboks.listify((ArrayList<Student>)listeobjekter);
		vis(studentboks.visResultat(listen));
		}
		else if(qualifier == FAG){
			JList<Fag> listen = fagboks.listify((ArrayList<Fag>)listeobjekter);
			vis(fagboks.visResultat(listen));
		}
	}
	public RightClickMenus getRightClickMenu(){
		return popup;
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
		leggtil.setPreferredSize(toppSize);
		visning.setPreferredSize(søkSize);
		display.setPreferredSize(innholdSize);
		leggtil.setBorder(ramme);
		visning.setBorder(ramme);
		display.setBorder(ramme);
		
		søkefelt		= buttonGenerator.generateTextField("Søk", Buttons.KORT, new søkelytter());
		visning.add(søkefelt);
		
		søkeknapp 		= buttonGenerator.generateButton("Søk", visning, Buttons.HALV, new søkelytter());
		generateButtonGroup(visning);
		visAvansert 	= buttonGenerator.generateButton("Avansert søk", visning, Buttons.HALV);
		visning.add(Box.createRigidArea(Buttons.HALV));

		nystudent 		= buttonGenerator.generateButton("Legg til student", leggtil, Buttons.HALV);
		nylærer 		= buttonGenerator.generateButton("Legg til lærer", leggtil, Buttons.HALV);
		nyttfag 		= buttonGenerator.generateButton("Legg til fag", leggtil, Buttons.HALV);
		nyttstudieprog 	= buttonGenerator.generateButton("Legg til studieprogram", leggtil, new Dimension(200, 30));

		visstudent 		= buttonGenerator.generateButton("Vis studenter", visning, Buttons.HALV);
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
		navn	 		= buttonGenerator.generateTextField("Navn", 20);
		epost	 		= buttonGenerator.generateTextField("E-post", 20);
		tlf		 		= buttonGenerator.generateTextField("Telefon", 20);
		adresse			= buttonGenerator.generateTextField("Adresse", 20);
		innDato			= buttonGenerator.generateTextField("dag/mnd/år", 20);
		utDato			= buttonGenerator.generateTextField("Sluttdato", 20);
		kontorNr		= buttonGenerator.generateTextField("Kontornummer", 20);
		fagkode			= buttonGenerator.generateTextField("Fagkode", 20);
		beskrivelse		= buttonGenerator.generateTextField("Beskrivelse", 20);
		vurderingsform	= buttonGenerator.generateTextField("Vurderingsform", 20);
		studiepoeng		= buttonGenerator.generateTextField("Studiepoeng", 20);
		innÅr 			= buttonGenerator.generateTextField("Startår", 20);
		utÅr			= buttonGenerator.generateTextField("Sluttår", 20);
		studNr 			= buttonGenerator.generateTextField("StudentNr", 20);
	
		lagre 			= buttonGenerator.generateButton("Lagre", Buttons.HEL, new lagrelytter());
		leggtilfag 		= buttonGenerator.generateButton("Legg til fag", Buttons.HEL);
		avansert 		= buttonGenerator.generateButton("Søk", Buttons.HEL, new søkelytter());
		tilbake 		= buttonGenerator.generateButton("Tilbake", Buttons.HEL);
		
		fagBox = new JComboBox<Fag>();
		fagBox.setPreferredSize(Buttons.HEL);
		for(Fag f : skolen.getFagene().visAlle()) {
			fagBox.addItem(f);
		}
		
		lærerBox = new JComboBox<Laerer>();
		lærerBox.setPreferredSize(Buttons.HEL);
		for(Laerer l : skolen.getLærerne().visAlle()) {
			lærerBox.addItem(l);
		}
		
		progBox = new JComboBox<Studieprogram>();
		progBox.setPreferredSize(Buttons.HEL);
		for(Studieprogram sp : skolen.getStudieprogrammene().visAlle()) {
			progBox.addItem(sp);
		}
		
		String[] boxitems =  {"Muntlig", "Skriftlig", "Prosjekt"};
		vurderingBox = new JComboBox<String>(boxitems);
		vurderingBox.setPreferredSize(Buttons.HEL);

		innhold = new JPanel();
		innhold.setBorder( ramme);
		
		rammeverk.add(innhold, BorderLayout.CENTER);
		revalidate();
	}
		
	//Metoder for å vise relevante felter for registrering av objekter
	public void student() {
		stud = new JPanel();
		stud.setPreferredSize(innholdSize);

		stud.add(navn);
		stud.add(epost);
		stud.add(tlf);
		stud.add(adresse);
		stud.add(innDato);
		stud.add(progBox);
		stud.add(lagre);
		progBox.setSelectedIndex(-1);
		
		vis(stud);
	}
	public void lærer() {
		lær = new JPanel();
		lær.setPreferredSize(innholdSize);

		lær.add(navn);
		lær.add(epost);
		lær.add(tlf);
		lær.add(kontorNr);
		lær.add(lagre);

		vis(lær);
	}
	public void fag() {
		fag = new JPanel();
		fag.setPreferredSize(innholdSize);
		
		fag.add(navn);
		fag.add(fagkode);
		fag.add(beskrivelse);
		fag.add(studiepoeng);
		fag.add(vurderingBox);
		fag.add(lærerBox);
		fag.add(lagre);
		
		vis(fag);
	}
	public void studieprog() {
		studprog = new JPanel();
		studprog.setPreferredSize(innholdSize);
		
		studprog.add(navn);
		studprog.add(lagre);
		studprog.add(Box.createRigidArea(Buttons.HEL));
		studprog.add(fagBox);
		studprog.add(leggtilfag);
		fagBox.setVisible(false);
		leggtilfag.setVisible(false);
		vis(studprog);
	}
	public void avansert(int type){
		JPanel søk = new JPanel();
		søk.setPreferredSize(innholdSize);
		
		final int STUDENTFAG = 1, STUDENTPERIODE = 2, STUDENTPROGRAM = 3, POENGSTUDENT = 4, KARAKTER = 5;
		
		refresh();
		
		Buttons b = new Buttons(new søkelytter());
		
		switch(type){
		case STUDENTFAG:
			søk.add(fagBox);
			søk.add(innÅr = b.generateTextField("År", Buttons.LANG, new søkelytter()));
			søk.add(avansert);
			søk.add(tilbake);
			break;
		case STUDENTPERIODE:
			søk.add(innÅr);
			søk.add(utÅr);
			søk.add(avansert);
			søk.add(tilbake);
			break;
		case STUDENTPROGRAM:
			søk.add(progBox);
			søk.add(innÅr);
			søk.add(avansert);
			søk.add(tilbake);
			break;
		case POENGSTUDENT:
			søk.add(studNr);
			søk.add(innÅr);
			søk.add(utÅr);
			søk.add(avansert);
			søk.add(tilbake);
			break;
		case KARAKTER:
			søk.add(fagBox);
			søk.add(innDato = b.generateTextField("Dato", Buttons.LANG));
			søk.add(avansert);
			søk.add(tilbake);
			break;
		default:
			søk.add(b.generateButton("Finn studenter med fag", Buttons.HEL));
			søk.add(b.generateButton("Finn studenter i periode", Buttons.HEL));
			søk.add(b.generateButton("Finn studenter i studieprogram", Buttons.HEL));
			søk.add(b.generateButton("Finn studiepoeng for student", Buttons.HEL));
			søk.add(b.generateButton("Finn karakterfordeling", Buttons.HEL));
		}

		vis(søk);
	}
	
	//Resetter tekstfeltene
	public void refresh(){
		
		navn			.setText("Navn");
		epost			.setText("E-post");
		tlf				.setText("Telefon");
		adresse			.setText("Adresse");
		innDato			.setText("dag/mnd/år");
		utDato			.setText("Sluttdato");
		kontorNr		.setText("Kontornummer");
		fagkode			.setText("Fagkode");
		beskrivelse		.setText("Beskrivelse");
		vurderingsform	.setText("Vurderingsform");
		studiepoeng		.setText("Studiepoeng");
		innÅr 			.setText("Startår");
		utÅr			.setText("Sluttår");
		studNr 			.setText("StudentNr");
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
		vis();
		innhold.getComponent(FØRSTE).setVisible(false);
		innhold.add(c);
		innhold.updateUI();
		revalidate();
	}
	public void vis(){
		innhold.getComponent(FØRSTE).setVisible(true);
		if(innhold.getComponentCount() > 1)
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
	//Viser karakterdistribusjon i displayet
	public void displayKarakterer(int[] karakterer, double stryk){
		String distribusjon = "Karakterdistribusjon:\n";
		for(int i = 0; i < 6; i++ )
			distribusjon += (char)('A'+i) + ":" + karakterer[i] + "\n";
		distribusjon += "\nStrykprosent: " + stryk;
		setText(distribusjon);
		display();
	}
	
	public void actionPerformed(ActionEvent e) {
		//Nullstiller displayet
		display();
		setText(""); 
		//setter selectedValue for programmet sånn at vi får filtrert hva vi søker mot.
		if(e.getSource() == studentCheck || e.getSource() == lærerCheck || e.getSource() == fagCheck || e.getSource() == studieCheck)
		selectedValue = Integer.parseInt(e.getActionCommand());
		
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
		
		if (e.getSource() == visAvansert){
			avansert(type = 0);
		}
		if (e.getSource() == tilbake){
			avansert(type = 0);
		}
				
		if (e.getSource() == leggtilfag) {
			if(innhold.getComponent(FØRSTE).equals(studprog)){
				try{
					skolen.getStudieprogrammene().findEnByNavn(navn.getText()).addFag((Fag)fagBox.getSelectedItem());
					info.setText(skolen.getStudieprogrammene().findEnByNavn(navn.getText()).fullString());
				} catch (NullPointerException npe){
					info.setText("Ugyldig fagkode");
				}
			}
		}
	}
	
	private class søkelytter implements ActionListener{
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == søkefelt || e.getSource() == søkeknapp) {

				//Oppretter en arraylist av typen som returneres av søk-metoden
				ArrayList<?> resultat = skolen.søk(søkefelt.getText(), selectedValue);

				//Sjekker at søket ikke returnerte null eller tom list, sjekker så hva slags objekt første element i listen er,
				//og viser et displayvindu av riktig type
				if(!(resultat == null || resultat.isEmpty())){

					if(resultat.get(FØRSTE) instanceof Student){
						JList<Student> listen = studentboks.listify((ArrayList<Student>) resultat);
						vis(studentboks.visResultat(listen));

					} else if(resultat.get(FØRSTE) instanceof Laerer){
						JList<Laerer> listen = laererboks.listify((ArrayList<Laerer>) resultat);
						vis(laererboks.visResultat(listen));

					} else if(resultat.get(FØRSTE) instanceof Fag){
						JList<Fag> listen = fagboks.listify((ArrayList<Fag>) resultat);
						vis(fagboks.visResultat(listen));

					} else if(resultat.get(FØRSTE) instanceof Studieprogram){
						JList<Studieprogram> listen = studieboks.listify((ArrayList<Studieprogram>) resultat);
						vis(studieboks.visResultat(listen));
					}
				} else{
					vis(new JPanel());
					setText("Ingen treff");
				}
			} 
			
			else if(e.getSource() == avansert){
				String inn = innÅr.getText();
				String ut = utÅr.getText();
				String nr = studNr.getText();
				String d = innDato.getText();

				switch(type){
				case 1:
					JList<Student> listen = null;
//					if(fk.matches(fagkodeRegex)){
						if(inn.matches(årRegex))
							listen = studentboks.listify(skolen.findStudentMedFagByÅr(((Fag)fagBox.getSelectedItem()).getFagkode(),Integer.parseInt(inn)));
						else
							listen = studentboks.listify(skolen.findStudentMedFag(((Fag)fagBox.getSelectedItem()).getFagkode()));
					vis(studentboks.visResultat(listen));
//					}
					break;
				case 2:
					listen = null;
					if(inn.matches(årRegex)){
						if(ut.matches(årRegex))
							System.out.println("Lage en periodemetode");//listen = studentboks.listify(skolen.findStudentByPeriode(inn,ut);
						else
							listen = studentboks.listify(skolen.findStudentByStart(inn));
					} else
						listen = studentboks.listify(skolen.findStudentBySlutt(ut));
					vis(studentboks.visResultat(listen));
					break;
				case 3:
					listen = null;
					Studieprogram sp = (Studieprogram)progBox.getSelectedItem();
					if(inn.matches(årRegex)){
						listen = studentboks.listify(skolen.findStudentByStudieprogramByStart(sp, Integer.parseInt(inn)));
					} else
						listen = studentboks.listify(skolen.findStudentsByStudieprogram(sp.getNavn()));
					if(listen != null)
						vis(studentboks.visResultat(listen));
					break;
				case 4:
					int poeng = 0;
					if(nr.matches(studentNrRegex)){
						Student s = skolen.getStudentene().findStudentByStudentNr(nr);
						if(inn.matches(årRegex)){
							if(ut.matches(årRegex))
								poeng = skolen.findStudiepoengForStudIPeriode(s, inn, ut);
							else
								poeng = skolen.findStudiepoengForStudIPeriode(s, inn, "3000");
						} else if(ut.matches(årRegex))
							poeng = skolen.findStudiepoengForStudIPeriode(s, "1000", ut);
						else
							poeng = skolen.findStudiepoengForStudIPeriode(s, "1000", "3000");
						setText("Studiepoeng for " + s.getfNavn() + " " + s.geteNavn() + ": " + poeng);
					}
					break;
				case 5:
//					if(fk.matches(fagkodeRegex)){
						Fag f = (Fag)fagBox.getSelectedItem();
						int[] karakterer = null;
						if(d.matches(årRegex)){
							karakterer = skolen.findKarakterDistribusjon(f, Integer.parseInt(d));
							double stryk = skolen.findStrykProsent(f, Integer.parseInt(d) );
							displayKarakterer(karakterer, stryk);
						} else{
							GregorianCalendar dato = dateHandler.dateFixer(d, null);
							if(dato == null){
								JOptionPane.showMessageDialog(innhold, "Skriv inn år eller eksamensdato");
								return;
							}
							karakterer = skolen.findKarakterDistribusjon(f, Integer.parseInt(d));
							double stryk = skolen.findStrykProsent(f, Integer.parseInt(d) );
							displayKarakterer(karakterer, stryk);
						}
					break;
				default:
					avansert(0);
				}

			} else {

				JButton knapp = (JButton)e.getSource();
				
				switch(knapp.getText()){
				case "Finn studenter med fag":
					type = 1;
					break;
				case "Finn studenter i periode":
					type = 2;
					break;
				case "Finn studenter i studieprogram":
					type = 3;				
					break;
				case "Finn studiepoeng for student":
					type = 4;				
					break;
				case "Finn karakterfordeling":
					type = 5;			
					break;
				default:
					type = 0;
				}
				avansert(type);
			}
		}
	}
	
	private class lagrelytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == lagre) {
				
				if (innhold.getComponent(FØRSTE).equals(stud)) { //Sjekker hvilket panel som ligger i innhold-panelet
					
					try {
						int nr = Integer.parseInt(tlf.getText());
						//regex-checks på input.
						if(!navn.getText().matches(navnRegex)){
							navn.setText("Fornavn og Etternavn");
							return;
						}
						else if(!epost.getText().matches(mailRegex)){
							epost.setText("Feil epost-format.");
							return;
						}
						else if(!tlf.getText().matches(mobRegex)){
							tlf.setText("Feil nummerformat");
							return;
						}
						//setter dato i følge input.
						String dateString = innDato.getText();
								GregorianCalendar dato = dateHandler.dateFixer(dateString, null);
								//hvis dato null er input invalid på en eller annen måte.
								if(dato == null){
									innDato.setText("Feil dato-format:");
									return;
								}
								
								//oppretter Studentobjektet.
								Student s = skolen.getStudentene().addStudent(navn.getText(), 
										epost.getText(), 
										nr,
										adresse.getText(), 
										dato);
								if(progBox.getSelectedIndex() != -1)
									s.setStudieprogram((Studieprogram)progBox.getSelectedItem());
								
								setText(s.fullString());
							
					} catch (NumberFormatException nfe){
						tlf.setText("Feil nummerformat");
					}
					
				} 
			
				else if (innhold.getComponent(FØRSTE).equals(lær)) {
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
			
			else if (innhold.getComponent(FØRSTE).equals(fag)) {
					try{
						int poeng = Integer.parseInt(studiepoeng.getText());
						if(!fagkode.getText().matches(fagkodeRegex)){
							fagkode.setText("Feil kodeformat.");
							return;
						}
						
						info.setText(skolen.getFagene().addFag(navn.getText(), 
								fagkode.getText(),
								beskrivelse.getText(),
								vurderingBox.getSelectedItem().toString(), 
								poeng, 
								(Laerer)lærerBox.getSelectedItem()).fullString());
						
					}catch (NumberFormatException nfe){
						fagkode.setText("Feil kodeformat");
					}catch (NullPointerException nfe){
						info.setText("Nullpointer-feil");
					}catch (IndexOutOfBoundsException iobe){
						info.setText("Finner ikke lærer");
					}
					
				} 
			
			else if (innhold.getComponent(FØRSTE).equals(studprog)) {
					info.setText(skolen.getStudieprogrammene().addStudProg(navn.getText()).fullString());
					fagBox.setVisible(true);
					leggtilfag.setVisible(true);
				}
			}
		}
	}
}

