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
	private StudentRegister studentene;
	
	
	public Window(){
		super();
		
		studentene = new StudentRegister();
		
		ta = new JTextArea(20,25);
		add = new JButton("Add student");
		show = new JButton("Show students");
		
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

	private void addStudent(){
		studentene.addStudent("Are", "areATsomething.com", 95153437, "lillehammer 1", 2013);
	}

	private void display(){
		String stringen = studentene.toString();
		
		ta.setText(stringen);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == show){
			display();
		}
		if(e.getSource() == add){
			addStudent();
			System.out.println("ding");
		}
	}
	
}
