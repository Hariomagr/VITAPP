package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.example.lerningapps.vitapp.POJO.Student_details;
import com.example.lerningapps.vitapp.POJO.attendance;
import com.example.lerningapps.vitapp.POJO.timetable;
import com.example.lerningapps.vitapp.POJO.timetable_pojo;
import com.example.lerningapps.vitapp.timetable.attendance_faculty;
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

public class attendance_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    Context ctx;
    private ArrayList<Student_details> data;
    private ArrayList<timetable> stu_time = new ArrayList<>();
    private ArrayList<attendance> stu_att = new ArrayList<>();
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    public attendance_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(getActivity(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getActivity(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        View v = inflater.inflate(R.layout.fragment1, container, false);
        listView = (ListView) v.findViewById(R.id.listview21);
        attendance();
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        SharedPreferences pref = this.getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<timetable_pojo> call = request.gettimetable(reg_no,password,semester);

        final ArrayList<String> total_class = new ArrayList<>();
        final ArrayList<String> course_codeee = new ArrayList<>();
        final ArrayList<String> status= new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();
        final ArrayList<String> slot = new ArrayList<>();
        final ArrayList<String> title= new ArrayList<>();
        final ArrayList<String> attendance_per = new ArrayList<>();
        final ArrayList<String> slott = new ArrayList<>();
        final ArrayList<String> attended = new ArrayList<>();
        final ArrayList<String> type = new ArrayList<>();
        final ArrayList<String> name = new ArrayList<>();


        call.enqueue(new Callback<timetable_pojo>() {
            @Override
            public void onResponse(Call<timetable_pojo> call, Response<timetable_pojo> response) {
                if(response.code()==200) {
                    timetable_pojo jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getStudent_details()));
                    stu_time = data.get(0).getTimetable();
                    stu_att = data.get(0).getAttendance();
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
                    SharedPreferences test2 = getActivity().getSharedPreferences("attendance", 0);
                    SharedPreferences.Editor editortest1 = test2.edit();
                    editortest1.putString("attendance",attendanc);
                    editortest1.commit();
                    adapter_attendance adapter = new adapter_attendance(getActivity(), R.layout.attendance_fragment, fa);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();
                }
                else Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
              // attendance();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<timetable_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
              // attendance();
                progressDialog.dismiss();
            }
        });
        return v;
    }
    public void attendance(){

        Gson gson=new Gson();
        SharedPreferences test1 = getActivity().getSharedPreferences("attendance", 0);
        String att = test1.getString("attendance",null);
        Type type = new TypeToken<ArrayList<attendance_faculty>>(){}.getType();
        ArrayList<attendance_faculty> fa = gson.fromJson(att,type);
        adapter_attendance adapter = new adapter_attendance(getActivity(), R.layout.attendance_fragment, fa);
        listView.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getActivity(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getActivity(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        SharedPreferences pref = this.getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<timetable_pojo> call = request.gettimetable(reg_no,password,semester);

        final ArrayList<String> total_class = new ArrayList<>();
        final ArrayList<String> course_codeee = new ArrayList<>();
        final ArrayList<String> status= new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();
        final ArrayList<String> slot = new ArrayList<>();
        final ArrayList<String> title= new ArrayList<>();
        final ArrayList<String> attendance_per = new ArrayList<>();
        final ArrayList<String> slott = new ArrayList<>();
        final ArrayList<String> attended = new ArrayList<>();
        final ArrayList<String> type = new ArrayList<>();
        final ArrayList<String> name = new ArrayList<>();


        call.enqueue(new Callback<timetable_pojo>() {
            @Override
            public void onResponse(Call<timetable_pojo> call, Response<timetable_pojo> response) {
                if(response.code()==200) {
                    timetable_pojo jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getStudent_details()));
                    stu_time = data.get(0).getTimetable();
                    stu_att = data.get(0).getAttendance();
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
                    SharedPreferences test2 = getActivity().getSharedPreferences("attendance", 0);
                    SharedPreferences.Editor editortest1 = test2.edit();
                    editortest1.putString("attendance",attendanc);
                    editortest1.commit();
                    adapter_attendance adapter = new adapter_attendance(getActivity(), R.layout.attendance_fragment, fa);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
              //  attendance();
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<timetable_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
               // attendance();
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
