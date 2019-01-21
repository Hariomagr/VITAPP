package com.example.lerningapps.vitapp.POJO;

public class assignments_details
{
    private String max_mark;

    private String title;

    private String status;

    private String weightage;

    private String due_date;

    public String getMax_mark ()
    {
        return max_mark;
    }

    public void setMax_mark (String max_mark)
    {
        this.max_mark = max_mark;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getWeightage ()
    {
        return weightage;
    }

    public void setWeightage (String weightage)
    {
        this.weightage = weightage;
    }

    public String getDue_date ()
    {
        return due_date;
    }

    public void setDue_date (String due_date)
    {
        this.due_date = due_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [max_mark = "+max_mark+", title = "+title+", status = "+status+", weightage = "+weightage+", due_date = "+due_date+"]";
    }
}