package no.HiOAProsjektV2013.Interface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import no.HiOAProsjektV2013.DataStructure.Eksamen;
import no.HiOAProsjektV2013.DataStructure.EksamensDeltaker;
import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

public class RightClickMenus extends MouseAdapter implements ActionListener{

	private JPopupMenu popMeny;
	private JMenu fagene;
	private JMenuItem eStrykPros, eKarDist, fagBeståttKrav, fagStudenter,studieStudenter, fjernFagFraStud;
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
		//finner alle studenter, og beståtte studenter for valgt fag.
		else if(o instanceof Fag){
			fagBeståttKrav = new JMenuItem("Finn studenter som har bestått kravene");
			fagStudenter = new JMenuItem("Finn studenter med dette faget");
			
			fagBeståttKrav.addActionListener(this);
			fagStudenter.addActionListener(this);
			
			popMeny.add(fagBeståttKrav);
			popMeny.add(fagStudenter);
		}
		//finner studenter for valgt studieprogram
		else if(o instanceof Studieprogram){
			studieStudenter = new JMenuItem("Finn studenter ved dette studieprogrammet");
			
			studieStudenter.addActionListener(this);
			
			popMeny.add(studieStudenter);
		}
		//lager en liste av menuitems for hvert fag, og fjerner valgt fag fra studenten.
		else if(o instanceof Student){
			final Student s = (Student) o;
			fagene = new JMenu("Fjern fag fra student");
			if(s.getfagListe() != null){
				for (Fag fag : s.getfagListe()) {
					final JMenuItem fagItem = new JMenuItem(fag.getFagkode());
					fagItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							curObject = (tw.getSkole().søk(
									fagItem.getActionCommand(), TestWindow.FAG)).get(0);
							int option = JOptionPane.showConfirmDialog(tw, "Er du sikker på at du vil melde\n" + s.getfNavn() + " " + s.geteNavn() + " av " + fagItem.getActionCommand());
							if(option == JOptionPane.OK_OPTION)
							s.removeFag((Fag)curObject);
						}
					});
					fagene.add(fagItem);
				}
			popMeny.add(fagene);
			}
		}
		else if(o instanceof EksamensDeltaker){
			eStrykPros = new JMenuItem("Finn Strykprosent i dette faget.");
			eKarDist = new JMenuItem("Finn Karakterdistribusjon");
			
			eStrykPros.addActionListener(this);
			eKarDist.addActionListener(this);
			
			popMeny.add(eStrykPros);
			popMeny.add(eKarDist);
			
			EksamensDeltaker ed = ((EksamensDeltaker) o);
			Eksamen e = ed.getFag().findEksamenByDate(ed.getDato());
			curObject = e;
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
				createPopMenu(curObject);
			}
			else if(comp instanceof JTable){
				JTable lista = (JTable) comp;
				int row = lista.getSelectedRow();
				if (row != -1) {
					curObject = lista.getValueAt(row, 1);
					createPopMenu(curObject);
				}
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
			JOptionPane.showMessageDialog(null, tw.getSkole().findStrykProsent(eks.getFag(), eks) + "");
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
			ArrayList<Student> studenter = tw.getSkole().findStudentsByStudieprogram(sp.getNavn());
			if(!studenter.isEmpty())
			tw.listApplier(studenter);
			else
				JOptionPane.showMessageDialog(null, "Det er ingen studenter i studieprogrammet.");
		}
		if(source == eKarDist){
			Eksamen eks = (Eksamen) curObject;
			Fag f = eks.getFag();
			int[] karakterer = tw.getSkole().findKarakterDistribusjon(f, eks);
			double stryk = tw.getSkole().findStrykProsent(f, eks);
			tw.displayKarakterer(karakterer, stryk);

		}
	}
}
