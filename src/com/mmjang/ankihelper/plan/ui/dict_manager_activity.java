package com.mmjang.ankihelper.plan.ui;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.knziha.ankislicer.R;
import com.knziha.ankislicer.ui.CMN;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

public class dict_manager_activity extends FragmentActivity implements OnClickListener
{
    private ViewGroup mDrawerLayout;
    private Toolbar toolbar;
    dict_Manager_DSLFragment f1;
    private ViewPager viewPager;  //对应的viewPager  
    private TabLayout mTabLayout;
    int actionBarHeight;
	LayoutInflater inflater;    	
	String[] mdicts;
	Intent RET = new Intent();
	private FloatingActionButton fab;
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if(f1.SelectionMode==true) {
				f1.SelectionMode=false;
				f1.adapter.notifyDataSetChanged();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dict_manager_main);
		setResult(110, RET);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogProperties properties = new DialogProperties();
                properties.selection_mode = DialogConfigs.MULTI_MODE;
                properties.selection_type = DialogConfigs.FILE_SELECT;
                properties.root = new File(DialogConfigs.DEFAULT_DIR);
                properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
                properties.offset = new File("/sdcard/mdictlib");
                properties.opt_dir=new File(getExternalFilesDir(null).getAbsolutePath()+"/favorites/");
                properties.opt_dir.mkdirs();
                properties.extensions = new String[] {"mdd","mdx"};
				FilePickerDialog dialog = new FilePickerDialog(dict_manager_activity.this, properties);
	                dialog.setTitle("请选择保存目录");
	                dialog.setDialogSelectionListener(new DialogSelectionListener() {
	                    @Override
	                    public void
	                    onSelectedFilePaths(String[] files) { //files is the array of the paths of files selected by the Application User. 
	                        for(String fi:files)
	                        	if(!f1.getList().contains(fi))
	                        		f1.getList().add(fi);
	                        f1.adapter.notifyDataSetChanged();
	                    }
	                });
	                dialog.show();
			}});
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
		inflater=LayoutInflater.from(getApplicationContext());
		List<Fragment> fragments=new ArrayList<Fragment>();
	
    
	    String[] tabTitle = {"词典管理","全部词典"};//

		f1 = new dict_Manager_DSLFragment();
		fragments.add(f1);	
		
		FragAdapter adapterf = new FragAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapterf);
	    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        for (int i=0; i<tabTitle.length; i++) 
            mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[i]));
	    mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            	viewPager.setCurrentItem(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
        
	    viewPager.setCurrentItem(0);
		
	    
	    TypedValue tval = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tval, true))
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tval.data,getResources().getDisplayMetrics());
        
		mDrawerLayout = (ViewGroup) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        
        findViewById(R.id.tools1).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        
		Intent intent = getIntent();
		String dicts = intent.getStringExtra("extra");
		mdicts = dicts.split("\n");
		
		
	}
	//onCreate结束
	
   
    @Override
    protected void onDestroy() {
        //TODO unload as well
    	
        
        
        super.onDestroy();
    }
		
    public void showT(String text){
        if(m_currentToast != null)
        m_currentToast.cancel();
        m_currentToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        m_currentToast.show();
    }Toast m_currentToast;

    public class FragAdapter extends FragmentPagerAdapter {

    	private List<Fragment> mFragments;
    	
    	public FragAdapter(FragmentManager fm,List<Fragment> fragments) {
    		super(fm);
    		mFragments=fragments;
    	}

    	@Override
    	public Fragment getItem(int arg0) {
    		return mFragments.get(arg0);
    	}

    	@Override
    	public int getCount() {
    		return mFragments.size();
    	}

    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.back:
				finish();
			break;
			case R.id.tools1://删除
				new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.warn_deletedict, f1.Selection.size()))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	ArrayList<Integer> l = f1.Selection.flatten();
                    	for(int i=0;i<l.size();i++) {
                    		int pos = l.size()-i-1;//count down
                    		f1.list.remove(pos);
                    	}
                    	if(f1.list.size()==0)
                    		f1.SelectionMode=false;
                    	f1.adapter.notifyDataSetChanged();
                    }
                }).show();
			break;
		}
	}




}


