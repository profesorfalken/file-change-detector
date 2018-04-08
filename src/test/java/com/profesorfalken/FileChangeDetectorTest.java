package com.profesorfalken;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class FileChangeDetectorTest {
    public static final String BASE_DIRECTORY = "j:\\dev";

    @Test
    public void TestRegisteAllDirectories() throws IOException, ExecutionException, InterruptedException {
        FileChangeDetector fileChangeDetector = new FileChangeDetector();

        fileChangeDetector.watch(Paths.get(BASE_DIRECTORY));

        while (!fileChangeDetector.isReadyToPollEvents()) {
            //Wait
            Thread.sleep(10);
        }
    }
}