package no.HiOAProsjektV2013.Interface;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.HiOAProsjektV2013.DataStructure.Student;

//tingene her må legges i main-vindus-klassen
//endre til overload constructors på "Listiyfy"? for å ikke opprette mange typeparameteriserte objekter i testwindow?
public class ListeBoks<E> implements ListSelectionListener{
	private final int ROWCOUNT = 15;
	private Dimension størrelse = new Dimension(400,200);
	private Object valgt;

	public ListeBoks(){
		
	}


	public JList<E> listiFy(ArrayList<E> array){
		JList<E> listen = new JList<>();
		E[] tilArray = (E[]) array.toArray();
		listen.setListData(tilArray);
		listen.setVisibleRowCount(ROWCOUNT);
		listen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listen.addListSelectionListener(this);
		listen.setFixedCellWidth(240);
		listen.setPreferredSize(størrelse);
		
		return listen;
	}
	
	public void setValgt(Object v){
		valgt = v;
	}
	public Object getValgt(){
		return valgt;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Det som skal skje når vi clicker objektet
		if(e.getValueIsAdjusting()){
			JList<E> lista = (JList<E>) e.getSource();
			E valgtObjekt = lista.getSelectedValue();
			setValgt(valgtObjekt);
			//opprettUtvidelsesVindu(valgtObjekt);
		}
	}
	

	
	
}
