package com.knziha.ankislicer.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fenwjian.sdcardutil.RashMap;
import com.fenwjian.sdcardutil.RashSet;
import com.ichi2.anki.CollectionHelper;
import com.ichi2.async.DeckTask;
import com.ichi2.async.DeckTask.TaskData;
import com.ichi2.libanki.Card;
import com.ichi2.libanki.Collection;
import com.ichi2.libanki.Decks;
import com.knziha.ankislicer.R;
import com.knziha.ankislicer.customviews.ShelfLinearLayout;
import com.knziha.ankislicer.customviews.VerticalRecyclerViewFastScrollermy;
import com.mmjang.ankihelper.programData.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Fragment_CardViewer extends Fragment implements 
				View.OnClickListener, OnLongClickListener{
	
	long deckIDSelected=-1;
	
	public Fragment_CardViewer() {
		super();
		mCards = new ArrayList<Map<String, String>>();
		mAdapter = new main_list_Adapter();
		//![7]
		
	}
    
    
    class LIVEPULL_Option{
    	File hisHandle; 
    	String load_path;
    	boolean scrollImmediately=true;
    	
    }
    LIVEPULL_Option opt;

    
    
    private int startCandidate;
    
    ViewGroup main;
    
    private FloatingActionButton fab;
	RecyclerView lv;
	TextView startButton   ;
    TextView status_widget1;
    TextView status_widget2;
    TextView status_widget3;
    TextView status_widget4;
    
    int actionBarHeight;
    main_list_Adapter mAdapter;
    
    View main_clister_layout;
    ListView mDrawerList;
    View listHolder;
    Toolbar toolbar;
    //Toolbar sideBarPopHolder;
	ShelfLinearLayout sideBar;
    TextView counter;
    View counterHolder;
    View shelfright;
    
    Collection mCol;
	protected List<Map<String, String>> mCards;
	LinearLayoutManager lm;
	VerticalRecyclerViewFastScrollermy fastScroller;
	//BookMarkView BookMarker;


	SearchView searchView;
	InputMethodManager imm;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		main_clister_layout= inflater.inflate(R.layout.card_lister, container,false);

		TypedValue tval = new TypedValue();
		if (CMN.a.getTheme().resolveAttribute(android.R.attr.actionBarSize, tval, true))
		{
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tval.data,getResources().getDisplayMetrics());
		}
		imm = 
				( InputMethodManager ) 
				getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
		mSettings = Settings.getInstance(getContext());
		
		
        lv = (RecyclerView) main_clister_layout.findViewById(R.id.main_list);
        //((DefaultItemAnimator) lv.getItemAnimator()).setSupportsChangeAnimations(false);//取消更新item时闪烁
        lm = new LinearLayoutManager(getContext());
        lv.setLayoutManager(lm);
        
        fastScroller = (VerticalRecyclerViewFastScrollermy) main_clister_layout.findViewById(R.id.fast_scroller);
        //bookMarker = (BookMarkView) main_clister_layout.findViewById(R.id.fast_scroller);
        fastScroller.setRecyclerView(lv);
        fastScroller.setConservativeScroll(!mSettings.getBoolean("strictscroll"));
        lv.addOnScrollListener(fastScroller.getOnScrollListener());
        
		lv.setAdapter(mAdapter);
		
		mAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				long id = Long.parseLong(mCards.get(position).get("id"), 10);
				if(SelectionMode==SelectionMode_select) {
					if(Selection.contains(id)) {
						Selection.removeLastSelected();
					}else {
						Selection.put(id,position);
					}
					counter.setText(Selection.size()+":\n"+mCards.size());
					mAdapter.notifyItemChanged(position);
					//CMN.show(""+position);
				}
			}});
		mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public void onItemLongClick(View view, int position) {
				
			}});
		
		main_clister_layout.findViewById(R.id.tools0).setOnClickListener(this);
		main_clister_layout.findViewById(R.id.tools2).setOnClickListener(this);
		View select_cards = main_clister_layout.findViewById(R.id.tools1);
		select_cards.setOnClickListener(this);
		select_cards.setOnLongClickListener(this);
		main_clister_layout.findViewById(R.id.tools3).setOnClickListener(this);
		
		
		
		main_clister_layout.findViewById(R.id.choosed).setOnClickListener(this);
		main_clister_layout.findViewById(R.id.changed).setOnClickListener(this);
		main_clister_layout.findViewById(R.id.bookmark).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onLongClick(main_clister_layout.findViewById(R.id.bookmark0));
				
			}});
		main_clister_layout.findViewById(R.id.side2).setOnClickListener(this);
		
		main_clister_layout.findViewById(R.id.search).setOnClickListener(this);
		
		ToggleButton tg2 = main_clister_layout.findViewById(R.id.tg2);
		tg2.setOnClickListener(checkViewOnClick);
		tg2.setOnLongClickListener(this);
		main_clister_layout.findViewById(R.id.choosed).setOnLongClickListener(this);
    	
		
    	if(mSettings.getBoolean("hidever")) {
    		tg2.setChecked(true);
    		fastScroller.setVisibility(View.GONE);
    	}
    	
		main_clister_layout.findViewById(R.id.lst).setOnClickListener(this);
		main_clister_layout.findViewById(R.id.nxt).setOnClickListener(this);
		
		toolbar = (Toolbar) main_clister_layout.findViewById(R.id.toolbar);
		//sideBarPopHolder = (Toolbar) main_clister_layout.findViewById(R.id.sideBarPopHolder);
		toolbar.inflateMenu(R.menu.menu_search_view);
		Menu toolbarmenu = toolbar.getMenu();
		MenuItem searchItem = toolbarmenu.getItem(0);
		searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		ImageView iv_submit = (ImageView) searchView.findViewById(R.id.search_go_btn);
	    //这样就可以修改图片了
	    iv_submit.setImageResource(R.drawable.enter);
		searchView.setIconified(false);//设置searchView处于展开状态
		searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
		searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
		searchItem.setShowAsAction(2);
		searchView.setSubmitButtonEnabled(true);//显示提交按钮
		searchView.findViewById(R.id.search_src_text).setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				//dont hide, nothing to feel shy about.
				//CMN.show("asd");
				v.postDelayed(new Runnable() {
					@Override
					public void run() {
						searchView.findViewById(R.id.search_go_btn).setVisibility(View.VISIBLE);
						searchView.findViewById(R.id.submit_area).setVisibility(View.VISIBLE);
					}}, 50);
			}});
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
	        @Override
	        public boolean onQueryTextSubmit(String query) {
	            //Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
	    		try {
	    			JSONObject x = mCol.getConf();
	    			x.put("sortBackwards", true);
	    			x.put("sortType", "noteCrt");
	    		} catch (JSONException e1) {
	    			e1.printStackTrace();
	    		}
	        	
	        	DeckTask.launchDeckTask(DeckTask.TASK_TYPE_SEARCH_CARDS, mSearchCardsHandler2, new DeckTask.TaskData(
	                    new Object[] { new HashMap<>(), mRestrictOnDeck+" "+query.toString(), ((true)),  0}));//"deck:\""+allNames.get(0)+"\""

        	    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        	    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
	            
        	    return true;
	        }

	        @Override
	        public boolean onQueryTextChange(String newText) {
	            //当输入框内容改变的时候回调
	            //Log.i(TAG,"内容: " + newText);
	            return true;
	        }
	    });
		searchView.clearFocus();
		
		mDrawerList = main_clister_layout.findViewById(R.id.left_drawer);
		listHolder = main_clister_layout.findViewById(R.id.listHolder);
		listHolder.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				last_listHolder_tt = System.currentTimeMillis();
				listHolder.setVisibility(View.INVISIBLE);
				revertage = should_hide_cd1==true?1:2;
				should_hide_cd1=should_hide_cd2=false;
				counterHolder.setBackground(null);
				shelfright.setBackgroundColor(Color.parseColor("#707070"));
				return false;
			}});
		sideBar = main_clister_layout.findViewById(R.id.sideBar);
		counter =  main_clister_layout.findViewById(R.id.counter);
		counterHolder =  main_clister_layout.findViewById(R.id.counterHolder);
		shelfright = main_clister_layout.findViewById(R.id.shelfright);
		return main_clister_layout;
	}
	long last_listHolder_tt;
	private OnClickListener checkViewOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			boolean ck = ((ToggleButton)v).isChecked();
			switch(v.getId()) {
				case R.id.tg2://.ver
					if(ck)
						fastScroller.setVisibility(View.GONE);
					else
						fastScroller.setVisibility(View.VISIBLE);
					mSettings.putBoolean("hidever", ck);
				break;
				case R.id.tg1://.hor
				break;
			}
		}};
		
	Settings mSettings;
	String mRestrictOnDeck;
	String[] names;
	Long[] mDeckIds;


	int lastChecked = 0;
	private android.widget.AdapterView.OnItemClickListener chooseDeckListener = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mDrawerList.setItemChecked(lastChecked=position, true);
			//view.setSelected(true);
			mSettings.putLong("browser_deckIDSelected",mDeckIds[position]);
			try {
				JSONObject x = mCol.getConf();
				x.put("sortBackwards", true);
				x.put("sortType", "noteCrt");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			Selection.clear(); // clear selection
			mRestrictOnDeck = new StringBuilder().append("deck:\"")
            		.append(names[position])
            		.append("\"").toString();
	    	DeckTask.launchDeckTask(DeckTask.TASK_TYPE_SEARCH_CARDS, mSearchCardsHandler, new DeckTask.TaskData(
	                new Object[] { new HashMap<>(), mRestrictOnDeck, ((true)),  0}));//"deck:\""+allNames.get(0)+"\""
		}},
	changeDeckListener = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			if(lastChecked!=-1)
				mDrawerList.setItemChecked(lastChecked, true);//revert
			else
				mDrawerList.setItemChecked(position, false);//revert
			if(Selection.getRoot()==null) {
                Toast.makeText(getActivity(), R.string.noseletion, Toast.LENGTH_SHORT).show();
        		return;
        	}
			new AlertDialog.Builder(getActivity())
            .setMessage(getResources().getString(R.string.warn_move, Selection.size(),lastChecked!=-1?names[lastChecked]:"unknown",names[position]))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	ArrayList<Long> l = new ArrayList<>();
        			ArrayList<Integer> lpos = new ArrayList<>();
        			Selection.keysAndValues(l,lpos);
        			//final long[] ids = new long[l.size()];
        			List<Map<String, String>> toDelete = new ArrayList<>();

        			for(int i=0;i<l.size();i++) {//TODO 修正搜索树
        				int pos1 = l.size()-i-1;//count down
        				long SelectedID = l.get(pos1);
        				Card c = mCol.getCard(SelectedID);
        				c.setDid(mDeckIds[position]);//移动
        				c.flush();
        				toDelete.add(mCards.get(lpos.get(i)));
        			}
					mCards.removeAll(toDelete);
        			mAdapter.notifyDataSetChanged();
        			counter.setText(0+":\n"+mCards.size());
        			listHolder.setVisibility(View.INVISIBLE);
        			//mCol.remCards(ids);
                }
            }).show();
		}}
	;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);;
		mCol = CollectionHelper.getInstance().getCol(getActivity());
    	Decks mDeck = mCol.getDecks();

        mRestrictOnDeck = "";

		ArrayList<String> k = new ArrayList<>();
		ArrayList<Long> v = new ArrayList<>();
		mDeck.allNamesAndID(k, v);
		names = k.toArray(new String[] {});
		mDeckIds = v.toArray(new Long[] {});
		long deckIDSelected = mSettings.getLong("browser_deckIDSelected");
		int selectedPosition=-1;//-1代表全部
		if(deckIDSelected!=-1) {
			selectedPosition=0;
			//find pos by deck's id
	    	int counter=0;
			for(long idor:mDeckIds) {
	        	if(deckIDSelected==idor) 
	        		break;
	        	counter++;
	        }
			if(counter>=0 && counter<mDeckIds.length)
				selectedPosition=counter;
			else
				selectedPosition=0;//reset
		}
		
		
		mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(),
	            R.layout.drawer_list_item, names));


		try {
			JSONObject x = mCol.getConf();
			x.put("sortBackwards", true);
			x.put("sortType", "noteCrt");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		if(selectedPosition!=-1) {
			mRestrictOnDeck = "deck:\""+names[selectedPosition]+"\"";
			mDrawerList.setItemChecked(lastChecked=selectedPosition, true);
		}else
			lastChecked=-1;
		
    	DeckTask.launchDeckTask(DeckTask.TASK_TYPE_SEARCH_CARDS, mSearchCardsHandler, new DeckTask.TaskData(
                new Object[] { new HashMap<>(), mRestrictOnDeck, ((true)),  0}));//"deck:\""+allNames.get(0)+"\""

		main_clister_layout.post(new Runnable() {@Override  public void run() {sideBar.setRbyPos(0);}});
		
	}
  
    //设置
    void scanSettings(){}
	void dumpSettiings(){}
	

	RashMap<Long,Integer> Selection = new RashMap<>();
    //for main list 
	//参见：live down
    //参见：P.L.O.D -> float search view -> HomeAdapter
    class main_list_Adapter extends RecyclerView.Adapter<MyViewHolder> 
    {
    	//构造 
    	main_list_Adapter(){
    	}
    	
		@Override
        public int getItemCount()
        {
            return mCards.size();
        }
        
        private int mTouchItemPosition = -1;
        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        //单机
        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
        {
            this.mOnItemClickListener = mOnItemClickListener;
        }
        //长按
        public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) 
        {
            this.mOnItemLongClickListener = mOnItemLongClickListener;
        }
        
        //Create
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.card_list_item, parent,false));
            //holder.setIsRecyclable(false);
			//if Recyclable, then setText in onBindViewHolder makes textviews unSelectable.
            //details on this bug:
            //https://blog.csdn.net/huawuque183/article/details/78563977
            //issue solved(私以为).
            return holder;
        }
        //Bind
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
        	long id = Long.parseLong(mCards.get(position).get("id"), 10);
        	Card c = mCol.getCard(id);
            HashMap<String, String> asd = c._getQA(false, false);
            String tmp = asd.get("a");
            StringBuilder sb = new StringBuilder(tmp);
            int offb = tmp.lastIndexOf("</script>");
            while(offb!=-1) {
            	//hate it again,why u show script,my dear android Html class?
            	int offa = tmp.lastIndexOf("<script",offb);
            	sb.delete(offa, offb+9);
            	offb = tmp.lastIndexOf("</script>",offa);
            }
            		
            holder.webView.setText(Html.fromHtml(sb.toString()));
            
            //CMN.show(asd.get("a"));
            //holder.webView.loadDataWithBaseURL("file:///",asd.get("a"),null, "UTF-8", null);
            //holder.webView.setText("a\n\ns\nd\n"+position);
            
            //TODO: move to better position
            wahahaTextView.mR=main_clister_layout.getRootView();
            
            if((SelectionMode==SelectionMode_select) && Selection.contains(id))
            	holder.itemView.setBackgroundColor(Color.parseColor("#a04F5F6F"));//FF4081 4F7FDF
            else
        		holder.itemView.setBackgroundColor(Color.parseColor("#00a0f0f0"));//aaa0f0f0
            
            

            if(inSearch && mSearchResTree!=null && mSearchResTree.contains(position))
            	holder.webView.setBackgroundResource(R.drawable.xuxian2);
            else
            	holder.webView.setBackground(null);
            
            if(false)
            holder.p.setOnTouchListener(new OnTouchListener() {
            	
				@Override
				public boolean onTouch(View v,MotionEvent e) {
					return false;
			}});
            
            if(SelectionMode==SelectionMode_select) {
	            holder.p.setOnClickListener(new OnClickListener() {
	
					@Override
					public void onClick(View v) {
						mOnItemClickListener.onItemClick(holder.itemView, position);
				}}); 
	            holder.p.setVisibility(View.VISIBLE);
            }else{
	            holder.p.setVisibility(View.GONE);
            }

        }
      
    } 
    
    
    
    
	public interface OnItemClickListener{
        void onItemClick(View view,int position);}
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);}

	class MyViewHolder extends ViewHolder
    {
    	TextView webView;
    	View p;
        public MyViewHolder(View view)
        {
        	super(view);
        	p = view.findViewById(R.id.p);
            webView = (TextView) view.findViewById(R.id.text1);
            webView.setTextIsSelectable(true);
        }
    }

    public void showProgressBar() {
        ProgressBar progressBar = (ProgressBar) main_clister_layout.findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    public void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) main_clister_layout.findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
    Runnable doAfterSearch;
	private DeckTask.TaskListener mSearchCardsHandler = new DeckTask.TaskListener() {
	        @Override
	        public void onProgressUpdate(TaskData... values) {
	            if (values[0] != null) {
	                mCards = values[0].getCards();
	                updateList();
	                //CMN.show(mCards.size()+":"+mCards.get(0).keySet().size());
	                
	                //CMN.show(asd.get("q"));
	            }
	            counter.setText(0+":\n"+mCards.size());
	            if(doAfterSearch!=null)
	            	doAfterSearch.run();
	        }


	        @Override
	        public void onPreExecute() {
	        	showProgressBar();
	        }


	        @Override
	        public void onPostExecute(TaskData result) {            
	            if (result != null && mCards != null) {
	                Log.i("","CardBrowser:: Completed doInBackgroundSearchCards Successfuly");
	                hideProgressBar();
	                updateList();
	            }
	        }
	        
	        @Override
	        public void onCancelled(){
	        	Log.i("","doInBackgroundSearchCards onCancelled() called");
	        }
	    };

	 RashSet<Integer> mSearchResTree = new RashSet<Integer>();
	 private DeckTask.TaskListener mSearchCardsHandler2 = new DeckTask.TaskListener() {
	        @Override
	        public void onProgressUpdate(TaskData... values) {
	            if (values[0] != null) {
	                List<Map<String, String>> Cards = values[0].getCards();
					mSearchResTree.clear();
	                if(Cards.size()==0)
	                	return;
	                long t = System.currentTimeMillis();
					boolean s;
					int finder = 0;
					String idToMatch = Cards.get(finder).get("id");
					for(int i=0;i<mCards.size();i++) {
					 //Card c = mCol.getCard(Long.parseLong(mCards.get(i).get("id"), 10));
						if(mCards.get(i).get("id").equals(idToMatch)) {
							mSearchResTree.put(i);
							if(++finder>Cards.size()-1)
								break;
							idToMatch = Cards.get(finder).get("id");
						}	
					}
					//TODO need some check?
					lm.scrollToPositionWithOffset(mSearchResTree.minimum(), toolbar.getHeight());
					fastScroller.setTree(mSearchResTree);
					fastScroller.timeLength = mCards.size();
					fastScroller.invalidate();
					//CMN.show(""+(System.currentTimeMillis()-t));
	                //CMN.show(mCards.size()+":"+mCards.get(0).keySet().size());
	                Toast.makeText(getActivity(), "结果数目："+Cards.size(), Toast.LENGTH_SHORT).show();
	                //CMN.show(asd.get("q"));
	            }
	            
	            
	        }


	        @Override
	        public void onPreExecute() {
	        	showProgressBar();
	        }


	        @Override
	        public void onPostExecute(TaskData result) {            
	            if (result != null && mCards != null) {
	                Log.i("","CardBrowser:: Completed doInBackgroundSearchCards Successfuly");
	                hideProgressBar();
	                updateList();
	            }
	        }
	        
	        @Override
	        public void onCancelled(){
	        	Log.i("","doInBackgroundSearchCards onCancelled() called");
	        }
	    };	    
	    
	    
	    
		protected void updateList() {
			mAdapter.notifyDataSetChanged();			
		}

		
		int SelectionMode;
		boolean inSearch = true;
		final int SelectionMode_pan=0;
		final int SelectionMode_select=1;
		final int SelectionMode_preview=2;
		boolean inDeckListChooser = false;
		/*神之显/隐体系*/
		int revertage=0;
		boolean should_hide_cd1,should_hide_cd2;
		/*hide/show system by a God*/
		@Override
		public void onClick(final View v) {
			int pos;
			switch(v.getId()) {
				case R.id.tools0://pan
					SelectionMode=SelectionMode_pan;
					sideBar.setRbyView(v);
					mAdapter.notifyDataSetChanged();
				break;	
				case R.id.tools1://选择
					SelectionMode=SelectionMode_select;
					sideBar.setRbyView(v);
					mAdapter.notifyDataSetChanged();
				break;
				case R.id.tools2://预览
					SelectionMode=SelectionMode_preview;
					sideBar.setRbyView(v);
					mAdapter.notifyDataSetChanged();
				break;
				case R.id.tools3://删除
					if(Selection.getRoot()==null) {
                        Toast.makeText(getActivity(), R.string.noseletion, Toast.LENGTH_SHORT).show();
                		return;
                	}
					//no possible ways to detect keyboard hiden,when you hide it by the top-right button. 
					//hate hate hate 
					//strange strange strange 
					//dmAroid dmAroid dmAroid
					//v.requestFocus();//dis-focus searchView
					//boolean hasKeyBoard = imm.isActive(searchView.findViewById(R.id.search_src_text));
					//CMN.show(""+hasKeyBoard);
					//if(!hasKeyBoard) searchView.clearFocus();//give some respect to the keyboard shown
					//卧槽草泥马的 AlertDialog 不要碰我的输入法 不要碰我的输入法 不要碰我的输入法 
					//算了。。
					final boolean hasKeyBoard = imm.hideSoftInputFromWindow(searchView.getWindowToken(),0);
					//CMN.show(""+hasKeyBoard);
					if(!hasKeyBoard) searchView.clearFocus();
					new AlertDialog.Builder(getActivity())
                    .setMessage(getResources().getString(R.string.warn_delete, Selection.size()))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	ArrayList<Long> l = new ArrayList<>();
                			ArrayList<Integer> lpos = new ArrayList<>();
                			Selection.keysAndValues(l,lpos);
        					final long[] ids = new long[l.size()];
        					List<Map<String, String>> toDelete = new ArrayList<>();
        					for(int i=0;i<l.size();i++) {//TODO 修正搜索树
        						//int pos = l.size()-i-1;//count down
        						long SelectedID = l.get(i);
        						ids[i] = SelectedID;
        						//mCards.remove(lpos.get(i));
        						toDelete.add(mCards.get(lpos.get(i)));
        						Selection.remove(SelectedID,null);
        					}
        					mCards.removeAll(toDelete);
        					mAdapter.notifyDataSetChanged();
        					counter.setText(0+":\n"+mCards.size());
        					mCol.remCards(ids);
        					lv.post(new Runnable() {

								@Override
								public void run() {
		        					
								}});
                        	return;
                        }
                    }).setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							if(hasKeyBoard) {
								imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
							}
						}}).show();
				break;				
				case R.id.choosed://choose deck
					if(System.currentTimeMillis()-last_listHolder_tt<150 && revertage==1) {//too fast from last hide
						should_hide_cd1=true;
					}
					mDrawerList.setOnItemClickListener(chooseDeckListener);
					listHolder.setVisibility((listHolder.getVisibility()!=View.VISIBLE && !should_hide_cd1)?View.VISIBLE:View.GONE);
					should_hide_cd1 = listHolder.getVisibility()==View.VISIBLE;
					if(should_hide_cd1) {
						should_hide_cd2=false;
						counterHolder.setBackground(null);
						shelfright.setBackgroundColor(Color.parseColor("#707070"));
					}
				break;
				case R.id.changed://change deck
					if(System.currentTimeMillis()-last_listHolder_tt<150 && revertage==2) {//too fast from last hide
						should_hide_cd2=true;
					}
					mDrawerList.setOnItemClickListener(changeDeckListener);
					listHolder.setVisibility((listHolder.getVisibility()!=View.VISIBLE && !should_hide_cd2)?View.VISIBLE:View.GONE);
					should_hide_cd2 = listHolder.getVisibility()==View.VISIBLE;
					if(should_hide_cd2) {
						should_hide_cd1=false;
						counterHolder.setBackground(getResources().getDrawable(R.drawable.movec2));
						shelfright.setBackgroundColor(Color.parseColor("#ff0000"));
					}else {
						counterHolder.setBackground(null);
						shelfright.setBackgroundColor(Color.parseColor("#707070"));
					}
				break;
				case R.id.side2:
					
				break;
				case R.id.search:
					toolbar.setVisibility(toolbar.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
					if(toolbar.getVisibility()==View.VISIBLE) {
						inSearch=true;
						searchView.requestFocus();
						imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
					}else {
						//automatically hides im ,GREAT!
						inSearch=false;
					}
					mAdapter.notifyDataSetChanged();//TODO min DB IO
					fastScroller.showBoolMark(inSearch);
				break;
				case R.id.lst:
					if(mSearchResTree==null || mSearchResTree.getRoot()==null) return;
					lv.scrollBy(0, +toolbar.getHeight());
					pos = mSearchResTree.xxing_samsara(lm.findFirstVisibleItemPosition()).getKey();
					lm.scrollToPositionWithOffset(pos, toolbar.getHeight());
				break;
				case R.id.nxt:
					if(mSearchResTree==null || mSearchResTree.getRoot()==null) return;
					lv.scrollBy(0, +toolbar.getHeight());
					pos = mSearchResTree.sxing_samsara(lm.findFirstVisibleItemPosition()).getKey();
					lm.scrollToPositionWithOffset(pos, toolbar.getHeight());
				break;
			}
		}

		//lazy strategy. reuse as much as I can.
		PopupWindow sharePopup;
		int menuResId = -1;
		int onclickBase=0;
		int lastPopupId=-1;
		ArrayAdaptermy<String> shareListAda;
		void initPopup(){
			View view = getActivity().getLayoutInflater().inflate(R.layout.popup_more_tools, null);  
			sharePopup = new PopupWindow(view,   
                    (int)(160 * getResources().getDisplayMetrics().density), LayoutParams.WRAP_CONTENT);  
			
			sharePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
			}});
			final ListView shareList = (ListView) view.findViewById(R.id.share_list); 
			shareListAda = new ArrayAdaptermy<String>(getActivity(),
		            R.layout.popup_list_item);
			shareList.setAdapter(shareListAda);
			shareList.setOnItemClickListener(new ListView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					switch(position+onclickBase) {//处理点击事件
						case 10://selection t
							//CMN.show("select all!");
							//TODO develop more efficient and elegant algorithm.
							for(int i=0;i<mCards.size();i++)
								Selection.put(Long.parseLong(mCards.get(i).get("id"), 10),i);
							counter.setText(Selection.size()+":\n"+mCards.size());
							mAdapter.notifyDataSetChanged();
						break;
						case 11:
							Selection.clear();
							counter.setText(Selection.size()+":\n"+mCards.size());
							mAdapter.notifyDataSetChanged();
						break;
						case 30://show all decks
							mSettings.putLong("lastChecked",lastChecked=-1);
							int iii = mDrawerList.getCheckedItemPosition();
							if(iii>=0)
								mDrawerList.setItemChecked(iii, false);
							if(!mRestrictOnDeck.equals(""));{
								mRestrictOnDeck="";
								DeckTask.launchDeckTask(DeckTask.TASK_TYPE_SEARCH_CARDS, mSearchCardsHandler, new DeckTask.TaskData(
						                new Object[] { new HashMap<>(), mRestrictOnDeck, ((true)),  0}));
							}
						break;
						case 31://刷新
							DeckTask.launchDeckTask(DeckTask.TASK_TYPE_SEARCH_CARDS, mSearchCardsHandler, new DeckTask.TaskData(
					                new Object[] { new HashMap<>(), mRestrictOnDeck, ((true)),  0}));
						break;
						case 40://更新书签
							mSettings.putLong("b"+0, Long.valueOf(mCards.get(lm.findFirstVisibleItemPosition()).get("id")));
							Toast.makeText(getActivity(),R.string.bookmarkupdated, Toast.LENGTH_SHORT).show();
						break;
						case 41://跳转书签
							final long BooKMarkCid = mSettings.getLong("b"+0);
							long did = mCol.findCardById(BooKMarkCid);
							doAfterSearch = new Runnable() {
								@Override
								public void run() {
									for(int i=0;i<mCards.size();i++) {
										if(mCards.get(i).get("id").equals(""+BooKMarkCid)) {
											lm.scrollToPositionWithOffset(i,0);
											break;
										}
									}
									doAfterSearch=null;
								}};
							if(did!=-1) {
								if(lastChecked!=-1 && mDeckIds[lastChecked]!=did) {//if in wrong deck
									int selectedPosition=-1;//-1代表全部
									//find pos by deck's id
							    	int counter=0;
									for(long idor:mDeckIds) {
							        	if(did==idor) 
							        		break;
							        	counter++;
							        }
									if(counter>=0 && counter<mDeckIds.length) {
										selectedPosition=counter;
										chooseDeckListener.onItemClick(null, null, selectedPosition, did);
										mDrawerList.setItemChecked(selectedPosition, true);
									}
								}else {
									//then jump
									doAfterSearch.run();
								}
							}
						break;
						case 50://严格模式
							fastScroller.setConservativeScroll(true);
							mSettings.putBoolean("strictscroll", true);
						break;
						case 51://宽松模式
							fastScroller.setConservativeScroll(false);
							mSettings.putBoolean("strictscroll", false);
						break;
					}
					
					getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
						@Override
						public void run() {
							sharePopup.dismiss();								
						}
					}, 150);
				}});
		}
		
		@Override
		public boolean onLongClick(final View v) {
			menuResId = -1;
			onclickBase=0;
			boolean interceptClick = false;
			switch(v.getId()) {
				case R.id.tools1:
					menuResId=R.array.selection_tweak;
					onclickBase=10;
				break;
				case R.id.choosed:
					menuResId=R.array.choosed_tweak;
					onclickBase=30;
					interceptClick=true;
				break;
				case R.id.bookmark0:
					menuResId=R.array.bookmark0_tweak;
					onclickBase=40;
					interceptClick=true;
				break;
				case R.id.tg2:
					menuResId=R.array.ver_tweak;
					onclickBase=50;
					interceptClick=true;
				break;
			}
			if(sharePopup!=null && sharePopup.isShowing()) {
				sharePopup.dismiss();
				return true;
			}
			if(sharePopup==null)
				initPopup();
			
			if(lastPopupId!=v.getId()) {//need re-populate
				shareListAda.setArray(Arrays.asList(getResources().getStringArray(menuResId)));
				lastPopupId=v.getId();
			}
			
			sharePopup.setFocusable(false);  
	        sharePopup.setOutsideTouchable(true);  
	        sharePopup.setBackgroundDrawable(null);
	        sharePopup.showAsDropDown(v, v.getWidth(), -v.getHeight());  
	        return interceptClick;
		}
		


    
    
}
