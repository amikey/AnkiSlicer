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
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    Settings settings;

	NoScrollViewPager viewPager;
	public Fragment_Launcher f1;

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
        
        Fragment_CardViewer f0 = new Fragment_CardViewer();
		fragments.add(f0);
		
		f1 = new Fragment_Launcher();
		
		fragments.add(f1);		
		
		FragAdapter adapterf = new FragAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapterf);
		viewPager.setCurrentItem(1);	
    
        //CMN.show(""+PreferenceManager.getDefaultSharedPreferences(this).getInt("haha", 100));
    
    }
    
    
}
