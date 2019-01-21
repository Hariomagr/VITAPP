package com.example.lerningapps.vitapp.POJO;

public class facultylate_pojo
{
    private facultylate_schools[] schools;

    public facultylate_schools[] getSchools ()
    {
        return schools;
    }

    public void setSchools (facultylate_schools[] schools)
    {
        this.schools = schools;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [schools = "+schools+"]";
    }
}