package com.knziha.ankislicer.ui;

import android.content.Context;
import android.graphics.ColorFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
//安卓，你这Goo娘养的小婊砸
public class ColorFilterGettableView extends View{
	public ColorFilterGettableView(Context context) {
		super(context);
	}
	public ColorFilterGettableView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ColorFilterGettableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public ColorFilterGettableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	ColorFilter CFCFCF;
	public void setColorFilter(ColorFilter ColorFilter) {
		getBackground().setColorFilter(CFCFCF = ColorFilter);
	}
	public ColorFilter getColorFilter() {
		return CFCFCF;
	}
	
	



	
}