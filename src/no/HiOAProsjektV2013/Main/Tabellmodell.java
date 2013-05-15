package no.HiOAProsjektV2013.Main;
//Lars-Erik Kasin - s178816 - Dataingeniør - 1AA
//Siste versjon: 14.05.13

import java.util.LinkedList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import no.HiOAProsjektV2013.DataStructure.Eksamen;
import no.HiOAProsjektV2013.DataStructure.EksamensDeltaker;
import no.HiOAProsjektV2013.DataStructure.Student;


//Tabellmodell for våre JTables som befinner seg i EditPanel
public class TabellModell extends AbstractTableModel{

	private static final long serialVersionUID = 100110L;
	public static final int FAG = 0, DATO  = 1, SNR = 2, MØTT = 3, KAR = 4, DATOBREDDE = 130, KOLONNER = 5;
	private TableModelListener tabellytter;

	private String[] kolonnenavn = {"Fag", "Dato", "StudentNr", "Møtt", "Karakter"};
	private Object[][] celler = {{"", "", "", "", ""}};	//Tom tabell

	public TabellModell(Eksamen e, TableModelListener tabell){ 	//Fyller tabellen med alle deltakere på en eksamen
		tabellytter = tabell;
		if(!e.getDeltakere().isEmpty()){
			fyllTabell(e.getDeltakere().size(), e.getDeltakere());
		}
	}
	public  TabellModell(Student s, TableModelListener tabell){	//Fyller tabellen med alle eksamener for en student
		tabellytter = tabell;
		if(!s.getEksamener().isEmpty())
			fyllTabell(s.getEksamener().size(), s.getEksamener());
	}

	//Legger inn riktige elementer på det rett plass i tabellen med en for-løkke
	private void fyllTabell(int lengde, LinkedList<EksamensDeltaker> eksamener){
		celler = new Object[lengde][KOLONNER];

		int rad = 0; //Sørger for at hver deltaker havner på en ny rad
		for(EksamensDeltaker ed: eksamener){
			Eksamen e = ed.getFag().findEksamenByDate(ed.getDato());
			celler[rad][FAG] = ed.getFag().getFagkode();
			celler[rad][DATO] = e;
			celler[rad][SNR] = ed;
			celler[rad][MØTT] = ed.isOppmøtt();
			celler[rad++][KAR] = new String(""+ed.getKarakter());
		}
		this.addTableModelListener(tabellytter);
	}

	public Object getValueAt(int rad, int kolonne){ //Henter verdien i en gitt celle
		return celler[rad][kolonne];
	}

	public String getColumnName(int kolonne) { //Henter kolonnenavn
		return kolonnenavn[kolonne];
	}

	public int getRowCount() { //Henter antall rader
		return celler.length; 
	}

	public int getColumnCount() { //Henter antall kolonner
		return celler[FAG].length;
	}

	public boolean isCellEditable(int rad, int kolonne){ //Sørger for at kun møtt, og karakteren er redigerbare
		switch (kolonne) {
		case MØTT:
		case KAR:
			return true;
		default:
			return false;
		}
	}
	public void removeRow(int row){
		fireTableRowsDeleted(row,row);
	}

	public void setValueAt(Object value, int rad, int kolonne) { //Setter ny verdi i gitt celler
		celler[rad][kolonne] = value;
		fireTableCellUpdated(rad, kolonne);
	}

	//For å informere tabellmodellen om kolonnenes datatyper.
	public Class<? extends Object> getColumnClass(int kolonne) {
		return celler[FAG][kolonne].getClass();
	}
} //End of class Tabellmodell
