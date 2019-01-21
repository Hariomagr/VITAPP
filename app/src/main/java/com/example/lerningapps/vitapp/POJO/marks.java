package com.example.lerningapps.vitapp.POJO;

import java.util.ArrayList;

public class marks
{
    private String course_code;

    private String course_title;

    private ArrayList<marks_details> marks;

    private String course_type;

    private String class_number;

    public String getCourse_code ()
    {
        return course_code;
    }

    public ArrayList<marks_details> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<marks_details> marks) {
        this.marks = marks;
    }

    public void setCourse_code (String course_code)
    {
        this.course_code = course_code;
    }

    public String getCourse_title ()
    {
        return course_title;
    }

    public void setCourse_title (String course_title)
    {
        this.course_title = course_title;
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
        return "ClassPojo [course_code = "+course_code+", course_title = "+course_title+", marks = "+marks+", course_type = "+course_type+", class_number = "+class_number+"]";
    }
}