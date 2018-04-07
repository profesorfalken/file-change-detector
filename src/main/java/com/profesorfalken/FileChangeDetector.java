package com.profesorfalken;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileChangeDetector {
    private final WatchService watcher = FileSystems.getDefault().newWatchService();;

    Map<WatchKey, Path> watchers = new HashMap<>();

    Future registerWatcherService = null;

    public FileChangeDetector() throws IOException {
    }

    public void watch(Path baseDirectory) throws IOException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        this.registerWatcherService = executorService.submit(new RegisterWatcherService(baseDirectory, this.watcher, this.watchers));
        processEvents();
    }

    private void processEvents() {
        System.out.println("Start processing events");
        boolean run = true;
        while(run) {
            if (this.registerWatcherService.isDone()) {
                System.out.println("Finished");
                run = false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//https://howtodoinjava.com/java-8/java-8-watchservice-api-tutorial/

}
