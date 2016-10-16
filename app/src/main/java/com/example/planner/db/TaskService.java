package com.example.planner.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.planner.Task;
import com.example.planner.utils.HardCodedValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chalauri on 9/10/16.
 */
public class TaskService {

    TaskHelper dbHelper;

    public TaskService() {
        dbHelper = TaskHelper.getInstance();
    }

    public long saveTask(Long time, String desc) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TaskContract.TaskEntry.COL_DATE, time);
        values.put(TaskContract.TaskEntry.COL_DESC, desc);
        values.put(TaskContract.TaskEntry.COL_STATUS, HardCodedValues.FAIL);


        long newRowId = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);

        return newRowId;
    }


    public List<Task> getTasks(long from, long till,List<Task> tasks) {

        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COL_DATE,
                TaskContract.TaskEntry.COL_STATUS,
                TaskContract.TaskEntry.COL_DESC
        };


        String selection = TaskContract.TaskEntry.COL_DATE + " >= ? AND  " + TaskContract.TaskEntry.COL_DATE + " <= ? ";
        String[] selectionArgs = {String.valueOf(from), String.valueOf(till)};

        String sortOrder =
                TaskContract.TaskEntry.COL_DATE + " ASC";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry._ID));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_DESC));
                Integer status = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_STATUS));
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COL_DATE));


                Task task = new Task();
                task.id = rowId;
                task.status = status;
                task.date = new Date(timestamp);
                task.task = desc;

                tasks.add(task);


            } while (cursor.moveToNext());
        }

        db.close();


        return tasks;
    }

    public void deleteTask(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = TaskContract.TaskEntry.TABLE_NAME;
        String whereClause = "_id" + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        db.delete(table, whereClause, whereArgs);
    }

    public void updateTaskStatus(int id, int status) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = TaskContract.TaskEntry.TABLE_NAME;
        String strSQL = "UPDATE " + table + "  SET " + TaskContract.TaskEntry.COL_STATUS + " = " + status + " WHERE _id = " + id;

        db.execSQL(strSQL);
    }
}
