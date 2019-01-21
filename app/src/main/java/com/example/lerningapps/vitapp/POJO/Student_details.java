package com.example.lerningapps.vitapp.POJO;

import java.util.ArrayList;

public class Student_details
{
    private ArrayList<attendance> attendance;

    private ArrayList<timetable> timetable;

    public ArrayList<com.example.lerningapps.vitapp.POJO.attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(ArrayList<com.example.lerningapps.vitapp.POJO.attendance> attendance) {
        this.attendance = attendance;
    }

    public ArrayList<com.example.lerningapps.vitapp.POJO.timetable> getTimetable() {
        return timetable;
    }

    public void setTimetable(ArrayList<com.example.lerningapps.vitapp.POJO.timetable> timetable) {
        this.timetable = timetable;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [attendance = "+attendance+", timetable = "+timetable+"]";
    }
}