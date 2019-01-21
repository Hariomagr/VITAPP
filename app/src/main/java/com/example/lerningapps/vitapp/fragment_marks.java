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

import com.example.lerningapps.vitapp.POJO.marks;
import com.example.lerningapps.vitapp.POJO.marks_pojo;
import com.example.lerningapps.vitapp.firebase.marks_fire;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

public class fragment_marks extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private NiceSpinner spinners,spinnerd;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    public fragment_marks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.marks_fragment, container, false);
        listView=(ListView) v.findViewById(R.id.marks_list);
        markss();
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY,Color.CYAN,Color.MAGENTA);
        SharedPreferences pref = getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getContext(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getContext(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<marks_pojo> call = request.getmarks(reg_no,password,semester);
        call.enqueue(new Callback<marks_pojo>() {
            @Override
            public void onResponse(Call<marks_pojo> call, Response<marks_pojo> response) {
                if(response.code()==200){
                    marks_pojo jsonResponse=response.body();
                    final ArrayList<marks> marks =  new ArrayList<>(Arrays.asList(jsonResponse.getMarks()));
                    for(int i=0;i<marks.size();i++){
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(marks.get(i).getClass_number()+marks.get(i).getCourse_code());
                        for(int j=0;j<marks.get(i).getMarks().size();j++){
                            final DatabaseReference databaseReference1 = databaseReference.child(marks.get(i).getMarks().get(j).getTitle());
                            final marks_fire marks_fire=new marks_fire();
                            marks_fire.setName(reg_no);
                            marks_fire.setMark((marks.get(i).getMarks().get(j).getScored_marks()));
                            final int finalI = i;
                            final int finalJ = j;
                            databaseReference1.orderByChild("name").equalTo(reg_no)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if(!dataSnapshot.exists()){
                                                String idd = databaseReference1.push().getKey();
                                                databaseReference1.child(idd).setValue(marks_fire);
                                            }
                                            else{
                                                DataSnapshot nodeDataSnapshott = dataSnapshot.getChildren().iterator().next();
                                                String key = nodeDataSnapshott.getKey();
                                                databaseReference1.child(key).child("mark").setValue(marks.get(finalI).getMarks().get(finalJ).getScored_marks());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                        }
                    }
                    Gson gson = new Gson();
                    String dataa= gson.toJson(marks);
                    SharedPreferences test1 = getActivity().getSharedPreferences("marks", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("marks",dataa);
                    editortest.commit();
                    marks_adapter adapter = new marks_adapter(getActivity(), R.layout.adapter_marks, marks);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();

                }
                else{
                    Toast.makeText(getActivity(),"Error Ocurred",Toast.LENGTH_LONG).show();
                  //  markss();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<marks_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Ocurred",Toast.LENGTH_LONG).show();
             //   markss();
                progressDialog.dismiss();
            }
        });
        return v;
    }

    public void markss(){
        Gson gson=new Gson();
        SharedPreferences test1 = getActivity().getSharedPreferences("marks", 0);
        String mar = test1.getString("marks",null);
        Type type = new TypeToken<ArrayList<marks>>(){}.getType();
        ArrayList<marks> data = gson.fromJson(mar,type);
        if(data!=null) {
            marks_adapter adapter = new marks_adapter(getActivity(), R.layout.adapter_marks, data);
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
        progressDialog=new ProgressDialog(getContext(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getContext(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vit-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<marks_pojo> call = request.getmarks(reg_no,password,semester);
        call.enqueue(new Callback<marks_pojo>() {
            @Override
            public void onResponse(Call<marks_pojo> call, Response<marks_pojo> response) {
                if(response.code()==200){
                    marks_pojo jsonResponse=response.body();
                    final ArrayList<marks> marks =  new ArrayList<>(Arrays.asList(jsonResponse.getMarks()));
                    for(int i=0;i<marks.size();i++){
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(marks.get(i).getClass_number()+marks.get(i).getCourse_code());
                        for(int j=0;j<marks.get(i).getMarks().size();j++){
                            final DatabaseReference databaseReference1 = databaseReference.child(marks.get(i).getMarks().get(j).getTitle());
                            final marks_fire marks_fire=new marks_fire();
                            marks_fire.setName(reg_no);
                            marks_fire.setMark((marks.get(i).getMarks().get(j).getScored_marks()));
                            final int finalI = i;
                            final int finalJ = j;
                            databaseReference1.orderByChild("name").equalTo(reg_no)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if(!dataSnapshot.exists()){
                                                String idd = databaseReference1.push().getKey();
                                                databaseReference1.child(idd).setValue(marks_fire);
                                            }
                                            else{
                                                DataSnapshot nodeDataSnapshott = dataSnapshot.getChildren().iterator().next();
                                                String key = nodeDataSnapshott.getKey();
                                                databaseReference1.child(key).child("mark").setValue(marks.get(finalI).getMarks().get(finalJ).getScored_marks());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                        }
                    }
                    Gson gson = new Gson();
                    String dataa= gson.toJson(marks);
                    SharedPreferences test1 = getActivity().getSharedPreferences("marks", 0);
                    SharedPreferences.Editor editortest = test1.edit();
                    editortest.putString("marks",dataa);
                    editortest.commit();
                    marks_adapter adapter = new marks_adapter(getActivity(), R.layout.adapter_marks, marks);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);

                }
                else{
                    Toast.makeText(getActivity(),"Error Ocurred",Toast.LENGTH_LONG).show();
                 //   markss();
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<marks_pojo> call, Throwable t) {
                Toast.makeText(getActivity(),"Error Ocurred",Toast.LENGTH_LONG).show();
               // markss();
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
