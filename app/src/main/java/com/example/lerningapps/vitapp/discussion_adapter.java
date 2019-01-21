package com.example.lerningapps.vitapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.firebase.time_fire;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class discussion_adapter extends ArrayAdapter<time_fire> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView ass_name,ass_code,ass_type,chatt;
    }

    public discussion_adapter(Context context, int resource, ArrayList<time_fire> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String class_number = getItem(position).getClass_number();
        final String course_code = getItem(position).getCourse_code();
        final String course_title = getItem(position).getCourse_name();
        final String course_type = getItem(position).getCourse_type();
        final String faculty_name = getItem(position).getFaculty_name();
        final String chat = getItem(position).getChat();
        final String slot = getItem(position).getSlot();
        final String[] course_typee = course_type.split(Pattern.quote(" "));
        final View result;
        final ViewHolder holder;
        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.ass_code = (TextView) convertView.findViewById(R.id.ass_code);
            holder.ass_name = (TextView) convertView.findViewById(R.id.ass_name);
            holder.ass_type = (TextView) convertView.findViewById(R.id.ass_type);
            holder.chatt = (TextView) convertView.findViewById(R.id.chatt);
            result = convertView;
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(mcontext, discussion_room.class);
              i.putExtra("class",class_number+course_code);
              mcontext.startActivity(i);
            }
        });
        holder.chatt.setText(chat);
        holder.ass_code.setText(course_code);
        if(course_type.equals("Soft Skill"))holder.ass_type.setText("STS");
        else if(course_type.equals("Theory Only"))holder.ass_type.setText("Theory");
        else if(course_type.equals("Lab Only"))holder.ass_type.setText("Lab");
        else if(course_type.equals("Project Only"))holder.ass_type.setText("Project");
        else holder.ass_type.setText(course_typee[1]);
        holder.ass_name.setText(course_title);
        return convertView;
    }

}
