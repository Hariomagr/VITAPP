package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.Student_details;
import com.example.lerningapps.vitapp.POJO.attendance;
import com.example.lerningapps.vitapp.POJO.attendance_details;
import com.example.lerningapps.vitapp.POJO.timetable;
import com.example.lerningapps.vitapp.POJO.timetable_pojo;
import com.example.lerningapps.vitapp.timetable.Timetable_POJO;
import com.example.lerningapps.vitapp.timetable.attendance_faculty;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText reg_no,password;
    Button login;
    ProgressDialog progressDialog;
    private ArrayList<timetable> stu_time = new ArrayList<>();
    private ArrayList<attendance> stu_att = new ArrayList<>();
    private ArrayList<Student_details> data;
    private ArrayList<Timetable_POJO> timetable=new ArrayList<>();

    private ArrayList<Timetable_POJO> monday=new ArrayList<>();
    private ArrayList<Timetable_POJO> tuesday=new ArrayList<>();
    private ArrayList<Timetable_POJO> wednesday=new ArrayList<>();
    private ArrayList<Timetable_POJO> thursday=new ArrayList<>();
    private ArrayList<Timetable_POJO> friday=new ArrayList<>();
    private ArrayList<Timetable_POJO> saturday=new ArrayList<>();
    private ArrayList<Timetable_POJO> sunday=new ArrayList<>();


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
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        reg_no = (EditText)findViewById(R.id.reg_no);
        password = (EditText)findViewById(R.id.password);
        progressDialog=new ProgressDialog(this,R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(MainActivity.this,null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        login = (Button)findViewById(R.id.login);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setContentView(avLoadingIndicatorView);
                final String regs_no = reg_no.getText().toString();
                final String pass = password.getText().toString();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
                String semester = pref.getString("semester",null);
                Call<timetable_pojo> call = request.gettimetable(regs_no,pass,semester);
                call.enqueue(new Callback<timetable_pojo>() {
                    @Override
                    public void onResponse(Call<timetable_pojo> call, Response<timetable_pojo> response) {
                        if(response.code()==200) {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("reg_no", regs_no);
                            editor.putString("password",pass);
                            editor.commit();

                            timetable_pojo jsonResponse = response.body();
                            data = new ArrayList<>(Arrays.asList(jsonResponse.getStudent_details()));
                            stu_time = data.get(0).getTimetable();
                            stu_att = data.get(0).getAttendance();
                            HashMap< String,Integer> hm = new HashMap< String,Integer>();
                            ArrayList<attendance_faculty> fa = new ArrayList<>();
                            for(int i=0;i<stu_att.size();i++){
                                for(int j=0;j<stu_time.size();j++){
                                    if(!stu_time.get(j).getVenue().equals("NIL")){
                                        if(stu_att.get(i).getCourse_code().equals(stu_time.get(j).getCourse_code()) && stu_att.get(i).getSlot().equals(stu_time.get(j).getSlot())){
                                            attendance_faculty attendance_faculty=new attendance_faculty(stu_att.get(i).getTotal_classes(),stu_att.get(i).getCourse_code(),stu_att.get(i).getDetails(),stu_att.get(i).getCourse_title(),stu_att.get(i).getAttendance_percentage(),stu_att.get(i).getSlot(),stu_att.get(i).getAttended_classes(),stu_att.get(i).getCourse_type(),stu_time.get(j).getFaculty_name());
                                            fa.add(attendance_faculty);
                                        }
                                    }
                                }
                            }
                            Gson gsonn = new Gson();
                            String attendanc= gsonn.toJson(fa);
                            SharedPreferences test2 = getApplicationContext().getSharedPreferences("attendance", 0);
                            SharedPreferences.Editor editortest1 = test2.edit();
                            editortest1.putString("attendance",attendanc);
                            editortest1.commit();

                            for(int i=0;i<stu_time.size();i++){
                                if(!stu_time.get(i).getCourse_name().equals("Capstone Project")) {
                                    if (stu_time.get(i).getCourse_type().equals("Embedded Project")) {
                                        if (hm.containsKey(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name())) {
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), hm.get(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name()) + 1);
                                        } else
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), 1);
                                    } else if (stu_time.get(i).getCourse_type().equals("Embedded Lab")||stu_time.get(i).getCourse_type().equals("Lab Only")) {
                                        String lab = stu_time.get(i).getSlot();
                                        String[] labb = lab.split(Pattern.quote("+"));
                                        if (hm.containsKey(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name())) {
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), hm.get(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name()) + labb.length / 2);
                                        } else
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), labb.length / 2);


                                    } else if (stu_time.get(i).getCourse_type().equals("Soft Skill")) {
                                        if (hm.containsKey(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name())) {
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), hm.get(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name()) + 1);
                                        } else
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), 1);
                                    } else if (stu_time.get(i).getCourse_type().equals("Embedded Theory")||stu_time.get(i).getCourse_type().equals("Theory Only")) {
                                        String sl = stu_time.get(i).getSlot();
                                        Integer x = 0;
                                        String[] ssl = sl.split(Pattern.quote("+"));
                                        if (ssl.length == 3) x = 4;
                                        else if (ssl.length == 2) x = 3;
                                        else if (ssl.length == 1) {
                                            String y = ssl[0];
                                            if (y.substring(0, 1).equals("T")) x = 1;
                                            else x = 2;
                                        }
                                        if (hm.containsKey(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name())) {
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), hm.get(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name()) + x);
                                        } else
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), x);
                                    } else {
                                        if (hm.containsKey(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name())) {
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), hm.get(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name()) + 2);
                                        } else
                                            hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), 2);
                                    }
                                }
                                else{
                                    hm.put(stu_time.get(i).getCourse_code() + " " + stu_time.get(i).getCourse_name(), 20);
                                }

                                if(!stu_time.get(i).getVenue().equals("NIL")){
                                    String slot = stu_time.get(i).getSlot();
                                    String[] slott = slot.split(Pattern.quote("+"));
                                    String[][] dayy = new String[slott.length][4];
                                    String[][] timee = new String[slott.length][4];
                                    for(int j=0;j<slott.length;j++){
                                        if(slott[j].equals("A1")){
                                            dayy[0][0]="Monday";
                                            dayy[0][1]="Wednesday";
                                            timee[0][0]="08:00 to 08:50";
                                            timee[0][1]="09:00 to 09:50";
                                        }
                                        else if(slott[j].equals("B1")){
                                            dayy[0][0]="Tuesday";
                                            dayy[0][1]="Thursday";
                                            timee[0][0]="08:00 to 08:50";
                                            timee[0][1]="09:00 to 09:50";
                                        }
                                        else if(slott[j].equals("C1")){
                                            dayy[0][0]="Wednesday";
                                            dayy[0][1]="Friday";
                                            timee[0][0]="08:00 to 08:50";
                                            timee[0][1]="09:00 to 09:50";
                                        }
                                        else if(slott[j].equals("D1")){
                                            dayy[0][0]="Thursday";
                                            dayy[0][1]="Monday";
                                            timee[0][0]="08:00 to 08:50";
                                            timee[0][1]="10:00 to 10:50";
                                        }
                                        else if(slott[j].equals("E1")){
                                            dayy[0][0]="Friday";
                                            dayy[0][1]="Tuesday";
                                            timee[0][0]="08:00 to 08:50";
                                            timee[0][1]="10:00 to 10:50";
                                        }
                                        else if(slott[j].equals("F1")){
                                            dayy[0][0]="Monday";
                                            dayy[0][1]="Wednesday";
                                            timee[0][0]="09:00 to 09:50";
                                            timee[0][1]="10:00 to 10:50";
                                        }
                                        else if(slott[j].equals("G1")){
                                            dayy[0][0]="Tuesday";
                                            dayy[0][1]="Thursday";
                                            timee[0][0]="09:00 to 09:50";
                                            timee[0][1]="10:00 to 10:50";
                                        }
                                        else if(slott[j].equals("A2")){
                                            dayy[0][0]="Monday";
                                            dayy[0][1]="Wednesday";
                                            timee[0][0]="14:00 to 14:50";
                                            timee[0][1]="15:00 to 15:50";
                                        }
                                        else if(slott[j].equals("B2")){
                                            dayy[0][0]="Tuesday";
                                            dayy[0][1]="Thursday";
                                            timee[0][0]="14:00 to 14:50";
                                            timee[0][1]="15:00 to 15:50";
                                        }
                                        else if(slott[j].equals("C2")){
                                            dayy[0][0]="Wednesday";
                                            dayy[0][1]="Friday";
                                            timee[0][0]="14:00 to 14:50";
                                            timee[0][1]="15:00 to 15:50";
                                        }
                                        else if(slott[j].equals("D2")){
                                            dayy[0][0]="Thursday";
                                            dayy[0][1]="Monday";
                                            timee[0][0]="14:00 to 14:50";
                                            timee[0][1]="16:00 to 16:50";
                                        }
                                        else if(slott[j].equals("E2")){
                                            dayy[0][0]="Friday";
                                            dayy[0][1]="Tuesday";
                                            timee[0][0]="14:00 to 14:50";
                                            timee[0][1]="16:00 to 16:50";
                                        }
                                        else if(slott[j].equals("F2")){
                                            dayy[0][0]="Monday";
                                            dayy[0][1]="Wednesday";
                                            timee[0][0]="15:00 to 15:50";
                                            timee[0][1]="16:00 to 16:50";
                                        }
                                        else if(slott[j].equals("G2")){
                                            dayy[0][0]="Tuesday";
                                            dayy[0][1]="Thursday";
                                            timee[0][0]="15:00 to 15:50";
                                            timee[0][1]="16:00 to 16:50";
                                        }
                                        else if(slott[j].equals("TA1")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="10:00 to 10:50";
                                        }
                                        else if(slott[j].equals("TB1")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="11:00 to 11:50";
                                        }
                                        else if(slott[j].equals("TC1")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="11:00 to 11:50";
                                        }
                                        else if(slott[j].equals("TD1")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="12:00 to 12:50";
                                        }
                                        else if(slott[j].equals("TE1")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="11:00 to 11:50";
                                        }
                                        else if(slott[j].equals("TF1")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="11:00 to 11:50";
                                        }
                                        else if(slott[j].equals("TG1")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="12:00 to 12:50";
                                        }
                                        else if(slott[j].equals("TA2")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="16:00 to 16:50";
                                        }
                                        else if(slott[j].equals("TB2")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="17:00 to 17:50";
                                        }
                                        else if(slott[j].equals("TC2")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="17:00 to 17:50";
                                        }
                                        else if(slott[j].equals("TD2")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="17:00 to 17:50";
                                        }
                                        else if(slott[j].equals("TE2")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="17:00 to 17:50";
                                        }
                                        else if(slott[j].equals("TF2")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="17:00 to 17:50";
                                        }
                                        else if(slott[j].equals("TG2")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="18:00 to 18:50";
                                        }
                                        else if(slott[j].equals("TAA1")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="12:00 to 12:50";
                                        }
                                        else if(slott[j].equals("TCC1")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="12:00 to 12:50";
                                        }
                                        else if(slott[j].equals("TAA2")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="18:00 to 18:50";
                                        }
                                        else if(slott[j].equals("TBB2")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="18:00 to 18:50";
                                        }
                                        else if(slott[j].equals("TCC2")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="18:00 to 18:50";
                                        }
                                        else if(slott[j].equals("TDD2")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="18:00 to 18:50";
                                        }
                                        else if(slott[j].equals("V1")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="11:00 to 11:50";
                                        }
                                        else if(slott[j].equals("V2")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="12:00 to 12:50";
                                        }
                                        else if(slott[j].equals("V3")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="19:00 to 19:50";
                                        }
                                        else if(slott[j].equals("V4")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="19:00 to 19:50";
                                        }
                                        else if(slott[j].equals("V5")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="19:00 to 19:50";
                                        }
                                        else if(slott[j].equals("V6")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="19:00 to 19:50";
                                        }
                                        else if(slott[j].equals("V7")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="19:00 to 19:50";
                                        }
                                        else if(slott[j].equals("V8")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="08:00 to 08:50";
                                        }
                                        else if(slott[j].equals("V9")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="19:00 to 19:50";
                                        }
                                        else if(slott[j].equals("V10")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="08:00 to 08:50";
                                        }
                                        else if(slott[j].equals("V11")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="19:00 to 19:50";
                                        }
                                        else if(slott[j].equals("X1")){
                                            dayy[0][0]="Saturday";
                                            dayy[0][1]="Saturday";
                                            dayy[0][2]="Sunday";
                                            dayy[0][3]="Sunday";
                                            timee[0][0]="09:00 to 09:50";
                                            timee[0][1]="10:00 to 10:50";
                                            timee[0][2]="11:00 to 11:50";
                                            timee[0][3]="12:00 to 12:50";
                                        }
                                        else if(slott[j].equals("Y1")){
                                            dayy[0][0]="Saturday";
                                            dayy[0][1]="Saturday";
                                            dayy[0][2]="Sunday";
                                            dayy[0][3]="Sunday";
                                            timee[0][0]="11:00 to 11:50";
                                            timee[0][1]="12:00 to 12:50";
                                            timee[0][2]="09:00 to 09:50";
                                            timee[0][3]="10:00 to 10:50";
                                        }
                                        else if(slott[j].equals("X2")){
                                            dayy[0][0]="Saturday";
                                            dayy[0][1]="Sunday";
                                            timee[0][0]="14:00 to 14:50";
                                            timee[0][1]="16:00 to 16:50";
                                        }
                                        else if(slott[j].equals("Y2")){
                                            dayy[0][0]="Saturday";
                                            dayy[0][1]="Sunday";
                                            timee[0][0]="16:00 to 16:50";
                                            timee[0][1]="14:00 to 14:50";
                                        }
                                        else if(slott[j].equals("W2")){
                                            dayy[0][0]="Saturday";
                                            dayy[0][1]="Saturday";
                                            dayy[0][2]="Sunday";
                                            dayy[0][3]="Sunday";
                                            timee[0][0]="17:00 to 17:50";
                                            timee[0][1]="18:00 to 18:50";
                                            timee[0][2]="17:00 to 17:50";
                                            timee[0][3]="18:00 to 18:50";
                                        }
                                        else if(slott[j].equals("Z")){
                                            dayy[0][0]="Saturday";
                                            dayy[0][1]="Sunday";
                                            timee[0][0]="15:00 to 15:50";
                                            timee[0][1]="15:00 to 15:50";
                                        }

                                        //lab
                                        else if(slott[j].equals("L1")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="08:00 to 08:45";
                                        }
                                        else if(slott[j].equals("L2")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="08:46 to 09:30";
                                        }
                                        else if(slott[j].equals("L3")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="10:00 to 10:45";
                                        }
                                        else if(slott[j].equals("L4")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="10:46 to 11:30";
                                        }
                                        else if(slott[j].equals("L5")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="11:31 to 12:15";
                                        }
                                        else if(slott[j].equals("L6")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="12:15 to 13:00";
                                        } else if(slott[j].equals("L7")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="08:00 to 08:45";
                                        } else if(slott[j].equals("L8")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="08:46 to 09:30";
                                        } else if(slott[j].equals("L9")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="10:00 to 10:45";
                                        } else if(slott[j].equals("L10")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="10:46 to 11:30";
                                        } else if(slott[j].equals("L11")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="11:31 to 12:15";
                                        } else if(slott[j].equals("L12")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="12:15 to 13:00";
                                        } else if(slott[j].equals("L13")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="08:00 to 08:45";
                                        } else if(slott[j].equals("L14")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="08:46 to 09:30";
                                        } else if(slott[j].equals("L15")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="10:00 to 10:45";
                                        } else if(slott[j].equals("L16")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="10:46 to 11:30";
                                        } else if(slott[j].equals("L19")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="08:00 to 08:45";
                                        } else if(slott[j].equals("L20")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="08:46 to 09:30";
                                        } else if(slott[j].equals("L21")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="10:00 to 10:45";
                                        } else if(slott[j].equals("L22")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="10:46 to 11:30";
                                        } else if(slott[j].equals("L23")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="11:31 to 12:15";
                                        } else if(slott[j].equals("L24")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="12:15 to 13:00";
                                        } else if(slott[j].equals("L25")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="08:00 to 08:45";
                                        } else if(slott[j].equals("L26")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="08:46 to 09:30";
                                        } else if(slott[j].equals("L27")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="10:00 to 10:45";
                                        } else if(slott[j].equals("L28")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="10:46 to 11:30";
                                        } else if(slott[j].equals("L29")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="11:31 to 12:15";
                                        } else if(slott[j].equals("L30")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="12:15 to 13:00";
                                        } else if(slott[j].equals("L31")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="14:00 to 14:45";
                                        } else if(slott[j].equals("L32")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="14:46 to 15:30";
                                        } else if(slott[j].equals("L33")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="16:00 to 16:45";
                                        } else if(slott[j].equals("L34")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="16:46 to 17:30";
                                        } else if(slott[j].equals("L35")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="17:31 to 18:16";
                                        } else if(slott[j].equals("L36")){
                                            dayy[0][0]="Monday";
                                            timee[0][0]="18:16 to 19:00";
                                        } else if(slott[j].equals("L37")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="14:00 to 14:45";
                                        } else if(slott[j].equals("L38")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="14:46 to 15:30";
                                        } else if(slott[j].equals("L39")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="16:00 to 16:45";
                                        } else if(slott[j].equals("L40")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="16:46 to 17:30";
                                        } else if(slott[j].equals("L41")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="17:31 PM to 18:16";
                                        } else if(slott[j].equals("L42")){
                                            dayy[0][0]="Tuesday";
                                            timee[0][0]="18:16 to 19:00";
                                        } else if(slott[j].equals("L43")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="14:00 to 14:45";
                                        } else if(slott[j].equals("L44")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="14:46 to 15:30";
                                        } else if(slott[j].equals("L45")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="16:00 to 16:45";
                                        } else if(slott[j].equals("L46")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="16:46 to 17:30";
                                        } else if(slott[j].equals("L47")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="17:31 to 18:16";
                                        } else if(slott[j].equals("L48")){
                                            dayy[0][0]="Wednesday";
                                            timee[0][0]="18:16 to 19:00";
                                        } else if(slott[j].equals("L49")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="14:00 to 14:45";
                                        } else if(slott[j].equals("L50")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="14:46 to 15:30";
                                        } else if(slott[j].equals("L51")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="16:00 to 16:45";
                                        } else if(slott[j].equals("L52")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="16:46 to 17:30";
                                        } else if(slott[j].equals("L53")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="17:31 to 18:16";
                                        } else if(slott[j].equals("L54")){
                                            dayy[0][0]="Thursday";
                                            timee[0][0]="18:16 to 19:00";
                                        } else if(slott[j].equals("L55")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="14:00 to 14:45";
                                        } else if(slott[j].equals("L56")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="14:46 to 15:30";
                                        } else if(slott[j].equals("L57")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="16:00 to 16:45";
                                        } else if(slott[j].equals("L58")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="16:46 to 17:30";
                                        } else if(slott[j].equals("L59")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="17:31 to 18:16";
                                        } else if(slott[j].equals("L60")){
                                            dayy[0][0]="Friday";
                                            timee[0][0]="18:16 to 19:00";
                                        }

                                        else if(slott[j].equals("L71")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="08:00 to 08:45";
                                        }else if(slott[j].equals("L72")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="08:46 to 09:30";
                                        }else if(slott[j].equals("L73")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="10:00 to 10:45";
                                        }else if(slott[j].equals("L74")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="10:46 to 11:30";
                                        }else if(slott[j].equals("L75")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="11:31 to 12:15";
                                        }else if(slott[j].equals("L76")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="12:16 to 13:00";
                                        }else if(slott[j].equals("L77")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="14:00 to 14:45";
                                        }else if(slott[j].equals("L78")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="14:46 to 15:30";
                                        }else if(slott[j].equals("L79")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="16:00 to 16:45";
                                        }else if(slott[j].equals("L80")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="16:46 to 17:30";
                                        }else if(slott[j].equals("L81")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="17:31 to 18:15";
                                        }else if(slott[j].equals("L82")){
                                            dayy[0][0]="Saturday";
                                            timee[0][0]="18:16 to 19:00";
                                        }else if(slott[j].equals("L83")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="08:16 to 08:45";
                                        }else if(slott[j].equals("L84")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="08:46 to 09:30";
                                        }else if(slott[j].equals("L85")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="10:00 to 10:45";
                                        }else if(slott[j].equals("L86")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="10:46 to 11:30";
                                        }else if(slott[j].equals("L87")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="11:31 to 12:15";
                                        }else if(slott[j].equals("L88")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="12:16 to 13:00";
                                        }else if(slott[j].equals("L89")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="14:00 to 14:45";
                                        }else if(slott[j].equals("L90")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="14:46 to 15:30";
                                        }else if(slott[j].equals("L91")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="16:00 to 16:45";
                                        }else if(slott[j].equals("L92")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="16:46 to 17:30";
                                        }else if(slott[j].equals("L93")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="17:31 to 18:15";
                                        }else if(slott[j].equals("L94")){
                                            dayy[0][0]="Sunday";
                                            timee[0][0]="18:16 to 19:00";
                                        }
                                        //storing
                                        int ss=0;
                                        for(int k=0;k<stu_att.size();k++){
                                            if(stu_att.get(k).getCourse_code().equals(stu_time.get(i).getCourse_code()) && stu_att.get(k).getSlot().equals(stu_time.get(i).getSlot())){
                                                if(slott[j].equals("A1")||slott[j].equals("B1")||slott[j].equals("C1")||slott[j].equals("D1")||slott[j].equals("E1")||slott[j].equals("F1")||slott[j].equals("G1")||slott[j].equals("A2")||slott[j].equals("B2")||slott[j].equals("C2")||slott[j].equals("D2")||slott[j].equals("E2")||slott[j].equals("F2")||slott[j].equals("G2")||slott[j].equals("X2")||slott[j].equals("Y2")||slott[j].equals("Z")){
                                                    Timetable_POJO timetable_pojo1 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][0],timee[0][0],stu_time.get(i).getFaculty_name(),stu_att.get(k).getAttendance_percentage(),stu_att.get(k).getTotal_classes(),stu_att.get(k).getAttended_classes(),stu_att.get(k).getDetails());
                                                    timetable.add(timetable_pojo1);
                                                    Timetable_POJO timetable_pojo2 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][1],timee[0][1],stu_time.get(i).getFaculty_name(),stu_att.get(k).getAttendance_percentage(),stu_att.get(k).getTotal_classes(),stu_att.get(k).getAttended_classes(),stu_att.get(k).getDetails());
                                                    timetable.add(timetable_pojo2);
                                                }
                                                else if(slott.equals("X1")||slott.equals("Y1")||slott.equals("W2")){
                                                    Timetable_POJO timetable_pojo1 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][0],timee[0][0],stu_time.get(i).getFaculty_name(),stu_att.get(k).getAttendance_percentage(),stu_att.get(k).getTotal_classes(),stu_att.get(k).getAttended_classes(),stu_att.get(k).getDetails());
                                                    timetable.add(timetable_pojo1);
                                                    Timetable_POJO timetable_pojo2 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][1],timee[0][1],stu_time.get(i).getFaculty_name(),stu_att.get(k).getAttendance_percentage(),stu_att.get(k).getTotal_classes(),stu_att.get(k).getAttended_classes(),stu_att.get(k).getDetails());
                                                    timetable.add(timetable_pojo2);
                                                    Timetable_POJO timetable_pojo3 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][2],timee[0][2],stu_time.get(i).getFaculty_name(),stu_att.get(k).getAttendance_percentage(),stu_att.get(k).getTotal_classes(),stu_att.get(k).getAttended_classes(),stu_att.get(k).getDetails());
                                                    timetable.add(timetable_pojo3);
                                                    Timetable_POJO timetable_pojo4 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][3],timee[0][3],stu_time.get(i).getFaculty_name(),stu_att.get(k).getAttendance_percentage(),stu_att.get(k).getTotal_classes(),stu_att.get(k).getAttended_classes(),stu_att.get(k).getDetails());
                                                    timetable.add(timetable_pojo4);
                                                }
                                                else{
                                                    Timetable_POJO timetable_pojo1 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][0],timee[0][0],stu_time.get(i).getFaculty_name(),stu_att.get(k).getAttendance_percentage(),stu_att.get(k).getTotal_classes(),stu_att.get(k).getAttended_classes(),stu_att.get(k).getDetails());
                                                    timetable.add(timetable_pojo1);
                                                }
                                                ss=1;
                                            }

                                        }
                                        if(ss==0){
                                            ArrayList<attendance_details> x = new ArrayList<>();
                                            if(slott[j].equals("A1")||slott[j].equals("B1")||slott[j].equals("C1")||slott[j].equals("D1")||slott[j].equals("E1")||slott[j].equals("F1")||slott[j].equals("G1")||slott[j].equals("A2")||slott[j].equals("B2")||slott[j].equals("C2")||slott[j].equals("D2")||slott[j].equals("E2")||slott[j].equals("F2")||slott[j].equals("G2")||slott[j].equals("X2")||slott[j].equals("Y2")||slott[j].equals("Z")){
                                                Timetable_POJO timetable_pojo1 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][0],timee[0][0],stu_time.get(i).getFaculty_name(),"100","1","1",x);
                                                timetable.add(timetable_pojo1);
                                                Timetable_POJO timetable_pojo2 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][1],timee[0][1],stu_time.get(i).getFaculty_name(),"100","1","1",x);
                                                timetable.add(timetable_pojo2);
                                            }
                                            else if(slott.equals("X1")||slott.equals("Y1")||slott.equals("W2")){
                                                Timetable_POJO timetable_pojo1 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][0],timee[0][0],stu_time.get(i).getFaculty_name(),"100","1","1",x);
                                                timetable.add(timetable_pojo1);
                                                Timetable_POJO timetable_pojo2 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][1],timee[0][1],stu_time.get(i).getFaculty_name(),"100","1","1",x);
                                                timetable.add(timetable_pojo2);
                                                Timetable_POJO timetable_pojo3 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][2],timee[0][2],stu_time.get(i).getFaculty_name(),"100","1","1",x);
                                                timetable.add(timetable_pojo3);
                                                Timetable_POJO timetable_pojo4 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][3],timee[0][3],stu_time.get(i).getFaculty_name(),"100","1","1",x);
                                                timetable.add(timetable_pojo4);
                                            }
                                            else{
                                                Timetable_POJO timetable_pojo1 = new Timetable_POJO(stu_time.get(i).getClass_number(),stu_time.get(i).getCourse_code(),stu_time.get(i).getCourse_name(),stu_time.get(i).getCourse_type(),stu_time.get(i).getLtpjc(),stu_time.get(i).getCourse_option(),stu_time.get(i).getCourse_mode(),slott[j],stu_time.get(i).getVenue(),dayy[0][0],timee[0][0],stu_time.get(i).getFaculty_name(),"100","1","1",x);
                                                timetable.add(timetable_pojo1);
                                            }
                                        }
                                    }
                                }
                            }
                            ArrayList<Integer> cre = new ArrayList<>();
                            ArrayList<String> sub = new ArrayList<>();
                            for(Map.Entry<String, Integer> entry: hm.entrySet()) {
                                cre.add(entry.getValue());
                                sub.add(entry.getKey());
                            }
                            SharedPreferences prefdata1 = getApplicationContext().getSharedPreferences("cgpa_calc", 0);
                            SharedPreferences.Editor editorr1 = prefdata1.edit();
                            ObjectSerializer objectSerializer=new ObjectSerializer();
                            try {
                                editorr1.putString("cre", objectSerializer.serialize(cre));
                                editorr1.putString("sub", objectSerializer.serialize(sub));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            editorr1.commit();
                            int s=0;
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                            ArrayList<Timetable_POJO> timetable_pojoa = new ArrayList<>();
                            /***/
                            /****/

                            /****/
                            for(int i = 0; i< timetable.size(); i++){
                                Log.d("time",String.valueOf(i)+" "+String.valueOf(timetable.size()));
                                Log.d("time",timetable.get(i).getDay().toString());
                                if(timetable.get(i).getDay().equals(simpleDateFormat.format(date))){
                                    timetable_pojoa.add(timetable.get(i));
                                }
                                if(timetable.get(i).getDay().equals("Monday")){
                                  monday.add(timetable.get(i));

                                }
                                else if(timetable.get(i).getDay().equals("Tuesday")){
                                  tuesday.add(timetable.get(i));
                                }
                                else if(timetable.get(i).getDay().equals("Wednesday")){
                                  wednesday.add(timetable.get(i));
                                }
                                else if(timetable.get(i).getDay().equals("Thursday")){
                                   thursday.add(timetable.get(i));
                                }
                                else if(timetable.get(i).getDay().equals("Friday")){
                                   friday.add(timetable.get(i));
                                }
                                else if(timetable.get(i).getDay().equals("Saturday")){
                                  saturday.add(timetable.get(i));

                                }
                                else if(timetable.get(i).getDay().equals("Sunday")){
                                    sunday.add(timetable.get(i));
                                }
                            }
                            Gson gson = new Gson();
                            String mon= gson.toJson(monday);
                            String tue= gson.toJson(tuesday);
                            String wed= gson.toJson(wednesday);
                            String thu= gson.toJson(thursday);
                            String fri= gson.toJson(friday);
                            String sat= gson.toJson(saturday);
                            String sun= gson.toJson(sunday);
                            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
                            SharedPreferences.Editor editortest = test1.edit();
                            editortest.putString("monday",mon);
                            editortest.putString("tuesday",tue);
                            editortest.putString("wednesday",wed);
                            editortest.putString("thursday",thu);
                            editortest.putString("friday",fri);
                            editortest.putString("saturday",sat);
                            editortest.putString("sunday",sun);
                            editortest.commit();
                            Collections.sort(timetable_pojoa);
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this,Drawer.class);
                            startActivity(i);
                            progressDialog.dismiss();
                        }
                        else if(response.code()==403){
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("reg_no", "");
                            editor.putString("password","");
                            editor.commit();
                            password.setText("");
                            reg_no.setText("");
                            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else{
                            password.setText("");
                            reg_no.setText("");
                            Toast.makeText(getApplicationContext(),"VTOP Server Down",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<timetable_pojo> call, Throwable t) {
                        password.setText("");
                        reg_no.setText("");
                        Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
