package com.example.lerningapps.vitapp.firebase;



public class time_fire {
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
    private String chat;

    public time_fire(String ltpjc, String faculty_name, String course_code, String course_option, String course_mode, String course_name, String venue, String slot, String course_type, String class_number, String chat) {
        this.ltpjc = ltpjc;
        this.faculty_name = faculty_name;
        this.course_code = course_code;
        this.course_option = course_option;
        this.course_mode = course_mode;
        this.course_name = course_name;
        this.venue = venue;
        this.slot = slot;
        this.course_type = course_type;
        this.class_number = class_number;
        this.chat = chat;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getLtpjc() {
        return ltpjc;
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

    @Override
    public String toString() {
        return "ClassPojo [ltpjc = " + ltpjc + ", faculty_name = " + faculty_name + ", course_code = " + course_code + ", course_option = " + course_option + ", course_mode = " + course_mode + ", course_name = " + course_name + ", venue = " + venue + ", slot = " + slot + ", course_type = " + course_type + ", class_number = " + class_number + "]";
    }
}