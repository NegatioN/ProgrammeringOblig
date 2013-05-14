package no.HiOAProsjektV2013.Interface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

/*
 * Klasse som oppretter redigeringsvinduer for et valgt objekt. Tar imot student, lærer, fag og studieprogram.
 * Brukes også til å redigere arbeidskrav, eksamener og lignende. Her kan du legge til alt som ikke kunne legges til
 * fær hovedobjektet allerede var opprettet.
 */
public class EditPanel extends JPanel{

	private static final long serialVersionUID = 1073L;
	private final int FAG = 0, DATO  = 1, SNR = 2, MØTT = 3, KAR = 4, DATOBREDDE = 120, KOLONNER = 5;

	private Dimension venstreSize = new Dimension(290, 600), høyreSize = new Dimension(400, 600), infoSize = new Dimension(400, 250), tabellSize = new Dimension(385, 200), listeSize = new Dimension(250, 195);
	private InputFelt navn, epost, tlf, adresse, start, slutt, kontorNr, fagkode, beskrivelse, studiepoeng, studentNr, eksamensdato;
	private JPanel panelet, visepanel, faginfo, kravinfo;
	private Vindu vindu;
	private lytter ly = new lytter();
	private Buttons button = new Buttons(ly);
	private JButton leggtil, fjern, oppmeldte, deltaker , lagreKrav, visFag, visEksamen, visKrav, tilbake;
	private JComboBox<Fag> velgFag, studentFag;
	private JComboBox<Laerer> velgLærer;
	private JComboBox<Studieprogram> velgProg;
	private JComboBox<Eksamen> velgEksamen;
	private JComboBox<String> vurderingBox = null;
	private combolytter cl = new combolytter();
	private JTable resultater;
	private JCheckBox oppmeldtCheck;
	private JList<Krav> kravListe;
	private JList<Fag> fagListe;
	private Arbeidskrav aktivKrav;
	private RightClickMenus popup;
	private DateHandler dateHandler = new DateHandler();
	private Object aktiv; //Brukes til å utføre diverse operasjoner på objektet som klassen er kalt opp for

	public EditPanel(Vindu vindu, Object o){
		this.vindu = vindu; //Tar imot vindusobjekt for å kunne vise panelene i forskjellige deler av hovedvinduet
		popup = vindu.getRightClickMenu(); //Høyreklikkmeny for lister og tabeller

		//Sjekker hva slags objekt som er sendt med, og oppretter korrekt panel
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

	//Overloadet "konstruktør" som fyller panelet med relevante elementer for å vise og endre egenskaper ved Studenten og de andre objektene
	public Component fyllVindu(Student s){
		aktiv = s;

		//Finner alle nåværende verdier ved studenten og sender disse til tekstfeltene for visning
		String studNavn = s.getfNavn() +" "+ s.geteNavn(); 
		String studEpost = s.getEpost();
		String studTlf = s.getTelefonNr() + "";
		String studAdresse = s.getAdresse();
		Date startdato = s.getStart().getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd. MMM yyyy");

		//Oppretter tekstfelter med nåværende verdier
		navn	 		= new InputFelt(studNavn, InputFelt.LANG, false);
		epost	 		= new InputFelt(studEpost, InputFelt.LANG, Vindu.mailRegex);
		tlf		 		= new InputFelt(studTlf, InputFelt.LANG, Vindu.mobRegex);
		adresse			= new InputFelt(studAdresse, InputFelt.LANG);
		start			= new InputFelt(format.format(startdato.getTime()), InputFelt.LANG, false);

		try{ 	//Sluttdato kan ikke settes ved opprettelse av studenten, og viser derfor enten standardtekst eller nåværende verdi
			Date sluttdato = s.getSlutt().getTime(); 
			slutt 		= new InputFelt(format.format(sluttdato.getTime()), InputFelt.LANG, Vindu.dateRegex);
		} catch(NullPointerException npe){
			slutt 		= new InputFelt("Sluttdato", InputFelt.LANG, Vindu.dateRegex);
		}

		//Her opprettes kombobokser som brukes til å bla gjennom aktuelle elementer
		velgFag = new JComboBox<Fag>();
		velgFag.setPreferredSize(Buttons.HEL); //Setter størrelse til å matche knappene
		for(Fag f : vindu.getSkole().getFagene().getAlle()) { //Fyller komboboksen med alle fag
			velgFag.addItem((Fag)f);
		}

		velgProg = new JComboBox<Studieprogram>();
		velgProg.setPreferredSize(Buttons.HEL);
		for(Studieprogram sp : vindu.getSkole().getStudieprogrammene().getAlle()) {
			velgProg.addItem(sp);
		}

		try{ 	//Her settes komboboksen til studentens valgte studieprogram, eller til et blankt felt hvis han ikke har noe studieprogram
			velgProg.setSelectedItem(s.getStudieprogram());
		} catch(NullPointerException npe){
			velgProg.setSelectedIndex(Vindu.BLANK);
		}

		//Legger til alle felter og knapper
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
		leggtil = button.generateButton("Legg til fag", panelet, Buttons.HEL); 		//Knapp for å legge til fag fra velgFag-komboboksen
		panelet.add(Box.createRigidArea(Buttons.HEL));

		visFag = button.generateButton("Vis fag", panelet, Buttons.HEL); 			//Knapp for å vise alle studentens fag, med arbeidskrav
		visEksamen = button.generateButton("Vis Eksamener", panelet, Buttons.HEL); 	//Knapp for å vise alle studentes eksamener med resultat
		button.generateButton("Lagre", panelet, Buttons.HEL);						//Knapp for å lagre alle endringer som er gjort

		return panelet;
	} //End of fyllVindu(Student)

	//Nokså lik metode som fyllVindu(Student), men med færre, og litt andre felter
	public Component fyllVindu(Laerer l){ 
		aktiv = l;

		String n = l.getfNavn() +" "+ l.geteNavn();
		String e = l.getEpost();
		String t = l.getTelefonNr() + "";
		String k = l.getKontor();

		navn	 		= new InputFelt(n, InputFelt.LANG, false);
		epost	 		= new InputFelt(e, InputFelt.LANG, Vindu.mailRegex);
		tlf		 		= new InputFelt(t, InputFelt.LANG, Vindu.mobRegex);
		kontorNr		= new InputFelt(k, InputFelt.LANG);

		panelet = new JPanel();
		panelet.setPreferredSize(venstreSize);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(kontorNr);
		button.generateButton("Lagre", panelet, Buttons.HEL);

		return panelet;
	}  //End of fyllVindu(Laerer)

	//Nokså lik metode som fyllVindu(Student), men med andre felter og funksjoner
	public Component fyllVindu(Fag f){ 
		aktiv = f;

		String n = f.getNavn();
		String fk = f.getFagkode();
		String b = f.getBeskrivelse() + "";
		int sp = f.getStudiepoeng();

		navn	 		= new InputFelt(n, InputFelt.LANG, false);
		fagkode	 		= new InputFelt(fk, InputFelt.LANG, false);
		beskrivelse		= new InputFelt(b, InputFelt.LANG);
		studiepoeng		= new InputFelt(""+sp, InputFelt.LANG, Vindu.sPoengRegex);
		eksamensdato 	= new InputFelt("dag/mnd/år", InputFelt.LANG, Vindu.dateRegex);

		//Oppretter aktuelle kombobokser
		String[] boxitems =  {"Muntlig", "Skriftlig", "Prosjekt"}; //Komboboks for å bestemme hva slags vurderingsform som brukes i faget
		vurderingBox = new JComboBox<String>(boxitems);
		vurderingBox.setPreferredSize(Buttons.HEL);
		vurderingBox.setSelectedItem(f.getVurderingsform()); //Setter den nåværende vurderingsformen

		velgLærer = new JComboBox<Laerer>(); //Komboboks med alle lærerne. Setter nåværende lærer for faget som standard
		velgLærer.setSelectedItem(f.getLærer());
		velgLærer.setPreferredSize(Buttons.HEL);
		for(Laerer l : vindu.getSkole().getLærerne().getAlle()) {
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
		panelet.add(Box.createRigidArea(Buttons.HEL));

		panelet.add(eksamensdato);
		leggtil = button.generateButton("Legg til Eksamen", panelet, Buttons.HEL);	//Knapp for å legge til eksamen på datoen som oppgis i eksamensdato-feltet
		panelet.add(Box.createRigidArea(Buttons.HEL));

		visEksamen = button.generateButton("Vis Eksamener", panelet, Buttons.HEL);	//Knapp for å vise eksamener, og fjerne og legge til deltakere og resultater
		visKrav = button.generateButton("Vis Arbeidskrav", panelet, Buttons.HEL);	//Knapp for å vise arbeidskrav for faget. Her kan også krav fjernes og legges til
		button.generateButton("Lagre", panelet, Buttons.HEL);

		return panelet;
	} //End of fyllVindu(Fag)

	//Nokså lik metode som fyllVindu(Student), men med færre, og litt andre felter
	public Component fyllVindu(Studieprogram sp){
		aktiv = sp;

		String n = sp.getNavn();
		navn	= new InputFelt(n, InputFelt.LANG, Vindu.tittelRegex);

		//Oppretter en liste med alle fagene i studieprogrammet. Forutsetter her en viss grense på antall fag. Scrolling er derfor ikke aktivert.
		DefaultListModel<Fag> fagmodell = new DefaultListModel<Fag>();  
		fagListe = new JList<>(fagmodell);
		fagListe.setPreferredSize(listeSize);
		fagListe.setBorder(BorderFactory.createEtchedBorder());
		for(Fag f : sp.getFagene()){
			fagmodell.addElement(f);
		}

		velgFag = new JComboBox<Fag>();
		velgFag.setPreferredSize(Buttons.HEL);
		for(Fag f : vindu.getSkole().getFagene().getAlle()) {
			velgFag.addItem((Fag)f);
		}

		panelet = new JPanel();
		panelet.setPreferredSize(venstreSize);
		panelet.add(navn);
		panelet.add(fagListe);
		fjern = button.generateButton("Fjern fag", panelet, Buttons.HEL);	//Knapp for å fjerne fag fra lista og studieprogrammet
		panelet.add(Box.createRigidArea(Buttons.HEL));

		panelet.add(velgFag);
		leggtil = button.generateButton("Legg til fag", panelet, Buttons.HEL);//Knapp for å legge til fag fra komboboksen
		panelet.add(Box.createRigidArea(Buttons.HEL));

		button.generateButton("Lagre", panelet, Buttons.HEL);
		return panelet;
	} //End of fyllVindu(Studieprogram)


	/*Metoder som oppretter paneler som kalles på fra redigeringspanelene. Neste nivå i hierarkiet. 
	Her vises arbeidskrav, eksamener og lignende, og kan redigeres*/

	//Oppretter panel for å redigere arbeidskrav for alle fagene en student tar
	public JPanel fagPanel(){ 
		visepanel = new JPanel();
		visepanel.setPreferredSize(høyreSize);

		faginfo = new JPanel(new GridLayout(5,1));
		faginfo.setPreferredSize(infoSize);

		studentFag = new JComboBox<Fag>(); //Komboboks for å velge hvilket av fagene til studenten som skal vises
		studentFag.setPreferredSize(Buttons.HEL);
		studentFag.addItemListener(cl);
		for(Fag f : ((Student) aktiv).getfagListe()) {
			studentFag.addItem((Fag)f);
		}

		visepanel.add(studentFag);

		if(studentFag.getItemCount() > Vindu.FØRSTE)		//Hvis studenten har fag registrert på seg, vises det første.
			visFag(studentFag.getItemAt(Vindu.FØRSTE));		
		else												//Ellers vises vinduet med beskjed om at ingen fag er registrert
			visFag();

		visepanel.add(faginfo);		
		fjern = button.generateButton("Fjern fag", visepanel, Buttons.HEL); //Knapp for å fjerne fag fra studentens fagliste
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL); //Knapp for å fjerne fagpanelet og vise studentlisten igjen

		return visepanel;
	}
	//Oppretter panel for å redigere arbeidskravene til et fag
	public JPanel kravPanel(Fag f){
		visepanel = new JPanel();
		visepanel.setPreferredSize(høyreSize);

		aktivKrav = f.getKrav(); //Velger kravlisten som skal endres, fordi denne skal kunne brukes i lytterklassen

		DefaultListModel<Krav> modell = new DefaultListModel<Krav>();  //Oppretter en liste over alle kravene som er registrert på faget
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

		beskrivelse	= new InputFelt("Arbeidskrav", InputFelt.LANG);

		visepanel.add(kravinfo);
		visepanel.add(beskrivelse);
		lagreKrav = button.generateButton("Lagre krav", visepanel, Buttons.HEL);	//Knapp for å lagre nye krav med navn fra beskrivelse-feltet
		fjern = button.generateButton("Fjern valgt krav", visepanel, Buttons.HEL);	//Knapp for å fjerne faget som er valgt i listen
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);

		return visepanel;
	}
	//Oppretter panel for visning og redigering av eksamener i et bestemt fag
	public JPanel eksamensPanel(){
		visepanel = new JPanel();
		visepanel.setPreferredSize((høyreSize));

		faginfo = new JPanel();
		faginfo.setPreferredSize(infoSize);

		velgEksamen = new JComboBox<Eksamen>();	//Komboboks for å velge hvilken eksamen som skal vises
		velgEksamen.setPreferredSize(Buttons.HEL);
		velgEksamen.addItemListener(cl);
		for(Eksamen e : ((Fag)aktiv).getEksamener()) {
			velgEksamen.addItem(e);
		}

		if(velgEksamen.getItemCount() > Vindu.TOM)	//Hvis faget har noen eksamener registrert, vises den første av disse
			visEksamen(velgEksamen.getItemAt(Vindu.FØRSTE));
		else
			visFag();//FEEEEEEEEEEEEEEEEEEEEEEEEEIL		//Ellers vises et tomt panel med beskjed om at ingen fag er lagt til

		///AFAFAWFAWFAWFAWFAWF
		studentNr = new InputFelt("StudentNr", InputFelt.LANG, Vindu.studentNrRegex);

		visepanel.add(velgEksamen);
		visepanel.add(faginfo);
		visepanel.add(studentNr);
		deltaker 	= button.generateButton("Legg til deltaker", visepanel, Buttons.HEL);	//Legger til deltaker med studentnr fra studentnrfeltet
		oppmeldte 	= button.generateButton("Legg til Oppmeldte", visepanel, Buttons.HEL);	//Legger til alle studenter som er oppmeldt til eksamen
		tilbake 	= button.generateButton("Tilbake", visepanel, Buttons.HEL);

		return visepanel;
	} //End of eksamensPanel


	//Oppretter panel for visning av eksamener for en bestemt student
	public JPanel eksamensPanel(Student s){
		visepanel = new JPanel();
		visepanel.setPreferredSize((høyreSize));

		faginfo = new JPanel();
		faginfo.setPreferredSize(infoSize);
		faginfo.setBorder(BorderFactory.createTitledBorder("Eksamener for " + s.getfNavn() + " " + s.geteNavn()));

		Tabellmodell modell = new Tabellmodell(s);	//Oppretter tabell for visning og redigering av alle studentens eksamener
		resultater = new JTable(modell);
		resultater.setPreferredScrollableViewportSize(tabellSize);
		resultater.addMouseListener(popup);
		faginfo.add(new JScrollPane(resultater));
		resultater.getColumnModel().getColumn(DATO).setPreferredWidth(DATOBREDDE);

		visepanel.add(faginfo);
		tilbake = button.generateButton("Tilbake", visepanel, Buttons.HEL);
		return visepanel;
	}
	//Oppretter og viser en tabell med deltakere og resultater for en bestemt eksamen
	public void visEksamen(Eksamen e){
		faginfo.removeAll();
		faginfo.setBorder(BorderFactory.createTitledBorder("Eksamen for " + ((Fag)aktiv).getNavn()));

		Tabellmodell modell = new Tabellmodell(e);
		resultater = new JTable(modell);
		resultater.setPreferredScrollableViewportSize(tabellSize);
		resultater.addMouseListener(popup);	//Legger til høyreklikkfunksjoner
		resultater.getColumnModel().getColumn(DATO).setPreferredWidth(DATOBREDDE);

		faginfo.add(new JScrollPane(resultater));
		faginfo.updateUI();
	}
	//Fyller et panel med arbeidskrav for en student, hvis han har noen fag
	public void visFag(){
		faginfo.removeAll();
		if(studentFag == null || studentFag.getSelectedIndex() == Vindu.BLANK)
			faginfo.setBorder(BorderFactory.createTitledBorder("Ingen fag"));
		else
			visFag((Fag)studentFag.getSelectedItem());

		faginfo.updateUI();
	}
	//Fyller et panel med sjekkbokser for alle arbeidskrav for et fag 
	public void visFag(Fag f){
		faginfo.removeAll();
		faginfo.setBorder(BorderFactory.createTitledBorder(f.getNavn()));

		aktivKrav = ((Student)aktiv).getFagKrav(f);

		checklytter cl = new checklytter();
		for(Krav krav : aktivKrav.getKrav()){
			JCheckBox cb = new JCheckBox(krav.getBeskrivelse());
			faginfo.add(cb);
			cb.setSelected(krav.isGodkjent());	//Checkboxene er checket hvis kravene er godkjent
			cb.addItemListener(cl);
		}

		oppmeldtCheck = new JCheckBox("Oppmeldt til eksamen");
		oppmeldtCheck.setSelected(((Student)aktiv).innfriddKrav(f));	//Eleven blir oppmeldt til eksamen når alle arbeidskrav er godkjent

		faginfo.add(oppmeldtCheck);
		faginfo.updateUI();
	}


	//Tabellmodell som setter formatet for tabellen som viser eksamener, og fyller denne
	private class Tabellmodell extends AbstractTableModel{

		private static final long serialVersionUID = 100110L;

		private String[] kolonnenavn = {"Fag", "Dato", "StudentNr", "Møtt", "Karakter"};
		private Object[][] celler = {{"", "", "", "", ""}};	//Tom tabell

		public Tabellmodell(Eksamen e){ //Fyller tabellen med alle deltakere på en eksamen
			if(!e.getDeltakere().isEmpty())
				fyllTabell(e.getDeltakere().size(), e.getDeltakere());
		}
		public Tabellmodell(Student s){	//Fyller tabellen med alle eksamener for en student
			if(!s.getEksamener().isEmpty())
				fyllTabell(s.getEksamener().size(), s.getEksamener());
		}

		//Legger inn riktige elementer på det rett plass i tabellen med en for-løkke
		private void fyllTabell(int lengde, LinkedList<EksamensDeltaker> eksamener){
			celler = new Object[lengde][KOLONNER];

			int rad = 0; //Sørger for at hver deltaker havner på en ny rad
			for(EksamensDeltaker ed: eksamener){
				Eksamen e = ed.getFag().findEksamenByDate(ed.getDato());
				celler[rad][FAG] = ed.getFag().getFagkode();
				celler[rad][DATO] = e;
				celler[rad][SNR] = ed;
				celler[rad][MØTT] = ed.isOppmøtt();
				celler[rad++][KAR] = new String(""+ed.getKarakter());
			}
			this.addTableModelListener(new tabellytter());
		}


		public Object getValueAt(int rad, int kolonne){ //Henter verdien i en gitt celle
			return celler[rad][kolonne];
		}

		public String getColumnName(int kolonne) { //Henter kolonnenavn
			return kolonnenavn[kolonne];
		}

		public int getRowCount() { //Henter antall rader
			return celler.length; 
		}

		public int getColumnCount() { //Henter antall kolonner
			return celler[FAG].length;
		}

		public boolean isCellEditable(int rad, int kolonne){ //Sørger for at kun møtt, og karakteren er redigerbare
			switch (kolonne) {
			case MØTT:
			case KAR:
				return true;
			default:
				return false;
			}
		}

		public void setValueAt(Object value, int rad, int kolonne) { //Setter ny verdi i gitt celler
			celler[rad][kolonne] = value;
			fireTableCellUpdated(rad, kolonne);
		}

		//For å informere tabellmodellen om kolonnenes datatyper.
		public Class<? extends Object> getColumnClass(int kolonne) {
			return celler[FAG][kolonne].getClass();
		}
	} //End of class Tabellmodell

	//Privat klasse som lytter etter endringer i eksamenstabellen
	private class tabellytter implements TableModelListener{
		public void tableChanged(TableModelEvent e) {
			EksamensDeltaker ed = (EksamensDeltaker)resultater.getValueAt(e.getFirstRow(), SNR); //Henter ut deltakeren som skal redigeres

			if(e.getColumn() == MØTT)	//Setter oppmøtestatus med checkbox
				ed.setOppmøtt((Boolean)resultater.getValueAt(e.getFirstRow(), MØTT));

			else{	//Setter karakter som blir skrevet inn
				String s = (resultater.getValueAt(e.getFirstRow(), e.getColumn())).toString();
				char k = ed.getKarakter();

				if(s.matches("[a-fA-F]")){ 		//Sjekker her fordi den blanke char'en har verdien \0. Hvis du prøver å bare skrive inn en bokstav,
					k = s.charAt(Vindu.FØRSTE);	//blir whitespacet registrert fremfor bokstaven. Denne sjekken sikrer at det den innskrevne karakteren lagres.
				} else if(s.matches("\0[a-fA-F]")){
					k = s.charAt(Vindu.ANDRE);
				}
				if(k != ed.getKarakter())		//Hvis karakteren er den samme, trengs det ikke å gjøres noen endring
					ed.setKarakter(k);
			}

			//Oppdaterer visningspanelet. Enten med den valgte eksamenen et fags eksamenspanel, eller med et nytt eksamenspanel for en student
			if(velgEksamen != null)	
				visEksamen((Eksamen)velgEksamen.getSelectedItem());
			else
				vindu.cover(eksamensPanel((Student)aktiv));
		}
	} //End of class tabellytter

	/*Klasse og metode som oppdaterer visningen når bruker velger element i en kombobox. 
	Blir iblant delay på dette som gjør at bruker må trykke på nytt i boksen. Har ikke klart å finne årsaken til dette problemet*/
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
	} //End of class combolytter

	//Klasse og metode som godkjenner eller fjerner godkjennelse fra arbeidskrav hos en student ved hjelp av checkbokser
	private class checklytter implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) { //Setter krav med matchende navn til godkjent når boksen checkes
				for(Krav k : aktivKrav.getKrav()){
					if( ((JCheckBox)e.getSource()).getText() == k.getBeskrivelse())
						k.setGodkjent(true);
				}
			} else if (e.getStateChange() == ItemEvent.DESELECTED) { //Setter krav med matchende navn til ikke godkjent når boksen uncheckes
				for(Krav k : aktivKrav.getKrav()){
					if( ((JCheckBox)e.getSource()).getText() == k.getBeskrivelse())
						k.setGodkjent(false);
				}
			}
			visFag();
		}       
	} //End of class checklytter

	//Klasse som tar seg av hendelseshåndtering for knappene i editpanelene
	private class lytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			//Metoder for å legge til fag eller eksamen avhengig av hva slags panel som er åpent
			if (e.getSource() == leggtil) {
				try{
					if(aktiv instanceof Student){ //Hvis vi er i et studentpanel, skal det legges til et fag med arbeidskrav til studenten
						Fag f = (Fag)velgFag.getSelectedItem();
						Student s = (Student)aktiv;
						s.addFag(f);
						vindu.cover(fagPanel()); //Viser et fagpanel med det nye faget og dets arbeidskrav
						studentFag.setSelectedItem(f);
						visFag(f);
					}
					else if(aktiv instanceof Studieprogram){ //Hvis vi er i et studieprogrampanel, skal det legges til et fag til programmet
						Fag f = (Fag)velgFag.getSelectedItem();
						if(!((Studieprogram) aktiv).harFaget(f)) //Oppdaterer faglisten, bare hvis faget ikke finnes i listen fra før
							((DefaultListModel<Fag>) fagListe.getModel()).addElement(f);
						((Studieprogram) aktiv).addFag(f);
					}
					else if(aktiv instanceof Fag){ //Hvis vi er i et fagpanel, skal det legges til en eksamen til faget

						//Oppretter et kalenderobjekt utfra teksten i eksamensdatofeltet
						GregorianCalendar dato = dateHandler.dateFixer(eksamensdato.getText(), null);  

						//Legger det nye eksamensobjektet til faget
						Eksamen eks = new Eksamen(dato, (Fag)aktiv);
						((Fag)aktiv).addEksamen(eks);
						eksamensdato.setText("Eksamen lagret");

						vindu.cover(eksamensPanel());	//Viser et eksamenspanel med den nye eksamenen
						velgEksamen.setSelectedItem(eks);
						visEksamen(eks);
					}
				} catch (NullPointerException npe){ //Bruker kun en enkel try catch fordi inputfelt viser de korrekte feilmeldingene automatisk
					System.out.println("Feil i datoinput");
				}

				//Metoder for å fjerne fag eller arbeidskrav avhengig av hva slags panel som er åpent
			} else if(e.getSource() == fjern) {
				try{
					if(aktiv instanceof Student){ //Hvis vi er i et studentpanel skal et fag fjernes
						((Student) aktiv).removeFag((Fag)studentFag.getSelectedItem());
						vindu.cover(fagPanel()); //Oppdaterer panelet
					}
					else if(aktiv instanceof Studieprogram){ //Hvis vi er i et studieprogrampanel skal et fag fjernes
						((Studieprogram) aktiv).fjernFag(fagListe.getSelectedValue());
						((DefaultListModel<Fag>) fagListe.getModel()).remove(fagListe.getSelectedIndex()); //Oppdaterer listen
					}
					else if(aktiv instanceof Fag){	//Hvis vi er i et fagpanel skal et krav fjernes
						aktivKrav.fjernKrav(kravListe.getSelectedValue());
						vindu.cover(kravPanel((Fag)aktiv)); //Oppdaterer panelet
					}
				} catch (NullPointerException npe){
					//Kan forekomme hvis knappen trykkes når et studieprogram er tomt for fag
					System.out.println("Nullpointer fordi alle fag er fjernet"); 
				}
			}

			//Metode for å legge en deltaker til en eksamen
			else if(e.getSource() == deltaker){ 
				Student s = vindu.getSkole().getStudentene().findStudentByStudentNr(studentNr.getText());
				if(s != null)
					((Eksamen)velgEksamen.getSelectedItem()).addDeltaker(s);
				visEksamen((Eksamen)velgEksamen.getSelectedItem());  //Oppdaterer panelet med den nye deltakeren lagt til
			}
			//Metode for å legge til alle oppmeldte deltakere til en eksamen
			else if(e.getSource() == oppmeldte){
				ArrayList<Student> studentliste = vindu.getSkole().getStudentene().getAlle();
				((Eksamen)velgEksamen.getSelectedItem()).addOppmeldteStudenter(studentliste);
				visEksamen((Eksamen)velgEksamen.getSelectedItem());   //Oppdaterer panelet med de nye deltakerene lagt til
			}

			//Metode for å lagre nye arbeidskrav med beskrivelse fra et tekstfelt
			else if(e.getSource() == lagreKrav){
				aktivKrav.addKrav(new Krav(beskrivelse.getText()));
				vindu.cover(kravPanel((Fag) aktiv));
			} 

			//Metode for å fjerne paneler fra høyre del av vinduet og vise søkeresultatlisten
			else if(e.getSource() == tilbake){
				vindu.vis();
			}

			//Viser eksamener for enten en student eller et fag
			else if(e.getSource() == visEksamen){
				if(aktiv instanceof Student)
					vindu.cover(eksamensPanel((Student)aktiv));
				else
					vindu.cover(eksamensPanel());
			}
			//Viser arbeidskrav for et fag
			else if(e.getSource() == visKrav){
				vindu.cover(kravPanel((Fag) aktiv));
			}
			//Viser fag med arbeidskrav for en student
			else if(e.getSource() == visFag){
				vindu.cover(fagPanel());
			}

			//Lagrer endringer på de forskjellige objektene
			else{
				//Nullstiller displayet i Vinduet
				vindu.display();
				vindu.vis();

				try{
					if(aktiv instanceof Student){
						Student s = (Student) aktiv;

						GregorianCalendar dato = dateHandler.dateFixer(slutt.getText(), null);
						if(dato != null) //Dato blir null hvis inputteksten ikke matcher 
							s.setSlutt(dato);

						int nr = Integer.parseInt(tlf.getText());

						s.setAdresse(adresse.getText());
						s.setTlf(nr);
						s.setEpost(epost.getText());
						if(velgProg.getSelectedIndex() != Vindu.BLANK)
							s.setStudieprogram((Studieprogram)velgProg.getSelectedItem());

						vindu.setText(s.fullString());

					} else if(aktiv instanceof Laerer){
						Laerer l = (Laerer) aktiv;

						int nr = Integer.parseInt(tlf.getText());

						l.setTlf(nr);
						l.setEpost(epost.getText());
						l.setKontor(kontorNr.getText());

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
				}catch (NumberFormatException nfe){
					System.out.println("Feil Nummerformat");
				}				
			}
		}
	}
}
