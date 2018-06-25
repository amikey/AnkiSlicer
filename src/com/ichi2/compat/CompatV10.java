
package com.ichi2.compat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RemoteViews;


import com.ichi2.anki.AbstractFlashcardViewer;
import com.ichi2.anki.ReadText;
import com.knziha.ankislicer.ui.LauncherActivity;

import java.io.File;

import io.requery.android.database.sqlite.SQLiteDatabase;
import timber.log.Timber;

/** Implementation of {@link Compat} for SDK level 7 */
@TargetApi(10)
public class CompatV10 implements Compat {
    protected static final int FULLSCREEN_ALL_GONE = 2;

    /*
     *  Return the input string in a form suitable for display on a HTML page. Replace “<”, “>”, “&”, “"” and “'” with
     *  HTML entities.
     *
     * @param txt Text to be cleaned.
     * @return The input text, with HTML tags and entities escaped.
    */
    public String detagged(String txt) {
        if (txt == null) {
            return "";
        }
        return txt.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace(
                "'", "&#39;");
    }

    public void setTtsOnUtteranceProgressListener(TextToSpeech tts) {
        tts.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
            @Override
            public void onUtteranceCompleted(String utteranceId) {
                if (ReadText.sTextQueue.size() > 0) {
                    String[] text = ReadText.sTextQueue.remove(0);
                    ReadText.speak(text[0], text[1], TextToSpeech.QUEUE_FLUSH);
                }
            }
        });
    }

    public void disableDatabaseWriteAheadLogging(SQLiteDatabase db) {
        // We've never used WAL mode on Gingerbread so don't need to do anything here
    }


    // Below API level 12, file scheme pages are not restricted, so no adjustment is needed.
    public void enableCookiesForFileSchemePages() {
        Timber.w("Cookies not supported in API version %d", CompatHelper.getSdkVersion());
    }

    // CookieSyncManager is need to be initialized before use.
    // Note: CookieSyncManager is deprecated since API level 21, but still need to be used here.
    public void prepareWebViewCookies(Context context) {
        CookieSyncManager.createInstance(context);
    }

    // A data of cookies may be lost when an application exists just after it was written.
    // Below API level 21, this problem can be solved by using CookieSyncManager.sync().
    // Note: CookieSyncManager is deprecated since API level 21, but still need to be used here.
    public void flushWebViewCookies() {
        CookieSyncManager.getInstance().sync();
    }

    // Below API level 17, there is no simple way to enable the auto play feature of HTML media elements.
    public void setHTML5MediaAutoPlay(WebSettings webSettings, Boolean allow) {

    }

    // Below API level 16, widget dimensions cannot be adjusted
    public void updateWidgetDimensions(Context context, RemoteViews updateViews, Class<?> cls) {

    }

    /**
     * Pre-honeycomb just completely boot back to the DeckPicker
     */
    public void restartActivityInvalidateBackstack(LauncherActivity activity) {
        Timber.i("LauncherActivity -- restartActivityInvalidateBackstack()");
        //TODO: Find a way to recreate the backstack even pre-Honeycomb
        
    }

    public void setFullScreen(AbstractFlashcardViewer a) {
    }


    public void setSelectableBackground(View view) {}

    public void openUrl(LauncherActivity activity, Uri uri) {}

    /**
     * FloatingActionsMenu has a bug on Android 2.3 where the collapsed menu items can still be clicked,
     * so we revert to showing a ContextMenu below API 14.
     * @param activity DeckPicker instance that we can run callbacks on
     */

    @Override
    public Intent getPreferenceSubscreenIntent(Context context, String subscreen) {
        // We're using "legacy preference headers" below API 11
        return  new Intent();
    }

    @Override
    public void setStatusBarColor(Window window, int color) {
        // Not settable before API 21 so do nothing
    }

    @Override
    public boolean isImmersiveSystemUiVisible(LauncherActivity activity) {
        return false;   // Immersive mode introduced in KitKat
    }

    @Override
    public boolean deleteDatabase(File db) {
        return db.delete();
    }

    @Override
    public Uri getExportUri(Context context, File file) {
        return Uri.fromFile(file);
    }
}