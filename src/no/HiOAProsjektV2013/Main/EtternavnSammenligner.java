package no.HiOAProsjektV2013.Main;

import java.io.Serializable;
import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.Comparator;

import no.HiOAProsjektV2013.DataStructure.Student;

public class EtternavnSammenligner implements Comparator<Student>, Serializable{
	
	private static final long serialVersionUID = 1001L;
	
	private String rekkefølge = "<\0<0<1<2<3<4<5<6<7<8<9"
			+ "<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j"
			+ "<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t"
			+ "<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";
	
	private RuleBasedCollator kollator;
	
	public EtternavnSammenligner(){
		try{
			kollator = new RuleBasedCollator(rekkefølge);
			kollator.setStrength(Collator.PRIMARY);
		}catch(ParseException e){
			e.printStackTrace();
		}
	}

	@Override
	public int compare(Student s1, Student s2) {
		String s1f = s1.getfNavn();
		String s1e = s1.geteNavn();
		String s2f = s2.getfNavn();
		String s2e = s2.geteNavn();
		
		int d = kollator.compare(s1e, s2e);
		System.out.println("1: " + s1e + " 2: " + s2e);
		System.out.println(d);
		if(d == 0)
			return kollator.compare(s1f, s2f);

		return d;
	}

}
