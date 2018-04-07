package com.profesorfalken;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileChangeDetector {
    public static final String BASE_DIRECTORY = "j:\\dev";

    private final WatchService watcher = FileSystems.getDefault().newWatchService();;

    Map<WatchKey, Path> watchers = new HashMap<>();

    public FileChangeDetector() throws IOException {
    }

    public void registerAllDirectories() throws IOException {
        Files.list(new File(BASE_DIRECTORY).toPath())
                .filter(Files::isDirectory)
                .forEach(path -> {
                    System.out.println("Register and walk " + path);
                    registerDirectory(path);
                });
    }

    private void registerDirectory(Path rootPath) {
        try {
        Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                System.out.println("Register " + dir);
                watchers.put(key, dir);
                return FileVisitResult.CONTINUE;
            }
        }); } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

//https://howtodoinjava.com/java-8/java-8-watchservice-api-tutorial/

}
