package com.fenwjian.sdcardutil;

import java.util.ArrayList;

    public class additiveMyCpr1 implements Comparable<additiveMyCpr1>{
        private final static String replaceReg = " |:|\\.|,|-|\'";
        private final static String emptyStr = "";
    	public String key;
    	public ArrayList<Integer> value;
    	public additiveMyCpr1(String k,ArrayList<Integer> v){
    		key=k;value=v;
    	}
    	public int compareTo(additiveMyCpr1 other) {
    		//return this.key.toLowerCase().replaceAll(replaceReg,emptyStr).compareTo(other.key.toLowerCase().replaceAll(replaceReg,emptyStr));
    		//return this.key.compareTo(other.key);                 //replaceReg = " |:|\\.|,|-|\'
    		//return this.key.toLowerCase().replace(" ",emptyStr).replace("'",emptyStr).compareTo(other.key.toLowerCase().replace(" ",emptyStr).replace("'",emptyStr));
    		return this.key.toLowerCase().replace(" ",emptyStr).replace("'",emptyStr).replace(":",emptyStr).replace(".",emptyStr).replace("-",emptyStr).replace(",",emptyStr).compareTo(other.key.toLowerCase().replace(" ",emptyStr).replace("'",emptyStr).replace(":",emptyStr).replace(".",emptyStr).replace("-",emptyStr).replace(",",emptyStr));
    		
    	}
    	public String toString(){
    		String str = ""; for(Integer i:value) str+="@"+i;
    		return key+"____"+str;//;
    	}
    }