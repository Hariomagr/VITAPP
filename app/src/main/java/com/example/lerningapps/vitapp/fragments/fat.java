package com.example.lerningapps.vitapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.exam_catI;
import com.example.lerningapps.vitapp.POJO.exam_catII;
import com.example.lerningapps.vitapp.POJO.exam_fat;
import com.example.lerningapps.vitapp.POJO.exam_schedule;
import com.example.lerningapps.vitapp.POJO.exam_schedule_pojo;
import com.example.lerningapps.vitapp.R;
import com.example.lerningapps.vitapp.RequestInterface;
import com.example.lerningapps.vitapp.adapter_exam_venue2;
import com.example.lerningapps.vitapp.exam_venue_fragment;
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

public class fat extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    Context ctx;
    SwipeRefreshLayout swipeRefreshLayout;
    public fat() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ListView listView;
        View v = inflater.inflate(R.layout.fragment, container, false);
        listView=(ListView) v.findViewById(R.id.listview);
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        Gson gson=new Gson();
        SharedPreferences test1 = getActivity().getSharedPreferences("exam_sch", 0);
        String fac = test1.getString("fat",null);
        Type type = new TypeToken<ArrayList<exam_fat>>(){}.getType();
        ArrayList<exam_fat> data = gson.fromJson(fac,type);
        if(data!=null) {
            adapter_exam_venue2 adapter = new adapter_exam_venue2(getActivity(), R.layout.exam_venue_adapter, data);
            listView.setAdapter(adapter);
        }
        return v;
    }
    @Override
    public void onRefresh() {
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getContext(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getActivity(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        swipeRefreshLayout.setRefreshing(true);
        SharedPreferences pref = getContext().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<exam_schedule_pojo> call = request.getexamschedule(reg_no,password,semester);
        call.enqueue(new Callback<exam_schedule_pojo>() {
            @Override
            public void onResponse(Call<exam_schedule_pojo> call, Response<exam_schedule_pojo> response) {
                if(response.code()==200){
                    exam_schedule_pojo jsonResponse = response.body();
                    ArrayList<exam_schedule> data = new ArrayList<>(Arrays.asList(jsonResponse.getExam_schedule()));
                    ArrayList<exam_catI> cat1 = data.get(0).getCAT_I();
                    ArrayList<exam_catII> cat2 = data.get(0).getCAT_II();
                    ArrayList<exam_fat> fat = data.get(0).getFinal_Assessment_Test();
                    Gson gson = new Gson();
                    String cat11= gson.toJson(cat1);
                    String cat22= gson.toJson(cat2);
                    String fat11= gson.toJson(fat);
                    SharedPreferences test1 = getActivity().getApplicationContext().getSharedPreferences("exam_sch", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("cat1",cat11);
                    editortest.putString("cat2",cat22);
                    editortest.putString("fat",fat11);
                    editortest.commit();
                    SharedPreferences pref11 = getActivity().getSharedPreferences("exam_index", 0);
                    SharedPreferences.Editor editor11 = pref11.edit();
                    editor11.putInt("index", 2);
                    editor11.putInt("chk", 1);
                    editor11.commit();
                    exam_venue_fragment fragment = new exam_venue_fragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<exam_schedule_pojo> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
            }
        });
    }
}