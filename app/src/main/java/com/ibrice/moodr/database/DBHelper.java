package com.ibrice.moodr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // database name and version
    public static final String DB_NAME = "MOODR.DB";
    public static final int DB_VERSION = 1;

    // table names
    public static final String DIARY_TABLE_NAME = "dbDiary";
    public static final String TGT_TABLE_NAME = "dbThreeGoodThings";
    public static final String HABITS_TABLE_NAME = "dbHabits";

    // table IDs
    public static final String _ID = "_id";

    // dates, used by TGT and Diary databases, generated at runtime (no need to call)
    public static final String DATE = "date";

    // diary variables
    public static final String DIARY_TITLE = "title";
    public static final String DIARY_MOOD = "mood";
    public static final String DIARY_ENTRY = "entry";

    // create table
    private static final String DIARY_CREATE_TABLE =
            "CREATE TABLE " + DIARY_TABLE_NAME +
                    "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATE + " TEXT NOT NULL, " +
                    DIARY_TITLE + " TEXT NOT NULL, " +
                    DIARY_MOOD + " TEXT NOT NULL, " +
                    DIARY_ENTRY + " TEXT NOT NULL)";

    // three good things values
    public static final String THING_1 = "Thing1";
    public static final String THING_2 = "Thing2";
    public static final String THING_3 = "Thing3";

    // create table
    private static final String TGT_CREATE_TABLE =
            "CREATE TABLE " + TGT_TABLE_NAME +
                    "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATE + " TEXT NOT NULL, " +
                    THING_1 + " TEXT NOT NULL, " +
                    THING_2 + " TEXT NOT NULL, " +
                    THING_3 + " TEXT NOT NULL)";

    // habits values
    public static final String HABITS_TITLE = "title";
    public static final String HABITS_DESCRIPTION = "description";
    public static final String HABITS_TIME = "time";

    // create table
    private static final String HABITS_CREATE_TABLE =
            "CREATE TABLE " + HABITS_TABLE_NAME +
                    "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATE + " TEXT NOT NULL, " +
                    HABITS_TITLE + " TEXT NOT NULL, " +
                    HABITS_DESCRIPTION + " TEXT NOT NULL, " +
                    HABITS_TIME + " TEXT NOT NULL)";


    // creating a helper object to manage the database
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DIARY_CREATE_TABLE);
        db.execSQL(TGT_CREATE_TABLE);
    }

    // create new tables when this is called
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DIARY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TGT_TABLE_NAME);
        onCreate(db);
    }
}
