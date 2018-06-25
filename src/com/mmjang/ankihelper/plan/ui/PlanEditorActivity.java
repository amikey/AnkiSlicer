package com.mmjang.ankihelper.plan.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.litepal.crud.DataSupport;

import com.knziha.ankislicer.R;
import com.knziha.ankislicer.ui.CMN;
import com.knziha.ankislicer.ui.MyApplication;
import com.knziha.ankislicer.ui.PopupActivity;
import com.mmjang.ankihelper.anki.AnkiDroidHelper;
import com.mmjang.ankihelper.data.dict.IDictionary;
import com.mmjang.ankihelper.plan.OutputPlan;
import com.mmjang.ankihelper.programData.Settings;
import com.mmjang.ankihelper.util.Constant;
import com.mmjang.ankihelper.util.Utils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlanEditorActivity extends AppCompatActivity {

    private String planNameToEdit;
    private AnkiDroidHelper mAnkiDroid;
    private OutputPlan planForEdit;
    private Map<Long, String> deckList;
    private Map<Long, String> modelList;
    //private List<IDictionary> dictionaryList;
    //private List<FieldsMapItem> fieldsMapItemList;
    private IDictionary currentDictionary;
    private long currentDeckId;
    private long currentModelId;
    //views
    private EditText planNameEditText;
    private Spinner deckSpinner;
    private Spinner modelSpinner;
    private RecyclerView fieldsSpinnersContainer;
    
    View tv_choose_dictionary;
    String[] mdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initAnkiApi();
        setViewMember();
        handleIntent();
        loadDecksAndModels();
    	mdList = planForEdit.getMdicts();
        populateDictionary();
        populateDecksAndModels();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_plan_editor_menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_plan_edit:
                if (savePlan()) {
                    finish();
                }
            break;
            case android.R.id.home:
            	finish();
        	break;
        }
        return true;
    }

    private void setViewMember() {
    	tv_choose_dictionary = findViewById(R.id.tv_choose_dictionary);
        planNameEditText = (EditText) findViewById(R.id.text_edit_plan_name);
        deckSpinner = (Spinner) findViewById(R.id.deck_spinner);
        modelSpinner = (Spinner) findViewById(R.id.model_spinner);
        fieldsSpinnersContainer = (RecyclerView) findViewById(R.id.recycler_view_fields_map);
        tv_choose_dictionary.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent DElauncher = new Intent(PlanEditorActivity.this, dict_manager_activity.class);
				DElauncher.putExtra("extra", planForEdit.getMdicts_());
				startActivityForResult(DElauncher,110);
			}});
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode==110) {
    		ArrayList<String> l = data.getStringArrayListExtra("extra");
    		planForEdit.setMdicts(l);
    		mdList = l.toArray(new String[] {});
    		populateDictionary();//re-populate dicts
    	}
    }
    
    private void initAnkiApi() {
        if (mAnkiDroid == null) {
            mAnkiDroid = new AnkiDroidHelper(this);
        }
        if (!AnkiDroidHelper.isApiAvailable(MyApplication.getContext())) {
            Toast.makeText(this, R.string.api_not_available_message, Toast.LENGTH_LONG).show();
        }

        if (mAnkiDroid.shouldRequestPermission()) {
            mAnkiDroid.requestPermission(this, 0);
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();
            if (action != null && action.equals(Intent.ACTION_SEND)) {
                planNameToEdit = intent.getStringExtra(Intent.EXTRA_TEXT);
                List<OutputPlan> re = DataSupport.where("planName = ?", planNameToEdit).find(OutputPlan.class);
                if (!re.isEmpty()) {
                    planForEdit = re.get(0);
                    planNameEditText.setText(planNameToEdit);
					if(planNameToEdit.equals(Settings.getInstance(this).getLastSelectedPlanName()))
                    	needUpdateDisk=true;
                }
            }else
            	planForEdit = new OutputPlan();
        }
    }

    private void loadDecksAndModels() {
        deckList = Utils.hashMap2LinkedHashMap(mAnkiDroid.getApi().getDeckList());
        modelList = Utils.hashMap2LinkedHashMap(mAnkiDroid.getApi().getModelList());
    }

    private void populateDictionary() {
    	View l = findViewById(R.id.choosed);
    	final ViewGroup lv = (ViewGroup) l.findViewById(R.id.lv);
    	lv.removeAllViews();
    	int dictCount=0;
    	boolean doNotPopulate = (mdList.length==1) && (mdList[0].equals(""));
    	if(!doNotPopulate)
    	for(String dictI:mdList) {
        	ViewGroup ViewTmp = (ViewGroup) getLayoutInflater().inflate(R.layout.list_item_dicts, null);
        	lv.addView(ViewTmp);
        	final TextView title = ((TextView)ViewTmp.findViewById(R.id.text1));
			title.setText(new File(dictI).getName());
			if(dictCount==planForEdit.homeDictIdx)
        		title.setTextColor(Color.parseColor("#ff0000"));

			ViewTmp.setTag(dictCount++);
			ViewTmp.setOnClickListener(new OnClickListener() {//TODO optimize
				@Override
				public void onClick(View v) {//选择词典
					int NewIdx = (int) v.getTag();
	        		((TextView)lv.getChildAt(planForEdit.homeDictIdx).findViewById(R.id.text1)).setTextColor(Color.parseColor("#000000"));
	        		title.setTextColor(Color.parseColor("#ff0000"));
	        		planForEdit.homeDictIdx = NewIdx;					
			}});
    	}
    	
    }

    private void populateDecksAndModels() {
        ArrayAdapter<String> deckSpinnerAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, Utils.getMapValueArray(deckList));
        deckSpinner.setAdapter(deckSpinnerAdapter);
        ArrayAdapter<String> modelSpinnerAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, Utils.getMapValueArray(modelList));
        modelSpinner.setAdapter(modelSpinnerAdapter);

        if (planForEdit != null) {
            long savedDeckId = planForEdit.getOutputDeckId();
            long savedModelId = planForEdit.getOutputModelId();
            //int i = 0;
            long[] deckIdList = Utils.getMapKeyArray(deckList);
            //int deckPos = Arrays.asList(deckIdList).indexOf(savedDeckId);
            int deckPos = Utils.getArrayIndex(deckIdList, savedDeckId);
            if (deckPos == -1) {
                deckPos = 0;
            }
            currentDeckId = deckIdList[deckPos];
            deckSpinner.setSelection(deckPos);

            long[] modelIdList = Utils.getMapKeyArray(modelList);
            //int modelPos = Arrays.asList(modelIdList).indexOf(savedModelId);
            int modelPos = Utils.getArrayIndex(modelIdList, savedModelId);
            if (modelPos == -1) {
                modelPos = 0;
            }
            currentModelId = modelIdList[modelPos];
            modelSpinner.setSelection(modelPos);

            refreshFieldSpinners();
        } else {
            currentDeckId = Utils.getMapKeyArray(deckList)[0];
            currentModelId = Utils.getMapKeyArray(modelList)[0];
            refreshFieldSpinners();
        }

        modelSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentModelId = Utils.getMapKeyArray(modelList)[position];
                        refreshFieldSpinners();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        deckSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentDeckId = Utils.getMapKeyArray(deckList)[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

    }
    FieldMapSpinnerListAdapter balabaAda;
    private void refreshFieldSpinners() {
    	balabaAda = new FieldMapSpinnerListAdapter(PlanEditorActivity.this);
        String[] fields = mAnkiDroid.getApi().getFieldList(currentModelId);
        
        //重置 映射
        int[] selections = new int[fields.length];
        balabaAda.setFields(fields);
        String[] fieldsMapper = planForEdit.getFieldsMap();
        String[] allElements = Constant.SHARED_EXPORT_ELEMENTS;//Utils.concatenate(sharedElements, dictionaryElements);
        //fieldsMapItemList = new ArrayList<>();
        //if edit, then set spinner initial position

        
        
        //if (planForEdit != null && currentModelId == planForEdit.getOutputModelId()) {
        int FIcounter = 0;
        //CMN.show(fieldsMapper.length+":"+fields.length);
        if(fieldsMapper.length==fields.length)//输出模板字段数 与  plan内设字段数 理应一致
        for (String fi : fields) {//遍历所有目标 输出模板 的字段  注意 fi 并无软用
        	int pos = 0;
        	String mapper = fieldsMapper[FIcounter];
            if (!mapper.equals(" ")) {
            	//if(mapper.equals("")) mapper = fi;
            	pos = Arrays.asList(allElements).indexOf(mapper);
            	//CMN.show(pos+":"+mapper+":"+fi);
            }
            //if (pos == -1) pos = 0;
            
            selections[FIcounter++] = pos;
        }
        balabaAda.setFieldSelections(selections);
        //}
        fieldsSpinnersContainer.setLayoutManager(new LinearLayoutManager(this));
        fieldsSpinnersContainer.setAdapter(balabaAda);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(MainActivity.this, R.string.permission_granted, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
        }
    }

    private boolean savePlan() {
        String planName = planNameEditText.getText().toString().trim();
        if (planName.isEmpty()) {
            Toast.makeText(this, R.string.str_plan_name_should_not_be_blank, Toast.LENGTH_SHORT).show();
            return false;
        }
        
        //if when edit an exiting plan, and the user chang the plan name to another existing plan name
        if (!planName.equals(planNameToEdit)) {
            //if name conflicts, toast.
            List<OutputPlan> rel = DataSupport.where("planName = ?", planName).find(OutputPlan.class);
            if (!rel.isEmpty()) {
                Toast.makeText(this, R.string.plan_already_exists, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        planForEdit.setPlanName(planName);
        //plan.setDictionaryKey(currentDictionary.getDictionaryName());
        planForEdit.setOutputDeckId(currentDeckId);
        planForEdit.setOutputModelId(currentModelId);

        //重新设置字段映射
        String[] map = new String[mAnkiDroid.getApi().getFieldList(currentModelId).length];
        
        for (int i=0;i<map.length;i++) {
        	//CMN.show("Selections:"+balabaAda.Selections[i]);
        	if(balabaAda.Selections[i]==0)
        		map[i]=" ";
    		else
    			map[i] = Constant.SHARED_EXPORT_ELEMENTS[balabaAda.Selections[i]];
        }

           // Toast.makeText(this, R.string.save_plan_error_all_blank, Toast.LENGTH_SHORT).show();
           // return false;

        planForEdit.setFieldsMap(map);//.toArray(new String[0])
        planForEdit.save();
        //CMN.show("plan_saved!");
        if(needUpdateDisk) {
        	//CMN.show("disk_saved!");
        	planForEdit.saveToDisk();
        	Settings.getInstance(this).setLastSelectedPlanName(planName);
        }	
        return true;
    }

    boolean needUpdateDisk;
}
