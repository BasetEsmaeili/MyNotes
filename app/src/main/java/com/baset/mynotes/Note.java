package com.baset.mynotes;

import android.content.Context;

public class Note {
    public static final String TABLE_NAME="MyNotes";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NOTE="note";
    public static final String COLUMN_TIMESTAMP="date";
    private int id;
    private String note;
    private String date;
    public Note(){

    }

    public Note(int id, String note, String date) {
        this.id = id;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
