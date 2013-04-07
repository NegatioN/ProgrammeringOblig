package no.HiOAProsjektV2013.DataStructure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Liste<E> {
	//listenes parentklasse implementerer en privat linkedlist
	//bruk listemetoder for innsetting o.l.

	private List<E> register;
	//iterator må lages på nytt hver gang et objekt har blitt lagt til
	//ellers oppstår concurrentmodification-exception.
	private Iterator<E> iterator;
	
	
//	public Liste(){
//		register = new LinkedList<E>();
//	}
//	
//	//finner objektet ved en iterator while loop
//	public E find(E obj){
//		E e = null;
//		iterator = register.iterator();
//		
//		while(iterator.hasNext()){
//			if(obj.equals(iterator.next())){
//				e = iterator.next();
//				return e;
//			}
//			iterator.next();
//		}
//		return null;
//	}
//	public List<E>
//	
//	public Iterator<E> getIterator(){
//		return iterator;
//	}
	
	private void refreshIterator(){
		iterator = register.iterator();
	}
}
