package com.knziha.ankislicer.ui;

import java.util.HashSet;

import com.fenwjian.sdcardutil.CoordCpr;
import com.fenwjian.sdcardutil.LinearLayoutmy;
import com.fenwjian.sdcardutil.TextViewmy;
import com.fenwjian.sdcardutil.myCpr;
import com.knziha.ankislicer.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class KeyWordSpan extends ClickableSpan{
        private PopupActivity context;
        
        int color = -1;//TODO ??? int color = null
        
        public KeyWordSpan(PopupActivity context) {
            this.context = context;
        }
        
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor((color!=-1)?color:Color.RED);
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(true);
            ds.setFakeBoldText(true);
            //ds.set
        }

        @Override
        public void onClick(View widget) {
        	//if(mTv.ignoreNxtUpUp) {
        	///	mTv.ignoreNxtUpUp=false;
        	//	return;
        	//}
        	//mTv.lastClickableSpanTime=System.currentTimeMillis();
        	if(context.currentSpanOnFocus==this) {//隐藏
        		((TextViewmy)widget).highlightBounds=null;
        		context.currentSpanOnFocus=null;
        		((TextViewmy)widget).invalidate();
        		return;
        	}
        		
        	Spanned span = (Spanned) ((TextView)widget).getText();
        	//Toast.makeText(context,"发生了点击效果"+span.getSpanStart(this)+":"+span.getSpanEnd(this),Toast.LENGTH_SHORT).show();
        	final int offA = span.getSpanStart(this);
        	final int offB = span.getSpanEnd(this);
        	int line = ((TextView)widget).getLayout().getLineForOffset(offA);
        	int line2 = ((TextView)widget).getLayout().getLineForOffset(offB);
        	
        	
    		Rect bounds = new Rect();
			((TextView)widget).getLayout().getLineBounds(line, bounds);
        	float left = ((TextView)widget).getLayout().getPrimaryHorizontal(offA);
        	float right = ((TextView)widget).getLayout().getSecondaryHorizontal(offB);
        	if(line==line2) {
	        	((TextViewmy)widget).highlightBounds
	        			=new RectF[] {new RectF(
	        					left,
			        			bounds.top,
			        			right,
			        			bounds.bottom)
	        					};
	        	
        	}else {
        		Rect bounds2 = new Rect();
    			((TextView)widget).getLayout().getLineBounds(line2, bounds2);
    			RectF b = new RectF(left,bounds.top,bounds.right,bounds.bottom);
    			RectF b2 = new RectF(bounds2.left,bounds2.top,right,bounds2.bottom);
    			
    			((TextViewmy)widget).highlightBounds=new RectF[] {b,b2};
        	}
        	context.currentSpanOnFocus=this;
        	((TextViewmy)widget).invalidate();
        	//CMN.show(offA+":"+offB);
        	((TextViewmy)widget).post(new Runnable() {
				@Override
				public void run() {
					CoordCpr<Integer, Integer> span_entryIndexor = new CoordCpr<Integer,Integer>(offA,offB);
		        	HashSet<CoordCpr<Integer, Integer>> entryIndexorPool = context.keysPool.get(span_entryIndexor);
		        	if(entryIndexorPool!=null) {
		        		//CMN.show("哈哈哈！");
		        		if(entryIndexorPool.size()==1) {
		        			CoordCpr<Integer, Integer> recordViewInor = entryIndexorPool.toArray(new CoordCpr[0])[0];
		        			myCpr<CoordCpr<Integer, Integer>, LinearLayout> view = context.recordsPool.searchT(new myCpr<CoordCpr<Integer, Integer>, LinearLayout>(recordViewInor,null));
		        			context.recyclerViewDefinitionList = (LinearLayoutmy) view.value;
		        			context.notifyDataSetChanged();	
		        		}
		        	}
				}});
        	
        }
    }