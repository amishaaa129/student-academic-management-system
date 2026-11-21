package com.college.service;

import com.college.dao.SubjectDAO;
import com.college.exception.DatabaseException;
import com.college.model.Subject;

import java.util.List;

public class SubjectService {

    private final SubjectDAO subjectDAO = new SubjectDAO();

    public boolean addSubject(int courseId, String code,
                            String name, int semester, int credits)
            throws DatabaseException {

        if (courseId <= 0)
            throw new IllegalArgumentException("Invalid course ID.");

        if (code == null || code.isBlank())
            throw new IllegalArgumentException("Subject code required.");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Subject name required.");

        if (semester < 1 || semester > 8)
            throw new IllegalArgumentException("Semester must be 1-8.");

        if (credits < 1 || credits > 10)
            throw new IllegalArgumentException("Invalid credits.");

        return subjectDAO.addSubject(
                courseId, code, name, semester, credits
        );
    }

    public List<Subject> getAllSubjects() throws DatabaseException {
        return subjectDAO.getAllSubjects();
    }

    public Subject searchSubject(String code) throws DatabaseException {
        return subjectDAO.searchSubject(code);
    }

    public boolean deleteSubject(String code) throws DatabaseException {
        return subjectDAO.deleteSubject(code);
    }
}