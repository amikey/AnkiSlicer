package momo.htmlutil;

import java.util.HashMap;

import com.knziha.plod.dictionary.mdict;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class ImageUrlAssit {  
  
    /** 
     * 内部类实现单例模式 
     * 延迟加载，减少内存开销 
     *  
     * @author xuzhaohu 
     *  
     */  
	public HashMap<String,Uri> resTmp;
	public HashMap<String,Drawable> imageCache;
    
    private static class SingletonHolder {  
        private static ImageUrlAssit instance = new ImageUrlAssit();  
    }

	protected static String A = null;

	public HashMap<String,mdict> tmpHash_0_mdictCache;  
  
    /** 
     * 私有的构造函数 
     */  
    private ImageUrlAssit() {  
    	tmpHash_0_mdictCache=new HashMap<String,mdict>();
    	resTmp = new HashMap<String,Uri>();
    	imageCache = new HashMap<String,Drawable>();
    	A="a";
    }  
  
    public static ImageUrlAssit getInstance() {  
        return SingletonHolder.instance;  
    }  
  
    protected void method() {  
        System.out.println("SingletonInner");  
    }  
}