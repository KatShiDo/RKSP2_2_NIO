package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class Task4 {
    public static void main(String[] args) {
        Path directory = Paths.get("myDirectory");
        Map<Path, Short> files = new HashMap<>();
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key = watchService.take();
            while (true) {
                //WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path filePath = (Path) event.context();
                        Short checksum = CustomFileUtils.calculateChecksum("myDirectory/" + filePath.toString());
                        System.out.println("New file created: " + filePath + "; with checksum: " + checksum);
                        files.put(filePath, checksum);
                        DirectoryWatcher.putNewFile(
                                filePath,
                                CustomFileUtils.readLinesFromFile(directory.resolve(filePath)));
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Path filePath = (Path) event.context();
                        Short checksum = CustomFileUtils.calculateChecksum("myDirectory/" + filePath.toString());
                        System.out.println("File modified: " + filePath + "; with checksum: " + checksum);
                        files.put(filePath, checksum);
                        DirectoryWatcher.detectFileChanges(directory.resolve(filePath));
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        Path filePath = (Path) event.context();
                        System.out.println("File deleted: " + filePath + "; with checksum: " + files.get(filePath));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception: " + e.getMessage());
        }
    }
}
