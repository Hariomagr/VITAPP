package com.example.lerningapps.vitapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.exam_catII;

import java.util.ArrayList;

public class adapter_exam_venue1 extends ArrayAdapter<exam_catII> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView title,code,venue,date,time;
    }

    public adapter_exam_venue1(Context context, int resource, ArrayList<exam_catII> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String title = getItem(position).getCourse_name();
        final String code = getItem(position).getCourse_code();
        final String slot = getItem(position).getSlot();
        final String date = getItem(position).getExam_date();
        final String day = getItem(position).getWeek_day();
        final String time = getItem(position).getTime();
        final String venue = getItem(position).getVenue();
        final String table = getItem(position).getTable_number();
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.venue = (TextView) convertView.findViewById(R.id.venue);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.title.setText(title);
        holder.code.setText(code+" ("+slot+")");
        holder.venue.setText(venue+" ("+table+")");
        holder.date.setText(date+", "+day);
        holder.time.setText(time);
        return convertView;
    }

}