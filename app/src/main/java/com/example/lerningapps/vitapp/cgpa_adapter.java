package com.example.lerningapps.vitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lerningapps.vitapp.fragments.current_grade;
import com.example.lerningapps.vitapp.timetable.cgpa_calc;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class cgpa_adapter extends ArrayAdapter<cgpa_calc> {
    private Context mcontext;
    int mresource;
    Integer total_sem_credit=0;
    Double total_gpa=10.00;
    HashMap< Integer,String> hm = new HashMap< Integer, String>();
    DecimalFormat df2,df22;
    String cgpa3,credit2;
    Double cgpa2;
    private static class ViewHolder {
        TextView ass_name,ass_code,ass_type,textt;
        ImageButton up,down;
        View parent;
    }

    public cgpa_adapter(Context context, int resource, ArrayList<cgpa_calc> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final String title = getItem(position).getTitle();
        final String code = getItem(position).getCode();
        final Integer credit = getItem(position).getCreedit();
        final View result;
        final ViewHolder holder;
        final Integer[] i = {10};
        HashMap< String,Integer> hmm = new HashMap< String,Integer>();
        SharedPreferences preff = mcontext.getSharedPreferences("exam_grade", 0);
        total_sem_credit = preff.getInt("total_sem_credit", 0);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.ass_code = (TextView) convertView.findViewById(R.id.ass_code);
            holder.ass_name = (TextView) convertView.findViewById(R.id.ass_name);
            holder.ass_type = (TextView) convertView.findViewById(R.id.ass_credit);
            holder.textt = (TextView) convertView.findViewById(R.id.textt);
            holder.up = (ImageButton) convertView.findViewById(R.id.up);
            holder.down = (ImageButton) convertView.findViewById(R.id.down);
            holder.parent = (View)convertView.findViewById(android.R.id.content);
            hm.put(10,"S");
            hm.put(9,"A");
            hm.put(8,"B");
            hm.put(7,"C");
            hm.put(6,"D");
            hm.put(5,"E");
            hm.put(4,"F");
            hm.put(3,"N");
            /***/
            df2 = new DecimalFormat(".##");
            df2.setRoundingMode(RoundingMode.UP);
            df22 = new DecimalFormat(".##");
            df22.setRoundingMode(RoundingMode.DOWN);
            SharedPreferences pref = mcontext.getSharedPreferences("exam_grade", 0);
            cgpa3 = pref.getString("cgpa",null);
            credit2 = pref.getString("credits",null);
            /***/
            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


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
        SharedPreferences pref90 = mcontext.getSharedPreferences("creds", 0);
        final Double finalCred_grade = Double.parseDouble(pref90.getString("cred_grade",null));
        final Double finalCred = Double.parseDouble(pref90.getString("cred",null));
        /***/
        Double total_reg = Double.valueOf(credit2.split(Pattern.quote("/"))[1]);
        Double total_cleared = Double.valueOf(credit2.split(Pattern.quote("/"))[0]);
        Double tot_cre = total_reg-finalCred;
        cgpa2=(((total_reg*Double.parseDouble(String.valueOf(cgpa3))/10.0)-finalCred_grade)/(total_reg-finalCred))*10.0;
        /***/
        Log.d("Gfdg",finalCred.toString()+ "  "+finalCred_grade.toString()+"  "+total_reg.toString()+" "+cgpa2.toString()+" "+cgpa3.toString()+" "+tot_cre);
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i[0]>3) {
                    i[0] = i[0] - 1;
                    if(i[0]!=4) {
                        if(i[0]==3){
                            total_gpa = total_gpa;
                            holder.textt.setText(hm.get(i[0]));
                            String[] credit23 = credit2.split(Pattern.quote("/"));
                            Double cgpaa = ((Double.parseDouble(String.valueOf(cgpa2))*Double.parseDouble(String.valueOf(credit23[1])))+(Double.parseDouble(String.valueOf(total_sem_credit))*Double.parseDouble(String.valueOf(total_gpa))))/((Double.parseDouble(String.valueOf(total_sem_credit))+Double.parseDouble(String.valueOf(credit23[1]))));
                            if(String.valueOf(df2.format(total_gpa)).substring(0,1).equals(".")){
                                current_grade.cr( String.valueOf(df22.format(cgpaa)), String.valueOf("0"+df2.format(total_gpa)));
                            }
                            else  current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf(df2.format(total_gpa)));
                        }
                        else {
                            total_gpa = total_gpa - Double.parseDouble(String.valueOf(credit)) / Double.parseDouble(String.valueOf(total_sem_credit));
                            holder.textt.setText(hm.get(i[0]));
                            String[] credit23 = credit2.split(Pattern.quote("/"));
                            Double cgpaa = ((Double.parseDouble(String.valueOf(cgpa2)) * Double.parseDouble(String.valueOf(credit23[1]))) + (Double.parseDouble(String.valueOf(total_sem_credit)) * Double.parseDouble(String.valueOf(total_gpa)))) / ((Double.parseDouble(String.valueOf(total_sem_credit)) + Double.parseDouble(String.valueOf(credit23[1]))));
                            if (String.valueOf(df2.format(total_gpa)).substring(0, 1).equals(".")) {
                                current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf("0" + df2.format(total_gpa)));
                            } else
                                current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf(df2.format(total_gpa)));
                        }
                    }
                    else{
                        total_gpa = total_gpa - Double.parseDouble(String.valueOf(5*credit)) / Double.parseDouble(String.valueOf(total_sem_credit));
                        holder.textt.setText(hm.get(i[0]));
                        String[] credit23 = credit2.split(Pattern.quote("/"));
                        Double cgpaa = ((Double.parseDouble(String.valueOf(cgpa2))*Double.parseDouble(String.valueOf(credit23[1])))+(Double.parseDouble(String.valueOf(total_sem_credit))*Double.parseDouble(String.valueOf(total_gpa))))/((Double.parseDouble(String.valueOf(total_sem_credit))+Double.parseDouble(String.valueOf(credit23[1]))));
                        if(String.valueOf(df2.format(total_gpa)).substring(0,1).equals(".")){
                            current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf("0"+df2.format(total_gpa)));
                        }
                        else  current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf(df2.format(total_gpa)));
                    }
                }
            }
        });
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i[0]<10) {
                    i[0] = i[0] + 1;
                    if(i[0]!=5) {
                        if (i[0] == 4) {
                            total_gpa = total_gpa;
                            holder.textt.setText(hm.get(i[0]));
                            String[] credit23 = credit2.split(Pattern.quote("/"));
                            Double cgpaa = ((Double.parseDouble(String.valueOf(cgpa2)) * Double.parseDouble(String.valueOf(credit23[1]))) + (Double.parseDouble(String.valueOf(total_sem_credit)) * Double.parseDouble(String.valueOf(total_gpa)))) / ((Double.parseDouble(String.valueOf(total_sem_credit)) + Double.parseDouble(String.valueOf(credit23[1]))));
                            if (String.valueOf(df2.format(total_gpa)).substring(0, 1).equals(".")) {
                                current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf("0" + df2.format(total_gpa)));
                            } else
                                current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf(df2.format(total_gpa)));
                        } else {
                            total_gpa = total_gpa + Double.parseDouble(String.valueOf(credit)) / Double.parseDouble(String.valueOf(total_sem_credit));
                            holder.textt.setText(hm.get(i[0]));
                            String[] credit23 = credit2.split(Pattern.quote("/"));
                            Double cgpaa = ((Double.parseDouble(String.valueOf(cgpa2)) * Double.parseDouble(String.valueOf(credit23[1]))) + (Double.parseDouble(String.valueOf(total_sem_credit)) * Double.parseDouble(String.valueOf(total_gpa)))) / ((Double.parseDouble(String.valueOf(total_sem_credit)) + Double.parseDouble(String.valueOf(credit23[1]))));
                            if (String.valueOf(df2.format(total_gpa)).substring(0, 1).equals(".")) {
                                current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf("0" + df2.format(total_gpa)));
                            } else
                                current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf(df2.format(total_gpa)));
                        }
                    }
                    else{
                        total_gpa = total_gpa + Double.parseDouble(String.valueOf(5*credit)) / Double.parseDouble(String.valueOf(total_sem_credit));
                        holder.textt.setText(hm.get(i[0]));
                        String[] credit23 = credit2.split(Pattern.quote("/"));
                        Double cgpaa = ((Double.parseDouble(String.valueOf(cgpa2))*Double.parseDouble(String.valueOf(credit23[1])))+(Double.parseDouble(String.valueOf(total_sem_credit))*Double.parseDouble(String.valueOf(total_gpa))))/((Double.parseDouble(String.valueOf(total_sem_credit))+Double.parseDouble(String.valueOf(credit23[1]))));
                        if(String.valueOf(df2.format(total_gpa)).substring(0,1).equals(".")){
                            current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf("0"+df2.format(total_gpa)));
                        }
                        else  current_grade.cr(String.valueOf(df22.format(cgpaa)), String.valueOf(df2.format(total_gpa)));
                    }
                }
            }
        });

        //current_grade.cr("fgh");
        holder.ass_code.setText(code+" | ");
        holder.ass_type.setText("Credit: "+String.valueOf(credit));
        holder.ass_name.setText(title);
        return convertView;
    }

}
