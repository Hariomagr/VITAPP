package com.example.lerningapps.vitapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.timetable.assignment_detail_pojo1;

import java.util.ArrayList;

public class assignment_detail_adapter extends ArrayAdapter<assignment_detail_pojo1> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView ass_title,ass_due,ass_status,ass_max,ass_weightage;
    }

    public assignment_detail_adapter(Context context, int resource, ArrayList<assignment_detail_pojo1> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String title = getItem(position).getTitle();
        final String due_date = getItem(position).getDue_date();
        final String max_mark = getItem(position).getMax_mark();
        final String weightage = getItem(position).getWeightage();
        final String status = getItem(position).getStatus();
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.ass_title = (TextView) convertView.findViewById(R.id.ass_title);
            holder.ass_due = (TextView) convertView.findViewById(R.id.ass_due);
            holder.ass_weightage = (TextView) convertView.findViewById(R.id.ass_weightage);
            holder.ass_status = (TextView) convertView.findViewById(R.id.ass_status);
            holder.ass_max = (TextView) convertView.findViewById(R.id.ass_max);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.ass_title.setText(title);
        holder.ass_due.setText(due_date);
        holder.ass_weightage.setText(weightage);
        holder.ass_max.setText(max_mark);
        holder.ass_status.setText(status);
        return convertView;
    }

}
