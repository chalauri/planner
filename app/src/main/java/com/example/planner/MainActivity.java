package com.example.planner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planner.db.TaskService;
import com.example.planner.utils.HardCodedValues;
import com.example.planner.utils.TaskAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements StatusChangeListener {

    List<Task> tasks;
    DatePickerDialog.OnDateSetListener fromListener;
    DatePickerDialog.OnDateSetListener tillListener;
    Calendar myCalendar;
    EditText from;
    EditText till;
    Button searchButton;
    Button statisticButton;

    TaskAdapter adapter;

    public static final String FROM_KEY = "FROM";
    public static final String TILL_KEY = "TILL";


    String myFormat = "dd/MM/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        from = new EditText(this);
        till = new EditText(this);
        myCalendar = Calendar.getInstance();

        setDefaultDate();
        setParametersToComponents();
        LinearLayout l = (LinearLayout) findViewById(R.id.search_panel);
        setListenersToDateFields();


        l.addView(from);
        l.addView(till);
        l.addView(searchButton);
        l.addView(statisticButton);

        setListenetToStatisticButton();
        setListenerToSearchButton();

        // Data layout
        tasks = new ArrayList<>();
        tasks = getData();
        drawData();
    }

    private void setListenetToStatisticButton() {
        statisticButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StatisticActivity.class);
                Map<String, Long> dateFields = getDateFieldValues();
                i.putExtra(FROM_KEY, dateFields.get(FROM_KEY));
                i.putExtra(TILL_KEY, dateFields.get(TILL_KEY));

                startActivity(i);
            }
        });
    }

    private void setListenerToSearchButton() {
        searchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    drawData();
                tasks = getData();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_menu: {
                Intent i = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(i);
                return true;
            }
        }
        return true;
    }

    public void drawData() {
        RecyclerView lv = (RecyclerView) findViewById(R.id.main_view);
        lv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskAdapter(tasks, getApplicationContext(), this);
        lv.setAdapter(adapter);
    }

    private void setParametersToComponents() {
        searchButton = new Button(this);
        searchButton.setText("ძებნა");
        statisticButton = new Button(this);
        statisticButton.setText("სტატისტიკა");
        //   searchButton.setBackground(getResources().getDrawable(R.drawable.new_task_button));
        from.setFocusable(false);
        till.setFocusable(false);
        till.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        from.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        till.setFocusableInTouchMode(false);
        from.setFocusableInTouchMode(false);
        from.setTextSize(16);
        till.setTextSize(16);

        from.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        till.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        searchButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        statisticButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

    }


    private void updateLabelTill(EditText editText) {

        myCalendar.add(myCalendar.DATE, 1);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel(EditText editText) {


        editText.setText(sdf.format(myCalendar.getTime()));
    }

    private void setListenersToDateFields() {

        fromListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(from);
            }

        };

        tillListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(till);
            }

        };


        from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, fromListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        till.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, tillListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setDefaultDate() {
        updateLabel(from);
        updateLabelTill(till);

    }

    public List<Task> getData() {

        try {
            long fromDate = sdf.parse(from.getText().toString()).getTime();
            long tillDate = sdf.parse(till.getText().toString()).getTime();
            tasks.clear();
            tasks = new TaskService().getTasks(fromDate, tillDate, tasks);

            return tasks;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private Map<String, Long> getDateFieldValues() {
        try {
            long fromDate = sdf.parse(from.getText().toString()).getTime();
            long tillDate = sdf.parse(till.getText().toString()).getTime();

            Map<String, Long> result = new HashMap<>();
            result.put(FROM_KEY, fromDate);
            result.put(TILL_KEY, tillDate);

            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void deleteClick(final int id) {
        System.out.println("SUCC CLICK");
        System.out.println(id);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                new ContextThemeWrapper(this, R.style.MyAlertDialog));

        // set title
        alertDialogBuilder.setTitle("ნამდვილად გსურთ დავალების წაშლა ?");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("დიახ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int other_id) {
                        new TaskService().deleteTask(id);
                        tasks = getData();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("არა", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void successClick(int id, int position) {
        System.out.println("SUCC CLICK");
        System.out.println(id);


        new TaskService().updateTaskStatus(id, HardCodedValues.SUCCEED);

        tasks = getData();

        // drawData();
        adapter.notifyDataSetChanged();
    }


    public void failClick(int id) {
        System.out.println("SUCC CLICK");
        System.out.println(id);

        new TaskService().updateTaskStatus(id, HardCodedValues.FAIL);

        tasks = getData();

        adapter.notifyDataSetChanged();
    }
}
