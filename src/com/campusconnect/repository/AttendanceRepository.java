package com.campusconnect.repository;

import com.campusconnect.model.Attendance;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepository {

    private final String filePath;

    public AttendanceRepository(String filePath) {
        this.filePath = filePath;
    }

    public List<Attendance> loadAttendance() {

        List<Attendance> attendanceList = new ArrayList<>();
        Path path = Paths.get(filePath);

        try {

            if (!Files.exists(path)) {
                createFile();
                return attendanceList;
            }

            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {

                String line = lines.get(i);

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length != 3) {
                    System.out.println("Warning: Invalid attendance record skipped.");
                    continue;
                }

                String studentId = data[0].trim();
                String date = data[1].trim();
                boolean present = Boolean.parseBoolean(data[2].trim());

                attendanceList.add(
                        new Attendance(studentId, date, present)
                );
            }

        } catch (IOException e) {
            System.out.println("Error reading attendance file: " + e.getMessage());
        }

        return attendanceList;
    }

    public void saveAttendance(List<Attendance> attendanceList) {

        Path path = Paths.get(filePath);

        try {

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {

                writer.write("studentId,date,present");
                writer.newLine();

                for (Attendance attendance : attendanceList) {

                    writer.write(
                            attendance.getStudentId() + "," +
                                    attendance.getDate() + "," +
                                    attendance.isPresent()
                    );

                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving attendance file: " + e.getMessage());
        }
    }

    private void createFile() {

        Path path = Paths.get(filePath);

        try {

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {

                writer.write("studentId,date,present");
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error creating attendance file: " + e.getMessage());
        }
    }
}