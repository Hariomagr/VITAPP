package com.example.lerningapps.vitapp.POJO;

public class Curriculum_UE
{
    private String Credit;

    private String Code;

    private String Title;

    public String getCredit ()
    {
        return Credit;
    }

    public void setCredit (String Credit)
    {
        this.Credit = Credit;
    }

    public String getCode ()
    {
        return Code;
    }

    public void setCode (String Code)
    {
        this.Code = Code;
    }

    public String getTitle ()
    {
        return Title;
    }

    public void setTitle (String Title)
    {
        this.Title = Title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Credit = "+Credit+", Code = "+Code+", Title = "+Title+"]";
    }
}

