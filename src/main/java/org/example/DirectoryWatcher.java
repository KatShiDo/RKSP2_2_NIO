package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryWatcher {

    private DirectoryWatcher() {
    }

    private static final Map<Path, List<String>> fileContentsMap = new HashMap<>();

    public static void putNewFile(Path path, List<String> content) {
        fileContentsMap.put(path, content);
    }

    public static void detectFileChanges(Path filePath) {
        List<String> newFileContents = CustomFileUtils.readLinesFromFile(filePath);
        List<String> oldFileContents = fileContentsMap.get(filePath);

        if (oldFileContents != null) {
            List<String> addedLines = newFileContents.stream()
                    .filter(line -> !oldFileContents.contains(line))
                    .toList();
            List<String> deletedLines = oldFileContents.stream()
                    .filter(line -> !newFileContents.contains(line))
                    .toList();
            if (!addedLines.isEmpty()) {
                System.out.println("Added lines in " + filePath + ":");
                addedLines.forEach(line -> System.out.println("+ " + line));
            }
            if (!deletedLines.isEmpty()) {
                System.out.println("Deleted lines in " + filePath + ":");
                deletedLines.forEach(line -> System.out.println("- " + line));
            }
            fileContentsMap.put(filePath, newFileContents);
        }
    }
}
