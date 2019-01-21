package com.example.lerningapps.vitapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Open_Source extends AppCompatActivity {
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
        setContentView(R.layout.activity_open__source);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_facultyy);
        toolbar.setTitle("Open Source License");
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
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
