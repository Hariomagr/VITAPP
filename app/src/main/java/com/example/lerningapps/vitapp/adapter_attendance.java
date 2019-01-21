package com.example.lerningapps.vitapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.attendance_details;
import com.example.lerningapps.vitapp.timetable.attendance_faculty;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class adapter_attendance extends ArrayAdapter<attendance_faculty> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView sub_name,sub_type,sub_code,sub_att;
        CircularProgressBar c1;
    }

    public adapter_attendance(Context context, int resource, ArrayList<attendance_faculty> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String course_code = getItem(position).getCourse_code();
        final String facul = getItem(position).getFaculty();
        final String course_title = getItem(position).getCourse_title();
        final String course_type = getItem(position).getCourse_type();
        final String slot = getItem(position).getSlot();
        final String attended = getItem(position).getAttended_classes();
        final String total = getItem(position).getTotal_classes();
        final String percentage = getItem(position).getAttendance_percentage();
        final ArrayList<attendance_details> details = getItem(position).getDetails();
        final String course_typee = getItem(position).getCourse_type();
        final ArrayList<String> att_date = new ArrayList<>();
        final ArrayList<String> att_slot = new ArrayList<>();
        final ArrayList<String> att_status = new ArrayList<>();
        for(int i=0;i<details.size();i++){
            att_date.add(details.get(i).getDate());
            att_slot.add(details.get(i).getSlot());
            att_status.add(details.get(i).getStatus());
        }
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.sub_name = (TextView) convertView.findViewById(R.id.att_name);
            holder.c1 = (CircularProgressBar) convertView.findViewById(R.id.att_bar);
            holder.sub_type = (TextView) convertView.findViewById(R.id.att_type);
            holder.sub_code = (TextView) convertView.findViewById(R.id.att_code);
            holder.sub_att = (TextView) convertView.findViewById(R.id.att_att);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,attendance_details_activity.class);
                i.putExtra("code",course_code);
                i.putExtra("title",course_title);
                i.putExtra("type",course_type);
                i.putExtra("slot",slot);
                i.putExtra("attended",attended);
                i.putExtra("total",total);
                i.putExtra("percentage",percentage);
                i.putExtra("att_date",att_date);
                i.putExtra("att_slot",att_slot);
                i.putExtra("att_status",att_status);
                i.putExtra("facul",facul);
                mcontext.startActivity(i);
            }
        });
        holder.sub_name.setText(course_title);
        holder.sub_type.setText(course_type);
        holder.sub_code.setText(course_code);
        holder.sub_att.setText(percentage+"%");
        if(Integer.parseInt(percentage)>80)
            holder.c1.setColor(ContextCompat.getColor(mcontext, R.color.green));
        else if(Integer.parseInt(percentage)<75)
            holder.c1.setColor(ContextCompat.getColor(mcontext, R.color.red));
        else
            holder.c1.setColor(ContextCompat.getColor(mcontext, R.color.yellow));
        holder.c1.setProgressWithAnimation(Integer.parseInt(percentage), 1500);
        return convertView;
    }

}
