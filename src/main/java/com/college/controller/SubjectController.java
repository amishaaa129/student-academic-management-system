package com.college.controller;

import com.college.exception.DatabaseException;
import com.college.model.Subject;
import com.college.service.SubjectService;

import java.util.List;

public class SubjectController {

    private final SubjectService subjectService = new SubjectService();

    public boolean addSubject(int courseId, String code,
                             String name, int semester, int credits)
            throws DatabaseException {
        return subjectService.addSubject(
                courseId, code, name, semester, credits
        );
    }

    public List<Subject> getAllSubjects() throws DatabaseException {
        return subjectService.getAllSubjects();
    }

    public Subject searchSubject(String code) throws DatabaseException {
        return subjectService.searchSubject(code);
    }

    public boolean deleteSubject(String code) throws DatabaseException {
        return subjectService.deleteSubject(code);
    }
}