package com.knziha.ankislicer.ui;

import com.mmjang.ankihelper.util.ViewUtil;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewmy extends ScrollView {  
    private Context mContext;  
  
    public ScrollViewmy(Context context) {  
        super(context);  
        init(context);  
    }  
  
    public ScrollViewmy(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init(context);  
  
    }  
  
    public ScrollViewmy(Context context, AttributeSet attrs, int defStyleAttr) {  
        super(context, attrs, defStyleAttr);  
        init(context);  
    }  
  
    private void init(Context context) {  
        mContext = context;  
    }  
  
    public interface ScrollViewListener {
        void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy);
    }
    private ScrollViewListener sl = null;
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.sl = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        if (sl != null)
            sl.onScrollChanged(this, x, y, oldx, oldy);
        super.onScrollChanged(x, y, oldx, oldy);
    }
    
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        /*try {  
            //最大高度显示为屏幕内容高度的一半  
            Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();  
            DisplayMetrics d = new DisplayMetrics();  
            display.getMetrics(d);  
        //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）  
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(ViewUtil.dp2px(100), MeasureSpec.AT_MOST);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  */
        //重新计算控件高、宽  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
    }  
}  