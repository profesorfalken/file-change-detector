package com.profesorfalken;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileChangeDetectorTest {
    public static final String BASE_DIRECTORY = "j:\\dev";

    @Test
    public void TestRegisteAllDirectories() throws IOException {
        FileChangeDetector fileChangeDetector = new FileChangeDetector();

        fileChangeDetector.watch(Paths.get(BASE_DIRECTORY));
    }
}