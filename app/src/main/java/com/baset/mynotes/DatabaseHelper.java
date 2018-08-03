package com.baset.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "mynotes_db";
    public static final String CREATE_TABLE = "CREATE TABLE " + Note.TABLE_NAME + "("
            + Note.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Note.COLUMN_NOTE + " TEXT,"
            + Note.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public long insertNote(String note){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Note.COLUMN_NOTE,note);
        long id=sqLiteDatabase.insert(Note.TABLE_NAME,null,values);
        sqLiteDatabase.close();
        return id;
    }
    public Note getNote(long id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(Note.TABLE_NAME,new String[]{Note.COLUMN_ID,Note.COLUMN_NOTE,Note.COLUMN_TIMESTAMP}
        ,Note.COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null
        ,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        Note note=new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
        cursor.close();
        return note;
    }
    public List<Note> getAllNotes(){
        List<Note> notes=new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                Note note=new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                note.setDate(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                notes.add(note);
            }while (cursor.moveToNext());
        }
        db.close();
        return notes;
    }
    public int getNotesCount(){
        String countQuery="SELECT * FROM "+Note.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        int count=cursor.getCount();
        cursor.close();
        return count;
    }
    public int updateNote(Note note){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Note.COLUMN_NOTE,note.getNote());
        return sqLiteDatabase.update(Note.TABLE_NAME,contentValues,Note.COLUMN_ID+" = ? ",new String[]{String.valueOf(note.getId())});
    }
    public void deletNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Note.TABLE_NAME,Note.COLUMN_ID+" = ? ",new String[]{String.valueOf(note.getId())});
        db.close();;
    }
}
