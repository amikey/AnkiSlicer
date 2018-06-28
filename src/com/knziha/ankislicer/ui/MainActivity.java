package com.knziha.ankislicer.ui;

import java.util.ArrayList;
import java.util.List;

import com.fenwjian.sdcardutil.NoScrollViewPager;
import com.knziha.ankislicer.R;
import com.mmjang.ankihelper.programData.Settings;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    Settings settings;

	NoScrollViewPager viewPager;
	public Fragment_Launcher f1;
	Fragment_CardViewer f0;
	
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
		 	if(viewPager.getCurrentItem()==0)
		        switch (keyCode) {
		        case KeyEvent.KEYCODE_VOLUME_DOWN:
		        	if(f0.inSearch)
		        		f0.onClick(f0.main_clister_layout.findViewById(R.id.nxt));
		        	else {
		        		View v = new View(this);
		        		v.setId(R.id.nxt_plain);
		        		f0.onClick(v);
		        	}
		            return true;
		        case KeyEvent.KEYCODE_VOLUME_UP:
		        	if(f0.inSearch)
		        		f0.onClick(f0.main_clister_layout.findViewById(R.id.lst));
		        	else {
		        		View v = new View(this);
		        		v.setId(R.id.lst_plain);
		        		f0.onClick(v);
		        	}
		            return true;
		        case KeyEvent.KEYCODE_VOLUME_MUTE:
		            return true;
		        }
	        return super.onKeyDown(keyCode, event);
	    }

	 
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CMN.a=this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        //disable keyboard auto-coming up feature
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        viewPager.setNoScroll(false);
        
        List<Fragment> fragments=new ArrayList<Fragment>();
        
        f0 = new Fragment_CardViewer();
		fragments.add(f0);
		
		f1 = new Fragment_Launcher();
		
		fragments.add(f1);		
		
		FragAdapter adapterf = new FragAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapterf);
		viewPager.setCurrentItem(1);	
    
        //CMN.show(""+PreferenceManager.getDefaultSharedPreferences(this).getInt("haha", 100));
    
    }
    
    
}
