package com.knziha.ankislicer.ui;

import com.knziha.ankislicer.R;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

public class SrcGettableTextView extends TextView{
	private String SRC;

	public SrcGettableTextView(Context context) {
		super(context);
	}
	public SrcGettableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public SrcGettableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public SrcGettableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	
	public void setText(String src) {
		if(Build.VERSION.SDK_INT>=24)
        	setText(Html.fromHtml(SRC=src, Html.FROM_HTML_MODE_COMPACT));
        else
        	setText(Html.fromHtml(SRC=src));
	}
	public String getSource() {
		return SRC;
	}
	public void setSource(String html) {
		SRC = html;
	}




}
