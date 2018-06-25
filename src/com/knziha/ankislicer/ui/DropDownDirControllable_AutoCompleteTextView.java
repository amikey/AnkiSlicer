package com.knziha.ankislicer.ui;

import java.lang.reflect.Field;

import com.djr.fitpopupwindow.utils.DensityUtils;
import com.djr.fitpopupwindow.utils.FitPopupWindow;
import com.djr.fitpopupwindow.utils.ScreenUtils;
import com.knziha.ankislicer.R;
import com.knziha.ankislicer.ui.PopupActivity.ListViewAdapter;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
//		     DamnAndroidDamnAndroidDamnAndroidDamnAndroid
public class DropDownDirControllable_AutoCompleteTextView extends EditText{
	public DropDownDirControllable_AutoCompleteTextView(Context context) {
		super(context);
		init(context);
	}
	public DropDownDirControllable_AutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public DropDownDirControllable_AutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	public DropDownDirControllable_AutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}
 
	private PopupWindow mPopup; 
	public ListView l;
	public void init(Context context){
		l= (ListView) LayoutInflater.from(context).inflate(R.layout.simple_list,null);
		//l.setSelector();

		//l = new ListView(getContext());
		//l.setSelector(R.drawable.color_double_list_bg);
		//l.setSelector(android.R.drawable.list_selector_background);
		mPopup=new PopupWindow(l, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		mPopup.setBackgroundDrawable(new BitmapDrawable());
		mPopup.setFocusable(false);
		mPopup.setOutsideTouchable(false);//mPopup.setOutsideTouchable(true);
		mPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); 
		mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); 
	}
	public void showDropDown(boolean whetherAdaptive) {
		if(mPopup.isShowing()) {
			if(whetherAdaptive)
				mPopup.update(this, 0, 0, -1, 350);
			else
				mPopup.update(this, 0, 0, -1, WindowManager.LayoutParams.WRAP_CONTENT);
			return;
		}
		mPopup.setWidth(getWidth());
		if(whetherAdaptive)
			mPopup.setHeight(350);
		else
			mPopup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		mPopup.showAsDropDown(this, 0, 0, Gravity.TOP|Gravity.START);
		mPopup.update(this, 0, 0, -1, -1); 
	}

	public void update(float dy) {
		mPopup.update(this, 0, 0, -1, -1);
	}

	BaseAdapter baseAdapter;
	public void setAdapter(BaseAdapter adaptermy) {
		l.setAdapter(baseAdapter=adaptermy);
		
	}
	public void setListSelection(int i) {
		l.setSelection(i);
	}
	public void dismissDropDown() {
		mPopup.dismiss();
	}
	public boolean isPopupShowing() {
		return mPopup.isShowing();
	}
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		l.setOnItemClickListener(onItemClickListener);
	}
	
	


	


}