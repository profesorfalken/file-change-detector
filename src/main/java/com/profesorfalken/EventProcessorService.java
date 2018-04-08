package com.profesorfalken;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.concurrent.Future;

class EventProcessorService implements Runnable  {
    private Future registerWatcherService = null;
    private WatchService watcher = null;
    private Map<WatchKey, Path> watchers = null;

    private boolean ready = false;

    public boolean isReady() {
        return ready;
    }

    public EventProcessorService(Future registerWatcherService, WatchService watcher, Map<WatchKey, Path> watchers) {
        this.registerWatcherService = registerWatcherService;
        this.watcher = watcher;
        this.watchers = watchers;
    }

    @Override
    public void run() {
        System.out.println("Start processing events");
        boolean run = true;
        while (!registerWatcherService.isDone()) {
            //Wait
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished registering");

        while(run) {
            // wait for key to be signalled
            WatchKey watchKey;
            try {
                watchKey = watcher.take();
                System.out.println("Something detected!");
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event : watchKey.pollEvents()) {
                System.out.format("%s: %s\n", event.kind().name(), event.context());
            }



            boolean canReset = watchKey.reset();
            if (!canReset) {
                this.watchers.remove(watchKey);
            }
        }
    }
}
