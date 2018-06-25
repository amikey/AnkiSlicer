package com.mmjang.ankihelper.plan;

import com.knziha.ankislicer.ui.MyApplication;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.util.Utils;

import android.content.Context;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liao on 2017/4/20.
 */

public class OutputPlanWrapper{
	AnkiDroidHelper mAnkidroid;
    Context mContext;
    
	public long getOutputModelId() {return -1;};
	
    public OutputPlanWrapper(Context context){
        mAnkidroid = MyApplication.getAnkiDroid();
        mContext = context;
    }
}
