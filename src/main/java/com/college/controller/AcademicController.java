package com.college.controller;

import com.college.exception.DatabaseException;
import com.college.service.AcademicService;

public class AcademicController {

    private final AcademicService academicService = new AcademicService();

    public boolean assignFaculty(int facultyId, int subjectId,
                               String academicYear)
            throws DatabaseException {
        return academicService.assignFaculty(
                facultyId, subjectId, academicYear
        );
    }

    public boolean registerStudentSubject(
            int studentId, int subjectId, String academicYear)
            throws DatabaseException {
        return academicService.registerStudentSubject(
                studentId, subjectId, academicYear
        );
    }

    public boolean addTimetableEntry(
            int subjectId, int facultyId, String day,
            String startTime, String endTime, String room)
            throws DatabaseException {
        return academicService.addTimetableEntry(
                subjectId, facultyId, day,
                startTime, endTime, room
        );
    }

    public boolean postNotice(String title, String content, int userId)
            throws DatabaseException {
        return academicService.postNotice(title, content, userId);
    }

    public void viewTimetable() throws DatabaseException {
        academicService.viewTimetable();
    }
}