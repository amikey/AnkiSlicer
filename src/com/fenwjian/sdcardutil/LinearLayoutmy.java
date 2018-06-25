package com.fenwjian.sdcardutil;

import com.knziha.ankislicer.R;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class LinearLayoutmy extends LinearLayout {

	public LinearLayoutmy(Context context) {
		super(context);
	}
	
	@Override
	public void addView(View v){
		v.setTag(R.id.Pos,getChildCount());
		super.addView(v);
	}
}
