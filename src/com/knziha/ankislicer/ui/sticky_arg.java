package com.knziha.ankislicer.ui;

public class sticky_arg<T>{
	T obj;
	
	sticky_arg(T value){
		obj = value;
	}
	public T getValue(){
		return obj;
	}
	
	public void setValue(T value){
		obj = value;
	}
}
