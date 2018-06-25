package com.fenwjian.sdcardutil;

public class myCpr_strict<T1 extends Comparable<T1>,T2 extends Comparable<T2>> implements Comparable<myCpr_strict<T1,T2>>{
    	public T1 key;
    	public T2 value;
    	public myCpr_strict(T1 k,T2 v){
    		key=k;value=v;
    	}
    	public int compareTo(myCpr_strict<T1,T2> other) {
    		//if()
    			return this.key.compareTo(other.key);
    		//else
    		//	return 
    	}
    	public String toString(){
    		return key+"_"+value;
    	}
    }