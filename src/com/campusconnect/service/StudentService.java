
package com.campusconnect.service;

import com.campusconnect.model.Student;
import com.campusconnect.repository.FileRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final FileRepository repository;

    private final List<Student> students;

    public StudentService(FileRepository repository) {

        this.repository = repository;

        this.students = new ArrayList<>(
                repository.loadStudents()
        );
    }

    public boolean addStudent(Student student) {

        if (findStudentById(student.getStudentId())
                != null) {

            return false;
        }

        students.add(student);

        repository.saveStudents(students);

        return true;
    }

    public Student findStudentById(String studentId) {

        for (Student student : students) {

            if (student.getStudentId()
                    .equalsIgnoreCase(studentId)) {

                return student;
            }
        }

        return null;
    }

    public List<Student> getAllStudents() {

        return new ArrayList<>(students);
    }

    public boolean updateStudent(
            String studentId,
            String name,
            String branch,
            int semester) {

        Student student =
                findStudentById(studentId);

        if (student == null) {
            return false;
        }

        student.setName(name);
        student.setBranch(branch);
        student.setSemester(semester);

        repository.saveStudents(students);

        return true;
    }

    public boolean deleteStudent(String studentId) {

        Student student =
                findStudentById(studentId);

        if (student == null) {
            return false;
        }

        students.remove(student);

        repository.saveStudents(students);

        return true;
    }

    public List<Student> searchStudentsByName(
            String name) {

        List<Student> results = new ArrayList<>();

        for (Student student : students) {

            if (student.getName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {

                results.add(student);
            }
        }

        return results;
    }
}
