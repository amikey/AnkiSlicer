
package com.ichi2.compat;

import com.knziha.ankislicer.ui.LauncherActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.TaskStackBuilder;

import io.requery.android.database.sqlite.SQLiteDatabase;
import timber.log.Timber;

/** Implementation of {@link Compat} for SDK level 11 (Honeycomb) */
@TargetApi(11)
public class CompatV11 extends CompatV10 implements Compat {

    /**
     * Restart the activity and discard old backstack, creating it new from the heirarchy in the manifest
     */
    public void restartActivityInvalidateBackstack(LauncherActivity activity) {
        Timber.i("AnkiActivity -- restartActivityInvalidateBackstack()");
        Intent intent = new Intent();
        intent.setClass(activity, activity.getClass());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
        stackBuilder.addNextIntentWithParentStack(intent);
        stackBuilder.startActivities(new Bundle());
        activity.finish();
    }

    @Override
    public void disableDatabaseWriteAheadLogging(SQLiteDatabase db) {
        // disableWriteAheadLogging() method only available from API 16
        db.rawQuery("PRAGMA journal_mode = DELETE", null);
    }

    @Override
    public Intent getPreferenceSubscreenIntent(Context context, String subscreen) {
        Intent i = new Intent(context, LauncherActivity.class);
        i.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, "com.ichi2.anki.Preferences$SettingsFragment");
        Bundle extras = new Bundle();
        extras.putString("subscreen", subscreen);
        i.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT_ARGUMENTS, extras);
        i.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
        return i;
    }
}
