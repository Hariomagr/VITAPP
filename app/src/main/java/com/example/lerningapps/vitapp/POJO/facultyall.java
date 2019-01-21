package com.example.lerningapps.vitapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class facultyall
{
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("open_hours")
    @Expose
    private ArrayList<facultyall_openhrs> open_hours;
    @SerializedName("intercom")
    @Expose
    private String intercom;
    @SerializedName("room")
    @Expose
    private String room;

    public String getEmpid ()
    {
        return empid;
    }

    public void setEmpid (String empid)
    {
        this.empid = empid;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getSchool ()
    {
        return school;
    }

    public void setSchool (String school)
    {
        this.school = school;
    }

    public String getDivision ()
    {
        return division;
    }

    public void setDivision (String division)
    {
        this.division = division;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDesignation ()
    {
        return designation;
    }

    public void setDesignation (String designation)
    {
        this.designation = designation;
    }

    public ArrayList<facultyall_openhrs> getOpen_hours() {
        return open_hours;
    }

    public void setOpen_hours(ArrayList<facultyall_openhrs> open_hours) {
        this.open_hours = open_hours;
    }

    public String getIntercom ()
    {
        return intercom;
    }

    public void setIntercom (String intercom)
    {
        this.intercom = intercom;
    }

    public String getRoom ()
    {
        return room;
    }

    public void setRoom (String room)
    {
        this.room = room;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [empid = "+empid+", phone = "+phone+", school = "+school+", division = "+division+", _id = "+_id+", email = "+email+", name = "+name+", designation = "+designation+", open_hours = "+open_hours+", intercom = "+intercom+", room = "+room+"]";
    }
}