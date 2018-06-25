package com.mmjang.ankihelper.plan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.knziha.ankislicer.ui.MyApplication;
import com.knziha.ankislicer.R;
import com.mmjang.ankihelper.plan.OutputPlan;
import com.mmjang.ankihelper.util.DialogUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PlansManagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans_manager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_plan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getAnkiDroid().isAnkiDroidRunning()) {
                    Intent intent = new Intent(PlansManagerActivity.this, PlanEditorActivity.class);
                    startActivity(intent);
                } else {
                    DialogUtil.showStartAnkiDialog(PlansManagerActivity.this);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //refreshPlanList();
        
        RecyclerView planList = (RecyclerView) findViewById(R.id.plan_list);
        planList.setLayoutManager(new LinearLayoutManager(this));

        List<OutputPlan> plans = DataSupport.findAll(OutputPlan.class);
        PlansAdapter pa = new PlansAdapter(PlansManagerActivity.this, plans);
        planList.setAdapter(pa);
    }
    
}
