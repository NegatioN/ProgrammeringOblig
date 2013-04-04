package no.HiOAProsjektV2013.Main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Window extends JFrame implements ActionListener{
	
	public static void main(String[] args){
		Window w = new Window();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea ta;
	private JButton add, show;
	private Skole skolen;
	
	
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
		Laerer l = new Laerer("Eva", "EvaATpost.com", 95153437, "PI227");
		skolen.getLærerne().add(l);
	}
	
//	private void addStudent(){
//		skolen.getStudentene().addStudent("Are", "areATsomething.com", 95153437, "lillehammer 1", 2013);
//	}

	private void display(){
		String stringen = finnLærere("Eva");
		
		if(stringen == null)
			System.out.println("NULL");
		ta.setText(stringen);
	}
	
	private String finnLærere(String s){
		Laerer[] lærerne = skolen.findLærer(s);
		String stringen = new String();
		
		for(int i = 0; i < lærerne.length;i++){
			stringen += lærerne[i].getNavn() + "\n";
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
