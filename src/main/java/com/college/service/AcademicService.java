package com.college.service;

import com.college.dao.AcademicDAO;
import com.college.exception.DatabaseException;

public class AcademicService {

    private final AcademicDAO academicDAO = new AcademicDAO();

    public boolean assignFaculty(int facultyId, int subjectId,
                               String academicYear)
            throws DatabaseException {

        if (facultyId <= 0 || subjectId <= 0)
            throw new IllegalArgumentException("Invalid IDs.");

        if (academicYear == null || academicYear.isBlank())
            throw new IllegalArgumentException("Academic year required.");

        return academicDAO.assignFaculty(
                facultyId, subjectId, academicYear
        );
    }

    public boolean registerStudentSubject(
            int studentId, int subjectId, String academicYear)
            throws DatabaseException {

        if (studentId <= 0 || subjectId <= 0)
            throw new IllegalArgumentException("Invalid IDs.");

        if (academicYear == null || academicYear.isBlank())
            throw new IllegalArgumentException("Academic year required.");

        return academicDAO.registerStudentSubject(
                studentId, subjectId, academicYear
        );
    }

    public boolean addTimetableEntry(
            int subjectId, int facultyId, String day,
            String startTime, String endTime, String room)
            throws DatabaseException {

        if (subjectId <= 0 || facultyId <= 0)
            throw new IllegalArgumentException("Invalid IDs.");

        if (startTime.compareTo(endTime) >= 0)
            throw new IllegalArgumentException(
                    "End time must be after start time."
            );

        if (room == null || room.isBlank())
            throw new IllegalArgumentException("Room is required.");

        return academicDAO.addTimetableEntry(
                subjectId, facultyId, day,
                startTime, endTime, room
        );
    }

    public boolean postNotice(String title, String content, int userId)
            throws DatabaseException {

        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Notice title required.");

        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Notice content required.");

        return academicDAO.postNotice(title, content, userId);
    }

    public void viewTimetable() throws DatabaseException {
        academicDAO.viewTimetable();
    }
}