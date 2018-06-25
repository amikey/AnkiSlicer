package momo.htmlutil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.knziha.ankislicer.ui.CMN;
import com.knziha.ankislicer.ui.MyApplication;
import com.knziha.ankislicer.ui.PopupActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class URLImageParser implements ImageGetter {
    Context c;
    TextView container;
    
    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public URLImageParser(TextView t, Context c) {
        this.c = c;
        this.container = t;
        
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();


        ImageGetterAsyncTask asyncTask = 
            new ImageGetterAsyncTask( urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
        	final int h = result.getIntrinsicHeight();
        	urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 
                    + h); 
           // Log.e("宽高0",5*result.getIntrinsicWidth()+":"+result.getIntrinsicHeight());
            result.setBounds(0, 0, result.getIntrinsicWidth(), 0 
                    + h);
            //Log.e("宽高",5*result.getIntrinsicWidth()+":"+result.getIntrinsicHeight());
            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            URLImageParser.this.container.invalidate();
            
            // For ICS
            URLImageParser.this.container.post(new Runnable() {
				@Override
				public void run() {
					URLImageParser.this.container.setHeight((URLImageParser.this.container.getHeight() 
				            + h));
				}
            });
            

        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */

        private Drawable fetchDrawable(String src) {
        	if(ImageUrlAssit.getInstance().imageCache.containsKey(src)) {
        		Log.e("!!!","旧的有！");
        		return ImageUrlAssit.getInstance().imageCache.get(src);//太快了也不行 T. T
        	}
        	Drawable drawable = null;
			try {
				//CMN.show(resTmp.get(src).getPath());
				drawable = Drawable.createFromStream(c.getContentResolver().openInputStream(ImageUrlAssit.getInstance().resTmp.get(src)), null);
				if(drawable!=null) {
					ImageUrlAssit.getInstance().imageCache.put(src, drawable);
				}
			} catch (Exception e) {
				Log.e("e1",e.getLocalizedMessage());
				//return null;
			}
			Log.e("d0",""+(drawable==null));
			if(drawable==null) {
				drawable = Drawable.createFromPath(src);
				if(drawable!=null) {
					ImageUrlAssit.getInstance().imageCache.put(src, drawable);
				}
				Log.e("d1",""+(drawable==null));
			}

			try {
				URL u = new URL(src);
				URLConnection con = u.openConnection();
				con.setConnectTimeout(5000);
				drawable = Drawable.createFromStream(con.getInputStream(), null);
				
				if(drawable!=null) {
					ImageUrlAssit.getInstance().imageCache.put(src, drawable);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Log.e("d2",""+(drawable==null));
			if(drawable==null)
				Log.e("获取图片失败！",src);
			
			//if(drawable!=null)
			//	drawable.setBounds(0, 0,drawable.getIntrinsicWidth(), (int) (drawable.getIntrinsicHeight()));
			//if(drawable!=null)
				//drawable.setBounds(0, 0,100, (int) (100.f/ drawable.getIntrinsicWidth()*drawable.getIntrinsicHeight()));
			return drawable;
        }
    }
}