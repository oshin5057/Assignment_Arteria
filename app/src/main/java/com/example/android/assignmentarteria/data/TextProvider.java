package com.example.android.assignmentarteria.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TextProvider extends ContentProvider {

    public static final String LOG_TAG = TextProvider.class.getSimpleName();

    private TextDbHelper mDbHelper;

    private static final int TEXT = 100;

    private static final int TEXT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(TextContract.CONTENT_AUTHORITY, TextContract.PATH_NOTES, TEXT);
        sUriMatcher.addURI(TextContract.CONTENT_AUTHORITY, TextContract.PATH_NOTES + "/#", TEXT_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new TextDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match){
            case TEXT:
                cursor = database.query(TextContract.TextEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case TEXT_ID:
                selection = TextContract.TextEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};

                cursor =database.query(TextContract.TextEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query UNKNOWN Uri " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case TEXT:
                return TextContract.TextEntry.CONTENT_LIST_TYPE;
            case TEXT_ID:
                return TextContract.TextEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case TEXT:
                return insertText(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertText(Uri uri, ContentValues values) {
        String text = values.getAsString(TextContract.TextEntry.COLUMN_NAME);
        if (text == null){
            throw new IllegalArgumentException("Notes require a name");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(TextContract.TextEntry.TABLE_NAME, null, values);

        if (id == -1){
            Log.e(LOG_TAG,"Failed to insert row now " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int rowDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match){
            case TEXT:
                rowDeleted = db.delete(TextContract.TextEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TEXT_ID:
                selection = TextContract.TextEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = db.delete(TextContract.TextEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
