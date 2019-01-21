package com.example.lerningapps.vitapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class exam_schedule implements Serializable
{
    @Expose
    @SerializedName("Final Assessment Test")
    private ArrayList<exam_fat> Final_Assessment_Test;
    @Expose
    @SerializedName("CAT - II")
    private ArrayList<exam_catII> CAT_II;
    @Expose
    @SerializedName("CAT - I")
    private ArrayList<exam_catI> CAT_I;

    public ArrayList<exam_fat> getFinal_Assessment_Test() {
        return Final_Assessment_Test;
    }

    public void setFinal_Assessment_Test(ArrayList<exam_fat> final_Assessment_Test) {
        Final_Assessment_Test = final_Assessment_Test;
    }

    public ArrayList<exam_catII> getCAT_II() {
        return CAT_II;
    }

    public void setCAT_II(ArrayList<exam_catII> CAT_II) {
        this.CAT_II = CAT_II;
    }

    public ArrayList<exam_catI> getCAT_I() {
        return CAT_I;
    }

    public void setCAT_I(ArrayList<exam_catI> CAT_I) {
        this.CAT_I = CAT_I;
    }

    @Override
    public String toString() {
        return "exam_schedule{" +
                "Final_Assessment_Test=" + Final_Assessment_Test +
                ", CAT_II=" + CAT_II +
                ", CAT_I=" + CAT_I +
                '}';
    }
}
