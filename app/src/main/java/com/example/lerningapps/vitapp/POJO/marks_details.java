package com.example.lerningapps.vitapp.POJO;

public class marks_details
{
    private String scored_percentage;

    private String conducted_on;

    private String title;

    private String status;

    private String weightage;

    private String scored_marks;

    private String max_marks;

    public String getScored_percentage ()
    {
        return scored_percentage;
    }

    public void setScored_percentage (String scored_percentage)
    {
        this.scored_percentage = scored_percentage;
    }

    public String getConducted_on ()
    {
        return conducted_on;
    }

    public void setConducted_on (String conducted_on)
    {
        this.conducted_on = conducted_on;
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

    public String getScored_marks ()
    {
        return scored_marks;
    }

    public void setScored_marks (String scored_marks)
    {
        this.scored_marks = scored_marks;
    }

    public String getMax_marks ()
    {
        return max_marks;
    }

    public void setMax_marks (String max_marks)
    {
        this.max_marks = max_marks;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [scored_percentage = "+scored_percentage+", conducted_on = "+conducted_on+", title = "+title+", status = "+status+", weightage = "+weightage+", scored_marks = "+scored_marks+", max_marks = "+max_marks+"]";
    }
}