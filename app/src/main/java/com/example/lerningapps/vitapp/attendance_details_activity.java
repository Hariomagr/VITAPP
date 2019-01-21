package com.example.lerningapps.vitapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lerningapps.vitapp.POJO.attendance_details;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class attendance_details_activity extends AppCompatActivity {
    TextView header_sub2,header_total2,header_att2,header_miss2,header_attend2,he_code,he_facul;
    ImageButton up_att2,up_miss2,down_att2,down_miss2;
    CircularProgressBar header_circular2;
    ListView listView12;
    Integer attend1=0;
    Integer miss1=0;
    Integer percentage;
    Integer total_class=0,total_attended=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences the = getApplicationContext().getSharedPreferences("theme", 0);
        String theme = the.getString("theme",null);
        if(theme.equals("app"))setTheme(R.style.AppTheme);
        else if(theme.equals("red"))setTheme(R.style.red);
        else if(theme.equals("purple"))setTheme(R.style.purple);
        else if(theme.equals("deeporange"))setTheme(R.style.deep_orange);
        else if(theme.equals("yellow"))setTheme(R.style.yellow);
        else if(theme.equals("green"))setTheme(R.style.green);
        else if(theme.equals("teal"))setTheme(R.style.teal);
        else if(theme.equals("pink"))setTheme(R.style.pink);
        else if(theme.equals("deeppurple"))setTheme(R.style.deep_purple);
        else if(theme.equals("orange"))setTheme(R.style.orange);
        else if(theme.equals("grey"))setTheme(R.style.grey);
        else if(theme.equals("bluegrey"))setTheme(R.style.bluegrey);
        setContentView(R.layout.activity_attendance_details_activity);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Attendance Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        header_sub2=(TextView)findViewById(R.id.header_sub2);
        header_total2=(TextView)findViewById(R.id.header_total2);
        header_att2=(TextView)findViewById(R.id.header_att2);
        up_att2=(ImageButton)findViewById(R.id.up_att2);
        he_facul=(TextView)findViewById(R.id.he_facul);
        up_miss2=(ImageButton)findViewById(R.id.up_miss2);
        down_att2=(ImageButton)findViewById(R.id.down_att2);
        down_miss2=(ImageButton)findViewById(R.id.down_miss2);
        header_miss2=(TextView)findViewById(R.id.header_miss2);
        header_attend2=(TextView)findViewById(R.id.header_attend2);
        he_code=(TextView)findViewById(R.id.he_code);
        listView12=(ListView)findViewById(R.id.listview12);
        header_circular2=(CircularProgressBar)findViewById(R.id.header_circular2);
        String code = getIntent().getStringExtra("code");
        String facul = getIntent().getStringExtra("facul");
        String title = getIntent().getStringExtra("title");
        String type = getIntent().getStringExtra("type");
        String slot = getIntent().getStringExtra("slot");
        String attended = getIntent().getStringExtra("attended");
        String total = getIntent().getStringExtra("total");
        total_class = Integer.parseInt(String.valueOf(total));
        total_attended = Integer.parseInt(String.valueOf(attended));
        String percentagee = getIntent().getStringExtra("percentage");
        header_sub2.setText(title);
        he_facul.setText(facul);
        header_total2.setText("Total: "+attended+" of "+total);
        header_att2.setText(percentagee+"%");
        if(Integer.parseInt(percentagee)>80)
            header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        else if(Integer.parseInt(percentagee)<75)
            header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        else
            header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        header_circular2.setProgressWithAnimation(Integer.parseInt(percentagee), 1500);
        he_code.setText(code+" "+"( "+type+" )");
        ArrayList<String> att_date = getIntent().getStringArrayListExtra("att_date");
        ArrayList<String> att_slot = getIntent().getStringArrayListExtra("att_slot");
        ArrayList<String> att_status = getIntent().getStringArrayListExtra("att_status");
        ArrayList<attendance_details> attendanceDetails = new ArrayList<>();
        for(int i=0;i<att_date.size();i++){
            if(att_status.get(i)!=null) {
                attendance_details attendanceDetails1 = new attendance_details(att_date.get(i), att_slot.get(i), att_status.get(i));
                attendanceDetails.add(attendanceDetails1);
            }
            else {
                attendance_details attendanceDetails1 = new attendance_details(att_date.get(i), att_slot.get(i), "");
                attendanceDetails.add(attendanceDetails1);
            }
        }
        up_att2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_atte();
            }
        });
        up_miss2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_miss();
            }
        });
        down_att2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down_att();
            }
        });
        down_miss2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down_miss();
            }
        });
        adapter_attendance_details adapter =new adapter_attendance_details(attendance_details_activity.this,R.layout.adapter_attendance_details,attendanceDetails);
        listView12.setAdapter(adapter);
    }
    public void up_atte(){
        attend1=attend1+1;
        header_attend2.setText("Attend "+String.valueOf(attend1));
        Integer total_class1=total_class+attend1+miss1;
        Integer total_atte2=total_attended+attend1;
        header_total2.setText("Total: "+String.valueOf(total_atte2)+" of "+String.valueOf(total_class1));
        Integer to = (total_attended+attend1)*100;
        Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
        double aatte = Math.ceil(total_attended1/total_class1);
        String[] z= String.valueOf(aatte).split(Pattern.quote("."));
        percentage=Integer.parseInt(String.valueOf(z[0]));
        // Toast.makeText(getApplicationContext(),String.valueOf(total_class1)+" "+String.valueOf(total_attended1+" "+String.valueOf(aatte)),Toast.LENGTH_LONG).show();
        header_att2.setText(String.valueOf(percentage)+"%");
        header_circular2.setProgress(percentage);
        if(percentage>80) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        else if(percentage<75) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        else  header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
    }
    public void down_att(){
        if(attend1!=0) {
            attend1 = attend1 - 1;
            if(attend1==0){
                header_attend2.setText("Attend");
            }
            else
                header_attend2.setText("Attend " + String.valueOf(attend1));
            Integer total_class1=total_class+attend1+miss1;
            Integer total_atte2=total_attended+attend1;
            header_total2.setText("Total: "+String.valueOf(total_atte2)+" of "+String.valueOf(total_class1));
            Integer to = (total_attended+attend1)*100;
            Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
            //  Toast.makeText(getApplicationContext(),String.valueOf(total_class1)+" "+String.valueOf(total_attended1),Toast.LENGTH_LONG).show();
            double aatte = Math.ceil(total_attended1/total_class1);
            String[] z= String.valueOf(aatte).split(Pattern.quote("."));
            percentage=Integer.parseInt(String.valueOf(z[0]));
            header_att2.setText(String.valueOf(percentage)+"%");
            header_circular2.setProgress(percentage);
            if(percentage>80) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            else if(percentage<75) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
            else  header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        }
    }
    public void  up_miss(){
        miss1=miss1+1;
        header_miss2.setText("Miss "+String.valueOf(miss1));
        Integer total_class1=total_class+attend1+miss1;
        Integer total_atte2=total_attended+attend1;
        header_total2.setText("Total: "+String.valueOf(total_atte2)+" of "+String.valueOf(total_class1));
        Integer to = (total_attended+attend1)*100;
        Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
        double aatte = Math.ceil(total_attended1/total_class1);
        String[] z= String.valueOf(aatte).split(Pattern.quote("."));
        percentage=Integer.parseInt(String.valueOf(z[0]));
        header_att2.setText(String.valueOf(percentage)+"%");
        header_circular2.setProgress(percentage);
        if(percentage>80) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        else if(percentage<75) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        else  header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
    }
    public void down_miss(){
        if(miss1!=0){
            miss1=miss1-1;
            if(miss1==0){
                header_miss2.setText("Miss");
            }
            else
                header_miss2.setText("Miss "+String.valueOf(miss1));
            Integer total_class1=total_class+attend1+miss1;
            Integer total_atte2=total_attended+attend1;
            Integer to = (total_attended+attend1)*100;
            header_total2.setText("Total: "+String.valueOf(total_atte2)+" of "+String.valueOf(total_class1));
            Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
            double aatte = Math.ceil(total_attended1/total_class1);
            String[] z= String.valueOf(aatte).split(Pattern.quote("."));
            percentage=Integer.parseInt(String.valueOf(z[0]));
            // Toast.makeText(getApplicationContext(),String.valueOf(total_class1)+" "+String.valueOf(total_attended1),Toast.LENGTH_LONG).show();
            header_att2.setText(String.valueOf(percentage)+"%");
            header_circular2.setProgress(percentage);
            if(percentage>80) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            else if(percentage<75) header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
            else  header_circular2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        }
    }
}
