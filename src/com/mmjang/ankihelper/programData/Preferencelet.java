/*
 * Copyright (C) 2017 Jared Rummler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmjang.ankihelper.programData;

import com.knziha.ankislicer.ui.CMN;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.Preference;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * A Preference to select a color
 */
public class Preferencelet extends Preference   {


  public Preferencelet(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public Preferencelet(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    setPersistent(true);
  }

  @Override protected void onClick() {
    super.onClick();
    CMN.show(""+this.getSummary());
  }

  @Override protected void onAttachedToActivity() {
    super.onAttachedToActivity();

    
  }

  @Override protected void onBindView(View view) {
    super.onBindView(view);
   // ColorPanelView preview = (ColorPanelView) view.findViewById(R.id.cpv_preference_preview_color_panel);
   //   preview.setColor(color);
  }

  @Override protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {

  }

  @Override protected Object onGetDefaultValue(TypedArray a, int index) {
    return a.getInteger(index, Color.BLACK);
  }


  /**
   * Set the new color
   *
   * @param color
   *     The newly selected color
   */
  public void saveValue(@ColorInt int color) {
    //this.color = color;
    //persistInt(this.color);
    notifyChanged();
    callChangeListener(color);
  }



  /**
   * The tag used for the {@link ColorPickerDialog}.
   *
   * @return The tag
   */
  public String getFragmentTag() {
    return "color_" + getKey();
  }


}
