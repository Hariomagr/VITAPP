package com.example.lerningapps.vitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.grade_details;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

public class grade_adapter extends ArrayAdapter<grade_details> {
    private Context mcontext;
    int mresource;
    private static class ViewHolder {
        TextView code,title,credit,grade;
        CircularProgressBar att_bar;
        View parent;
    }

    public grade_adapter(Context context, int resource, ArrayList<grade_details> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final String code = getItem(position).getCourse_code();
        final String title = getItem(position).getCourse_title();
        final String credit = getItem(position).getCredits();
        final String grade = getItem(position).getGrade();
        final View result;
        HashMap< String,Integer> hm = new HashMap< String,Integer>();
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();

            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.credit = (TextView) convertView.findViewById(R.id.credit);
            holder.grade = (TextView) convertView.findViewById(R.id.grade);
            holder.att_bar = (CircularProgressBar) convertView.findViewById(R.id.att_bar);
            holder.parent = (View)convertView.findViewById(android.R.id.content);
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        /***/
        convertView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                SharedPreferences pref = mcontext.getSharedPreferences("exam_grade", 0);
                final String cgpa1 = pref.getString("cgpa",null);
                final String credits1 = pref.getString("credits",null);


                Snackbar snackbar = Snackbar
                        .make(parent, "Credits: "+credits1, Snackbar.LENGTH_LONG)
                        .setAction("CGPA: "+cgpa1, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();

                return true;
            }
        });

        /***/



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        convertView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                SharedPreferences pref = mcontext.getSharedPreferences("exam_grade", 0);
                final String cgpa1 = pref.getString("cgpa",null);
                final String credits1 = pref.getString("credits",null);


                    Snackbar snackbar = Snackbar
                            .make(parent, "Credits: "+credits1, Snackbar.LENGTH_LONG)
                            .setAction("CGPA: "+cgpa1, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                return true;
            }
        });

        if(position%2==0)holder.att_bar.setProgressWithAnimation(-50);
        else holder.att_bar.setProgressWithAnimation(50);
        holder.code.setText(code);
        holder.title.setText(title);
        holder.credit.setText(credit);
        holder.grade.setText(grade);
        return convertView;
    }

}
