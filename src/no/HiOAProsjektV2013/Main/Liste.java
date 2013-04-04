package no.HiOAProsjektV2013.Main;

import java.util.LinkedList;
import java.util.List;

public class Liste<E> {

	private List<E> register = new LinkedList<E>();
	
	public void add(E obj){
		register.add(obj);
	}
	public E get(int i){
		return register.get(i);
	}
	
	
	//Muligens utdatert metode, se Skole
//	public Laerer find(String navn){
//		for(int i = 0;i<register.size();i++){
//			if(navn.equals(((Laerer)register.get(i)).getNavn())){
//				return ((Laerer)register.get(i));
//			}
//		}
//		
//		return null;
//	}
	
	public E find(E obj){
		E e = null;
		for(int i = 0; i < register.size();i++){
			if(obj.equals(register.get(i)))
				e = register.get(i);
			return e;
		}
		
		return null;
	}
	
	public int size(){
		return register.size();
	}
	
}
