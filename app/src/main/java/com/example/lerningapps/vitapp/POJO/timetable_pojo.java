package com.example.lerningapps.vitapp.POJO;

public class timetable_pojo
{
    private Student_details student_details;

    public Student_details getStudent_details ()
    {
        return student_details;
    }

    public void setStudent_details (Student_details student_details)
    {
        this.student_details = student_details;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [student_details = "+student_details+"]";
    }
}
