package com.example.lerningapps.vitapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.facultyall;
import com.example.lerningapps.vitapp.POJO.facultyall_openhrs;

import java.util.ArrayList;

public class SwipeRecycleViewAdapter extends ArrayAdapter<facultyall> {
    private Context mcontext;
    int mresource;


    private static class ViewHolder {
        TextView namee;
    }

    public SwipeRecycleViewAdapter(Context context, int resource, ArrayList<facultyall> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String name = getItem(position).getName();
        final String empid = getItem(position).getEmpid();
        final String email = getItem(position).getEmail();
        final String phone = getItem(position).getPhone();
        final String room = getItem(position).getRoom();
        final String division = getItem(position).getDivision();
        final String designation = getItem(position).getDesignation();
        final String school = getItem(position).getSchool();
        ArrayList<facultyall_openhrs> openhrs = getItem(position).getOpen_hours();
        final String day1 = openhrs.get(0).getDay();
        final String start1 = openhrs.get(0).getStart_time();
        final String end1 = openhrs.get(0).getEnd_time();
        final String day2 = openhrs.get(1).getDay();
        final String start2 = openhrs.get(1).getStart_time();
        final String end2 = openhrs.get(1).getEnd_time();
        final View result;
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.namee = (TextView) convertView.findViewById(R.id.tvName);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity)mcontext;
                Intent i = new Intent(mcontext,activity_faculty_details.class);
                i.putExtra("name",name);
                i.putExtra("division",division);
                i.putExtra("school",school);
                i.putExtra("designation",designation);
                i.putExtra("phone",phone);
                i.putExtra("room",room);
                i.putExtra("email",email);
                i.putExtra("day1",day1);
                i.putExtra("start1",start1);
                i.putExtra("end1",end1);
                i.putExtra("day2",day2);
                i.putExtra("start2",start2);
                i.putExtra("end2",end2);
                i.putExtra("empid",empid);
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        holder.namee.setText(name);
        return convertView;
    }
}

