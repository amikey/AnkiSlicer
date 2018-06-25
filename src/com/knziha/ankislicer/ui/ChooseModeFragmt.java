package com.knziha.ankislicer.ui;

import java.util.List;

import org.litepal.crud.DataSupport;

import com.knziha.ankislicer.R;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.domain.CBWatcherService;
import com.mmjang.ankihelper.plan.DefaultPlan;
import com.mmjang.ankihelper.plan.OutputPlan;
import com.mmjang.ankihelper.plan.ui.PlansManagerActivity;
import com.mmjang.ankihelper.programData.DeckChooserPreference;
import com.mmjang.ankihelper.programData.Settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.preference.PreferenceFragment;
import android.support.v4.preference.PreferenceFragment.OnPreferenceStartFragmentCallback;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChooseModeFragmt extends PreferenceFragment  {
	AnkiDroidHelper mAnkiDroid;
    Settings settings;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.slicer_mode_preferences);

        this.findPreference("last_selected_plan").setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				//CMN.show(System.currentTimeMillis()+":"+newValue);
				((DeckChooserPreference)findPreference("choose_deck")).readInCurrentDeck();
				return true;
			}});
    }
    
    
    
    @Override
    public void onStart() {
        super.onStart();

    }    
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setLayoutParams(
        		new LinearLayout.LayoutParams(
        				getActivity().getResources().getDisplayMetrics().widthPixels*2/3
        				,
        				-2)
        		);

    }
    
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference p) {
    	Intent intent;
    	//CMN.show("onPreferenceTreeClick"+p.getKey());
    	switch(p.getKey()) {
	    	case "choose_mode":
			break;
	    	case "btn_help":
			break;
	    	case "btn_about_and_support":
			break;
	    	case "switch_monite_clipboard":
			break;
	    	case "test":
	    		//getActivity().findViewById()
	    		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
	            FragmentTransaction transaction = fragmentManager.beginTransaction();
	            ChooseModeFragmt prefFragment = new ChooseModeFragmt();
	            transaction.add(R.id.dialogHolder, prefFragment);
	            transaction.commit();
    		break;
    	}
		return false;
    }
   
    
}