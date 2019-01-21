package com.example.lerningapps.vitapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.ObjectSerializer;
import com.example.lerningapps.vitapp.POJO.grade_details;
import com.example.lerningapps.vitapp.POJO.grades;
import com.example.lerningapps.vitapp.POJO.grades_pojo;
import com.example.lerningapps.vitapp.R;
import com.example.lerningapps.vitapp.RequestInterface;
import com.example.lerningapps.vitapp.grade_adapter;
import com.example.lerningapps.vitapp.grade_fragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class grade_history extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    Context ctx;
    SwipeRefreshLayout swipeRefreshLayout;
    public grade_history() {
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
        SharedPreferences pref = getActivity().getSharedPreferences("exam_grade", 0);
        SharedPreferences preff = this.getActivity().getSharedPreferences("exam_grade", 0);
        ArrayList<String>code=null,title=null,credit=null,grade=null;
        try {
            title = (ArrayList) ObjectSerializer.deserialize(preff.getString("data01", ObjectSerializer.serialize(new ArrayList())));
            code = (ArrayList) ObjectSerializer.deserialize(preff.getString("data02", ObjectSerializer.serialize(new ArrayList())));
            credit = (ArrayList) ObjectSerializer.deserialize(preff.getString("data03", ObjectSerializer.serialize(new ArrayList())));
            grade = (ArrayList) ObjectSerializer.deserialize(preff.getString("data04", ObjectSerializer.serialize(new ArrayList())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(title.size()>0) {
            ArrayList<grade_details> gradeDetails = new ArrayList<>();
            for (int i = 0; i < title.size(); i++) {
                grade_details grade_details = new grade_details(code.get(i),title.get(i),grade.get(i),credit.get(i));
                gradeDetails.add(grade_details);
            }
            grade_adapter adapter = new grade_adapter(getActivity(), R.layout.grade_adapter, gradeDetails);
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
        SharedPreferences pref = getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<grades_pojo> call = request.getgrades(reg_no,password,semester);
        call.enqueue(new Callback<grades_pojo>() {
            @Override
            public void onResponse(Call<grades_pojo> call, Response<grades_pojo> response) {
                if(response.code()==200){
                    grades_pojo jsonResponse = response.body();
                    ArrayList<grades> data = new ArrayList<>(Arrays.asList(jsonResponse.getGrades()));
                    ArrayList<grade_details> gradeDetails = data.get(0).getGrades();
                    String credit_reg = data.get(0).getCredits_registered();
                    String credit_ear = data.get(0).getCredits_earned();
                    String cgpa = data.get(0).getCgpa();
                    ArrayList<String> title= new ArrayList<>();
                    ArrayList<String> code= new ArrayList<>();
                    ArrayList<String> credit= new ArrayList<>();
                    ArrayList<String> grade= new ArrayList<>();

                    for(int i = 0; i< gradeDetails.size(); i++){
                        title.add(gradeDetails.get(i).getCourse_title());
                        code.add(gradeDetails.get(i).getCourse_code());
                        credit.add(gradeDetails.get(i).getCredits());
                        grade.add(gradeDetails.get(i).getGrade());
                    }
                    SharedPreferences prefdata = getActivity().getSharedPreferences("exam_grade", 0);
                    SharedPreferences.Editor editorr = prefdata.edit();
                    ObjectSerializer objectSerializer=new ObjectSerializer();


                    try {
                        editorr.putString("cgpa",cgpa);
                        editorr.putString("credits",credit_ear+"/"+credit_reg);
                        editorr.putString("data01", objectSerializer.serialize(title));
                        editorr.putString("data02", objectSerializer.serialize(code));
                        editorr.putString("data03", objectSerializer.serialize(credit));
                        editorr.putString("data04", objectSerializer.serialize(grade));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    editorr.commit();
                    SharedPreferences pref11 = getActivity().getSharedPreferences("grade_index", 0);
                    SharedPreferences.Editor editor11 = pref11.edit();
                    editor11.putInt("index", 1);
                    editor11.putInt("chk",1);
                    editor11.commit();
                    grade_fragment fragment = new grade_fragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.commit();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    },200);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<grades_pojo> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
            }
        });
    }
}