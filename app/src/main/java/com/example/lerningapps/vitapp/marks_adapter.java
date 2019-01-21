package com.example.lerningapps.vitapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.marks;
import com.example.lerningapps.vitapp.POJO.marks_details;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class marks_adapter extends ArrayAdapter<marks> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView ass_name,ass_code,ass_type;
    }

    public marks_adapter(Context context, int resource, ArrayList<marks> objects) {
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
        final String[] type = course_type.split(Pattern.quote(" "));
        final ArrayList<marks_details> details = getItem(position).getMarks();
        final ArrayList<String> title = new ArrayList<>();
        final ArrayList<String> max_mark = new ArrayList<>();
        final ArrayList<String> weightage = new ArrayList<>();
        final ArrayList<String> scored = new ArrayList<>();
        final ArrayList<String> status = new ArrayList<>();
        final ArrayList<String> percentage = new ArrayList<>();


        for(int i=0;i<details.size();i++){
            title.add(details.get(i).getTitle());
            max_mark.add(details.get(i).getMax_marks());
            weightage.add(details.get(i).getWeightage());
            scored.add(details.get(i).getScored_marks());
            status.add(details.get(i).getStatus());
            percentage.add(details.get(i).getScored_percentage());
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
                Intent i = new Intent(mcontext, adapter_marks_details.class);
                i.putExtra("title",title);
                i.putExtra("max_mark",max_mark);
                i.putExtra("weightage",weightage);
                i.putExtra("scored",scored);
                i.putExtra("status",status);
                i.putExtra("percentage",percentage);
                i.putExtra("class",class_number);
                i.putExtra("code",course_code);
                mcontext.startActivity(i);
            }
        });
        holder.ass_code.setText(course_code);
        if(course_type.equals("Soft Skill"))holder.ass_type.setText("STS");
         else if(course_type.equals("Theory Only"))holder.ass_type.setText("Theory");
        else if(course_type.equals("Lab Only"))holder.ass_type.setText("Lab");
        else if(course_type.equals("Project Only"))holder.ass_type.setText("Project");
       else holder.ass_type.setText(type[1]);
        holder.ass_name.setText(course_title);
        return convertView;
    }

}
