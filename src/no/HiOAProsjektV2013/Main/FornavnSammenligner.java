package no.HiOAProsjektV2013.Main;
//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 14.05.13
import java.io.Serializable;
import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.Comparator;

import no.HiOAProsjektV2013.DataStructure.Person;

//Comparator for fornavn-sortering og søk.
public class FornavnSammenligner implements Comparator<Person>, Serializable{

	private static final long serialVersionUID = 1002L;
	private String rekkefølge = "<\0<0<1<2<3<4<5<6<7<8<9"
			+ "<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j"
			+ "<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t"
			+ "<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";
	
	private RuleBasedCollator kollator;
	
	public FornavnSammenligner(){
		try{
			kollator = new RuleBasedCollator(rekkefølge);
			kollator.setStrength(Collator.PRIMARY);
		}catch(ParseException e){
			e.printStackTrace();
		}
	}

	@Override
	public int compare(Person p1, Person p2) {
		String p1f = p1.getfNavn();
		String p1e = p1.geteNavn();
		String p2f = p2.getfNavn();
		String p2e = p2.geteNavn();
		
		int d = kollator.compare(p1f, p2f);
		if(d == 0){
			d = kollator.compare(p1e, p2e);
			//hvis hele navnet er identisk, plasser 1 spot før forrige instans.
			if(d == 0){
				return -1;
			}
		}

		return d;
	}
}
