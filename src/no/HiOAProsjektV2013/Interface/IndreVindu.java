package no.HiOAProsjektV2013.Interface;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class IndreVindu<E> {

	private JInternalFrame indreVindu;
	private String title = "Info-display";
	
	public IndreVindu(){
		indreVindu = new JInternalFrame(title,true,true,true,true);
	}
	
	public void generateWindow(JList<E> liste, JPanel panel){
		JList<E> lista = liste;
		
	}
	
}
