package com.example.android.assignmentarteria.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class TextContract {

    private TextContract(){

    }

    public static final String CONTENT_AUTHORITY = "com.example.android.assignmentarteria";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_NOTES = "text";

    public static final class TextEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;

        public static final String TABLE_NAME = "texts";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_NAME = "name";

    }
}
