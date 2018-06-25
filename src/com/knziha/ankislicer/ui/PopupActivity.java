package com.knziha.ankislicer.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.neojson.JSONArray;
import org.neojson.JSONException;
import org.neojson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.litepal.crud.DataSupport;

import com.bung87.customshare.AppInfo;
import com.bung87.customshare.ShareCustomAdapter;
import com.bung87.customshare.ShareCustomAdapter.OnItemClickListenermy;
import com.fenwjian.sdcardutil.ColoredUnderlineSpan;
import com.fenwjian.sdcardutil.CoordCpr;
import com.fenwjian.sdcardutil.LinearLayoutmy;
import com.fenwjian.sdcardutil.RBTNode;
import com.fenwjian.sdcardutil.RBTree;
import com.fenwjian.sdcardutil.RashMap;
import com.fenwjian.sdcardutil.TextViewmy;
import com.fenwjian.sdcardutil.TextViewmy.onSelectionChangedListener;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.gson.JsonObject;
import com.fenwjian.sdcardutil.myCpr;
import com.ichi2.anki.api.NoteInfo;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.knziha.ankislicer.R;
import com.knziha.ankislicer.customviews.RelativeLayoutmy;
import com.knziha.ankislicer.customviews.ShelfLinearLayout;
import com.knziha.plod.dictionary.mdict;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.data.dict.Definition;
import com.mmjang.ankihelper.data.dict.DictionaryRegister;
import com.mmjang.ankihelper.data.dict.IDictionary;
import com.mmjang.ankihelper.data.model.UserTag;
import com.mmjang.ankihelper.domain.CBWatcherService;
import com.mmjang.ankihelper.domain.PlayAudioManager;
import com.mmjang.ankihelper.domain.PronounceManager;
import com.mmjang.ankihelper.plan.MyLitepalGson;
import com.mmjang.ankihelper.plan.OutputPlan;
import com.mmjang.ankihelper.plan.VocabularyCardModel;
import com.mmjang.ankihelper.plan.ui.dict_manager_activity;
import com.mmjang.ankihelper.programData.Settings;
import com.mmjang.ankihelper.util.Constant;
import com.mmjang.ankihelper.util.TextSplitter;
import com.mmjang.ankihelper.util.Utils;
import com.mmjang.ankihelper.util.ViewUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import momo.htmlutil.ImageUrlAssit;
import momo.htmlutil.URLImageParser;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class PopupActivity extends AppCompatActivity implements 
				View.OnClickListener,
				RelativeLayoutmy.OnResizeListener{
	TextViewmy tv;
	ScrollViewmy s;
    Timer timer = new Timer();
    Timer timer3 = new Timer();
    private boolean selPara=true;
    SpannableStringBuilder baseSpan;
    boolean isDragging=false;
    boolean ignoreNextTvUp=false;
	int pselStart,	pselEnd;
	private final int Default_InvalidationTime=800;
	//main_list_Adapter AdapterMyDefs;
	List<Definition> Data;
	private ViewGroup fmain;
	private ViewGroup bottom_wrap;
	ViewGroup coreSibling;
	ViewGroup actionMenu;
	ViewGroup dialogHolder;
	RelativeLayout main;
	
    //List<IDictionary> dictionaryList;
    mdict currentDictionary;
    ListViewAdapter adaptermy;
    String[] mdList;
    List<OutputPlan> outputPlanList;
    List<String> languageList;
    OutputPlan currentOutputPlan;
    Settings settings;
    SpannableStringBuilder mTextToProcess=new SpannableStringBuilder();
    String mPlanNameFromIntent;
    String mCurrentKeyWord;
    TextSplitter mTextSplitter;
    String mNoteEditedByUser = "";
    HashSet<String> mTagEditedByUser = new HashSet<>();
    //posiblle pre set target word
    String mTargetWord;
    //possible url from dedicated borwser
    String mUrl = "";
    //possible specific note id to update
    Long mUpdateNoteId = 0L;
    //update action   replace/append    append is the default action, to prevent data loss;
    String mUpdateAction;
    //possible bookmark id from fbreader
    String mFbReaderBookmarkId;
    //views
    int idx2=-1;
    DropDownDirControllable_AutoCompleteTextView act;
    
    Button btnPronounce;
    ImageButton highlightBtn;
    View colorPicker;
    Spinner pronounceLanguageSpinner;
    
    ListView LinearLayout;
    Button btnSearch;
    ProgressBar progressBar;
    Drawable drawable;
    
    View middle_expandit;
    View middle_expandable;
    
    ClickableSpan tvClicker;
    //					a-b的key				对应某些词条索引
    RashMap<CoordCpr<Integer,Integer>,HashSet<CoordCpr<Integer,Integer>> > keysPool = new RashMap<CoordCpr<Integer,Integer>,HashSet<CoordCpr<Integer,Integer>> >();//无需排序
    //		词条索引：词典idx的第pos个词条   		              的view
    RBTree<myCpr<CoordCpr<Integer,Integer> , LinearLayout  >> recordsPool = new RBTree<myCpr<CoordCpr<Integer,Integer>,LinearLayout >>();//干脆把整个内容view保存下来
    
    HashMap<String,Long> vocaburary_recorder = new HashMap<String,Long>();
    
    public ClickableSpan currentSpanOnFocus;
    
    
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if(dialogHolder.getVisibility()==View.VISIBLE)
				onPlanDismiss();
			else if(sharePopup!=null && sharePopup.isShowing())
				sharePopup.dismiss();
			else if(act.isPopupShowing()) {
				act.dismissDropDown();
			}else if(tv.hasSelection()) {
				if(Build.VERSION.SDK_INT>=24)
					tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
				else
					tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
	           	 tv.hideTvSelecionHandle();
			}else
				return super.onKeyDown(keyCode, event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    //plan b
    //LinearLayout viewDefinitionList;
	class tk_gross extends TimerTask{
		int msg = 0;
		tk_gross(int message){super();msg = message;}
		@Override
		public void run() {
			mHandler.sendEmptyMessage(msg);
		}
	};
    //async event
    //private static final int PROCESS_DEFINITION_LIST = 1;
    private static final int ASYNC_SEARCH_FAILED = 2;
    //async
    Handlermy mHandler_;
	protected boolean tvUp_detected;
    
    void refresh_processDef_handler(){
	    	mHandler_=new Handlermy() {
	            @Override
	            public void handleMessage(Message msg) {
	            	if(!valid) return;
					showSearchButton();
					if(recyclerViewDefinitionList_unTouchable.get())
						return;
                    if(idx2!=-1){
                        act.showDropDown(false);
                        act.setListSelection(idx2-1);
                        idx2=-1;
                        tvUp_detected=false;
                    }else {
                    	notifyDataSetChanged();
                    }
					if(msg.obj!=null) {
						Toast.makeText(PopupActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
					}
	            }
	    	};
    }
    
    Handler mHandler = new Handler() {
    	
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
	            case 11:
	                //refreshPersist();
	            	String tmpstr = baseSpan.subSequence(pselStart,pselEnd)+"";
	            	if(tmpstr.replace("\r", "").replace("\n", "").replace(" ", "").equals(""))
	            		break;
	            	act.removeTextChangedListener(mTW);
	                act.setText(tmpstr);
	            	act.addTextChangedListener(mTW);
	                asyncSearch(tmpstr);
                break;
                case ASYNC_SEARCH_FAILED:
                    showSearchButton();
                    Toast.makeText(PopupActivity.this, (String) msg.obj+"ghjk", Toast.LENGTH_LONG).show();
                    break;
                case 101:
                	//if(true) break;
                    Log.e("fmain","handleMessage 1 hasSelection:"+tv.hasSelection());
                    if((baseSpan.subSequence(tv.getSelectionStart(),tv.getSelectionEnd())+"").replace("\r", "").replace("\n", "").replace(" ", "").equals("")){
                    	//撤销空选择
                    	if(tv.hasSelection()){
        	            	if (Build.VERSION.SDK_INT>=24){
    	                         //tv.hideTvSelecionHandle();
    	                         //tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK));
    	                         //tv.hideTvSelecionHandle();
    	                         //tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_BACK));
    	                          //CMN.a.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK));
    	                
    	                         tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
    	                         //tv.clearFocus();
    	                         //tv.hideTvSelecionHandle();
    	                         //tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
    	                             
    	                             //tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
    	                         //isNeedNextUp = false;
    	                         //tv.setSelected(false);
    	                         //tv.requestFocus();
    	                     }else {
    	                         tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
    	                    	 //tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK));
    	                    	 tv.hideTvSelecionHandle();
    	                         //mPopupMenu.dismiss();
    	                     }   
    	                    
    	                }
                    }
                    Log.e("fmain","keyis:"+(baseSpan.subSequence(tv.getSelectionStart(),tv.getSelectionEnd())+""));
                    Log.e("fmain","keyis=kong "+(baseSpan.subSequence(tv.getSelectionStart(),tv.getSelectionEnd())+"").replace("\r", "").replace("\n", "").replace(" ", "").equals(""));
                    //CMN.a.toolbarToStateSelected();
                break;
                case 404:
                    //this is mHandler.sendEmptyMessage(4)
                    //tv.performLongClick();
                    //tv.performClick();
                    tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
                    //refreshTextView();
                    //tv.clearFocus();

                    //tv.cancelLongPress();
                    //svp.ShowTvSelecionHandle();
                break;
                case 1996:
                	tv.ShowTvSelecionHandle();
            	break;
                default:
                    break;
            }
        }
    };

    HashMap<Integer,Definition> result_Cache = new HashMap<Integer,Definition>();
	LinearLayoutmy recyclerViewDefinitionList;
	private AtomicBoolean recyclerViewDefinitionList_unTouchable;
	ScrollView DefinitionListS;
	protected boolean tvScrolled;
	protected boolean selecChangedWhenD;
	protected long lastOnPlanDismiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	CMN.a=this;
    	if(false)
        denv = new dictionary_environment(new String[] {"<div class=\"cont-list-pos sense-pos\">","<div class=\"cont-pos_subsense\""},
                                            new String[] {"<div class=\"labelbtn\">","<div class=\"cont-addition main-addition"},
                                            new String[] {"<dl class=\"abd1\">","<dl class=\"abc1\">","<dl class=\"ccc1\">"},
                                            new String[] {"<div class=\"cont-exam\">"});
        denv = new dictionary_environment(  new String[] {},
                                            new String[] {},
                                            new String[] {},
                                            new String[] {});
    	super.onCreate(savedInstanceState);
    	
        //hide the title bar  
    	if(Build.VERSION.SDK_INT>22) {
    		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  
    	}
    	//requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //setStatusBarColor();
        setContentView(R.layout.activity_popup2);
        recyclerViewDefinitionList_unTouchable = new AtomicBoolean(false);
        //set animation
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        main = (RelativeLayout) findViewById(R.id.scrollView);
        //OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        //
        fmain = (ViewGroup) findViewById(R.id.floatMain);
        coreSibling = (ViewGroup) findViewById(R.id.core);
        actionMenu = (ViewGroup) findViewById(R.id.actionMenu);
        bottom_wrap = (ViewGroup) findViewById(R.id.bottom_wrap);
        dialogHolder = (ViewGroup) findViewById(R.id.dialogHolder);    
        act = (DropDownDirControllable_AutoCompleteTextView) findViewById(R.id.edit_text_hwd);
        
        
        btnSearch = (Button) findViewById(R.id.btn_search);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		
        btnPronounce = ((Button) findViewById(R.id.btn_pronounce));
        ImageButton ChooseMode_Btn = (ImageButton) findViewById(R.id.plan_spinner_btn);
        ImageButton language_spinner_Btn = (ImageButton) findViewById(R.id.language_spinner_btn);
        pronounceLanguageSpinner = (Spinner) findViewById(R.id.language_spinner);

        DefinitionListS = (ScrollView) findViewById(R.id.DefinitionListS);
        DefinitionListS.setVerticalScrollBarEnabled(false);
        DefinitionListS.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(act.isPopupShowing()) {
    				act.dismissDropDown();
    			}
				return false;
			}
        });
        //viewDefinitionList = (LinearLayout) findViewById(R.id.view_definition_list);
        highlightBtn = (ImageButton) findViewById(R.id.favorite);
        colorPicker =  findViewById(R.id.colorPicker);
        
		
        
        fmain.setOnTouchListener(mMoveListener);
        findViewById(R.id.btn_move_pad).setOnTouchListener(mMoveListener2);
        findViewById(R.id.move0).getBackground().setColorFilter(new PorterDuffColorFilter(Color.parseColor("#0000ff"), PorterDuff.Mode.MULTIPLY));
        findViewById(R.id.btn_move).setOnTouchListener(mMoveListener2);
		
        
        findViewById(R.id.actionMenu_close).setOnClickListener(this);
        findViewById(R.id.mBtnNewCard).setOnClickListener(this);
        findViewById(R.id.actionMenu1).setOnClickListener(this);
        findViewById(R.id.actionMenu2).setOnClickListener(this);
        findViewById(R.id.actionMenu_share).setOnClickListener(this);
        findViewById(R.id.mBtnMainNote).setOnClickListener(this);
        tv = (TextViewmy)findViewById(R.id.webview);
        s = (ScrollViewmy)findViewById(R.id.sv);
        baseSpan = new SpannableStringBuilder(tv.getText());//+"\r\n\r\n\r\n");
        CMN.sdkVersionCode=Build.VERSION.SDK_INT;
		//tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,scale(this,81));

        findViewById(R.id.mt1).setOnClickListener(toggleOnclick);
        findViewById(R.id.mt2).setOnClickListener(toggleOnclick);
        middle_expandit = findViewById(R.id.middle_expandit);
        middle_expandable = findViewById(R.id.middle_expandable);
        middle_expandit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(auto_unfold_exp)
					((ToggleButton)middle_expandable.findViewById(R.id.mt1)).setChecked(true);
				if(un_review_mainnote) 
					((ToggleButton)middle_expandable.findViewById(R.id.mt2)).setChecked(true);
				
				if(middle_expandable.getVisibility()!=View.VISIBLE)
					middle_expandable.setVisibility(View.VISIBLE);
				else
					middle_expandable.setVisibility(View.GONE);
			}});
        s.setScrollViewListener(new ScrollViewmy.ScrollViewListener(){

			private int lastY = 0;
			private int touchEventId = -9983761;
        	Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						View scroller = (View) msg.obj;

						if (msg.what == touchEventId) {
							if (lastY == scroller.getScrollY()) {
								//停止了，此处你的操作业务
								///
	      						/*if(tv.hasSelection()&&tv.sHided) {
	      							tv.ShowTvSelecionHandle();
	    	                        tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
	      						}*/
								//if(Math.abs(scroller.getScrollY()-lastY)<5) {

							} else {
								handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 1);
								lastY = scroller.getScrollY();
							}
						}
					}
				};
        	@Override
			public void onScrollChanged(View v, int scrollX, final int scrollY,
									   int oldScrollX, int oldScrollY) {
        		
				//handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
        		//s.requestFocus();
        		//tv.clearFocus();
        		//CMN.show("onScrollChanged");
        		tvScrolled=true;
			}});
			
        s.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent mv) {
				switch (mv.getAction()){
					case MotionEvent.ACTION_UP:
						isDragging=false;
						if(tvClicker!=null && !tvScrolled) {
							
  						}else if(tv.hasSelection()&&tv.sHided) {
  							tv.ShowTvSelecionHandle();
	                        tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
  						}
						break;
					case MotionEvent.ACTION_DOWN://滑动
						isDragging=true;
						//if(tv.hasSelection()&&!tv.sHided) tv.hideTvSelecionHandle();
						break;
				}
				return false;
			}
		});
        act.addTextChangedListener(mTW);
        tv.setOnSelectionChangedListener(new onSelectionChangedListener(){
			@Override
			public void onSelectionChanged(int selStart, int selEnd) {
				if(tv.hasSelection())
					actionMenu.setVisibility(View.VISIBLE);
				else
					actionMenu.setVisibility(View.INVISIBLE);
				//CMN.show("onSelectionChanged"+selStart+":"+selEnd);
				if(selStart==selEnd){
					selPara = true;
					return;
				}
				selPara = false;
				if(selStart==tv.selStart && selEnd==tv.selEnd)
					return;
				else if(selEnd==tv.selStart && selStart==tv.selEnd)
					return;
				//if(!isDragging)???
				if(isDragging)
					selecChangedWhenD=true;
				if(true){
					if(selStart>selEnd){
						int tmp = selEnd;
						selEnd = selStart;
						selStart = tmp;
					}
					//showToast(selStart+":"+selEnd);
					//eshowToast(tv.selStart+":2:"+tv.selEnd);
					pselStart = selStart;
					pselEnd = selEnd;
					timer3.cancel();
					timer3 = new Timer();
		            timer3.schedule(new tk_gross(11), 142);///检测选择2
				}
			}
        });
      //![1] 获取x,y位置
      		tv.setOnTouchListener(new View.OnTouchListener() {
      			
      			@Override
      			public boolean onTouch(View view, MotionEvent mv) {
      				switch (mv.getAction()){
      					case MotionEvent.ACTION_UP:
      						tvUp_detected=true;
      						//CMN.show("ACTION_UP");
      						///
      						isDragging=false;
      						
      						if(tvClicker!=null && !tvScrolled) {
      							if(Build.VERSION.SDK_INT<24 && tv.hasSelection()) {
      				        		tv.post(new Runnable() {
      									@Override
      									public void run() {
      										tv.hideTvSelecionHandle();
      									}});
      				        	}
      							tvClicker.onClick(view);
      						}else if(tv.hasSelection()&&tv.sHided) {
      							tv.ShowTvSelecionHandle();
    	                        tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
      						
    	                        tk_gross tktk = new tk_gross(101);///检测选择
          			            timer.schedule(tktk, 6);
      						}
      						
      						
      						break;
      					case MotionEvent.ACTION_DOWN:
      						selecChangedWhenD=false;
      						tvScrolled=false;
      						tvClicker=null;
      						//splitterrl.setVisibility(View.VISIBLE);
      						isDragging=true;
      						
      						
      						if(CMN.sdkVersionCode<24)
      						if (tv.hasSelection()){
      							//mPopupMenu.show();
      						}
      						
      						float x = tv.xmy = mv.getX();
      		                float y = tv.ymy = mv.getY();
      		  
      		                x -= tv.getTotalPaddingLeft();  
      		                y -= tv.getTotalPaddingTop();  
      		  
      		                x += tv.getScrollX();  
      		                y += tv.getScrollY();  
      		  
      		                Layout layout = tv.getLayout();  
      		                int line = layout.getLineForVertical((int) y);  
      		                int off = layout.getOffsetForHorizontal(line, x);  
      		              
      						ClickableSpan[] link = baseSpan.getSpans(off, off, ClickableSpan.class);  
      		                if (link.length != 0) {  
      		                    tvClicker = link[0];  
      		                } 
      						//if(tv.hasSelection()&&!tv.sHided) tv.hideTvSelecionHandle();
      						break;

      				}
      				return false;
      			}
      		});
      		//![2] textview OnClick监听类
      		tv.setOnClickListener(new OnClickListener() {
      			@Override
      			public void onClick(View view) {
      				//TODO : onClick-Animation
      				//if(!isRestoreSelectionExpected)
      				if(tvClicker!=null)
      					return;
      				
  					tv.performLongClick();
  					//CMN.show("onClick");
      				tk_gross tvlong = new tk_gross(404);
      				if(CMN.sdkVersionCode>=24) {
      					//Log.e("sd", "sdf");
      					timer.schedule(tvlong, 50);
      					return;
      				}
      				
      			}
      		});
      		if(CMN.sdkVersionCode<24) {
                tv.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;

                    }

                    public void onDestroyActionMode(ActionMode mode) {
                    }

                    //here
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        tv.ShowTvSelecionHandle();
                        return false;//hide actionMode menu here
                    }

                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                        return true;
                    }
                });

            }else if(true){
                tv.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    	if(true)
                    		menu.clear();//隐藏AM
                    	//menu.setGroupVisible(0, false);
                        return false;
                    }

                    public void onDestroyActionMode(ActionMode mode) {
                    }

                    //here
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    	//MenuInflater menuInflater = actionMode.getMenuInflater();
                        //menuInflater.inflate(R.menu.selection_action_menu,menu);
                    	//int idxtmp=menu.size()-1;
                    	//showToast(menu.getItem(idxtmp).getItemId()+":"+menu.getItem(idxtmp).getOrder()+":"+menu.getItem(idxtmp).getTitle());
    	                	//menu.getItem(0).setActionView(R.layout.action);
    	                	//menuView = (View) menu.getItem(0).getActionView().getParent();
    	                	//menuView.setBackgroundColor(Color.parseColor("#0000ff"));
                		menu.add(0, 910086, 6, "查 词");//actionMode.hide(1000);
                    	//tv.ShowTvSelecionHandle();

                        //menu.setGroupVisible(0, false);
                        //menu.setGroupVisible(0, false);
                        //menu.setGroupVisible(menu.getItem(4).getGroupId(), false);
                        ///actionMode.getCustomView().setVisibility(View.INVISIBLE);
                        return true;///* false true   */返回false则不会显示弹窗

                    }

                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                    	//showToast(""+item.getTitle()+" "+item.getGroupId());
                    	switch(item.getItemId()){
                    		
                    	}	
                        return false;
                    }
                });			
    			
    		}
  		dialogHolder.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				lastOnPlanDismiss = System.currentTimeMillis();
				onPlanDismiss();
				return false;
			}});
        ChooseMode_Btn.setOnClickListener(new OnClickListener() {
        	boolean isFragInitiated=false;
			@Override
			public void onClick(View arg0) {
				if(System.currentTimeMillis()-lastOnPlanDismiss<150)//too fast
					return;
				//planSpinner.performClick();
	    		//getActivity().findViewById()
				if(act.isPopupShowing()) {
					actNeedShowDrop=true;
					act.dismissDropDown();
				}
				CMN.currentOutputPlan = currentOutputPlan;
				dialogHolder.setVisibility(View.VISIBLE);
				if(!isFragInitiated) {
		    		FragmentManager fragmentManager = getSupportFragmentManager();
		            FragmentTransaction transaction = fragmentManager.beginTransaction();
		            ChooseModeFragmt prefFragment = new ChooseModeFragmt();
		            transaction.add(R.id.dialog_, prefFragment);
		            transaction.commit();
		            isFragInitiated=true;
				}
			}});
        language_spinner_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pronounceLanguageSpinner.performClick();
			}});
        language_spinner_Btn.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				finish();
				//System.exit(0); will effect other instances
				return true;
			}});

        loadData(); //dictionaryList;
        loadPlan();//首次加载词典 
        //CMN.deckSelected = currentOutputPlan.getOutputDeckId();
        populateLanguageSpinner();
        setEventListener();
        adaptermy = new ListViewAdapter(currentDictionary);//首次设置词典 
        act.setAdapter(adaptermy);
        scanSettings();
        handleIntent();
        if (settings.getMoniteClipboardQ()) {
            startCBService();
        }


    }

    
    long oldDeck,oldModel;
    protected void onPlanDismiss() {
    	dialogHolder.setVisibility(View.GONE);
    	if(actNeedShowDrop)
    		act.showDropDown(false);
    	if(CMN.currentOutputPlan!=currentOutputPlan) {
    		currentOutputPlan=CMN.currentOutputPlan;
    		currentOutputPlan.saveToDisk();
    		mdList = currentOutputPlan.getMdicts();
    		try {
				initDictAt(mdList[denv.currDictIdx=currentOutputPlan.homeDictIdx]);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		if(CDView_added) {
                coreSibling.getLayoutParams().height-=coreSibling.getChildAt(0).getHeight();
                coreSibling.removeViewAt(0);
                CDView_added=false;
                add_Choose_dict_View();
            }
    	}
		if(currentOutputPlan.getOutputDeckId()!=oldDeck || 
				oldModel!=currentOutputPlan.getOutputModelId()) {
			//drop mainnote history
			mUpdateNoteId=null;
			mMainNoteId=0l;
		}
		CMN.currentOutputPlan = null;		
	}




	@Override
    public void onStart(){
    	overridePendingTransition(R.anim.activity_anim_in_right,0);
    	super.onStart();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    }    
    boolean actNeedShowDrop = false;
	OnTouchListener mMoveListener = new OnTouchListener(){
    	float lastY;
		@Override public boolean onTouch(View v, MotionEvent e) {
			switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:
					lastY = e.getRawY();
					if(!selecChangedWhenD) 
					if(Build.VERSION.SDK_INT>=24 && tv.hasSelection()&&!tv.sHided) {
						tv.hideTvSelecionHandle();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					float dy = e.getRawY() - lastY;
					if(act.isPopupShowing()) {
						actNeedShowDrop=true;
						//act.update(dy);//跟随不能，太卡
						act.dismissDropDown();
					}
					//ViewCompat.setY(handleThumb,e.getRawY());  
					int newTop = (int) (bottom_wrap.getHeight()- dy);
					newTop = Math.max(newTop, 0);
					//main.setTop(newTop);
					ViewGroup.LayoutParams  lpmy = bottom_wrap.getLayoutParams();
					lpmy.height=newTop;
					bottom_wrap.setLayoutParams(lpmy);
					bottom_wrap.postInvalidate();
					lastY = e.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					if(tv.hasSelection()&&tv.sHided) {
						if(!selecChangedWhenD) {
						//CMN.show("ShowTvSelecionHandle");
						tv.ShowTvSelecionHandle();
						if(Build.VERSION.SDK_INT>=24) 
							tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
						}
					}
					if(actNeedShowDrop) {
						act.showDropDown(false);
						actNeedShowDrop=false;
					}
					break;
				default:
					break;
			}
			return true;
	}};
	
	OnTouchListener mMoveListener2 = new OnTouchListener(){
    	float lastY;
		@Override public boolean onTouch(View v, MotionEvent e) {
			switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:
					lastY = e.getRawY();
					if(Build.VERSION.SDK_INT>=24 && tv.hasSelection()&&!tv.sHided) {
						tv.hideTvSelecionHandle();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					float dy = e.getRawY() - lastY;
					//ViewCompat.setY(handleThumb,e.getRawY());  
					int newTop = (int) (coreSibling.getHeight()- dy);
					newTop = Math.max(newTop, 125);
					//main.setTop(newTop);
					ViewGroup.LayoutParams  lpmy = coreSibling.getLayoutParams();
					lpmy.height=newTop;
					coreSibling.setLayoutParams(lpmy);
					coreSibling.postInvalidate();
					lastY = e.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					if(tv.hasSelection()&&tv.sHided) {
						//CMN.show("ShowTvSelecionHandle");
						tv.ShowTvSelecionHandle();
						if(Build.VERSION.SDK_INT>=24) 
							tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));

					}
					break;
				default:
					break;
			}
			return true;
	}};
	protected int BGColor = Color.CYAN;
	

    private void setStatusBarColor() {
        int statusBarColor = 0;
        if (Build.VERSION.SDK_INT >= 21) {
            statusBarColor = getWindow().getStatusBarColor();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(statusBarColor);
        }
    }

    private void assignViews() {

    }

    private void loadData() {
        //dictionaryList = DictionaryRegister.getDictionaryObjectList();
        outputPlanList = DataSupport.findAll(OutputPlan.class);


        settings = Settings.getInstance(this);
        //新日漢大辭典.mdx
			//mdTest = new mdict("/sdcard/mdictlib/ode2_raw.mdx",false);//ODE2双解(from3in1).mdx
		
        //load tag
        boolean loadQ = settings.getSetAsDefaultTag();
        if (loadQ) {
            mTagEditedByUser.add(settings.getDefaulTag());
        }
        //check if outputPlanList is empty
        if(outputPlanList.size() == 0){
            Toast.makeText(this, R.string.toast_no_available_plan, Toast.LENGTH_LONG).show();
        }
    }

    private void loadPlan() {
        String[] planNameArr = new String[outputPlanList.size()];
        for (int i = 0; i < outputPlanList.size(); i++) {
            planNameArr[i] = outputPlanList.get(i).getPlanName();
        }

        currentOutputPlan = OutputPlan.resumeFromDisk();
        
        if (currentOutputPlan==null)
        {//初始化
        	//CMN.show("没有的啦");
            if (outputPlanList.size() > 0) {
            	int lastSelectedPlan = settings.getLastSelectedPlanPos();
            	//CMN.show(""+lastSelectedPlan);
            	if(lastSelectedPlan!=-1 && lastSelectedPlan<outputPlanList.size() 
            			&& outputPlanList.get(lastSelectedPlan).getPlanName().equals(settings.getLastSelectedPlanName())) 
            	{//根据存储的pos从数据库恢复
            		currentOutputPlan = outputPlanList.get(lastSelectedPlan);
            	}else {//初始化，默认选中第一个plan
	                settings.setLastSelectedPlanName(outputPlanList.get(0).getPlanName());
	                settings.setLastSelectedPlanPos(0);
	                currentOutputPlan = outputPlanList.get(0);
            	}
                currentOutputPlan.saveToDisk();
            } 
        }else {
        	//CMN.show("有的啦");
        }

        if(currentOutputPlan==null) {
            Toast.makeText(PopupActivity.this, "方案未配置，无法启动浮窗", Toast.LENGTH_LONG).show();
			finish();
			System.exit(0);
        }
        
        mdList = currentOutputPlan.getMdicts();
        try {
        	initDictAt(mdList[denv.currDictIdx=currentOutputPlan.homeDictIdx]);
        } catch (IOException e) {
			e.printStackTrace();
		}
        
    }

    private void populateLanguageSpinner() {
    	
        String[] languages = PronounceManager.getAvailablePronounceLanguage();
        ArrayAdapter<String> languagesSpinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, languages);
        languagesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pronounceLanguageSpinner.setAdapter(languagesSpinnerAdapter);
        pronounceLanguageSpinner.setSelection(settings.getLastPronounceLanguage());

    }

    private void setEventListener() {

        //auto finish
        pronounceLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            	//CMN.show("onItemSelected");
            	if(position<5)
                	settings.setLastPronounceLanguage(position);
                else if(position==5) {
                	PackageManager manager = PopupActivity.this.getPackageManager();
                    //Intent i = manager.getLaunchIntentForPackage("com.ichi2.anki");
                	//Intent i=new Intent(Intent.ACTION_MAIN);
                	Intent i=new Intent(Intent.ACTION_VIEW);//EXTRA_PROCESS_TEXT ACTION_VIEW
                	ComponentName cn=new ComponentName("com.ichi2.anki",
                	        //"com.ichi2.anki.IntentHandler");
                	"com.ichi2.anki.CardBrowser");/*Reviewer CardBrowser   */
                	i.setComponent(cn);
                    i.putExtra("EXTRA_DECK_ID", currentOutputPlan.getOutputDeckId());
                    i.putExtra("defaultDeckId", currentOutputPlan.getOutputDeckId());
                    i.putExtra("selectedDeck", currentOutputPlan.getOutputDeckId());
                    
                    i.putExtra("deckId", currentOutputPlan.getOutputDeckId());//Reviewer Intent.ACTION_VIEW

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    try {
                    	PopupActivity.this.startActivity(i);
                    }catch(Exception e) {
                        Toast.makeText(PopupActivity.this, "Anki启动失败：未安装Anki或指派的启动Activity不可访问\n"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                    Class<?> myClass = AdapterView.class;
                    if(false)
                    try {
                    	
                        Field field = myClass.getDeclaredField("mOldSelectedPosition");
                        field.setAccessible(true);
                        //field.setInt(pronounceLanguageSpinner,settings.getLastPronounceLanguage());
                        field.setInt(pronounceLanguageSpinner,settings.getLastPronounceLanguage());//奇怪的是两个spinner竟然会连觉
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String word = act.getText().toString();
                        if (!word.isEmpty()) {
                            asyncSearch(word);
                            Utils.hideSoftKeyboard(PopupActivity.this);
                        }
                    }
                }
        );

        btnPronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String word = act.getText().toString();
                PlayAudioManager.playPronounceVoice(PopupActivity.this, word);
            }
        });

        highlightBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //setupEditNoteDialog();
                        //setupEditNoteDialog();
                        if(tv.hasSelection()){
                        	boolean isSpanFound = false;
        					int st = tv.getSelectionStart();
        					int ed = tv.getSelectionEnd();
        					BackgroundColorSpan[] spans = baseSpan.getSpans(st, ed, BackgroundColorSpan.class);
        					for(BackgroundColorSpan span:spans) {
        						if(baseSpan.getSpanStart(span)==st && baseSpan.getSpanEnd(span)==ed) {
        							baseSpan.removeSpan(span);
        							if(Build.VERSION.SDK_INT>=24)
        								tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
        							else
        								tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
        							tv.hideTvSelecionHandle();
    	    						tv.clearFocus();
        							tv.setTextKeepState(baseSpan);
        							isSpanFound=true;
        							break;
        						}
        					}
        					if(!isSpanFound) {
	    						baseSpan.setSpan(new BackgroundColorSpan(BGColor ),st,ed, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    							if(Build.VERSION.SDK_INT>=24)
    								tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
    							else
    								tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
	    						tv.hideTvSelecionHandle();
	    						tv.clearFocus();
	    						tv.setTextKeepState(baseSpan);
        					}
    						
                        }
                    
                    }
                }
        );

        highlightBtn.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //setupEditNoteDialog();
                        if(tv.hasSelection()){
                        	boolean isSpanFound = false;
        					int st = tv.getSelectionStart();
        					int ed = tv.getSelectionEnd();
        					ColoredUnderlineSpan[] spans = baseSpan.getSpans(st, ed, ColoredUnderlineSpan.class);
        					for(ColoredUnderlineSpan span:spans) {
        						if(baseSpan.getSpanStart(span)==st && baseSpan.getSpanEnd(span)==ed) {
        							baseSpan.removeSpan(span);
        							if(Build.VERSION.SDK_INT>=24)
        								tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
        							else
        								tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
        							tv.hideTvSelecionHandle();
    	    						tv.clearFocus();
        							tv.setTextKeepState(baseSpan);
        							isSpanFound=true;
        							break;
        						}
        					}
        					if(!isSpanFound) {
	    						baseSpan.setSpan(new ColoredUnderlineSpan(Color.parseColor("#ff0000"),8.f), st,ed,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    						tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
	    						tv.hideTvSelecionHandle();
	    						tv.clearFocus();
	    						tv.setTextKeepState(baseSpan);
        					}
    						
                        }
						return true;
                    }
                }
        );

        colorPicker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ColorPickerDialog asd = 
		    	ColorPickerDialog.newBuilder()
		            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
		            .setAllowPresets(true)
		            .setDialogId(123123)
		            .setShowAlphaSlider(false)
		            .create(); 
		    	asd.setColorPickerDialogListener(new ColorPickerDialogListener() {
					@Override
					public void onColorSelected(int dialogId, int Color) {
						BGColor=Color;
					}

					@Override
					public void onDialogDismissed(int dialogId) {
						
					}});
		    	asd.show(getFragmentManager(),"color-picker-dialog");				
			}
		});
        findViewById(R.id.choose_dict_btn).setOnClickListener(this);
  
    }
    
    OnClickListener chooseDictOnclick = new OnClickListener() {
	@Override
	public void onClick(View v) {//选择词典
		int NewIdx = (int) v.getTag();
		if(denv.currDictIdx!=NewIdx)//切换词典
		try {
			initDictAt((String) v.getTag(R.id.path));
			adaptermy.setDict(currentDictionary);
			String word = act.getText().toString();
			if(!word.equals("")) {
				asyncSearch(word);
			}
			((ShelfLinearLayout) v.getParent()).setRbyView(v);
    		//((TextView)lv.getChildAt(denv.currDictIdx).findViewById(R.id.text1)).setTextColor(Color.parseColor("#000000"));
    		//title.setTextColor(Color.parseColor("#ff0000"));
    		denv.currDictIdx=NewIdx;
			//currentOutputPlan.homeDictIdx
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(PopupActivity.this, "Dict Loading Error: "+v.getTag(R.id.path)+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
		}
	}};
    boolean CDView_added=false;
    private void add_Choose_dict_View() {
    	if(coreSibling.getChildCount()==3) {
    		coreSibling.removeViewAt(0);
    	}else {
        	final ViewGroup l = (ViewGroup) getLayoutInflater().inflate(R.layout.choose_dict_layout, null);
        	l.setVisibility(View.INVISIBLE);
        	final ShelfLinearLayout lv = (ShelfLinearLayout) l.findViewById(R.id.lv);
        	lv.drawRectOver=true;
        	lv.getPaint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        	int dictCount=0;
        	boolean doNotPopulate = (mdList.length==1) && (mdList[0].equals(""));
        	if(!doNotPopulate)
        	for(String dictI:mdList) {
        		final String path = dictI;
	        	ViewGroup ViewTmp = (ViewGroup) getLayoutInflater().inflate(R.layout.list_item_dicts, null);
	        	final TextView title = ((TextView)ViewTmp.findViewById(R.id.text1));
    			title.setText(new File(dictI).getName());
	        	if(dictCount==denv.currDictIdx) {
	        		final int dictCountF = dictCount;
	        		lv.post(new Runnable() {
						@Override
						public void run() {
							lv.setRbyPos(dictCountF);
						}});
	        	}
	        	ViewTmp.setTag(dictCount++);
	        	ViewTmp.setTag(R.id.path,path);
	        	lv.addView(ViewTmp);
	        	ViewTmp.setOnClickListener(chooseDictOnclick);
        	}
        	ViewGroup AddDictViewTmp = (ViewGroup) getLayoutInflater().inflate(R.layout.list_item_dicts, null);
        	((ImageView)AddDictViewTmp.findViewById(R.id.cover)).setImageResource(R.drawable.ic_add);
        	((TextView)AddDictViewTmp.findViewById(R.id.text1)).setVisibility(View.GONE);
        	AddDictViewTmp.setLayoutParams(new LayoutParams(-2,-1));
        	lv.addView(AddDictViewTmp);
        	AddDictViewTmp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {//添加词典
					DialogProperties properties = new DialogProperties();
	                properties.selection_mode = DialogConfigs.MULTI_MODE;
	                properties.selection_type = DialogConfigs.FILE_SELECT;
	                properties.root = new File(DialogConfigs.DEFAULT_DIR);
	                properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
	                properties.offset = new File(
	                		Settings.getInstance(PopupActivity.this).getString("lastMdlibPath","/sdcard/mdictlib")
	                		);
	                properties.opt_dir=new File(getExternalFilesDir(null).getAbsolutePath()+"/favorites/");
	                properties.opt_dir.mkdirs();
	                properties.extensions = new String[] {"mdx"};
					FilePickerDialog dialog = new FilePickerDialog(PopupActivity.this, properties);
		                dialog.setTitle("添加词典");
		                dialog.setDialogSelectionListener(new DialogSelectionListener() {
		                    @Override
		                    public void
		                    onSelectedFilePaths(String[] files) { //files is the array of the paths of files selected by the Application User. 
		                    	if(files.length>0) {
		                    		Settings.getInstance(PopupActivity.this).putString("lastMdlibPath",new File(files[0]).getParent());
		                        	ArrayList<String> mdArray =  new ArrayList<>(Arrays.asList(mdList));
			                    	for(String fi:files){
			                        	if(!mdArray.contains(fi))
			                        		mdArray.add(fi);
			                        }
			                    	currentOutputPlan.setMdicts(mdArray);
			                    	currentOutputPlan.saveToDisk();
			                    	//currentOutputPlan.save();
			                    	mdList = mdArray.toArray(new String[]{});
			                    	if(CDView_added) {
			                            coreSibling.getLayoutParams().height-=coreSibling.getChildAt(0).getHeight();
			                            coreSibling.removeViewAt(0);
			                            CDView_added=false;
			                            add_Choose_dict_View();
			                        }
		                        }
		                    }
		                });
		                dialog.show();
				}
			});
        	coreSibling.addView(l,0);
          	LayoutParams lp = coreSibling.getLayoutParams();
          	lp.height += ViewUtil.dp2px(70); //height is not ready
          	coreSibling.setLayoutParams(lp);
        	CDView_added = true;
        	l.post(new Runnable() {
	             @Override
	             public void run() {
	                  	LayoutParams lp = coreSibling.getLayoutParams();
	                  	lp.height += l.getHeight()-ViewUtil.dp2px(70); //height is ready
	                  	coreSibling.setLayoutParams(lp);
	                  	l.setVisibility(View.VISIBLE);
		              }
	         });
    	}
    	
    }
    
    protected void initDictAt(String path) throws IOException {
    	long start = System.currentTimeMillis();
    	if(!new File(path).exists()) {
    		currentDictionary=null;
    		return;
    	}
    	currentDictionary = new mdict(path,false);
    	Log.e("time init dict:",(System.currentTimeMillis()-start)+"");
    	start = System.currentTimeMillis();
    	String dictName = new File(path).getName().substring(0,new File(path).getName().lastIndexOf("."));
    	
    	File JsonDescriber = new File("/sdcard/mdictlib/dicts.json");
    	if(JsonDescriber.exists()) {
	    	FileInputStream FIN = new FileInputStream(JsonDescriber);
	        byte[] buff = new byte[(int) JsonDescriber.length()];
	        FIN.read(buff);
	        FIN.close();
	        String jsonRaw = new String(buff,"utf-8");
	    	/*InputStream is = new FileInputStream("/sdcard/mdictlib/dicts.json");
	        InputStreamReader streamReader = new InputStreamReader(is);
	        BufferedReader reader = new BufferedReader(streamReader);
	        String line;
	        StringBuilder stringBuilder = new StringBuilder();
	        try {
	          while ((line = reader.readLine()) != null) {
	            // stringBuilder.append(line);
	            stringBuilder.append(line);
	          }
	          reader.close();
	          reader.close();
	          is.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	        String jsonRaw = stringBuilder.toString();*/
	        //Log.e("time read:",(System.currentTimeMillis()-start)+"");
	        //start = System.currentTimeMillis();
	    	try {
	            JSONObject all = new JSONObject(jsonRaw);
	            //CMN.show(infArray.length()+" "+infArray.toString());
	            String[] sep1 = new String[] {},sep2= new String[] {},sep3= new String[] {},sep4= new String[] {};
	            if(all.has(dictName)){
	                JSONObject dict_describer = all.getJSONObject(dictName);
	                if(dict_describer.has("sep1")){
	                    JSONArray sepReader = dict_describer.getJSONArray("sep1");
	                    sep1 = sepReader.toList().toArray(sep1);
	                }
	                if(dict_describer.has("sep2")){
	                    JSONArray sepReader = dict_describer.optJSONArray("sep2");
	                    sep2 = sepReader.toList().toArray(sep2);
	                }
	                if(dict_describer.has("sep3")){
	                    JSONArray sepReader = dict_describer.getJSONArray("sep3");
	                    sep3 = sepReader.toList().toArray(sep3);
	                }
	                if(dict_describer.has("sep4")){
	                    JSONArray sepReader = dict_describer.getJSONArray("sep4");
	                    sep4 = sepReader.toList().toArray(sep4);
	                }
	                denv.setSeparators(sep1, sep2, sep3, sep4);
	            }
	          } catch (JSONException e) {
	            e.printStackTrace();
	          }
    	}
	}
    
	SpannableStringBuilder[] top_new_card_Span=new SpannableStringBuilder[] {new SpannableStringBuilder(""),new SpannableStringBuilder("")};
    int top_new_card_OldSelected=0;
    boolean New_Card_added=false;
    int new_card_height=0;
    protected void add_New_Card_View() {
    	top_new_card_OldSelected=0;
    	if(coreSibling.getChildCount()==3) {
    		coreSibling.removeViewAt(0);
    	}
    	final HeghtFetchableView l = (HeghtFetchableView) this.getLayoutInflater().inflate(R.layout.new_card_interface, null);
    	coreSibling.addView(l,0);
		New_Card_added=true;
    	coreSibling.getLayoutParams().height+=ViewUtil.dp2px(new_card_height=(int) l.Height);
    	LayoutParams lp = l.getLayoutParams();
    	lp.height=ViewUtil.dp2px(new_card_height=(int) l.Height);
    	lp.width=LayoutParams.MATCH_PARENT;
    	TabLayout tabLayout = (TabLayout) l.findViewById(R.id.tabLayout);
		tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
		tabLayout.addTab(tabLayout.newTab().setText("正面"));
		tabLayout.addTab(tabLayout.newTab().setText("背面"));
		((EditText)l.findViewById(R.id.TNC_et)).setText(top_new_card_Span[0]);
		tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
			@Override
			public void onTabReselected(Tab t) {
			}
			
			@Override
			public void onTabSelected(Tab t) {
				//CMN.show("!!!");
				int pos = t.getPosition();
				EditText top_new_card_et =(EditText)l.findViewById(R.id.TNC_et);
				top_new_card_Span[top_new_card_OldSelected]=(SpannableStringBuilder) top_new_card_et.getText();
				top_new_card_et.setText(top_new_card_Span[pos]);
				top_new_card_OldSelected = pos;
			}
			@Override
			public void onTabUnselected(Tab t) {}
		 });
	}


    private Long mMainNoteId = 0l;
	private String ProjectPath = null;
/*
    HashMap<String,Uri> resTmp = new HashMap<String,Uri>();
    HashMap<String,Drawable> imageCache = new HashMap<String,Drawable>();
	private ImageGetter myHtmlImgDownloader = new Html.ImageGetter() {
		@Override
		public Drawable getDrawable(final String src) {
			drawable = null;
			try {
				//CMN.show(resTmp.get(src).getPath());
				drawable = Drawable.createFromStream(PopupActivity.this.getContentResolver().openInputStream(resTmp.get(src)), null);
				if(drawable!=null) {
					imageCache.put(src, drawable);
				}
			} catch (Exception e) {
				//CMN.show("e1"+e.getLocalizedMessage());
				//return null;
			}
			//CMN.show("d0"+(drawable==null));
			if(drawable==null) {
				drawable = Drawable.createFromPath(src);
				if(drawable!=null) {
					imageCache.put(src, drawable);
				}
				//CMN.show("d1"+(drawable==null));
			}
			if(drawable==null) {
				Thread t=
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							URL u = new URL(src);
							URLConnection con = u.openConnection();
							con.setConnectTimeout(5000);
							drawable = Drawable.createFromStream(con.getInputStream(), null);
							
							if(drawable!=null) {
								imageCache.put(src, drawable);
							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//CMN.show(new URL(src).getPath()+"URL");								
					}
					
				});
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//CMN.show("d2"+(drawable==null));
				if(drawable==null)
					CMN.show("获取图片失败！"+src);
			}
			
			if(drawable!=null)
				drawable.setBounds(0, 0,100, (int) (100.f/ drawable.getIntrinsicWidth()*drawable.getIntrinsicHeight()));
			return drawable;
		}
	};*/
	private void handleIntent() {
		mMainNoteId = 0l;
    	mTextToProcess.clear();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (intent == null) {
            return;
        }
        
        //color dict 接口
        if(action.equals("colordict.intent.action.SEARCH")) {
	        baseSpan = new SpannableStringBuilder(intent.getStringExtra("EXTRA_QUERY"));
	        tv.setText(baseSpan);
	        //todo 自动选择
	        return;
        }
        
        //if (type == null) return;
        //CMN.show(action+":"+type);
        //getStringExtra() may return null
        
        if (Intent.ACTION_SEND.equals(action)) {//分享、剪贴板监控
        	if(type.contains("text/plain")) {
	        	//CMN.show("ACTION_SEND!");
	        	if(intent.hasExtra(Intent.EXTRA_HTML_TEXT)) {
	        		if(Build.VERSION.SDK_INT>=24)
		            	mTextToProcess.append(Html.fromHtml(intent.getStringExtra(Intent.EXTRA_HTML_TEXT), Html.FROM_HTML_MODE_COMPACT, new URLImageParser(tv, this) ,null));
	                else
		            	mTextToProcess.append(Html.fromHtml(intent.getStringExtra(Intent.EXTRA_HTML_TEXT), new URLImageParser(tv, this) ,null));
	        	}
	        	else
	        		mTextToProcess.append(intent.getStringExtra(Intent.EXTRA_TEXT));//.replace("\n", "<br>")
	            if(type!=null && type.contains("image/*")) {//追加图片
	            	Uri uri=intent.getParcelableExtra(Intent.EXTRA_STREAM);
	            	ImageUrlAssit.getInstance().resTmp.put(uri.getPath() , uri);
	            	mTextToProcess.append("<img src=\""+uri.getPath()+"\" />");
	            }
	            //mTargetWord = intent.getStringExtra(Constant.INTENT_ANKIHELPER_TARGET_WORD);
	            //mUrl = intent.getStringExtra(Constant.INTENT_ANKIHELPER_TARGET_URL);
	            //mFbReaderBookmarkId = intent.getStringExtra(Constant.INTENT_ANKIHELPER_FBREADER_BOOKMARK_ID);
	            String noteEditedByUser = intent.getStringExtra(Constant.INTENT_ANKIHELPER_NOTE);
	            if(noteEditedByUser != null){
	                mNoteEditedByUser = noteEditedByUser;
	            }
	            String updateId = intent.getStringExtra(Constant.INTENT_ANKIHELPER_NOTE_ID);
	            mUpdateAction = intent.getStringExtra(Constant.INTENT_ANKIHELPER_UPDATE_ACTION);
	            if(updateId != null && !updateId.isEmpty())
	            {
	                    try{
	                        mUpdateNoteId = Long.parseLong(updateId);
	                    }
	                    catch(Exception e){
	
	                    }
	            }
        	}
        	
        	
        	URLSpan[] spans = mTextToProcess.getSpans(0, mTextToProcess.length(), URLSpan.class);
			for(URLSpan span:spans) {
				mTextToProcess.insert(mTextToProcess.getSpanStart(span), " ");
				mTextToProcess.removeSpan(span);
			}
        	
    	}else if (Intent.ACTION_PROCESS_TEXT.equals(action)) {
    		if(type!=null && type.equals("text/plain")) {
    			//CMN.show(""+intent.getCharArrayExtra(Intent.EXTRA_TEXT));
    			if(Build.VERSION.SDK_INT>=24)
        			//mTextToProcess.append(intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT).replace("\n", "<br>"));
        			mTextToProcess.append(Html.fromHtml(intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT).replace("\n", "<br>"), Html.FROM_HTML_MODE_COMPACT));
    	        else
        			mTextToProcess.append(Html.fromHtml(intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT).replace("\n", "<br>")));
    		}
			//CMN.show(""+Html.toHtml(new SpannableString(intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT))));
    		//CMN.show(""+intent.hasExtra(Intent.EXTRA_HTML_TEXT));
        }else if (Constant.ACTION_OPEN_PROJECT.equals(action)) {
        	try {
	        	ProjectPath  = intent.getStringExtra(Constant.EXTRA_PROJECT_PATH); 
	        	File TODAY = new File(ProjectPath);
	        	byte[] buffer = new byte[(int) TODAY.length()];
				FileInputStream fin = new FileInputStream(TODAY);
				fin.read(buffer);
				fin.close();
				JSONObject all = new JSONObject(new String(buffer));
				if(Build.VERSION.SDK_INT>=24)
	    			mTextToProcess.append(Html.fromHtml(all.getString("html"), Html.FROM_HTML_MODE_COMPACT));
		        else
	    			mTextToProcess.append(Html.fromHtml(all.getString("html")));
				
				String mainkey = all.getString("mainkey");
				if(!mainkey.equals(""))
				for(String i:mainkey.split("\n")) {
					String[] ab = i.split(" ");
					int a = Integer.valueOf(ab[0]);
					int b = Integer.valueOf(ab[1]);
					mTextToProcess.setSpan(new KeyWordSpan(PopupActivity.this), a, b, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				
        	} catch (IOException e) {
        		CMN.show(e.getLocalizedMessage());
				e.printStackTrace();
			}
        	
    			
			//CMN.show(""+Html.toHtml(new SpannableString(intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT))));
    		//CMN.show(""+intent.hasExtra(Intent.EXTRA_HTML_TEXT));
        }
        
        if (mTextToProcess == null) {
            return;
        }
        
        
        //baseSpan.setSpan(new MyClickText2(PopupActivity.this), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        baseSpan=mTextToProcess;
        //tv.setMovementMethod(new LinkMovementMethod()); buggy
        tv.setText(baseSpan);

        if(baseSpan.toString().trim().indexOf(" ")==-1) {
        	tv.setSelection(0,baseSpan.length());//设置自动选择
        	//new Timer().schedule(new tk_gross(1996), 142);
        	//tv.setSelected(true);
        	//tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,0,0, 0));
        }
    	
        //tv.setText(mTextToProcess.toString());	       
        //populateWordSelectBox();
    }



	Thread thread;
    //xxx
    private void asyncSearch(String wordRaw) {
    	//handle some ligature
    	final String word = wordRaw.replace("ﬁ", "fi").replace("ﬂ", "fl");
    	//Log.e("word is:",word+"  "+(word.equals("profile")));
    	idx2=-1;
    	DefinitionListS.removeAllViews();
    	denv.contents.setLength(0);
        if (word.length() == 0) {
			btnPronounce.setVisibility(View.GONE);
            return;
        }
        showProgressBar();
		btnPronounce.setVisibility(View.VISIBLE);
        if(thread!=null && thread.isAlive()) {
			thread.interrupt();
			mHandler_.valid=false;
		}

    	refresh_processDef_handler();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
            	final Handlermy handlet = mHandler_;
            	//List<Definition> ds = new ArrayList<Definition>();
            	Message message = new Message();
            	recyclerViewDefinitionList_unTouchable.set(true);;
                try {
                	LinearLayoutmy DefinitionListTmp=new LinearLayoutmy(PopupActivity.this);
					//又是这个坑 fatal signal 6 (SIGABRT), code -6 什么的GL错误什么的
					//recyclerViewDefinitionList.setLayerType(View.LAYER_TYPE_HARDWARE,null);
					DefinitionListTmp.setOrientation(1);
					int position = 0;
                    Log.d("clicked", "yes");
                    HashMap<String, String> eleMap = new HashMap<>();
        	        int idx = currentDictionary.lookUp(word);
        	        
    	        	while(idx>0) {
						String tmp = currentDictionary.getEntryAt(idx-1);
						if(!tmp.toLowerCase().equals(denv.current_hwd))
							break;
						idx--;
        	        }
    	        	if(idx!=-1)
    	        		DefinitionListTmp.setTag(idx);
    	        	else {
                        idx2 = currentDictionary.lookUp(word,true);
                    }
    	        	denv.currEntryPos=idx;
    	        	
    	        	CoordCpr<Integer, Integer> entryIndexor = new CoordCpr<Integer,Integer>(denv.currDictIdx,idx);
    	        	myCpr<CoordCpr<Integer, Integer>, android.widget.LinearLayout> tmptmp = recordsPool.searchT(new myCpr<CoordCpr<Integer, Integer>, LinearLayout>(entryIndexor,null));
    	        	if(tmptmp!=null)
    	        		DefinitionListTmp=(LinearLayoutmy) tmptmp.value;
    	        	else if(idx!=-1) {
                    	denv.current_hwd = currentDictionary.getEntryAt(idx).toLowerCase();
                    	while(true) {
                    		handleRecordAt(idx,DefinitionListTmp);
                        	//ds.add(new Definition(eleMap,mdTest.getRecordAt(idx)));
							if(idx>=currentDictionary.getNumberEntrys()-1)
								break;
        					String tmp = currentDictionary.getEntryAt(++idx);
        					if(tmp==null)//TODO:: check it
        						break;
        					if(!tmp.toLowerCase().equals(denv.current_hwd))
        						break;
                    	}
                    }
                    recyclerViewDefinitionList = DefinitionListTmp;
                } catch (Exception e) {
                	e.printStackTrace();
                	//不知为何Toast就不会进入finally
                	message.obj = "出错："+e.getLocalizedMessage();
                }finally {
                	if(false && recyclerViewDefinitionList.getChildCount()==0){
                        try{
                        	recyclerViewDefinitionList.addView(fetchCardView(-1,CMN.SearchYouDao().getDisplayHtml(),null));
                        }
                        catch (IOException e){
                        	//message.obj = message.obj+"出错：本地词典未查到，有道词典在线查询失败，请检查网络连接";
                        }finally {
                        	//message.obj = ds;
	                        //message.what = PROCESS_DEFINITION_LIST;
                        	handlet.sendMessage(message);
                        }
                    }else {
	                    //message.obj = ds;
	                    //message.what = PROCESS_DEFINITION_LIST;
	                    recyclerViewDefinitionList_unTouchable.set(false);
	                    handlet.sendMessage(message);
                    }
                }
            }
        });
        thread.start();
    }
    
    protected void handleRecordAt(int idx, LinearLayoutmy DefinitionListTmp) throws IOException {
    	int position=0;//TODO {}里面的pos标记，是这样好还是同一递增的好？
        int StartOffA;
    	String res = currentDictionary.getRecordAt(idx);
        /*if(true) {
            String StrTmp = res.toLowerCase();
            SpannableString MainStr = new SpannableString(StrTmp);
            int indexA=StrTmp.indexOf(denv.current_hwd.toLowerCase());
            while(indexA!=-1) {
            	MainStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexA, indexA+denv.current_hwd.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            	indexA=StrTmp.indexOf(denv.current_hwd.toLowerCase(),indexA+denv.current_hwd.length());
            }
            res = Build.VERSION.SDK_INT>=24?Html.toHtml(MainStr,Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE):
            	Html.toHtml(MainStr);
        }*/
    	//加粗方案二
    	denv.contents.append("\r\n").append(res);
    	RBTree<Integer> breakerT;
		//String[] MeaningSplitters = new String[] {"<p><font color=red>["};
		//String[] denv.MeaningletsExpandibleSplitters = new String[] {"<p><font color=green>例:"};
    

		//1
		breakerT = new RBTree<Integer>();
		StartOffA=-1;
		for(String SplitLet:denv.ExtraHeaderSplitters){
			StartOffA = res.indexOf(SplitLet);
			while(StartOffA!=-1) {
				breakerT.insert(StartOffA);
				StartOffA = res.indexOf(SplitLet,StartOffA+SplitLet.length());
			}
        }
		ArrayList<Integer> brs = breakerT.flatten();
		
		
		//第一步，剥离掉extra
		String[] extras = new String[brs.size()];
		if(brs.size()!=0) {
			extras[brs.size()-1]=res.substring(brs.get(brs.size()-1));
			int i=0;
			while(i<brs.size()-1) {
				extras[i]=res.substring(brs.get(i),brs.get(i+1));
				i++;
			}
			res = res.substring(0,brs.get(0));
		}
		
		breakerT = new RBTree<Integer>();
		StartOffA=-1;
		for(String SplitLet:denv.MeaningsHeaderSplitters){
			StartOffA = res.indexOf(SplitLet);
			while(StartOffA!=-1) {
				breakerT.insert(StartOffA);
				StartOffA = res.indexOf(SplitLet,StartOffA+SplitLet.length());
			}
        }
		ArrayList<Integer> brs_MeaningHeaders = breakerT.flatten();
		
		RBTree<Integer> breakerT_HeaderTag = new RBTree<Integer>();
		StartOffA=-1;
		for(String SplitLet:denv.HeaderTagHeaderSplitters){//header Tag
			StartOffA = res.indexOf(SplitLet);
			while(StartOffA!=-1) {
				breakerT_HeaderTag.insert(StartOffA);
				StartOffA = res.indexOf(SplitLet,StartOffA+SplitLet.length());
			}
        }
		RBTree<Definition> HeaderTags = new RBTree<Definition>();
		ArrayList<Integer> brs_HeaderTag = breakerT_HeaderTag.flatten();
		if(brs_HeaderTag.size()!=0) {
			//resCoarseParsed[0]=res.substring(0,brs_MeaningHeaders.get(0));
			int i=0;
			while(i<brs_HeaderTag.size()) {
				RBTNode<Integer> tmp = breakerT.sxing(brs_HeaderTag.get(i));
				if(tmp!=null && tmp.usedUp==0) {
					HeaderTags.insert(new Definition(null,res.substring(brs_HeaderTag.get(i),tmp.getKey()),brs_HeaderTag.get(i)));
					tmp.usedUp+=1;
				}
				//else break;
				i++;
			}/*
            i=0;
			while(i<brs_HeaderTag.size()-1) {
				RBTNode<Integer> tmp = breakerT.sxing(brs_HeaderTag.get(i));
				if(tmp!=null)
					HeaderTags.insert(new Definition(null,res.substring(brs_HeaderTag.get(i),tmp.getKey()),brs_HeaderTag.get(i)));
				else
					break;
				i++;
			}*/
		}
		

		if(brs_MeaningHeaders.size()!=0) {
			int cutterStarter = brs_MeaningHeaders.get(0);
			if(brs_HeaderTag.size()!=0)
				cutterStarter = Math.min(brs_HeaderTag.get(0),cutterStarter);
			if(cutterStarter>0) {
				currGrossHeader = (ViewGroup) fetchCardView(-2,res.substring(0,cutterStarter),null);
				DefinitionListTmp.addView(currGrossHeader);
			}
			//String[] resCoarseParsed = new String[brs_MeaningHeaders.size()];
			//resCoarseParsed[0]=res.substring(0,brs_MeaningHeaders.get(0));
			int i=0;
			String i1;
			while(i<brs_MeaningHeaders.size()) {
				//CMN.show(brs_MeaningHeaders.get(i)+":"+brs_MeaningHeaders.get(i+1)+":"+res.length());
				
				RBTNode<Integer> tmpNode = breakerT_HeaderTag.sxing(brs_MeaningHeaders.get(i));
				if(tmpNode!=null)
					i1=res.substring(brs_MeaningHeaders.get(i),Math.min(brs_MeaningHeaders.get(i+1),tmpNode.getKey()));//遇到Headertag 分义项
				else if(i!=brs_MeaningHeaders.size()-1)
					i1=res.substring(brs_MeaningHeaders.get(i),brs_MeaningHeaders.get(i+1));//遇到义项 分义项
				else
					i1=res.substring(brs_MeaningHeaders.get(brs_MeaningHeaders.size()-1));
				sticky_arg<String> sticky_i1 = new sticky_arg<String>(i1);
				String[] examples = get_ex_liJU(sticky_i1);
				View viewTmpI = fetchCardView(position++,sticky_i1.getValue().trim(),examples);
				if(currGrossHeader!=null)
					 viewTmpI.setTag(R.id.currGrossHeader,currGrossHeader);
				RBTNode<Definition> tmpSearch = HeaderTags.xxing(new Definition(null,null,brs_MeaningHeaders.get(i)));
				if(tmpSearch!=null) {//有Tag加Tag记录联系
                    if(tmpSearch.usedUp==0){
                        DefinitionListTmp.addView(fetchCardView(-1,tmpSearch.getKey().getDisplayHtml(),null));
                        tmpSearch.usedUp+=1;
                        viewTmpI.setTag(DefinitionListTmp.getChildCount()-1);
                    }else{
                        tmpSearch.usedUp+=1;
                        viewTmpI.setTag(DefinitionListTmp.getChildAt(DefinitionListTmp.getChildCount()-1).getTag());
                    }
                }else//没Tag记录-1
                	viewTmpI.setTag(-1);
				DefinitionListTmp.addView(viewTmpI);
				i++;
			}
			
		}
		else {
			sticky_arg<String> sticky_i1 = new sticky_arg<String>(res);
			String[] examples = get_ex_liJU(sticky_i1);
			View viewTmpI = fetchCardView(position++,sticky_i1.getValue().trim(),examples);
			viewTmpI.setTag(-1);
			DefinitionListTmp.addView(viewTmpI);
		}
		//处理extra
		for(String i1:extras) {
			SpannableStringBuilder spanTmp;
			View viewTmpI = fetchCardView(position++,i1.trim(),null);
			viewTmpI.setTag(-1);
            DefinitionListTmp.addView(viewTmpI);
		}
		
	}
    public View fetchCardView(int position,String src,String[] examples){
        //CMN.show(spanTmp.charAt(spanTmp.length()-1)+"");

			SpannableStringBuilder MainStr = Build.VERSION.SDK_INT>=24?
	                (SpannableStringBuilder) Html.fromHtml(src,Html.FROM_HTML_MODE_COMPACT)
	                :(SpannableStringBuilder) Html.fromHtml(src);
			final View view = getLayoutInflater().inflate(R.layout.definition_item, null);
			SrcGettableTextView cardDefTv = (SrcGettableTextView)view.findViewById(R.id.textview_definition);
			char endC = MainStr.charAt(MainStr.length()-1);
            while(endC=='\n'||endC=='\r') {
                MainStr=MainStr.delete(MainStr.length()-1, MainStr.length());
                endC = MainStr.charAt(MainStr.length()-1);
            }
            cardDefTv.setSource(src); 
            //cardDefTv.setText(MainStr);
            MainStr.insert(0, "[+]");
            MainStr.setSpan(new AbsoluteSizeSpan(45), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
            if(position>-1){
                MainStr.setSpan(new MyClickText(PopupActivity.this), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                String lnPosTag = new StringBuilder().append("{").append(position).append("}").toString();
                MainStr.insert(3, lnPosTag);
                MainStr.setSpan(new AbsoluteSizeSpan(45), 3, 3+lnPosTag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
                MainStr.setSpan(new MyClickText(PopupActivity.this), 3, 3+lnPosTag.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else {//是tag
                MainStr.setSpan(new MyClickText(PopupActivity.this,Color.BLUE), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                view.setTag(R.id.valid_counter,-1);
            }
            processKeyWord(MainStr);
            cardDefTv.setText(MainStr);
            cardDefTv.setTextColor(Color.BLACK);
            cardDefTv.setMovementMethod(LinkMovementMethod.getInstance());
        	//((TextView)view.findViewById(R.id.textview_definition)).setTextSize(((TextView)view.findViewById(R.id.textview_definition)).getTextSize());
            //cardDefTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((TextView)view.findViewById(R.id.textview_definition)).getTextSize()+2.f);
        	final ViewGroup expandible = (ViewGroup) view.findViewById(R.id.expandible);
			final View expandit = view.findViewById(R.id.expandit);
        	if(examples!=null && examples.length>0) {
	    		for(String ix:examples) {
		        	ViewGroup ViewTmp = (ViewGroup) PopupActivity.this.getLayoutInflater().inflate(R.layout.list_item_expandible, null);
		        	
		        	SpannableStringBuilder ixSpanned = (SpannableStringBuilder) (Build.VERSION.SDK_INT>=24?Html.fromHtml(ix, Html.FROM_HTML_MODE_COMPACT):
        				Html.fromHtml(ix));
		        	processKeyWord(ixSpanned);
		        	//((SrcGettableTextView)ViewTmp.findViewById(R.id.text1)).setText(ix);
		        	//processKeyWord(ixSpanned);
		        	((SrcGettableTextView)ViewTmp.findViewById(R.id.text1)).setSource(ix);
		        	((SrcGettableTextView)ViewTmp.findViewById(R.id.text1)).setText(ixSpanned);
		        	((TextView)ViewTmp.findViewById(R.id.text1)).setTextColor(Color.BLACK);
		        	//((TextView)ViewTmp.findViewById(R.id.text1)).setText(ix);
		        	expandible.addView(ViewTmp);
	        		expandit.setVisibility(View.VISIBLE);
	        		expandible.setVisibility(View.VISIBLE);
	        		if(auto_unfold_exp) {
		        		LayoutParams lp = expandible.getLayoutParams();
						lp.height=-2;
						expandible.setLayoutParams(lp);
	        		}
	    		}
        	}else {
        		expandit.setVisibility(View.INVISIBLE);
        		expandible.setVisibility(View.INVISIBLE);
        	}
			expandit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					LayoutParams lp = expandible.getLayoutParams();
					if(lp.height==0) {
						lp.height=-2;
					}else {
						lp.height=0;
					}
					expandible.setLayoutParams(lp);
				}});
        	expandit.setOnLongClickListener(new OnLongClickListener() {
        		boolean toCheck=true;
				@Override
				public boolean onLongClick(View v) {
					for(int i=0;i<expandible.getChildCount();i++) {
						((CheckBox)expandible.getChildAt(i).findViewById(R.id.checker)).setChecked(toCheck);
					}
					toCheck=!toCheck;
					LayoutParams lp = expandible.getLayoutParams();
					if(lp.height==0) {
						lp.height=-2;
						expandible.setLayoutParams(lp);
					}
					return true;
				}});
        	
			//adadad
			view.findViewById(R.id.btn_add_definition).setOnClickListener(
	                new View.OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        vibrate(Constant.VIBRATE_DURATION);
	                        AnkiDroidHelper mAnkiDroid = MyApplication.getAnkiDroid();
	                        long deckId = currentOutputPlan.getOutputDeckId();
	                        long modelId = currentOutputPlan.getOutputModelId();

	                        String[] map = currentOutputPlan.getFieldsMap();
	                        String[] FieldList = getModelFieldList(modelId);//mAnkiDroid.getApi().getFieldList(modelId);
	                        String[] exportFields = new String[FieldList.length];//开始构造输出内容
	                        int sdkId = Build.VERSION.SDK_INT;
	                        //if(map.length!=exportFields.length)
	                        int i = 0;
	                        for (String exportedFieldKey : map) {
	                        	if(exportedFieldKey.equals(" "))//handle defaults//try 
	                        		exportedFieldKey =  FieldList[i];
	                        	//CMN.show(exportedFieldKey);
	                        	switch(exportedFieldKey) {//处理共有项
	                        		default:
		                                exportFields[i]="";//设回空字符串
	                        			break;
	                        		case "例句":
		                                exportFields[i]=StringEscapeUtils.unescapeHtml(sdkId>=24?Html.toHtml(baseSpan,Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE):
		                                	Html.toHtml(baseSpan));
		                                break;
	                        		case "url":
		                                exportFields[i]="none feature for future(占位)";
		                                break;
	                        		case "单词":
		                                exportFields[i]=denv.current_hwd;
		                                break;
	                        		case "笔记":
	                            		if(!New_Card_added) 
	                            			exportFields[i]=StringEscapeUtils.unescapeHtml(Html.toHtml(top_new_card_Span[top_new_card_OldSelected]));
	                            		else
	                            			exportFields[i]=StringEscapeUtils.unescapeHtml(((EditText)findViewById(R.id.TNC_et)).getText().toString());
	                            		break;
	                        		case "有道美式发音":
	                        			exportFields[i]=CMN.getYoudaoAudioTag(denv.current_hwd,2);
	                        			break;
	                        		case "有道英式发音":
	                        			exportFields[i]=CMN.getYoudaoAudioTag(denv.current_hwd,1);
	                        			break;
	                        		case "释义":
	                        			//CMN.show("释义");
	                        			StringBuilder spanBuilder = new StringBuilder(((SrcGettableTextView)view.findViewById(R.id.textview_definition)).getSource());
	                        			
	                        			ViewGroup VIP = (ViewGroup)view.findViewById(R.id.expandible);
	                        			for(int vIdx=0;vIdx<VIP.getChildCount();vIdx++) {
		                                	ViewGroup vi =  (ViewGroup) VIP.getChildAt(vIdx);
		                                	if(((CheckBox)vi.findViewById(R.id.checker)).isChecked()) {
		                                		spanBuilder.append("<br>")
		                                				   .append(((SrcGettableTextView)vi.findViewById(R.id.text1)).getSource())
		                                				   ;
		                                	}
		                                }
	                        			exportFields[i]=spanBuilder.append("<HR class=\"horiLn\">").toString();
	                        			break;
	                        			
	                        	}
	                        	
	                            //if (Data.get(position).hasElement(exportedFieldKey)) {
	                            //    exportFields[i] = Data.get(position).getExportElement(exportedFieldKey);
	                            //    i++;continue;
	                            //}
	                            i++;
	                        }
	                        mUpdateNoteId = vocaburary_recorder.get(denv.current_hwd);
	                        if(mUpdateNoteId == null){
	                        	Long result=null;
	                        	try {
	                        		List<NoteInfo> searchRes = mAnkiDroid.getApi().findDuplicateNotes(modelId, exportFields[0]);
									for (NoteInfo Ni:searchRes) {
										mUpdateNoteId = Ni.getId();
										boolean compareStringArrayIsPara=true;
										if(Ni.getFields().length!=exportFields.length) {
											compareStringArrayIsPara=false;
										}else {
											int idx=0;
											for(String i1:Ni.getFields()) {//遍历所有字段
												if(idx==0) {idx++;continue;}
												if(!i1.equals(exportFields[idx++])) {
													compareStringArrayIsPara=false;
													break;
												}
											}
										}
										if(compareStringArrayIsPara)
										{
											vocaburary_recorder.put(denv.current_hwd,mUpdateNoteId);
											Toast.makeText(PopupActivity.this, "笔记重复，已关联，待更新。", Toast.LENGTH_SHORT).show();
			                                return;
										}
									}
	                        		result = mAnkiDroid.getApi().addNote(modelId, deckId, exportFields,mTagEditedByUser);
	                        		vocaburary_recorder.put(denv.current_hwd,result);
	                        	} catch (Exception e) {
	                                Toast.makeText(PopupActivity.this, "添加失败！"+"可能是Anki没有打开，请打开以建立连接\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	                                return;
								}
								if (result != null) {
	                                Toast.makeText(PopupActivity.this, R.string.str_added, Toast.LENGTH_SHORT).show();
	                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
	                                	v.setBackground(ContextCompat.getDrawable(
	                                            PopupActivity.this, R.drawable.ic_add_grey));
	                                }
	                                //v.setEnabled(false);
	                                //if (settings.getAutoCancelPopupQ())  finish();
	                            } else {
	                                Toast.makeText(PopupActivity.this, R.string.str_failed_add, Toast.LENGTH_SHORT).show();
	                            }
	                        }
	                        else{
	                            String[] original = mAnkiDroid.getApi().getNote(mUpdateNoteId).getFields();
	                            if(original == null || original.length != exportFields.length){
	                                Toast.makeText(PopupActivity.this, "note failed to update. Note type not compatible", Toast.LENGTH_SHORT).show();
	                                return ;
	                            }
	                            
                            	//only explanations mean to append
	                            else {
	                                    for(int j = 0; j < original.length; j++){
	                                    	String exportedFieldKey = currentOutputPlan.getFieldsMap()[j];
	                                    	if(exportedFieldKey.equals(" "))//handle defaults//try 
	        	                        		exportedFieldKey = getModelFieldList(modelId)[j]; 
	                                    	if(exportedFieldKey.equals("释义")) {//append
		                                        if(original[j].trim().isEmpty() || exportFields[j].trim().isEmpty()) {
		                                            exportFields[j] = original[j] + exportFields[j];
		                                        }else{
		                                            exportFields[j] = original[j] + "<br/>" + exportFields[j];
		                                        }
	                                    	}else {//replace if not blank
	                                    		if (exportFields[j].isEmpty()) {
	    	                                        exportFields[j] = original[j];
	    	                                    }
	                                    	}
	                                    }
	                            }

	                            boolean success = mAnkiDroid.getApi().updateNoteFields(mUpdateNoteId, exportFields);
	                            boolean successTag = mAnkiDroid.getApi().updateNoteTags(mUpdateNoteId, mTagEditedByUser);
	                            if (success && successTag) {
	                                Toast.makeText(PopupActivity.this, "note updated!", Toast.LENGTH_SHORT).show();
	                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
	                                	v.setBackground(ContextCompat.getDrawable(
	                                            PopupActivity.this, R.drawable.ic_add_grey));
	                                }
	                                //v.setEnabled(false);
	                                //if(settings.getAutoCancelPopupQ())finish();
	                            } else {
	                                Toast.makeText(PopupActivity.this, "note failed to update!", Toast.LENGTH_SHORT).show();
	                            }
	                        }
	                    }
	                });
			return view;
	}
    
    String[] FieldListtmp;
    long lastIdx=-1;
	protected String[] getModelFieldList(long modelId) {
		if(FieldListtmp!=null && modelId==lastIdx)
			return FieldListtmp;
		FieldListtmp=MyApplication.getAnkiDroid().getApi().getFieldList(lastIdx=modelId);
		return FieldListtmp;
	}
	class dictionary_environment{
		int currDictIdx;
		int currEntryPos;
        String current_hwd;
        StringBuilder contents;
		String[] HeaderTagHeaderSplitters      ;
		String[] ExtraHeaderSplitters          ;
		String[] MeaningsHeaderSplitters       ;
		String[] MeaningletsExpandibleSplitters;
		public dictionary_environment() {
		}
        public dictionary_environment(String[] a,
                                      String[] b,
                                      String[] c,
                                      String[] d){
             HeaderTagHeaderSplitters      = a;
             ExtraHeaderSplitters          = b;
             MeaningsHeaderSplitters       = c;
             MeaningletsExpandibleSplitters= d;
             contents = new StringBuilder();
        }

		public void setSeparators(String[] a,
				                  String[] b,
				                  String[] c,
				                  String[] d){
			HeaderTagHeaderSplitters      = a;
			ExtraHeaderSplitters          = b;
			MeaningsHeaderSplitters       = c;
			MeaningletsExpandibleSplitters= d;
			contents = new StringBuilder();
		}
    }
    
    dictionary_environment denv;
	
	public ViewGroup currGrossHeader;
    
    protected String[] get_ex_liJU(sticky_arg<String> sticky_arg) {
    	String i1 = sticky_arg.getValue();
    	RBTree<Integer> breakerT = new RBTree<Integer>();
		int StartOffA = -1;
		for(String SplitLet:denv.MeaningletsExpandibleSplitters){//处理例句
			StartOffA = i1.indexOf(SplitLet);
			while(StartOffA!=-1) {
				breakerT.insert(StartOffA);
				StartOffA = i1.indexOf(SplitLet,StartOffA+SplitLet.length());
			}
        }
		ArrayList<Integer> brs = breakerT.flatten();
		String[] ex_liJU = null;
		if(brs.size()!=0) {
			ex_liJU = new String[brs.size()];
			ex_liJU[ex_liJU.length-1]=i1.substring(brs.get(ex_liJU.length-1));
			int i=0;
			while(i<brs.size()-1) {
				ex_liJU[i]=i1.substring(brs.get(i),brs.get(i+1));
				i++;
			}
			sticky_arg.setValue(i1.substring(0,brs.get(0)));
		}
		return ex_liJU;
	}


    private void setupEditNoteDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PopupActivity.this);
        LayoutInflater inflater = PopupActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_edit_note, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit_note);
        edt.setHorizontallyScrolling(false);
        edt.setMaxLines(4);
        edt.setText(mNoteEditedByUser);
        edt.setSelection(mNoteEditedByUser.length());
        dialogBuilder.setTitle(R.string.dialog_note);
        //dialogBuilder.setMessage("输入笔记");
        dialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mNoteEditedByUser = edt.getText().toString();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void setupEditTagDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PopupActivity.this);
        LayoutInflater inflater = PopupActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_edit_tag, null);
        dialogBuilder.setView(dialogView);
        final AutoCompleteTextView editTag = (AutoCompleteTextView) dialogView.findViewById(R.id.edit_tag);
        final CheckBox checkBoxSetAsDefaultTag = (CheckBox) dialogView.findViewById(R.id.checkbox_as_default_tag);
        editTag.setImeOptions(EditorInfo.IME_ACTION_DONE);
        if (mTagEditedByUser.size() == 1) {
            String text = (String) mTagEditedByUser.toArray()[0];
            editTag.setText(text);
            editTag.setSelection(text.length());
        }
        List<UserTag> userTags = DataSupport.findAll(UserTag.class);
        String[] arr = new String[userTags.size()];
        for (int i = 0; i < userTags.size(); i++) {
            arr[i] = userTags.get(i).getTag();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PopupActivity.this,
                R.layout.support_simple_spinner_dropdown_item, arr);
        editTag.setAdapter(arrayAdapter);
        editTag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editTag.getText().toString().isEmpty()) {
                }
                return false;
            }
        });
        boolean setDefaultQ = settings.getSetAsDefaultTag();
        checkBoxSetAsDefaultTag.setChecked(setDefaultQ);
        dialogBuilder.setTitle(R.string.dialog_tag);
        //dialogBuilder.setMessage("输入笔记");
        dialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String tag = editTag.getText().toString().trim();
                if (tag.isEmpty()) {
                    if (checkBoxSetAsDefaultTag.isChecked()) {
                        mTagEditedByUser.clear();
                        Toast.makeText(PopupActivity.this, R.string.tag_cant_be_blank, Toast.LENGTH_LONG).show();
                    } else {
                        settings.setSetAsDefaultTag(false);
                        mTagEditedByUser.clear();
                    }
                    return;
                } else {
                    mTagEditedByUser.clear();
                    mTagEditedByUser.add(tag);
                    settings.setSetAsDefaultTag(checkBoxSetAsDefaultTag.isChecked());
                    settings.setDefaultTag(tag);
                    UserTag userTag = new UserTag(tag);
                    userTag.save();
                }

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    //cancel auto completetextview focus
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof AutoCompleteTextView) {
            View currentFocus = getCurrentFocus();
            int screenCoords[] = new int[2];
            currentFocus.getLocationOnScreen(screenCoords);
            float x = event.getRawX() + currentFocus.getLeft() - screenCoords[0];
            float y = event.getRawY() + currentFocus.getTop() - screenCoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < currentFocus.getLeft() ||
                    x >= currentFocus.getRight() ||
                    y < currentFocus.getTop() ||
                    y > currentFocus.getBottom())) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                v.clearFocus();
            }
        }
        return ret;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //CMN.show("ondestroy!");
        Runtime.getRuntime().gc();
        if(ProjectPath!=null) {
        	JsonObject gg = new JsonObject();
			gg.addProperty("html", Html.toHtml(baseSpan));
			StringBuilder mkeySB = new StringBuilder();
			KeyWordSpan[] spans = baseSpan.getSpans(0, baseSpan.length(), KeyWordSpan.class);
			for(KeyWordSpan span:spans) {
				mkeySB.append(baseSpan.getSpanStart(span))
					.append(" ")
					.append(baseSpan.getSpanEnd(span))
					.append("\n");
			}
			gg.addProperty("mainkey", mkeySB.toString());
			File TODAY = new File(ProjectPath);
			try {
				TODAY.getParentFile().mkdirs();
				FileOutputStream fout = new FileOutputStream(TODAY);
				fout.write(gg.toString().getBytes());
				fout.flush();
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    private void startCBService() {
        Intent intent = new Intent(this, CBWatcherService.class);
        startService(intent);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.GONE);
    }

    private void showSearchButton() {
        progressBar.setVisibility(View.GONE);
        btnSearch.setVisibility(View.VISIBLE);
    }


    void vibrate(int ms) {
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(ms);
    }

	
	protected void notifyDataSetChanged() {
		act.dismissDropDown();
		if(recyclerViewDefinitionList==null)
			return;
		DefinitionListS.removeAllViews();
		DefinitionListS.setLayerType(View.LAYER_TYPE_HARDWARE,null);
		recyclerViewDefinitionList
		.setPadding(0, 0, 0, (int) (25*getResources().getDisplayMetrics().density));
		DefinitionListS.addView(recyclerViewDefinitionList);
		DefinitionListS.post(new Runnable() {
            @Override
            public void run() {
        		DefinitionListS.setScrollY(0);
	              }
        });
	}
	
	public void addMainNote() {
		Set<String> styles = PreferenceManager.getDefaultSharedPreferences(this)
			.getStringSet("mainNoteStyle", null);
		
        AnkiDroidHelper mAnkiDroid = MyApplication.getAnkiDroid();
    	Long basicCardMid = mAnkiDroid.findModelIdByName("Slicer Basic", 2);
        if (basicCardMid == null) {
        	basicCardMid = mAnkiDroid.getApi().addNewCustomModel("Slicer Basic",
	                new String[] {"Front","Back"},
	                new String[] {"card1"},
	                new String[] {"<script type=\"text/javascript\">\n" + 
	                		"function FFC(tag){\n" + 
	                		"     document.all(tag).style.fontFamily=\"宋体\";\n" + 
	                		"  }\n" + 
	                		"\n" + 
	                		"</script>"
	                		+ "{{Front}}"},
	                new String[] {"{{FrontSide}}\n" + 
	                "\n" + 
	                "<hr id=answer>\n" + 
	                "\n" + 
	                "<div class=back>{{Back}}<div>"}
        			,
	                
	                "@font-face {\n" + 
	                " font-family: AdobeNotDef;\n" + 
	                " src: url('AND-Regular.otf');\n" + 
	                "}"
	                + ".card{\n" + 
	                "font-family: arial;\n" + 
	                "font-size: 20px;\n" + 
	                "text-align: center;\n" + 
	                "color: black;\n" + 
	                "background-color: white;\n" + 
	                "}"
	                +".back{\n"+
	                "font-family: arial;\n" + 
	                "font-size: 20px;\n" + 
	                "text-align: left;\n" + 
	                "color: black;\n" + 
	                "background-color: white;\n" +
	                "}"
	                +".examples{\n"+
	                "text-indent:2em;\n" +
	                "font-size: 16px;\n" + 
	                "}"
	                + ".keyheader{\n"+
	                "color: red;\n" + 
	                "float:left;"+
	                "}"
	                +".line_m{\n" + 
	                "text-decoration:underline;\n" + 
	                "font-family:AdobeNotDef;\n" + 
	                "｝"
	                + 
	                
	                "}",
	                null,
	                null
	        );
        }

		vibrate(Constant.VIBRATE_DURATION);
        String[] exportFields = new String[2];
        int i = 0;
        SpannableStringBuilder sb;
        
        if(New_Card_added)
        	top_new_card_Span[top_new_card_OldSelected]=(SpannableStringBuilder) ((EditText)findViewById(R.id.TNC_et)).getText();
        
        if(un_review_mainnote)
        	exportFields[0]=Build.VERSION.SDK_INT>=24?Html.toHtml(top_new_card_Span[0],Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE):
            	Html.toHtml(top_new_card_Span[0]);
    	else {
    		SpannableStringBuilder mainNoteSpan = new SpannableStringBuilder();
    		mainNoteSpan.append(baseSpan)
    					.append("\n")
    					.append(top_new_card_Span[0]);
    		processMainNote(mainNoteSpan);
    		exportFields[0]=Build.VERSION.SDK_INT>=24?Html.toHtml(mainNoteSpan,Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE):
    			Html.toHtml(mainNoteSpan);
    	}
        try {
			exportFields[0] = processImgTag(exportFields[0]);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        exportFields[0] = StringEscapeUtils.unescapeHtml(exportFields[0]);//hate hate hate
        sb = new SpannableStringBuilder();
        
        StringBuilder raw_sb = new StringBuilder();
		//if(recyclerViewDefinitionList!=null)
		ArrayList<myCpr<CoordCpr<Integer, Integer>, android.widget.LinearLayout>> res = recordsPool.flatten();
		for(int iii=0;iii<res.size();iii++) {
			android.widget.LinearLayout recyclerViewDefinitionListTTT = res.get(iii).value;
			for(int i1=0;i1<recyclerViewDefinitionListTTT.getChildCount();i1++) {
	        	ViewGroup ppp = (ViewGroup) recyclerViewDefinitionListTTT.getChildAt(i1);
	        	ColorFilterGettableView bg = ppp.findViewById(R.id.bg);
	        	boolean isTag = bg.getColorFilter()!=null;
	        	if(isTag || ppp.findViewById(R.id.add_indicator).getVisibility()==View.VISIBLE) {
	        		SrcGettableTextView itemTv = (SrcGettableTextView)ppp.findViewById(R.id.textview_definition);
	        		//if(isTag) raw_sb.append("<b>");//强行加粗
	        		//else raw_sb.append("<a class=keyheader>【<a>");
	        		
	        		raw_sb.append(itemTv.getSource());
	        		//sb.append((Spanned)itemTv.getText());
	    			ViewGroup VIP = (ViewGroup)ppp.findViewById(R.id.expandible);
	    			for(int vIdx=0;vIdx<VIP.getChildCount();vIdx++) {
	                	ViewGroup vi =  (ViewGroup) VIP.getChildAt(vIdx);
	                	if(((CheckBox)vi.findViewById(R.id.checker)).isChecked()) {
	                		raw_sb//.append("<p class=examples>")//例句首行缩进
	                				   .append(((SrcGettableTextView)vi.findViewById(R.id.text1)).getSource())
	                				   //.append("</p>")
	                				   ;
	                	}
	                }
	    			//if(isTag) sb.append("</b>");//强行加粗
	        	}
	        }
			raw_sb.append("<HR class=\"horiLn\">");
		}
		
		raw_sb.append(Build.VERSION.SDK_INT>=24?Html.toHtml(top_new_card_Span[1],Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE):
        	Html.toHtml((top_new_card_Span[1])));
        exportFields[1] = raw_sb.toString();
        
        try {
        	exportFields[1]  = processImgTag(exportFields[1] );
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        
        long deckId = currentOutputPlan.getOutputDeckId();
        if(mMainNoteId == 0){
        	Long result=null;
        	try {
        		List<NoteInfo> searchRes = mAnkiDroid.getApi().findDuplicateNotes(basicCardMid, exportFields[0]);
				for (NoteInfo Ni:searchRes) {
					boolean compareStringArrayIsPara=true;
					if(Ni.getFields().length!=exportFields.length) {
						compareStringArrayIsPara=false;
					}else {
						int idx=0;
						for(String i1:Ni.getFields()) {
							if(idx==0) {idx++;continue;}
							if(!i1.equals(exportFields[idx++])) {
								compareStringArrayIsPara=false;
								break;
							}
						}
					}
					if(compareStringArrayIsPara)
					{
						Toast.makeText(PopupActivity.this, "笔记重复，添加失败！", Toast.LENGTH_SHORT).show();
                        return;
					}
				}
        		result = mAnkiDroid.getApi().addNote(basicCardMid, deckId, exportFields,mTagEditedByUser);
			} catch (Exception e) {
                Toast.makeText(PopupActivity.this, "添加失败！"+"可能是Anki没有打开，请打开以建立连接\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return;
			}
			if (result != null) {
                Toast.makeText(PopupActivity.this, R.string.str_added, Toast.LENGTH_SHORT).show();
                mMainNoteId =result;
			} else {
                Toast.makeText(PopupActivity.this, R.string.str_failed_add, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            String[] original = mAnkiDroid.getApi().getNote(mMainNoteId).getFields();
            if(original == null || original.length != exportFields.length){
                Toast.makeText(PopupActivity.this, "note failed to update. Note type not compatible", Toast.LENGTH_SHORT).show();
                return ;
            }

            boolean success = mAnkiDroid.getApi().updateNoteFields(mMainNoteId, exportFields);
            boolean successTag = mAnkiDroid.getApi().updateNoteTags(mMainNoteId, mTagEditedByUser);
            if (success && successTag) {
                Toast.makeText(PopupActivity.this, "note updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PopupActivity.this, "note failed to update!", Toast.LENGTH_SHORT).show();
            }
        }

    }
	
	private void processMainNote(SpannableStringBuilder builder){
		
		KeyWordSpan[] spans = builder.getSpans(0, builder.length(), KeyWordSpan.class);
		int counter=0;
		for(KeyWordSpan span:spans) {
			builder.insert(builder.getSpanEnd(span), "</u>＿</u>");
			builder.insert(builder.getSpanStart(span), "<u>＿<u class=\"line_m\" id=\"z"+counter+"\" onClick=\"FFC('z"+counter
					+"')\">");
			counter++;
		}
	}
	
	
	
	private String processImgTag(String str) throws FileNotFoundException {
        //sad that E cannot hack html.java
		//More labors then.
		StringBuilder sb = new StringBuilder();
		int baseIdx=0;
		int offA = str.indexOf("<img src=\"");
		if(offA!=-1) {
			int offB = str.indexOf("\">",offA);
			sb.append(str.substring(baseIdx,offA+"<img src=\"".length()));
			String src = str.substring(offA+"<img src=\"".length(),offB);
			String new_src = src;
			if(ImageUrlAssit.getInstance().imageCache.containsKey(src)) {
				//CMN.show("真的有！");
				Drawable image_data = ImageUrlAssit.getInstance().imageCache.get(src);
				new_src = System.currentTimeMillis()+".png";
				//保存
				new File("/sdcard/AnkiDroid/collection.media").mkdirs();
	        	File iconFile = new File("/sdcard/AnkiDroid/collection.media/"+new_src);
	        	Bitmap bitmap = Bitmap  
	                    .createBitmap(  
	                    		(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, image_data.getIntrinsicWidth(), getResources().getDisplayMetrics())
	                    		,  
	                    		(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, image_data.getIntrinsicHeight(), getResources().getDisplayMetrics())
	                    		,  
	                    		image_data.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
	                                    : Bitmap.Config.RGB_565);  
	            Canvas canvas = new Canvas(bitmap);  
	            image_data.setBounds(0, 0,   bitmap.getWidth() ,bitmap.getHeight());  
	            image_data.draw(canvas);  
	            int size = bitmap.getWidth() * bitmap.getHeight() * 4;  
	            // 压缩格式PNG 质量为100
	            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(iconFile));
	            image_data.setBounds(0, 0, image_data.getIntrinsicWidth(),  image_data.getIntrinsicHeight());  
			}
			sb.append(new_src)
				.append("\">");
			baseIdx = offB+"\">".length();
			offA = str.indexOf("<img src=\"",baseIdx);
		}
		if(baseIdx<str.length())
			sb.append(str.substring(baseIdx));
		return sb.toString();
	}




	public void processKeyWord(SpannableStringBuilder MainStr){
    	if(true) {
            String StrTmp = MainStr.toString().toLowerCase();
            //Log.e("hahaha",StrTmp);
            //Log.e("hahaha",Html.toHtml(MainStr));
            int indexA=StrTmp.indexOf(denv.current_hwd.toLowerCase());
            while(indexA!=-1) {
            	//Log.e("hahaha2",indexA+":"+denv.current_hwd);
            	MainStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexA, indexA+denv.current_hwd.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            	indexA=StrTmp.indexOf(denv.current_hwd.toLowerCase(),indexA+denv.current_hwd.length());
            }
            //Log.e("hahaha3",Html.toHtml(MainStr));
        }
	}

	//grossclick
	
	PopupWindow sharePopup;
	boolean isPopUpShown;
	long preVDissmissT=-1;
	@SuppressLint("InlinedApi")
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.actionMenu1:
				SpannableStringBuilder tmpBuilder = (SpannableStringBuilder) Html.fromHtml(Html.toHtml(baseSpan));
				tmpBuilder.delete(tv.getSelectionEnd(),tmpBuilder.length());
				tmpBuilder.delete(0, tv.getSelectionStart());
				Intent intent = new Intent(this, PopupActivity.class);
            	intent.setAction(Intent.ACTION_PROCESS_TEXT);
            	intent.setType("text/plain");
            	intent.putExtra(Intent.EXTRA_PROCESS_TEXT, Html.toHtml(tmpBuilder));
            	startActivity(intent);
			break;
			case R.id.actionMenu2://toggle设置key
				if(tv.hasSelection()){
                	boolean isSpanFound = false;
					int st = tv.getSelectionStart();
					int ed = tv.getSelectionEnd();
					KeyWordSpan[] spans = baseSpan.getSpans(st, ed, KeyWordSpan.class);
					for(KeyWordSpan span:spans) {
						if(baseSpan.getSpanStart(span)==st && baseSpan.getSpanEnd(span)==ed) {
							baseSpan.removeSpan(span);
							if(Build.VERSION.SDK_INT>=24)
								tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
							else
								tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
							tv.hideTvSelecionHandle();
    						tv.clearFocus();
							tv.setTextKeepState(baseSpan);
							isSpanFound=true;
							break;
						}
					}
					if(!isSpanFound) {
						baseSpan.setSpan(new KeyWordSpan(PopupActivity.this), st, ed, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
						tv.hideTvSelecionHandle();
						tv.clearFocus();
						tv.setTextKeepState(baseSpan);
					}
					
                }
			break;
			case R.id.actionMenu_share:
				if(System.currentTimeMillis()-preVDissmissT<200) {
					//sharePopup.dismiss();
					break;
				}
		        View view = getLayoutInflater().inflate(R.layout.popup_share, null);  
				sharePopup = new PopupWindow(view,   
		                    (int)(160 * getResources().getDisplayMetrics().density), LayoutParams.WRAP_CONTENT);  
				sharePopup.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						sharePopup=null;
						preVDissmissT = System.currentTimeMillis();
					}});
		        List<AppInfo> shareAppInfos = new ArrayList<AppInfo>();
		        new File(AppInfo.extDir).mkdirs();
		        for(File beautifully:new File(AppInfo.extDir).listFiles()) {
	        		AppInfo infoI = new AppInfo();
		        	File bi = beautifully.listFiles()[0];
		        	Bitmap bitmap;  
	                bitmap = BitmapFactory.decodeFile(bi.getAbsolutePath());  
	                infoI.setAppIcon(new BitmapDrawable(bitmap));
	                String[] l = bi.getName().split("OVIHCS");
	                infoI.setAppName(l[0]);
	                infoI.setAppLauncherClassName(l[1]);
	                infoI.setAction(l[2]);
	                infoI.setAppPkgName(beautifully.getName());
	                infoI.setInternal(true);
	                shareAppInfos.add(infoI);
		        }
	            final ListView shareList = (ListView) view.findViewById(R.id.share_list);  
	            final ShareCustomAdapter adapter = new ShareCustomAdapter(this, shareAppInfos);  
	            adapter.setPopulaterFutherListener(new OnItemClickListenermy() {  
					@Override
					public void populateFurther() {
						List<ResolveInfo> resolveInfos;
				        PackageManager packageManager = getPackageManager();  

				        Intent Xiiaror = new Intent(Intent.ACTION_SEND, null);  
				        Xiiaror.addCategory(Intent.CATEGORY_DEFAULT);  
				        Xiiaror.setType("text/plain");  
				        PackageManager pManager = getPackageManager();  
				        resolveInfos = pManager.queryIntentActivities(Xiiaror,   
				                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT); 
				        //List<AppInfo> shareAppInfos = adapter._list;  
				        if (resolveInfos != null){  
				            for (ResolveInfo resolveInfo : resolveInfos) {
				                String pkgName = resolveInfo.activityInfo.packageName;
				                if(!new File(AppInfo.extDir+pkgName).exists()) {  
					                AppInfo appInfo = new AppInfo();  
					                appInfo.setAppPkgName(pkgName);  
					                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);  
					                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());  
					                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));  
					                adapter._list.add(appInfo); 
				                }
				            }  
				        } 
				        Xiiaror = new Intent(Intent.ACTION_WEB_SEARCH);  
				        resolveInfos = pManager.queryIntentActivities(Xiiaror,   
				        		PackageManager.MATCH_ALL); 
				        if (resolveInfos != null){  
				            for (ResolveInfo resolveInfo : resolveInfos) {
				                String pkgName = resolveInfo.activityInfo.packageName;
				                if(!new File(AppInfo.extDir+pkgName).exists()) {  
					                AppInfo appInfo = new AppInfo();  
					                appInfo.setAppPkgName(pkgName);  
					                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);  
					                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());  
					                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));  
					                appInfo.setAction(Intent.ACTION_WEB_SEARCH);
					                adapter._list.add(appInfo); 
				                }
				            }  
				        } 
				        Xiiaror = new Intent(SearchManager.INTENT_ACTION_GLOBAL_SEARCH);  
				        resolveInfos = pManager.queryIntentActivities(Xiiaror,   
				        		PackageManager.MATCH_ALL); 
				        if (resolveInfos != null){  
				            for (ResolveInfo resolveInfo : resolveInfos) {
				                String pkgName = resolveInfo.activityInfo.packageName;
				                if(!new File(AppInfo.extDir+pkgName).exists()) {  
					                AppInfo appInfo = new AppInfo();  
					                appInfo.setAppPkgName(pkgName);  
					                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);  
					                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());  
					                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));  
					                appInfo.setAction(Intent.ACTION_WEB_SEARCH);
					                adapter._list.add(appInfo); 
				                }
				            }  
				        } 
				        
						adapter.setListMargin(3);
						shareList.setAdapter(adapter);
					}

					@Override
					public void deleteSelected() {
						int st = tv.getSelectionStart();
    					int ed = tv.getSelectionEnd();
    					baseSpan.delete(st, ed);
						if(Build.VERSION.SDK_INT>=24)
							tv.onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.ACTION_DOWN));
						else
							tv.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP,tv.xmy,tv.ymy, 0));
    					tv.hideTvSelecionHandle();
    					tv.clearFocus();
						tv.setTextKeepState(baseSpan);
						
						vibrate(Constant.VIBRATE_DURATION);
					}

					@Override
					public void preDeleteSelected() {
						sharePopup.dismiss();						
					}

					@Override
					public void sendToFront() {
						if(New_Card_added) {
							top_new_card_Span[top_new_card_OldSelected]=(SpannableStringBuilder) ((EditText)findViewById(R.id.TNC_et)).getText();
						}
						int st = tv.getSelectionStart();
    					int ed = tv.getSelectionEnd();
    					CharSequence subseq = baseSpan.subSequence(st, ed);
						top_new_card_Span[0].append(subseq);
						if(New_Card_added && top_new_card_OldSelected==0)
							((EditText)findViewById(R.id.TNC_et)).setText(top_new_card_Span[0]);
					}  
					
					@Override
					public void sendToBack() {
						if(New_Card_added) {
							top_new_card_Span[top_new_card_OldSelected]=(SpannableStringBuilder) ((EditText)findViewById(R.id.TNC_et)).getText();
						}
						int st = tv.getSelectionStart();
    					int ed = tv.getSelectionEnd();
    					CharSequence subseq = baseSpan.subSequence(st, ed);
						top_new_card_Span[1].append(subseq);
						if(New_Card_added && top_new_card_OldSelected==1)
							((EditText)findViewById(R.id.TNC_et)).setText(top_new_card_Span[1]);
					} 
					
	            });
	            shareList.setAdapter(adapter);  
	            shareList.setOnItemClickListener(new OnItemClickListener() {  
	                @Override  
	                public void onItemClick(AdapterView<?> p, View view,  
	                        int position, long id) {  
	                    AppInfo appInfo = (AppInfo) p.getAdapter().getItem(position);  
	                    Intent shareIntent = new Intent(appInfo.getAction()); //
	                    shareIntent.setComponent(new ComponentName(appInfo.getAppPkgName(), appInfo.getAppLauncherClassName()));  
	                    int st = tv.getSelectionStart();
    					int ed = tv.getSelectionEnd();
    					shareIntent.setType("text/html;text/plain");  
	                    shareIntent.putExtra(SearchManager.QUERY,baseSpan.subSequence(st, ed).toString().replace(".", " "));
	                    shareIntent.putExtra(Intent.EXTRA_HTML_TEXT,baseSpan.subSequence(st, ed));  
	                    shareIntent.putExtra(Intent.EXTRA_TEXT,baseSpan.subSequence(st, ed).toString());  
	                      
	                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	                    	
	                    startActivity(shareIntent);  
	                }
	            }); 
	            shareList.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> p, View view, int position, long id) {
						//CMN.show("longClick!");
	                    AppInfo appInfo = (AppInfo) p.getAdapter().getItem(position);  
	                    try {
							appInfo.toggleInternal();
							//adapter.notifyDataSetChanged();
							int pos = shareList.getFirstVisiblePosition();
							shareList.setAdapter(adapter);
							shareList.setSelection(pos);
							vibrate(Constant.VIBRATE_DURATION);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return true;
					}});
		              
	           
		        sharePopup.setFocusable(false);  
		        sharePopup.setOutsideTouchable(true);  
		        sharePopup.setBackgroundDrawable(new BitmapDrawable());
		        sharePopup.showAsDropDown(v, -5, 5);  
			break;
			case R.id.actionMenu_close:
				actionMenu.setVisibility(View.INVISIBLE);
			break;
            case R.id.mBtnNewCard:
                if(New_Card_added) {
                    top_new_card_Span[top_new_card_OldSelected]=(SpannableStringBuilder) ((EditText)findViewById(R.id.TNC_et)).getText();
                    coreSibling.removeViewAt(0);
                    coreSibling.getLayoutParams().height-=ViewUtil.dp2px(new_card_height);
                    New_Card_added=false;
                }
                else
                    add_New_Card_View();
            break;
            case R.id.choose_dict_btn:
                if(New_Card_added) {
                    top_new_card_Span[top_new_card_OldSelected]=(SpannableStringBuilder) ((EditText)findViewById(R.id.TNC_et)).getText();
                    coreSibling.removeViewAt(0);
                    coreSibling.getLayoutParams().height-=ViewUtil.dp2px(new_card_height);
                    New_Card_added=false;
                }else if(CDView_added) {
                    coreSibling.getLayoutParams().height-=coreSibling.getChildAt(0).getHeight();
                    coreSibling.removeViewAt(0);
                    CDView_added=false;
                }
                else
                    add_Choose_dict_View();
            break;
            case R.id.mBtnMainNote:/*
            	PopupMenu mPopupMenu = new PopupMenu(PopupActivity.this, v);

		        mPopupMenu.inflate(R.menu.add_main_note);   
		        mPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem arg0) {
						addMainNote();
						return false;
					}});  
		        mPopupMenu.setGravity(Gravity.CENTER);
		        mPopupMenu.show();*/
            	View vTmp = LayoutInflater.from(PopupActivity.this).inflate(R.layout.simple_add_menu,null);
            	final PopupWindow mPopup=new PopupWindow(vTmp, WindowManager.LayoutParams.WRAP_CONTENT,
        				WindowManager.LayoutParams.WRAP_CONTENT);
            	vTmp.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						addMainNote();
						mPopup.dismiss();
					}});
        		mPopup.setBackgroundDrawable(new BitmapDrawable());
        		mPopup.setFocusable(false);
        		mPopup.setOutsideTouchable(true);
        		mPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); 
        		mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); 
        		mPopup.setHeight(v.getHeight()+10);
        		mPopup.showAsDropDown(v, 0, -50, Gravity.TOP|Gravity.START);
        		mPopup.update(v, 0, -50, -1, -1); 
        	break;
		}
		
	}
	
	OnClickListener toggleOnclick = new OnClickListener() {
		boolean ck;
		@Override
		public void onClick(View v) {
			ck = ((ToggleButton)v).isChecked();//这个是点击之后的值
			switch(v.getId()){
		        case R.id.mt1://自动展开例句
		        	//CMN.show(""+ck);
		        	Settings.getInstance(PopupActivity.this).putBoolean("auto_unfold_exp", auto_unfold_exp=ck);
		        	
		        		ArrayList<myCpr<CoordCpr<Integer, Integer>, android.widget.LinearLayout>> res = recordsPool.flatten();
		        		if(recyclerViewDefinitionList!=null)
		        			res.add(new myCpr(null,recyclerViewDefinitionList));
		        		for(int iii=0;iii<res.size();iii++) {
		        			android.widget.LinearLayout recyclerViewDefinitionListTTT = res.get(iii).value;
		        			for(int i1=0;i1<recyclerViewDefinitionListTTT.getChildCount();i1++) {
		        	        	ViewGroup ppp = (ViewGroup) recyclerViewDefinitionListTTT.getChildAt(i1);
		        	        	View ex = ppp.findViewById(R.id.expandit);
		        	        	if(ex!=null && ex.getVisibility()==View.VISIBLE)
	        	        		if(ck) {
	        	        			LayoutParams lp = ppp.findViewById(R.id.expandible).getLayoutParams();
        							lp.height=-2;
        							ppp.findViewById(R.id.expandible).setLayoutParams(lp);
	        	        		}else {
	        	        			LayoutParams lp = ppp.findViewById(R.id.expandible).getLayoutParams();
	        	        			lp.height=0;
	        	        			ppp.findViewById(R.id.expandible).setLayoutParams(lp);
	        	        		}
	        			}
		        	}
		        	middle_expandable.postDelayed(new Runnable() {
						@Override 
						public void run() {middle_expandable.setVisibility(View.GONE);}
						}, 250);
	        	break;
		    	case R.id.mt2://un-review main note i papyrus scroll
		    		Settings.getInstance(PopupActivity.this).putBoolean("un_review_mainnote", un_review_mainnote=ck);
		    		//un_review_mainnote=ck;
		    		middle_expandable.postDelayed(new Runnable() {
						@Override 
						public void run() {middle_expandable.setVisibility(View.GONE);}
						}, 250);
	    		break;
			}
		}};
	boolean auto_unfold_exp,un_review_mainnote;
	
	public void scanSettings(){
		Settings s = Settings.getInstance(PopupActivity.this);
		auto_unfold_exp = s.getBoolean("auto_unfold_exp");
		un_review_mainnote = s.getBoolean("un_review_mainnote");
	}
	
    public class ListViewAdapter extends BaseAdapter implements Filterable {   
    	
        private Object myFilter;
        private mdict dictionary;
		//构造函数
        public ListViewAdapter() 
        {  
        	
        }
        public ListViewAdapter(mdict d) 
        {  
        	dictionary = d;
        }
        public void setDict(mdict d) {
        	dictionary = d;
        	notifyDataSetChanged();
        }
        @Override
        public int getCount() {
			if(dictionary==null)
				return 0;
			return (int) dictionary.getNumberEntrys();
        }  
        @Override
        public View getItem(int position) {
			return null;
			}    
        @Override
        public long getItemId(int position) {    
          return position;    
        }  
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
          //return lstItemViews.get(position);
        	viewHolder vh;
            
	        final String keyText = dictionary.getEntryAt(position);
	        
	        if(convertView!=null){
	        		//标题
	        		vh=(viewHolder)convertView.getTag();
	                vh.title.setText(keyText);
	        	}else{
	                //标题
	        		convertView = View.inflate(getApplicationContext(),R.layout.simple_spinner_dropdown_itemmy , null);//R.layout.listview_item0
	        		vh=new viewHolder();
	        		vh.title = (TextView) convertView;//(TextView) convertView.findViewById(R.id.text1);  
	        		vh.title.setTextColor(Color.parseColor("#000000"));
	        		vh.title.setText(keyText);    
	                convertView.setTag(vh);
	        	}

	        vh.title.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						//Log.e("asd","asdasd");
						recyclerViewDefinitionList = new LinearLayoutmy(PopupActivity.this);
						recyclerViewDefinitionList.setOrientation(1);
						PopupActivity.this.notifyDataSetChanged();
						denv.current_hwd = keyText;
						int idx = position;
						while(idx>0) {
							String tmp = currentDictionary.getEntryAt(idx-1);
							if(!tmp.toLowerCase().equals(denv.current_hwd))
								break;
							idx--;
	        	        }
    	        		recyclerViewDefinitionList.setTag(denv.currEntryPos=idx);
    	        		CoordCpr<Integer, Integer> entryIndexor = new CoordCpr<Integer,Integer>(denv.currDictIdx,idx);
        	        	myCpr<CoordCpr<Integer, Integer>, android.widget.LinearLayout> tmptmp = recordsPool.searchT(new myCpr<CoordCpr<Integer, Integer>, LinearLayout>(entryIndexor,null));
        	        	if(tmptmp!=null) {
        	        		recyclerViewDefinitionList=(LinearLayoutmy) tmptmp.value;
        	        		PopupActivity.this.notifyDataSetChanged();
        	        	}else while(true) {
                    		handleRecordAt(idx,recyclerViewDefinitionList);
                        	//ds.add(new Definition(eleMap,mdTest.getRecordAt(idx)));
							if(idx>=currentDictionary.getNumberEntrys()-1)
								break;
        					String tmp = currentDictionary.getEntryAt(++idx);
        					if(tmp==null)//TODO:: check it
        						break;
        					if(!tmp.toLowerCase().equals(denv.current_hwd))
        						break;
                    	}
						act.dismissDropDown();
					} catch (IOException e) {
					}
				}});
	          return convertView;
        }
		@Override
		public Filter getFilter() {
	        if (myFilter == null) {  
	            myFilter = new MyFilter();  
	        }  
	        return (Filter) myFilter;  
		}
		class MyFilter extends Filter {
			int idx = -1;
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				try {
					//idx = currentDictionary.lookUp(constraint.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				//CMN.show(constraint+"");
				//if(idx!=-1) {
					//act.setListSelection(idx);
				//}
			}  
		}
    }
    
    static class viewHolder{
    	private TextView title;
    }
  
    TextWatcher mTW = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.e("222222222","!!!onTextChanged");
			Log.e("saasd",act.hasFocus()+":"+s);
			try {
				int idx2Tmp = currentDictionary.lookUp(s.toString(),true);
				act.showDropDown(true);
				act.setListSelection(idx2Tmp-1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			
		}};
		
		
		//int heightDelta = 0;
		boolean hasKeyBoard = false;
		@Override//监听键盘弹出
		public void onResize(int w, int h, int oldw, int oldh) {
			//CMN.showTT((main_lv.getVisibility()==View.VISIBLE)+"");
			//if(focusedView==null) return;
			//如果第一次初始化
			if (oldh == 0) {
				return;
			}
			//如果用户横竖屏转换
			if (w != oldw) {
				return;
			}
			if (h < oldh) {
				//heightDelta = h - oldh -250;
				hasKeyBoard=true;
				//RelativeLayout.LayoutParams lp = ((RelativeLayout.LayoutParams)splitterrl.getLayoutParams());
				//lp.bottomMargin=250;
				//splitterrl.setLayoutParams(lp);
				//输入法弹出
				CMN.show("输入法弹出");
				//main_lv.scrollToPosition((Integer) focusedView.getTag(R.id.position));
			} else if (h > oldh) {
				//heightDelta=0;
				hasKeyBoard=false;
				//输入法关闭
				CMN.show("输入法关闭");
			}
			//int distance = h - old;
			//EventBus.getDefault().post(new InputMethodChangeEvent(distance,mCurrentImageId));
		}


}
