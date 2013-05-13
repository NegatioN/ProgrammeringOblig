package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
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

	private final int ROWCOUNT = 18;
	private Object valgt;
	private JPanel vis;
	private JButton rediger, slett;
	private TestWindow tw;
	private RightClickMenus popup;
	private JList<E> curList = null;

	public ListeBoks(TestWindow tw){
		this.tw = tw;
		popup = tw.getRightClickMenu();
	}

	public JPanel visResultat(JList<E> liste){
		vis = new JPanel(new BorderLayout());
		JPanel knapper = new JPanel(new GridLayout(2,1));

		Object o = liste.getSelectedValue();

		Buttons button = new Buttons(this);
		rediger = button.generateButton("Rediger", knapper, Buttons.HEL);
		if(!(o instanceof Student)){
			slett = button.generateButton("Slett", knapper, Buttons.HEL);
		}

		vis.add(new JScrollPane(liste), BorderLayout.CENTER);
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
		//		@SuppressWarnings("unchecked")
		//		E[] tilArray = (E[]) array.toArray();
		//		listen.setListData(tilArray);
		listen.setVisibleRowCount(ROWCOUNT);

		listen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listen.addListSelectionListener(this);
		listen.addMouseListener(popup);
		listen.setFixedCellWidth(390);
		listen.setSelectedIndex(0);
		//listen.setPreferredSize(størrelse);

		return listen;
	}
	public JComboBox<E> combify(ArrayList<E> array){
		//vi vet det er E som kommer inn, og kan derfor caste trygt til E[]
		E[] tilArray = (E[]) array.toArray();
		JComboBox<E> combo = new JComboBox<>(tilArray);
		combo.setSelectedIndex(0);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> boks = (JComboBox<?>) e.getSource();
				int i = boks.getSelectedIndex();
			}

		});

		return combo;
	}

	private void visInfo(Object o){

		tw.display();
		if(o instanceof Student)
			tw.setText(((Student) o).fullString());
		if(o instanceof Laerer)
			tw.setText(((Laerer) o).fullString());
		if(o instanceof Fag)
			tw.setText(((Fag) o).fullString());
		if(o instanceof Studieprogram)
			tw.setText(((Studieprogram) o).fullString());

	}

	private void setValgt(Object v){
		valgt = v;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Det som skal skje når vi clicker objektet
		//if(e.getValueIsAdjusting()){
		@SuppressWarnings("unchecked")
		JList<E> lista = (JList<E>) e.getSource();
		E valgtObjekt = lista.getSelectedValue();
		curList = lista;
		setValgt(valgtObjekt);
		visInfo(valgtObjekt);
		//	}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rediger){
			PopupVindu pop = new PopupVindu(tw, valgt);
		}
		else if(e.getSource() == slett){
			int svar = JOptionPane.showConfirmDialog(
					tw,
					"Er du sikker på at du vil slette?",
					"Slette objekt?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (svar == JOptionPane.YES_OPTION )
			{
				if(valgt instanceof Student)
					tw.getSkole().getStudentene().removeStudent((Student)valgt);
				else if(valgt instanceof Laerer)
					tw.getSkole().getLærerne().removeLærer((Laerer)valgt);
				else if(valgt instanceof Fag)
					tw.getSkole().getFagene().removeFag((Fag)valgt);
				else if(valgt instanceof Studieprogram)
					tw.getSkole().getStudieprogrammene().removeStudieprogram((Studieprogram)valgt);

				tw.setText(valgt.toString() + " ble fjernet fra systemet.");
				tw.display();
				//sørger for at lista mister det objektet som blir sletta fra datastrukturen.
				DefaultListModel<E> model = (DefaultListModel<E>) curList.getModel();
				model.removeElement(valgt);
			}
		}
	}
}
