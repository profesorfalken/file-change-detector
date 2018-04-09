package com.profesorfalken;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.Future;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

class EventProcessorService implements Runnable  {
    private final  Path baseDirectory;
    private final  WatchService watchService;
    private final  Map<WatchKey, Path> watchers;

    private boolean ready = false;

    public boolean isReady() {
        return ready;
    }

    public EventProcessorService(Path baseDirectory, WatchService watchService, Map<WatchKey, Path> watchers) {
        this.baseDirectory = baseDirectory;
        this.watchService = watchService;
        this.watchers = watchers;
    }

    @Override
    public void run() {
        System.out.println("Start processing events");
        registerDirectories();
        System.out.println("Finished registering");

        while(true) {
            // wait for key to be signalled
            WatchKey watchKey;
            try {
                watchKey = this.watchService.take();
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

    private void registerDirectories() {
        try {
            Files.list(this.baseDirectory)
                    .filter(Files::isDirectory)
                    .forEach((Path path) -> {
                        System.out.println("Register and walk " + path);
                        try {
                            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                                @Override
                                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                    WatchKey key = dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                                    System.out.println("Register " + dir);
                                    watchers.put(key, dir);
                                    return FileVisitResult.CONTINUE;
                                }
                            });
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }
}
