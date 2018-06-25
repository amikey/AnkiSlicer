package com.mmjang.ankihelper.plan.ui;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import android.view.MotionEvent;
import android.util.Log;

import com.mobeta.android.dslv.DragSortListView;
import com.fenwjian.sdcardutil.RashSet;
import com.knziha.ankislicer.R;
import com.knziha.ankislicer.ui.CMN;
import com.mobeta.android.dslv.DragSortController;

public class dict_Manager_DSLFragment extends dict_manager_DSLFragmenr_base<String> {
	
	private dict_manager_activity activity;

	
	@Override
    public int getItemLayout() {
        return R.layout.dict_manager_dslitem;
    }

    @Override
    public void setListAdapter() {
    	list = new ArrayList<>();
        activity.RET.putStringArrayListExtra("extra", list);
        adapter = new MyAdapter(list);
        setListAdapter(adapter);
    }

    @Override
    public DragSortController buildController(DragSortListView dslv) {
        MyDSController c = new MyDSController(dslv);
        return c;
    }


    private class MyAdapter extends ArrayAdapter<String> {
      
      public MyAdapter(List<String> mdicts) {
        super(getActivity(), getItemLayout(), R.id.text, mdicts);
      }

      public View getView(int position, View convertView, ViewGroup parent) {
        
    	View v = super.getView(position, convertView, parent);
        v.getBackground().setLevel(1000);
        CheckBox ck = (CheckBox)v.findViewById(R.id.ck);
        if(SelectionMode) {
        	ck.setVisibility(View.VISIBLE);
        	if(Selection.contains(position))
        		ck.setChecked(true);
        	else
        		ck.setChecked(false);
        }else
        	ck.setVisibility(View.GONE);
        return v;
      }
    }
    
    boolean SelectionMode=false;
    RashSet<Integer> Selection = new RashSet();
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	activity = ((dict_manager_activity)getActivity());
        super.onActivityCreated(savedInstanceState);
        mDslv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(SelectionMode) {
					if(Selection.contains(position))
						Selection.removeLastSelected();
					else
						Selection.put(position);
					adapter.notifyDataSetChanged();
				}
			}});
        mDslv.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				SelectionMode=true;
				Selection.put(position);
				adapter.notifyDataSetChanged();
				return true;
			}});
        mDslv.setPadding(0, activity.actionBarHeight, 0, 0);
        
        if(activity.mdicts.length==1) {
        	String dictI = activity.mdicts[0];
        	if(!dictI.equals(""))
        		adapter.add(dictI);
        }
        else for(String dictI:activity.mdicts) {
        		adapter.add(dictI);
        }
        
        adapter.notifyDataSetChanged();
    }
    
    private class MyDSController extends DragSortController {
    	viewHolder vh;
        DragSortListView mDslv;
        public MyDSController(DragSortListView dslv) {
            super(dslv);
            setDragHandleId(R.id.drag_handle);
            mDslv = dslv;
        }

        @Override
        public View onCreateFloatView(int position) {
            View v = adapter.getView(position, null, mDslv);
            //v.getBackground().setLevel(500);
            mDslv.setFloatAlpha(1.0f);
            v.setBackgroundColor(Color.parseColor("#ffffaa"));//TODO: get primary color
            return v;
        }

        @Override
        public void onDestroyFloatView(View floatView) {
            //do nothing; block super from crashing
        }

    }

    private static class viewHolder{
    	private ImageView handle;
    	private TextView title;
    }

}
