package com.college.service;

import com.college.dao.FacultyDAO;
import com.college.exception.DatabaseException;
import com.college.model.Faculty;

import java.util.List;

public class FacultyService {

    private final FacultyDAO facultyDAO = new FacultyDAO();

    public boolean registerFaculty(
            String username,
            String password,
            String email,
            String facultyCode,
            String fullName,
            String department,
            String designation
    ) throws DatabaseException {

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username is required.");

        if (password == null || password.length() < 6)
            throw new IllegalArgumentException(
                    "Password must contain at least 6 characters."
            );

        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email address.");

        if (facultyCode == null || facultyCode.isBlank())
            throw new IllegalArgumentException("Faculty code is required.");

        if (fullName == null || fullName.isBlank())
            throw new IllegalArgumentException("Name is required.");

        if (department == null || department.isBlank())
            throw new IllegalArgumentException("Department is required.");

        if (designation == null || designation.isBlank())
            throw new IllegalArgumentException("Designation is required.");

        return facultyDAO.registerFaculty(
                username, password, email, facultyCode,
                fullName, department, designation
        );
    }

    public List<Faculty> getAllFaculty() throws DatabaseException {
        return facultyDAO.getAllFaculty();
    }

    public Faculty searchFaculty(String facultyCode)
            throws DatabaseException {

        if (facultyCode == null || facultyCode.isBlank())
            throw new IllegalArgumentException("Faculty code is required.");

        return facultyDAO.searchFaculty(facultyCode);
    }

    public boolean deleteFaculty(String facultyCode)
            throws DatabaseException {

        if (facultyCode == null || facultyCode.isBlank())
            throw new IllegalArgumentException("Faculty code is required.");

        return facultyDAO.deleteFaculty(facultyCode);
    }
}