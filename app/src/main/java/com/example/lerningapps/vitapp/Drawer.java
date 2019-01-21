package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.Student_details;
import com.example.lerningapps.vitapp.POJO.attendance;
import com.example.lerningapps.vitapp.POJO.attendance_details;
import com.example.lerningapps.vitapp.POJO.timetable;
import com.example.lerningapps.vitapp.POJO.timetable_pojo;
import com.example.lerningapps.vitapp.timetable.Timetable_POJO;
import com.example.lerningapps.vitapp.timetable.attendance_faculty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog progressDialog;
    /***/
    private TextView header_sub,header_time,header_venue,header_att,header_attend,header_miss;
    private CircularProgressBar header_circular;
    private ImageButton up_miss,up_att,down_miss,down_att;
    LinearLayout container;
    private CardView card2,card4;
    private TextView todayy;
    Integer attend1=0;
    Integer miss1=0;
    Integer total_class=0,total_attended=0;
    Integer percentage;
    ArrayList<Timetable_POJO> timetable_pojoa2 = new ArrayList<>();
    /***/
    private ArrayList<Student_details> data;
    private ArrayList<Timetable_POJO> timetable=new ArrayList<>();

    private ArrayList<Timetable_POJO> monday=new ArrayList<>();
    private ArrayList<Timetable_POJO> tuesday=new ArrayList<>();
    private ArrayList<Timetable_POJO> wednesday=new ArrayList<>();
    private ArrayList<Timetable_POJO> thursday=new ArrayList<>();
    private ArrayList<Timetable_POJO> friday=new ArrayList<>();
    private ArrayList<Timetable_POJO> saturday=new ArrayList<>();
    private ArrayList<Timetable_POJO> sunday=new ArrayList<>();

    private CircleImageView circleImageView;
    private ListView listView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<timetable> stu_time = new ArrayList<>();
    private ArrayList<attendance> stu_att = new ArrayList<>();
    /***/
    Integer i1=0,i2=0,i3=0,i4=0,i5=0,i6=0,i7=0,i8=0,i9=0,i10=0;
    /***/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences the = getApplicationContext().getSharedPreferences("theme", 0);
        final String theme = the.getString("theme",null);
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
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Today");
        /***/

        card2=(CardView)findViewById(R.id.card2);
        container=(LinearLayout)findViewById(R.id.container);
        card4=(CardView)findViewById(R.id.card4);
        todayy=(TextView)findViewById(R.id.today);
        card2.setVisibility(View.GONE);
        todayy.setVisibility(View.GONE);
        card4.setVisibility(View.GONE);
        header_sub=(TextView)findViewById(R.id.header_sub);
        header_time=(TextView)findViewById(R.id.header_time);
        header_venue=(TextView)findViewById(R.id.header_venue);
        header_att=(TextView)findViewById(R.id.header_att);
        header_attend=(TextView)findViewById(R.id.header_attend);
        header_miss=(TextView)findViewById(R.id.header_miss);
        header_circular=(CircularProgressBar)findViewById(R.id.header_circular);
        up_miss=(ImageButton)findViewById(R.id.up_miss);
        up_att=(ImageButton)findViewById(R.id.up_att);
        down_att=(ImageButton)findViewById(R.id.down_att);
        down_miss=(ImageButton)findViewById(R.id.down_miss);

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
        SharedPreferences.Editor editor1 = pref1.edit();
        editor1.putString("chk", "0");
        editor1.commit();
        String ckh = pref1.getString("chk",null);
        if(!ckh.equals("0")){
            listView.setVisibility(View.GONE);
            card2.setVisibility(View.GONE);
            card4.setVisibility(View.GONE);
            todayy.setVisibility(View.GONE);
        }
        else{
            container.setVisibility(View.GONE);
        }

        /***/
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
           // navigationView.getMenu().getItem(0).setChecked(true);
        listView=(ListView)findViewById(R.id.listview1);
        final View headerView = navigationView.getHeaderView(0);
        circleImageView = (CircleImageView)headerView.findViewById(R.id.profile_image);
        TextView regg = (TextView)headerView.findViewById(R.id.reg_no);
        SharedPreferences sh = getApplicationContext().getSharedPreferences("image", 0);
        String imagess = sh.getString("image",null);
        if(!imagess.equals("")) {
            byte[] decodedByte = Base64.decode(imagess, 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            circleImageView.setImageBitmap(bitmap);
        }

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));
                startActivityForResult(i,1);
            }
        });
        up_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               up_atte();
            }
        });
        down_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               down_att();
            }
        });
        up_miss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              up_miss();
            }
        });
        down_miss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down_miss();
            }
        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        regg.setText(reg_no);
        day();
        progressDialog=new ProgressDialog(this,R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(Drawer.this,null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<timetable_pojo> call = request.gettimetable(reg_no,password,semester);
        call.enqueue(new Callback<timetable_pojo>() {
            @Override
            public void onResponse(Call<timetable_pojo> call, Response<timetable_pojo> response) {

                if(response.code()==200) {
                    timetable_pojo jsonResponse = response.body();
                  //
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
                            } else if (stu_time.get(i).getCourse_type().equals("Embedded Lab") || stu_time.get(i).getCourse_type().equals("Lab Only")) {
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
                            } else if (stu_time.get(i).getCourse_type().equals("Embedded Theory") || stu_time.get(i).getCourse_type().equals("Theory Only")) {
                                String sl = stu_time.get(i).getSlot();
                                Integer x = 0;
                                String[] ssl = sl.split(Pattern.quote("+"));
                                if (ssl.length == 3) x = 4;
                                else if(ssl.length==4) x=4;
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
                                Log.d("hj",String.valueOf(ss));
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
                    Log.d("sdf",hm.toString());
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
                    Date d = new Date();
                    SimpleDateFormat simpDate;
                    simpDate = new SimpleDateFormat("kk:mm:ss");
                    String today_time = simpDate.format(d);
                    String[] today_ttime = today_time.split(Pattern.quote(":"));
                 //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
                    Integer ing = -1;
                    Integer min = 86400;
                    String z = today_ttime[0];
                    if(z.equals("24")){z="00";}
                    Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
                   // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
                    for(int q=0;q<timetable_pojoa.size();q++){
                        String time1 = timetable_pojoa.get(q).getTime();
                        String[] time2 = time1.split(Pattern.quote(" "));
                        String[] time3 = time2[0].split(Pattern.quote(":"));
                        Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                        Integer diff = time4-today_milli;
                        if(diff>=0){
                            if(min>diff){
                                min=diff;
                                ing=q;
                            }
                        }
                    }
                    if(ing==-1){ card4.setVisibility(View.VISIBLE);
                    todayy.setText("Earlier Today");}
                    else{
                        if(ing!=0){
                            String time1 = timetable_pojoa.get(ing-1).getTime();
                            String[] time2 = time1.split(Pattern.quote(" "));
                            String[] time3 = time2[0].split(Pattern.quote(":"));
                            Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                            Integer difff = today_milli-time4;
                            if(difff<1200){
                                long m = (difff / 60) % 60;
                                long h = (difff / (60 * 60)) % 24;
                                if(h==0){
                                    if(m<10){
                                        header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                                    }
                                    else{
                                        header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                                    }
                                }
                                else{
                                    if(m<10){
                                        header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                                    }
                                    else{
                                        header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                                    }
                                }
                                header_sub.setText(timetable_pojoa.get(ing-1).getCourse_name());
                                header_venue.setText("Venue: " + timetable_pojoa.get(ing-1).getVenue());
                                header_att.setText(timetable_pojoa.get(ing-1).getAtt() + "%");
                                header_circular.setProgress(Integer.parseInt(timetable_pojoa.get(ing-1).getAtt()));
                                if(Integer.parseInt(timetable_pojoa.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                                else if(Integer.parseInt(timetable_pojoa.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                                total_class = Integer.parseInt(timetable_pojoa.get(ing-1).getTotal_class());
                                total_attended = Integer.parseInt(timetable_pojoa.get(ing-1).getTotal_attended());

                            }
                            else{
                                long m = (min / 60) % 60;
                                long h = (min / (60 * 60)) % 24;
                                if(h==0){
                                    if(m<10){
                                        header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                                    }
                                    else{
                                        header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                                    }
                                }
                                else{
                                    if(m<10){
                                        header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                                    }
                                    else{
                                        header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                                    }
                                }
                                header_sub.setText(timetable_pojoa.get(ing).getCourse_name());
                                header_venue.setText("Venue: " + timetable_pojoa.get(ing).getVenue());
                                header_att.setText(timetable_pojoa.get(ing).getAtt() + "%");
                                header_circular.setProgress(Integer.parseInt(timetable_pojoa.get(ing).getAtt()));
                                if(Integer.parseInt(timetable_pojoa.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                                else if(Integer.parseInt(timetable_pojoa.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                                total_class = Integer.parseInt(timetable_pojoa.get(ing).getTotal_class());
                                total_attended = Integer.parseInt(timetable_pojoa.get(ing).getTotal_attended());
                            }
                        }
                        else {
                            long m = (min / 60) % 60;
                            long h = (min / (60 * 60)) % 24;
                            if(h==0){
                                if(m<10){
                                    header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                                }
                                else{
                                    header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                                }
                            }
                            else{
                                if(m<10){
                                    header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                                }
                                else{
                                    header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                                }
                            }
                            header_sub.setText(timetable_pojoa.get(ing).getCourse_name());
                            header_venue.setText("Venue: " + timetable_pojoa.get(ing).getVenue());
                            header_att.setText(timetable_pojoa.get(ing).getAtt() + "%");
                            header_circular.setProgress(Integer.parseInt(timetable_pojoa.get(ing).getAtt()));
                            if(Integer.parseInt(timetable_pojoa.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                            else if(Integer.parseInt(timetable_pojoa.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                            total_class = Integer.parseInt(timetable_pojoa.get(ing).getTotal_class());
                            total_attended = Integer.parseInt(timetable_pojoa.get(ing).getTotal_attended());
                        }
                        card2.setVisibility(View.VISIBLE);

                    }
                    todayy.setVisibility(View.VISIBLE);
                    adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,timetable_pojoa);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
                else if(response.code()==403){
                    progressDialog.dismiss();
                    day();
                    Toast.makeText(getApplicationContext(),"Invalid Credentials, Login Again",Toast.LENGTH_LONG).show();
                }
                else{
                    day();
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"VTOP DOWN",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<timetable_pojo> call, Throwable t) {
                progressDialog.dismiss();
                day();
                Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_LONG).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.schedule) {
            if(i1!=1) {
                SharedPreferences pref10 = getApplicationContext().getSharedPreferences("schedule_index", 0);
                SharedPreferences.Editor editor10 = pref10.edit();
                editor10.putInt("index", 0);
                editor10.putInt("chk",0);
                editor10.commit();
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                container.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle("Schedule");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);

                        schedule fragment = new schedule();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();


            }
            i1=1;
            i2=0;i3=0;i4=0;i5=0;i6=0;i7=0;i8=0;i9=0;i10=0;
        } else if (id == R.id.attendance) {
            if(i2!=1) {
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                getSupportActionBar().setTitle("Attendance");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);
                attendance_fragment fragment = new attendance_fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
            container.setVisibility(View.VISIBLE);
            i2=1;
            i1=0;i3=0;i4=0;i5=0;i6=0;i7=0;i8=0;i9=0;i10=0;
        } else if (id == R.id.faculty) {
            if(i3!=1){
                container.setVisibility(View.VISIBLE);
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                getSupportActionBar().setTitle("Faculty");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);
                faculty_fragment fragment = new faculty_fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
            i3=1;
            i2=0;i1=0;i4=0;i5=0;i6=0;i7=0;i8=0;i9=0;i10=0;
        } else if (id == R.id.assignments) {
            if(i4!=1){
                container.setVisibility(View.VISIBLE);
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                getSupportActionBar().setTitle("Assignments");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);
                fragment_assignments fragment = new fragment_assignments();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
            i4=1;
            i2=0;i3=0;i1=0;i5=0;i6=0;i7=0;i8=0;i9=0;i10=0;
        }else if(id == R.id.discussion){
            if(i9!=1){
                container.setVisibility(View.VISIBLE);
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                getSupportActionBar().setTitle("Discussion");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);
                fragment_discussion fragment = new fragment_discussion();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
            i9=1;
            i2=0;i3=0;i1=0;i5=0;i6=0;i7=0;i8=0;i4=0;i10=0;
        } else if (id == R.id.marks) {
            if(i5!=1){
                container.setVisibility(View.VISIBLE);
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                getSupportActionBar().setTitle("Marks");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);
                fragment_marks fragment = new fragment_marks();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
            i5=1;
            i2=0;i3=0;i4=0;i1=0;i6=0;i7=0;i8=0;i9=0;i10=0;
        } else if (id == R.id.grades) {

            if(i6!=1){
                container.setVisibility(View.VISIBLE);
                SharedPreferences pref11 = getApplicationContext().getSharedPreferences("grade_index", 0);
                SharedPreferences.Editor editor11 = pref11.edit();
                editor11.putInt("index", 0);
                editor11.putInt("chk",0);
                editor11.commit();
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                getSupportActionBar().setTitle("Grades");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);

                        grade_fragment fragment = new grade_fragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();



            }
            i6=1;
            i2=0;i3=0;i4=0;i5=0;i1=0;i7=0;i8=0;i9=0;i10=0;
        }else if (id == R.id.exam_schedule) {
            if(i7!=1){
                SharedPreferences pref12 = getApplicationContext().getSharedPreferences("exam_index", 0);
                SharedPreferences.Editor editor12 = pref12.edit();
                editor12.putInt("index", 0);
                editor12.putInt("chk",0);
                editor12.commit();
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                container.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle("Exam Schedule");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);

                        exam_venue_fragment fragment = new exam_venue_fragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();


            }
            i7=1;
            i2=0;i3=0;i4=0;i5=0;i6=0;i1=0;i8=0;i9=0;i10=0;
        }else if(id==R.id.curriculum){
            if(i10!=1){
                container.setVisibility(View.VISIBLE);
                SharedPreferences pref12 = getApplicationContext().getSharedPreferences("curriculum_index", 0);
                SharedPreferences.Editor editor12 = pref12.edit();
                editor12.putInt("index", 0);
                editor12.putInt("chk",0);
                editor12.commit();
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
                SharedPreferences.Editor editor1 = pref1.edit();
                editor1.putString("chk", "");
                editor1.commit();
                getSupportActionBar().setTitle("Curriculum");
                listView.setVisibility(View.GONE);
                card2.setVisibility(View.GONE);
                card4.setVisibility(View.GONE);
                todayy.setVisibility(View.GONE);

                        Curriculum_Fragment fragment = new Curriculum_Fragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();
                        container.setVisibility(View.VISIBLE);


            }
            i10=1;
            i2=0;i3=0;i4=0;i5=0;i6=0;i1=0;i8=0;i9=0;i7=0;
        }else if(id == R.id.settings){
            Intent i =new Intent(Drawer.this,settings.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==1){
            Uri uri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
                SharedPreferences sh = getApplicationContext().getSharedPreferences("image", 0);
                SharedPreferences.Editor editor = sh.edit();
                editor.putString("image", imageEncoded);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
            circleImageView.setImageURI(uri);

        }
    }
    public void up_atte(){
        attend1=attend1+1;
        header_attend.setText("Attend "+String.valueOf(attend1));
        Integer total_class1=total_class+attend1+miss1;
        Integer to = (total_attended+attend1)*100;
        Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
        double aatte = Math.ceil(total_attended1/total_class1);
        String[] z= String.valueOf(aatte).split(Pattern.quote("."));
        percentage=Integer.parseInt(String.valueOf(z[0]));
       // Toast.makeText(getApplicationContext(),String.valueOf(total_class1)+" "+String.valueOf(total_attended1+" "+String.valueOf(aatte)),Toast.LENGTH_LONG).show();
        header_att.setText(String.valueOf(percentage)+"%");
        header_circular.setProgress(percentage);
        if(percentage>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        else if(percentage<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
    }
    public void down_att(){
        if(attend1!=0) {
            attend1 = attend1 - 1;
            if(attend1==0){
                header_attend.setText("Attend");
            }
            else
                header_attend.setText("Attend " + String.valueOf(attend1));
            Integer total_class1=total_class+attend1+miss1;
            Integer to = (total_attended+attend1)*100;
            Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
         //  Toast.makeText(getApplicationContext(),String.valueOf(total_class1)+" "+String.valueOf(total_attended1),Toast.LENGTH_LONG).show();
            double aatte = Math.ceil(total_attended1/total_class1);
            String[] z= String.valueOf(aatte).split(Pattern.quote("."));
            percentage=Integer.parseInt(String.valueOf(z[0]));
            header_att.setText(String.valueOf(percentage)+"%");
            header_circular.setProgress(percentage);
            if(percentage>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            else if(percentage<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
            else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        }
    }
    public void  up_miss(){
        miss1=miss1+1;
        header_miss.setText("Miss "+String.valueOf(miss1));
        Integer total_class1=total_class+attend1+miss1;
        Integer to = (total_attended+attend1)*100;
        Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
        double aatte = Math.ceil(total_attended1/total_class1);
        String[] z= String.valueOf(aatte).split(Pattern.quote("."));
        percentage=Integer.parseInt(String.valueOf(z[0]));
        header_att.setText(String.valueOf(percentage)+"%");
        header_circular.setProgress(percentage);
        if(percentage>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        else if(percentage<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
    }
    public void down_miss(){
        if(miss1!=0){
            miss1=miss1-1;
            if(miss1==0){
                header_miss.setText("Miss");
            }
            else
                header_miss.setText("Miss "+String.valueOf(miss1));
            Integer total_class1=total_class+attend1+miss1;
            Integer to = (total_attended+attend1)*100;
            Double total_attended1=Double.parseDouble(String.valueOf((total_attended+attend1)*100));
            double aatte = Math.ceil(total_attended1/total_class1);
            String[] z= String.valueOf(aatte).split(Pattern.quote("."));
            percentage=Integer.parseInt(String.valueOf(z[0]));
           // Toast.makeText(getApplicationContext(),String.valueOf(total_class1)+" "+String.valueOf(total_attended1),Toast.LENGTH_LONG).show();
            header_att.setText(String.valueOf(percentage)+"%");
            header_circular.setProgress(percentage);
            if(percentage>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            else if(percentage<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
            else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        }
    }

    public void day(){
        Date datee = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        if(simpleDateFormat.format(datee).equals("Monday")){
            Gson gson=new Gson();
            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
            String mon = test1.getString("monday",null);
            Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
            ArrayList<Timetable_POJO> test = gson.fromJson(mon,type);
                Collections.sort(test);
                Date d = new Date();
                SimpleDateFormat simpDate;
                simpDate = new SimpleDateFormat("kk:mm:ss");
                String today_time = simpDate.format(d);
                String[] today_ttime = today_time.split(Pattern.quote(":"));
                //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
                Integer ing = -1;
                Integer min = 86400;
                String z = today_ttime[0];
                if(z.equals("24")){z="00";}
                Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
                // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
                for(int q=0;q<test.size();q++){
                    String time1 = test.get(q).getTime();
                    String[] time2 = time1.split(Pattern.quote(" "));
                    String[] time3 = time2[0].split(Pattern.quote(":"));
                    Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                    Integer diff = time4-today_milli;
                    if(diff>=0){
                        if(min>diff){
                            min=diff;
                            ing=q;
                        }
                    }
                }
                if(ing==-1){ card4.setVisibility(View.VISIBLE);
                    todayy.setText("Earlier Today");}
                else{
                    if(ing!=0){
                        String time1 = test.get(ing-1).getTime();
                        String[] time2 = time1.split(Pattern.quote(" "));
                        String[] time3 = time2[0].split(Pattern.quote(":"));
                        Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                        Integer difff = today_milli-time4;
                        if(difff<1200){
                            long m = (difff / 60) % 60;
                            long h = (difff / (60 * 60)) % 24;
                            if(h==0){
                                if(m<10){
                                    header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                                }
                                else{
                                    header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                                }
                            }
                            else{
                                if(m<10){
                                    header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                                }
                                else{
                                    header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                                }
                            }
                            header_sub.setText(test.get(ing-1).getCourse_name());
                            header_venue.setText("Venue: " + test.get(ing-1).getVenue());
                            header_att.setText(test.get(ing-1).getAtt() + "%");
                            header_circular.setProgress(Integer.parseInt(test.get(ing-1).getAtt()));
                            if(Integer.parseInt(test.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                            else if(Integer.parseInt(test.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                            total_class = Integer.parseInt(test.get(ing-1).getTotal_class());
                            total_attended = Integer.parseInt(test.get(ing-1).getTotal_attended());

                        }
                        else{
                            long m = (min / 60) % 60;
                            long h = (min / (60 * 60)) % 24;
                            if(h==0){
                                if(m<10){
                                    header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                                }
                                else{
                                    header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                                }
                            }
                            else{
                                if(m<10){
                                    header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                                }
                                else{
                                    header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                                }
                            }
                            header_sub.setText(test.get(ing).getCourse_name());
                            header_venue.setText("Venue: " + test.get(ing).getVenue());
                            header_att.setText(test.get(ing).getAtt() + "%");
                            header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                            if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                            else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                            total_class = Integer.parseInt(test.get(ing).getTotal_class());
                            total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                        }
                    }
                    else {
                        long m = (min / 60) % 60;
                        long h = (min / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing).getVenue());
                        header_att.setText(test.get(ing).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                        if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                    }
                    card2.setVisibility(View.VISIBLE);

                }
                todayy.setVisibility(View.VISIBLE);
                adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,test);
                listView.setAdapter(adapter);

        }
        else if(simpleDateFormat.format(datee).equals("Tuesday")){
            Gson gson=new Gson();
            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
            String mon = test1.getString("tuesday",null);
            Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
            ArrayList<Timetable_POJO> test = gson.fromJson(mon,type);
            Collections.sort(test);
            Date d = new Date();
            SimpleDateFormat simpDate;
            simpDate = new SimpleDateFormat("kk:mm:ss");
            String today_time = simpDate.format(d);
            String[] today_ttime = today_time.split(Pattern.quote(":"));
            //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
            Integer ing = -1;
            Integer min = 86400;
            String z = today_ttime[0];
            if(z.equals("24")){z="00";}
            Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
            // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
            for(int q=0;q<test.size();q++){
                String time1 = test.get(q).getTime();
                String[] time2 = time1.split(Pattern.quote(" "));
                String[] time3 = time2[0].split(Pattern.quote(":"));
                Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                Integer diff = time4-today_milli;
                if(diff>=0){
                    if(min>diff){
                        min=diff;
                        ing=q;
                    }
                }
            }
            if(ing==-1){ card4.setVisibility(View.VISIBLE);
                todayy.setText("Earlier Today");}
            else{
                if(ing!=0){
                    String time1 = test.get(ing-1).getTime();
                    String[] time2 = time1.split(Pattern.quote(" "));
                    String[] time3 = time2[0].split(Pattern.quote(":"));
                    Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                    Integer difff = today_milli-time4;
                    if(difff<1200){
                        long m = (difff / 60) % 60;
                        long h = (difff / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing-1).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing-1).getVenue());
                        header_att.setText(test.get(ing-1).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing-1).getAtt()));
                        if(Integer.parseInt(test.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing-1).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing-1).getTotal_attended());

                    }
                    else{
                        long m = (min / 60) % 60;
                        long h = (min / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing).getVenue());
                        header_att.setText(test.get(ing).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                        if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                    }
                }
                else {
                    long m = (min / 60) % 60;
                    long h = (min / (60 * 60)) % 24;
                    if(h==0){
                        if(m<10){
                            header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                        }
                    }
                    else{
                        if(m<10){
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                        }
                    }
                    header_sub.setText(test.get(ing).getCourse_name());
                    header_venue.setText("Venue: " + test.get(ing).getVenue());
                    header_att.setText(test.get(ing).getAtt() + "%");
                    header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                    if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    total_class = Integer.parseInt(test.get(ing).getTotal_class());
                    total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                }
                card2.setVisibility(View.VISIBLE);

            }
            todayy.setVisibility(View.VISIBLE);
            adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,test);
            listView.setAdapter(adapter);
        }
        else if(simpleDateFormat.format(datee).equals("Wednesday")){
            Gson gson=new Gson();
            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
            String mon = test1.getString("wednesday",null);
            Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
            ArrayList<Timetable_POJO> test = gson.fromJson(mon,type);
            Collections.sort(test);
            Date d = new Date();
            SimpleDateFormat simpDate;
            simpDate = new SimpleDateFormat("kk:mm:ss");
            String today_time = simpDate.format(d);
            String[] today_ttime = today_time.split(Pattern.quote(":"));
            //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
            Integer ing = -1;
            Integer min = 86400;
            String z = today_ttime[0];
            if(z.equals("24")){z="00";}
            Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
            // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
            for(int q=0;q<test.size();q++){
                String time1 = test.get(q).getTime();
                String[] time2 = time1.split(Pattern.quote(" "));
                String[] time3 = time2[0].split(Pattern.quote(":"));
                Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                Integer diff = time4-today_milli;
                if(diff>=0){
                    if(min>diff){
                        min=diff;
                        ing=q;
                    }
                }
            }
            if(ing==-1){ card4.setVisibility(View.VISIBLE);
                todayy.setText("Earlier Today");}
            else{
                if(ing!=0){
                    String time1 = test.get(ing-1).getTime();
                    String[] time2 = time1.split(Pattern.quote(" "));
                    String[] time3 = time2[0].split(Pattern.quote(":"));
                    Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                    Integer difff = today_milli-time4;
                    if(difff<1200){
                        long m = (difff / 60) % 60;
                        long h = (difff / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing-1).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing-1).getVenue());
                        header_att.setText(test.get(ing-1).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing-1).getAtt()));
                        if(Integer.parseInt(test.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing-1).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing-1).getTotal_attended());

                    }
                    else{
                        long m = (min / 60) % 60;
                        long h = (min / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing).getVenue());
                        header_att.setText(test.get(ing).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                        if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                    }
                }
                else {
                    long m = (min / 60) % 60;
                    long h = (min / (60 * 60)) % 24;
                    if(h==0){
                        if(m<10){
                            header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                        }
                    }
                    else{
                        if(m<10){
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                        }
                    }
                    header_sub.setText(test.get(ing).getCourse_name());
                    header_venue.setText("Venue: " + test.get(ing).getVenue());
                    header_att.setText(test.get(ing).getAtt() + "%");
                    header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                    if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    total_class = Integer.parseInt(test.get(ing).getTotal_class());
                    total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                }
                card2.setVisibility(View.VISIBLE);

            }
            todayy.setVisibility(View.VISIBLE);
            adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,test);
            listView.setAdapter(adapter);
        }
        else if(simpleDateFormat.format(datee).equals("Thursday")){
            Gson gson=new Gson();
            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
            String mon = test1.getString("thursday",null);
            Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
            ArrayList<Timetable_POJO> test = gson.fromJson(mon,type);
            Collections.sort(test);
            Date d = new Date();
            SimpleDateFormat simpDate;
            simpDate = new SimpleDateFormat("kk:mm:ss");
            String today_time = simpDate.format(d);
            String[] today_ttime = today_time.split(Pattern.quote(":"));
            //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
            Integer ing = -1;
            Integer min = 86400;
            String z = today_ttime[0];
            if(z.equals("24")){z="00";}
            Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
            // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
            for(int q=0;q<test.size();q++){
                String time1 = test.get(q).getTime();
                String[] time2 = time1.split(Pattern.quote(" "));
                String[] time3 = time2[0].split(Pattern.quote(":"));
                Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                Integer diff = time4-today_milli;
                if(diff>=0){
                    if(min>diff){
                        min=diff;
                        ing=q;
                    }
                }
            }
            if(ing==-1){ card4.setVisibility(View.VISIBLE);
                todayy.setText("Earlier Today");}
            else{
                if(ing!=0){
                    String time1 = test.get(ing-1).getTime();
                    String[] time2 = time1.split(Pattern.quote(" "));
                    String[] time3 = time2[0].split(Pattern.quote(":"));
                    Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                    Integer difff = today_milli-time4;
                    if(difff<1200){
                        long m = (difff / 60) % 60;
                        long h = (difff / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing-1).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing-1).getVenue());
                        header_att.setText(test.get(ing-1).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing-1).getAtt()));
                        if(Integer.parseInt(test.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing-1).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing-1).getTotal_attended());

                    }
                    else{
                        long m = (min / 60) % 60;
                        long h = (min / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing).getVenue());
                        header_att.setText(test.get(ing).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                        if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                    }
                }
                else {
                    long m = (min / 60) % 60;
                    long h = (min / (60 * 60)) % 24;
                    if(h==0){
                        if(m<10){
                            header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                        }
                    }
                    else{
                        if(m<10){
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                        }
                    }
                    header_sub.setText(test.get(ing).getCourse_name());
                    header_venue.setText("Venue: " + test.get(ing).getVenue());
                    header_att.setText(test.get(ing).getAtt() + "%");
                    header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                    if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    total_class = Integer.parseInt(test.get(ing).getTotal_class());
                    total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                }
                card2.setVisibility(View.VISIBLE);

            }
            todayy.setVisibility(View.VISIBLE);
            adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,test);
            listView.setAdapter(adapter);
        }
        else if(simpleDateFormat.format(datee).equals("Friday")){
            Gson gson=new Gson();
            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
            String mon = test1.getString("friday",null);
            Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
            ArrayList<Timetable_POJO> test = gson.fromJson(mon,type);
            Collections.sort(test);
            Date d = new Date();
            SimpleDateFormat simpDate;
            simpDate = new SimpleDateFormat("kk:mm:ss");
            String today_time = simpDate.format(d);
            String[] today_ttime = today_time.split(Pattern.quote(":"));
            //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
            Integer ing = -1;
            Integer min = 86400;
            String z = today_ttime[0];
            if(z.equals("24")){z="00";}
            Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
            // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
            for(int q=0;q<test.size();q++){
                String time1 = test.get(q).getTime();
                String[] time2 = time1.split(Pattern.quote(" "));
                String[] time3 = time2[0].split(Pattern.quote(":"));
                Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                Integer diff = time4-today_milli;
                if(diff>=0){
                    if(min>diff){
                        min=diff;
                        ing=q;
                    }
                }
            }
            if(ing==-1){ card4.setVisibility(View.VISIBLE);
                todayy.setText("Earlier Today");}
            else{
                if(ing!=0){
                    String time1 = test.get(ing-1).getTime();
                    String[] time2 = time1.split(Pattern.quote(" "));
                    String[] time3 = time2[0].split(Pattern.quote(":"));
                    Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                    Integer difff = today_milli-time4;
                    if(difff<1200){
                        long m = (difff / 60) % 60;
                        long h = (difff / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing-1).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing-1).getVenue());
                        header_att.setText(test.get(ing-1).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing-1).getAtt()));
                        if(Integer.parseInt(test.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing-1).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing-1).getTotal_attended());

                    }
                    else{
                        long m = (min / 60) % 60;
                        long h = (min / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing).getVenue());
                        header_att.setText(test.get(ing).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                        if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                    }
                }
                else {
                    long m = (min / 60) % 60;
                    long h = (min / (60 * 60)) % 24;
                    if(h==0){
                        if(m<10){
                            header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                        }
                    }
                    else{
                        if(m<10){
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                        }
                    }
                    header_sub.setText(test.get(ing).getCourse_name());
                    header_venue.setText("Venue: " + test.get(ing).getVenue());
                    header_att.setText(test.get(ing).getAtt() + "%");
                    header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                    if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    total_class = Integer.parseInt(test.get(ing).getTotal_class());
                    total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                }
                card2.setVisibility(View.VISIBLE);

            }
            todayy.setVisibility(View.VISIBLE);
            adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,test);
            listView.setAdapter(adapter);

        }
        else if(simpleDateFormat.format(datee).equals("Saturday")){
            Gson gson=new Gson();
            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
            String mon = test1.getString("saturday",null);
            Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
            ArrayList<Timetable_POJO> test = gson.fromJson(mon,type);
            Collections.sort(test);
            Date d = new Date();
            SimpleDateFormat simpDate;
            simpDate = new SimpleDateFormat("kk:mm:ss");
            String today_time = simpDate.format(d);
            String[] today_ttime = today_time.split(Pattern.quote(":"));
            //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
            Integer ing = -1;
            Integer min = 86400;
            String z = today_ttime[0];
            if(z.equals("24")){z="00";}
            Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
            // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
            for(int q=0;q<test.size();q++){
                String time1 = test.get(q).getTime();
                String[] time2 = time1.split(Pattern.quote(" "));
                String[] time3 = time2[0].split(Pattern.quote(":"));
                Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                Integer diff = time4-today_milli;
                if(diff>=0){
                    if(min>diff){
                        min=diff;
                        ing=q;
                    }
                }
            }
            if(ing==-1){ card4.setVisibility(View.VISIBLE);
                todayy.setText("Earlier Today");}
            else{
                if(ing!=0){
                    String time1 = test.get(ing-1).getTime();
                    String[] time2 = time1.split(Pattern.quote(" "));
                    String[] time3 = time2[0].split(Pattern.quote(":"));
                    Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                    Integer difff = today_milli-time4;
                    if(difff<1200){
                        long m = (difff / 60) % 60;
                        long h = (difff / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing-1).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing-1).getVenue());
                        header_att.setText(test.get(ing-1).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing-1).getAtt()));
                        if(Integer.parseInt(test.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing-1).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing-1).getTotal_attended());

                    }
                    else{
                        long m = (min / 60) % 60;
                        long h = (min / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing).getVenue());
                        header_att.setText(test.get(ing).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                        if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                    }
                }
                else {
                    long m = (min / 60) % 60;
                    long h = (min / (60 * 60)) % 24;
                    if(h==0){
                        if(m<10){
                            header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                        }
                    }
                    else{
                        if(m<10){
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                        }
                    }
                    header_sub.setText(test.get(ing).getCourse_name());
                    header_venue.setText("Venue: " + test.get(ing).getVenue());
                    header_att.setText(test.get(ing).getAtt() + "%");
                    header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                    if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    total_class = Integer.parseInt(test.get(ing).getTotal_class());
                    total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                }
                card2.setVisibility(View.VISIBLE);

            }
            todayy.setVisibility(View.VISIBLE);
            adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,test);
            listView.setAdapter(adapter);
        }
        else if(simpleDateFormat.format(datee).equals("Sunday")){
            Gson gson=new Gson();
            SharedPreferences test1 = getApplicationContext().getSharedPreferences("timetable", 0);
            String mon = test1.getString("sunday",null);
            Type type = new TypeToken<ArrayList<Timetable_POJO>>(){}.getType();
            ArrayList<Timetable_POJO> test = gson.fromJson(mon,type);
            Collections.sort(test);
            Date d = new Date();
            SimpleDateFormat simpDate;
            simpDate = new SimpleDateFormat("kk:mm:ss");
            String today_time = simpDate.format(d);
            String[] today_ttime = today_time.split(Pattern.quote(":"));
            //   Toast.makeText(getApplicationContext(),String.valueOf(today_time),Toast.LENGTH_LONG).show();
            Integer ing = -1;
            Integer min = 86400;
            String z = today_ttime[0];
            if(z.equals("24")){z="00";}
            Integer today_milli = Integer.parseInt(z)*3600+Integer.parseInt(today_ttime[1])*60+Integer.parseInt(today_ttime[2]);
            // Toast.makeText(getApplicationContext(),String.valueOf(today_milli),Toast.LENGTH_LONG).show();
            for(int q=0;q<test.size();q++){
                String time1 = test.get(q).getTime();
                String[] time2 = time1.split(Pattern.quote(" "));
                String[] time3 = time2[0].split(Pattern.quote(":"));
                Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                Integer diff = time4-today_milli;
                if(diff>=0){
                    if(min>diff){
                        min=diff;
                        ing=q;
                    }
                }
            }
            if(ing==-1){ card4.setVisibility(View.VISIBLE);
                todayy.setText("Earlier Today");}
            else{
                if(ing!=0){
                    String time1 = test.get(ing-1).getTime();
                    String[] time2 = time1.split(Pattern.quote(" "));
                    String[] time3 = time2[0].split(Pattern.quote(":"));
                    Integer time4 = Integer.parseInt(time3[0])*3600+Integer.parseInt(time3[1])*60;
                    Integer difff = today_milli-time4;
                    if(difff<1200){
                        long m = (difff / 60) % 60;
                        long h = (difff / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Departed: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Departed: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing-1).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing-1).getVenue());
                        header_att.setText(test.get(ing-1).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing-1).getAtt()));
                        if(Integer.parseInt(test.get(ing-1).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing-1).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing-1).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing-1).getTotal_attended());

                    }
                    else{
                        long m = (min / 60) % 60;
                        long h = (min / (60 * 60)) % 24;
                        if(h==0){
                            if(m<10){
                                header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                            }
                        }
                        else{
                            if(m<10){
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" 0"+String.valueOf(m)+" minutes");
                            }
                            else{
                                header_time.setText("Time Remaining: "+String.valueOf(h)+" hour"+" "+String.valueOf(m)+" minutes");
                            }
                        }
                        header_sub.setText(test.get(ing).getCourse_name());
                        header_venue.setText("Venue: " + test.get(ing).getVenue());
                        header_att.setText(test.get(ing).getAtt() + "%");
                        header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                        if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                        total_class = Integer.parseInt(test.get(ing).getTotal_class());
                        total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                    }
                }
                else {
                    long m = (min / 60) % 60;
                    long h = (min / (60 * 60)) % 24;
                    if(h==0){
                        if(m<10){
                            header_time.setText("Time Remaining: "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(m)+" minutes");
                        }
                    }
                    else{
                        if(m<10){
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+"0"+String.valueOf(m)+" minutes");
                        }
                        else{
                            header_time.setText("Time Remaining: "+String.valueOf(h)+" hour "+String.valueOf(m)+" minutes");
                        }
                    }
                    header_sub.setText(test.get(ing).getCourse_name());
                    header_venue.setText("Venue: " + test.get(ing).getVenue());
                    header_att.setText(test.get(ing).getAtt() + "%");
                    header_circular.setProgress(Integer.parseInt(test.get(ing).getAtt()));
                    if(Integer.parseInt(test.get(ing).getAtt())>80) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    else if(Integer.parseInt(test.get(ing).getAtt())<75) header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    else  header_circular.setColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                    total_class = Integer.parseInt(test.get(ing).getTotal_class());
                    total_attended = Integer.parseInt(test.get(ing).getTotal_attended());
                }
                card2.setVisibility(View.VISIBLE);

            }
            todayy.setVisibility(View.VISIBLE);
            adapter_home adapter =new adapter_home(Drawer.this,R.layout.daily_schedule,test);
            listView.setAdapter(adapter);

        }

    }
}
