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

import com.example.lerningapps.vitapp.POJO.Student_details;
import com.example.lerningapps.vitapp.POJO.timetable;
import com.example.lerningapps.vitapp.POJO.timetable_pojo;
import com.example.lerningapps.vitapp.firebase.time_fire;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class fragment_discussion extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private NiceSpinner spinners,spinnerd;
    ListView listView;
    ArrayList<time_fire> timeFires;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    public fragment_discussion() {
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
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        SharedPreferences pref = getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        progressDialog=new ProgressDialog(getActivity(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getActivity(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        assign();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<timetable_pojo> call = request.gettimetable(reg_no,password,semester);
        call.enqueue(new Callback<timetable_pojo>() {
            @Override
            public void onResponse(Call<timetable_pojo> call, Response<timetable_pojo> response) {
                if(response.code()==200){
                    timetable_pojo jsonResponse=response.body();
                    ArrayList<Student_details> timetable_pojos =   new ArrayList<>(Arrays.asList(jsonResponse.getStudent_details()));
                    ArrayList<timetable> timetables = new ArrayList<>();
                    timetables = timetable_pojos.get(0).getTimetable();
                    Gson gson = new Gson();
                    String dataa= gson.toJson(timetables);
                    SharedPreferences test1 = getActivity().getSharedPreferences("discussion", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("discussion",dataa);
                    editortest.commit();
                    final ArrayList<String> xx1 = new ArrayList<>();
                    timeFires = new ArrayList<>();
                    for(int k=0;k<timetables.size();k++){
                        final ArrayList<timetable> finalTimetables = timetables;
                        final int finalK = k;
                        final ArrayList<timetable> finalTimetables1 = timetables;
                        final int finalK1 = k;
                        FirebaseDatabase.getInstance().getReference(timetables.get(k).getClass_number()+timetables.get(k).getCourse_code()).child("Chat_Data")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String reg="",chat="",chk="";
                                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                            if(snapshot.exists()) {
                                                reg = snapshot.child("reg_no").getValue(String.class);
                                                chat = snapshot.child("chat").getValue(String.class);
                                                chk = snapshot.child("chk").getValue(String.class);
                                            }
                                        }
                                        time_fire timeFire;
                                        if(chk.equals("0")){
                                             timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),reg+": "+chat);
                                        }
                                        else if(chk.equals("1")){
                                             timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),reg+": "+"Image");
                                        }
                                        else if(chk.equals("2")){
                                             timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),reg+": "+"Document");
                                        }
                                        else {
                                            timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),"");
                                        }
                                        xx1.add("A");
                                        timeFires.add(timeFire);
                                        if(xx1.size() == finalTimetables.size()){
                                            discussion_adapter adapter = new discussion_adapter(getActivity(), R.layout.adapter_discussion, timeFires);
                                            adapter.notifyDataSetChanged();
                                            listView.setAdapter(adapter);
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        time_fire timeFire;
                                        timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),"");
                                        timeFires.add(timeFire);
                                        xx1.add("A");
                                        if(xx1.size() == finalTimetables.size()){
                                            discussion_adapter adapter = new discussion_adapter(getActivity(), R.layout.adapter_discussion, timeFires);
                                            adapter.notifyDataSetChanged();
                                            listView.setAdapter(adapter);
                                        }
                                        progressDialog.dismiss();
                                    }
                                });
                    }


                }
                else{
                  //  assign();
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<timetable_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
              //  assign();
                progressDialog.dismiss();
            }
        });
        return v;
    }
    public void assign(){
        Gson gson=new Gson();
        SharedPreferences test1 = getActivity().getSharedPreferences("discussion", 0);
        String assignm = test1.getString("discussion",null);
        Type type = new TypeToken<ArrayList<timetable>>(){}.getType();
        ArrayList<timetable> data = gson.fromJson(assignm,type);
        if(data!=null) {
            timeFires = new ArrayList<>();
            final ArrayList<String> xx1 = new ArrayList<>();
            for(int k=0;k<data.size();k++){
                final ArrayList<timetable> finalTimetables = data;
                final int finalK = k;
                time_fire timeFire;
                timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),"");
                timeFires.add(timeFire);
            }
            discussion_adapter adapter = new discussion_adapter(getActivity(), R.layout.adapter_discussion, timeFires);
            adapter.notifyDataSetChanged();
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
        Call<timetable_pojo> call = request.gettimetable(reg_no,password,semester);
        call.enqueue(new Callback<timetable_pojo>() {
            @Override
            public void onResponse(Call<timetable_pojo> call, Response<timetable_pojo> response) {
                if(response.code()==200){
                    timetable_pojo jsonResponse=response.body();
                    ArrayList<Student_details> timetable_pojos =   new ArrayList<>(Arrays.asList(jsonResponse.getStudent_details()));
                    ArrayList<timetable> timetables = new ArrayList<>();
                    timetables = timetable_pojos.get(0).getTimetable();
                    Gson gson = new Gson();
                    String dataa= gson.toJson(timetables);
                    SharedPreferences test1 = getActivity().getSharedPreferences("discussion", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("discussion",dataa);
                    editortest.commit();
                    final ArrayList<String> xx1 = new ArrayList<>();
                    timeFires = new ArrayList<>();
                    for(int k=0;k<timetables.size();k++){
                        final ArrayList<timetable> finalTimetables = timetables;
                        final int finalK = k;
                        final ArrayList<timetable> finalTimetables1 = timetables;
                        final int finalK1 = k;
                        FirebaseDatabase.getInstance().getReference(timetables.get(k).getClass_number()+timetables.get(k).getCourse_code()).child("Chat_Data")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String reg="",chat="",chk="";
                                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                            if(snapshot.exists()) {
                                                reg = snapshot.child("reg_no").getValue(String.class);
                                                chat = snapshot.child("chat").getValue(String.class);
                                                chk = snapshot.child("chk").getValue(String.class);
                                            }
                                        }
                                        time_fire timeFire;
                                        if(chk.equals("0")){
                                            timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),reg+": "+chat);
                                        }
                                        else if(chk.equals("1")){
                                            timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),reg+": "+"Image");
                                        }
                                        else if(chk.equals("2")){
                                            timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),reg+": "+"Document");
                                        }
                                        else {
                                            timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),"");
                                        }
                                        xx1.add("A");
                                        timeFires.add(timeFire);
                                        if(xx1.size() == finalTimetables.size()){
                                            discussion_adapter adapter = new discussion_adapter(getActivity(), R.layout.adapter_discussion, timeFires);
                                            adapter.notifyDataSetChanged();
                                            listView.setAdapter(adapter);
                                            progressDialog.dismiss();
                                            swipeRefreshLayout.setRefreshing(false);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        time_fire timeFire;
                                        timeFire = new time_fire(finalTimetables.get(finalK).getLtpjc(),finalTimetables.get(finalK).getFaculty_name(),finalTimetables.get(finalK).getCourse_code(),finalTimetables.get(finalK).getCourse_option(),finalTimetables.get(finalK).getCourse_mode(),finalTimetables.get(finalK).getCourse_name(),finalTimetables.get(finalK).getVenue(),finalTimetables.get(finalK).getSlot(),finalTimetables.get(finalK).getCourse_type(),finalTimetables.get(finalK).getClass_number(),"");
                                        timeFires.add(timeFire);
                                        xx1.add("A");
                                        if(xx1.size() == finalTimetables.size()){
                                            discussion_adapter adapter = new discussion_adapter(getActivity(), R.layout.adapter_discussion, timeFires);
                                            adapter.notifyDataSetChanged();
                                            listView.setAdapter(adapter);
                                        }
                                        progressDialog.dismiss();
                                    }
                                });
                    }


                }
                else{
                  //  assign();
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<timetable_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Occurred",Toast.LENGTH_LONG).show();
              //  assign();
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
            }
        });
    }
}
