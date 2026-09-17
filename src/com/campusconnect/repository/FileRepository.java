
package com.campusconnect.repository;

import com.campusconnect.model.Student;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

    private final String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    public List<Student> loadStudents() {

        List<Student> students = new ArrayList<>();

        Path path = Paths.get(filePath);

        try {

            if (!Files.exists(path)) {
                createFile();
                return students;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {

                String line = lines.get(i);

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length != 4) {
                    System.out.println(
                            "Warning: Invalid record skipped."
                    );
                    continue;
                }

                try {

                    String studentId = data[0].trim();
                    String name = data[1].trim();
                    String branch = data[2].trim();

                    int semester =
                            Integer.parseInt(data[3].trim());

                    Student student = new Student(
                            studentId,
                            name,
                            branch,
                            semester
                    );

                    students.add(student);

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Invalid semester. Record skipped."
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error reading file: "
                            + e.getMessage()
            );
        }

        return students;
    }

    public void saveStudents(List<Student> students) {

        Path path = Paths.get(filePath);

        try {

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            try (BufferedWriter writer =
                         Files.newBufferedWriter(path)) {

                writer.write(
                        "studentId,name,branch,semester"
                );

                writer.newLine();

                for (Student student : students) {

                    writer.write(
                            student.getStudentId() + "," +
                                    student.getName() + "," +
                                    student.getBranch() + "," +
                                    student.getSemester()
                    );

                    writer.newLine();
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving file: "
                            + e.getMessage()
            );
        }
    }

    private void createFile() {

        Path path = Paths.get(filePath);

        try {

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            try (BufferedWriter writer =
                         Files.newBufferedWriter(path)) {

                writer.write(
                        "studentId,name,branch,semester"
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error creating file: "
                            + e.getMessage()
            );
        }
    }
}
