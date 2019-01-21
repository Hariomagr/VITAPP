package com.example.lerningapps.vitapp.POJO;

import java.util.ArrayList;

public class assignments_courses
{
    private String faculty_name;

    private String course_code;

    private ArrayList<assignments_details> details;

    private String course_title;

    private String slot;

    private String course_type;

    private String class_number;

    public String getFaculty_name ()
    {
        return faculty_name;
    }

    public void setFaculty_name (String faculty_name)
    {
        this.faculty_name = faculty_name;
    }

    public String getCourse_code ()
    {
        return course_code;
    }

    public void setCourse_code (String course_code)
    {
        this.course_code = course_code;
    }

    public ArrayList<assignments_details> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<assignments_details> details) {
        this.details = details;
    }

    public String getCourse_title ()
    {
        return course_title;
    }

    public void setCourse_title (String course_title)
    {
        this.course_title = course_title;
    }

    public String getSlot ()
    {
        return slot;
    }

    public void setSlot (String slot)
    {
        this.slot = slot;
    }

    public String getCourse_type ()
    {
        return course_type;
    }

    public void setCourse_type (String course_type)
    {
        this.course_type = course_type;
    }

    public String getClass_number ()
    {
        return class_number;
    }

    public void setClass_number (String class_number)
    {
        this.class_number = class_number;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [faculty_name = "+faculty_name+", course_code = "+course_code+", details = "+details+", course_title = "+course_title+", slot = "+slot+", course_type = "+course_type+", class_number = "+class_number+"]";
    }
}