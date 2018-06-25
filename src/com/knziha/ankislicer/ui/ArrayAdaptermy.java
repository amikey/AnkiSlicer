package com.knziha.ankislicer.ui;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ArrayAdaptermy<T> extends ArrayAdapter<T> {

	protected List<T> mObjects;
	
	Field mObjectsF;
	
	public ArrayAdaptermy(Context context, int resource) {
		super(context, resource);
		try {
			mObjectsF = ArrayAdapter.class.getDeclaredField("mObjects");
			mObjectsF.setAccessible(true);
		
		
			//mObjects = (List<T>) mObjectsF.get(this);
		} catch (IllegalArgumentException | NoSuchFieldException e) {
			CMN.show(""+e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public void setArray(List<T> o) {
		//mObjects=o;
		try {
			mObjectsF.set(this, o);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			CMN.show(""+e.getLocalizedMessage());
			e.printStackTrace();
		}
		notifyDataSetChanged();
	}
}
