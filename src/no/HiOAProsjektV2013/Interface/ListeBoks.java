package no.HiOAProsjektV2013.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import no.HiOAProsjektV2013.DataStructure.Fag;
import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;
import no.HiOAProsjektV2013.DataStructure.Student;
import no.HiOAProsjektV2013.DataStructure.Studieprogram;

//tingene her må legges i main-vindus-klassen
//endre til overload constructors på "Listiyfy"? for å ikke opprette mange typeparameteriserte objekter i testwindow?
public class ListeBoks<E> implements ListSelectionListener, ActionListener{
	private final int ROWCOUNT = 10;
	private Dimension størrelse = new Dimension(300,300);
	private Object valgt;
	private JPanel vis;
	private JTextArea info;
	private Skole skolen;
	
	public ListeBoks(Skole skolen){
		this.skolen = skolen;
	}
	
	public JPanel visResultat(JList<E> liste){
		vis = new JPanel(new BorderLayout());
		info = new JTextArea(10,32);
		info.setBorder(BorderFactory.createLoweredBevelBorder());
		info.setEditable(false);
		info.setLineWrap(true);
		JButton rediger = new JButton("Rediger"); 
		rediger.addActionListener(this);
		
		vis.add(info, BorderLayout.NORTH);
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
		listen.setFixedCellWidth(240);
		//listen.setPreferredSize(størrelse);
		
		return listen;
	}
	public JComboBox<E> combify(ArrayList<E> array){
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
		if(o instanceof Student)
			info.setText(((Student) o).fullString());
		if(o instanceof Laerer)
			info.setText(((Laerer) o).fullString());
		if(o instanceof Fag)
			info.setText(((Fag) o).fullString());
		if(o instanceof Studieprogram)
			info.setText(((Studieprogram) o).fullString());

	}
	
	private void setValgt(Object v){
		valgt = v;
	}
	public Object getValgt(){
		return valgt;
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
		PopupVindu pop = new PopupVindu(vis, valgt, skolen);
	}
}
