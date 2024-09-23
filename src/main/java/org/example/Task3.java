package org.example;

public class Task3 {
    public static void main(String[] args) {
        String filePath = "sample.txt";
        short checksum = CustomFileUtils.calculateChecksum(filePath);
        System.out.printf("Checksum: %s: 0x%04X%n\n", filePath, checksum);
    }
}
