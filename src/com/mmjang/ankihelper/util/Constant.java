package com.mmjang.ankihelper.util;

/**
 * Created by liao on 2017/4/27.
 */

public class Constant {
    public static final String[] SHARED_EXPORT_ELEMENTS = new String[]{
            "默认值",
            "单词",
            "音标",
            "释义",
            "笔记",
            "例句",
            "url",
            "有道美式发音",
            "有道英式发音",
            //"FBReader跳转链接"
    };

    public static String[] getSharedExportElements() {
        return SHARED_EXPORT_ELEMENTS;
    }

    public static final String INTENT_ANKIHELPER_TARGET_WORD = "com.mmjang.ankihelper.target_word";
    public static final String INTENT_ANKIHELPER_TARGET_URL = "com.mmjang.ankihelper.url";
    public static final String INTENT_ANKIHELPER_NOTE_ID = "com.mmjang.ankihelper.note_id";
    public static final String INTENT_ANKIHELPER_UPDATE_ACTION = "com.mmjang.ankihelper.note_update_action";//replace;append;
    public static final String INTENT_ANKIHELPER_PLAN_NAME = "com.mmjang.ankihelper.plan_name";
    public static final String INTENT_ANKIHELPER_FBREADER_BOOKMARK_ID = "com.mmjang.ankihelper.fbreader.bookmark.id";
    public static final String ANKI_PACKAGE_NAME = "com.ichi2.anki";
    public static final String FBREADER_URL_TMPL = "<a href=\"intent:#Intent;action=android.fbreader.action.VIEW;category=android.intent.category.DEFAULT;type=text/plain;component=org.geometerplus.zlibrary.ui.android/org.geometerplus.android.fbreader.FBReader;S.fbreader.bookmarkid.from.external=%s;end;\">查看原文</a>";
    static final public String INTENT_ANKIHELPER_NOTE = "com.mmjang.ankihelper.note";
    public static final int VIBRATE_DURATION = 10;
	public static final String ACTION_OPEN_PROJECT = "actionopenproject";
	public static final String EXTRA_PROJECT_PATH = "extraprojectpath";
}
