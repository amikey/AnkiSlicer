package com.knziha.ankislicer.ui;

import java.util.HashMap;
import java.util.HashSet;

import com.fenwjian.sdcardutil.CoordCpr;
import com.fenwjian.sdcardutil.RBTree;
import com.fenwjian.sdcardutil.myCpr;
import com.knziha.ankislicer.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyClickText extends ClickableSpan{
        private PopupActivity context;
        int color = -1;//TODO ??? int color = null
        
        public MyClickText(PopupActivity context,int color_) {
            this.context = context;
            color = color_;
        }

        public MyClickText(PopupActivity context) {
            this.context = context;
        }
        
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor((color!=-1)?color:Color.RED);
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
        	ViewGroup ppp = (ViewGroup) widget.getParent().getParent().getParent();
        	int currEntryPos = context.denv.currEntryPos;//(int) ((View) ppp.getParent()).getTag();
        	int posIdx = (int) ppp.getTag(R.id.Pos);
        	
        	CoordCpr<Integer, Integer> entryIndexor = new CoordCpr<Integer,Integer>(context.denv.currDictIdx,currEntryPos);
        	
        	
        	ColorFilterGettableView bg = ppp.findViewById(R.id.bg);
        	View idor = ppp.findViewById(R.id.add_indicator);
        	int delta = 0;
        	if(color==-1) {//当作子项处理
	        	if(idor.getVisibility()==View.VISIBLE) {
        			idor.setVisibility(View.GONE);//消失
	        		delta-=1;
	        	}else {
	        		delta+=1;
	        		idor.setVisibility(View.VISIBLE);
	        	}	
        	}else {//当作Tag项处理 
        		//CMN.show((ppp.getColorFilter()==null)+"");
	        	if(bg.getColorFilter()!=null) {
	        		delta-=1;
	        		bg.setColorFilter(null);//消失
	        	}else {
	        		delta+=1;
	        		//ViewCompat.setBackgroundTintList(ppp,new ColorStateList(new int[][]{new int[0]}, new int[]{Color.CYAN}));
	        		bg.setColorFilter(new LightingColorFilter(Color.CYAN,Color.CYAN));
	        	}
        	}
    		HashSet<CoordCpr<Integer, Integer>> entrysPool = context.keysPool.get(new CoordCpr<Integer,Integer>(context.pselStart,context.pselEnd));
        	if(delta==-1) {//消失
        		
        	}else {//显示
        		if(entrysPool!=null) {//已加入过该 key span
        		
        		}else {//等于空，认为不存在键值，则为初次加入该 key span
        			entrysPool = new HashSet<CoordCpr<Integer, Integer>>();
        			context.keysPool.put(new CoordCpr<Integer,Integer>(context.pselStart,context.pselEnd),
        					entrysPool);
        			//CMN.show(context.pselStart+"~"+context.pselEnd);
        			entrysPool.add(entryIndexor);
        			context.recordsPool.insert(new myCpr<CoordCpr<Integer,Integer> , LinearLayout  >(entryIndexor,
        					(LinearLayout) ppp.getParent()));
        			//初次加入该 key span需要高亮之
        			context.baseSpan.setSpan(new KeyWordSpan(context), context.pselStart,context.pselEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        			//context.tv.clearFocus();
        			
        			context.tv.setTextKeepState(context.baseSpan);

        			
        			//if(Build.VERSION.SDK_INT==24)
        			if(context.tv.hasSelection()) {//需要恢复选择
        				final int st = context.tv.getSelectionStart();
        				final int ed = context.tv.getSelectionEnd();
        				context.tv.post(new Runnable() {
							@Override
							public void run() {
								context.tv.ShowTvSelecionHandle();
								if(Build.VERSION.SDK_INT>=24) {
									if(Build.VERSION.SDK_INT>=25)
									context.tv.setOnSelectionChangedListener(null);
									context.tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,context.tv.xmy,context.tv.ymy, 0));
								}
								if(Build.VERSION.SDK_INT>=25)
								context.tv.postDelayed(new Runnable() {
									@Override
									public void run() {
										context.tv.setSelection(st, ed);
										context.tv.setOnSelectionChangedListener(context.mTSW);
									}}, 200);
							}});
        			}
        		}
        	
        	}
        	if(color==-1 && (int)ppp.getTag()!=-1) {//子项 且有Tag  
        		ViewGroup TagView = (ViewGroup) ((ViewGroup) ppp.getParent()).getChildAt((int)ppp.getTag());
        		ColorFilterGettableView bg2 = TagView.findViewById(R.id.bg);
        		int valid_counter =  (int)TagView.getTag(R.id.valid_counter)+delta;
        		//Toast.makeText(context,"发生了点击效果"+TagView.getTag(R.id.valid_counter),Toast.LENGTH_SHORT).show();
        		TagView.setTag(R.id.valid_counter,valid_counter);
        		//idor = TagView.findViewById(R.id.add_indicator);
        		if(valid_counter<0) {
            		//idor.setVisibility(View.GONE);
        			bg2.setColorFilter(null);
            	}else {
            		//idor.setVisibility(View.VISIBLE);
            		bg2.setColorFilter(new LightingColorFilter(Color.CYAN,Color.CYAN));
            	}
        	}
        	
        	if(ppp.getTag(R.id.currGrossHeader)!=null) {
        		ViewGroup TagView = (ViewGroup) ppp.getTag(R.id.currGrossHeader);
        		ColorFilterGettableView bg2 = TagView.findViewById(R.id.bg);
        		int valid_counter = (int) TagView.getTag(R.id.valid_counter)+delta;
        		TagView.setTag(R.id.valid_counter,valid_counter);
        		//idor = TagView.findViewById(R.id.add_indicator);
        		if(valid_counter<0) {
            		//idor.setVisibility(View.GONE);
        			bg2.setColorFilter(null);
            	}else {
            		//idor.setVisibility(View.VISIBLE);
            		bg2.setColorFilter(new LightingColorFilter(Color.CYAN,Color.CYAN));
            	}
        	}
        	//Toast.makeText(context,"发生了点击效果"+ppp.getTag(),Toast.LENGTH_SHORT).show();
            
        	
            
        
        
        }
    }