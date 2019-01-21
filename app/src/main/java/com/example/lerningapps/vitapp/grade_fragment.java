package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.Student_details;
import com.example.lerningapps.vitapp.POJO.grade_details;
import com.example.lerningapps.vitapp.POJO.grades;
import com.example.lerningapps.vitapp.POJO.grades_pojo;
import com.example.lerningapps.vitapp.fragments.current_grade;
import com.example.lerningapps.vitapp.fragments.grade_history;
import com.example.lerningapps.vitapp.timetable.Timetable_POJO;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class grade_fragment extends Fragment{
    Context ctx;
    private ArrayList<Student_details> data;
    private ArrayList<Timetable_POJO> timetable = new ArrayList<>();
    private CircleImageView circleImageView;
    private ListView listView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public grade_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HashMap< String,Integer> hmm = new HashMap< String,Integer>();

        View v = inflater.inflate(R.layout.grade, container, false);
        SharedPreferences pref1 = getActivity().getSharedPreferences("grade_index", 0);
        Integer index = pref1.getInt("index",0);
        Integer chk = pref1.getInt("chk",0);
        if(chk==0) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.alert);
            progressDialog.setCancelable(false);
            final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(getActivity(), null, 50, 50);
            avLoadingIndicatorView.setIndicator("BallPulseIndicator");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            progressDialog.setContentView(avLoadingIndicatorView);
            SharedPreferences pref = getActivity().getSharedPreferences("login_credentials", 0);
            final String reg_no = pref.getString("reg_no", null);
            final String password = pref.getString("password", null);
            final String semester = pref.getString("semester", null);
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://vit-app.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final RequestInterface request = retrofit.create(RequestInterface.class);
            Call<grades_pojo> call = request.getgrades(reg_no, password, semester);
            call.enqueue(new Callback<grades_pojo>() {
                @Override
                public void onResponse(Call<grades_pojo> call, Response<grades_pojo> response) {
                    if (response.code() == 200) {
                        grades_pojo jsonResponse = response.body();
                        ArrayList<grades> data = new ArrayList<>(Arrays.asList(jsonResponse.getGrades()));
                        ArrayList<grade_details> gradeDetails = data.get(0).getGrades();
                        String credit_reg = data.get(0).getCredits_registered();
                        String credit_ear = data.get(0).getCredits_earned();
                        String cgpa = data.get(0).getCgpa();
                        ArrayList<String> title = new ArrayList<>();
                        ArrayList<String> code = new ArrayList<>();
                        ArrayList<String> credit = new ArrayList<>();
                        ArrayList<String> grade = new ArrayList<>();

                        for (int i = 0; i < gradeDetails.size(); i++) {
                            title.add(gradeDetails.get(i).getCourse_title());
                            code.add(gradeDetails.get(i).getCourse_code());
                            credit.add(gradeDetails.get(i).getCredits());
                            grade.add(gradeDetails.get(i).getGrade());
                        }
                        SharedPreferences prefdata = getActivity().getSharedPreferences("exam_grade", 0);
                        SharedPreferences.Editor editorr = prefdata.edit();
                        ObjectSerializer objectSerializer = new ObjectSerializer();


                        try {
                            editorr.putString("cgpa", cgpa);
                            editorr.putString("credits", credit_ear + "/" + credit_reg);
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
                        editor11.putInt("chk",1);
                        editor11.commit();
                        grade_fragment fragment = new grade_fragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();
                    } else
                        Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<grades_pojo> call, Throwable t) {
                    Toast.makeText(getActivity(), "Errorr Occurred", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
            SharedPreferences preff = getActivity().getSharedPreferences("exam_grade", 0);
            final String cgpa1 = preff.getString("cgpa", null);
            final String credits1 = preff.getString("credits", null);
            SharedPreferences preffff = getActivity().getSharedPreferences("cgpa_calc", 0);
            ArrayList<String> sub = null;
            ArrayList<Integer> cre = null;
            try {
                sub = (ArrayList) ObjectSerializer.deserialize(preffff.getString("sub", ObjectSerializer.serialize(new ArrayList())));
                cre = (ArrayList) ObjectSerializer.deserialize(preffff.getString("cre", ObjectSerializer.serialize(new ArrayList())));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (sub.size() > 0) {
                for (int j = 0; j < sub.size(); j++) {
                    String tit = sub.get(j);
                    String[] xx = tit.split(Pattern.quote(" "));
                    String xxx = "";
                    for (int k = 1; k < xx.length; k++) {
                        xxx = xxx + xx[k] + " ";
                    }
                    hmm.put(xx[0], cre.get(j));
                }
            }

            SharedPreferences prefff = getActivity().getSharedPreferences("exam_grade", 0);
            ArrayList<String> codee = null;
            ArrayList<String> creditt = null, gradee = null;
            try {
                codee = (ArrayList) ObjectSerializer.deserialize(prefff.getString("data02", ObjectSerializer.serialize(new ArrayList())));
                creditt = (ArrayList) ObjectSerializer.deserialize(prefff.getString("data03", ObjectSerializer.serialize(new ArrayList())));
                gradee = (ArrayList) ObjectSerializer.deserialize(prefff.getString("data04", ObjectSerializer.serialize(new ArrayList())));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            HashMap<String, Double> hm1 = new HashMap<String, Double>();
            hm1.put("S", 1.0);
            hm1.put("A", 0.9);
            hm1.put("B", 0.8);
            hm1.put("C", 0.7);
            hm1.put("D", 0.6);
            hm1.put("E", 0.5);
            hm1.put("F", 0.4);
            hm1.put("N", 0.3);
            Double cred_grade = 0.00;
            Double cred = 0.00;
            Integer xp = 0;
            for (int j = 0; j < codee.size(); j++) {
                if (hmm.containsKey(codee.get(j))) {
                    if (hmm.get(codee.get(j)) == Integer.parseInt(creditt.get(j))) {
                        xp = xp + 1;
                        cred_grade = cred_grade + hm1.get(gradee.get(j)) * Double.parseDouble(creditt.get(j));
                        cred = cred + Double.parseDouble(creditt.get(j));
                    }

                }
            }
            SharedPreferences prefdata = getActivity().getSharedPreferences("creds", 0);
            SharedPreferences.Editor editorr = prefdata.edit();
            editorr.putString("cred_grade", String.valueOf(cred_grade));
            editorr.putString("cred", String.valueOf(cred));
            editorr.commit();
            //  Toast.makeText(getContext(),String.valueOf(cred_grade)+" "+String.valueOf(cred),Toast.LENGTH_LONG).show();
            v.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        View parentLayout = getActivity().findViewById(android.R.id.content);
                        Snackbar snackbar = Snackbar
                                .make(parentLayout, "Credits: " + credits1, Snackbar.LENGTH_LONG)
                                .setAction("CGPA: " + cgpa1, new View.OnClickListener() {
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
        }
        viewPager = (ViewPager) v.findViewById(R.id.viewpagerr);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabss);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(index).select();
        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new current_grade(), "CGPA Calculator");
        adapter.addFragment(new grade_history(), "Academic History");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

}