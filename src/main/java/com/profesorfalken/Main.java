package com.profesorfalken;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class Main {
    public static final String BASE_DIRECTORY = "j:\\dev";

    Map<String, WatchService> watchers = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Files.list(new File(BASE_DIRECTORY).toPath())
                .filter(Files::isDirectory)
                .forEach(path -> {
                    System.out.println(path.getFileName());
                });

        /*
        try (Stream<Path> paths = Files.walk(Paths.get(BASE_DIRECTORY))) {
            paths.filter(Files::isDirectory)
                    .forEach(System.out::println);
        }*/

/*
        WatchService watchService = path.getFileSystem().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey watchKey = null;
        while (true) {
            watchKey = watchService.poll();
            if(watchKey != null) {
                watchKey.pollEvents().stream().forEach(event -> System.out.println("Modify file: " + event.context() + " Event: " + event.kind()));
                watchKey.reset();
            }
        }*/
    }
// https://howtodoinjava.com/java-8/java-8-watchservice-api-tutorial/
}
