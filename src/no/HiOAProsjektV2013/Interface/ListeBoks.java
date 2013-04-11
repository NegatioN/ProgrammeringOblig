package no.HiOAProsjektV2013.Interface;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//tingene her må legges i main-vindus-klassen
public class ListeBoks<E> implements ListSelectionListener{
	private final int ROWCOUNT = 10;
	
	public ListeBoks(){
		
	}


	public JList<E> listiFy(ArrayList<E> array){
		JList<E> listen = new JList<>();
		E[] tilArray = (E[]) array.toArray();
		listen.setListData(tilArray);
		listen.setVisibleRowCount(ROWCOUNT);
		listen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listen.addListSelectionListener(this);
		
		return listen;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Det som skal skje når vi clicker objektet
		if(e.getValueIsAdjusting()){
			JList<E> lista = (JList<E>) e.getSource();
			E valgtObjekt = lista.getSelectedValue();
			//opprettUtvidelsesVindu(valgtObjekt);
		}
	}
	
	
}
