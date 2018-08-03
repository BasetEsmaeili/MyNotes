package com.baset.mynotes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<ViewHolderNotes> {
private List<Note> notes;
private Context context;
private InterfaceOnRecyclerViewItemClick onRecyclerViewItemClick;
public AdapterNotes(Context context,List<Note> notes,InterfaceOnRecyclerViewItemClick onRecyclerViewItemClick){
    this.context=context;
    this.notes=notes;
    this.onRecyclerViewItemClick=onRecyclerViewItemClick;
}
    @NonNull
    @Override
    public ViewHolderNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.itemview_note,parent, false);
        return new ViewHolderNotes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotes holder, int position) {
holder.bindData(notes.get(position),onRecyclerViewItemClick);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
