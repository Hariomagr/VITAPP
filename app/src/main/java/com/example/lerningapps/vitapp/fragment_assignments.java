package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.assignments_courses;
import com.example.lerningapps.vitapp.POJO.assignments_pojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import org.angmarch.views.NiceSpinner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragment_assignments extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private NiceSpinner spinners,spinnerd;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    public fragment_assignments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assignments, container, false);
        listView=(ListView) v.findViewById(R.id.listvv);
        assign();
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        SharedPreferences pref = getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getActivity(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getActivity(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<assignments_pojo> call = request.getassignments(reg_no,password,semester);
        call.enqueue(new Callback<assignments_pojo>() {
            @Override
            public void onResponse(Call<assignments_pojo> call, Response<assignments_pojo> response) {
                if(response.code()==200){
                    assignments_pojo jsonResponse=response.body();
                    ArrayList<assignments_courses> assignmentsCourses =   new ArrayList<>(Arrays.asList(jsonResponse.getCourses()));
                    Gson gson = new Gson();
                    String dataa= gson.toJson(assignmentsCourses);
                    SharedPreferences test1 = getActivity().getSharedPreferences("assignments", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("assignments",dataa);
                    editortest.commit();
                    assignments_adapter adapter = new assignments_adapter(getActivity(), R.layout.adapter_assignments, assignmentsCourses);
                    listView.setAdapter(adapter);

                }
                else{
                  //  assign();
                    Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<assignments_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
               // assign();
                progressDialog.dismiss();
            }
        });
        return v;
    }
    public void assign(){
        Gson gson=new Gson();
        SharedPreferences test1 = getActivity().getSharedPreferences("assignments", 0);
        String assignm = test1.getString("assignments",null);
        Type type = new TypeToken<ArrayList<assignments_courses>>(){}.getType();
        ArrayList<assignments_courses> data = gson.fromJson(assignm,type);
        if(data!=null) {
            assignments_adapter adapter = new assignments_adapter(getActivity(), R.layout.adapter_assignments, data);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        SharedPreferences pref = getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getActivity(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getActivity(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<assignments_pojo> call = request.getassignments(reg_no,password,semester);
        call.enqueue(new Callback<assignments_pojo>() {
            @Override
            public void onResponse(Call<assignments_pojo> call, Response<assignments_pojo> response) {
                if(response.code()==200){
                    assignments_pojo jsonResponse=response.body();
                    ArrayList<assignments_courses> assignmentsCourses =   new ArrayList<>(Arrays.asList(jsonResponse.getCourses()));
                    Gson gson = new Gson();
                    String dataa= gson.toJson(assignmentsCourses);
                    SharedPreferences test1 = getActivity().getSharedPreferences("assignments", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("assignments",dataa);
                    editortest.commit();
                    assignments_adapter adapter = new assignments_adapter(getActivity(), R.layout.adapter_assignments, assignmentsCourses);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);

                }
                else{
                   // assign();
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<assignments_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
               // assign();
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
