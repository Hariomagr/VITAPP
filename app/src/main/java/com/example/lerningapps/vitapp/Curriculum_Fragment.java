package com.example.lerningapps.vitapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.Curriculum;
import com.example.lerningapps.vitapp.POJO.Curriculum_PC;
import com.example.lerningapps.vitapp.POJO.Curriculum_PE;
import com.example.lerningapps.vitapp.POJO.Curriculum_Pojo;
import com.example.lerningapps.vitapp.POJO.Curriculum_Total;
import com.example.lerningapps.vitapp.POJO.Curriculum_UC;
import com.example.lerningapps.vitapp.POJO.Curriculum_UE;
import com.example.lerningapps.vitapp.POJO.Student_details;
import com.example.lerningapps.vitapp.fragments.PC;
import com.example.lerningapps.vitapp.fragments.PE;
import com.example.lerningapps.vitapp.fragments.UC;
import com.example.lerningapps.vitapp.fragments.UE;
import com.example.lerningapps.vitapp.timetable.Timetable_POJO;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Curriculum_Fragment extends Fragment {
    Context ctx;
    private ArrayList<Student_details> data;
    private ArrayList<Timetable_POJO> timetable = new ArrayList<>();
    private CircleImageView circleImageView;
    private ListView listView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public Curriculum_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.exam_venue, container, false);
        SharedPreferences pref12 = getActivity().getSharedPreferences("curriculum_index", 0);
        Integer index = pref12.getInt("index",0);
        Integer chk = pref12.getInt("chk",0);
        if(chk==0) {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity(),R.style.alert);
        progressDialog.setCancelable(false);
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(getActivity(),null,50,50);
        avLoadingIndicatorView.setIndicator("BallPulseIndicator");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        progressDialog.setContentView(avLoadingIndicatorView);
        SharedPreferences pref = getActivity().getSharedPreferences("login_credentials", 0);
        final String reg_no = pref.getString("reg_no",null);
        final String password = pref.getString("password",null);
        final String semester = pref.getString("semester",null);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://vit-app.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final RequestInterface request = retrofit.create(RequestInterface.class);
            Call<Curriculum_Pojo> call = request.getCurriculum(reg_no, password);
            call.enqueue(new Callback<Curriculum_Pojo>() {
                @Override
                public void onResponse(Call<Curriculum_Pojo> call, Response<Curriculum_Pojo> response) {
                    if (response.code() == 200) {
                        Curriculum_Pojo jsonResponse = response.body();
                        ArrayList<Curriculum> data = new ArrayList<>(Arrays.asList(jsonResponse.getCurriculum()));
                        ArrayList<Curriculum_PC> pcs = data.get(0).getProgramme_core();
                        ArrayList<Curriculum_PE> pes = data.get(0).getProgramme_elective();
                        ArrayList<Curriculum_UC> ucs = data.get(0).getUniversity_core();
                        ArrayList<Curriculum_UE> ues = data.get(0).getUniversity_elective();
                        ArrayList<Curriculum_Total> totals = data.get(0).getTotal_credit();
                        Gson gson = new Gson();
                        String pcss = gson.toJson(pcs);
                        String pess = gson.toJson(pes);
                        String ucss = gson.toJson(ucs);
                        String uess = gson.toJson(ues);
                        String totalss = gson.toJson(totals);
                        SharedPreferences test1 = getActivity().getSharedPreferences("curriculum", 0);
                        SharedPreferences.Editor editortest = test1.edit();
                        editortest.putString("pc", pcss);
                        editortest.putString("pe", pess);
                        editortest.putString("uc", ucss);
                        editortest.putString("ue", uess);
                        editortest.putString("total", totalss);
                        Log.d("pc", jsonResponse.toString());
                        editortest.commit();
                        SharedPreferences pref11 = getActivity().getSharedPreferences("curriculum_index", 0);
                        SharedPreferences.Editor editor11 = pref11.edit();
                        editor11.putInt("chk",1);
                        editor11.commit();
                        Curriculum_Fragment fragment = new Curriculum_Fragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();
                    } else
                        Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }

                @Override
                public void onFailure(Call<Curriculum_Pojo> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Errorr Occurred", Toast.LENGTH_LONG).show();

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
        adapter.addFragment(new PC(), "PC");
        adapter.addFragment(new PE(), "PE");
        adapter.addFragment(new UC(), "UC");
        adapter.addFragment(new UE(), "UE");
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