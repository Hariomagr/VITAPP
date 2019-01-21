package com.example.lerningapps.vitapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class facultyall_openhrs
{
    @SerializedName("end_time")
    @Expose
    private String end_time;
    @SerializedName("start_time")
    @Expose
    private String start_time;
    @SerializedName("day")
    @Expose
    private String day;

    public String getEnd_time ()
    {
        return end_time;
    }

    public void setEnd_time (String end_time)
    {
        this.end_time = end_time;
    }

    public String getStart_time ()
    {
        return start_time;
    }

    public void setStart_time (String start_time)
    {
        this.start_time = start_time;
    }

    public String getDay ()
    {
        return day;
    }

    public void setDay (String day)
    {
        this.day = day;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [end_time = "+end_time+", start_time = "+start_time+", day = "+day+"]";
    }
}