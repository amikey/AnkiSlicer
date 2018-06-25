package com.fenwjian.sdcardutil;


/*	compare 2-d coordinates */
public class CoordCpr<T1 extends Comparable<T1>,T2 extends Comparable<T2>>  implements Comparable<CoordCpr<T1,T2>>{

	T1 key1;
	T2 key2;
	
	public CoordCpr(T1 k1,T2 k2) {
		key1=k1;
		key2=k2;
	}
	
	@Override
	public int compareTo(CoordCpr<T1,T2> other) {
		if(key1.compareTo(other.key1)!=0)
			return key1.compareTo(other.key1);
		else if(key2.compareTo(other.key2)!=0)
			return key2.compareTo(other.key2);
		return 0;
	}
	
	public String toString(){
		return key1+"_"+key2;
	}

}
