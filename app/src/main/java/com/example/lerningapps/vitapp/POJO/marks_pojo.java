package com.example.lerningapps.vitapp.POJO;

public class marks_pojo
{
    private marks[] marks;

    public marks[] getMarks ()
    {
        return marks;
    }

    public void setMarks (marks[] marks)
    {
        this.marks = marks;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [marks = "+marks+"]";
    }
}