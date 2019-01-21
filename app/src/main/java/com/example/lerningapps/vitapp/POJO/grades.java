package com.example.lerningapps.vitapp.POJO;

import java.util.ArrayList;

public class grades
{
    private String cgpa;

    private String credits_registered;

    private ArrayList<grade_sem_wisee> semester_wise;

    private String credits_earned;

    private ArrayList<grade_count> grade_count;

    private ArrayList<grade_details> grades;

    public String getCgpa ()
    {
        return cgpa;
    }

    public void setCgpa (String cgpa)
    {
        this.cgpa = cgpa;
    }

    public String getCredits_registered ()
    {
        return credits_registered;
    }

    public void setCredits_registered (String credits_registered)
    {
        this.credits_registered = credits_registered;
    }

    public ArrayList<grade_sem_wisee> getSemester_wise() {
        return semester_wise;
    }

    public void setSemester_wise(ArrayList<grade_sem_wisee> semester_wise) {
        this.semester_wise = semester_wise;
    }

    public String getCredits_earned() {
        return credits_earned;
    }

    public void setCredits_earned(String credits_earned) {
        this.credits_earned = credits_earned;
    }

    public ArrayList<com.example.lerningapps.vitapp.POJO.grade_count> getGrade_count() {
        return grade_count;
    }

    public void setGrade_count(ArrayList<com.example.lerningapps.vitapp.POJO.grade_count> grade_count) {
        this.grade_count = grade_count;
    }

    public ArrayList<grade_details> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<grade_details> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "grades{" +
                "cgpa='" + cgpa + '\'' +
                ", credits_registered='" + credits_registered + '\'' +
                ", semester_wise=" + semester_wise +
                ", credits_earned='" + credits_earned + '\'' +
                ", grade_count=" + grade_count +
                ", grades=" + grades +
                '}';
    }
}