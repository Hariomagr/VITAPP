package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.lerningapps.vitapp.timetable.mark_detail_pojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class adapter_marks_details extends AppCompatActivity {
    ListView listView;
    android.support.v7.widget.Toolbar toolbar;
    Double wei=0.0,per=0.0;
    Double total_wei=0.0;
    ArrayList<String> avgg=new ArrayList<>();
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
        setContentView(R.layout.activity_adapter_marks_details);
        final ArrayList<String> title = getIntent().getStringArrayListExtra("title");
        final ArrayList<String> max_mark = getIntent().getStringArrayListExtra("max_mark");
        final ArrayList<String> weightage = getIntent().getStringArrayListExtra("weightage");
        final ArrayList<String> percentage = getIntent().getStringArrayListExtra("percentage");
        final ArrayList<String> status = getIntent().getStringArrayListExtra("status");
        final ArrayList<String> scored = getIntent().getStringArrayListExtra("scored");
        String class_number = getIntent().getStringExtra("class");
        String course_code = getIntent().getStringExtra("code");
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this,R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(adapter_marks_details.this,null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final ArrayList<Integer> sort = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(class_number+course_code);
        for(int i=0;i<title.size();i++) {
            final Double[] totalmark = {0.00};
            final Integer[] total = {0};
            final int finalI = i;
            databaseReference.child(title.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    sort.add(finalI);
                 //   Log.d("dfgdf",title.get(finalI)+" "+finalI);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.child("mark").exists()) {
                            String x = snapshot.child("mark").getValue(String.class);
                            totalmark[0] = totalmark[0] + Double.parseDouble(x);
                            total[0] = total[0] + 1;
                        }
                    }
                    if (total[0] != 0) {
                        avgg.add(String.valueOf(totalmark[0] / Double.parseDouble(String.valueOf(total[0]))) + "/" + String.valueOf(total[0]));
                    }
                    else{
                        avgg.add(String.valueOf(totalmark[0] / Double.parseDouble(String.valueOf("1"))) + "/" + String.valueOf("1"));
                    }
                    if(avgg.size()==title.size()) {

                        ArrayList<mark_detail_pojo> markDetailPojos = new ArrayList<>();
                        for(int in=0;in<title.size();in++){
                            if(title.get(in).equals("Additional Learning")){
                                per = per + Double.parseDouble(percentage.get(in));
                            }
                            else {
                                per = per + Double.parseDouble(percentage.get(in));
                                wei = wei + Double.parseDouble(weightage.get(in));
                            }
                            String[] xx = avgg.get(sort.indexOf(in)).split(Pattern.quote("/"));
                            Double x = Double.parseDouble(xx[0]);
                            total_wei = total_wei + (Double.parseDouble(String.valueOf(weightage.get(in)))*Double.parseDouble(String.valueOf(x)))/Double.parseDouble(max_mark.get(in));
                            mark_detail_pojo mark_detail_pojo = new mark_detail_pojo(title.get(in),max_mark.get(in),status.get(in),percentage.get(in),scored.get(in),weightage.get(in),String.valueOf(x)+"/"+xx[1]);
                            markDetailPojos.add(mark_detail_pojo);
                        }
                        listView = (ListView) findViewById(R.id.listi);
                        mark_detail_adapter markDetailAdapter = new mark_detail_adapter(adapter_marks_details.this,R.layout.adapter_mark_detail,markDetailPojos);
                        listView.setAdapter(markDetailAdapter);
                        View parentLayout = (View)findViewById(android.R.id.content);
                        Snackbar snackbar = Snackbar
                                .make(parentLayout, "Total: "+String.valueOf(per)+"/"+String.valueOf((wei)), Snackbar.LENGTH_LONG)
                                .setAction("Avg: " + String.valueOf((total_wei))+"/"+String.valueOf(wei), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    avgg.clear();
                    for(int in=0;in<title.size();in++)avgg.add("");
                    ArrayList<mark_detail_pojo> markDetailPojos = new ArrayList<>();
                    for(int in=0;in<title.size();in++){
                        mark_detail_pojo mark_detail_pojo = new mark_detail_pojo(title.get(in),max_mark.get(in),status.get(in),percentage.get(in),scored.get(in),weightage.get(in),avgg.get(in));
                        markDetailPojos.add(mark_detail_pojo);
                    }
                    listView = (ListView) findViewById(R.id.listi);
                    mark_detail_adapter markDetailAdapter = new mark_detail_adapter(adapter_marks_details.this,R.layout.adapter_mark_detail,markDetailPojos);
                    listView.setAdapter(markDetailAdapter);
                    progressDialog.dismiss();
                }
            });
        }
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_facultyy);
        toolbar.setTitle("Marks Details");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View parentLayout = (View)findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(parentLayout, "Total: "+String.valueOf(per)+"/"+String.valueOf(wei), Snackbar.LENGTH_LONG)
                .setAction("Avg: " + String.valueOf(total_wei) + "/" + String.valueOf(wei), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        return true;
    }
}
