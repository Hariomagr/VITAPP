package com.example.lerningapps.vitapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.ObjectSerializer;
import com.example.lerningapps.vitapp.POJO.grade_details;
import com.example.lerningapps.vitapp.POJO.grades;
import com.example.lerningapps.vitapp.POJO.grades_pojo;
import com.example.lerningapps.vitapp.R;
import com.example.lerningapps.vitapp.RequestInterface;
import com.example.lerningapps.vitapp.cgpa_adapter;
import com.example.lerningapps.vitapp.grade_fragment;
import com.example.lerningapps.vitapp.timetable.cgpa_calc;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class current_grade extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    Context ctx;
    public static TextView cg,gp;
    SwipeRefreshLayout swipeRefreshLayout;
    public current_grade() {
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
        View v = inflater.inflate(R.layout.fragment2, container, false);
        listView=(ListView) v.findViewById(R.id.listview);
        cg=(TextView)v.findViewById(R.id.cgpa);
        gp=(TextView)v.findViewById(R.id.gpa);
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        SharedPreferences pref = getActivity().getSharedPreferences("exam_grade", 0);
        final String cgpa1 = pref.getString("cgpa",null);
        cg.setText(cgpa1);
        Integer total_sem_credit=0;
        final String credits1 = pref.getString("credits",null);
        SharedPreferences preff = this.getActivity().getSharedPreferences("cgpa_calc", 0);
        ArrayList<String> sub = null;
        ArrayList<Integer> cre = null;
        try {
            sub = (ArrayList) ObjectSerializer.deserialize(preff.getString("sub", ObjectSerializer.serialize(new ArrayList())));
            cre = (ArrayList) ObjectSerializer.deserialize(preff.getString("cre", ObjectSerializer.serialize(new ArrayList())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<cgpa_calc> cgpa_calcs = new ArrayList<>();
        if(sub.size()>0) {
            for (int i = 0; i < sub.size(); i++) {
                String tit = sub.get(i);
                String[] xx = tit.split(Pattern.quote(" "));
                String xxx = "";
                for (int j = 1; j < xx.length; j++) {
                    xxx = xxx + xx[j] + " ";
                }
                String code = xx[0];
                String title = xxx;
                Integer credit = cre.get(i);
                total_sem_credit=total_sem_credit+cre.get(i);
                cgpa_calc cgpa_calc = new cgpa_calc(title, code, credit);
                cgpa_calcs.add(cgpa_calc);
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("total_sem_credit", total_sem_credit);
            editor.commit();
            cgpa_adapter adapter = new cgpa_adapter(getActivity(), R.layout.cgpa_adapter, cgpa_calcs);
            listView.setAdapter(adapter);
        }
        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar snackbar = Snackbar
                            .make(parentLayout, "Credits: "+credits1, Snackbar.LENGTH_LONG)
                            .setAction("CGPA: "+cgpa1, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
                return true;
            }
        });
        return v;

    }
    public static void cr(String c,String g){
        cg.setText(c);
        gp.setText(g);
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
                    editor11.putInt("index", 0);
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