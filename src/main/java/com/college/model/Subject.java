package com.college.model;

public class Subject {

    private int subjectId;
    private int courseId;
    private String subjectCode;
    private String subjectName;
    private int semester;
    private int credits;

    public Subject(int subjectId, int courseId,
                  String subjectCode, String subjectName,
                  int semester, int credits) {

        this.subjectId = subjectId;
        this.courseId = courseId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.semester = semester;
        this.credits = credits;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSemester() {
        return semester;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return "\nSubject ID: " + subjectId +
                "\nCode: " + subjectCode +
                "\nName: " + subjectName +
                "\nCourse ID: " + courseId +
                "\nSemester: " + semester +
                "\nCredits: " + credits;
    }
}