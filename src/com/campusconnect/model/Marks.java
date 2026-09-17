package com.campusconnect.model;

public class Marks {

    private String studentId;
    private String subject;
    private double marks;

    public Marks(String studentId, String subject, double marks) {
        this.studentId = studentId;
        this.subject = subject;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public double getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return "Marks{" +
                "Student ID='" + studentId + '\'' +
                ", Subject='" + subject + '\'' +
                ", Marks=" + marks +
                '}';
    }
}