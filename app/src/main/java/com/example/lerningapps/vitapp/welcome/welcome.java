package com.example.lerningapps.vitapp.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lerningapps.vitapp.Drawer;
import com.example.lerningapps.vitapp.MainActivity;
import com.example.lerningapps.vitapp.ObjectSerializer;
import com.example.lerningapps.vitapp.POJO.assignments_courses;
import com.example.lerningapps.vitapp.POJO.exam_catI;
import com.example.lerningapps.vitapp.POJO.exam_catII;
import com.example.lerningapps.vitapp.POJO.exam_fat;
import com.example.lerningapps.vitapp.POJO.facultyall;
import com.example.lerningapps.vitapp.POJO.marks;
import com.example.lerningapps.vitapp.POJO.timetable;
import com.example.lerningapps.vitapp.R;
import com.example.lerningapps.vitapp.timetable.Timetable_POJO;
import com.example.lerningapps.vitapp.timetable.attendance_faculty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class welcome extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        else {
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
            stude.putString("monday",x);
            stude.putString("tuesday",x);
            stude.putString("wednesday",x);
            stude.putString("thursday",x);
            stude.putString("friday",x);
            stude.putString("saturday",x);
            stude.putString("sunday",x);
            stude.commit();

            String xc="";
            SharedPreferences studc = getApplicationContext().getSharedPreferences("curriculum", 0);
            SharedPreferences.Editor studec = studc.edit();
            studec.putString("uc",xc);
            studec.putString("pc",xc);
            studec.putString("pe",xc);
            studec.putString("ue",xc);
            studec.putString("total",xc);
            studec.commit();

            String xx="";
            Type typee = new TypeToken<ArrayList<attendance_faculty>>(){}.getType();
            ArrayList<attendance_faculty> testt = gson.fromJson(xx,typee);
            xx = gson.toJson(testt);
            SharedPreferences studd = getApplicationContext().getSharedPreferences("attendance", 0);
            SharedPreferences.Editor studee = studd.edit();
            studee.putString("attendance",xx);
            studee.commit();

            String xxx="[{\"_id\":\"5a1ede059c6cdf43caa92d64\",\"designation\":\"Senior Professor\",\"division\":\"Department of Electrical Engineering (SELECT)\",\"email\":\"tjayabarathi@vit.ac.in\",\"empid\":\"10020\",\"intercom\":\"2497\",\"name\":\"JAYABARATHI T\",\"open_hours\":[{\"day\":\"\",\"end_time\":\"\",\"start_time\":\"\"},{\"day\":\"\",\"end_time\":\"\",\"start_time\":\"\"}],\"phone\":\"9488276543\",\"room\":\"TT-241-241\",\"school\":\"School of Electrical Engineering\"}]";
            Type typeee = new TypeToken<ArrayList<facultyall>>(){}.getType();
            ArrayList<facultyall> testtt = gson.fromJson(xxx,typeee);
            xxx = gson.toJson(testtt);
            SharedPreferences studdd = getApplicationContext().getSharedPreferences("facultyall", 0);
            SharedPreferences.Editor studeee = studdd.edit();
            studeee.putString("facultyall",xxx);
            studeee.commit();

            String xxxx="";
            Type typeeee = new TypeToken<ArrayList<assignments_courses>>(){}.getType();
            ArrayList<assignments_courses> testttt = gson.fromJson(xxxx,typeeee);
            xxxx = gson.toJson(testttt);
            SharedPreferences studddd = getApplicationContext().getSharedPreferences("assignments", 0);
            SharedPreferences.Editor studeeee = studddd.edit();
            studeeee.putString("assignments",xxxx);
            studeeee.commit();

            String xxxxx="";
            Type typeeeee = new TypeToken<ArrayList<marks>>(){}.getType();
            ArrayList<marks> testtttt = gson.fromJson(xxxxx,typeeeee);
            xxxxx = gson.toJson(testtttt);
            SharedPreferences studdddd = getApplicationContext().getSharedPreferences("marks", 0);
            SharedPreferences.Editor studeeeee = studdddd.edit();
            studeeeee.putString("marks",xxxxx);
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
            stude5.putString("cat1",x5);
            stude5.putString("cat2",x6);
            stude5.putString("fat",x7);
            stude5.commit();


            SharedPreferences pref1 = getApplicationContext().getSharedPreferences("chk", 0);
            SharedPreferences.Editor editor1 = pref1.edit();
            editor1.putString("chk", "");
            editor1.commit();

            String xx1="";
            Type typee1 = new TypeToken<ArrayList<timetable>>(){}.getType();
            ArrayList<timetable> testt1 = gson.fromJson(xx1,typee1);
            xx1 = gson.toJson(testt1);
            SharedPreferences studd1 = getApplicationContext().getSharedPreferences("discussion", 0);
            SharedPreferences.Editor studee1 = studd1.edit();
            studee1.putString("discussion",xx);
            studee1.commit();


            SharedPreferences pref10 = getApplicationContext().getSharedPreferences("schedule_index", 0);
            SharedPreferences.Editor editor10 = pref10.edit();
            editor10.putInt("index", 0);
            editor10.putInt("chk",0);
            editor10.commit();

            SharedPreferences pref11 = getApplicationContext().getSharedPreferences("grade_index", 0);
            SharedPreferences.Editor editor11 = pref11.edit();
            editor11.putInt("index", 0);
            editor11.putInt("chk",0);
            editor11.commit();

            SharedPreferences pref13 = getApplicationContext().getSharedPreferences("curriculum_index", 0);
            SharedPreferences.Editor editor13 = pref13.edit();
            editor13.putInt("index", 0);
            editor13.putInt("chk",0);
            editor13.commit();

            SharedPreferences pref12 = getApplicationContext().getSharedPreferences("exam_index", 0);
            SharedPreferences.Editor editor12 = pref12.edit();
            editor12.putInt("index", 0);
            editor12.putInt("chk",0);
            editor12.commit();

        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome1,
                R.layout.welcome2,
                R.layout.welcome3,
                R.layout.welcome4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("login_credentials", 0);
        String reg_no = pref.getString("reg_no",null);
        String password = pref.getString("password",null);
        if(!reg_no.equals("") && !password.equals(""))
        {
            startActivity(new Intent(welcome.this, Drawer.class));
        }
        else {
            startActivity(new Intent(welcome.this, MainActivity.class));
        }
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}