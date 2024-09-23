package org.example;

public class Task2 {
    public static void main(String[] args) {
        String sourceFile = "source.txt";
        String destinationFile = "destination.txt";

        CustomFileUtils.createLargeFile(sourceFile, 100);

        long startTime1 = System.currentTimeMillis();
        CustomFileUtils.copyUsingFileStreams(sourceFile, destinationFile);
        long endTime1 = System.currentTimeMillis();
        printTimeAndMemoryUsage("FileInputStream/FileOutputStream", startTime1, endTime1);

        long startTime2 = System.currentTimeMillis();
        CustomFileUtils.copyUsingFileChannel(sourceFile, destinationFile);
        long endTime2 = System.currentTimeMillis();
        printTimeAndMemoryUsage("FileChannel", startTime2, endTime2);

        long startTime3 = System.currentTimeMillis();
        CustomFileUtils.copyUsingApacheCommonsIo(sourceFile, destinationFile);
        long endTime3 = System.currentTimeMillis();
        printTimeAndMemoryUsage("Apache Commons IO", startTime3, endTime3);

        long startTime4 = System.currentTimeMillis();
        CustomFileUtils.copyUsingFilesClass(sourceFile, destinationFile);
        long endTime4 = System.currentTimeMillis();
        printTimeAndMemoryUsage("Files class", startTime4, endTime4);
    }

    private static void printTimeAndMemoryUsage(String method, long startTime, long endTime) {
        long elapsedTime = endTime - startTime;
        System.out.println("Method: " + method + ":");
        System.out.println("Elapsed time: " + elapsedTime + " ms");
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory used: " + memoryUsed / (1024 * 1024) + " MB\n");
    }
}
