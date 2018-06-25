/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmjang.ankihelper.programData;

import java.util.ArrayList;

import com.ichi2.anki.CollectionHelper;
import com.ichi2.libanki.Collection;
import com.ichi2.libanki.Decks;
import com.knziha.ankislicer.R;
import com.knziha.ankislicer.ui.CMN;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;


//no persist.
public class DeckChooserPreference extends DynListPreference {
    private Long[] mDeckIds;//id

    public DeckChooserPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    	super.init(context, attrs, defStyleAttr, defStyleRes);
        //策略一：官方API 然在相当一部分机器上不稳定 alphabet order
        /*
        ArrayList<String> mEntriesT = new ArrayList<String>();
        ArrayList<Long> mDeckIdsT = new ArrayList<Long>();
        
        new AnkiDroidHelper(context).getApi().getDeckArrayList(mEntriesT,mDeckIdsT);
        mEntries = mEntriesT.toArray(new String[] {});//名称 名称会变
		mDeckIds = mDeckIdsT.toArray(new Long[] {});//id id会消失
		*/
        
        //策略二：直接访问数据库 no order
        Collection mCol = CollectionHelper.getInstance().getCol(context);
    	Decks mDeck = mCol.getDecks();//
    	mEntries = mDeck.allNames().toArray(new String[] {});
    	mDeckIds = mDeck.allIds();
		//有奇怪的东西
    	
        readInCurrentDeck();
	}
    
    public DeckChooserPreference(Context context, AttributeSet attrs, int defStyleAttr) {
    	super(context, attrs, defStyleAttr, 0);
    }
    
    public DeckChooserPreference(Context context, AttributeSet attrs) {
    	super(context, attrs, Resources.getSystem().getIdentifier("dialogPreferenceStyle","attr", "android"));
    }

    public DeckChooserPreference(Context context) {
    	super(context, null);
    }
    
    long deckIDSelected;
    
    public void readInCurrentDeck() {
		//find pos by deck's id
    	int counter=0;
    	deckIDSelected = CMN.currentOutputPlan.getOutputDeckId();
		for(long idor:mDeckIds) {
        	if(deckIDSelected==idor) 
        		break;
        	counter++;
        }
		if(counter>0 && counter<mEntries.length)
			selectedPosition=counter;
		else
			selectedPosition=0;//reset
		//set it
    	setSummary("目标牌组: "+mEntries[selectedPosition]);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if(positiveResult){
	        setSummary("目标牌组: "+mEntries[selectedPosition]);
	    	CMN.show(selectedPosition+":"+deckIDSelected+":"+mDeckIds[selectedPosition]);
	        if(deckIDSelected!=mDeckIds[selectedPosition]) {
	        	deckIDSelected=mDeckIds[selectedPosition];
	        	CMN.currentOutputPlan.setOutputDeckId(deckIDSelected);
	        	CMN.currentOutputPlan.saveToDisk();
	        }
        }
    }
    
	//deck chooser persist nothing.
	//onSetInitialValue will not be called
    
}
