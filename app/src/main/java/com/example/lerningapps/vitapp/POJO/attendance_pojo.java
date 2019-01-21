package com.example.lerningapps.vitapp.POJO;

public class attendance_pojo
{
    private attendance[] attendance;

    public attendance[] getAttendance ()
    {
        return attendance;
    }

    public void setAttendance (attendance[] attendance)
    {
        this.attendance = attendance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [attendance = "+attendance+"]";
    }
}