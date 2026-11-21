package com.college.model;

public class Student {

    private int studentId;
    private int userId;
    private String rollNumber;
    private String fullName;
    private String department;
    private int semester;

    public Student(int studentId, int userId, String rollNumber,
                  String fullName, String department, int semester) {
        this.studentId = studentId;
        this.userId = userId;
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.department = department;
        this.semester = semester;
    }

    // Getters

    public int getStudentId() {
        return studentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public int getSemester() {
        return semester;
    }

    // Setters

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
               "\nUser ID: " + userId +
               "\nRoll Number: " + rollNumber +
               "\nName: " + fullName +
               "\nDepartment: " + department +
               "\nSemester: " + semester;
    }
}