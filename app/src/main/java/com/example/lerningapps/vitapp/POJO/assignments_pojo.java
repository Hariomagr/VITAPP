package com.example.lerningapps.vitapp.POJO;


public class assignments_pojo
{
    private assignments_courses[] courses;

    public assignments_courses[] getCourses ()
    {
        return courses;
    }

    public void setCourses (assignments_courses[] courses)
    {
        this.courses = courses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [courses = "+courses+"]";
    }
}

