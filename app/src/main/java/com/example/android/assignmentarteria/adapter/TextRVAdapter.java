package com.example.android.assignmentarteria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.assignmentarteria.R;
import com.example.android.assignmentarteria.listener.TextListener;
import com.example.android.assignmentarteria.model.FragmentText;

import java.util.List;

public class TextRVAdapter extends RecyclerView.Adapter<TextRVAdapter.ViewHolder> {

    private List<FragmentText> textNote;
    private TextListener listener;

    public TextRVAdapter(List<FragmentText> textNote, TextListener listener){
        this.textNote = textNote;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TextRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextRVAdapter.ViewHolder holder, final int position) {
        holder.mEditTextNote.setText(textNote.get(position).text);

        holder.mBTNDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onDelete(position, textNote.get(position).cursorID);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return textNote.size();
    }

    public void setData(List<FragmentText> fragmentTexts) {
        this.textNote = fragmentTexts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText mEditTextNote;
        Button mBTNDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mEditTextNote = itemView.findViewById(R.id.edit_text);
            mBTNDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
