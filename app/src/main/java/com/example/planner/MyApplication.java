package com.example.planner;

import android.app.Application;

import com.example.planner.db.TaskHelper;

/**
 * Created by chalauri on 9/10/16.
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        initTaskHelper();

    }

    private void initTaskHelper() {
        TaskHelper.init(getApplicationContext());
    }


}
