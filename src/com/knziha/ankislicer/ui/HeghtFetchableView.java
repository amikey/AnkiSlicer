package com.knziha.ankislicer.ui;

import com.knziha.ankislicer.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class HeghtFetchableView extends LinearLayout {
	public float Height;
	
	private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		String tmp = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
		//CMN.show(tmp);
		Height = Float.valueOf(tmp.replace("dp","").replace("dip",""));
	}
	
	public HeghtFetchableView(Context context) {
		super(context);
	}
	
	public HeghtFetchableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0, 0);
	}

	public HeghtFetchableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr, 0);
	}

	public HeghtFetchableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr, defStyleRes);
	}

		
		
		
	@Override
	public void addView(View v){
		super.addView(v);
	}
}
