package com.college.model;

public class Course {

    private int courseId;
    private String courseCode;
    private String courseName;
    private String department;
    private int durationYears;

    public Course(int courseId, String courseCode, String courseName,
                 String department, int durationYears) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.department = department;
        this.durationYears = durationYears;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDepartment() {
        return department;
    }

    public int getDurationYears() {
        return durationYears;
    }

    @Override
    public String toString() {
        return "\nCourse ID: " + courseId +
                "\nCode: " + courseCode +
                "\nName: " + courseName +
                "\nDepartment: " + department +
                "\nDuration: " + durationYears + " years";
    }
}