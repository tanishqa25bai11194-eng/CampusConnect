package com.campusconnect.service;

import com.campusconnect.model.Marks;
import com.campusconnect.model.Student;

import java.util.List;

public class ReportService {

    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final MarksService marksService;

    public ReportService(
            StudentService studentService,
            AttendanceService attendanceService,
            MarksService marksService) {

        this.studentService = studentService;
        this.attendanceService = attendanceService;
        this.marksService = marksService;
    }

    public void generateStudentReport(String studentId) {

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {

            System.out.println("Student not found.");

            return;
        }

        System.out.println();
        System.out.println("================================");
        System.out.println("          STUDENT REPORT");
        System.out.println("================================");

        System.out.println(
                "Student ID : " + student.getStudentId()
        );

        System.out.println(
                "Name       : " + student.getName()
        );

        System.out.println(
                "Branch     : " + student.getBranch()
        );

        System.out.println(
                "Semester   : " + student.getSemester()
        );

        System.out.println();
        System.out.println("---------- ATTENDANCE ----------");

        double attendance =
                attendanceService
                        .calculateAttendancePercentage(studentId);

        System.out.printf(
                "Attendance : %.2f%%%n",
                attendance
        );

        System.out.println();
        System.out.println("------------- MARKS -------------");

        List<Marks> marksList =
                marksService.getMarksForStudent(studentId);

        if (marksList.isEmpty()) {

            System.out.println(
                    "No marks records found."
            );

        } else {

            for (Marks marks : marksList) {

                System.out.println(
                        marks.getSubject()
                                + " : "
                                + marks.getMarks()
                );
            }

            double average =
                    marksService.calculateAverage(studentId);

            String grade =
                    marksService.calculateGrade(studentId);

            System.out.printf(
                    "Average    : %.2f%n",
                    average
            );

            System.out.println(
                    "Grade      : " + grade
            );
        }

        System.out.println(
                "================================"
        );
    }
}