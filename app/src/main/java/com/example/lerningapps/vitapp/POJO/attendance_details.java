package com.example.lerningapps.vitapp.POJO;

public class attendance_details
{
    private String status;

    private String slot;

    private String date;
    public attendance_details(String date,String slot, String status){
        this.date=date;
        this.status=status;
        this.slot=slot;
    }
    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getSlot ()
    {
        return slot;
    }

    public void setSlot (String slot)
    {
        this.slot = slot;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", slot = "+slot+", date = "+date+"]";
    }
}