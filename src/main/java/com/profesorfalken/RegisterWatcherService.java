package com.profesorfalken;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

class RegisterWatcherService implements Runnable {
    private Path baseDirectory = null;
    private WatchService watcher = null;
    private Map<WatchKey, Path> watchers = null;

    public RegisterWatcherService(Path baseDirectory, WatchService watcher, Map<WatchKey, Path> watchers) {
        this.baseDirectory = baseDirectory;
        this.watcher = watcher;
        this.watchers = watchers;
    }

    @Override
    public void run() {
        try {
            Files.list(this.baseDirectory)
                    .filter(Files::isDirectory)
                    .forEach(path -> {
                        System.out.println("Register and walk " + path);
                        try {
                            registerDirectory(path);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void registerDirectory(Path rootPath) throws IOException {
        Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                System.out.println("Register " + dir);
                watchers.put(key, dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
