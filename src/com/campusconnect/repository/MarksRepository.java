package com.campusconnect.repository;

import com.campusconnect.model.Marks;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MarksRepository {

    private final String filePath;

    public MarksRepository(String filePath) {
        this.filePath = filePath;
    }

    public List<Marks> loadMarks() {

        List<Marks> marksList = new ArrayList<>();
        Path path = Paths.get(filePath);

        try {
            if (!Files.exists(path)) {
                createFile();
                return marksList;
            }

            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {

                String line = lines.get(i);

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length != 3) {
                    System.out.println("Warning: Invalid marks record skipped.");
                    continue;
                }

                try {
                    String studentId = data[0].trim();
                    String subject = data[1].trim();
                    double marks = Double.parseDouble(data[2].trim());

                    marksList.add(
                            new Marks(studentId, subject, marks)
                    );

                } catch (NumberFormatException e) {
                    System.out.println("Invalid marks value. Record skipped.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading marks file: " + e.getMessage());
        }

        return marksList;
    }

    public void saveMarks(List<Marks> marksList) {

        Path path = Paths.get(filePath);

        try {

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            BufferedWriter writer = Files.newBufferedWriter(path);

            writer.write("studentId,subject,marks");
            writer.newLine();

            for (Marks marks : marksList) {

                writer.write(
                        marks.getStudentId() + "," +
                                marks.getSubject() + "," +
                                marks.getMarks()
                );

                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving marks file: " + e.getMessage());
        }
    }

    private void createFile() {

        Path path = Paths.get(filePath);

        try {

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            BufferedWriter writer = Files.newBufferedWriter(path);

            writer.write("studentId,subject,marks");
            writer.newLine();

            writer.close();

        } catch (IOException e) {
            System.out.println("Error creating marks file: " + e.getMessage());
        }
    }
}