package com.ibrice.moodr.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBManager {
    private DBHelper dbHelper = null;
    private final Context context;
    private SQLiteDatabase database;

    // allows the DBManager class to be referenced, keyword "this" should be used for context
    public DBManager(Context c) {
        context = c;
    }

    // open database
    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // close database
    public void close() {
        dbHelper.close();
    }

    // call to insert a diary entry
    @SuppressLint("SimpleDateFormat")
    public void insertDiary(String title, String mood, String entry) {
        ContentValues contentValues = new ContentValues();
        // insert date automatically
        contentValues.put(DBHelper.DATE,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        contentValues.put(DBHelper.DIARY_TITLE, title);
        contentValues.put(DBHelper.DIARY_MOOD, mood);
        contentValues.put(DBHelper.DIARY_ENTRY, entry);
        database.insert(DBHelper.DIARY_TABLE_NAME, null, contentValues);
    }

    // call to insert a ThreeGoodThings entry
    @SuppressLint("SimpleDateFormat")
    public void insertTGT(String thing1, String thing2, String thing3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DATE,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        contentValues.put(DBHelper.THING_1, thing1);
        contentValues.put(DBHelper.THING_2, thing2);
        contentValues.put(DBHelper.THING_3, thing3);
        database.insert(DBHelper.TGT_TABLE_NAME, null, contentValues);
    }

    // call to insert a Habits entry
    @SuppressLint("SimpleDateFormat")
    public void insertHabits(String title, String description, String time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DATE,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        contentValues.put(DBHelper.HABITS_TITLE, title);
        contentValues.put(DBHelper.HABITS_DESCRIPTION, description);
        contentValues.put(DBHelper.HABITS_TIME, time);
        database.insert(DBHelper.HABITS_TABLE_NAME, null, contentValues);
    }

    // TODO: make able to fetch based on timeframe, probably using date
    // fetch diary entries/data from database
    // "Cursor" provides R/W access to db, NOTE: this is where async through threads should be done
    public Cursor fetchDiary(){
        // create array of strings representing each column in the db
        String[] columns = new String[] {
                DBHelper._ID,
                DBHelper.DATE,
                DBHelper.DIARY_TITLE,
                DBHelper.DIARY_MOOD,
                DBHelper.DIARY_ENTRY
        };
        // query the database using the table name and columns generated previously
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

    // fetch ThreeGoodTHings entries from database
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

    // fetch Habits entries from database
    public Cursor fetchHabits(){
        String[] columns = new String[] {
                DBHelper._ID,
                DBHelper.DATE,
                DBHelper.HABITS_TITLE,
                DBHelper.HABITS_DESCRIPTION,
                DBHelper.HABITS_TIME
        };
        Cursor cursor = database.query(DBHelper.HABITS_TABLE_NAME,
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

    // take id (for if just one entry requires deletion), and bool to check if all should be deleted or not
    public void deleteDiary(long id, boolean deleteAll) {
        if (deleteAll) {
            database.delete(DBHelper.DIARY_TABLE_NAME, null, null);
            dbHelper.close();
        } else {
            database.delete(DBHelper.DIARY_TABLE_NAME, DBHelper._ID + " = " + id, null);
        }
    }

    public void deleteTGT(long id, boolean deleteAll) {
        if (deleteAll) {
            database.delete(DBHelper.TGT_TABLE_NAME, null, null);
            dbHelper.close();
        } else {
            database.delete(DBHelper.TGT_TABLE_NAME, DBHelper._ID + " = " + id, null);
        }
    }

    public void deleteHabits(long id, boolean deleteAll) {
        if (deleteAll) {
            database.delete(DBHelper.HABITS_TABLE_NAME, null, null);
            dbHelper.close();
        } else {
            database.delete(DBHelper.HABITS_TABLE_NAME, DBHelper._ID + " = " + id, null);
        }
    }
}
