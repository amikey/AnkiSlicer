package com.mmjang.ankihelper.plan;

import android.content.Context;

import com.knziha.ankislicer.ui.MyApplication;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.data.dict.Collins;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by liao on 2017/7/23.
 */

public class DefaultPlan {
    private static final String DEFAULT_VOCABULARY_MODEL_NAME = "Slicer划书默认单词模版";
    private static final String DEFAULT_VOCABULARY_MODEL_NAME_RAW = "Slicer划书默认单词模版";
    private static final String DEFAULT_CLOZE_MODEL_NAME = "ankihelper_default_cloze_card";
    private static final String DEFAULT_DECK_NAME = "ANKI_划书✎";
    public static final String DEFAULT_PLAN_NAME = "Collins(默认方案)";
    AnkiDroidHelper mAnkidroid;
    VocabularyCardModel vc;
    Context mContext;
    public DefaultPlan(Context context){
        mAnkidroid = MyApplication.getAnkiDroid();
        mContext = context;
    }

    public void addDefaultPlan(){
        //Collins collins = new Collins(mContext);
        String[] elements = new String[]{
                " ",
                " ",
                " ",
                " ",
                " ",
                " ",
                "有道美式发音"
        };

        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        for(int i = 0; i < VocabularyCardModel.FILEDS.length; i ++){
            fieldMap.put(VocabularyCardModel.FILEDS[i], elements[i]);
        }
        OutputPlan defaultPlan = new OutputPlan();
        defaultPlan.setPlanName(DEFAULT_PLAN_NAME);
        defaultPlan.setOutputModelId(getDefaultModelId());
        defaultPlan.setOutputDeckId(getDefaultDeckId());
        //defaultPlan.setDictionaryKey("asdf");
        defaultPlan.setFieldsMap(elements);
        defaultPlan.save();
    }


    long getDefaultDeckId(){
        Map<Long, String> deckList = mAnkidroid.getApi().getDeckList();
        for(Long id : deckList.keySet()){
            if(deckList.get(id).equals(DEFAULT_DECK_NAME)){
                return id;
            }
        }
        return mAnkidroid.getApi().addNewDeck(DEFAULT_DECK_NAME);
    }

    long getDefaultModelId(){
    	Long mid = mAnkidroid.findModelIdByName(DEFAULT_VOCABULARY_MODEL_NAME, VocabularyCardModel.FILEDS.length);
        if (mid == null) {
	        String modelName = DEFAULT_VOCABULARY_MODEL_NAME;
	        vc = new VocabularyCardModel(mContext);
	        mid = mAnkidroid.getApi().addNewCustomModel(modelName,
	                vc.FILEDS,
	                vc.Cards,
	                vc.QFMT,
	                vc.AFMT,
	                vc.CSS,
	                null,
	                null
	        );
        }

        
        return mid;
    }


}
