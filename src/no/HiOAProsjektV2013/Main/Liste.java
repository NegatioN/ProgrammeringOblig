package no.HiOAProsjektV2013.Main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Liste<E> {

	private List<E> register = new LinkedList<E>();
	private Iterator<E> iterator = register.listIterator();
	
	public void add(E obj){
		register.add(obj);
	}
	public E get(int i){
		return register.get(i);
	}
	
	//finner objektet ved en iterator while loop
	public E find(E obj){
		E e = null;
		
		while(iterator.hasNext()){
			if(obj.equals(iterator.next())){
				e = iterator.next();
				return e;
			}
			iterator.next();
		}
		return null;
	}
	
	public int size(){
		return register.size();
	}
	public Iterator<E> getIterator(){
		return iterator;
	}
	
}
