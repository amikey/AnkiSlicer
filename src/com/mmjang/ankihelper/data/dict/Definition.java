package com.mmjang.ankihelper.data.dict;

import java.util.Map;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by liao on 2017/4/20.
 */

public class Definition implements Comparable<Definition>{
	long dictID;
	long defID;
	public String[] examples;
    private Map<String, String> exportElements;
    private String displayHtml;
    int offset;

    public Definition(Map<String, String> expEle, String dspHtml) {
        exportElements = expEle;
        displayHtml = dspHtml;
    }
    
    public Definition(Map<String, String> expEle, String dspHtml,int off) {
        exportElements = expEle;
        displayHtml = dspHtml;
        this.offset=off;
    }
    public Definition(Map<String, String> expEle, String dspHtml,String[] examples_) {
        exportElements = expEle;
        displayHtml = dspHtml.trim();
        examples = examples_;
    }

    public String getExportElement(String key) {
        return exportElements.get(key);
    }

    public boolean hasElement(String key) {
        return exportElements.containsKey(key);
    }

    public String getDisplayHtml() {
        return displayHtml;
    }

	@Override
	public int compareTo(Definition other) {
		// TODO Auto-generated method stub
		return offset-other.offset;
	}

	public Spanned getHtml() {
		return Build.VERSION.SDK_INT>=24?Html.fromHtml(displayHtml, Html.FROM_HTML_MODE_LEGACY)
				:Html.fromHtml(displayHtml);
	}
}
