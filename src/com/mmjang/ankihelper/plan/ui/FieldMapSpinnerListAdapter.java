package com.mmjang.ankihelper.plan.ui;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.knziha.ankislicer.R;
import com.mmjang.ankihelper.util.Constant;

import java.util.List;

/**
 * Created by liao on 2017/4/28.
 */

public class FieldMapSpinnerListAdapter
        extends RecyclerView.Adapter<FieldMapSpinnerListAdapter.ViewHolder> {
    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView exportElementName;
        Spinner fieldsSpinner;

        public ViewHolder(View view) {
            super(view);
            exportElementName = (TextView) view.findViewById(R.id.tv_export_element);
            fieldsSpinner = (Spinner) view.findViewById(R.id.spinner_fields);
        }
    }

    public FieldMapSpinnerListAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.field_map_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.exportElementName.setText(Fields[position]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                mActivity,
                R.layout.support_simple_spinner_dropdown_item,
                Constant.SHARED_EXPORT_ELEMENTS
        );
        holder.fieldsSpinner.setAdapter(arrayAdapter);
        holder.fieldsSpinner.setSelection(Selections[position]);
        holder.fieldsSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int posSelected, long id) {
                    	Selections[position] = posSelected;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return Fields.length;
    }
    String[] Fields;
    int[] Selections;
	public void setFields(String[] fields) {
		Fields = fields;
	}

	public void setFieldSelections(int[] selections) {
		Selections = selections;
	}
}
