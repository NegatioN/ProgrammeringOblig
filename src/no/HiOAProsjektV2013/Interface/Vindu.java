package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
 * Hovedvinduet i programmet. Inneholder mesteparten av interfacen. Oppretter og fyller ut forskjellige paneler som er konstante i vinduet,
 * samt paneler for oppretting av nye studenter, fag osv. 
 */
public class Vindu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	//checkbox burde sette selectedValue til en av disse, og passe value inn i search
	public static final int LÆRER = 30, STUDENT = 40, FAG = 50, STUDIEPROGRAM = 60, FØRSTE = 0, ANDRE = 1, TITTELSIZE = 20, TOM = 0, BLANK = -1;
	private final static int VELGSØK = 0, STUDENTFAG = 1, STUDENTPERIODE = 2, STUDENTPROGRAM = 3, POENGSTUDENT = 4, KARAKTER = 5;
	private final static int COVER = 2, COVERED = 3;
	private final static String FORTID = "1000", FREMTID = "3000";

	public static final String fagkodeRegex = "[\\wæøåÆØÅ]{4}\\d{4}";
	public static final String studentNrRegex = "s\\d{6}";
	public static final String årRegex = "\\d{4}";
	public static final String mobRegex = "\\d{8}";
	public static final String mailRegex = "\\S+@\\S+[.]\\S+";
	public static final String navnRegex = "(?:([a-zA-ZæøåÆØÅ']+\\s+[a-zA-ZæøåÆØÅ']+\\s*)){1}(?:([a-zA-ZæøåÆØÅ']+\\s*))*";
	public static final String tittelRegex = "(\\D+)||(\\w+\\s\\S+)";
	public static final String dateRegex = "(\\d{1,2}\\W\\d{1,2}\\W((\\d{4})||(\\d{2})))";
	public static final String sPoengRegex = "(\\d)||([01-5]\\d)||60";

	private Archiver arkivet;
	private VinduLytter vl;
	private Skole skolen;
	private Buttons buttonGenerator;
	private DateHandler dateHandler;
	private RightClickMenus popup = new RightClickMenus(this);
	private ListeBoks<Student> studentboks;
	private ListeBoks<Laerer> laererboks;
	private ListeBoks<Fag> fagboks;
	private ListeBoks<Studieprogram> studieboks;
	private JComboBox<Fag> fagBox;
	private JComboBox<Laerer> lærerBox;
	private JComboBox<Studieprogram> progBox;
	private JComboBox<String> vurderingBox;
	private JTextArea info;
	private JButton nystudent, nylærer, nyttfag, nyttstudieprog, visstudent,
	vislærer, visfag, visstudieprog, lagre, leggtilfag, søkeknapp, avansert, visAvansert, tilbake;
	private JRadioButton studentCheck, lærerCheck, fagCheck, studieCheck;
	private InputFelt navn, tittel, epost, tlf, adresse, innDato, utDato, kontorNr, fagkode,
	beskrivelse, studiepoeng, innÅr, utÅr, studNr, søkefelt;
	private JPanel rammeverk, innhold, stud, lær, fag, studprog, display;
	public static Dimension innholdSize = new Dimension(300,500), toppSize = new Dimension(900,50), søkSize = new Dimension(170,500), totalSize = new Dimension(900,500);
	private Border ramme = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	private int type;
	private JLabel overskrift;
	//endre
	private int selectedValue = STUDENT;

	private JMenuBar meny;

	public Vindu(String tittel) {

		super(tittel);
		//oppretter buttongeneratoren som sparer linjer på gjentagende oppgaver.
		buttonGenerator = new Buttons(this);
		//oppretter save-objektet
		arkivet 	= new Archiver();
		//Oppretter gammelt objekt om det fins, eller nytt om vi ikke har et.
		skolen 		= arkivet.readFromFile();
		//Oppretter vinduslytter
		vl = new VinduLytter(this);

		//Oppretter listeboks for visning av søkeresultat
		studentboks = new ListeBoks<>(this);
		laererboks	= new ListeBoks<>(this);
		fagboks 	= new ListeBoks<>(this);
		studieboks 	= new ListeBoks<>(this);

		//script for å generere fag, studenter og lærere
		//kommenter den ut etter 1 generate
		ScriptClass sc = new ScriptClass(skolen);

		//Panel som inneholder alle de andre panelene og fordeler dem i vinduet
		rammeverk = new JPanel(new BorderLayout());
		add(rammeverk);
		fyllRamme(); //Fyller rammeverket

		//DateHandler for håndtering av datoinput
		dateHandler = new DateHandler();
		//Setter inn toppmeny
		populateMenu();

		pack();

		//setter icon til framen
		Image img = Toolkit.getDefaultToolkit().getImage("src/icons/icon.png");
		this.setIconImage(img);

		setVisible(true);
		setLocationRelativeTo(null);
		setMinimumSize(totalSize);
		setResizable(true);
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

			søkefelt		= new InputFelt("Søk", InputFelt.KORT, new søkelytter());
			visning.add(søkefelt);

			søkeknapp 		= buttonGenerator.generateButton("Søk", visning, Buttons.HALV, new søkelytter());
			generateButtonGroup(visning);
			visAvansert 	= buttonGenerator.generateButton("Avansert søk", visning, Buttons.HALV);
			visning.add(Box.createRigidArea(Buttons.HALV));

			nystudent 		= buttonGenerator.generateButton("Legg til student", leggtil, Buttons.HALV);
			nylærer 		= buttonGenerator.generateButton("Legg til lærer", leggtil, Buttons.HALV);
			nyttfag 		= buttonGenerator.generateButton("Legg til fag", leggtil, Buttons.HALV);
			nyttstudieprog 	= buttonGenerator.generateButton("Legg til studieprogram", leggtil, Buttons.HEL);

			visstudent 		= buttonGenerator.generateButton("Vis studenter", visning, Buttons.HALV);
			vislærer 		= buttonGenerator.generateButton("Vis lærere", visning, Buttons.HALV);
			visfag 			= buttonGenerator.generateButton("Vis fag", visning, Buttons.HALV);
			visstudieprog 	= buttonGenerator.generateButton("Vis studieprogram", visning, Buttons.HALV);

			info = new JTextArea(23, 23);
			info.setBorder(BorderFactory.createLoweredBevelBorder());
			info.setEditable(false);
			info.setLineWrap(true);
			setText("\n\n\n\n\n Velkommen til vår studieadmininistrasjon!\n" +
					"                       Her er alt mulig!");

			display.add(info);
			rammeverk.add(display, BorderLayout.WEST);
			rammeverk.add(leggtil, BorderLayout.NORTH);
			rammeverk.add(visning, BorderLayout.EAST);

			//Oppretter objekter til registreringsfelter
			overskrift = new JLabel();
			overskrift.setFont(new Font("Arial", Font.BOLD, TITTELSIZE));

			navn	 		= new InputFelt("Navn", InputFelt.LANG, navnRegex);
			tittel			= new InputFelt("Navn", InputFelt.LANG, tittelRegex);
			epost	 		= new InputFelt("E-post", InputFelt.LANG, mailRegex);
			tlf		 		= new InputFelt("Telefon", InputFelt.LANG, mobRegex);
			adresse			= new InputFelt("Adresse", InputFelt.LANG);
			innDato			= new InputFelt("Startdato", InputFelt.LANG, dateRegex);
			utDato			= new InputFelt("Sluttdato", InputFelt.LANG, dateRegex);
			kontorNr		= new InputFelt("Kontornummer", InputFelt.LANG);
			fagkode			= new InputFelt("Fagkode", InputFelt.LANG, fagkodeRegex);
			beskrivelse		= new InputFelt("Beskrivelse", InputFelt.LANG);
			studiepoeng		= new InputFelt("Studiepoeng", InputFelt.LANG,  sPoengRegex);
			innÅr 			= new InputFelt("Startår", InputFelt.LANG,  årRegex);
			utÅr			= new InputFelt("Sluttår", InputFelt.LANG,  årRegex);
			studNr 			= new InputFelt("StudentNr", InputFelt.LANG,  studentNrRegex);

			lagre 			= buttonGenerator.generateButton("Lagre", Buttons.HEL, new lagrelytter());
			leggtilfag 		= buttonGenerator.generateButton("Legg til fag", Buttons.HEL);
			avansert 		= buttonGenerator.generateButton("Søk", Buttons.HEL, new søkelytter());
			tilbake 		= buttonGenerator.generateButton("Tilbake", Buttons.HEL);

			fagBox = new JComboBox<Fag>();
			fagBox.setPreferredSize(Buttons.HEL);
			for(Fag f : skolen.getFagene().getAlle()) {
				fagBox.addItem(f);
			}

			lærerBox = new JComboBox<Laerer>();
			lærerBox.setPreferredSize(Buttons.HEL);
			for(Laerer l : skolen.getLærerne().getAlle()) {
				lærerBox.addItem(l);
			}

			progBox = new JComboBox<Studieprogram>();
			progBox.setPreferredSize(Buttons.HEL);
			for(Studieprogram sp : skolen.getStudieprogrammene().getAlle()) {
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
		JMenuItem brukerveiledning = new JMenuItem("Brukerveiledning");
		brukerveiledning.setMnemonic('B');
		brukerveiledning.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String userbeskrivelse = "Fra hovedmenyen kan du velge å lage nye studenter, lærere, fag og studieprogram. Når du velger en av disse, vil det komme frem nødvendige registreringsfelt. Registreringsfeltene vil hele tiden gi deg tilbakemelding på om det du har skrevet er tillatte verdier." +
						"\n\nVidere er det muligheter for å søke på alle disse tingene i søkefeltet til høyre, hvor man kan søke på identiske treff, eller bare delvise treff. Man må velge hvilken type man skal søke etter i programmet ved hjelp av radioknappene under.\n\n" +
						"Ved mer spesielle søk enn navn, fagkode og studentnummer må man søke ved hjelp av de avanserte søkene, som man finner en knapp til rett under." +
						"\nDet er også knapper for å vise alle objekter av en type, som fag, lærere, studenter og studieprogram.\n\n" +
						"Når en liste kommer opp, kan man høyreklikke på et valgt objekt for å få opp relevante alternativer for hva man kan gjøre med det. \nOm man ønsker videre handlinger med objektet, kan man trykke rediger-knappen under lista. Dette tar deg videre til et mer spesialisert område.";
				JOptionPane optionPane = new limitedOptionPane();
				optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
				optionPane.setMessage(userbeskrivelse);
				JDialog dialog = optionPane.createDialog(null, "Brukerveiledning");
				dialog.setVisible(true);
			}
		});

		om.add(omProgrammet);
		om.add(omOss);
		om.add(brukerveiledning);


		meny.add(fil);
		meny.add(om);


		setJMenuBar(meny);
	}
	//tar inn lister fra høyreclick-menyene.
	@SuppressWarnings("unchecked")
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
	public void setSelectedValue(int i){
		selectedValue = i;
	}
	public Buttons getButtonGen() {
		return buttonGenerator;
	}


	//Metoder for å vise relevante felter for registrering av objekter
	public void studentPanel() {
		stud = new JPanel();
		stud.setPreferredSize(innholdSize);

		overskrift.setText("Registrer student");
		stud.add(overskrift);
		stud.add(navn);
		stud.add(epost);
		stud.add(tlf);
		stud.add(adresse);
		stud.add(innDato);
		stud.add(progBox);
		stud.add(lagre);
		progBox.setSelectedIndex(BLANK);

		vis(stud);
	}
	public void lærerPanel() {
		lær = new JPanel();
		lær.setPreferredSize(innholdSize);

		overskrift.setText("Registrer lærer");
		lær.add(overskrift);
		lær.add(navn);
		lær.add(epost);
		lær.add(tlf);
		lær.add(kontorNr);
		lær.add(lagre);

		vis(lær);
	}
	public void fagPanel() {
		fag = new JPanel();
		fag.setPreferredSize(innholdSize);

		overskrift.setText("Registrer fag");
		fag.add(overskrift);
		fag.add(tittel);
		fag.add(fagkode);
		fag.add(beskrivelse);
		fag.add(studiepoeng);
		fag.add(vurderingBox);
		fag.add(lærerBox);
		fag.add(lagre);

		vis(fag);
	}
	public void studieprogPanel() {
		studprog = new JPanel();
		studprog.setPreferredSize(innholdSize);

		overskrift.setText("Registrer studieprogram");
		studprog.add(overskrift);
		studprog.add(tittel);
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

		refresh();

		Buttons b = new Buttons(new søkelytter());
		overskrift.setText("Avansert søk");
		søk.add(overskrift);
		switch(type){
		case STUDENTFAG:
			søk.add(fagBox);
			søk.add(innÅr);
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
			søk.add(innDato);
			søk.add(innÅr);
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

	
	//Metoder som oppdaterer innholdspanelet
	
	//Resetter tekstfeltene til standardverdier
	public void refresh(){
				navn			.setText("Navn");
				tittel			.setText("Navn");
				epost			.setText("E-post");
				tlf				.setText("Telefon");
				adresse			.setText("Adresse");
				innDato			.setText("Startdato");
				utDato			.setText("Sluttdato");
				kontorNr		.setText("Kontornummer");
				fagkode			.setText("Fagkode");
				beskrivelse		.setText("Beskrivelse");
				studiepoeng		.setText("Studiepoeng");
				innÅr 			.setText("Startår");
				utÅr			.setText("Sluttår");
				studNr 			.setText("StudentNr");
			}
	//Oppdaterer innholdspanelet, og legger til gitt komponent
	public void vis(JPanel p){
		refresh(); //Oppdaterer tekstfeltene for å ikke dra med verdier fra forrige bruk
		innhold.removeAll(); //Passer på at det ikke hoper seg opp med innhold
		innhold.add(overskrift);
		innhold.add(p);
		innhold.updateUI();
	}
	//Viser paneler i innholdspanelet uten å fjerne søkersultatene, som ligger der fra før. Brukes fra EditPanel
	public void cover(JPanel p){
		vis();	//Passer på at det ikke legges til to "cover"-paneler oppå hverandre
		innhold.getComponent(FØRSTE).setVisible(false); //Gjemmer overskrift og søkeresultat
		innhold.getComponent(ANDRE).setVisible(false);
		innhold.add(p);
		innhold.updateUI();
	}
	//Fjerne "Cover"-panel og vise søkeresultat igjen
	public void vis(){
		innhold.getComponent(FØRSTE).setVisible(true);
		innhold.getComponent(ANDRE).setVisible(true);
		if(innhold.getComponentCount() == COVERED) //Fjerner "coveret" hvis det finnes. Det vil alltid være maks 3 komponenter i innhold
			innhold.remove(COVER); //Det ekstra panelet har alltid index 2
		innhold.updateUI();
	}
		
	//Metoder som oppdaterer display-panelet
		
	//Oppdaterer display-panelet med gitt komponent, som oftest et panel
	public void display(Component c){
		display.removeAll();
		display.add(c);
		display.updateUI();
	}
	//Får opp igjen display-tekstområdet i displaypanelet
	public void display(){
		display.removeAll();
		display.add(info);
		display.updateUI();
	}
	//Endrer teksten i display-tekstområdet
	public void setText(String tekst){
			info.setText(tekst);
		}
	//Viser karakterdistribusjon i displayet
	public void displayKarakterer(int[] karakterer, double stryk){
		String distribusjon = "Karakterdistribusjon:\n";
		for(int i = 0; i < (int) ('F'-'A'); i++ )	//Går igjennom bokstavene fra A til F
			distribusjon += (char)('A'+i) + ":" + karakterer[i] + "\n";
		distribusjon += "\nStrykprosent: " + stryk;
		setText(distribusjon);
		display();
	}
	
	
	//Metode som sjekker om alle tekstfelt i et panel inneholder godkjent tekst til lagring
	public boolean inputGodkjent(JPanel aktivt){
			boolean godkjent = true;
			
			for(Component c: aktivt.getComponents()){
				if(c instanceof InputFelt){
					if(!((InputFelt)c).matcher()) {
						godkjent = false;
					}
				}
			}
			return godkjent;
		}

	//Lytterklasser
	
	//Lytterklasse som tar imot events fra diverse knapper som utfører mindre oppgaver
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		//setter selectedValue for programmet sånn at vi får filtrert hva vi søker mot.
		if(source == studentCheck || source == lærerCheck || source == fagCheck || source == studieCheck){
			selectedValue = Integer.parseInt(e.getActionCommand());
			return;
		}
		
		//Nullstiller displayet
		display();
		setText(""); 

		//Åpner registreringspaneler for de ulike objektene
		if (source == nystudent) {
			studentPanel();
		}
		if (source == nylærer) {
			lærerPanel();
		}
		if (source == nyttfag) {
			fagPanel();
		}
		if (source == nyttstudieprog) {
			studieprogPanel();
		}
		
		//Viser alle registrerte studenter, lærere, fag eller studieprogram
		if (source == visstudent) {
			overskrift.setText("Studenter");
			vis(studentboks.visResultat(studentboks.listify(skolen.getStudentene().getAlle())));
		}
		if (source == vislærer) {
			overskrift.setText("Lærere");
			vis(laererboks.visResultat(laererboks.listify(skolen.getLærerne().getAlle())));
		}
		if (source == visfag) {
			overskrift.setText("Fag");
			vis(fagboks.visResultat(fagboks.listify(skolen.getFagene().getAlle())));
		}
		if (source == visstudieprog) {
			overskrift.setText("Studieprogram");
			vis(studieboks.visResultat(studieboks.listify(skolen.getStudieprogrammene().getAlle())));
		}

		//Viser avansert søk-panelet
		if (source == visAvansert || source == tilbake){
			avansert(type = Vindu.VELGSØK);
		}

		//Legger til et fag i et studieprogram
		if (source == leggtilfag) {
			if(innhold.getComponent(ANDRE).equals(studprog)){
				try{
					Studieprogram sp = skolen.getStudieprogrammene().findEnByNavn(tittel.getText()); 
					sp.addFag((Fag)fagBox.getSelectedItem());
					setText(sp.fullString());
				} catch (NullPointerException npe){
					setText("Ikke lagret studieprogram");
				}
			}
		}
	}

	//Lytterklasse for alle komponenter som har med søk å gjøre
	private class søkelytter implements ActionListener{
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
			//Nullstiller displayet
			display();
			setText(""); 
			
			try{
				if (e.getSource() == søkefelt || e.getSource() == søkeknapp) {

					if(søkefelt.getText().length() == TOM ){
						setText("Ingen treff");
						overskrift.setText("Ingen treff");
						return;
					}
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
						overskrift.setText("Søkeresultat");
					} else{
						vis(new JPanel());
						overskrift.setText("Ingen treff");
						setText("Ingen treff");
					}
				} 

				else if(e.getSource() == avansert){
					String inn = innÅr.getText();
					String ut = utÅr.getText();
					String nr = studNr.getText();
					String d = innDato.getText();

					switch(type){
					case Vindu.STUDENTFAG:
						JList<Student> listen = null;
						if(inn.matches(årRegex))
							listen = studentboks.listify(skolen.findStudentMedFagByÅr(((Fag)fagBox.getSelectedItem()).getFagkode(),Integer.parseInt(inn)));
						else
							listen = studentboks.listify(skolen.findStudentMedFag(((Fag)fagBox.getSelectedItem()).getFagkode()));
						vis(studentboks.visResultat(listen));
						break;
						
					case Vindu.STUDENTPERIODE:
						listen = null;

						if(inn.matches(årRegex)){
							if(ut.matches(årRegex))
								listen = studentboks.listify(skolen.findStudentByPeriode(inn,ut));
							else
								listen = studentboks.listify(skolen.findStudentByStart(inn));
						} else
							listen = studentboks.listify(skolen.findStudentBySlutt(ut));
						vis(studentboks.visResultat(listen));
						break;
						
					case Vindu.STUDENTPROGRAM:
						listen = null;
						Studieprogram sp = (Studieprogram)progBox.getSelectedItem();
						if(inn.matches(årRegex)){
							listen = studentboks.listify(skolen.findStudentByStudieprogramByStart(sp, Integer.parseInt(inn)));
						} else
							listen = studentboks.listify(skolen.findStudentsByStudieprogram(sp.getNavn()));
						if(listen != null)
							vis(studentboks.visResultat(listen));
						break;
						
					case Vindu.POENGSTUDENT:
						int poeng = 0;
						Student s = skolen.getStudentene().findStudentByStudentNr(nr);
						if(inn.matches(årRegex)){
							if(ut.matches(årRegex))
								poeng = skolen.findStudiepoengForStudIPeriode(s, inn, ut);
							else
								poeng = skolen.findStudiepoengForStudIPeriode(s, inn, Vindu.FREMTID);
						} else if(ut.matches(årRegex))
							poeng = skolen.findStudiepoengForStudIPeriode(s, Vindu.FORTID, ut);
						else
							poeng = skolen.findStudiepoengForStudIPeriode(s, Vindu.FORTID, Vindu.FREMTID);
						setText("Studiepoeng for " + s.getfNavn() + " " + s.geteNavn() + ": " + poeng);
						break;
						
					case Vindu.KARAKTER:
						Fag f = (Fag)fagBox.getSelectedItem();
						int[] karakterer = null;
						if(d.matches(dateRegex)){
							karakterer = skolen.findKarakterDistribusjon(f,f.findEksamenByDate(dateHandler.dateFixer(d, null)));
							double stryk = skolen.findStrykProsent(f, dateHandler.dateFixer(d, null).YEAR );
							displayKarakterer(karakterer, stryk);
						} else if (inn.matches(årRegex)){
							karakterer = skolen.findKarakterDistribusjon(f, Integer.parseInt(inn));
							double stryk = skolen.findStrykProsent(f, Integer.parseInt(inn) );
							displayKarakterer(karakterer, stryk);
						} else
							//overskrift.setText("Fyll inn nødvendige felter");
							setText("Fyll inn nødvendige felter");
						break;
						
					default:
						avansert(Vindu.VELGSØK);
					}

				} else {

					JButton knapp = (JButton)e.getSource();

					switch(knapp.getText()){
					case "Finn studenter med fag":
						type = Vindu.STUDENTFAG;
						break;
					case "Finn studenter i periode":
						type = Vindu.STUDENTPERIODE;
						break;
					case "Finn studenter i studieprogram":
						type = Vindu.STUDENTPROGRAM;				
						break;
					case "Finn studiepoeng for student":
						type = Vindu.POENGSTUDENT;				
						break;
					case "Finn karakterfordeling":
						type = Vindu.KARAKTER;			
						break;
					default:
						type = Vindu.VELGSØK;
					}
					avansert(type);
				}
			}catch (NumberFormatException nfe){
				setText("Fyll ut all nødvendige felter");
			}catch (NullPointerException nfe){
				setText("Fyll ut all nødvendige felter");
			}
		}
	}
	private class lagrelytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			JPanel aktivtPanel = (JPanel)innhold.getComponent(ANDRE);
			if(!inputGodkjent(aktivtPanel)){
				setText("Fyll inn nødvendige felter");
				return;
			}

			if (aktivtPanel.equals(stud)) { //Sjekker hvilket panel som ligger i innhold-panelet (Andre fordi overskriften er første komponent

				int nr = Integer.parseInt(tlf.getText());
				//setter dato i følge input.
				String dateString = innDato.getText();
				GregorianCalendar dato = dateHandler.dateFixer(dateString, null);

				//oppretter Studentobjektet.
				Student s = skolen.getStudentene().addStudent(navn.getText(), 
						epost.getText(), 
						nr,
						adresse.getText(), 
						dato);
				if(progBox.getSelectedIndex() != BLANK)
					s.setStudieprogram((Studieprogram)progBox.getSelectedItem());
				setText("Lagret student:\n\n" + s.fullString());
			} 

			else if (aktivtPanel.equals(lær)) {
				int nr = Integer.parseInt(tlf.getText());

				Laerer l = skolen.getLærerne().addLærer(navn.getText(), 
						epost.getText(), 
						nr,
						kontorNr.getText());
				setText("Lagret lærer:\n\n" + l.fullString());
				lærerBox.addItem(l);

			} 

			else if (aktivtPanel.equals(fag)) {
				int poeng = Integer.parseInt(studiepoeng.getText());

				Fag f = skolen.getFagene().addFag(tittel.getText(), 
						fagkode.getText(),
						beskrivelse.getText(),
						vurderingBox.getSelectedItem().toString(), 
						poeng, 
						(Laerer)lærerBox.getSelectedItem());

				setText("Lagret fag:\n\n" + f.fullString());
				fagBox.addItem(f);
			} 

			else if (aktivtPanel.equals(studprog)) {
				Studieprogram sp = skolen.getStudieprogrammene().addStudProg(tittel.getText());
				setText("Lagret studieprogram:\n\n" + sp.fullString());
				progBox.addItem(sp);
				fagBox.setVisible(true);
				leggtilfag.setVisible(true);
			}
		}
	}

	private class limitedOptionPane extends JOptionPane{

		public limitedOptionPane(){
			//
		}

		public int getMaxCharactersPerLineCount() {
			return 85;
		}
	}

}

