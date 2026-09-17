package com.campusconnect.service;

import com.campusconnect.model.Attendance;
import com.campusconnect.repository.AttendanceRepository;

import java.util.ArrayList;
import java.util.List;

public class AttendanceService {

    private final AttendanceRepository repository;
    private final List<Attendance> attendanceList;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
        this.attendanceList = new ArrayList<>(repository.loadAttendance());
    }

    // Marks attendance and prevents duplicate entries
    // for the same student on the same date.
    public boolean markAttendance(Attendance attendance) {

        for (Attendance existing : attendanceList) {

            if (existing.getStudentId()
                    .equalsIgnoreCase(attendance.getStudentId())
                    && existing.getDate()
                    .equalsIgnoreCase(attendance.getDate())) {

                return false;
            }
        }

        attendanceList.add(attendance);
        repository.saveAttendance(attendanceList);

        return true;
    }

    // Returns all attendance records for a student.
    public List<Attendance> getAttendanceForStudent(String studentId) {

        List<Attendance> result = new ArrayList<>();

        for (Attendance attendance : attendanceList) {

            if (attendance.getStudentId()
                    .equalsIgnoreCase(studentId)) {

                result.add(attendance);
            }
        }

        return result;
    }

    // Calculates attendance percentage.
    public double calculateAttendancePercentage(String studentId) {

        List<Attendance> studentAttendance =
                getAttendanceForStudent(studentId);

        if (studentAttendance.isEmpty()) {
            return 0.0;
        }

        int presentCount = 0;

        for (Attendance attendance : studentAttendance) {

            if (attendance.isPresent()) {
                presentCount++;
            }
        }

        return (presentCount * 100.0)
                / studentAttendance.size();
    }
}