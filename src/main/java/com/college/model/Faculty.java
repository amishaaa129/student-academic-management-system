package com.college.model;

public class Faculty {

    private int facultyId;
    private int userId;
    private String facultyCode;
    private String fullName;
    private String department;
    private String designation;

    public Faculty(int facultyId, int userId, String facultyCode,
                  String fullName, String department, String designation) {
        this.facultyId = facultyId;
        this.userId = userId;
        this.facultyCode = facultyCode;
        this.fullName = fullName;
        this.department = department;
        this.designation = designation;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public int getUserId() {
        return userId;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    public String toString() {
        return "Faculty ID: " + facultyId +
                "\nFaculty Code: " + facultyCode +
                "\nName: " + fullName +
                "\nDepartment: " + department +
                "\nDesignation: " + designation;
    }
}