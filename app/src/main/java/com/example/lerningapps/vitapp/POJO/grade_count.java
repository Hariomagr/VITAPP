package com.example.lerningapps.vitapp.POJO;

public class grade_count
{
    private String count;

    private String value;

    private String grade;

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String getGrade ()
    {
        return grade;
    }

    public void setGrade (String grade)
    {
        this.grade = grade;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [count = "+count+", value = "+value+", grade = "+grade+"]";
    }
}
