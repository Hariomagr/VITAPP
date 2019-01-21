package com.example.lerningapps.vitapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.timetable.mark_detail_pojo;

import java.util.ArrayList;

public class mark_detail_adapter extends ArrayAdapter<mark_detail_pojo> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView ass_title,ass_scored,ass_status,ass_max,ass_avg;
    }

    public mark_detail_adapter(Context context, int resource, ArrayList<mark_detail_pojo> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String title = getItem(position).getTitle();
        final String scored = getItem(position).getScored();
        final String max_mark = getItem(position).getMax_mark();
        final String weightage = getItem(position).getWeightage();
        final String status = getItem(position).getStatus();
        final String per = getItem(position).getPercentage();
        final String avg = getItem(position).getAvg();
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.ass_title = (TextView) convertView.findViewById(R.id.ass_title);
            holder.ass_scored = (TextView) convertView.findViewById(R.id.ass_due);
            holder.ass_status = (TextView) convertView.findViewById(R.id.ass_status);
            holder.ass_max = (TextView) convertView.findViewById(R.id.ass_max);
            holder.ass_avg = (TextView) convertView.findViewById(R.id.ass_avg);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.ass_title.setText(title);
        holder.ass_scored.setText(scored+"/"+max_mark);
        holder.ass_max.setText(per+"/"+weightage);
        holder.ass_status.setText(status);
        holder.ass_avg.setText(avg);
        return convertView;
    }

}
