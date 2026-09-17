package com.campusconnect.model;

public class Attendance {
    private String studentId;
    private String date;
    private boolean present;

    public Attendance(String studentId, String date, boolean present) {
        this.studentId = studentId;
        this.date = date;
        this.present = present;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "Student ID='" + studentId + '\'' +
                ", Date='" + date + '\'' +
                ", Status=" + (present ? "Present" : "Absent") +
                '}';
    }
}