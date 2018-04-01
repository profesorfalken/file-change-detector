package com.profesorfalken;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class Main {
    int pp;
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("J:\\dev");
        WatchService watchService = path.getFileSystem().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
new Main().pp=2;
        WatchKey watchKey = null;
        while (true) {
            watchKey = watchService.poll();
            if(watchKey != null) {
                watchKey.pollEvents().stream().forEach(event -> System.out.println("Modify file: " + event.context() + " Event: " + event.kind()));
                watchKey.reset();
            }
        }
    }
// https://howtodoinjava.com/java-8/java-8-watchservice-api-tutorial/
}
