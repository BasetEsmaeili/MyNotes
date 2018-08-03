package com.baset.mynotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, InterfaceOnRecyclerViewItemClick {
    private AdapterNotes adapterNotes;
    private List<Note> noteList=new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private EditText newNote;
    private Toolbar toolbar;
    private TextView tvDialogTitle;
    private FrameLayout frameEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupToolbar();
        setupDb();
        setupRv();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
    }

    private void setupDb() {
        databaseHelper=new DatabaseHelper(this);
        noteList.addAll(databaseHelper.getAllNotes());
        checkEmptyView();
    }

    private void setupRv() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterNotes=new AdapterNotes(this,noteList,this);
        recyclerView.setAdapter(adapterNotes);
    }

    private void setupViews() {
        recyclerView=findViewById(R.id.notes_rv);
        toolbar=findViewById(R.id.toolbar);
        frameEmpty=findViewById(R.id.emptyView);
        
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.fab_addNewNote:
                showDialog(null,0);
               break; 
        }
    }

    private void showDialog(final String note, final int position) {
        final View view= LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_add_new_note,null);

        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newNote =view.findViewById(R.id.edt_newNote);
                        if (TextUtils.isEmpty(newNote.getText().toString())){
                            Snackbar.make(findViewById(R.id.main_root),getResources().getString(R.string.note_not_be_empty),Snackbar.LENGTH_SHORT).show();
                        }else if (note==null){
                            createNote(newNote.getText().toString());
                        }else if (note!=null){
                            updateNote(newNote.getText().toString(),position);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
        tvDialogTitle=view.findViewById(R.id.dialog_title);
        if (note!=null){
            tvDialogTitle.setText(getResources().getString(R.string.edit_note));
        }

    }

    private void updateNote(String note,int position) {
        Note note1=noteList.get(position);
        note1.setNote(note);
        databaseHelper.updateNote(note1);
        noteList.set(position,note1);
        adapterNotes.notifyItemChanged(position);
        checkEmptyView();
    }

    private void createNote(String note) {
        long id = databaseHelper.insertNote(note);
        Note n = databaseHelper.getNote(id);
        if (n != null) {
            noteList.add(0, n);
            adapterNotes.notifyDataSetChanged();

        }
        checkEmptyView();
    }

    @Override
    public void onRemoveBtnClick(int position) {
databaseHelper.deletNote(noteList.get(position));
noteList.remove(position);
adapterNotes.notifyItemRemoved(position);
checkEmptyView();
    }

    @Override
    public void onEditBtnClick(String note,int position) {
showDialog(note,position);
    }
    public void checkEmptyView(){
        if (databaseHelper.getNotesCount()>0){
            frameEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            frameEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about_us:
                startActivity(new Intent(getBaseContext(), ActivityAbout.class));
                break;
            case R.id.clear_all_notes:
                databaseHelper.deletAll();
                adapterNotes.notifyDataSetChanged();
                checkEmptyView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
