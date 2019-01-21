package com.example.lerningapps.vitapp.POJO;

public class grade_sem_wisee
{
    private String exam_held;

    private String gpa;

    private String credits;

    public String getExam_held ()
    {
        return exam_held;
    }

    public void setExam_held (String exam_held)
    {
        this.exam_held = exam_held;
    }

    public String getGpa ()
    {
        return gpa;
    }

    public void setGpa (String gpa)
    {
        this.gpa = gpa;
    }

    public String getCredits ()
    {
        return credits;
    }

    public void setCredits (String credits)
    {
        this.credits = credits;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exam_held = "+exam_held+", gpa = "+gpa+", credits = "+credits+"]";
    }
}