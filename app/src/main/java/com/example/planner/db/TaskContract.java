package com.example.planner.db;

import android.provider.BaseColumns;

/**
 * Created by chalauri on 9/10/16.
 */
public class TaskContract {
    private TaskContract() {
    }

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "TASKS";
        public static final String COL_DATE = "DATE";
        public static final String COL_DESC = "DESC";
        public static final String COL_STATUS = "STATUS";
    }

}
