package com.bung87.customshare;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class AppInfo {
	final public static String extDir = "/storage/emulated/0/Android/data/com.knziha.ankislicer/beautifulFolders/";
	private String		AppPkgName;
	private String		AppLauncherClassName;
	private String		AppName;
	private String		Action;
	private Drawable	AppIcon;
	private boolean isInternal;
	
	public AppInfo() {
		super();
	}

	public AppInfo(String appPkgName, String appLauncherClassName) {
		super();
		AppPkgName = appPkgName;
		AppLauncherClassName = appLauncherClassName;
	}

	public String getAppPkgName() {
		return AppPkgName;
	}

	public void setAppPkgName(String appPkgName) {
		AppPkgName = appPkgName;
	}

	public String getAppLauncherClassName() {
		return AppLauncherClassName;
	}

	public void setAppLauncherClassName(String appLauncherClassName) {
		AppLauncherClassName = appLauncherClassName;
	}

	public String getAppName() {
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}

	public Drawable getAppIcon() {
		return AppIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		AppIcon = appIcon;
	}
	
	public void toggleInternal() throws IOException {
		if(isInternal) {//删除
        	//new File(extDir+AppPkgName).delete();
			deleteDirWithFile(new File(extDir+AppPkgName));
        }else {//保存
        	new File(extDir+AppPkgName).mkdirs();
        	File iconFile = new File(extDir+AppPkgName+"/"+AppName+"OVIHCS"+AppLauncherClassName+"OVIHCS"+Action);
        	Bitmap bitmap = Bitmap  
                    .createBitmap(  
                    		AppIcon.getIntrinsicWidth(),  
                    		AppIcon.getIntrinsicHeight(),  
                    		AppIcon.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                                    : Bitmap.Config.RGB_565);  
            Canvas canvas = new Canvas(bitmap);  
            AppIcon.setBounds(0, 0, AppIcon.getIntrinsicWidth(),  
            		AppIcon.getIntrinsicHeight());  
            AppIcon.draw(canvas);  
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;  
            // 压缩格式PNG 质量为100
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(iconFile));
            
        }
		isInternal=!isInternal;
	}
    public void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
            	deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

	public boolean isInternal() {
		return isInternal;
	}

	public void setInternal(boolean b) {
		isInternal = b;
	}

	public String getAction() {
		return Action;
	}
	
	public void setAction(String act) {
		Action = act;
	}
}
