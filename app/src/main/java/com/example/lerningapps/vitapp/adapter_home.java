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
import com.example.lerningapps.vitapp.timetable.Timetable_POJO;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class adapter_home extends ArrayAdapter<Timetable_POJO> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView sub_name,sub_venue,sub_time1,sub_time2,sub_type,sub_code,sub_att;
        CircularProgressBar c1,c2;
    }

    public adapter_home(Context context, int resource, ArrayList<Timetable_POJO> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String ltpc = getItem(position).getLtpjc();
        final String faculty_name = getItem(position).getFaculty_name();
        final String course_code = getItem(position).getCourse_code();
        final String course_option = getItem(position).getCourse_option();
        final String course_mode = getItem(position).getCourse_mode();
        final String course_name = getItem(position).getCourse_name();
        final String venue = getItem(position).getVenue();
        final String slot = getItem(position).getSlot();
        final String course_typee = getItem(position).getCourse_type();
        final String total = getItem(position).getTotal_class();
        final String attended = getItem(position).getTotal_attended();
        final ArrayList<attendance_details> details = getItem(position).getDetails();
        final ArrayList<String> att_date = new ArrayList<>();
        final ArrayList<String> att_slot = new ArrayList<>();
        final ArrayList<String> att_status = new ArrayList<>();
        for(int i=0;i<details.size();i++){
            att_date.add(details.get(i).getDate());
            att_slot.add(details.get(i).getSlot());
            att_status.add(details.get(i).getStatus());
        }

        final String[] course_type = course_typee.split(Pattern.quote(" "));
        final String class_number = getItem(position).getClass_number();
        final String timee = getItem(position).getTime();
        final String day = getItem(position).getDay();
        final String attendance = getItem(position).getAtt();
        String x[] = timee.split(Pattern.quote(" "));
        String xx[] = x[0].split(Pattern.quote(":"));
        String xxx[] = x[2].split(Pattern.quote(":"));
        String ampm = "";
        String ampm1 = "";
        if(Integer.parseInt(xx[0])<12)
            ampm="AM";
        else {
            ampm = "PM";
            if(Integer.parseInt(xx[0])>12)
                xx[0]=String.valueOf(Integer.parseInt(xx[0])-12);
        }
        if(Integer.parseInt(xxx[0])<12)
            ampm1="AM";
        else {
            ampm1 = "PM";
            if(Integer.parseInt(xxx[0])>12)
                xxx[0]=String.valueOf(Integer.parseInt(xxx[0])-12);
        }
        Integer i =25;
        if(position%2==0)
            i=25;
        else
            i=-25;
        final View result;
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.sub_name = (TextView) convertView.findViewById(R.id.sub_name);
            holder.sub_time1 = (TextView) convertView.findViewById(R.id.sub_time1);
            holder.sub_time2 = (TextView) convertView.findViewById(R.id.sub_time2);
            holder.c1 = (CircularProgressBar) convertView.findViewById(R.id.circularbarr);
            holder.c2 = (CircularProgressBar) convertView.findViewById(R.id.circularbar);
            holder.sub_venue = (TextView) convertView.findViewById(R.id.sub_venue);
            holder.sub_type = (TextView) convertView.findViewById(R.id.sub_type);
            holder.sub_code = (TextView) convertView.findViewById(R.id.sub_code);
            holder.sub_att = (TextView) convertView.findViewById(R.id.sub_att);
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
                i.putExtra("title",course_name);
                i.putExtra("type",course_type[1]);
                i.putExtra("slot",slot);
                i.putExtra("attended",attended);
                i.putExtra("total",total);
                i.putExtra("percentage",attendance);
                i.putExtra("att_date",att_date);
                i.putExtra("att_slot",att_slot);
                i.putExtra("att_status",att_status);
                i.putExtra("facul",faculty_name);
                mcontext.startActivity(i);
            }
        });
        holder.sub_name.setText(course_name);
        holder.sub_venue.setText(venue);
        if(course_typee.equals("Soft Skill"))holder.sub_type.setText("STS");
        else if(course_typee.equals("Theory Only"))holder.sub_type.setText("Theory");
        else if(course_typee.equals("Lab Only"))holder.sub_type.setText("Lab");
        else if(course_typee.equals("Project Only"))holder.sub_type.setText("Project");
        else holder.sub_type.setText(course_type[1]);
        holder.sub_code.setText(course_code);
        holder.sub_time1.setText(xx[0]+":"+xx[1]+ampm);
        holder.sub_time2.setText(xxx[0]+":"+xxx[1]+ampm1);
        holder.sub_att.setText(attendance+"%");
        if(Integer.parseInt(attendance)>80)
            holder.c2.setColor(ContextCompat.getColor(mcontext, R.color.green));
        else if(Integer.parseInt(attendance)<75)
            holder.c2.setColor(ContextCompat.getColor(mcontext, R.color.red));
        else
            holder.c2.setColor(ContextCompat.getColor(mcontext, R.color.yellow));
        holder.c2.setProgressWithAnimation(Integer.parseInt(attendance), 1500);
        holder.c1.setProgressWithAnimation(i, 1500);
        return convertView;
    }

}
