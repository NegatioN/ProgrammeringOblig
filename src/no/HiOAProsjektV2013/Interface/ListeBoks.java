package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

/*
 * Klassen lager listene for de forskjellige objekttypene med type-parameter og legger til en actionlistener og listselectionlistener.
 */
public class ListeBoks<E> implements ListSelectionListener, ActionListener{

	private final int ROWCOUNT = 20;
	private Object valgt;
	private JPanel vis;
	private JButton rediger, slett;
	private Vindu vindu;
	private RightClickMenus popup;
	private JList<E> curList = null;
	private Buttons button;
	//private static siden vi har flere instanser av dette objektet, men bildene vil alltid bli de samme.
	private final static ImageIcon editIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/icons/editBlue.png"));
	private final static ImageIcon delIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/icons/delBlue.png"));
	private final static ImageIcon editRoll = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/icons/editBlueRoll.png"));
	private final static ImageIcon delRoll = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/icons/delBlueRoll.png"));
	private final static ImageIcon editPress = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/icons/editBluePress.png"));
	private final static ImageIcon delPress = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/icons/delBluePress.png"));
	
	
	public ListeBoks(Vindu vindu){
		this.vindu = vindu;
		popup = vindu.getRightClickMenu();
		button = vindu.getButtonGen();
	}

	public JPanel visResultat(JList<E> liste){
		vis = new JPanel(new BorderLayout());
		JPanel knapper = new JPanel();

		Object o = liste.getSelectedValue();

		//oppretter knapper
		rediger = button.generateButton(null, knapper, Buttons.ICONBUTTON, BorderLayout.WEST,editIcon,editRoll,editPress,this);
		
		if(!(o instanceof Student)){
		slett = button.generateButton(null, knapper, Buttons.ICONBUTTON, BorderLayout.EAST, delIcon,delRoll,delPress,this);
		}
		//definerer scrollPane
		JScrollPane scroller = new JScrollPane(liste);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		vis.add(scroller, BorderLayout.CENTER);
		vis.add(knapper, BorderLayout.SOUTH);

		return vis;
	}

	//gjør om arraylisten til en JList
	public JList<E> listify(ArrayList<E> array){
		E[] tilArray = (E[]) array.toArray();
		DefaultListModel<E> model = new DefaultListModel<>();
		for(E object : tilArray){
			model.addElement(object);
		}
		JList<E> listen = new JList<>(model);
		listen.setVisibleRowCount(ROWCOUNT);

		listen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listen.addListSelectionListener(this);
		listen.addMouseListener(popup);
		listen.setFixedCellWidth(390);
		listen.setSelectedIndex(0);
		//listen.setPreferredSize(størrelse);

		return listen;
	}

	private void visInfo(Object o){

		vindu.display();
		if(o instanceof Student)
			vindu.setText(((Student) o).fullString());
		if(o instanceof Laerer)
			vindu.setText(((Laerer) o).fullString());
		if(o instanceof Fag)
			vindu.setText(((Fag) o).fullString());
		if(o instanceof Studieprogram)
			vindu.setText(((Studieprogram) o).fullString());

	}

	private void setValgt(Object v){
		valgt = v;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Det som skal skje når vi clicker objektet
		@SuppressWarnings("unchecked")
		JList<E> lista = (JList<E>) e.getSource();
		E valgtObjekt = lista.getSelectedValue();
		curList = lista;
		setValgt(valgtObjekt);
		visInfo(valgtObjekt);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rediger){
			new EditPanel(vindu, valgt);
		}
		else if(e.getSource() == slett){
			int svar = JOptionPane.showConfirmDialog(
					vindu,
					"Er du sikker på at du vil slette?",
					"Slette objekt?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (svar == JOptionPane.YES_OPTION )
			{
				if(valgt instanceof Student)
					vindu.getSkole().getStudentene().removeStudent((Student)valgt);
				else if(valgt instanceof Laerer)
					vindu.getSkole().getLærerne().removeLærer((Laerer)valgt);
				else if(valgt instanceof Fag)
					vindu.getSkole().getFagene().removeFag((Fag)valgt);
				else if(valgt instanceof Studieprogram)
					vindu.getSkole().getStudieprogrammene().removeStudieprogram((Studieprogram)valgt);
				
				//displayer informasjonen i displayvinduet.
				vindu.setText(valgt.toString() + " ble fjernet fra systemet.");
				vindu.display();
				//sørger for at lista mister det objektet som blir sletta fra datastrukturen.
				DefaultListModel<E> model = (DefaultListModel<E>) curList.getModel();
				model.removeElement(valgt);
			}
		}
	}
}
