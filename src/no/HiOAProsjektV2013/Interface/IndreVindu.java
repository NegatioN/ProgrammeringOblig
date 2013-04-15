package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.HiOAProsjektV2013.DataStructure.Student;

public class IndreVindu implements ListSelectionListener{

	private JInternalFrame indreVindu;
	private String title = "Info-display";
	private Dimension size = new Dimension(800, 600);
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode,
	beskrivelse, studiepoeng, vurderingsform, lærer;
	private JButton visFag, vislærer, visfag, visstudieprog, leggtilFagPåStud;
	private JPanel panelet;
	
	public IndreVindu(JDesktopPane desktop, Student stud){
		indreVindu = new JInternalFrame(title,true,true,true,true);
		Container c = indreVindu.getContentPane();
		
		c.add(generateWindow(stud), BorderLayout.CENTER);
		indreVindu.setSize(size);
		indreVindu.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		indreVindu.setVisible(true);
		c.setVisible(true);
		indreVindu.pack();
		desktop.add(indreVindu);
		System.out.println("vi er her");
		try {
			indreVindu.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Component generateWindow(JList liste){
		panelet = new JPanel(new BorderLayout());


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
		
		
		return panelet;
	}
	//trenger ikke ta inn lista som parameter, kan hentes fra objeketet.
	public Component generateWindow(Student s){
		String studNavn = s.geteNavn();
		String studEpost = s.getEpost();
		String studTlf = s.getTelefonNr() + "";
		String studAdresse = s.getAdresse();
		
//		Arbeidskrav[] fagene = s.getFagene();
//		JList<Arbeidskrav> liste = new JList<>();
//		liste.setListData(fagene);
		
		
		navn	 		= new JTextField(studNavn, 20);
		epost	 		= new JTextField(studEpost, 20);
		tlf		 		= new JTextField(studTlf, 20);
		adresse			= new JTextField(studAdresse, 20);
		start			= new JTextField("startdato", 20);
		navn.setEditable(false);
		epost.setEditable(false);
		tlf.setEditable(false);
		start.setEditable(false);
		
		panelet = new JPanel();
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
//		panelet.add(liste);
		
		return panelet;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//hva skjer når listene blir selecta i indre vindu?
	}
	
}
