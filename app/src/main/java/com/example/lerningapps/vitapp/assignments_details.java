package com.example.lerningapps.vitapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.lerningapps.vitapp.timetable.assignment_detail_pojo1;

import java.util.ArrayList;

public class assignments_details extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
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
        setContentView(R.layout.activity_assignments_details);
        ArrayList<String> title = getIntent().getStringArrayListExtra("title");
        ArrayList<String> max_mark = getIntent().getStringArrayListExtra("max_mark");
        ArrayList<String> weightage = getIntent().getStringArrayListExtra("weightage");
        ArrayList<String> due_date = getIntent().getStringArrayListExtra("due_date");
        ArrayList<String> status = getIntent().getStringArrayListExtra("status");
        ArrayList<assignment_detail_pojo1> assignmentDetailPojo1 = new ArrayList<>();
        for(int i=0;i<title.size();i++){
            assignment_detail_pojo1 assignment_detail_pojo1 = new assignment_detail_pojo1(title.get(i),due_date.get(i),max_mark.get(i),weightage.get(i),status.get(i));
            assignmentDetailPojo1.add(assignment_detail_pojo1);
        }
        listView = (ListView) findViewById(R.id.listi);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_facultyy);
        toolbar.setTitle("Assignment Details");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        assignment_detail_adapter assignmentDetailAdapter = new assignment_detail_adapter(this,R.layout.assignment_detail_adapter,assignmentDetailPojo1);
        listView.setAdapter(assignmentDetailAdapter);
    }
}
