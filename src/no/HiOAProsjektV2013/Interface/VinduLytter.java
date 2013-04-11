package no.HiOAProsjektV2013.Interface;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

public class VinduLytter implements WindowListener, Serializable{
	

	private static final long serialVersionUID = 2L;
	TestWindow info;
	
	public VinduLytter(TestWindow tw){
		info = tw;
		tw.addWindowListener(this);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		info.getArkiv().writeToFile(info.getSkole());
		System.out.println("Ka-ching");
		
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