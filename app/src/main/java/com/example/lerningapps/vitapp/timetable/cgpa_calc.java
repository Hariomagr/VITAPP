package com.example.lerningapps.vitapp.timetable;

public class cgpa_calc {
    String title;
    String code;
    Integer creedit;

    public cgpa_calc(String title, String code, Integer creedit) {
        this.title = title;
        this.code = code;
        this.creedit = creedit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCreedit() {
        return creedit;
    }

    public void setCreedit(Integer creedit) {
        this.creedit = creedit;
    }

    @Override
    public String toString() {
        return "cgpa_calc{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", creedit=" + creedit +
                '}';
    }
}
