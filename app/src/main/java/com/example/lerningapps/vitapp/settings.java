package com.example.lerningapps.vitapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lerningapps.vitapp.POJO.assignments_courses;
import com.example.lerningapps.vitapp.POJO.exam_catI;
import com.example.lerningapps.vitapp.POJO.exam_catII;
import com.example.lerningapps.vitapp.POJO.exam_fat;
import com.example.lerningapps.vitapp.POJO.facultyall;
import com.example.lerningapps.vitapp.POJO.marks;
import com.example.lerningapps.vitapp.timetable.Timetable_POJO;
import com.example.lerningapps.vitapp.timetable.attendance_faculty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class settings extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
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
        setContentView(R.layout.activity_settings);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_facultyy);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this,Drawer.class));
            }
        });
        listView=(ListView)findViewById(R.id.list);
        final String[] values = new String[]{"Winter Semester","Fall Semester","Tri Semester"};
        final String[] values1 = new String[]{"Themes","Privacy Policy","Open Source License","Change Semester","Log Out"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values1);
        listView.setAdapter(adapter);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
        final SharedPreferences.Editor editor = pref.edit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    startActivity(new Intent(settings.this,Theme.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else if(position==3){
                    AlertDialog.Builder alt_bld = new AlertDialog.Builder(settings.this);
                    alt_bld.setTitle("Select Semester");
                    int i=-1;
                    if(pref.getString("semester",null).equals("WS")){
                        i=0;
                    }
                    else if(pref.getString("semester",null).equals("FS")){
                        i=1;
                    }
                    else if(pref.getString("semester",null).equals("TS")){
                        i=2;
                    }
                    alt_bld.setSingleChoiceItems(values, i, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0) {
                                editor.putString("semester", "WS");
                                editor.commit();
                            }
                            else if(which==1){
                                editor.putString("semester", "FS");
                                editor.commit();
                            }
                            else if(which==2){
                                editor.putString("semester", "TS");
                                editor.commit();
                            }
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alt_bld.create();
                    alertDialog.show();
                }
                else if(position==4){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("reg_no", "");
                    editor.putString("password","");
                    editor.putString("semester","FS");
                    editor.commit();
                    SharedPreferences theme = getApplicationContext().getSharedPreferences("theme", 0);
                    SharedPreferences.Editor themee = theme.edit();
                    themee.putString("theme", "app");
                    themee.commit();
                    SharedPreferences prefdata = getApplicationContext().getSharedPreferences("exam_grade", 0);
                    SharedPreferences.Editor editorrr = prefdata.edit();
                    editorrr.putString("cgpa","0.00");
                    editorrr.putString("credits","0.00"+"/"+"0.00");
                    editorrr.commit();
                    SharedPreferences sh = getApplicationContext().getSharedPreferences("image", 0);
                    SharedPreferences.Editor editorr = sh.edit();
                    editorr.putString("image", "");
                    editorr.commit();
                    ArrayList<Integer> cre = new ArrayList<>();
                    ArrayList<String> sub = new ArrayList<>();
                    SharedPreferences prefdata1 = getApplicationContext().getSharedPreferences("cgpa_calc", 0);
                    SharedPreferences.Editor editorr1 = prefdata1.edit();
                    ObjectSerializer objectSerializer=new ObjectSerializer();
                    try {
                        editorr1.putString("cre", objectSerializer.serialize(cre));
                        editorr1.putString("sub", objectSerializer.serialize(sub));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String x="";
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
                    ArrayList<Timetable_POJO> test = gson.fromJson(x,type);
                    x = gson.toJson(test);
                    SharedPreferences stud = getApplicationContext().getSharedPreferences("timetable", 0);
                    SharedPreferences.Editor stude = stud.edit();
                    stud.getString("monday",x);
                    stud.getString("tuesday",x);
                    stud.getString("wednesday",x);
                    stud.getString("thursday",x);
                    stud.getString("friday",x);
                    stud.getString("saturday",x);
                    stud.getString("sunday",x);
                    stude.commit();

                    String xx="";
                    Type typee = new TypeToken<ArrayList<attendance_faculty>>(){}.getType();
                    ArrayList<attendance_faculty> testt = gson.fromJson(xx,typee);
                    xx = gson.toJson(testt);
                    SharedPreferences studd = getApplicationContext().getSharedPreferences("attendance", 0);
                    SharedPreferences.Editor studee = studd.edit();
                    studd.getString("attendance",xx);
                    studee.commit();

                    String xxx="[{\"_id\":\"5a1ede059c6cdf43caa92d64\",\"designation\":\"Senior Professor\",\"division\":\"Department of Electrical Engineering (SELECT)\",\"email\":\"tjayabarathi@vit.ac.in\",\"empid\":\"10020\",\"intercom\":\"2497\",\"name\":\"JAYABARATHI T\",\"open_hours\":[{\"day\":\"\",\"end_time\":\"\",\"start_time\":\"\"},{\"day\":\"\",\"end_time\":\"\",\"start_time\":\"\"}],\"phone\":\"9488276543\",\"room\":\"TT-241-241\",\"school\":\"School of Electrical Engineering\"}]";
                    Type typeee = new TypeToken<ArrayList<facultyall>>(){}.getType();
                    ArrayList<facultyall> testtt = gson.fromJson(xxx,typeee);
                    xxx = gson.toJson(testtt);
                    SharedPreferences studdd = getApplicationContext().getSharedPreferences("facultyall", 0);
                    SharedPreferences.Editor studeee = studdd.edit();
                    studdd.getString("facultyall",xxx);
                    studeee.commit();

                    String xxxx="";
                    Type typeeee = new TypeToken<ArrayList<assignments_courses>>(){}.getType();
                    ArrayList<assignments_courses> testttt = gson.fromJson(xxxx,typeeee);
                    xxxx = gson.toJson(testttt);
                    SharedPreferences studddd = getApplicationContext().getSharedPreferences("assignments", 0);
                    SharedPreferences.Editor studeeee = studddd.edit();
                    studddd.getString("assignments",xxxx);
                    studeeee.commit();

                    String xxxxx="";
                    Type typeeeee = new TypeToken<ArrayList<marks>>(){}.getType();
                    ArrayList<marks> testtttt = gson.fromJson(xxxxx,typeeeee);
                    xxxxx = gson.toJson(testtttt);
                    SharedPreferences studdddd = getApplicationContext().getSharedPreferences("marks", 0);
                    SharedPreferences.Editor studeeeee = studdddd.edit();
                    studdddd.getString("marks",xxxxx);
                    studeeeee.commit();

                    String x5="";
                    Type type5 = new TypeToken<ArrayList<exam_catI>>(){}.getType();
                    ArrayList<exam_catI> test5 = gson.fromJson(x5,type5);
                    x5 = gson.toJson(test5);
                    String x6="";
                    Type type6 = new TypeToken<ArrayList<exam_catII>>(){}.getType();
                    ArrayList<exam_catI> test6 = gson.fromJson(x6,type6);
                    x6 = gson.toJson(test6);
                    String x7="";
                    Type type7 = new TypeToken<ArrayList<exam_fat>>(){}.getType();
                    ArrayList<exam_fat> test7 = gson.fromJson(x7,type7);
                    x7 = gson.toJson(test7);
                    SharedPreferences stud5 = getApplicationContext().getSharedPreferences("exam_sch", 0);
                    SharedPreferences.Editor stude5 = stud5.edit();
                    stud5.getString("cat1",x5);
                    stud5.getString("cat2",x6);
                    stud5.getString("fat",x7);
                    stude5.commit();
                    startActivity(new Intent(settings.this,MainActivity.class));
                }
                else if(position==1){
                    startActivity(new Intent(settings.this,Privacy_Policy.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else if(position==2){
                    startActivity(new Intent(settings.this,Open_Source.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(settings.this,Drawer.class));
    }
}
