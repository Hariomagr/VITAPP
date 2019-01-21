package com.example.lerningapps.vitapp.POJO;

public class grades_pojo
{
    private grades grades;

    public grades getGrades ()
    {
        return grades;
    }

    public void setGrades (grades grades)
    {
        this.grades = grades;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [grades = "+grades+"]";
    }
}
