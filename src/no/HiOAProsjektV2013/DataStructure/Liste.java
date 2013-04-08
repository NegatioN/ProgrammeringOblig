package no.HiOAProsjektV2013.DataStructure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


//OVERFLØDIG FOR NÅ. Om vi finner en god måte å bruke arv, burde vi vel det.
public abstract class Liste<E> {
	private LinkedList<E> register = new LinkedList<>();
	private Iterator<E> iterator;
	
	public Liste(){
		
	}
	public LinkedList<E> getRegister(){
		return register;
	}
	
	public void refreshIterator(){
		iterator = register.iterator();
	}
}
