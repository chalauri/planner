package com.example.planner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chalauri on 9/10/16.
 */
public class TaskHelper extends SQLiteOpenHelper {

    private static TaskHelper instance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PLANNER.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                    TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    TaskContract.TaskEntry.COL_DATE + INTEGER_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COL_DESC + TEXT_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COL_STATUS + INTEGER_TYPE +

                    " )";

    public static void init(Context context){

       if(instance == null ){
           instance = new TaskHelper(context);
       }

    }

    public static TaskHelper getInstance(){
        return instance;
    }

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME;

    public TaskHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("GPS", "HELPER CONSTRUCTOR");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("GPS", "ON CREATE HELPER");
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
