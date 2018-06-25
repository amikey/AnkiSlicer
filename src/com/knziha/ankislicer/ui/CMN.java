package com.knziha.ankislicer.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mmjang.ankihelper.data.dict.Definition;
import com.mmjang.ankihelper.data.dict.YoudaoOnline;
import com.mmjang.ankihelper.data.dict.YoudaoResult;
import com.mmjang.ankihelper.plan.OutputPlan;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.Toast;




//common
public class CMN{
	public static DisplayMetrics dm;
	public static int sdkVersionCode = 100;
	public static Activity a;
	public static int actionBarHeight;
	public static Boolean module_set_invalid = true;
	public static LayoutInflater inflater;
	public static  int adapter_idx=0;
	public static FragmentTransaction fragmentTransaction;
	public static FragmentManager  managerF;
	public static String EXTRA_QUERY;
	
	
	//UI交互
	public static int fmain_moveMode=0;
	public static boolean isServiceBinded;
	public static int indexOffset;
	public static long deckSelected;
	
	
	public static OutputPlan currentOutputPlan;
	
	public static void show(String string) 
	{Toast.makeText(a, string, Toast.LENGTH_SHORT).show();}  
	
	public static final String[] EXP_ELE_LIST = new String[]{
            "单词",
            "音标",
            "释义",
            "有道美式发音",
            "有道英式发音",
            "复合项"
    };
	
	public static Definition toDefinition(YoudaoResult youdaoResult){
        String notiString = "<font color='gray'>本地词典未查到，以下是有道在线释义</font><br/>";
        String definition = "<b>" + youdaoResult.returnPhrase + "</b><br/>";
        for(String def : youdaoResult.translation){
            definition += def + "<br/>";
        }

        definition += "<font color='gray'>网络释义</font><br/>";
        for(String key : youdaoResult.webTranslation.keySet()){
            String joined = "";
            for(String value : youdaoResult.webTranslation.get(key)){
                joined += value + "; ";
            }
            definition += "<b>" + key + "</b>: " + joined + "<br/>";
        }

        Map<String, String> exp = new HashMap<>();
        exp.put(EXP_ELE_LIST[0], youdaoResult.returnPhrase);
        exp.put(EXP_ELE_LIST[1], youdaoResult.phonetic);
        exp.put(EXP_ELE_LIST[2], definition);
        exp.put(EXP_ELE_LIST[3], getYoudaoAudioTag(youdaoResult.returnPhrase, 2));
        exp.put(EXP_ELE_LIST[4], getYoudaoAudioTag(youdaoResult.returnPhrase, 1));
        exp.put(EXP_ELE_LIST[5], getCombined(exp));

        return new Definition(exp, notiString + definition);
    }
	public static String getYoudaoAudioTag(String word, int voiceType){
        return "[sound:http://dict.youdao.com/dictvoice?audio=" + word + "&type=" + voiceType +"]";
    }
	public static String getCombined(Map<String, String> eleMap) {
        return "<b>" + eleMap.get(EXP_ELE_LIST[0]) + "</b> " + eleMap.get(EXP_ELE_LIST[1]) +
                eleMap.get(EXP_ELE_LIST[3])  + "" + eleMap.get(EXP_ELE_LIST[2]).replace("<br/>", " ");

    }
	public static Definition SearchYouDao() throws IOException {
		if(true)
			return toDefinition(YoudaoOnline.getDefinition(EXTRA_QUERY));
		else
			return null;
	}
	
}