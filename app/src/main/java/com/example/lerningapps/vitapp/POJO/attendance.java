package com.example.lerningapps.vitapp.POJO;

import java.util.ArrayList;

public class attendance
{
    private String total_classes;

    private String course_code;

    private ArrayList<attendance_details> details;

    private String course_title;

    private String attendance_percentage;

    private String slot;

    private String attended_classes;

    private String course_type;
    public attendance(String total_classes,String course_code,ArrayList<attendance_details> attendance_details,String course_title,String attendance_percentage,String slot,String attended_classes,String course_type){

    }

    public String getTotal_classes ()
    {
        return total_classes;
    }

    public void setTotal_classes (String total_classes)
    {
        this.total_classes = total_classes;
    }

    public String getCourse_code ()
    {
        return course_code;
    }

    public void setCourse_code (String course_code)
    {
        this.course_code = course_code;
    }

    public ArrayList<attendance_details> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<attendance_details> details) {
        this.details = details;
    }

    public String getCourse_title ()
    {
        return course_title;
    }

    public void setCourse_title (String course_title)
    {
        this.course_title = course_title;
    }

    public String getAttendance_percentage ()
    {
        return attendance_percentage;
    }

    public void setAttendance_percentage (String attendance_percentage)
    {
        this.attendance_percentage = attendance_percentage;
    }

    public String getSlot ()
    {
        return slot;
    }

    public void setSlot (String slot)
    {
        this.slot = slot;
    }

    public String getAttended_classes ()
    {
        return attended_classes;
    }

    public void setAttended_classes (String attended_classes)
    {
        this.attended_classes = attended_classes;
    }

    public String getCourse_type ()
    {
        return course_type;
    }

    public void setCourse_type (String course_type)
    {
        this.course_type = course_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total_classes = "+total_classes+", course_code = "+course_code+", details = "+details+", course_title = "+course_title+", attendance_percentage = "+attendance_percentage+", slot = "+slot+", attended_classes = "+attended_classes+", course_type = "+course_type+"]";
    }
}