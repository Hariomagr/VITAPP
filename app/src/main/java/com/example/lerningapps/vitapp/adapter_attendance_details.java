package com.example.lerningapps.vitapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.attendance_details;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class adapter_attendance_details extends ArrayAdapter<attendance_details> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView adate,aslot,astatus;
        CircularProgressBar c1;
    }

    public adapter_attendance_details(Context context, int resource, ArrayList<attendance_details> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String date = getItem(position).getDate();
        final String slot = getItem(position).getSlot();
        final String status = getItem(position).getStatus();
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.adate = (TextView) convertView.findViewById(R.id.adate);
            holder.astatus = (TextView) convertView.findViewById(R.id.astatus);
            holder.aslot = (TextView) convertView.findViewById(R.id.aslot);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        holder.adate.setText(date);
        holder.aslot.setText(slot);
        holder.astatus.setText(status);
        if(status.equals("On Duty"))holder.astatus.setTextColor(ContextCompat.getColor(mcontext, R.color.yellow));
        else if(status.equals("Present"))holder.astatus.setTextColor(ContextCompat.getColor(mcontext, R.color.green));
        else if(status.equals("Absent"))holder.astatus.setTextColor(ContextCompat.getColor(mcontext, R.color.red));
        return convertView;
    }

}
