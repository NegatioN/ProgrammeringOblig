package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
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
	private Dimension size = new Dimension(300, 300);
	private JTextField navn, epost, tlf, adresse, start, kontorNr, fagkode,
	beskrivelse, studiepoeng, vurderingsform, lærer;
	private JPanel panelet;
	private Student studenten;
	
	public IndreVindu(JDesktopPane desktop, Student stud){
		studenten = stud;
		indreVindu = new JInternalFrame(title,true,true,true,true);
		indreVindu.add(generateWindow(stud));		
		indreVindu.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		indreVindu.setVisible(true);
		indreVindu.pack();
		indreVindu.setResizable(false);

		desktop.add(indreVindu, BorderLayout.CENTER);
		
		try {
			indreVindu.setSelected(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Component generateWindow(JList liste){
		panelet = new JPanel();


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
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		String studNavn = s.getfNavn() +" "+ s.geteNavn();
		String studEpost = s.getEpost();
		String studTlf = s.getTelefonNr() + "";
		String studAdresse = s.getAdresse();
		Date startdato = s.getStart();
//		Arbeidskrav[] fagene = s.getFagene();
//		JList<Arbeidskrav> liste = new JList<>();
//		liste.setListData(fagene);
		lytter l = new lytter();
		
		navn	 		= new JTextField(studNavn, 20);
		epost	 		= new JTextField(studEpost, 20);
		tlf		 		= new JTextField(studTlf, 20);
		adresse			= new JTextField(studAdresse, 20);
		start			= new JTextField(formatter.format(startdato), 20);
		JButton lagre	= new JButton("Lagre");
		
		navn.setEditable(false);
		epost.setEditable(true);
		tlf.setEditable(true);
		start.setEditable(false);
		lagre.setPreferredSize(new Dimension(150,30));
		lagre.addActionListener(l);
		
		panelet = new JPanel();
		panelet.setSize(size);
		panelet.add(navn);
		panelet.add(epost);
		panelet.add(tlf);
		panelet.add(adresse);
		panelet.add(start);
		panelet.add(lagre);
//		panelet.add(liste);
		
		return panelet;
	}
	
	private class lytter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			studenten.setAdresse(adresse.getText());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//hva skjer når listene blir selecta i indre vindu?
	}
	
}
