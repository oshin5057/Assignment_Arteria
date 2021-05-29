package com.example.android.assignmentarteria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.assignmentarteria.data.TextContract;

public class AddActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_NOTE_LOADER = 0;

    private Uri mCurrentNoteUri;

    private EditText mNameEditText;

    private boolean mNoteHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mNoteHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();
        mCurrentNoteUri = intent.getData();

        mNameEditText = (EditText) findViewById(R.id.et_name_text);
        mNameEditText.setOnTouchListener(mTouchListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveNote();
                finish();
                return true;

            case android.R.id.home:
                if(mNoteHasChanged){
                    NavUtils.navigateUpFromSameTask(AddActivity.this);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String name = mNameEditText.getText().toString().trim();

        if (mCurrentNoteUri == null && TextUtils.isEmpty(name)){
            return;
        }

        ContentValues values = new ContentValues();
        values.put(TextContract.TextEntry.COLUMN_NAME, name);

        if (mCurrentNoteUri == null){
            Uri newUri = getContentResolver().insert(TextContract.TextEntry.CONTENT_URI, values);
            if (newUri == null){
                Toast.makeText(this, R.string.error_with_saving_notes, Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
            }
            else {
                Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }

        setResult(Activity.RESULT_OK);
        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {

        String[] projection = {
                TextContract.TextEntry._ID,
                TextContract.TextEntry.COLUMN_NAME
        };

        return new CursorLoader(this,
                mCurrentNoteUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1){
            return;
        }

        if (cursor.moveToFirst()){
            int nameColumnIndex = cursor.getColumnIndex(TextContract.TextEntry.COLUMN_NAME);

            String name = cursor.getString(nameColumnIndex);

            mNameEditText.setText(name);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mNameEditText.setText("");
    }

}
