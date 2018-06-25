package com.mmjang.ankihelper.plan;

import com.knziha.plod.dictionary.mdict;
import com.mmjang.ankihelper.util.Utils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liao on 2017/4/20.
 */

public class OutputPlan extends DataSupport {
    private String planName;
    private String mdPaths="/sdcard/mdictlib/ode2_raw.mdx\n/sdcard/mdictlib/NameOfPlants.mdx\n/sdcard/mdictlib/新日漢大辭典.mdx\n/sdcard/mdictlib/Irish-En.mdx\n/sdcard/mdictlib/俄汉汉俄辞典.mdx";
	private String fieldsMap_internal="";
	
	
    public int homeDictIdx = 0;
    public int mainNote_Style;
    public int planIndex;
    
    private long mNoutputDeckId=-1;
    private long mNoutputModelId=-1;
    
    private long outputDeckId;
    private long outputModelId;
    
    private String[] fieldsMap;
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanName() {
        return planName;
    }
    
    public void setOutputDeckId(long outputDeckId) {
        this.outputDeckId = outputDeckId;
    }

    public long getOutputDeckId() {
        return outputDeckId;
    }

    public void setOutputModelId(long outputModelId) {
        this.outputModelId = outputModelId;
    }

    public  long getOutputModelId(long outputModelId) {
        return outputModelId;
    }
    
    public long getOutputModelId() {
        return outputModelId;
    }

   public void setFieldsMap(String[] fieldsMap_) {
	    StringBuilder sb = new StringBuilder();
	   	for(String fi:fieldsMap_) {
	   		sb.append(fi).append("\n");
	   	}
	   	fieldsMap_internal = sb.toString();
	   	sb.setLength(0);
    }

    public String[] getFieldsMap() {
    	if(fieldsMap==null)
    		fieldsMap = fieldsMap_internal.split("\n");
    	return fieldsMap; 
	}
    
    
    public String[] getMdicts() {
    	return mdPaths.split("\n");
        // new ArrayList<String>(Arrays.asList(fnL));
    }
	public String getMdicts_() {
		return mdPaths;
	}
    public void setMdicts(ArrayList<String> md) {
    	StringBuilder sb = new StringBuilder();
    	for(int i=0;i<md.size()-1;i++) {
    		sb.append(md.get(i)).append("\n");
    	}
    	if(md.size()>0)
    		sb.append(md.get(md.size()-1));
    	mdPaths = sb.toString();
    	sb.setLength(0);
    }
    public void setMdicts_(String dicts) {
    	mdPaths = dicts;
    }
    
	public void saveToDisk() {//TODO minimize IO ops
        //Log.e("gson",new MyLitepalGson().toJson(outputPlanList.get(0)));
		try {
			new File("/sdcard/AnkiDroid/3rd_SlicerDir/").mkdirs();
			FileOutputStream fout = new FileOutputStream("/sdcard/AnkiDroid/3rd_SlicerDir/currOutPlan.json");
			fout.write(new MyLitepalGson().toJson(this).getBytes());
			fout.flush();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static OutputPlan resumeFromDisk() {//TODO minimize IO ops
        //Log.e("gson",new MyLitepalGson().toJson(outputPlanList.get(0)));
		try {
			File fin = new File("/sdcard/AnkiDroid/3rd_SlicerDir/currOutPlan.json");
			if(!fin.exists()) return null;
			byte[] buffer = new byte[(int) fin.length()];
			FileInputStream fout = new FileInputStream("/sdcard/AnkiDroid/3rd_SlicerDir/currOutPlan.json");
			fout.read(buffer);
			
			fout.close();
			OutputPlan ret = new MyLitepalGson().fromJson(new String(buffer),OutputPlan.class);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


    
}
