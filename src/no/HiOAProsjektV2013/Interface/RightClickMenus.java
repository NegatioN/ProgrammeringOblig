package no.HiOAProsjektV2013.Interface;
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 13.05.13

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
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;
import no.HiOAProsjektV2013.Main.TabellModell;

/*
 * Klassen har som formål å generere høyreklikk-menyer for våre JLists
 * og JPanel hvor det er praktisk. Streamliner mulighetene for å bruke søkemetodene som er implementert.
 */
public class RightClickMenus extends MouseAdapter implements ActionListener{

	private JPopupMenu popMeny;
	private JMenu fagene;
	private JMenuItem eStrykPros, eKarDist, fagBeståttKrav, fagStudenter,studieStudenter, fagLedetAv;
	private Object curObject = null;
	private Vindu vindu;
	private final int PANELOBJEKT = 2;
	private JMenuItem edFjernOppmelding;
	private Component comp;
	public RightClickMenus(Vindu vindu){
		this.vindu = vindu;
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
							curObject = (vindu.getSkole().søk(
									fagItem.getActionCommand(), Vindu.FAG)).get(Vindu.FØRSTE);
							String melding = "Er du sikker på at du vil melde\n" + s.getfNavn() + " " + s.geteNavn() + " av " + fagItem.getActionCommand();
							int option = JOptionPane.showConfirmDialog(vindu, melding, "Slette faget?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
			edFjernOppmelding = new JMenuItem("Fjern eksamensoppmelding");
			
			eStrykPros.addActionListener(this);
			eKarDist.addActionListener(this);
			edFjernOppmelding.addActionListener(this);
			
			popMeny.add(eStrykPros);
			popMeny.add(eKarDist);
			popMeny.add(edFjernOppmelding);
			
		}
		else if(o instanceof Laerer){
			fagLedetAv = new JMenuItem("Finn fag ledet av lærer");
			
			fagLedetAv.addActionListener(this);
			
			popMeny.add(fagLedetAv);
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
			comp = e.getComponent();
			if(comp instanceof JList<?>){
				JList<?> lista =(JList<?>) comp;
				Object o = lista.getSelectedValue();
				curObject = o;
				createPopMenu(curObject);
			}
			else if(comp instanceof JTable){
				JTable lista = (JTable) comp;
				int row = lista.getSelectedRow();
				if (row != -1) {
					curObject = lista.getValueAt(row, PANELOBJEKT);
					createPopMenu(curObject);
				}
			}
			try{
			popMeny.show(e.getComponent(), e.getX(), e.getY());
			}catch(NullPointerException ex){
				//user har trykket på før et listeobjekt er valgt.
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		//vanlig actionlistener
		Object source = e.getSource();
		if(source == eStrykPros){
			EksamensDeltaker ed = ((EksamensDeltaker) curObject);
			Eksamen eks = ed.getFag().findEksamenByDate(ed.getDato());
			vindu.setText("Eksamen " + eks.toString() + " har : " + vindu.getSkole().findStrykProsent(eks.getFag(), eks) + "% stryk.");
			vindu.display();
		}
		if(source == fagBeståttKrav){
			Fag fag = (Fag) curObject;
			ArrayList<Student> studenter = vindu.getSkole().findKravBeståtteStudenter(fag);
			if(!studenter.isEmpty())
				vindu.listApplier(studenter, Vindu.STUDENT);
			else
				JOptionPane.showMessageDialog(null, "Det er ingen studenter som har besått kravene enda");
		}
		if(source == fagStudenter){
			Fag fag = (Fag) curObject;
			ArrayList<Student> studenter = vindu.getSkole().findStudentMedFag(fag);
			if(!studenter.isEmpty())
			vindu.listApplier(studenter, Vindu.STUDENT);
			else
				JOptionPane.showMessageDialog(null, "Det er ingen studenter i faget.");
		}
		if(source == studieStudenter){
			Studieprogram sp = (Studieprogram) curObject;
			ArrayList<Student> studenter = vindu.getSkole().findStudentsByStudieprogram(sp.getNavn());
			if(!studenter.isEmpty())
			vindu.listApplier(studenter, Vindu.STUDENT);
			else
				JOptionPane.showMessageDialog(null, "Det er ingen studenter i studieprogrammet.");
		}
		if(source == eKarDist){
			EksamensDeltaker ed = ((EksamensDeltaker) curObject);
			Eksamen eks = ed.getFag().findEksamenByDate(ed.getDato());
			Fag f = eks.getFag();
			int[] karakterer = vindu.getSkole().findKarakterDistribusjon(f, eks);
			double stryk = vindu.getSkole().findStrykProsent(f, eks);
			vindu.displayKarakterer(karakterer, stryk);

		}
		if(source == fagLedetAv){
			Laerer lærer = (Laerer) curObject;
			ArrayList<Fag> fagene = vindu.getSkole().fagLedetAv(lærer);
			if(!fagene.isEmpty()){
				vindu.listApplier(fagene, Vindu.FAG);
			}else{
				JOptionPane.showMessageDialog(null, "Læreren har ingen fag.");
			}
			
		}
		if(source == edFjernOppmelding){
			EksamensDeltaker ed = (EksamensDeltaker) curObject;
			int svar = JOptionPane.showConfirmDialog(null, "Er du sikker på at du vil slette eksamensdeltakelse for " + ed.getDeltaker().getfNavn() + " "+ ed.getDeltaker().geteNavn() + "?", 
					"Prompt", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(svar == JOptionPane.YES_OPTION){
			Eksamen eks = ed.getFag().findEksamenByDate(ed.getDato());
			eks.avmeld(ed.getDeltaker());
			}
			
		}
	}
}
