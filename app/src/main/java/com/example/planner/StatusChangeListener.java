package com.example.planner;

import android.view.View;

import com.example.planner.db.TaskService;
import com.example.planner.utils.HardCodedValues;

/**
 * Created by chalauri on 10/16/2016.
 */

public interface StatusChangeListener {
    void deleteClick(int id);

    void successClick(int id, int position);

    void failClick(int id);
}
