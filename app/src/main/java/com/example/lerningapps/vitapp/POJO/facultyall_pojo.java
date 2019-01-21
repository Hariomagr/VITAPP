package com.example.lerningapps.vitapp.POJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class facultyall_pojo
{
    @SerializedName("Faculty")
    @Expose
    private facultyall[] Faculty;

    public facultyall[] getFaculty ()
    {
        return Faculty;
    }

    public void setFaculty (facultyall[] Faculty)
    {
        this.Faculty = Faculty;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [faculty = "+Faculty+"]";
    }
}