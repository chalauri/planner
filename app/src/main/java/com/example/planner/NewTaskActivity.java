package com.example.planner;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.planner.db.TaskService;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    Button saveButton;
    EditText taskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setEnabled(false);

        taskView = (EditText) findViewById(R.id.task);
        setListenerToEditText();
    }

    public void editTask() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ჩაწერეთ დავალების აღწერა");
        final EditText tweetContent = new EditText(this);
        builder.setView(tweetContent);

        builder.setPositiveButton("დამახსოვრება", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taskView.setText(tweetContent.getText().toString());
            }
        });

        builder.setNegativeButton("გაუქმება", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public void saveData(View view) {


        DatePicker datePicker = (DatePicker) findViewById(R.id.date);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Log.i("PLANNER", String.valueOf(year));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);

        String task = taskView.getText().toString();

        Log.i("PLANNER", task);

        Log.i("PLANNER", String.valueOf(cal.getTime()));

        long r = new TaskService().saveTask(cal.getTime().getTime(), task);

        System.out.println(" ROW " + r);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void setListenerToEditText() {
        taskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask();
            }
        });


        taskView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
                String task = taskView.getText().toString();
                if (task != null && !task.trim().equals("")) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                String task = taskView.getText().toString();
                if (task != null && !task.trim().equals("")) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }

            }
        });
    }
}
