package com.example.lerningapps.vitapp;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wang.avi.AVLoadingIndicatorView;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_faculty_details extends AppCompatActivity {
    TextView nname,ddivision,sschool,eemail,pphone,rroom,ddesignation,dday1,dday2,sstart1,sstart2,eend1,eend2;
    String naame,scchool,diivision;
    CircleImageView circleImageView;
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
        setContentView(R.layout.activity_faculty_details);
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_faculty1);
        toolbar.setTitle("Faculty Detail");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        String name = getIntent().getStringExtra("name");
        String school = getIntent().getStringExtra("school");
        String division = getIntent().getStringExtra("division");
        String designation = getIntent().getStringExtra("designation");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String room = getIntent().getStringExtra("room");
        String day1=getIntent().getStringExtra("day1");
        String start1=getIntent().getStringExtra("start1");
        String end1=getIntent().getStringExtra("end1");
        String day2=getIntent().getStringExtra("day2");
        String start2=getIntent().getStringExtra("start2");
        String end2=getIntent().getStringExtra("end2");
        String empid=getIntent().getStringExtra("empid");
        StorageReference storageReference  = FirebaseStorage.getInstance().getReference().child("faculty_photo").child(empid+".jpg");
        circleImageView=(CircleImageView)findViewById(R.id.faculty_image);
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this,R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(activity_faculty_details.this,null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Glide.with(activity_faculty_details.this)
                        .load(bytes)
                        .asBitmap()
                        .placeholder(R.drawable.baseline_person_black_48)
                        .into(circleImageView);
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
               progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });


        nname=(TextView)findViewById(R.id.name);
        ddivision=(TextView)findViewById(R.id.division);
        ddesignation=(TextView)findViewById(R.id.designation);
        rroom=(TextView)findViewById(R.id.room);
        pphone=(TextView)findViewById(R.id.phone);
        eemail=(TextView)findViewById(R.id.email);
        sschool=(TextView)findViewById(R.id.school);
        dday1=(TextView)findViewById(R.id.day1);
        dday2=(TextView)findViewById(R.id.day2);
        sstart1=(TextView)findViewById(R.id.start1);
        sstart2=(TextView)findViewById(R.id.start2);
        eend1=(TextView)findViewById(R.id.end1);
        eend2=(TextView)findViewById(R.id.end2);
        nname.setText(name);
        ddivision.setText(division);
        sschool.setText(school);
        ddesignation.setText(designation);
        pphone.setText(phone);
        eemail.setText(email);
        rroom.setText(room);
        dday1.setText(day1);
        dday2.setText(day2);
        sstart1.setText(start1);
        sstart2.setText(start2);
        eend1.setText(end1);
        eend2.setText(end2);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("faculty", 0);
        naame=pref.getString("name",null);
        scchool=pref.getString("school",null);
        diivision=pref.getString("division",null);
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
