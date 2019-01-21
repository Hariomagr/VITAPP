package com.example.lerningapps.vitapp.POJO;

public class Curriculum_Total
{
    private String course;

    public String getCourse ()
    {
        return course;
    }

    public void setCourse (String course)
    {
        this.course = course;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [course = "+course+"]";
    }
}
