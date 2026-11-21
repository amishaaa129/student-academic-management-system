
package com.college;

import com.college.controller.LoginController;
import com.college.controller.StudentController;
import com.college.exception.*;
import com.college.model.User;
import com.college.model.Role;
import com.college.model.Student;
import com.college.util.AppLogger;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LoginController controller = new LoginController();

        boolean running = true;

        while (running) {

            System.out.println("\n====================================");
            System.out.println(" STUDENT ACADEMIC MANAGEMENT SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        try {

                            User user = controller.authenticate(
                                    username, password
                            );

                            System.out.println("\nLogin successful!");
                            System.out.println("Welcome, " + user.getUsername());

                            System.out.println(
                                    controller.getDashboard(user)
                            );

                            // Redirect according to role
                            switch (user.getRole()) {

                                case ADMIN:
                                    adminDashboard(sc, user);
                                    break;

                                case FACULTY:
                                    facultyDashboard(sc, user);
                                    break;

                                case STUDENT:
                                    studentDashboard(sc, user);
                                    break;
                            }

                        } catch (AuthenticationException e) {

                            System.out.println(
                                    "Login failed: " + e.getMessage()
                            );

                        } catch (DatabaseException e) {

                            System.out.println(
                                    "Database error: " + e.getMessage()
                            );

                            AppLogger.error(
                                    "Login database error", e
                            );
                        }

                        break;

                    case 2:

                        running = false;

                        System.out.println(
                                "Thank you for using the system!"
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid choice. Try again."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }

        sc.close();
    }


    // ================= ADMIN DASHBOARD =================

    private static void adminDashboard(
            Scanner sc, User user) {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println("\n====================================");
            System.out.println("          ADMIN DASHBOARD");
            System.out.println("====================================");

            System.out.println("Welcome, " + user.getUsername());
            System.out.println();

            System.out.println("1. Manage Students");
            System.out.println("2. Manage Faculty");
            System.out.println("3. Manage Courses");
            System.out.println("4. Manage Subjects");
            System.out.println("5. Manage Timetable");
            System.out.println("6. View Notices");
            System.out.println("7. Logout");

            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        studentManagementMenu(sc);
                        break;

                    case 2:
                        System.out.println(
                                "Faculty management module coming soon!"
                        );
                        break;

                    case 3:
                        System.out.println(
                                "Course management module coming soon!"
                        );
                        break;

                    case 4:
                        System.out.println(
                                "Subject management module coming soon!"
                        );
                        break;

                    case 5:
                        System.out.println(
                                "Timetable module coming soon!"
                        );
                        break;

                    case 6:
                        System.out.println(
                                "Notice module coming soon!"
                        );
                        break;

                    case 7:

                        loggedIn = false;

                        System.out.println(
                                "Admin logged out successfully."
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid choice. Try again."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    // ================= STUDENT MANAGEMENT =================

    
private static void studentManagementMenu(Scanner sc) {

    StudentController controller = new StudentController();

    boolean running = true;

    while (running) {

        System.out.println("\n====================================");
        System.out.println("        STUDENT MANAGEMENT");
        System.out.println("====================================");

        System.out.println("1. Register Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Back to Admin Dashboard");

        System.out.print("Enter choice: ");

        try {

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                // REGISTER STUDENT
                case 1:

                    System.out.println("\n----- REGISTER STUDENT -----");

                    System.out.print("Username: ");
                    String username = sc.nextLine();

                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Roll Number: ");
                    String rollNumber = sc.nextLine();

                    System.out.print("Full Name: ");
                    String fullName = sc.nextLine();

                    System.out.print("Department: ");
                    String department = sc.nextLine();

                    System.out.print("Semester: ");
                    int semester = Integer.parseInt(sc.nextLine());

                    boolean registered = controller.registerStudent(
                            username,
                            password,
                            email,
                            rollNumber,
                            fullName,
                            department,
                            semester
                    );

                    if (registered) {
                        System.out.println(
                                "\nStudent registered successfully!"
                        );
                        System.out.println(
                                "Student can now log in using the credentials."
                        );
                    }

                    break;


                // VIEW ALL STUDENTS
                case 2:

                    System.out.println("\n----- ALL STUDENTS -----");

                    List<Student> students =
                            controller.getAllStudents();

                    if (students.isEmpty()) {

                        System.out.println("No students found.");

                    } else {

                        for (Student student : students) {
                            System.out.println(student);
                        }
                    }

                    break;


                // SEARCH STUDENT
                case 3:

                    System.out.println("\n----- SEARCH STUDENT -----");

                    System.out.print("Enter roll number: ");
                    String searchRoll = sc.nextLine();

                    Student student =
                            controller.searchStudent(searchRoll);

                    if (student != null) {

                        System.out.println("\nStudent found!");
                        System.out.println(student);

                    } else {

                        System.out.println("Student not found.");
                    }

                    break;


                // DELETE STUDENT
                case 4:

                    System.out.println("\n----- DELETE STUDENT -----");

                    System.out.print("Enter roll number: ");
                    String deleteRoll = sc.nextLine();

                    boolean deleted =
                            controller.deleteStudent(deleteRoll);

                    if (deleted) {
                        System.out.println(
                                "Student deleted successfully!"
                        );
                    } else {
                        System.out.println(
                                "Student not found."
                        );
                    }

                    break;


                // BACK
                case 5:

                    running = false;
                    break;


                default:

                    System.out.println(
                            "Invalid choice. Try again."
                    );
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid number entered."
            );

        } catch (Exception e) {

            System.out.println(
                    "\nOperation failed: " + e.getMessage()
            );

            AppLogger.error(
                    "Student management operation failed", e
            );
        }
    }
}


    // ================= FACULTY DASHBOARD =================

    private static void facultyDashboard(
            Scanner sc, User user) {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println("\n====================================");
            System.out.println("         FACULTY DASHBOARD");
            System.out.println("====================================");

            System.out.println("Welcome, " + user.getUsername());
            System.out.println();

            System.out.println("1. View My Subjects");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Upload Marks");
            System.out.println("4. Manage Assignments");
            System.out.println("5. Logout");

            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.println(
                                "Subject module coming soon!"
                        );
                        break;

                    case 2:
                        System.out.println(
                                "Attendance module coming soon!"
                        );
                        break;

                    case 3:
                        System.out.println(
                                "Marks module coming soon!"
                        );
                        break;

                    case 4:
                        System.out.println(
                                "Assignment module coming soon!"
                        );
                        break;

                    case 5:

                        loggedIn = false;

                        System.out.println(
                                "Faculty logged out successfully."
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid choice. Try again."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    // ================= STUDENT DASHBOARD =================

    private static void studentDashboard(
            Scanner sc, User user) {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println("\n====================================");
            System.out.println("         STUDENT DASHBOARD");
            System.out.println("====================================");

            System.out.println("Welcome, " + user.getUsername());
            System.out.println();

            System.out.println("1. View My Attendance");
            System.out.println("2. View My Marks");
            System.out.println("3. View My Timetable");
            System.out.println("4. View Assignments");
            System.out.println("5. View Notices");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.println(
                                "Attendance module coming soon!"
                        );
                        break;

                    case 2:
                        System.out.println(
                                "Marks module coming soon!"
                        );
                        break;

                    case 3:
                        System.out.println(
                                "Timetable module coming soon!"
                        );
                        break;

                    case 4:
                        System.out.println(
                                "Assignment module coming soon!"
                        );
                        break;

                    case 5:
                        System.out.println(
                                "Notice module coming soon!"
                        );
                        break;

                    case 6:

                        loggedIn = false;

                        System.out.println(
                                "Student logged out successfully."
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid choice. Try again."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
