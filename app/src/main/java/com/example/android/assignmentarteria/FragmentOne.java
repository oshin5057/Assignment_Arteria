package com.example.android.assignmentarteria;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.assignmentarteria.adapter.TextRVAdapter;
import com.example.android.assignmentarteria.data.TextContract;
import com.example.android.assignmentarteria.listener.TextListener;
import com.example.android.assignmentarteria.model.FragmentText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentOne extends Fragment implements TextListener{

    private TextRVAdapter adapter;
    private List<FragmentText> notesText = new ArrayList<>();
    public RecyclerView mRecyclerView;

    public FragmentOne(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new TextRVAdapter(notesText, this);
        mRecyclerView.setAdapter(adapter);

        fetchAllNotes();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 1) {
                fetchAllNotes();
            }
        }
    }

    private void fetchAllNotes() {

        Cursor cursor = null;

        String[] projection = {
                TextContract.TextEntry._ID,
                TextContract.TextEntry.COLUMN_NAME
        };
        try {
            notesText = new ArrayList<>();
            ContentResolver resolver = getActivity().getContentResolver();
            cursor = resolver.query(TextContract.TextEntry.CONTENT_URI, projection, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                int idColumnIndex = cursor.getColumnIndex(TextContract.TextEntry._ID);
                int nameColumnIndex  = cursor.getColumnIndex(TextContract.TextEntry.COLUMN_NAME);

                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                FragmentText textNote = new FragmentText();
                textNote.text = name;
                textNote.cursorID = id;
                notesText.add(textNote);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        adapter.setData(notesText);

    }

    @Override
    public void onDelete(int position, int cursorId) {
        ContentResolver contentResolver = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            contentResolver = Objects.requireNonNull(getActivity()).getContentResolver();
        }
        Uri mCurrentNoteUri = ContentUris.withAppendedId(TextContract.TextEntry.CONTENT_URI, cursorId);
        contentResolver.delete(mCurrentNoteUri, null, null);
        notesText.remove(position);
        adapter.setData(notesText);
    }
}
