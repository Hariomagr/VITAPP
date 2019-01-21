package com.example.lerningapps.vitapp.POJO;

import java.util.ArrayList;

public class Curriculum
{
   private ArrayList<Curriculum_PC> programme_core;
   private ArrayList<Curriculum_PE> programme_elective;
   private ArrayList<Curriculum_UC> university_core;
   private ArrayList<Curriculum_UE> university_elective;
   private ArrayList<Curriculum_Total> total_credit;

    public ArrayList<Curriculum_PC> getProgramme_core() {
        return programme_core;
    }

    public void setProgramme_core(ArrayList<Curriculum_PC> programme_core) {
        this.programme_core = programme_core;
    }

    public ArrayList<Curriculum_PE> getProgramme_elective() {
        return programme_elective;
    }

    public void setProgramme_elective(ArrayList<Curriculum_PE> programme_elective) {
        this.programme_elective = programme_elective;
    }

    public ArrayList<Curriculum_UC> getUniversity_core() {
        return university_core;
    }

    public void setUniversity_core(ArrayList<Curriculum_UC> university_core) {
        this.university_core = university_core;
    }

    public ArrayList<Curriculum_UE> getUniversity_elective() {
        return university_elective;
    }

    public void setUniversity_elective(ArrayList<Curriculum_UE> university_elective) {
        this.university_elective = university_elective;
    }

    public ArrayList<Curriculum_Total> getTotal_credit() {
        return total_credit;
    }

    public void setTotal_credit(ArrayList<Curriculum_Total> total_credit) {
        this.total_credit = total_credit;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [university_core = "+university_core+", total_credit = "+total_credit+", programme_elective = "+programme_elective+", programme_core = "+programme_core+", university_elective = "+university_elective+"]";
    }
}
