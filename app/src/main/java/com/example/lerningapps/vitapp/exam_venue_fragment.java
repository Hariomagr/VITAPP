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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lerningapps.vitapp.POJO.Student_details;
import com.example.lerningapps.vitapp.POJO.exam_catI;
import com.example.lerningapps.vitapp.POJO.exam_catII;
import com.example.lerningapps.vitapp.POJO.exam_fat;
import com.example.lerningapps.vitapp.POJO.exam_schedule;
import com.example.lerningapps.vitapp.POJO.exam_schedule_pojo;
import com.example.lerningapps.vitapp.fragments.cat1;
import com.example.lerningapps.vitapp.fragments.cat2;
import com.example.lerningapps.vitapp.fragments.fat;
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

public class exam_venue_fragment extends Fragment {
    Context ctx;
    private ArrayList<Student_details> data;
    private ArrayList<Timetable_POJO> timetable = new ArrayList<>();
    private CircleImageView circleImageView;
    private ListView listView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public exam_venue_fragment() {
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
        SharedPreferences pref12 = getActivity().getSharedPreferences("exam_index", 0);
        Integer index = pref12.getInt("index",0);
        Integer chk = pref12.getInt("chk",0);
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
            Call<exam_schedule_pojo> call = request.getexamschedule(reg_no, password, semester);
            call.enqueue(new Callback<exam_schedule_pojo>() {
                @Override
                public void onResponse(Call<exam_schedule_pojo> call, Response<exam_schedule_pojo> response) {
                    if (response.code() == 200) {
                        exam_schedule_pojo jsonResponse = response.body();
                        ArrayList<exam_schedule> data = new ArrayList<>(Arrays.asList(jsonResponse.getExam_schedule()));
                        ArrayList<exam_catI> cat1 = data.get(0).getCAT_I();
                        ArrayList<exam_catII> cat2 = data.get(0).getCAT_II();
                        ArrayList<exam_fat> fat = data.get(0).getFinal_Assessment_Test();
                        Gson gson = new Gson();
                        String cat11 = gson.toJson(cat1);
                        String cat22 = gson.toJson(cat2);
                        String fat11 = gson.toJson(fat);
                        SharedPreferences test1 = getActivity().getSharedPreferences("exam_sch", 0);
                        SharedPreferences.Editor editortest = test1.edit();
                        editortest.putString("cat1", cat11);
                        editortest.putString("cat2", cat22);
                        editortest.putString("fat", fat11);
                        editortest.commit();
                        SharedPreferences pref11 = getActivity().getSharedPreferences("exam_index", 0);
                        SharedPreferences.Editor editor11 = pref11.edit();
                        editor11.putInt("chk",1);
                        editor11.commit();
                        exam_venue_fragment fragment = new exam_venue_fragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();
                    } else
                        Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call<exam_schedule_pojo> call, Throwable t) {
                    Toast.makeText(getActivity(), "Errorr Occurred", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
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
        adapter.addFragment(new cat1(), "CAT I");
        adapter.addFragment(new cat2(), "CAT II");
        adapter.addFragment(new fat(), "FAT");
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
