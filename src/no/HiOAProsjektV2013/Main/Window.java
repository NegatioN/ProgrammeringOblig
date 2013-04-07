package no.HiOAProsjektV2013.Main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import no.HiOAProsjektV2013.DataStructure.Laerer;
import no.HiOAProsjektV2013.DataStructure.Skole;

public class Window extends JFrame implements ActionListener{
	
	public static void main(String[] args){
		Window w = new Window();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea ta;
	private JButton add, show;
	private Skole skolen;
	private Iterator<Laerer> iterator;
	private Date testDato;
	
	
	public Window(){
		super();
		
		skolen = new Skole();
		
		ta = new JTextArea(20,25);
		add = new JButton("Add teacher");
		show = new JButton("show teacher by name");
		
		add.addActionListener(this);
		show.addActionListener(this);
		
		setLayout(new FlowLayout());
		Container c = getContentPane();
		c.add(add);
		c.add(show);
		c.add(ta);
		
		setSize(500,600);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		}

	
	private void addLærer(){
		Laerer l1 = new Laerer("Linked Evasenr", "EvaATpost.com", 95153437, "PI227");
		skolen.getLærerne().add(l1);
		Laerer l = new Laerer("Eva LINKED LIST Hadler", "EvaATpost.com", 95153437, "PI227");
		skolen.getLærerne().add(l);
	}
	
//	private void addStudent(){
//	testDato = new Date();
//		skolen.getStudentene().addStudent("Are ben Hur", "areATsomething.com", 95153437, "lillehammer 1", testDato);
//	}

	private void display(){
		String stringen = finnLærere("Eva Evasenr");
		
		if(stringen == null)
			System.out.println("NULL");
		ta.setText(stringen);
	}
	
	private String finnLærere(String s){
		ArrayList<Laerer> lærerne = skolen.getLærerne().findLærerByNavn(s);
		
		iterator = lærerne.iterator();
		
		
		String stringen = new String();	
		
		while(iterator.hasNext()){
			Laerer lærer = iterator.next();
			stringen += lærer.getfNavn() + "\t" + lærer.geteNavn() + "\n";
		}
		return stringen;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == show){
			display();
		}
		if(e.getSource() == add){
			addLærer();
			System.out.println("ding");
		}
	}
}
