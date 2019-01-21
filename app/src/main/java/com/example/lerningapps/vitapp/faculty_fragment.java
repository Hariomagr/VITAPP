package com.example.lerningapps.vitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class faculty_fragment extends Fragment {
    private NiceSpinner spinners,spinnerd;
    public faculty_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.faculty_selection, container, false);
        final NiceSpinner spinner = (NiceSpinner) v.findViewById(R.id.spinner);
        final List<String> dataset = new LinkedList<>(Arrays.asList("Search By:","Name", "School", "Division"));
        spinner.attachDataSource(dataset);
        spinners = (NiceSpinner) v.findViewById(R.id.spinners);
        final List<String> school = new LinkedList<>(Arrays.asList("Select School:","School of Electrical Engineering", "School of Civil and Chemical Engineering", "Centre for Innovative Manufacturing Research",
                "School of Advanced Sciences", "School of Mechanical Engineering", "School of Social Sciences & Languages", "School of Electronics Engineering", "VIT Business School", "Centre for Crystal Growth", "School of Computer Engineering", "School of Information Technology and Engineering", "School of Mechanical and Building Sciences",
                "CO2 Research Centre", "Institute for Industry and International Programme", "Centre for Nanotechnology Research", "School of Bio Sciences and Technology", "Centre for Nanobiotechnology",
                "Centre for Biomaterials, Cellular and Molecular Theranostics", "Technology Information Forecasting and Assessment Council", "Health Centre",
                "Centre for Bio-Separation and Technology", "Automotive Research Centre", "O/o International Relations", "Centre for Sustainable Rural Development and Studies",
                "Centre for Disaster Mitigation and Management", "O/o Controller Of Examinations", "Technology Business Incubator",
                "Academics Staff College", "Software Development Centre", "Survey Research Centre", "O/o The VP University Affairs", "School of Architecture","Department of Physical Education", "Students Welfare"));

        spinnerd = (NiceSpinner)v. findViewById(R.id.spinnerd);
        final List<String> division = new LinkedList<>(Arrays.asList("Select Division:","Department of Electrical Engineering (SELECT)",
                "Department of Environmental and Water Resources Engineering (SCALE)", "Department of Manufacturing Engineering (SMEC)",
                "Department of Mathematics (SAS)", "Department of Physics (SAS)", "Department of Automotive Engineering (SMEC)", "Deptment of English (SSL)", "Department of Communication Engineering (SENSE)", "Department of MBA (VITBS)",
                "Department of Chemistry (SAS)", "Department of Software Systems (SCOPE)", "Department of Control and Automation (SELECT)", "Department of Analytics (SCOPE)", "Department of Instrumentation (SELECT)",
                "Department of Sensor and Biomedical Technology (SENSE)", "Deptment of Commerce (SSL)", "Department of Computer Applications and Creative Media (SITE)", "Department of Structural and Geotechnical Engineering (SCALE)", "Department of Database Systems (SCOPE)", "Department of Software and Systems Engineering (SITE)", "Department of Energy and Power Electronics (SELECT)", "Department of Embedded Technology (SENSE)",
                "Department of Chemical Engineering (SCALE)", "Department of Information Technology (SITE)", "Department of Digital Communications (SITE)", "Department of Design and Automation (SMEC)", "Department of Biotechnology (SBST)", "Department of Computational Intelligence (SCOPE)",
                "Department of Bio-Medical Sciences (SBST)", "Department of Technology Management (SMEC)", "Department of Micro and Nanoelectronics  (SENSE)",
                "Department of Integrative Biology (SBST)", "Deptment of Languages (SSL)", "Department of Thermal and Energy Engineering (SMEC)", "Department of Bio-Sciences (SBST)","Deptment of Social Sciences (SSL)", "Department of Database Systems"));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(dataset.get(position).toString().equals("School")){
                    spinnerd.setVisibility(View.GONE);
                    spinners.setVisibility(View.VISIBLE);
                    spinners.attachDataSource(school);
                }
                else if(dataset.get(position).equals("Division")){
                    spinnerd.attachDataSource(division);
                    spinners.setVisibility(View.GONE);
                    spinnerd.setVisibility(View.VISIBLE);
                }
                else if(dataset.get(position).equals("Name")){
                    spinners.setVisibility(View.GONE);
                    spinnerd.setVisibility(View.GONE);
                    SharedPreferences pref = getActivity().getSharedPreferences("faculty", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name", "0");
                    editor.commit();
                    Intent i = new Intent(getActivity(),activity_faculty_list.class);
                    i.putExtra("name","0");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
                else{
                    spinners.setVisibility(View.GONE);
                    spinnerd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!school.get(position).toString().equals("Select School:")){
                    SharedPreferences pref = getActivity().getSharedPreferences("faculty", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name", "1");
                    editor.putString("school",school.get(position).toString());
                    editor.commit();
                    Intent i = new Intent(getActivity(),activity_faculty_list.class);
                    i.putExtra("name","1");
                    i.putExtra("school",school.get(position).toString());
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!division.get(position).toString().equals("Select Division:")){
                    SharedPreferences pref = getActivity().getSharedPreferences("faculty", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name", "2");
                    editor.putString("division",division.get(position).toString());
                    editor.commit();
                    Intent i = new Intent(getActivity(),activity_faculty_list.class);
                    i.putExtra("name","2");
                    i.putExtra("division",division.get(position).toString());
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }
}
