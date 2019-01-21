package com.example.lerningapps.vitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Theme extends AppCompatActivity{
    Toolbar toolbar;
    SharedPreferences themee;
    SharedPreferences.Editor editor;
    ImageView app,red,purple,deeporange,yellow,green,teal,pink,deeppurple,orange,grey,bluegrey;
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
        setContentView(R.layout.activity_theme);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_facultyy);
        toolbar.setTitle("Themes");
        setSupportActionBar(toolbar);

        themee = getApplicationContext().getSharedPreferences("theme", 0);
        editor = themee.edit();

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Theme.this,settings.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        app=(ImageView)findViewById(R.id.app);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "app");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });
        red=(ImageView)findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "red");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        purple=(ImageView)findViewById(R.id.purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "purple");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        deeporange=(ImageView)findViewById(R.id.deeporange);
        deeporange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "deeporange");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        yellow=(ImageView)findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "yellow");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        green=(ImageView)findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "green");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        teal=(ImageView)findViewById(R.id.teal);
        teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "teal");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        pink =(ImageView)findViewById(R.id.pink);
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "pink");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        deeppurple=(ImageView)findViewById(R.id.deeppurple);
        deeppurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "deeppurple");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        orange=(ImageView)findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "orange");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        grey=(ImageView)findViewById(R.id.grey);
        grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "grey");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

        bluegrey=(ImageView)findViewById(R.id.bluegrey);
        bluegrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("theme", "bluegrey");
                editor.commit();
                startActivity(new Intent(Theme.this,Theme.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Theme.this,settings.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
