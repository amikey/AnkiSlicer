package com.knziha.ankislicer.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;
import org.neojson.JSONObject;

import com.google.gson.JsonObject;
import com.knziha.ankislicer.R;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.domain.CBWatcherService;
import com.mmjang.ankihelper.plan.DefaultPlan;
import com.mmjang.ankihelper.plan.MyLitepalGson;
import com.mmjang.ankihelper.plan.OutputPlan;
import com.mmjang.ankihelper.plan.ui.PlansManagerActivity;
import com.mmjang.ankihelper.programData.Settings;
import com.mmjang.ankihelper.util.Constant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class Fragment_Launcher extends PreferenceFragment  {
	AnkiDroidHelper mAnkiDroid;
    Settings settings;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //从xml文件加载选项 
        addPreferencesFromResource(R.xml.launcher_preferences);


    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if(v != null) {
            ListView lv = (ListView) v.findViewById(android.R.id.list);
            lv.setPadding(0, 0, 0, 0);
        }
        //CMN.show("kong:"+(v.getParent()==null));
        ViewGroup ret = (ViewGroup) inflater.inflate(R.layout.launcher_holder, null);
        ((ViewGroup)ret.findViewById(R.id.holder)).addView(v);
        return ret;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAnkiApi();
    }
    
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference p) {
    	Intent intent;
    	//CMN.show("onPreferenceTreeClick"+p.getKey());
    	switch(p.getKey()) {
	    	case "btn_open_plan_manager":
                if (mAnkiDroid == null)
                	initAnkiApi();
                intent = new Intent(getActivity(), PlansManagerActivity.class);
                startActivity(intent);
			break;
	    	case "btn_add_default_plan":
	    		if (mAnkiDroid == null) 
                	initAnkiApi();
	    		askIfAddDefaultPlan();
			break;
	    	case "btn_help":
			break;
	    	case "btn_about_and_support":
	    		intent = new Intent(getActivity(), PopupActivity.class);
            	intent.setAction(Intent.ACTION_PROCESS_TEXT);
            	intent.setType("text/plain");
            	intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "細雨帶風濕透黃昏的街道<br>抹去淚水雙眼無故地仰望<br>rрycтнaя пaмять cмотрит нa ночной cвет (俄)  ");
            	startActivity(intent);
			break;
	    	case "switch_monite_clipboard":
	    		if (((SwitchPreference)p).isChecked()) {
	    			CMN.show("checked!");
                    startCBService();
                } else {
                    stopCBService();
                }
			break;
	    	case "test":
    		break;
	    	case "poem":
    		String today = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    		final File TODAY = new File("/sdcard/AnkiDroid/3rd_SlicerDir/DailyPoems/"+today+".json");
    		final File CURSOR = new File("/sdcard/AnkiDroid/3rd_SlicerDir/DailyPoems/PoemPoolCursor");
    		new AsyncTask<Void,Void,String>(){
				@Override
				protected String doInBackground(Void... params) {
					String errorMsg=null;
					if(!TODAY.exists())
					try {
						CURSOR.mkdirs();
						int poemPoolCursorIdx=0;
						try {
							poemPoolCursorIdx = Integer.valueOf(CURSOR.list()[0]);
						}catch(Exception e) {}
						
						LineNumberReader fin = new LineNumberReader(new InputStreamReader(getResources().getAssets().open("poempool.txt")));
						
						poemPoolCursorIdx=poemPoolCursorIdx>339?0:poemPoolCursorIdx;
						

						String ripple = fin.readLine();
						fin.setLineNumber(0);
						while(fin.getLineNumber()<poemPoolCursorIdx) {
							ripple = fin.readLine();
						}
						
						Log.e("asd",ripple);
						for(File fi:CURSOR.listFiles())
							fi.delete();
						new File(CURSOR,""+(++poemPoolCursorIdx)).createNewFile();
						Document doc = Jsoup.connect(ripple).get();
						Elements eles = doc.getElementsByClass("content");
						String html = eles.get(0).html();
						JsonObject gg = new JsonObject();
						gg.addProperty("html", html);
						gg.addProperty("mainkey", "");
						
						TODAY.getParentFile().mkdirs();
						FileOutputStream fout = new FileOutputStream(TODAY);
						fout.write(gg.toString().getBytes());
						fout.flush();
						fout.close();
						return errorMsg;
					} catch (IOException e) {
						e.printStackTrace();
						errorMsg = e.getLocalizedMessage();
					}
					return errorMsg;
				}
				
				@Override
		        protected void onPostExecute(String errorMsg) {
					if(errorMsg==null) {
		            	Intent intent = new Intent(getActivity(), PopupActivity.class);
			        	intent.setAction(Constant.ACTION_OPEN_PROJECT);
			        	intent.putExtra(Constant.EXTRA_PROJECT_PATH, TODAY.getAbsolutePath());
			        	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	startActivity(intent);
					}else {
						Toast.makeText(getActivity(), "连接出错："+errorMsg, Toast.LENGTH_SHORT).show();
					}
				}
				
			}.execute();
    		break;
    	}
		return false;
    }
    
    private void initAnkiApi() {
        mAnkiDroid = new AnkiDroidHelper(getActivity());
        if (!AnkiDroidHelper.isApiAvailable(MyApplication.getContext())) {
            Toast.makeText(getActivity(), R.string.api_not_available_message, Toast.LENGTH_LONG).show();
        }

        if (mAnkiDroid.shouldRequestPermission()) {
            mAnkiDroid.requestPermission(getActivity(), 0);
        }
        else{

        }
    }

    private void startCBService() {
        Intent intent = new Intent(getActivity(), CBWatcherService.class);
        getActivity().startService(intent);
    }

    private void stopCBService() {
        Intent intent = new Intent(getActivity(), CBWatcherService.class);
        getActivity().stopService(intent);
    }
    
    void askIfAddDefaultPlan(){
        List<OutputPlan> plans = DataSupport.findAll(OutputPlan.class);
        for(OutputPlan plan : plans){
            if(plan.getPlanName().equals(DefaultPlan.DEFAULT_PLAN_NAME)){
                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.duplicate_plan_name_complain)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                return;
                            }
                        }).show();
                return ;
            }
        }
        if(plans.size() == 0) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.confirm_add_default_plan)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            DefaultPlan plan = new DefaultPlan(getActivity());
                            plan.addDefaultPlan();
                            Toast.makeText(getActivity(), R.string.default_plan_added, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }

        else{
            new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.confirm_add_default_plan_when_exists_already)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            DefaultPlan plan = new DefaultPlan(getActivity());
                            plan.addDefaultPlan();
                            Toast.makeText(getActivity(), R.string.default_plan_added, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }
    
}