package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.facultyall;
import com.example.lerningapps.vitapp.POJO.facultyall_pojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_faculty_list extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<facultyall> data,finaldata,temp;
    ProgressDialog progressDialog;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    String namee,school,division;
    private SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.Toolbar toolbar;
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
        setContentView(R.layout.activity_faculty_list);
        listView = (ListView) findViewById(R.id.listv);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_faculty);
        toolbar.setTitle("Faculty Name");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(activity_faculty_list.this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        finaldata = new ArrayList<>();

        namee = getIntent().getStringExtra("name");
        if (namee.equals("1")) {
            school = getIntent().getStringExtra("school");
        } else if (namee.equals("2")) {
            division = getIntent().getStringExtra("division");
        }
        facul();
        loadJSON();
    }
    private void loadJSON() {
        progressDialog=new ProgressDialog(this,R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(activity_faculty_list.this,null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<facultyall_pojo> call = request.getfacultyall();
        call.enqueue(new Callback<facultyall_pojo>() {
            @Override
            public void onResponse(Call<facultyall_pojo> call, Response<facultyall_pojo> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.code() == 200) {
                    facultyall_pojo jsonResponse = response.body();
                    temp = new ArrayList<>();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getFaculty()));
                    Gson gson = new Gson();
                    String dataa= gson.toJson(data);
                    SharedPreferences test1 = getApplicationContext().getSharedPreferences("facultyall", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("facultyall",dataa);
                    editortest.commit();
                    Log.d("sdgsf",dataa);
                    if (namee.equals("1")) {
                        for (Integer i = 0; i < data.size(); i++) {
                            if (data.get(i).getSchool().equals(school)) {
                                temp.add(data.get(i));
                            }
                        }
                    } else if (namee.equals("2")) {
                        for (Integer i = 0; i < data.size(); i++) {
                            if (data.get(i).getDivision().equals(division)) {
                                temp.add(data.get(i));
                            }
                        }
                    } else if (namee.equals("0")) {
                        for (Integer i = 0; i < data.size(); i++) {
                            temp.add(data.get(i));
                        }
                    }

                    SwipeRecycleViewAdapter adapter = new SwipeRecycleViewAdapter(activity_faculty_list.this, R.layout.list_faculty, temp);
                    listView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                }
                else{
                 //   facul();
                    swipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<facultyall_pojo> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainclass, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.searchh);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("Search Faculty");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                finaldata.clear();
                if(newText.length()==0){
                    finaldata.addAll(temp);
                }
                else{
                    for(facultyall faculty:temp){
                        if(faculty.getName().toLowerCase().contains(newText)){
                            finaldata.add(faculty);
                            SwipeRecycleViewAdapter adapterr =new SwipeRecycleViewAdapter(activity_faculty_list.this,R.layout.list_faculty,finaldata);
                            listView.setAdapter(adapterr);
                        }
                    }
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        loadJSON();
    }
    public void facul(){
        Gson gson=new Gson();
        SharedPreferences test1 = getApplicationContext().getSharedPreferences("facultyall", 0);
        String fac = test1.getString("facultyall",null);
        Type type = new TypeToken<ArrayList<facultyall>>(){}.getType();
        ArrayList<facultyall> data = gson.fromJson(fac,type);
        temp = new ArrayList<>();
        if(data!=null) {
            if (namee.equals("1")) {
                for (Integer i = 0; i < data.size(); i++) {
                    if (data.get(i).getSchool().equals(school)) {
                        temp.add(data.get(i));
                    }
                }
            } else if (namee.equals("2")) {
                for (Integer i = 0; i < data.size(); i++) {
                    if (data.get(i).getDivision().equals(division)) {
                        temp.add(data.get(i));
                    }
                }
            } else if (namee.equals("0")) {
                for (Integer i = 0; i < data.size(); i++) {
                    temp.add(data.get(i));
                }
            }
        }
        SwipeRecycleViewAdapter adapter = new SwipeRecycleViewAdapter(activity_faculty_list.this, R.layout.list_faculty, temp);
        listView.setAdapter(adapter);
    }
}
