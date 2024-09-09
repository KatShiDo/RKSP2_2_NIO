package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        String fileName = "sample.txt";
        String[] lines = {
                "Это первая строка текста.",
                "Это вторая строка текста.",
                "Это третья строка текста."
        };

        writeLinesToFile(fileName, lines);
        readAndPrintFileContent(fileName);

    }

    private static void writeLinesToFile(String fileName, String[] lines) {
        Path filePath = Paths.get(fileName);
        try {
            Files.write(filePath, List.of(lines));
            System.out.println("Successfully wrote to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to " + fileName);
        }
    }

    private static void readAndPrintFileContent(String fileName) {
        Path filePath = Paths.get(fileName);
        try {
            List<String> fileLines = Files.readAllLines(filePath);
            System.out.println("File (" + fileName + ") content:");
            for (String line : fileLines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from " + fileName);
        }
    }
}