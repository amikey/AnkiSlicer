package com.fenwjian.sdcardutil;

import android.support.annotation.ColorInt;
import android.text.TextPaint;

import java.lang.reflect.Method;

/**
 * Underline Span with color
 */
public class ColoredUnderlineSpan extends android.text.style.UnderlineSpan {

    private int underlineColor;
    private float thickness;

    public ColoredUnderlineSpan(@ColorInt int underlineColor) {
        super();
        this.underlineColor = underlineColor;
        this.thickness = 8.f;
    }

    public ColoredUnderlineSpan(@ColorInt int underlineColor,float thickness) {
        super();
        this.underlineColor = underlineColor;
        this.thickness = thickness;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        try {
            final Method method = TextPaint.class.getMethod("setUnderlineText",new Class[]{int.class,float.class});
            method.invoke(tp, new Object[] {underlineColor, thickness});
        } catch (final Exception e) {
            //tp.setUnderlineText(true);
            e.printStackTrace();
        }

    }


}