package no.HiOAProsjektV2013.Interface;
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 13.05.13
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

//sørger for at Archiver får lagret data før programmet lukkes.
public class VinduLytter implements WindowListener, Serializable{
	

	private static final long serialVersionUID = 2L;
	Vindu info;
	
	public VinduLytter(Vindu vindu){
		info = vindu;
		vindu.addWindowListener(this);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//Skriver datastrukturen til fil
		info.getArkiv().writeToFile(info.getSkole());
		
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
