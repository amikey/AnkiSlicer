package com.bung87.customshare;

import java.util.List;

import com.knziha.ankislicer.R;
import com.knziha.ankislicer.ui.CMN;
import com.knziha.ankislicer.ui.PopupActivity;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ShareCustomAdapter extends BaseAdapter implements OnClickListener {
	LayoutInflater inflater;
	Context _context;
	public List<AppInfo> _list;
	String[] _list_pre_empt;
	
	@Override//pre empt onClick
	public void onClick(View v) {
		//CMN.show(System.currentTimeMillis()+"");
		int pos = (int) v.getTag();
		switch(pos) {
			case 0:
				View vTmp = LayoutInflater.from(_context).inflate(R.layout.simple_add_menu,null);
            	final PopupWindow mPopup=new PopupWindow(vTmp, WindowManager.LayoutParams.WRAP_CONTENT,
        				WindowManager.LayoutParams.WRAP_CONTENT);
            	oiclMy.preDeleteSelected();
            	vTmp.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//CMN.show("asd");
						oiclMy.deleteSelected();
						mPopup.dismiss();
					}});
            	((TextView)vTmp.findViewById(R.id.text1)).setText("请确认删除");
        		mPopup.setBackgroundDrawable(new BitmapDrawable());
        		mPopup.setFocusable(false);
        		mPopup.setOutsideTouchable(true);
        		mPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); 
        		mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); 
        		mPopup.setHeight(v.getHeight()+10);
        		mPopup.setWidth(v.getWidth()+8);
        		View anchor = ((Activity)_context).getWindow().getDecorView().findViewById(R.id.actionMenu_share);
        		mPopup.showAsDropDown(anchor, 0, 0, Gravity.TOP|Gravity.START);
        		mPopup.update(anchor, -8, 0, -1, -1); 
        		//mPopup.showAsDropDown(v, 0, -50, Gravity.TOP|Gravity.START);
        		//mPopup.update(v, 0, -50, -1, -1); 
			break;
			case 1:
				oiclMy.sendToFront();
			break;
			case 2:
				oiclMy.sendToBack();
			break;
			case 3:
				oiclMy.populateFurther();
			break;
		}
	}

	public interface OnItemClickListenermy {
		public void populateFurther();

		public void sendToFront();

		public void sendToBack();
		
		public void preDeleteSelected();

		public void deleteSelected();

		
	}OnItemClickListenermy oiclMy;
	
	public ShareCustomAdapter(Context mContext, List<AppInfo> shareAppInfos) {
		 _context = mContext;
		 _list= shareAppInfos;
		 inflater = LayoutInflater.from(mContext);
		 _list_pre_empt = _context.getResources().getStringArray(R.array.share_pre_empt);
	}
	
	int number_margin=4;
	public int getCount() {return _list.size()+number_margin;}
	  
    public Object getItem(int pos) {
    	if(pos<number_margin)
    		return null;
    	return _list.get(pos-number_margin);
	}

	  public View getView ( int position, View convertView, ViewGroup parent ) {
		  if (convertView == null) {
			  convertView = ( LinearLayout ) inflater.inflate(  R.layout.popup_share_item, null );
		  }
		  convertView.findViewById(R.id.indicator).setVisibility(View.INVISIBLE);
		  if(position<number_margin) {
			  TextView tvName = (TextView) convertView.findViewById(R.id.share_item_name);
	          tvName.setText(_list_pre_empt[position]);
	          ImageView icon = (ImageView) convertView.findViewById(R.id.share_item_icon);
	          icon.setImageDrawable(null);
	          convertView.setTag(position);
	          convertView.setOnClickListener(this);
			  convertView.setClickable(true);
			  return convertView;
		  }
		  convertView.setOnClickListener(null);
		  convertView.setClickable(false);
		  AppInfo infoI = (AppInfo) getItem( position );
		  if(infoI.isInternal())
			  convertView.findViewById(R.id.indicator).setVisibility(View.VISIBLE);
          TextView tvName = (TextView) convertView.findViewById(R.id.share_item_name);
          tvName.setText(infoI.getAppName());
          ImageView icon = (ImageView) convertView.findViewById(R.id.share_item_icon);
          icon.setImageDrawable(  infoI.getAppIcon());
          return convertView;
    }

	@Override
	public int getItemViewType(int arg0) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
		// TODO Auto-generated method stub
	
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		 
		return true;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setListMargin(int i) {
		number_margin = i;
		//notifyDataSetChanged();this is not working here,why?
	}

	public void setPopulaterFutherListener(OnItemClickListenermy onItemClickListenermy) {
		oiclMy=onItemClickListenermy;
	}

	

}
