package com.college.controller;

import com.college.exception.DatabaseException;
import com.college.model.Faculty;
import com.college.service.FacultyService;

import java.util.List;

public class FacultyController {

    private final FacultyService facultyService = new FacultyService();

    public boolean registerFaculty(
            String username,
            String password,
            String email,
            String facultyCode,
            String fullName,
            String department,
            String designation
    ) throws DatabaseException {

        return facultyService.registerFaculty(
                username, password, email, facultyCode,
                fullName, department, designation
        );
    }

    public List<Faculty> getAllFaculty() throws DatabaseException {
        return facultyService.getAllFaculty();
    }

    public Faculty searchFaculty(String facultyCode)
            throws DatabaseException {

        return facultyService.searchFaculty(facultyCode);
    }

    public boolean deleteFaculty(String facultyCode)
            throws DatabaseException {

        return facultyService.deleteFaculty(facultyCode);
    }
}