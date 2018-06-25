/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmjang.ankihelper.programData;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

import com.fenwjian.sdcardutil.myCpr;
import com.knziha.ankislicer.R;
import com.knziha.ankislicer.ui.CMN;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.plan.OutputPlan;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;


//persist last_selected_plan's pos in DataSupport result list, as int
public class PlanChooserPreference extends DynListPreference {
    protected int oldSelected;
    List<OutputPlan> outputPlanList;
    
    public PlanChooserPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    	super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    	super.init(context, attrs, defStyleAttr, defStyleRes);
    	setSummary("当前方案: "+CMN.currentOutputPlan.getPlanName());
	}
    
    public PlanChooserPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public PlanChooserPreference(Context context, AttributeSet attrs) {
        super(context, attrs, Resources.getSystem().getIdentifier("dialogPreferenceStyle","attr", "android"));
    }
    
    public PlanChooserPreference(Context context) {
    	super(context, null);
    }

    //延迟加载
    @Override
    protected void onClick() {
    	//strategy：每次都重新加载
    	outputPlanList = DataSupport.findAll(OutputPlan.class);
    	ArrayList<String> mEntriesT = new ArrayList<String>();
    	for(int i=0;i<outputPlanList.size();i++) {
    		String name = outputPlanList.get(i).getPlanName();
    		mEntriesT.add(name);
    	}
    	mEntries = mEntriesT.toArray(new String[] {});
    	this.notifyChanged();
    	super.onClick();
    }
    
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if(positiveResult){
        	oldSelected=selectedPosition;
        	CMN.currentOutputPlan=outputPlanList.get(selectedPosition);
        	//同时plan的改变也影响deck、 mNStyle.
        	//只能在 Listener 里面操作
        	Settings.getInstance(getContext()).setLastSelectedPlanName(CMN.currentOutputPlan.getPlanName());
        	this.getOnPreferenceChangeListener().onPreferenceChange(this, selectedPosition);
        	setSummary("当前方案: "+mEntries[selectedPosition]);
        }
    }
    
    //avoid double call of mOnChangeListener.onPreferenceChange()
    @Override
    protected boolean callChangeListener(Object newValue) {
        return true;//must true
    }
    
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
    	super.onSetInitialValue(restoreValue,defaultValue);
    	oldSelected = selectedPosition;
    	setSummary("当前方案: "+CMN.currentOutputPlan.getPlanName());
    }
    
}
