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
import android.widget.TextView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.Curriculum_Fragment;
import com.example.lerningapps.vitapp.POJO.Curriculum;
import com.example.lerningapps.vitapp.POJO.Curriculum_PC;
import com.example.lerningapps.vitapp.POJO.Curriculum_PE;
import com.example.lerningapps.vitapp.POJO.Curriculum_Pojo;
import com.example.lerningapps.vitapp.POJO.Curriculum_Total;
import com.example.lerningapps.vitapp.POJO.Curriculum_UC;
import com.example.lerningapps.vitapp.POJO.Curriculum_UE;
import com.example.lerningapps.vitapp.R;
import com.example.lerningapps.vitapp.RequestInterface;
import com.example.lerningapps.vitapp.adapter_curriculum_pc;
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

public class PC extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    Context ctx;
    SwipeRefreshLayout swipeRefreshLayout;
    public PC() {
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
        final TextView pc,pe,uc,ue;
        View v = inflater.inflate(R.layout.fragment4, container, false);
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        listView=(ListView) v.findViewById(R.id.listview);
        pc=(TextView)v.findViewById(R.id.pc);
        pe=(TextView)v.findViewById(R.id.pe);
        uc=(TextView)v.findViewById(R.id.uc);
        ue=(TextView)v.findViewById(R.id.ue);
        Gson gson=new Gson();
        SharedPreferences test1 = getActivity().getSharedPreferences("curriculum", 0);
        String fac = test1.getString("pc",null);

        String fac1 = test1.getString("total",null);
        Type type1 = new TypeToken<ArrayList<Curriculum_Total>>(){}.getType();
        ArrayList<Curriculum_Total> data1 = gson.fromJson(fac1,type1);
        if(data1!=null){
            pc.setText("PC - "+data1.get(0).getCourse());
            pe.setText("PE - "+data1.get(1).getCourse());
            uc.setText("UC - "+data1.get(2).getCourse());
            ue.setText("UE - "+data1.get(3).getCourse());
        }

        Type type = new TypeToken<ArrayList<Curriculum_PC>>(){}.getType();
        ArrayList<Curriculum_PC> data = gson.fromJson(fac,type);
        if(data!=null) {
            adapter_curriculum_pc adapter = new adapter_curriculum_pc(getActivity(), R.layout.adapter_curriculum, data);
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
        Call<Curriculum_Pojo> call = request.getCurriculum(reg_no,password);
        call.enqueue(new Callback<Curriculum_Pojo>() {
            @Override
            public void onResponse(Call<Curriculum_Pojo> call, Response<Curriculum_Pojo> response) {
                if(response.code()==200){
                    Curriculum_Pojo jsonResponse = response.body();
                    ArrayList<Curriculum> data = new ArrayList<>(Arrays.asList(jsonResponse.getCurriculum()));
                    ArrayList<Curriculum_PC> pcs = data.get(0).getProgramme_core();
                    ArrayList<Curriculum_PE> pes = data.get(0).getProgramme_elective();
                    ArrayList<Curriculum_UC> ucs = data.get(0).getUniversity_core();
                    ArrayList<Curriculum_UE> ues = data.get(0).getUniversity_elective();
                    ArrayList<Curriculum_Total> totals = data.get(0).getTotal_credit();
                    Gson gson = new Gson();
                    String pcss= gson.toJson(pcs);
                    String pess= gson.toJson(pes);
                    String ucss= gson.toJson(ucs);
                    String uess= gson.toJson(ues);
                    String totalss= gson.toJson(totals);
                    SharedPreferences test1 = getActivity().getSharedPreferences("curriculum", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("pc",pcss);
                    editortest.putString("pe",pess);
                    editortest.putString("uc",ucss);
                    editortest.putString("ue",uess);
                    editortest.putString("total",totalss);
                    editortest.commit();
                    SharedPreferences pref12 = getActivity().getSharedPreferences("curriculum_index", 0);
                    SharedPreferences.Editor editor12 = pref12.edit();
                    editor12.putInt("index", 2);
                    editor12.putInt("chk", 1);
                    editor12.commit();
                    Curriculum_Fragment fragment = new Curriculum_Fragment();
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
            public void onFailure(Call<Curriculum_Pojo> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
            }
        });
    }
}