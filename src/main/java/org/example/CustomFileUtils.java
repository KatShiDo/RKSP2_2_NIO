package org.example;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CustomFileUtils {

    private CustomFileUtils() {
    }

    public static void createLargeFile(String fileName, int sizeMb) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024 * 1024];
            for (int i = 0; i < sizeMb; i++) {
                fos.write(buffer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
    }

    public static void copyUsingFileStreams(String src, String dest) {
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024 * 1024];
            int bytesRead = 0;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Some of these files not found: " + src + " or " + dest);
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
    }

    public static void copyUsingFileChannel(String src, String dest) {
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest);
             FileChannel srcChannel = fis.getChannel();
             FileChannel destChannel = fos.getChannel()) {
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
        } catch (FileNotFoundException e) {
            System.out.println("Some of these files not found: " + src + " or " + dest);
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
    }

    public static void copyUsingApacheCommonsIo(String src, String dest) {
        File srcFile = new File(src);
        File destFile = new File(dest);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
    }

    public static void copyUsingFilesClass(String src, String dest) {
        Path srcFile = Path.of(src);
        Path destFile = Path.of(dest);
        try {
            Files.copy(srcFile, destFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
    }

    public static short calculateChecksum(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel fileChannel = fis.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(2);
            short checksum = 0;
            while (fileChannel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    checksum ^= buffer.get();
                }
                buffer.clear();
            }
            return checksum;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
        return 0;
    }

    public static List<String> readLinesFromFile(Path filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
        return lines;
    }
}
