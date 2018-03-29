package com.example.hp1.attendance;

/**
 * Created by Ayush on 27-Feb-18.
 */

public class Student_info {

    private String faculty_number;
    private String  name;
private String attendance;

    public Student_info(String faculty_number, String name, String attendance) {
        this.faculty_number = faculty_number;
        this.name = name;
        this.attendance = attendance;
    }

    public String getFaculty_number() {
        return faculty_number;
    }


    public String getName() {
        return name;
    }


    public void setFaculty_number(String faculty_number) {
        this.faculty_number = faculty_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
