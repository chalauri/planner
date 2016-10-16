package com.example.planner.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.planner.R;
import com.example.planner.StatusChangeListener;
import com.example.planner.Task;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by chalauri on 10/16/2016.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    StatusChangeListener listener;

    List<Task> tasks;
    Context context;


    public TaskAdapter(List<Task> tasks, Context context, StatusChangeListener listener) {
        this.tasks = tasks;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        holder.success.setId(tasks.get(position).id);
        holder.delete.setId(tasks.get(position).id);
        holder.fail.setId(tasks.get(position).id);
        holder.task.setText(tasks.get(position).task);
        holder.date.setText(sdf.format(tasks.get(position).date));

        switch (tasks.get(position).status) {
            case HardCodedValues.SUCCEED: {
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_green));
                break;
            }
            case HardCodedValues.FAIL: {
                holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.border_red));
                break;
            }
        }

        /*switch (contacts.get(position).getSex()) {
            case Consts.SEX_FEMALE:
                holder.sex.setText("ქალი");
                Drawable female = ContextCompat.getDrawable(holder.sex.getContext(), R.mipmap.ic_female_logo);
                holder.sex.setCompoundDrawablesWithIntrinsicBounds(null, null, female, null);
                break;
            case Consts.SEX_MALE:
                holder.sex.setText("კაცი");
                Drawable male = ContextCompat.getDrawable(holder.sex.getContext(), R.mipmap.ic_male_logo);
                holder.sex.setCompoundDrawablesWithIntrinsicBounds(null, null, male, null);
                break;
        }*/
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView date, task;
        Button success, fail, delete;


        public TaskViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date_text);
            task = (TextView) itemView.findViewById(R.id.task_text);

            success = (Button) itemView.findViewById(R.id.sucess_button);
            fail = (Button) itemView.findViewById(R.id.fail_button);
            delete = (Button) itemView.findViewById(R.id.delete_button);

            success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.successClick(v.getId(),getAdapterPosition());
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.deleteClick(v.getId());
                    }
                }
            });


            fail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.failClick(v.getId());
                    }
                }
            });


        }


    }


}
