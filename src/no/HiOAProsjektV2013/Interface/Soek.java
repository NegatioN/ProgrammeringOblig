package no.HiOAProsjektV2013.Interface;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class Soek extends JPanel {
	
	private Skole skolen;
	private JTextField søkefelt;
	private JButton søkeknapp;
	private String in, resultat;
	private String navnRegex = "\\D+";
	private String fagkodeRegex = "\\w{4}\\d{4}";
	private String studentNrRegex = "s\\d{6}";
	private String årRegex = "\\d{4}";
	
	
	public Soek(Skole skolen, ActionListener lytter, int bredde, int høyde){
		this.skolen = skolen;
		
		søkefelt = new JTextField("Søk");
		søkefelt.setPreferredSize(new Dimension(140, 25));
		søkefelt.addActionListener(lytter);

		søkeknapp = new JButton("Søk");
		søkeknapp.setPreferredSize(new Dimension(140, 25));
		søkeknapp.addActionListener(lytter);
		
		add(søkefelt);
		add(søkeknapp);
		setPreferredSize(new Dimension(bredde, høyde));
	}
	
	public JTextField getFelt(){
		return søkefelt;
	}
	
	public JButton getKnapp(){
		return søkeknapp;
	}
	
	public String søk(){
		resultat = "Søkeresultat:";
		in = søkefelt.getText();
		
		if(in.matches(årRegex)){
			
		}
		else if (in.matches(fagkodeRegex)){
			return fagkodeSøk();
		}
		else if (in.matches(studentNrRegex)){
			return studentNrSøk();
		}
		else if (in.matches(navnRegex)){
			return navnSøk();
		}
		
		return "Ugyldig søk";
	}

	public String navnSøk(){
		
		for(Laerer l : skolen.getLærerne().findByNavn(søkefelt.getText())){
			resultat += "\n" + l.getfNavn() +" "+ l.geteNavn();
		}
		
		for(Student s : skolen.getStudentene().findByNavn(søkefelt.getText())){
			resultat += "\n" + s.getfNavn() +" "+ s.geteNavn();
		}
		
		for(Fag f : skolen.getFagene().findByNavn(søkefelt.getText())){
			resultat += "\n" + f.getNavn() +" "+ f.getFagkode();
		}
		
		if(skolen.finnStudProgByNavn(søkefelt.getText()) != null)
			resultat += "\n" + skolen.finnStudProgByNavn(søkefelt.getText()).getNavn();
		
		if( resultat == "Søkeresultat:")
			return "Ingen treff";
		return resultat;
	}
	
	public String årSøk(){
		return null;
	}

	public String studentNrSøk(){

		resultat += "\n" + skolen.getStudentene().findStudentByStudentNr(søkefelt.getText());
		if( resultat == "Søkeresultat:")
			return "Ingen treff";
		return resultat;
	}
	
	public String fagkodeSøk(){
	
		resultat += "\n" + skolen.getFagene().finnFagByFagkode(søkefelt.getText());
		if( resultat == "Søkeresultat:")
			return "Ingen treff";
		return resultat;

	}

}
