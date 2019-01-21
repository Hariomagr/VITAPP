package com.example.lerningapps.vitapp.timetable;

import android.support.annotation.NonNull;

import com.example.lerningapps.vitapp.POJO.attendance_details;

import java.util.ArrayList;

public class Timetable_POJO implements Comparable{
    private String ltpjc;

    private String faculty_name;

    private String course_code;

    private String course_option;

    private String course_mode;

    private String course_name;

    private String venue;

    private String slot;

    private String course_type;

    private String class_number;

    private String time;

    private String day;
    private String att;

    private String total_class;
    private String total_attended;

    private ArrayList<attendance_details> details;
    public Timetable_POJO(String class_number,String course_code,String course_name,String course_type,String ltpjc,String course_option,String course_mode,String slot,String venue,String day,String time,String faculty_name,String att,String total_class,String total_attended,ArrayList<attendance_details> details){
        this.class_number=class_number;
        this.course_code=course_code;
        this.course_name=course_name;
        this.course_type=course_type;
        this.ltpjc=ltpjc;
        this.course_option=course_option;
        this.course_mode=course_mode;
        this.slot=slot;
        this.venue=venue;
        this.day=day;
        this.time=time;
        this.faculty_name=faculty_name;
        this.att=att;
        this.total_attended=total_attended;
        this.total_class=total_class;
        this.details=details;
    }



    public ArrayList<attendance_details> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<attendance_details> details) {
        this.details = details;
    }

    public String getLtpjc() {
        return ltpjc;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public void setLtpjc(String ltpjc) {
        this.ltpjc = ltpjc;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_option() {
        return course_option;
    }

    public void setCourse_option(String course_option) {
        this.course_option = course_option;
    }

    public String getCourse_mode() {
        return course_mode;
    }

    public void setCourse_mode(String course_mode) {
        this.course_mode = course_mode;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTotal_class() {
        return total_class;
    }

    public void setTotal_class(String total_class) {
        this.total_class = total_class;
    }

    public String getTotal_attended() {
        return total_attended;
    }

    public void setTotal_attended(String total_attended) {
        this.total_attended = total_attended;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.getTime().compareTo(((Timetable_POJO) o).getTime());
    }

    @Override
    public String toString() {
        return "Timetable_POJO{" +
                "ltpjc='" + ltpjc + '\'' +
                ", faculty_name='" + faculty_name + '\'' +
                ", course_code='" + course_code + '\'' +
                ", course_option='" + course_option + '\'' +
                ", course_mode='" + course_mode + '\'' +
                ", course_name='" + course_name + '\'' +
                ", venue='" + venue + '\'' +
                ", slot='" + slot + '\'' +
                ", course_type='" + course_type + '\'' +
                ", class_number='" + class_number + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", att='" + att + '\'' +
                ", total_class='" + total_class + '\'' +
                ", total_attended='" + total_attended + '\'' +
                ", details=" + details +
                '}';
    }
}
