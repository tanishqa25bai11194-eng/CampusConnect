
package com.campusconnect.model;

public class Student {

    private String studentId;
    private String name;
    private String branch;
    private int semester;

    public Student(String studentId, String name,
                   String branch, int semester) {

        this.studentId = studentId;
        this.name = name;
        this.branch = branch;
        this.semester = semester;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {

        return "Student{" +
                "ID='" + studentId + '\'' +
                ", Name='" + name + '\'' +
                ", Branch='" + branch + '\'' +
                ", Semester=" + semester +
                '}';
    }
}
