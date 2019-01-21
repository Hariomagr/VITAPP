package com.example.lerningapps.vitapp.POJO;

import java.util.ArrayList;

public class facultylate_schools
{
    private String name;

    private String code;

    private ArrayList<facultylate> faculty;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public ArrayList<facultylate> getFaculty() {
        return faculty;
    }

    public void setFaculty(ArrayList<facultylate> faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", code = "+code+", faculty = "+faculty+"]";
    }
}
