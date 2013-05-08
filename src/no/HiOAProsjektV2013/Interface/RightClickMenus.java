package no.HiOAProsjektV2013.Interface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import no.HiOAProsjektV2013.DataStructure.Eksamen;
import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class RightClickMenus extends MouseAdapter implements ActionListener{

	private JPopupMenu popMeny;
	private JMenuItem eStrykPros, eKarDist, fagBeståttKrav, fagStudenter,studieStudenter;
	private Object curObject = null;
	private TestWindow tw;
	
	public RightClickMenus(TestWindow tw){
		this.tw = tw;
	}
	public void createPopMenu(Object o){
		popMeny = new JPopupMenu();
		populateMenu(o);
	}
	
	private void populateMenu(Object o) {
		curObject = o;
		if(o instanceof Eksamen){
			eStrykPros = new JMenuItem("Finn Strykprosent");
			eKarDist = new JMenuItem("Finn Karakterdistribusjon");
			
			eStrykPros.addActionListener(this);
			eKarDist.addActionListener(this);
			
			popMeny.add(eStrykPros);
			popMeny.add(eKarDist);
		}
		else if(o instanceof Fag){
			fagBeståttKrav = new JMenuItem("Finn studenter som har bestått kravene");
			fagStudenter = new JMenuItem("Finn studenter med dette faget");
			
			fagBeståttKrav.addActionListener(this);
			fagStudenter.addActionListener(this);
			
			popMeny.add(fagBeståttKrav);
			popMeny.add(fagStudenter);
		}
		else if(o instanceof Studieprogram){
			studieStudenter = new JMenuItem("Finn studenter ved dette studieprogrammet");
			
			studieStudenter.addActionListener(this);
			
			popMeny.add(studieStudenter);
		}
		else if(o instanceof Student){
			
		}
		
	}

	public void mousePressed(MouseEvent e){
		sjekkOmMenySkalVises(e);
	}

	public void mouseReleased(MouseEvent e) {
		sjekkOmMenySkalVises(e);
	}

	private void sjekkOmMenySkalVises(MouseEvent e){
		if(e.isPopupTrigger()){
			Component comp = e.getComponent();
			if(comp instanceof JList<?>){
				JList<?> lista =(JList<?>) comp;
				Object o = lista.getSelectedValue();
				System.out.println(o.toString());
				curObject = o;
				createPopMenu(o);
			}
			else if(comp instanceof JTable){
				
			}
			try{
			popMeny.show(e.getComponent(), e.getX(), e.getY());
			}catch(NullPointerException ex){
				ex.printStackTrace();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == eStrykPros){
			Eksamen eks = (Eksamen) curObject;
			tw.setText(tw.getSkole().findStrykProsent(eks.getFag(), eks) + "");
		}
		if(source == fagStudenter){
			Fag fag = (Fag) curObject;
			ArrayList<Student> studenter = tw.getSkole().findStudentMedFag(fag);
			if(!studenter.isEmpty())
			tw.listApplier(studenter);
			else
				JOptionPane.showMessageDialog(null, "Det er ingen studenter i faget.");
		}
		if(source == studieStudenter){
			Studieprogram sp = (Studieprogram) curObject;
			ArrayList<Student> studenter = tw.getSkole().findStudentsByStudieprogram(sp);
			if(!studenter.isEmpty())
			tw.listApplier(studenter);
			else
				JOptionPane.showMessageDialog(null, "Det er ingen studenter i studieprogrammet.");
		}
	}
}
