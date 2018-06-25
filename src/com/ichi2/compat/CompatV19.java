
package com.ichi2.compat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ichi2.anki.AbstractFlashcardViewer;
import com.knziha.ankislicer.ui.LauncherActivity;

/** Implementation of {@link Compat} for SDK level 19 */
@TargetApi(19)
public class CompatV19 extends CompatV17 implements Compat {
    private static final int ANIMATION_DURATION = 200;
    private static final float TRANSPARENCY = 0.90f;

    @Override
    public void setFullScreen(final AbstractFlashcardViewer a) {}

    private void showViewWithAnimation(final View view) {
        view.setAlpha(0.0f);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(TRANSPARENCY).setDuration(ANIMATION_DURATION).setListener(null);
    }

    private void hideViewWithAnimation(final View view) {
        view.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean isImmersiveSystemUiVisible(LauncherActivity activity) {
        return (activity.getWindow().getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    }
}