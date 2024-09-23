package org.example;

import java.io.IOException;
import java.nio.file.*;

public class Task4 {
    public static void main(String[] args) {
        Path directory = Paths.get("myDirectory");
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path filePath = (Path) event.context();
                        System.out.println("New file created: " + filePath);
                        DirectoryWatcher.putNewFile(
                                filePath,
                                CustomFileUtils.readLinesFromFile(directory.resolve(filePath)));

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
