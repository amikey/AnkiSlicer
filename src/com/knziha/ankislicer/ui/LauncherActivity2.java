package com.knziha.ankislicer.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ichi2.anki.CollectionHelper;
import com.ichi2.async.DeckTask;
import com.ichi2.async.DeckTask.TaskData;
import com.ichi2.libanki.Card;
import com.ichi2.libanki.Collection;
import com.ichi2.libanki.Decks;
import com.knziha.ankislicer.R;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.data.dict.CustomDictionary;
import com.mmjang.ankihelper.data.dict.Definition;
import com.mmjang.ankihelper.data.dict.YoudaoOnline;
import com.mmjang.ankihelper.domain.CBWatcherService;
import com.mmjang.ankihelper.plan.DefaultPlan;
import com.mmjang.ankihelper.plan.OutputPlan;
import com.mmjang.ankihelper.plan.ui.PlansManagerActivity;
import com.mmjang.ankihelper.programData.Settings;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LauncherActivity2 extends AppCompatActivity {

    Settings settings;

	ViewPager viewPager;
	public Fragment_CardViewer f1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CMN.a=this;
        setContentView(R.layout.main);
        settings = Settings.getInstance(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragments=new ArrayList<Fragment>();
        
		f1 = new Fragment_CardViewer();
		fragments.add(f1);		
		
		FragAdapter adapterf = new FragAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapterf);
		viewPager.setCurrentItem(0);
		
    }
    
    
}
