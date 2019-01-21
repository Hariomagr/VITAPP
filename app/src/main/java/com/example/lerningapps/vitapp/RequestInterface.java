package com.example.lerningapps.vitapp;

import com.example.lerningapps.vitapp.POJO.Curriculum_Pojo;
import com.example.lerningapps.vitapp.POJO.assignments_pojo;
import com.example.lerningapps.vitapp.POJO.attendance_pojo;
import com.example.lerningapps.vitapp.POJO.exam_schedule_pojo;
import com.example.lerningapps.vitapp.POJO.facultyall_pojo;
import com.example.lerningapps.vitapp.POJO.facultylate_pojo;
import com.example.lerningapps.vitapp.POJO.grades_pojo;
import com.example.lerningapps.vitapp.POJO.marks_pojo;
import com.example.lerningapps.vitapp.POJO.timetable_pojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {

    @FormUrlEncoded
    @POST("student/timetable")
    Call<timetable_pojo> gettimetable( @Field("reg_no") String reg,@Field("password") String password,@Field("semester") String semester);

    @FormUrlEncoded
    @POST("student/attendance")
    Call<attendance_pojo> getattendance(@Field("reg_no") String reg, @Field("password") String password,@Field("semester") String semester);

    @FormUrlEncoded
    @POST("student/marks")
    Call<marks_pojo> getmarks(@Field("reg_no") String reg, @Field("password") String password,@Field("semester") String semester);

    @FormUrlEncoded
    @POST("student/exam_schedule")
    Call<exam_schedule_pojo> getexamschedule(@Field("reg_no") String reg, @Field("password") String password,@Field("semester") String semester);

    @FormUrlEncoded
    @POST("student/grades")
    Call<grades_pojo> getgrades(@Field("reg_no") String reg, @Field("password") String password,@Field("semester") String semester);

    @FormUrlEncoded
    @POST("student/assignments")
    Call<assignments_pojo> getassignments(@Field("reg_no") String reg, @Field("password") String password,@Field("semester") String semester);

    @FormUrlEncoded
    @POST("student/curriculum")
    Call<Curriculum_Pojo> getCurriculum(@Field("reg_no") String reg, @Field("password") String password);

    @GET("faculty/all")
    Call<facultyall_pojo> getfacultyall();

    @GET("faculty/late")
    Call<facultylate_pojo> getfacultylate();
}
