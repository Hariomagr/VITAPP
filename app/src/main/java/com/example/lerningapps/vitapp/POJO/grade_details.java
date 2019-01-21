package com.example.lerningapps.vitapp.POJO;

public class grade_details
{
    private String exam_held;

    private String course_code;

    private String course_title;

    private String grade;

    private String credits;

    private String result_date;

    private String option;

    private String course_type;

    public grade_details(String course_code, String course_title, String grade, String credits) {
        this.course_code = course_code;
        this.course_title = course_title;
        this.grade = grade;
        this.credits = credits;
    }

    public String getExam_held ()
    {
        return exam_held;
    }

    public void setExam_held (String exam_held)
    {
        this.exam_held = exam_held;
    }

    public String getCourse_code ()
    {
        return course_code;
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

    public String getGrade ()
    {
        return grade;
    }

    public void setGrade (String grade)
    {
        this.grade = grade;
    }

    public String getCredits ()
    {
        return credits;
    }

    public void setCredits (String credits)
    {
        this.credits = credits;
    }

    public String getResult_date ()
    {
        return result_date;
    }

    public void setResult_date (String result_date)
    {
        this.result_date = result_date;
    }

    public String getOption ()
    {
        return option;
    }

    public void setOption (String option)
    {
        this.option = option;
    }

    public String getCourse_type ()
    {
        return course_type;
    }

    public void setCourse_type (String course_type)
    {
        this.course_type = course_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exam_held = "+exam_held+", course_code = "+course_code+", course_title = "+course_title+", grade = "+grade+", credits = "+credits+", result_date = "+result_date+", option = "+option+", course_type = "+course_type+"]";
    }
}