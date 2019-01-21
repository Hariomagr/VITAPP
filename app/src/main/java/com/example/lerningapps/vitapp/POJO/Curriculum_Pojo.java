package com.example.lerningapps.vitapp.POJO;

public class Curriculum_Pojo
{
    private Curriculum Curriculum;

    public Curriculum getCurriculum ()
    {
        return Curriculum;
    }

    public void setCurriculum (Curriculum Curriculum)
    {
        this.Curriculum = Curriculum;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Curriculum = "+Curriculum+"]";
    }
}