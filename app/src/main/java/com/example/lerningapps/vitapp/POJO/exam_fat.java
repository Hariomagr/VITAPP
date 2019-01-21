package com.example.lerningapps.vitapp.POJO;

public class exam_fat{
    private String time;

    private String course_code;

    private String session;

    private String exam_date;

    private String week_day;

    private String table_number;

    private String course_name;

    private String venue;

    private String slot;

    private String course_type;

    public exam_fat(String time, String course_code, String session, String exam_date, String week_day, String table_number, String course_name, String venue, String slot, String course_type) {
        this.time = time;
        this.course_code = course_code;
        this.session = session;
        this.exam_date = exam_date;
        this.week_day = week_day;
        this.table_number = table_number;
        this.course_name = course_name;
        this.venue = venue;
        this.slot = slot;
        this.course_type = course_type;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getCourse_code ()
    {
        return course_code;
    }

    public void setCourse_code (String course_code)
    {
        this.course_code = course_code;
    }

    public String getSession ()
    {
        return session;
    }

    public void setSession (String session)
    {
        this.session = session;
    }

    public String getExam_date ()
    {
        return exam_date;
    }

    public void setExam_date (String exam_date)
    {
        this.exam_date = exam_date;
    }

    public String getWeek_day ()
    {
        return week_day;
    }

    public void setWeek_day (String week_day)
    {
        this.week_day = week_day;
    }

    public String getTable_number ()
    {
        return table_number;
    }

    public void setTable_number (String table_number)
    {
        this.table_number = table_number;
    }

    public String getCourse_name ()
    {
        return course_name;
    }

    public void setCourse_name (String course_name)
    {
        this.course_name = course_name;
    }

    public String getVenue ()
    {
        return venue;
    }

    public void setVenue (String venue)
    {
        this.venue = venue;
    }

    public String getSlot ()
    {
        return slot;
    }

    public void setSlot (String slot)
    {
        this.slot = slot;
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
        return "ClassPojo [time = "+time+", course_code = "+course_code+", session = "+session+", exam_date = "+exam_date+", week_day = "+week_day+", table_number = "+table_number+", course_name = "+course_name+", venue = "+venue+", slot = "+slot+", course_type = "+course_type+"]";
    }
}