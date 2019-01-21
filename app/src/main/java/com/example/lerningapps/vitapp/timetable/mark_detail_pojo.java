package com.example.lerningapps.vitapp.timetable;

public class mark_detail_pojo {
    String title;
    String max_mark;
    String status;
    String percentage;
    String scored;
    String weightage;
    String avg;

    public mark_detail_pojo(String title, String max_mark, String status, String percentage, String scored, String weightage, String avg) {
        this.title = title;
        this.max_mark = max_mark;
        this.status = status;
        this.percentage = percentage;
        this.scored = scored;
        this.weightage = weightage;
        this.avg = avg;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMax_mark() {
        return max_mark;
    }

    public void setMax_mark(String max_mark) {
        this.max_mark = max_mark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getScored() {
        return scored;
    }

    public void setScored(String scored) {
        this.scored = scored;
    }

    public String getWeightage() {
        return weightage;
    }

    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }

    @Override
    public String toString() {
        return "mark_detail_pojo{" +
                "title='" + title + '\'' +
                ", max_mark='" + max_mark + '\'' +
                ", status='" + status + '\'' +
                ", percentage='" + percentage + '\'' +
                ", scored='" + scored + '\'' +
                ", weightage='" + weightage + '\'' +
                ", avg='" + avg + '\'' +
                '}';
    }
}
