package com.example.lerningapps.vitapp.POJO;

public class exam_schedule_pojo
{
    private exam_schedule exam_schedule;

    public exam_schedule getExam_schedule ()
    {
        return exam_schedule;
    }

    public void setExam_schedule (exam_schedule exam_schedule)
    {
        this.exam_schedule = exam_schedule;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exam_schedule = "+exam_schedule+"]";
    }
}