package com.example.android.assignmentarteria.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TextDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "texts.db";

    public static final int DATABASE_VERSION = 1;

    public TextDbHelper(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_NOTE_TABLE = "CREATE TABLE " + TextContract.TextEntry.TABLE_NAME + " ("
                + TextContract.TextEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TextContract.TextEntry.COLUMN_NAME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

