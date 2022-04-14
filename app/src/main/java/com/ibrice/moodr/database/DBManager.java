package com.ibrice.moodr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper dbHelper = null;
    private final Context context;
    private SQLiteDatabase database;

    // allows the DBManager class to be referenced, keyword "this" should be used for context
    public DBManager(Context c) {
        context = c;
    }

    // open database
    public void open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // close database
    public void close() {
        dbHelper.close();
    }

    // call to insert a diary entry
    public void insertDiary(String title, String mood, String entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TITLE, title);
        contentValues.put(DBHelper.MOOD, mood);
        contentValues.put(DBHelper.ENTRY, entry);
        database.insert(DBHelper.DIARY_TABLE_NAME, null, contentValues);
    }

    // call to insert a diary entry
    public void insertTGT(String thing1, String thing2, String thing3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.THING_1, thing1);
        contentValues.put(DBHelper.THING_2, thing2);
        contentValues.put(DBHelper.THING_3, thing3);
        database.insert(DBHelper.TGT_TABLE_NAME, null, contentValues);
    }

    // TODO: make able to fetch based on timeframe, probably using date
    // fetch diary entries/data from database
    public Cursor fetchDiary(){
        String[] columns = new String[] {
                DBHelper._ID,
                DBHelper.DATE,
                DBHelper.TITLE,
                DBHelper.MOOD,
                DBHelper.ENTRY
        };
        Cursor cursor = database.query(DBHelper.DIARY_TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    // fetch diary entries/data from database
    public Cursor fetchTGT(){
        String[] columns = new String[] {
                DBHelper._ID,
                DBHelper.DATE,
                DBHelper.THING_1,
                DBHelper.THING_2,
                DBHelper.THING_3
        };
        Cursor cursor = database.query(DBHelper.TGT_TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

}
