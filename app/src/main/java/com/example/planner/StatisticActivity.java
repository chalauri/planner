package com.example.planner;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.planner.db.TaskService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


import com.example.planner.utils.HardCodedValues;

public class StatisticActivity extends AppCompatActivity {

    List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        taskList = new ArrayList<>();

        pie();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void pie() {

        Intent i = getIntent();
        long from = i.getLongExtra(MainActivity.FROM_KEY, 0);
        long till = i.getLongExtra(MainActivity.TILL_KEY, 0);

         taskList = new TaskService().getTasks(from, till,taskList);

        float success = 0;
        float fail = 0;
        float progress = 0;


        for (Task task : taskList) {
            if (task.status == HardCodedValues.SUCCEED) {
                success++;
            }

            if (task.status == HardCodedValues.FAIL) {
                fail++;
            }
        }


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(success, 0));
        entries.add(new Entry(fail, 1));


        ArrayList<String> labels = new ArrayList<>();
        labels.add("შესრულებული");
        labels.add("არ შესრულებული");


        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(labels, dataset);

        PieChart pieChart = new PieChart(this);
        pieChart.setData(data);
        pieChart.animateY(3500);
        pieChart.setHorizontalScrollBarEnabled(true);

        pieChart.setDescription("");
        //pieChart.setUsePercentValues(true);


        setContentView(pieChart);

    }
}
