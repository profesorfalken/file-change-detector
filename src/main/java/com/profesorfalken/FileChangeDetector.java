package com.profesorfalken;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileChangeDetector {
    private final WatchService watcher = FileSystems.getDefault().newWatchService();;

    Map<WatchKey, Path> watchers = new HashMap<>();

    EventProcessorService eventProcessor = null;
    Future eventProcessorService = null;

    public FileChangeDetector() throws IOException {
    }

    public boolean isReadyToPollEvents () {
        return eventProcessor.isReady();
    }

    public void watch(Path baseDirectory) throws IOException, ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future registerWatcherService = executorService.submit(new RegisterWatcherService(baseDirectory, this.watcher, this.watchers));
        this.eventProcessor = new EventProcessorService(registerWatcherService, this.watcher, this.watchers);
        this.eventProcessorService = executorService.submit(this.eventProcessor);
    }

//https://howtodoinjava.com/java-8/java-8-watchservice-api-tutorial/

}
