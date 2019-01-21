package com.example.lerningapps.vitapp.timetable;

public class assignment_detail_pojo1 {
    private String title;
    private String due_date;
    private String max_mark;
    private String weightage;
    private String status;

    public assignment_detail_pojo1(String title,String due_date,String max_mark,String weightage,String status){
        this.title=title;
        this.due_date=due_date;
        this.max_mark=max_mark;
        this.weightage=weightage;
        this.status=status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getMax_mark() {
        return max_mark;
    }

    public void setMax_mark(String max_mark) {
        this.max_mark = max_mark;
    }

    public String getWeightage() {
        return weightage;
    }

    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "assignment_detail_pojo1{" +
                "title='" + title + '\'' +
                ", due_date='" + due_date + '\'' +
                ", max_mark='" + max_mark + '\'' +
                ", weightage='" + weightage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
