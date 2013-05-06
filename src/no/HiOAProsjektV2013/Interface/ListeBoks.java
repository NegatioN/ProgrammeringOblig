package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

	private final int ROWCOUNT = 20;
	private Object valgt;
	private JPanel vis;
	private TestWindow tw;
	
	public ListeBoks(TestWindow tw){
		this.tw = tw;
	}
	
	public JPanel visResultat(JList<E> liste){
		vis = new JPanel(new BorderLayout());

		JButton rediger = new JButton("Rediger"); 
		rediger.addActionListener(this);
		
		vis.add(new JScrollPane(liste), BorderLayout.CENTER);
		vis.add(rediger, BorderLayout.SOUTH);
		return vis;
	}

	//gjør om arraylisten til en JList
	public JList<E> listify(ArrayList<E> array){
		JList<E> listen = new JList<>();
		@SuppressWarnings("unchecked")
		E[] tilArray = (E[]) array.toArray();
		listen.setListData(tilArray);
		listen.setVisibleRowCount(ROWCOUNT);
		listen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listen.addListSelectionListener(this);
		listen.setFixedCellWidth(290);
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
				//GJØR MER STUFF
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
			setValgt(valgtObjekt);
			visInfo(valgtObjekt);
	//	}
	}
	
	public void actionPerformed(ActionEvent e) {
		PopupVindu pop = new PopupVindu(tw, valgt, tw.getSkole());
	}
}
