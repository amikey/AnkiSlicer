package com.knziha.ankislicer.ui;

import java.lang.reflect.Field;

import com.djr.fitpopupwindow.utils.DensityUtils;
import com.djr.fitpopupwindow.utils.FitPopupWindow;
import com.djr.fitpopupwindow.utils.ScreenUtils;
import com.knziha.ankislicer.R;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

public class AutoCompleteTextViewmy extends AutoCompleteTextView{
	public AutoCompleteTextViewmy(Context context) {
		super(context);
		init();
	}
	public AutoCompleteTextViewmy(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public AutoCompleteTextViewmy(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	public AutoCompleteTextViewmy(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}
	public AutoCompleteTextViewmy(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes,
			Theme popupTheme) {
		super(context, attrs, defStyleAttr, defStyleRes, popupTheme);
		init();
	}
	private ListPopupWindow mPopup; 
	private PopupWindow mPopupmPopup; 
	public void init(){
		try {
			Field mEditor = AutoCompleteTextView.class.getDeclaredField("mPopup");
		
			mEditor.setAccessible(true);
			mPopup= (ListPopupWindow) mEditor.get(this);
			
			mEditor = ListPopupWindow.class.getDeclaredField("mPopup");
			mEditor.setAccessible(true);
			mPopupmPopup= (PopupWindow) mEditor.get(mPopup);
			
			
			//mPopup.setVerticalOffset(-125);
			//mPopup.setDropDownGravity(Gravity.END|Gravity.BOTTOM);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public void showMyDropDown() {
		showDropDown();
		mPopupmPopup.showAtLocation(this, Gravity.TOP, 0, 0);
	}


	


}