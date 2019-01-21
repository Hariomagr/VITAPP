package com.example.lerningapps.vitapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.assignments_courses;
import com.example.lerningapps.vitapp.POJO.assignments_details;

import java.util.ArrayList;

public class assignments_adapter extends ArrayAdapter<assignments_courses> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView ass_name,ass_code,ass_type;
    }

    public assignments_adapter(Context context, int resource, ArrayList<assignments_courses> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String class_number = getItem(position).getClass_number();
        final String course_code = getItem(position).getCourse_code();
        final String course_title = getItem(position).getCourse_title();
        final String course_type = getItem(position).getCourse_type();
        final String faculty_name = getItem(position).getFaculty_name();
        final String slot = getItem(position).getSlot();
        final ArrayList<assignments_details> details = getItem(position).getDetails();
        final ArrayList<String> title = new ArrayList<>();
        final ArrayList<String> max_mark = new ArrayList<>();
        final ArrayList<String> weightage = new ArrayList<>();
        final ArrayList<String> due_date = new ArrayList<>();
        final ArrayList<String> status = new ArrayList<>();
        for(int i=0;i<details.size();i++){
            title.add(details.get(i).getTitle());
            max_mark.add(details.get(i).getMax_mark());
            weightage.add(details.get(i).getWeightage());
            due_date.add(details.get(i).getDue_date());
            status.add(details.get(i).getStatus());
        }
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.ass_code = (TextView) convertView.findViewById(R.id.ass_code);
            holder.ass_name = (TextView) convertView.findViewById(R.id.ass_name);
            holder.ass_type = (TextView) convertView.findViewById(R.id.ass_type);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext, com.example.lerningapps.vitapp.assignments_details.class);
                i.putExtra("title",title);
                i.putExtra("max_mark",max_mark);
                i.putExtra("weightage",weightage);
                i.putExtra("due_date",due_date);
                i.putExtra("status",status);
                mcontext.startActivity(i);
            }
        });
        holder.ass_code.setText(course_code);
        holder.ass_type.setText(course_type);
        holder.ass_name.setText(course_title);
        return convertView;
    }

}
